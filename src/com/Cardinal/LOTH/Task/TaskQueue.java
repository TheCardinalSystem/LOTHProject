package com.Cardinal.LOTH.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class TaskQueue implements Queue<ITask> {

	private ArrayList<ITask> list = new ArrayList<ITask>();

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<ITask> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ITask> c) {
		return list.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean add(ITask e) {
		return list.add(e);
	}

	@Override
	public boolean offer(ITask e) {
		return list.add(e);
	}

	@Override
	public ITask remove() {
		return list.remove(0);
	}

	@Override
	public ITask poll() {
		try {
			return remove();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ITask element() {
		return list.get(0);
	}

	@Override
	public ITask peek() {
		try {
			return element();
		} catch (Exception e) {
			return null;
		}
	}

}
