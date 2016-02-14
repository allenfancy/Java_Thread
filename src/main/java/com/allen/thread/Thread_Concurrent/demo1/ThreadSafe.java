package com.allen.thread.Thread_Concurrent.demo1;

import org.junit.Test;

public class ThreadSafe {

	private  int count = 0;
	
	@Test
	public void test(){
		for(int i = 0 ; i < 1000000;i++){
			new Thread(new Runnable(){
				public void run() {
					// TODO Auto-generated method stub
					service();
				}
				
			}).start();
		}
		System.out.println("count: "+count);
	}
	
	public void service(){
		//System.out.println("11");
		++count;
		//System.out.println("11");
	}
	
	
}
