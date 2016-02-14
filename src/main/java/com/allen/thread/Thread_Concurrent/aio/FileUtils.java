package com.allen.thread.Thread_Concurrent.aio;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtils {

	public final static int COPY_FILE_SIZE = 1024 *1024;
	
	public static void copyFile(String srcFileName,String dstFileName) throws IOException{
		File srcFile = new File(srcFileName);
		File dstFile = new File(dstFileName);
		
		if(!srcFile.exists())
			throw new RuntimeException("src file not exists!");
		if(dstFile.exists())
			throw new RuntimeException("dst file exists!");
		DataInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try{
			inputStream = new DataInputStream(new FileInputStream(srcFile));
			outputStream = new FileOutputStream(dstFile);
			int fileAvailble = inputStream.available();//文件小于2G情况下一般没有问题
			if(fileAvailble <= COPY_FILE_SIZE){
				byte[] bytes = new byte[fileAvailble];
				inputStream.readFully(bytes);
				outputStream.write(bytes);
			}else{
				byte[] bytes = new byte[COPY_FILE_SIZE];
				int len = inputStream.read(bytes);
				while(len > 0 ){
					outputStream.write(bytes,0,len);
					len = inputStream.read(bytes);
				}
			}
		}finally{
			outputStream.close();
			inputStream.close();
		}
	}
	
	public static void copyFileByteBuffer(String srcFileName,String dstFileName,ByteBuffer byteBuffer,boolean cleanDirectBuffer) throws IOException{
		File srcFile = new File(srcFileName);
		File dstFile = new File(dstFileName);
		if(!srcFile.exists()) throw new RuntimeException("src file not exsit!");
		if(!dstFile.exists()) throw new RuntimeException("dst file not exist!");
		
		FileChannel inFileChannel = null;
		FileChannel outFileChannel = null;
		FileInputStream in = new FileInputStream(srcFile);
		FileOutputStream out = new FileOutputStream(dstFile);
		try{
			inFileChannel = in.getChannel();
			outFileChannel = out.getChannel();
			int size = inFileChannel.read(byteBuffer);
			while(size > 0 ){
				byteBuffer.flip();
				outFileChannel.write(byteBuffer);
				byteBuffer.clear();
				size = inFileChannel.read(byteBuffer);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			byteBuffer.clear();
			if(byteBuffer.isDirect() && cleanDirectBuffer){
				//((DirectBuffer)byteBuffer).cleaner().clean();
			}
		}
	}
	
	public static void copyFileByChannel(String srcFileName,String dstFileName){
		
	}
}
