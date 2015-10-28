package com.allen.thread.Thread_Concurrent;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 
 * @author allen
 * 1.ArrayBlockingQueue介绍：
 * 	 ArrayBlockingQueue是数组实现的线程安全的有界的阻塞队列。
 * 线程安全是指，ArrayBlockingQueue内部通过互斥锁保存竞争资源，实现了多线程对竞争资源的互斥访问。而有界，则是指ArrayBlockingQueue对应的数组是有界的。
 * 阻塞队列，是指多线程访问竞争资源时，当竞争资源已被某个线程获取时，其他要获取该资源的线程需要阻塞等待；而且，ArrayBlockingQueue是按FIFO 先进先出原子对元素进行排序，元素都从尾部插入到队列，从头部开始返回
 * ArrayBlockingQueue不同于ConcurrentLinkedQueue，ArrayBlockingQueue是数组实现的，并且是有界限的；而ConcurrentLinkedQueue是链表实现的，是无界限的。
 * 2.ArrayBlockingQueue原理和数据结构
 * 	 AbstractQueue     		BlockingQueue
 * 		  
 * 
 * 	 ArrayBlockingQueue		
 *		
 *
 *
 *	ReentrantLock	 ---------  Condition
 *说明：
 *	1.ArrayBlockingQueue继承于AbstractQueue，并且实现了BlockingQueue接口
 *  2.ArrayBlockingQueue内部是通过Object[]数组保存数据的，也就是ArrayBlockingQueue本质上是通过数组实现的。ArrayBlockingQueue的大小，即数组的容量是创建ArrayBlockingQueue时指定的
 *  3.ArrayBlockingQueue与ReentrantLock是组合关系，ArrayBlockingQueue中包含一个ReentrantLock对象Lock。ReentrantLock是可重入的互斥锁，ArrayBlockingQueue就是根据该互斥锁实现"多线程对竞争资源的互斥访问"。
 *    而且ReentrantLock分为公平锁和非公平锁，关于具体使用公平锁还是非公平锁。在创建ArrayBlockingQueue时可以指定；而且，ArrayBlockingQueue默认会使用非公平锁。
 *  4.ArrayBlockingQueue与Condition是组合关系，ArrayBlockingQueu中包含俩个Condition对象(noEmpty和notFull).而且，Condition又依赖于ArrayBlockingQueue而存在，通过Condition可以实现对ArrayBlockingQueue的更精确的访问
 *  	
 *  	01.若某线程要取数据时，数组正好为空，则线程会执行notEmpty.await()进行等待；当某个线程向数组中插入数据之后，会调用notEmpty.signal()唤醒notEmpty上的等待线程。此时，线程A会被唤醒从而得以继续运行、
 *      02.若某个线程(线程H)要插入数据时，数组已满，则线程会它执行notFull.await()进行等待；当其他某个线程（线程I）取出数据之后，会调用notFull.signal()唤醒notFull上得等待线程。此时，线程H就会被唤醒从而得以继续运行。
 *       	关于ReentrantLock,公平锁，非公平锁，以及Condition等更多内容。
 *  
 *  ArrayBlockingQueue(int capacity)
 *  ArrayBlockingQueue(int capacity,boolean fair)
 *  ArrayBlockingQueue(int capacity,boolean fair,Collection<? extends E> c)
 *  boolean add(E e)
 *  void clear()
 *  boolean contains(Object o)
 *  int drainTo(Collection<? super E> c)
 *  int drainTo(Collection<? super E> c,int maxElements)
 *  Iterator<E> iterator()
 *  boolean offer(E e)
 *  boolean offer(E e,long timeout,TimeUnit unit)
 *  E peek()：获取但不移除此队列的头；如果此队列为空，则返回null
 *  E poll()：获取并移除此队列的头部，如果此队列为空，则返回null
 *  E poll(long timeout,TimeUnit unit)：获取并移除此队列的头部，在指定的等待时间前等待可用的元素(如果有必要)
 *  void put(E e):将指定的元素插入此队列的尾部，如果该队列已满，则等待可用的空间
 *  int remainingCapacity():返回在无阻塞的理想情况下(不存在内存或资源约束)，此队列能接受的其他元素数量
 *  boolean remove(Object o):此队列中移除指定元素的单个实例（如果存在）
 *  int size():返回此队列中元素的数量
 *  E take():获取并移除此队列的头部，在元素可变的可用之前一直等待
 *  Object[] toArray():返回一个按适当顺序包含此队列中所有元素的数组
 *  <T> T[] toArrat(T[] a):返回一个按适当顺序包含此队列中所有元素的数组；返回数组的运行时类型是指定数组的运行时类型。
 *  String toString():返回此collection的字符串表示形式
 *  
 *  
 *  1.创建：
 *  	public ArrayBlockingQueue(int capacity,boolean fair){
 *  		if(capacity <= 0)
 *  			throw new IllegalArgumentException();
 *  		this.items = new Object[capacity];
 *  		lock = new ReentrantLock(fair);
 *  		notEmpty = lock.newCondition();
 *  		notFull = lock.newCondition();
 *  	}
 *  	01.items是保存 阻塞队列 数据的数组。它的定义如下：
 *  	   final Object[] items;
 *  	02.fair是"可重入的独占锁(ReentrantLock)"的类型。fair为true，表示公平锁；fair为false，表示非公平锁
 *  	   notEmpty和notFull是锁的俩个Condition条件。它们的定义如下：
 *  	   final ReentrantLock lock;
 *  	   private final Condition notEmpty;
 *		   private final Condition notFull;
 *		   Lock的作用是提供独占锁机制，来保护竞争资源；而Condition是为了更加精细的对锁进行控制，它依赖于Lock,通过某个条件对多个线程进行控制。
 *		   notEmpty表示锁的非空条件。当某线程向从队列中取数据时，而此时又没有数据，则该线程通过notEmpty.await()进行等待；当其他线程向队列中插入元素之后，就调用notEmpty.signal()唤醒之前通过notEmpty.await()进入等待状态的线程。
 *		   同理，notFull表示 “锁的满条件”。当某线程想队列中插入元素，而此队列已满时，该线程等待；当其它线程从队列中取出元素之后，就唤醒该等待的线程。
 *  2.添加
 *     public boolean offer(E e){
 *     		checkNotNull(e):创建插入的元素是否为null、是的话抛出NullPointerException异常
 *     		final ReentrantLock lock = this.lock;
 *     		lock.lock();//开启锁
 *     		try{
 *     			if(count == items.length)
 *     					return false;
 *     			else{
 *     				insert(e);
 *     				return true;
 *     			}
 *     		}finally{
 *     			lock.unlock();//释放锁
 *     		}
 *     }
 *    说明：offer(E e)的作用是将e插入阻塞队列的尾部。如果队列已满，则返回false，表示插入失败；否则，插入元素，并返回true。
 *    		01.count表示 队列中的元素个数。除此以外，队列中还有另外
 */
public class ArrayBlockingQueueTest {

	private static Queue<String> queue = new ArrayBlockingQueue<String>(20);
	public static void main(String[] args){
		new MyThread("ta").start();
		new MyThread("tb").start();
	}
	private static void printAll(){
		String value;
		Iterator<String> iter = queue.iterator();
		while(iter.hasNext()){
			value = (String) iter.next();
			System.out.print(value + " ,");
		}
		System.out.println();
	}
	private static class MyThread extends Thread{
		MyThread(String name){
			super(name);
		}
		public void run(){
			int i = 0;
			while(i++ < 6){
				String val = Thread.currentThread().getName() + " - " + i;
				queue.add(val);
				printAll();
			}
		}
	}
}
