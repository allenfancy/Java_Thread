package com.allen.thread.Thread_Concurrent.book;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

public class BoundExecutor {

	private final Executor exec;
	private final Semaphore semaphore;
	
	public BoundExecutor(Executor exec,int bound){
		this.exec = exec;
		this.semaphore = new Semaphore(bound);
	}
	
	public void submitTask(final Runnable command) throws InterruptedException{
		semaphore.acquire();
		try{
			exec.execute(new Runnable(){

				public void run() {
					// TODO Auto-generated method stub
					try{
						command.run();
					}finally{
						semaphore.release();
					}
				}
				
			});
		}catch(Exception e){
			semaphore.release();
		}
	}
}
