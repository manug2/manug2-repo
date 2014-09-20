package com.manu.grid.head;

import com.manu.grid.executor.ComputeExecutor;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class HeadNode {

	//seconds before checking whether a compute node is offline
	//private int checkInterval = 5; 


    protected  int inactivityTimeOut;
	protected ComputeNodeBuilder cnBuilder = null;
    protected ComputeExecutor executor = null;
	protected List<ComputeNode> computeNodes;
	private ConcurrentHashMap<String, ComputeNode> urlNodeMap;
	
	private Queue<ComputeNode> freeNodes;
    private Map<String, ComputeNode> busyNodes;
    private Map<String, Object> nodePayload;
    private PriorityQueue<ComputeNode> leastActiveNodes;
    protected List<ComputeNode> inactiveNodes;

	public void setActive(String computeNodeURL) {
        ComputeNode cn;
        if(urlNodeMap.containsKey(computeNodeURL)) {
            cn = urlNodeMap.get(computeNodeURL);
            cn.setActive();
            leastActiveNodes.remove(cn);
        } else {
			cn = cnBuilder.build(computeNodeURL);
			computeNodes.add(cn);
			urlNodeMap.putIfAbsent(computeNodeURL, cn);
            freeNodes.add(cn);
		}
        leastActiveNodes.add(cn);
	}
	
	public ComputeNode getNextComputeNodeForService() {
		ComputeNode free = freeNodes.poll();
		busyNodes.put(free.url, free);
		return free;
	}
    public void setComputeNodeFree(String computeNodeURL) {
		ComputeNode wasBusy = urlNodeMap.get(computeNodeURL);
		freeNodes.add(wasBusy);
		busyNodes.remove(computeNodeURL);
	}

    public void reset() {
        urlNodeMap.clear();
    }

    public Object blackListInactiveNode() {
        Object payload = null;
        ComputeNode cn = leastActiveNodes.peek();
        final int time = (int) (System.currentTimeMillis() - cn.lastComm)/1000;
        if(time > inactivityTimeOut) {
            cn = leastActiveNodes.poll();
            freeNodes.remove(cn);
            if(busyNodes.containsKey(cn.url))
                busyNodes.remove(cn.url);
            if(nodePayload.containsKey(cn.url)) {
                payload = nodePayload.get(cn.url);
                nodePayload.remove(cn.url);
            }
        }
        return payload;
    }

}
