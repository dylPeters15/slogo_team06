package backend.states;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javafx.collections.ObservableListBase;

/**
 * @author Tavo Loaiza
 *
 */
public class StatesList<E> extends ObservableListBase<E> implements Queue<E> {

	// TODO change ObservableList to Observable
	
    private final Queue<E> queue ;
    private E placeHolder;

    /**
     * Creates an ObservableQueue backed by the supplied Queue. 
     * Note that manipulations of the underlying queue will not result
     * in notification to listeners.
     * 
     * @param queue
     */
    public StatesList(Queue<E> queue) {
        this.queue = queue ;
    }

    /**
     * Creates an ObservableQueue backed by a LinkedList.
     */
    public StatesList() {
        this(new LinkedList<>());
    }

    @Override
    public boolean offer(E e) {
        boolean result = queue.offer(e);
        return result ;
    }

    @Override
    public boolean add(E e) {
        beginChange() ;
        try {
            queue.add(e);
            nextAdd(queue.size()-1, queue.size());
            return true ;
        } finally {
            endChange();
        }
    }

    
    private void ifEmptySetPlaceholder(E e){
    	if(queue.isEmpty()){
    		placeHolder = e;
    	}
    }

    @Override
    public E remove() {
            E e = queue.remove();
            ifEmptySetPlaceholder(e);
            return e;
    }

    @Override
    public E poll() {
        E e = queue.poll();
        if (e != null) {
        	ifEmptySetPlaceholder(e);
        }
        return e ;
    }

    @Override
    public E element() {
        return queue.element();
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public E get(int index) {
        Iterator<E> iterator = queue.iterator();
        for (int i = 0; i < index; i++) iterator.next();
        return iterator.next();
    }
    
    public E getLast(){
    	if(queue.isEmpty()){
    		return placeHolder;
    	}
    	return get(size()-1);
    }
    
    public E removeLast() {
    	if (!queue.isEmpty()) {
    		E last = this.get(this.size() - 1);
    		queue.remove(last);
    		return last;
    	}
    	else {
    		return placeHolder;
    	}
    }
    
    @Override
    public int size() {
        return queue.size();
    }

}