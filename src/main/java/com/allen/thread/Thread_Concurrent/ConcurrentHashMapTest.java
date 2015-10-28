package com.allen.thread.Thread_Concurrent;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/***
 * 
 * @author allen
 * 简介：
 * 	ConcurrentHashMap是线程安全的哈希表。HashMap，Hashtable，ConcurrentHashMap之间的关联如下：
 * 	HashMap是非线程安全的哈希表，长用于单线程程序中
 *  Hashtable是线程安全的哈希表，它是通过synchronized来保证线程安全的；即，多线程通过同一个"对象的同步锁"来实现并发控制。
 *  Hashtabl在线程竞争激烈时，效率比较低(此时建议使用ConcurrentHashMap)!因为当一个线程访问Hashtable的同步方法时，其他线程就访问Hashtable的同步方法时，可能会进入阻塞状态
 *  
 *  ConcurrentHashMap是线程安全的哈希表，它是通过 锁分段 来保证线程安全的。ConcurrentHashMap将哈希表分成许多片段Segment,每一个片段除了保存了哈希表之外，本质上也是一个 可重入的互斥锁(ReentrantLock).多线程对同一个片段的访问，是互斥的；但是，对于不同片段的访问，却是可以同步进行的
 *
 */
public class ConcurrentHashMapTest {

	private static Map<String,String> map = new ConcurrentHashMap<String,String>();
	
	public static void main(String[] args){
		new MyThread("ta").start();
		new MyThread("tb").start();
	}
	public static void printAll(){
		String key,value;
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>) iter.next();
			key = entry.getKey();
			value = entry.getValue();
			System.out.print(key + " -- " +value + ".");
		}
		System.out.println();
	}
	private static class MyThread extends Thread{
		MyThread(String name){
			super(name);
		}
		public void run(){
			int i = 0;
			while(i++ < 6){
				String val = Thread.currentThread().getName()+" - "+i;
				map.put(String.valueOf(i), val);
				printAll();
			}
		}
	}
}
