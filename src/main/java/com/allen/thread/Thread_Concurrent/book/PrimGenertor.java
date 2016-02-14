package com.allen.thread.Thread_Concurrent.book;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimGenertor implements Runnable{

	private final List<BigInteger> primes = 
			new ArrayList<BigInteger>();
	
	private volatile boolean cancelled;
	public void run() {
		// TODO Auto-generated method stub
		BigInteger p = BigInteger.ONE;
		while(!cancelled){
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}
	
	public void cancel(){
		cancelled = true;
	}
	
	public synchronized List<BigInteger> get(){
		return new ArrayList<BigInteger>(primes);
	}
}
