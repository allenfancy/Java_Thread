package com.allen.thread.Thread_Concurrent.book;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws IOException{
		Thread threads[] = new Thread[10];
		Thread.State status[] = new Thread.State[10];
		for(int i = 0 ;i < 10; i++){
			threads[i] = new Thread(new Calculator(i));
			if((i%2) == 0){
				threads[i].setPriority(Thread.MAX_PRIORITY);
			}else{
				threads[i].setPriority(Thread.MIN_PRIORITY);
			}
			threads[i].setName("Thread " + i);
		}
		FileWriter file = new FileWriter(new File("/Users/allen/temp/log.txt"));
		PrintWriter pw = new PrintWriter(file);
		for(int i = 0; i< 10;i++){
			pw.println("Main : Status of Thread " + i + ":" +threads[i].getState());
			status[i] = threads[i].getState();
		}
		for(int i = 0; i< 10; i++){
			threads[i].start();
		}
	}
}
