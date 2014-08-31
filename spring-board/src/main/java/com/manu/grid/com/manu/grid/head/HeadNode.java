package com.manu.grid.com.manu.grid.head;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class HeadNode {

	//seconds before checking whether a compute node is offline
	//private int checkInterval = 5; 
	
	protected int scale;
	protected ComputeNodeBuilder cnBuilder = null;
	protected List<ComputeNode> computeNodes;
	private ConcurrentHashMap<String, ComputeNode> _urlNodeMap = new ConcurrentHashMap<String, ComputeNode>(10);
	
	private ConcurrentHashMap<String, List<ComputeNode>> _serviceNodeListMap = new ConcurrentHashMap<String, List<ComputeNode>>(10);
	private Queue<ComputeNode> freeNodes;
	private Map<String, ComputeNode> busyNodes;
	
	public void setActive(String computeNodeURL) {
		if(_urlNodeMap.containsKey(computeNodeURL)) {
			_urlNodeMap.get(computeNodeURL).setActive();
		} else {
			ComputeNode cn = cnBuilder.build(computeNodeURL);
			computeNodes.add(cn);
			_urlNodeMap.putIfAbsent(computeNodeURL, cn);
			
			for (String service : cn.getServices()) {
				List<ComputeNode> nodes = _serviceNodeListMap.get(service);
				if(nodes==null) {
					nodes = new ArrayList<ComputeNode>(10);
					_serviceNodeListMap.put(service, nodes);
				}
				nodes.add(cn);
			}
		}
	}
	
	public ComputeNode getNextComputeNodeForService(String serviceName) {
		ComputeNode free = freeNodes.poll();
		busyNodes.put(free.url, free);
		return free;
	}
	public void setComputeNodeFree(String computeNodeURL) {
		ComputeNode wasBusy = _urlNodeMap.get(computeNodeURL);
		freeNodes.add(wasBusy);
		busyNodes.remove(computeNodeURL);
	}

    public void reset() {
        _urlNodeMap.clear();
        _serviceNodeListMap.clear();//Clear all lists within
    }
}
