package com.allen.thread.Thread_Concurrent.book;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
/**
 * 使用 Semaphore 为容器设置边界
 * @author allen
 *
 * @param <T>
 */
public class BoundedHashSet<T> {

	private final Set<T> set ;
	private final Semaphore sem ;
	
	public BoundedHashSet(int bound){
		this.set = Collections.synchronizedSet(new HashSet<T>());
		sem = new Semaphore(bound);
	}
	
	public boolean add(T o) throws InterruptedException {
		sem.acquire();
		boolean wasAdded = false;
		try{
			wasAdded = set.add(o);
			return wasAdded;
		}finally{
			if(!wasAdded)
				sem.release();
		}
	}
	public boolean remove(Object o) {
		boolean  wasRemoved = set.remove(o);
		if(wasRemoved){
			sem.release();
		}
		return wasRemoved;
	} 
}
