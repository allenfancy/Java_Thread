package com.allen.thread.Thread_Concurrent.book;

public class Calculator implements Runnable{

	private int number;
	public Calculator(int number){
		this.number = number;
	}
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 1;i <= 10; i++){
			System.out.printf("%s:%d*%d=%d\n",Thread.currentThread().getName(),number,i,i*number);
		}
	}

}
