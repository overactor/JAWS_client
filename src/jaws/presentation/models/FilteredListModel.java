package jaws.presentation.models;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.AbstractListModel;

/**
 * A ListModel that allows filtering the elements and mapping them into another type
 * 
 * @author Rik
 *
 * @param <E> The type of the actual contained elements
 * @param <T> the type the elements are mapped onto
 */
public class FilteredListModel<E,T> extends AbstractListModel<T> implements Iterable<T> {

    private static final long serialVersionUID = -116312904291606401L;

    private Vector<E> delegate;
    private Vector<Entry<Integer,T>> filtered;

	private Predicate<E> predicate;
	private Function<E,T> mapper;

    /**
     * Constructor of FilteredListModel
     * 
     * @param mapper the mapper
     */
    public FilteredListModel(Function<E,T> mapper) {

        delegate = new Vector<>();
        filtered = new Vector<>();
        predicate = e -> true;
        this.mapper = mapper;
    }
    
    /**
     * Sets the Predicate used for filtering
     * 
     * @param predicate the predicate
     */
    public void setPredicate(Predicate<E> predicate) {
    	
    	this.predicate = predicate;
    	applyFilters();
    }
    
    /**
     * Sets the Mapper used to convert base type to return types
     * 
     * @param mapper the mapper
     */
    public void setMapper(Function<E,T> mapper) {
    	
    	this.mapper = mapper;
    	applyFilters();
    }

    @Override
    public T getElementAt(int index) {

        return filtered.elementAt(index).getValue();
    }

    @Override
    public int getSize() {

        return filtered.size();
    }

    /**
     * add an element to the list
     * 
     * @param element the element to add
     */
    public void add(E element) {

        delegate.add(element);
        if (predicate.test(element)) {
        	filtered.addElement(new SimpleEntry<>(delegate.size() - 1, mapper.apply(element)));
        	fireContentsChanged(this, filtered.size() - 1, filtered.size() - 1);
        }
    }

    /**
     * remove an element from the list
     * 
     * @param index the index of the element to remove
     */
    public void remove(int index) {

        delegate.remove(filtered.get(index).getKey().intValue());
        applyFilters();
    }
    
    /**
     * refreshes the filters and mappers
     */
    public void refresh() {
    	
    	applyFilters();
    }

    private void applyFilters() {
    		
    	AtomicInteger i = new AtomicInteger(0);
        filtered = new Vector<>(delegate.stream().map(e -> new SimpleEntry<>(i.getAndIncrement(), e))
        		                                 .filter(e -> predicate.test(e.getValue()))
                                                 .map(e -> new SimpleEntry<>(e.getKey(), mapper.apply(e.getValue())))
        		                                 .collect(Collectors.toList()));
        
        fireContentsChanged(this, 0, filtered.size() - 1);
    }

    @Override
    public Iterator<T> iterator() {
    	
        return filtered.stream().map(e -> e.getValue()).iterator();
    }
}