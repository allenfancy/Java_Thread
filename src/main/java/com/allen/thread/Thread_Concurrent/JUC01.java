package com.allen.thread.Thread_Concurrent;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;

/***
 * 
 * @author allen
 *
 *Java多线程系列 - "JUC原子类" 框架
 *AtomicLong 原子类：
 *根据修改的数据类型，可以将JUC包中的原子操作类可以分为4类：
 *	1.基本类型：AtomicInteger,AtomicLong,AtomicBoolean
 *	2.数组类型：AtomicIntegerArray,AtomicLongArray,AtomicReferenceArray
 *	3.引用类型：AtomicReference,AtomicStampedReference,AtomicMarkableReference
 *	4.对象的属性修改类型：AtomicIntegerFieldUpdater,AtomicLongFieldUpdater,AtomicReferenceFieldUpdater
 *这些类的存在的目的是对相应的数据进行原子操作。所谓原子操作，是指操作过程中不会被中断，保证数据操作是以原子方式进行的。
 *AtomicLong介绍和函数列表：
 *	AtomicLong作用是对长整形进行原子操作。
 *	在32位操作系统中，64位的long和double变量由于会被JVM当做俩个分离的32位来进行操作，所有不具有原子性。而使用AtomicLong能让long的操作保持原子性
 *
 *AtomicLongArray:
 *	作用：对长整形数组进行原子操作
 *
 *JUC锁机制：
 *	同步锁：即通过synchronized关键字来进行同步，实现竞争资源的互斥访问的锁。
 *	同步锁的原理：对每一个对象，有且仅有一个锁；不同的线程能共享访问该同步锁。但是在同一个时间点，该同步锁能且只能被一个线程获取到。
 *	这样，获取到同步锁的线程就能进行CPU调度，从而在CPU上执行；而没有获取到同步锁的线程，必须进行等待，直到获取到同步锁之后才能继续运行。
 *
 *JUC包中的锁：
 *	相比同步锁，JUC包中的锁的功能更加强大，它为锁提供了一个框架，该框架允许更加灵活地使用锁。
 *	JUC包中的锁：包括Lock接口，ReadWriteLock接口，LockSupport阻塞原语，Condition条件，AbstractOwnableSysnchronizer/AbastractQueuedSynchronizer/AbstractQueuedLongSynchronizer三个抽象类
 *	ReentrantLock独占锁，ReentrantReadWriteLock读写锁、由于CountDownLatch,CyclicBarrier和Semaphore也是通过AQS来实现的；
 *  框架图：
 *  															Lock												ReadWriteLock
 *  															  | 													|
 *  Semaphore		CountDownLatch		CyclicBarrier		ReetrantLock											ReentrantReadWriteLock
 *
 *
 *																					AbstractOwnableSynchronizer			ReadLock		WriteLock
 *																								|
 *																					AbstractQueuedSynchronizer						
 *																						|				|
 *																					LockSupport		Condition
 *01.Lock接口：
 *	 JUC包中的Lock接口支持那些语义不同的锁规则。所谓的语义不同，是指锁可是有"公平机制的锁","非公平机制的锁"，"可重入的锁"等,"公平机制"是指"不同线程获取锁的机制是公平的"，而"非公平机制"则是指"不同线程获取锁的机制是非公平的"，"可重入的锁"是指同一个锁能被一个线程多次获取
 *02.ReadWriteLock
 *	ReadWriteLock接口以和Lock类似的方式定义了一些读取者可以共享而写入者独占的锁。JUC包只有一个类实现了该接口，即ReentrantReadWriteLock,因为它适用于大部分的标准用法上下文。
 *03.AbstractOwnableSynchronizer/AbstractQueuedSynchronizer/AbstractQueuedLongSynchrronizer
 *	 AbstractQueuedSynchronizer就是被称为AQS的类，他是一个非常有用的超类，可以来定义锁以及依赖于排队阻塞线程的其他同步器；
 *	 ReentrantLock,ReentrantReadWriteLock,CountDownLatch,CyclicBarrier和Semaphore等这些类都是基于AQS类实现的。
 *	 AbstractQueuedLongSynchronizer类提供相当的功能但扩展了对同步状态的64位的支持。两者都扩展了类AbstractOwnableSynchronizer（一个帮助记录当前保持独占同步的锁线程的简单类）	
 *04.LockSupport
 *	 LockSupport提供"创建锁"和"其他同步锁的基本线程阻塞原语"
 *	 LockSupport的功能和Thread中得THread.suspend()和Thread.resume()有点类似。LockSupport中饭的park()和unpark()的作用分别是阻塞线程和解除阻塞线程，但是park()和unpark()不会遇到"Thread.suspend和Thread.resume可能引发的死锁"问题
 *05.Condition
 *	 Condition需要和Lock联合使用，它的作用是代替Object监听器方法，可以通过await(),signal()来休眠/唤醒线程。Condition接口描述可能会与锁有关联的条件变量。这些变量在用法上与使用Object.wait访问的隐藏监听器类似，但提供了强大的功能。需要特别指出的，单个Lock可能与多个Condition对象关联。为了避免兼容性问题，Condition方法的名称与对应的Object版本中得不同。
 *
 *06.ReentrantLock
 *	 ReentrantLock是独占锁，所谓独占锁，是只能被独自占领，即同一个时间点只能被一个线程锁获取到的锁。ReentrantLock锁包括"公平的ReentrantLock"和"非公平的ReentrantLock"。"公平的ReentrantLock"是指"不同线程获取锁的机制是公平的"，而"非公平的 ReentrantLock"则是指"不同线程获取的机制是非公平的"，ReentrantLock是"可重入的锁"
 *							Lock
 *
 *							ReentrantLock
 *	
 *							Sync						AQS
 *					
 *							FairSync  NonfairSync
 *
 *1.ReentrantLock实现了Lock
 *2.ReentrantLock中有一个成员变量sync ,sync是Sync类型；Sync是一个抽象类，而且它继承于AQS
 *3.ReentrantLock中有"公平锁类" FairSync和 非公平锁类 NonfairSync，它们都是Sync的子类。ReentrantReadWriteLock中sync对象，是FaireSync与NonfaireSync中的一种，这也意味着ReentrantLock是"公平锁"或者"非公平锁"中的一种。ReentrantLock默认是非公平锁
 *
 *07.ReentrantReadWriteLock
 *	 ReentrantReadWriteLock是读写锁接口ReadWriteLock的实现类，它包括子类ReadLock和WriteLock。ReentrantLock是共享锁，而WriteLock是独占锁。
 *	 1.ReentrantReadWriteLock实现了ReadWriteLock接口
 *   2，ReentrantReadWriteLock中包含Sync对，读锁readerLock和写锁writeLock。读锁ReadLock和写锁WriteLock都实现了Lok从接口
 *   3.和"ReentrantLock"一样，Sync是Sync类型；而且，Sync也是一个继承于AQS的抽象类。Sync也包括"公平锁"FairSync和非公平锁NonfairSync.
 *   
 *08.CountDownLatch
 *	 CountDownLatch是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一致等待。
 *09.CyclicBarrier
 *	 CyclicBarrier是一个同步辅助类，允许一组线程互相等待，直到到达某个公共屏障点(common barrier point).因为该barrier在释放等待线程后可以重用，所以称它为循环的barrier。
 *   CyclicBarrier是包含了ReentrantLock对象lock和Condition对象trip，它是独占锁实现的。 
 *   CyclicBarrier和CountDownLatch的区别是：
 *   	1.CountDownLatch的作用是允许1或N个线程等待其他线程完成执行；而CyclicBarrier则是允许N个线程相互等待
 *   	2.CountDownLatch的计数器无法被重置；CyclicBarrier的计数器可以被重置后使用，因为它被称为是循环的barrier
 *   
 *10.Semaphore
 *	 Semaphore是一个计数信号量，它的本质是一个"共享锁"	
 *	 信号量维护了一个信号量许可集。线程可以通过调用acquire()来获取信号量的许可；当信号量中由可用的许可时，线程能获取该许可；否则线程必须等待，直到有可用的许可为止。线程可以通过release()来释放它所持有的信号量许可。
 *   Semaphore的UML类图如下：
 *   
 *   和"ReentrantLock"一样，Semaphore包含了sync对象，sync是Sync类型；而且，Sync也是一个继承于AQS的抽象类。Sync也包括"公平信号量"FairSync 和 非公平信号量 NonfairSync。
 *   
 *   
 * "JUC锁"：ReentrantLock：
 * 	 1.ReentrantLock是一个可重入的互斥锁，又被称为"独占锁"
 * 		ReentrantLock锁在同一个时间只能被一个线程锁持有，而可重入的意思是，ReentrantLock锁，可以被单个线程多次获取。
 *      ReentrantLock分为"公平锁"和"非公平锁"。他们区别体现在获取锁的机制上是否公平，锁是我I类保护竞争资源，防止多个线程同时操作线程而出错，
 *      ReentrantLock在同一个时间点只能被一个线程获取，ReentrantLock是通过一个FIFO的等待 队列来管理获取该锁所有线程的。
 *      在公平锁的机制下，线程依次排队获取；非公平锁在锁是可获取状态时，不管自己是不是在队列的开头都回获取锁
 *      
 *   2.ReentrantLock函数：
 *   	ReentrantLock():创建一个非公平锁
 *   	ReentrantLock(boolean fair):fair为true表示公平锁，为false表示非公平锁
 *   	int getCount():查询当前线程保持此锁的次数
 *   	protected Thread getOwner():返回目前拥有此锁的线程，如果此锁不被任何线程拥有，则返回null；
 *      protected Collection<Thread> getQueuedThreads():返回一个Collection，包含可能正等待获取此锁的线程
 *      int getQueueLength():返回正在等待获取此锁的线程估计数，
 *      protected Collection<Thread> getWaitingThreads(Condition condition):返回可能等待此锁相关给定条件的那些线程，
 *      int getWaitQueueLengtj(Condition condition):返回等待次锁相关的给定条件的线程估计数
 *      // 查询是否有些线程正在等待获取此锁。
		boolean hasQueuedThreads()
		// 查询是否有些线程正在等待与此锁有关的给定条件。
		boolean hasWaiters(Condition condition)
		// 如果是“公平锁”返回true，否则返回false。
		boolean isFair()
		// 查询当前线程是否保持此锁。
		boolean isHeldByCurrentThread()
		// 查询此锁是否由任意线程保持。
		boolean isLocked()
		// 获取锁。
		void lock()
		// 如果当前线程未被中断，则获取锁。
		void lockInterruptibly()
		// 返回用来与此 Lock 实例一起使用的 Condition 实例。
		Condition newCondition()
		// 仅在调用时锁未被另一个线程保持的情况下，才获取该锁。
		boolean tryLock()
		// 如果锁在给定等待时间内没有被另一个线程保持，且当前线程未被中断，则获取该锁。
		boolean tryLock(long timeout, TimeUnit unit)
		// 试图释放此锁。
		void unlock()
		
 *  
 *  
 *  获取公平锁：
 *   1.lock()
 *   	lock在ReentrantLock的FairSync类中的实现
 *   	final void lock(){
 *   		acquire(1);
 *   	}
 *   2.acquire():
 *   	public final void acquire(int arg){
 *   		if(!tryAcquire(arg) && acquireQueued(addWriter(Node.EXCLUSIVE),arg))
 *   			selfInterrupt();
 *   	}
 *   (01):当前线程首先通过tryAcquire()尝试获取锁。获取成功的话，直接返回；尝试失败的话，进入到等待队列排序等待
 *   (02):当前线程失败的情况下，先通过addWaiter(Node.EXCLUSIVE)来将当前线程加入到CLH队列。CLH队列就是线程等待队列。 
 *   (03):在执行完addWaiter(Node.EXCLUSIVE)之后，会调用acquireQueued()来获取锁。
 *   
 *   
 *  Node是CLH队列的节点，代表"等待锁的线程队列"：
 *  	1.每个Node都会一个线程对应
 *      2.每个Node会通过prev 和 next分别指向上一个节点 和 下一个节点，这分别代表上一个待等待线程和下一个等待线程
 *      3.Node通过waitStatus保存线程的等待状态
 *      4.Node通过nextWaiter来区分线程是"独占锁"线程还是共享锁线程。如果是独占锁线程，则nextWaiter的值为EXCLUSIVE；如果是共享锁线程，则nextWaiter的值是SHARED
 *											
 */
