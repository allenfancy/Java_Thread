package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 
 * @author allen
 * 在主线程main中，通过newSingleThreadExecutor()新建一个线程池。接着创建Callable对象c1,然后在通过pool.submit(c1)将c1提交到线程池中进行处理
 * 并且将返回的结果保存到Future对象f1中。然后，我们通过f1.get()获取uCallable中保存结果；最后通过pool.shutdown()关闭线程池
 */
public class CallableTest1 {

	public static void main(String [] args) throws Exception, ExecutionException{
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Callable c1 = new MyCall();
		Future f1 = pool.submit(c1);
		System.out.println(f1.get());
		pool.shutdown();
	}
}
class MyCall implements Callable{

	public Object call() throws Exception {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0 ;i < 100; i++)
			sum+=i;
		return sum;
	}
	
}