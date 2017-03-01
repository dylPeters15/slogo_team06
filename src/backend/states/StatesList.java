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
        beginChange();
        boolean result = queue.offer(e);
        if (result) {
            nextAdd(queue.size()-1, queue.size());
        }
        endChange();
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
        beginChange();
        try {
            E e = queue.remove();
            nextRemove(0, e);
            ifEmptySetPlaceholder(e);
            return e;
        } finally {
            endChange();
        }
    }

    @Override
    public E poll() {
        beginChange();
        E e = queue.poll();
        if (e != null) {
        	ifEmptySetPlaceholder(e);
            nextRemove(0, e);
        }
        endChange();
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
    @Override
    public int size() {
        return queue.size();
    }

}