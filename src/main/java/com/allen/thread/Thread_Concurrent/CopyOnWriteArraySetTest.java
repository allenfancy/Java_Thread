package com.allen.thread.Thread_Concurrent;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 
 * @author allen
 * CopyOnWriteArraySet:
 * 	它是线程安全的无序的集合，可以将它理解成线程安全的HashSet。CopyOnWriteArraySet和HashSet虽然继承于共同的父类AbstractSet;但是，
 * 	HashSet是通过 散列表实现的。 而CopyOnWriteArraySet则是通过 动态数组(CopyOnWriteArrayList)实现的，并不是散列表
 *  CopyOnWriteArraySet的特点：
 *   1.它最适合于具有以下特征的应用程序：Set大小通常保持很小，只读操作远多于可变操作，需要在遍历期间防止线程间的冲突。
 *   2.它是线程安全的
 *   3.因为通常需要赋值整个基础数组，所以可变操作 add() set() 和 remove()等等 的开销很大
 *   4.迭代器支持hashNext()、next()等不可变操作，但不支持可变的remove()等操作
 *   5.使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照
 * 	 6.
 *
 */
public class CopyOnWriteArraySetTest {

	private static Set<String> set = new CopyOnWriteArraySet<String>();
	public static void main(String[] args){
		new MyThread("ta").start();
		new MyThread("tb").start();
	}
	private static void printAll(){
		String value = null;
		Iterator iter = set.iterator();
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
			int i =0;
			while(i++ < 10){
				String val = Thread.currentThread().getName() + "-" + (i);
				set.add(val);
				printAll();
			}
		}
	}
}
