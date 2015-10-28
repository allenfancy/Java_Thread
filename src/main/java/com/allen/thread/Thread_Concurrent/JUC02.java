package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JUC02 {

	public static void main(String [] args){
		Depot mDepot = new Depot();
		Producer mPro = new Producer(mDepot);
		Customer mCus = new Customer(mDepot);
		
		mPro.produce(60);
		mPro.produce(120);
		mCus.consume(90);
		mCus.consume(150);
		mPro.produce(110);
		
	}
}
class Depot{
	private int size; //仓库的实际数量
	 
	private Lock lock;//独占锁
	
	public Depot(){
		this.size = 0;
		this.lock = new ReentrantLock();
	}
	public void produce(int val){
		lock.lock();
		try{
			size +=val;
			System.out.printf("%s produce(%d) ----> size=%d\n",Thread.currentThread().getName(),val,size);
		}finally{
			lock.unlock();
		}
	}
	
	public void consume(int val){
		lock.lock();
		try{
			size -= val;
			System.out.printf("%s consume(%d) <-----size=%d\n",Thread.currentThread().getName(),val,size);
		}finally{
			lock.unlock();
		}
	}
}
class Producer{
	private Depot depot;
	
	Producer(Depot depot){
		this.depot = depot;
	}
	
	public void produce(final int val){
		new Thread(){
			public void run(){
				depot.produce(val);
			}
		}.start();
	}
}

class Customer{
	private Depot depot;
	
	public Customer(Depot depot){
		this.depot = depot;
	}
	
	public void consume(final int val){
		new Thread(){
			public void run(){
				depot.consume(val);
			}
		}.start();
	}
}
	
	
	


