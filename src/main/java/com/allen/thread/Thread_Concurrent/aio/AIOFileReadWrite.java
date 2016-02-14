package com.allen.thread.Thread_Concurrent.aio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class AIOFileReadWrite {

	public static void main(String[] args) throws IOException{
		AsynchronousFileChannel readChannel = AsynchronousFileChannel.open(Paths.get("/Users/allen/temp/1.html"));
		FileChannel writerChannel = new FileOutputStream("/Users/allen/temp/1.txt").getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		FileReadCompletion completion = new FileReadCompletion(byteBuffer,readChannel);
		readChannel.read(byteBuffer, 0l,writerChannel,completion);
		System.in.read();//让程序暂停，否则直接退出
	}
}