public class JUC01 {

	public static void main(String[] args){
		//testAtomicLongArray();
		testAtomicReferenceTest();
	}
	
	public static void testAtomicLong(){
		AtomicLong mAtoLong = new AtomicLong();
		mAtoLong.set(0x0123456789ABCDEFL);
		System.out.println(mAtoLong.get());
		System.out.println(mAtoLong.intValue());
		System.out.println(mAtoLong.longValue());
		System.out.println(mAtoLong.doubleValue());
		System.out.println(mAtoLong.floatValue());
	}
	
	public static void testAtomicLongArray(){
		long []arrlong = new long[]{10,20,30,40,50};
		AtomicLongArray ala = new AtomicLongArray(arrlong);
		ala.set(0, 100);
		for(int i = 0,len = ala.length();i<len;i++){
			System.out.println(i+ "   " +ala.get(i));
		}
		
		System.out.println(ala.getAndDecrement(0));
		System.out.println(ala.decrementAndGet(1));
		System.out.println(ala.getAndIncrement(2));
		System.out.println(ala.incrementAndGet(3));
		
		System.out.println(ala.addAndGet(0, 100));
		System.out.println(ala.getAndAdd(1, 100));
		System.out.println(ala.compareAndSet(2, 31, 1000));
		System.out.println(ala.get(2));
	}
	
	public static void testAtomicReferenceTest(){
		Person p1 = new Person(101);
		Person p2 = new Person(102);
		AtomicReference ar = new AtomicReference(p1);
		ar.compareAndSet(p1, p2);
		
		Person p3 = (Person) ar.get();
		System.out.println("p3 is " + p3);
		System.out.println("p3.equals(p1)="+p3.equals(p1));
	}
	
	
	
	
	
}

class Person{
	volatile long id;
	
	public Person(long id){
		this.id = id;
	}
	
	public String toString(){
		return "id:"+id;
	}
}









