package xipe.utils.world;

import java.util.ArrayDeque;
import java.util.Queue;

public class Pool<T>
{
    private final Queue<T> items;
    private final Producer<T> producer;
    
    public Pool(final Producer<T> producer) {
        this.items = new ArrayDeque<T>();
        this.producer = producer;
    }
    
    public synchronized T get() {
        if (this.items.size() > 0) {
            return this.items.poll();
        }
        return this.producer.create();
    }
    
    public synchronized void free(final T obj) {
        this.items.offer(obj);
    }
    
    public interface Producer<T>
    {
        T create();
    }
}
