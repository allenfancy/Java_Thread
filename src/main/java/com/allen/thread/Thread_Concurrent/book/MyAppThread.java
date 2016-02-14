package com.allen.thread.Thread_Concurrent.book;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class MyAppThread extends Thread{

	public static final String DEFAULT_NAME = "MyAppThread";
	private static volatile boolean debugLifecycle = false;
	private static final AtomicInteger created = new AtomicInteger();
	private static final AtomicInteger alive = new AtomicInteger();
	private static final Logger log = Logger.getAnonymousLogger();
	
}
