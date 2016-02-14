package com.allen.thread.Thread_Concurrent.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;

public class FileReadCompletion implements CompletionHandler<Integer, FileChannel>{

	private ByteBuffer byteBuffer;
	private long newPosition;
	
	private AsynchronousFileChannel readFileChannel;
	public FileReadCompletion(ByteBuffer byteBuffer,AsynchronousFileChannel readFileChannel){
		this.byteBuffer = byteBuffer;
		this.readFileChannel = readFileChannel;
	}
	public void completed(Integer result, FileChannel writeChannel) {
		// TODO Auto-generated method stub
		if(result > 0 ){
			byteBuffer.flip();
			try{
				int writeLength = writeChannel.write(byteBuffer,newPosition);
				newPosition += writeLength;
			}catch(Exception e){
				
			}
			byteBuffer.clear();
			readFileChannel.read(byteBuffer, newPosition,writeChannel,this);
		}else{
			//closeStreams(readFileChannel,writeChannel);
		}
	}

	public void failed(Throwable exc, FileChannel attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
	}
	
	public long getNowPosition(){
		return newPosition;
	}
}
