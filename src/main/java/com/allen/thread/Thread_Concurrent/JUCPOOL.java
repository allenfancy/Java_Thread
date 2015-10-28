package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author allen
 *
 * 线程池：
 * 	线程池的架构图如下：
 * 						Executor(interface)
 * 							^
 * 						    |(interface)
 * Executors --------> ExecutorService  <---------
 * 							^					  |
 *  						|					  |
 *   						|					  |
 *    						|					  |(interface)
 *    				  AbstractExecutorService	  ScheduledExecutorService
 *    						^							^
 *    						|							|
 *       					|							|
 *       				ThreadPoolExecutor <----- ScheduledThreadPoolExecutor
 *       
 * 1.Executor:
 * 	 执行者接口，它是来执行任务的。准确的说，Executor提供了execute()接口来执行已提交的Runnable任务的对象。Executor存在的目的是提供将 任务提交 和 任务如何允许 分离的机制
 *   void execute(Runnable command)
 * 2.ExecutorService
 * 	 ExecutorService继承了Executor。它是执行者服务接口，它是为执行者接口Executor服务而存在的；准确的话，ExecutorService提供了"将任务提交给执行者的接口(submit方法)"，"让执行者执行任务(invokeAll,invokeAny方法)"等接口
 * 	 方法类：
 * 	 boolean awaitTermination(long timeout,TimeUnit unit):请求关闭、发生超时或者当前线程中断，无论哪一个首先发生，都将导致阻塞，直到所有任务完成执行。
 *   <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks):执行给定的任务，当所有任务完成时，返回保持任务状态和结果的Future列表
 *   <T> List<Future<T>> invokeAll<Collection<? extends Callable<T>> tasks,long timeout,TimeUnit unit):
 *   <T> T invokeAny(Collection<? extends Callable<T>> tasks):执行给定的任务，如果某个任务已成功完成（也未抛出异常），则返回其结果。
 *   <T> T invokeAny(Collection<? extends Callable<T>> tasks,long timeout,TimeUnit unit):执行给定的任务，如果某个任务已成功完成（也未抛出异常），则返回其结果。
 *   boolean isShutdown():如果此执行程序已关闭，则返回true
 *   boolean isTerminated():如果关闭后所有任务都已王朝，则返回true
 *   void shutdown():启动一次顺序关闭，执行以前提交的任务，但不接受新任务
 *   List<Runnable> shutdownNow(): 视图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表
 *   <T> Future<T> submit(Callable<T> task):提交一个返回值的任务用于执行，返回一个表示任务的未决结果的Future
 *   Future<?> submit(Runnable task):提交一个Runnable任务用于执行，并返回一个表示该任务的Future
 *   <T> Future<T> submit(Runnbale task,T result):提交一个Runnable任务用于执行，并返回一个表示该任务的Future.
 *    
 *    
 *    
 * 3.AbstractExecutorService
 * 	 AbstractExecutorService是一个抽象类，它实现了ExecutorService接口
 * 	 AbstractExecutorService存在的目的是为ExecutorService中得函数接口提供了默认实现。
 *   AbstractExecutorService函数列表：
 *   	由于它的函数列表和ExecutorService一样。
 *   
 * 4.ThreadPoolExecutor
 * 	 ThreadPoolExecutor:就是线程池。它继承于AbstractExecutorService抽象类
 *   ThreadPoolExecutor函数列表：
 *   用于给定的初始参数和默认的线程工厂及被拒绝的执行处理程序创建的 ThreadPoolExecutor
 *   ThreadPoolExecutor(int  corePoolSize,int maximumPoolSize,long keepAlivevTime,TimeUnit unit,BlockingQueue<Runnable> workQueue):
 *   用于给定的初始化参数和默认的线程工厂创新的ThreadPoolExecutor
 *   ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keeppAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler):
 *   用于给定的初始化参数和默认被拒绝的执行处理程序创建的ThreadPoolExecutor
 *   ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory):
 *   用于给定的初始化参数创建新的ThreadPoolExecutor
 *   ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler):
 *   基于完成执行给定Run那边了所调用的方法
 *   protected void afterExecute(Runnable r,Throwable t)
 *   如果在保持活动时间内没有任务到达，新任务到达时正在替换，则设置控制核心线程是超时还是终止的策略
 *   void allowCoreThreadTimeOut(boolean value)
 *   请求关闭，发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所任务完成执行
 *   boolean awaitTermination(long timeout,TimeUnit unit)
 *   在执行给定线程中的给定Runnable之前调用的方法
 *   void beforeExecute(Thread t,Runnable r)
 *   在将某个时间执行给定新任务
 *   void execute(Runnable command)
 *   当不在引用此执行程序时，调用shutdown
 *   protected void finalize()
 *   返回主动执行任务的近似线程数
 *   int getActiveCount()
 *   返回已完成执行的近似任务总数
 *   long getCompletedTaskCount()
 *   返回核心线程数
 *   int getCorePoolSize()
 *   返回线程保持活动的时间，该时间就是超过核心池大小的线程可以在终止前保持空闲的时间值
 *   long getKeepAliveTime(TimeUnit unit)
 *   返回曾经同时位于池中的最大线程数。
 *   int getLargestPoolSize()
 *   允许的最大线程数
 *   int getMaximumPoolSize()
 *   
 *   int getPoolSize()
 *   返回此执行程序使用的任务队列
 *   BlockingQueue<Runnable> getQueue()
 *   
 *   RejectedExecutionHandler getRejectedExecutionHandler()
 *   
 *   long getTaskCount()
 *   
 *   ThreadFactory getThreadFactory()
 *   
 *   boolean isShutDown()
 *   
 *   boolean isTerminated()
 *   
 *   boolean isTerminating()
 *   启动所有核心线程，使其处于等待工作的空闲状态
 *   int prestartAllCoreThreads()
 *   启动核心线程，使其处于等待工作的空闲状态
 *   boolean prestartCoreThread()
 *   尝试从工作队列移除所有已取消的Future任务
 *   void purge()
 *   从执行程序的内部队列中移除此任务，从而如果尙为开始，则其不再运行
 *   boolean remove()
 *   设置核心线程数
 *   void setCorePoolSize();
 *   设置线程在终止前可以保持空闲的时间限制
 *   void setKeepAliveTime(long time,TimeUnit unit)
 *   设置允许的最大线程数
 *   void setMaximumPoolSize(int maximumPoolSize)
 *   设置用于未执行任务的新处理保持。
 *   void setRejectedExecutionHandler(RejectedExecutionHandler handler)
 *   设置用于创建新线程的线程工厂
 *   void setThreadFactory(ThreadFactory threadFactory)
 *  // 尝试停止所有的活动执行任务、暂停等待任务的处理，并返回等待执行的任务列表。
 *  void shutdown()
 	List<Runnable> shutdownNow()
 	// 当 Executor 已经终止时调用的方法。
 	protected void terminated()
 *   
 * 5.ScheduleExecutorService
 * 	 ScheduleExecutorService是一个接口，它继承于ExecutorService。它相当于提供了"延时"和"周期执行"功能的ExecutorService.
 *   ScheduleExecutorService提供了相应的函数接口，可以安排任务在给定的延迟后执行，也可以让任务周期执行
 *   ScheduledExecutorService函数列表：
 *   <V> ScheduledFuture<V> schelule(Callable<V> callable,long delay,TimeUnit unit)
 *   ScheduleFuture<?> schedule(Runnable command,long delay,TimeUnit unit)
 *   
 *   ScheduleFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)
 *   
 *   ScheduleFuture<?> scheduleWithFixedDelay(Runnable command,long initialDelay,long delay,TimeUnit unit)
 * 
 * 6.ScheduledThreadPoolExecutor
 * 	 ScheduledThreadPoolExecutor继承于ThreadPoolExecutor,并且实现ScheduledExecutorService接口，相当于提供了延迟 和 周期执行 功能 ScheduledExecutorService
 *   ScheduledThreadPoolExecutor继承于Timer，但是在高并发程序中，ScheduleThreadPoolExcutor的性能要优于Timer
 *   
 *   函数列表：
 *   ScheduledThreadPoolExecutor(int corePoolSize):使用给定核心池大小创建一个新的ScheduledThreadPoolExcutor
 *   ScheduledThreadPoolExecutor(int corePoolSize,RejectedExecutionHandler handler)
 *   ScheduledThreadPoolExecutor(int corePoolSize,ThreadFactory threadFactory)
 *   ScheduledThreadPoolExecutor(int corePoolSize,ThreadFactory threadFactory,RejectedExecutionHandler handler)
 *   
 * 7.Executors
 * 	 Executors是个静态工程类，它通过静态工程创建ExecutorService ScheduledExecutorService ThreadFactory Callable等类的对象
 *   
 * 
 * 二、线程池原理(一)
 *  
 *   ThreadPoolExecutor原理：
 *   	ThreadPoolExecutor是线程池类。对于线程池，可以通俗的理解为"存放一定数量线程的一个线程集合。线程池允许若个线程同时允许，允许同时运行的线程数量就是线程池的容量
 *   	当添加的到线程池中的线程超过它的容量时，会有一部分的线程阻塞等待。线程池会通过相应的调用策略和拒绝策略，对添加到线程池中得线程进行管理。"
 *      
 *      ThreadPoolExecutor数据结构：
 *      		ThreadPoolExecutor
 *      workQueue:BlockingQueue
 *      wokers:HashSet<Worker>
 *      mainLock:ReentrantLock
 *      termination:Condition
 *      largestPoolSize:int
 *      completedTaskCount:int
 *      volatile threadFactory:ThreadFactory
 *      volatile handler:RejectedExecutionHandler
 *      volatile keepAliveTime:int
 *      volatile allowCoreThreadTimeOut:boolean
 *      volatile corePoolSize:int
 *      volatile maximumPoolSize:int
 *      
 *      接口				
 *      BlockingQueue  Worker :thread : Thread  firstTask:Runnable  ======>  Runnable
 *      
 *      private final BlockingQueue<Runnable> workQueue:阻塞队列
 *      private final ReentrantLock mainLock = new ReentrantLock():互斥锁
 *      private final HashSet<Worker> workers = new HashSet<Worker>():线程集合。一个Workers对应一个线程
 *      private final Condition termination = mainLock.newCondition():终止条件 与 mainLock绑定
 *      private int largestPoolSize:线程池中线程数量曾今到达过的最大值
 *      private long completedTaskCount:已完成任务数量
 *      private volatile ThreadFactory threadFactory:ThreadFactory对象，用于创建线程
 *      private volatile RejectedExecutionHandler handler:拒绝策略的处理句柄
 *      private volatile long keepAliveTime:保持线程存活时间
 *      private volatile boolean allowCoreThreadTimeOut
 *      private volatile int corePoolSize:核心池大小
 *      private volatile int maximumPoolSize:最大池大小
 *    1.workers
 *    	workers是HashSet<Work>类型，即它是一个Worker集合。
 *    	而一个Worker对应一个线程，也就是说线程池通过workers包含了"一个线程集合"。当Worker对应的线程池启动时，它会执行线程池中得任务；当执行完成一个任务后，它会从线程池的阻塞队列中取出了一个阻塞的任务来继续运行
 *    	workers的作用是：线程池通过它实现了"允许多个线程同时运行"
 *    2.workQueue
 *    	workQueue是BlockingQueue类型，即它是一个阻塞队列。当线程池中得线程数量超过它的容量的时候，线程会进入阻塞队列进行阻塞等待
 *      通过workQueue,线程池实现了阻塞功能
 *    3.mainLock
 *   	mainLock是互斥锁，通过mainLock实现了对线程池的互斥访问
 *    4.corePoolSize和maximumPoolSize
 *    	corePoolSize是核心池大小，maximumPoolSize是最大池大小。他们的作用是调整"线程池中实际运行的线程的数量"
 *    5.poolSize
 *    	poolSize是当前线程池的实际大小，即线程池中任务的数量
 *    6.allowCoreThreadTimeOut和keepAliveTime
 *    	allowCoreThreadTimeOut表示是否允许"线程在空闲状态时，仍然能够存活"；而keepAliveTime是当线程池处于空闲状态的时候，超过keepAliveTime时间之后，空闲的线程会被终止
 *    7.threadFactory
 *    	threadFactory是ThreadFactory对象。它是一个线程工厂类。“线程池通过ThreadFactory创建线程”
 *    8.handler
 *    	handler是RejectedExecutionHandler类型。它是"线程池拒绝策略"的句柄。也就是说“当某任务添加到线程池中，而线程池拒绝该任务时，线程池会通过handler进行相应的处理”
 *    线程池通过workers来管理 线程集合。每个线程在启动后，会执行线程池中得任务；当一个任务执行完后，它会从线程池的阻塞队列中取出任务来继续运行。阻塞队列是来管理线程池任务的队列，当添加到线程池中得任务超过线程池容量时，该任务就会阻塞队列进行等待。
 *    
 *    
 *    线程五种状态：
 *    	新建状态 
 *      就绪状态
 *      运行状态	
 *      阻塞状态  
 *      死亡状态
 *   线程池的五种状态
 *   	Running
 *   	ShutDown
 *   	STOP
 *      TIDYING
 *      TERMINATED
 *   线程池状态定义代码如下：
 *   	private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING,0))
 *   	private static final int COUNT_BITS = Integer.SIZE - 3;
 *      private static final int CAPACITY = (1 << COUNT_BITS)
 *      private static final int RUNNING = -1 << COUNT_BITS;
 *      private static final int SHUTDOWN = 0 << COUNT_BITS;
 *      private static final int STOP = 1 << COUNT_BITS;
 *      private static final int TIDYING = 2 << COUNT_BITS;
 *      private static final int TERMINATED = 3 << COUNT_BITS;
 *      private static int ctlOf(int rs,int wc){ return rs | wc}
 *   
 *   说明：
 *   	ctl是一个AtomicInteger类型的原子对象。ctl记录了"线程池中得任务数量" 和 线程池状态 2 个信息
 *   	ctl共包括32位，其中，高3位表示线程池状态，低29位表示 线程池中的任务数量
 *   	RUNNING   对应的高3位值是111。
 *   	SHUTDOWN  对应的高3位值是000.
 *   	STOP	  对应的高3位值是001.
 *      TIDYING 	  对应的高3位值是010.
 *      TERMINATED	对应的高三位是011
 *      
 *   线程池各个状态之间的切换如下：
 *   						SHUTDOWN
 *   			/						\	队列为空，并且线程池中执行的任务也为空
 *   		/	shutdown()					\
 *   	/									 |			terminated()执行完毕
 *   Running  								TIDYING  ----------------------- >   TERMINATED
 *    	\									/
 *   	 \shutdownNow()				     /线程池中执行的任务为空	
 *   	   \					   		 /
 *   				\				/
 *  					 STOP 
 *   
 *   
 * 1.RUNNING
 * 		状态说明：线程池处在RUNNING状态时，能够接受新任务，以及对已添加的任务进行处理
 * 		状态切换：线程池的初始化状态是RUNNING。换句话说，线程池被一旦被创建，就处于RUNNING状态
 * 		private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING,0));
 * 2.SHUTDOWN
 * 		状态说明：线程池处在SHUTDOWN状态时，不接收新任务，但能处理已添加任务
 * 		状态切换：调用线程池的shutdown()接口时，线程池由RUNNING --> SHUTDOWN
 * 
 * 3.STOP
 * 		状态说明：线程池处在STOP状态时，不接收新任务，不处理已添加的任务，并且会中断正在处理的任务
 * 		状态切换：调用线程池的shutdownNow()接口时，线程池由(RUNNING  or SHUTDOWN) ---> STOP
 * 4.TIDYING
 * 		状态说明：当所有的任务已终止，ctl记录的任务数量为0，线程池会变为TIDYING状态。当线程池变为TIDYING状态时，会执行函数terminated()
 * 		状态切换：当线程池在SHUTDOWN状态下，阻塞队列为空并且线程池中执行的任务也为空时，就回由 SHUTDOWN ---> TIDYING
 * 				当线程池在STOP状态下，线程池中执行的任务为空时，就回由STOP --> TIDYING
 * 5.TERMINATED
 * 		状态说明：线程池彻底终止，就变成TERMINATED 状态
 * 		切换状态：线程池处于在TIDYING状态时，执行完terminated()之后，就会由TIDYING -- > TERMINATED
 * 
 * 
 * 拒绝策略介绍：
 * 	线程池的拒绝策略，是指当任务添加到线程池中被拒绝，采取的处理措施。
 *  当任务添加到线程池中之所以被拒绝，可能是由于：第一：线程池异常关闭，第二：任务数量超过线程池的最大限制
 *  线程池共包括4中拒绝策略：他们分别是：AbortPolicy,CallerRunsPolicy,DiscardOldestPolicy和DiscardPolicy.
 *  
 *  AbortPolicy: --当任务添加到线程池中被拒绝时，它将抛出 RejectedExecutionException异常
 *  CallerRunsPolicy: -- 当任务添加到线程池中被拒绝时，会在线程池当前正在运行的Thread线程池中处理被拒绝的任务
 *  DiscardOldestPolicy: -- 当任务添加到线程池中被拒绝时，线程池会放弃等待队列中最旧的未处理任务，然后将被拒绝任务添加到等待队列中
 *  DiscardPplicy: --  当任务添加到线程池中被拒绝时，线程池将丢弃被拒绝的任务
 *  
 *  线程池默认的处理策略是 AbortPolicy！
 *  
 * 6.Callable 和 Future 简介
 * 		Callable 和 Future 是比较有趣的一对组合。当我们需要获取线程的执行结果时，就需要用到他们。Callable用于产生结果，Future用于获取结果。
 *   1.Callable
 *   	Callable是一个接口，它只包含一个call()方法。Callable是一个返回结果并且可能抛出异常的任务
 *   	为了便于理解，我们可以将Callable比较一个Runnable接口，而Callable的call()方法类似于Runnable()方法
 *   	public interface Callable<V>{
 *   		V call() throws Exception;
 *   	}
 *     Callable支持泛型
 *   
 *   2.Future
 *   	Future是一个接口。它用于表示异步计算的结果。提供了检验计算是否完成的方法，以等待计算的完成，并获取计算的结果
 *      Future的源码如下：
 *      	public interface Future<V>{
 *      		boolean cancel(boolean myInterruptRunning)
 *      
 *      		boolean isCanceled()
 *      
 *      		boolean isDone()
 *      		
 *      		V get() throws InterrupedException,ExceutionException;
 *      
 *      		V get(long timeout,TimeUnit unit) throws InterruptedException,ExecutionException,TimeoutException;
 *      	}
 *   说明：Future用于表示异步计算的结果。他们实现类是FutureTask
 *   
 *   01.RunnableFuture是一个接口，它继承了Runnable和Future这俩个接口。RunnableFuture的源码如下：
 *   	public interface RunnableFuture<V> extends Runnable,Future<V>{
 *   		void run();
 *   	}
 *   02.FutureTask实现了RunnableFuture接口。所以，我们也说它实现了Future接口
 *   
 *   	
 *  
 *    
 *    
 */
public class JUCPOOL { 

	public static void main(String[] args){
		ExecutorService pool = Executors.newFixedThreadPool(2);
		//ThreadExecutorService pool1 = Executors.newSingleThreadExecutor()
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		
		pool.shutdown();
	}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
}
class MyThread extends Thread{
	public void run(){
		System.out.println(Thread.currentThread().getName() + "  is Running.");
	}
}
