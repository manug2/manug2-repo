package com.manu.grid.com.manu.grid.head;

import java.util.ArrayList;
import java.util.List;

public class ComputeNodeBuilder {

	public ComputeNode build(String computeNodeURL) {
		ComputeNode cn = new ComputeNode(computeNodeURL);
		return cn;
	}

}
