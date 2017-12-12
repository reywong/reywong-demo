package cn.com.yto.reywong.tool.java.concurrent;

import java.util.concurrent.CountDownLatch;

public class Decrementer implements Runnable {

    CountDownLatch latch=null;

    public Decrementer(CountDownLatch latch) {
         this.latch=latch;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("11111111111");
            this.latch.countDown();

            Thread.sleep(1000);
            System.out.println("22222222222222222");
            this.latch.countDown();

//            Thread.sleep(1000);
//            System.out.println("333333333333333");
//            this.latch.countDown();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
