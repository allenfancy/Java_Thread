package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest3 {

	public static void main(String[] agrs){
		Depot3 depot3 = new Depot3(100);
		Producer3 p = new Producer3(depot3);
		Customer3 c = new Customer3(depot3);
		
		p.produce(60);
		p.produce(120);
		c.consume(90);
		c.consume(150);
		p.produce(110);
	}
}

class Depot3 {
	private int capacity; // 仓库容量
	private int size; // 仓库的实际数量

	private Lock lock; // 独占锁
	private Condition fullCondition; // 生成条件
	private Condition emptyCondition; // 消费条件

	public Depot3(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.lock = new ReentrantLock();
		this.fullCondition = lock.newCondition();
		this.emptyCondition = lock.newCondition();
	}

	public void produce(int val) {
		lock.lock();
		try {
			int left = val;
			while (left > 0) {
				// 如果库存已经满了，等待消费者 消费产品
				while (size >= capacity) {
					fullCondition.await();
				}
				// 获取 实际生产的数量
				// 如果"库存"+"想要生产的数量" > “总的数量”，则实际数量 = 总的容量 - 当前容量
				// 否则，实际增量 = 想要生成的数量
				int inc = (size + left) > capacity ? (capacity - size) : left;
				size += inc;
				left -= inc;
				System.out.printf("%s produce(%3d) --> left = %3d,inc=%3d,size=%3d\n", Thread.currentThread().getName(),
						val, left, inc, size);
				// 通知"消费者"可以消费了
				emptyCondition.signal();
			}
		} catch (InterruptedException e) {
		} finally {
			lock.unlock();
		}
	}

	public void consume(int val) {
		lock.lock();
		try {
			// left 表示“客户要消费数量”(有可能消费量太大，库存不够，需多此消费)
			int left = val;
			while (left > 0) {
				// 库存为0时，等待“生产者”生产产品。
				while (size <= 0)
					emptyCondition.await();
				// 获取“实际消费的数量”(即库存中实际减少的数量)
				// 如果“库存”<“客户要消费的数量”，则“实际消费量”=“库存”；
				// 否则，“实际消费量”=“客户要消费的数量”。
				int dec = (size < left) ? size : left;
				size -= dec;
				left -= dec;
				System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n", Thread.currentThread().getName(),
						val, left, dec, size);
				fullCondition.signal();
			}
		} catch (InterruptedException e) {
		} finally {
			lock.unlock();
		}
	}
}

class Producer3{
	private Depot3 depot3;
	
	public Producer3(Depot3 depot3){
		this.depot3 = depot3;
	}
	
	public void produce(final int val){
		new Thread(){
			public void run(){
				depot3.produce(val);
			}
		}.start();
	}
}

class Customer3{
	private Depot3 depot3;
	
	public Customer3(Depot3 depot3){
		this.depot3 = depot3;
	}
	
	//消费产品：新建一个线程从仓库中消费产品
	public void consume(final int val){
		new Thread(){
			public void run(){
				depot3.consume(val);
			}
		}.start();
	}
}






















