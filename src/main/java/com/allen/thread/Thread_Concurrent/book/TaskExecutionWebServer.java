package com.allen.thread.Thread_Concurrent.book;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {

	private static final int NTHREADS = 100;
	
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
	public static void main(String[] args) throws IOException{
		ServerSocket socket = new ServerSocket(8080);
		while(true){
			final Socket connection = socket.accept();
			Runnable task = new Runnable(){
				public void run(){
					System.out.println(connection);
				}
			};
			exec.execute(task);
		}
	}
}
