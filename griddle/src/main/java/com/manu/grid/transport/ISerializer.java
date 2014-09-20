package com.manu.grid.transport;

/**
 * Created by ManuGarg on 9/9/14.
 */
public interface ISerializer<T> {
    public T serialize(Object o);
    public Object deserialize(T data);
}
