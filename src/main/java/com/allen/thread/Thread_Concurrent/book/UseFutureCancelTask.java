package com.allen.thread.Thread_Concurrent.book;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UseFutureCancelTask {

	private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(100);
	public static void timedRun(Runnable r,long timeout,TimeUnit unit) throws Exception{
		Future<?> task = cancelExec.submit(r);
		try{
			task.get(timeout, unit);
		}catch(TimeoutException e){
			//接下来任务将被取消
		}catch(ExecutionException e){
			//如果在任务中抛出异常，那么重新抛出该异常
			throw new Exception(e.getCause());
		}finally{
			//如果任务已经结束，那么执行取消操作不会带来任何的影响
			task.cancel(true);//如果任务正在运行，那么将会被中断
		}
	}
}
