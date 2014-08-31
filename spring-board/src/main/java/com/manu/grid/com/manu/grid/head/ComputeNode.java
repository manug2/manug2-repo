package com.manu.grid.com.manu.grid.head;

import java.util.List;

import com.manu.grid.GridNode;

public class ComputeNode extends GridNode {

	public ComputeNode(String url, List<String> services) {
		super(url);
        this.services = services;
	}
	
	private final List<String> services;
	
	protected boolean active;
	protected boolean busy;
	public void setActive() {
		this.active = true;
	}
	public void setInactive() {
		this.active = false;
	}
	
	public void setBusy() {
		this.busy = true;
	}
	public void setFree() {
		this.busy = false;
	}
	
	public List<String> getServices() {
		return this.services;
	}
	
	
}
