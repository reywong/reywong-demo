package cn.com.yto.reywong.tool.java.concurrent;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private BlockingQueue blockingQueue;

    public Producer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;

    }

    public void run() {
        try {
            int i = 0;
            while (true) {
                blockingQueue.put(i++);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
