package com.allen.thread.Thread_Concurrent;
/**
 * 
 * @author allen
 * ConcurrentSkipListMap是线程安全的有序的哈希表，使用高并发的场景
 * ConcurrentSkipListMap和TreeMap，他们虽然都是有序的哈希表。但是第一：他们的线程安全机制不同，TreeMap是非线程安全的，而ConcurrentSkipListMap是线程安全的。第二，ConcurrentSkipMap是通过跳表实现的，而TreeMap是通过红黑树实现的
 * 关于跳表(Skip List)它是平衡树的一种代替的数据结果，但是和红黑树不相同的是，跳表对于树的平衡的实现是基于一种随机化的算法的，这样也就是说跳表的插入和删除的工作时比较简单的
 * 
 *
 */
public class ConcurrentSkipListMapTest {

}
