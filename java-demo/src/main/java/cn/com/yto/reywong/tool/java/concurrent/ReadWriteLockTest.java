package cn.com.yto.reywong.tool.java.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    AtomicInteger i = new AtomicInteger(10);

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        readWriteLock.readLock().lock();

        readWriteLock.readLock().unlock();

        readWriteLock.readLock().lock();

        readWriteLock.readLock().unlock();

    }
}
