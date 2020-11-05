package eu.revamp.spigot.netty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Lock-free stack with constant-time {@link #size()} operation.
 */
public class LockFreeStackWithSize<T> {

    private static class Node<T> {
        private volatile Node<T> next;
        private int size;
        private T payload;
    }

    private static final Node<?> tail = new Node<>();

    private final AtomicReference<Node<T>> root = new AtomicReference<>((Node<T>) tail);

    /**
     * Add element to the stack.
     * @return <code>true</code>
     */
    public boolean add(T value) {
        Node<T> newRoot = new Node<>();
        newRoot.payload = value;
        for (;;) {
            Node<T> oldRoot = this.root.get();
            newRoot.next = oldRoot;
            newRoot.size = oldRoot.size + 1;
            if (root.compareAndSet(oldRoot, newRoot))
                return true;
        }
    }

    /**
     * Constant-time size operation.
     */
    public int size() {
        return root.get().size;
    }

    /**
     * Dequeue all works faster than calling dequeue in loop.
     */
    public List<T> removeAllReversed() {
        List<T> result = new ArrayList<T>(size() + 100);

        Node<T> r;

        do {
            r = root.get();
        } while (!root.compareAndSet(r, (Node<T>) tail));

        while (r != tail) {
            result.add(r.payload);
            r = r.next;
        }

        return result;
    }
}
