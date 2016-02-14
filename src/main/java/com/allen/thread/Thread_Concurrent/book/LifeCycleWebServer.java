package com.allen.thread.Thread_Concurrent.book;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import org.junit.runner.Request;

public class LifeCycleWebServer {
	private static final ExecutorService exec = Executors.newFixedThreadPool(10000);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket socket = new ServerSocket(8080);
		while(exec.isShutdown()){
			try{
			final Socket conn = socket.accept();
			exec.submit(new Runnable(){

				public void run() {
					// TODO Auto-generated method stub
				//	handleRequest(conn);
				}});
		}catch(RejectedExecutionException e){
			if(!exec.isShutdown())
				System.out.println("task submission rejected"+e);
		}
		}
	}

	private void stop() {
		exec.shutdown();
	}
	/*
	 * public void handleRequest(Socket connection){ Request req =
	 * readRequest(connection); if(isShutdownRequest(req)) stop(); else
	 * dispatchRequest(req); }
	 * 
	 * private Request readRequest(Socket conn){ return new Request(); }
	 */
}
