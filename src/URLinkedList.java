/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 1
 * I took this from lab 5
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class URLinkedList<E> implements URList<E>, Iterable<E>{
	
	URNode<E> head;
	URNode<E> tail;
	
	//'length' variable just for convenience
	int length;
	
	public URLinkedList() {
		head = new URNode<E>(null, tail);
		tail = new URNode<E>(head, null);
		head.setNext(tail);
		
		length = 0;
	}
	
	@Override
	public boolean add(E e) {
		
		//if empty, then set element for 'head' first
		if (length == 0) {
			head.setElement(e);
		} else {
			URNode<E> newNode = new URNode<E>(e, tail.prev(), tail);
			newNode.prev().setNext(newNode);
			newNode.next().setPrev(newNode);
		}
		length++;
		
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index > length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		URNode<E> comp = head;
		for (int i = 0; i < index; i++) {
			comp = comp.next();
		}
		
		//if empty, then set element for 'head' first
		if (comp == head) {
			if (length == 0) {
				head.setElement(element);
			} else {
				URNode<E> newNode = new URNode<E>(element, null, head);
				head = newNode;
				newNode.next().setPrev(newNode);
			}
		} else {
			URNode<E> newNode = new URNode<E>(element, comp.prev(), comp);
			
			newNode.prev().setNext(newNode);
			newNode.next().setPrev(newNode);
		}
		
		length++;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		Iterator<? extends E> it = c.iterator();
		
		while (it.hasNext()) {
			//if empty, then set element for 'head' first
			if (tail.prev().element() != null) {
				URNode<E> newNode = new URNode<E>(it.next(), tail.prev(), tail);
				newNode.prev().setNext(newNode);
				newNode.next().setPrev(newNode);
			} else {
				tail.prev().setElement(it.next());
			}
		}
		
		length += c.size();
		
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		if (index > length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		URNode<E> comp = head;
		for (int i = 0; i < index; i++) {
			comp = comp.next();
		}
		
		Iterator<? extends E> it = c.iterator();
		
		while (it.hasNext()) {
			//if empty, then set element for 'head' first
			if (comp == head) {
				URNode<E> newNode = new URNode<E>(it.next(), null, head);
				head = newNode;
				newNode.next().setPrev(newNode);
				comp = head.next();
			} else {
				URNode<E> newNode = new URNode<E>(it.next(), comp.prev(), comp);
				newNode.prev().setNext(newNode);
				newNode.next().setPrev(newNode);
			}
		}
		
		length += c.size();
		
		return true;
	}

	@Override
	public void clear() {
		head = new URNode<E>(null, tail);
		tail = new URNode<E>(head, null);
		
		length = 0;
	}

	@Override
	public boolean contains(Object o) {
		URNode<E> comp = head;
		for (int i = 0; i < length; i++) {
			if (comp.element().equals(o)) {
				return true;
			} else {
				comp = comp.next();
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		//similar to URArrayList, I created a duplicate for the original linked list
		//for convenience, and more accurate comparison
		URLinkedList<E> comp = new URLinkedList<E>();
		comp.head = head;
		comp.tail = tail;
		comp.length = length;
		
		Iterator<?> it = c.iterator();
		
		int count = 0;
		while(it.hasNext()) {
			for (int i = 0; i < comp.length; i++) {
				if (comp.get(i).equals(it.next())) {
					comp.remove(i);
					count++;
					break;
				}
			}
		}
		
		return count == c.size();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		
		//not sure if this one works
		E[] obj = (E[]) o;
		
		if (obj.length != length) {
			return false;
		}
			
		E[] comp = (E[]) new Object[length];
		for (int i = 0; i < length; i++) {
			comp[i] = get(i);
		}
		
		for (int i = 0; i < length; i++) {
			if (!comp[i].equals(obj[i])) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public E get(int index) {
		if (index >= length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		URNode<E> comp = head;
		for (int i = 0; i < index; i++) {
			comp = comp.next();
		}
		return comp.element();
	}

	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < length; i++) {
			if (get(i).equals(o)) {
				return i;
			}
		}
		//if not found, return -1
		return -1;
	}

	@Override
	public boolean isEmpty() {
		
		return length == 0;
	}

	@Override
	public Iterator<E> iterator() {
		
		//not sure if there is a simpler way to this one
		Iterator<E> it = new Iterator<E>() {
			
			int index = 0;

			@Override
			public boolean hasNext() {
				
				return length > index;
			}

			@Override
			public E next() {
				
				return get(index++);
			}
			
			
		};
		return it;
	}

	@Override
	public E remove(int index) {
		if (index >= length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		URNode<E> comp = head;
		for (int i = 0; i < index; i++) {
			comp = comp.next();
		}
		
		//if remove head, then there is a bit difference
		if (head.next() == tail) {
			head.setElement(null);
		} else if (comp == head) {
			head = head.next();
			head.setPrev(null);
		} else {
			comp.prev().setNext(comp.next());
			comp.next().setPrev(comp.prev());
		}
		
		length--;
		return comp.element();
	}

	@Override
	public boolean remove(Object o) {
		
		for (int i = 0; i < length; i++) {
			if (get(i).equals(o)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		Iterator<?> it = c.iterator();
		
		int count = 0;
		while(it.hasNext()) {
			for (int i = 0; i < length; i++) {
				if (get(i).equals(it.next())) {
					count++;
					remove(i);
				}
			}
		}
		
		//return false if 'count == 0', which means the list does not change
		return count != 0;
	}

	@Override
	public E set(int index, E element) {
		if (index > length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		E remove = get(index);
		URNode<E> comp = head;
		for (int i = 0; i < index; i++) {
			if (i == index - 1) {
				comp.next().setElement(element);
			} else {
				comp = comp.next();
			}
		}
		
		return remove;
	}

	@Override
	public int size() {
		return length;
	}

	@Override
	public URList<E> subList(int fromIndex, int toIndex) {
		if (fromIndex > size() || fromIndex < 0 || toIndex > size() || toIndex < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException(); 
		}
		
		URLinkedList<E> sub = new URLinkedList<E>();
		
		for (int i = fromIndex; i <= toIndex; i++) {
			sub.add(get(i));
		}
		return sub;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray() {
		E[] toArray = (E[]) new Object[length];
		for (int i = 0; i < length; i++) {
			toArray[i] = get(i);
		}
		return toArray;
	}
	
	public void addFirst(E e) {
		add(0, e);
	}
	
	public void addLast(E e) {
		
		//if is empty, set for head first
		if (length != 0) {
			URNode<E> newNode = new URNode<E>(e, tail.prev(), tail);
			tail.setPrev(newNode);
			newNode.prev().setNext(newNode);
		} else {
			addFirst(e);
		}
	}
	
	public E peekFirst() {
		return head.element();
	}
	
	public E peekLast() {
		return tail.prev().element();
	}
	
	public E pollFirst() {
		E remove = head.element();
		remove(0);
		
		return remove;
	}
	
	public E pollLast() {
		E remove = tail.prev().element();
		remove(size() - 1);
		
		return remove;
	}
}
