package com.manu.grid.head;

public class ComputeNodeBuilder {

	public ComputeNode build(String computeNodeURL) {
		ComputeNode cn = new ComputeNode(computeNodeURL);
		return cn;
	}

}
