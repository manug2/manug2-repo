package com.manu.grid.com.manu.grid.head;

import java.util.List;

import com.manu.grid.GridNode;
import com.manu.grid.transport.IRequestInvoker;
import com.manu.grid.transport.ISerializer;

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
