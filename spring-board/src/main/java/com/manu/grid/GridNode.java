package com.manu.grid;

public class GridNode {

	public GridNode(String url) {
		if(url==null || "".equals(url.trim()))
			throw new IllegalArgumentException("URL cannot be null for a grid node");
		else
			this.url = url;
	}
	
	public final String url;
	@Override public boolean equals(Object other) {
		if (other==null || ! (other instanceof GridNode))
			return false;
		else 
			return url.equals(((GridNode)other).url);
	}
	@Override public int hashCode() {
		return this.url.hashCode();
	}
	
	
}
