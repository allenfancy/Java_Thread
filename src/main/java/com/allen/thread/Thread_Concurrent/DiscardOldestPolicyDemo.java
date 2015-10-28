package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author allen
 * 将 线程池的拒绝策略 由 DiscardPolicy 修改为 DiscardOldestPolicy之后，当有任务添加到线程池被拒绝时，线程池会丢弃阻塞队列中末尾的任务，然后将被拒绝的任务添加到末尾。
 *
 */
public class DiscardOldestPolicyDemo {

	private static final int THREADS_SIZE = 1;
	private static final int CAPACITY = 1;
	
	
	public static void main(String[] args){
		ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE,THREADS_SIZE,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CAPACITY));
		
		pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
		
		for(int i = 0; i < 10;i ++){
			Runnable myrun = new MyRunnable("task - " + i);
			pool.execute(myrun);
		}
		pool.shutdown();
	}
}
