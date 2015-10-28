package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/***
 * 
 * @author allen
 *
 *说明：线程池pool的最大池大小 和 核心池大小 都为 1 ，这意味着 线程池能同时运行的任务数量最大只能是 1
 *     线程池Pool的阻塞队列是ArrayBlockingQueue，ArrayBlockingQueue是一个有界的阻塞队列，ArrayBlockingQueue的容量是1。这意味着线程池的阻塞队列只能有一个线程池阻塞等待
 *     
 *  线程池共运行2个任务。第一个任务直接放到Worker中，通过线程去执行；第2个任务放到阻塞队列中等待。其他都被丢弃了。
 *
 */
public class DiscardPolicyDemo {

	private static final int THREADS_SIZE = 1;
	private static final int CAPACITY = 1;
	
	public static void main(String [] agrs){
		ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));
		
		pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		
		for(int i=0;i<10;i++){
			Runnable myrun = new MyRunnable("task-" + i);
			pool.execute(myrun);
		}
		
		pool.shutdown();
	}
}
class MyRunnable implements Runnable{

	private String name;
	
	public MyRunnable(String name){
		this.name = name;
	}
	public void run() {
		// TODO Auto-generated method stub
		try{
			System.out.println(this.name +  " is Running!");
			Thread.sleep(1000);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
}
