package com.allen.thread.Thread_Concurrent.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsynchronousServerTest {

	public static void main(String []args) throws IOException, InterruptedException, ExecutionException{
		AsynchronousChannelGroup group = 
				AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(10), 2);
		AsynchronousServerSocketChannel serverChannel = 
				AsynchronousServerSocketChannel.open(group);
		serverChannel.bind(new InetSocketAddress("localhost",8888),128);
		Future<AsynchronousSocketChannel> future = serverChannel.accept();
		proccess(future.get());
	} 
	private static void proccess(AsynchronousSocketChannel asynchronousSocketChannel) throws IOException{
		System.out.println(asynchronousSocketChannel.getLocalAddress());
	}
}
