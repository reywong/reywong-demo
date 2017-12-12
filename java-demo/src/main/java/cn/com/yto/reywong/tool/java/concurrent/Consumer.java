package cn.com.yto.reywong.tool.java.concurrent;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue blockingQueue;

    public Consumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;

    }

    public void run() {
        try {
            while (true)
            {
                System.out.println(blockingQueue.take());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
