package com.manu.grid.com.manu.grid.head;

import java.util.ArrayList;
import java.util.List;

public class ComputeNodeBuilder {

	public ComputeNode build(String computeNodeURL) {
		ComputeNode cn = new ComputeNode(computeNodeURL, discoverServices(computeNodeURL));
		return cn;
	}
	
	public List<String> discoverServices(String computeNodeURL) {
		List<String> services = new ArrayList<String>(2);
		services.add("test-service");
		return services;
	}
}
