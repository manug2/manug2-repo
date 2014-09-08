package com.manu.grid.executor;

import com.manu.grid.com.manu.grid.head.ComputeNode;
import com.manu.grid.com.manu.grid.head.HeadNode;
import com.manu.grid.transport.IRequestInvoker;
import com.manu.grid.transport.ISerializer;

import java.util.concurrent.*;

/**
 * Created by ManuGarg on 9/9/14.
 */
public class ComputeExecutor<T> {

    protected ISerializer<T> serializer;
    protected IRequestInvoker<T> invoker;
    protected HeadNode head;

    public ComputeExecutor(HeadNode head, ISerializer<T> serializer, IRequestInvoker<T> invoker) {
        this.head = head;
        this.serializer = serializer;
        this.invoker = invoker;
    }

    public Object execute (Object input) {
        final ComputeNode node = head.getNextComputeNodeForService();
        Future<Object> future = this.submit(new CallableWrapper(node, input));
        while (!future.isDone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Check if any node is now offline
            //Check if request is taking too long.
        }
        Object output = null;
        try {
            output = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        head.setComputeNodeFree(node.url);
        return output;
    }

    protected Object execute(ComputeNode node, Object input) {
        T data = serializer.serialize(input);
        T outData = invoker.invoke(node, data);
        Object output = serializer.deserialize(outData);
        return output;
    }

    protected Object retry(ComputeNode node, Object input) {
        T data = serializer.serialize(input);
        T outData = invoker.invoke(node, data);
        Object output = serializer.deserialize(outData);
        return output;
    }

    protected ExecutorService es = null;
    protected ExecutorCompletionService ecs=null;

    private int requestCount=0;
    private int returnCount =0;

    protected Future<Object> submit(final Object input) {
        final ComputeNode node = head.getNextComputeNodeForService();
        Future<Object> future = ecs.submit(new CallableWrapper(node, input));

        return future;
    }

    class CallableWrapper  implements Callable<Object> {
        Object input;
        ComputeNode node;
        CallableWrapper(ComputeNode node, Object input) {
            this.node = node;
            this.input = input;
        }

        @Override
        public Object call() throws Exception {
            Object output = execute(this.node, this.input);
            head.setComputeNodeFree(node.url);
            return output;
        }
    }

}
