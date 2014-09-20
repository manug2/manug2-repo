package com.manu.grid.head;

import com.manu.grid.GridNode;

public class ComputeNode extends GridNode implements Comparable<ComputeNode> {

	public ComputeNode(String url) {
		super(url);
        this.lastComm = Integer.MAX_VALUE;
	}

    protected long lastComm ;
    public void setActive() {
        this.lastComm = System.currentTimeMillis();
    }
    public void setInactive() {

    }

    @Override public int compareTo(ComputeNode other) {
        return (int) (this.lastComm - other.lastComm);
    }
	
}
