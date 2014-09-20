package com.manu.grid.transport;

import com.manu.grid.head.ComputeNode;

/**
 * Created by ManuGarg on 9/9/14.
 */
public interface IRequestInvoker<T> {
    public T invoke(ComputeNode node, T data);
}
