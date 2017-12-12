package cn.com.yto.reywong.tool.java.concurrent;

import java.util.concurrent.CountDownLatch;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        BlockingQueue blockingQueue = new ArrayBlockingQueue(1024);
//        Producer producer = new Producer(blockingQueue);
//        Consumer consumer = new Consumer(blockingQueue);
//        new Thread(producer).start();
//        new Thread(consumer).start();
        CountDownLatch latch = new CountDownLatch(3);

        Waiter waiter = new Waiter(latch);
        Decrementer decrementer = new Decrementer(latch);

        new Thread(waiter).start();
        new Thread(decrementer).start();

        Thread.sleep(4000);
    }
}
