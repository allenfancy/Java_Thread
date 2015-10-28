package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author allen
 *
 * 将线程池的拒绝策略  由 DiscardPolicy 修改为 AbortPolicy之后，当有任务添加到线程池被拒绝时，会抛出RejectedExecutionException
 */
public class AbortPolicyDemo {

	private static final int THREADS_SIZE = 1;
	private static final int CAPACITY = 1;

	public static void main(String[] args) {
		// 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
		ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(CAPACITY));
		// 设置线程池的拒绝策略为"抛出异常"
		pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		for(int i = 0; i< 10;i++){
			Runnable r = new MyRunnable("task - "+ i);
			pool.execute(r);
		}
	}
}
