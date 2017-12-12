package cn.com.yto.reywong.tool.java.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class Test {
    public static void main(String[] args) {
        Runnable barrierAction1 = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 1 execute");
            }
        };

        Runnable barrierAction2 = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 2 execute");
            }
        };

        CyclicBarrier barrier1 = new CyclicBarrier(2, barrierAction1);
        CyclicBarrier barrier2 = new CyclicBarrier(2, barrierAction2);
        CyclicBarrierRunnable cyclicBarrierRunnable1 = new CyclicBarrierRunnable(barrier1, barrier2);
        CyclicBarrierRunnable cyclicBarrierRunnable2 = new CyclicBarrierRunnable(barrier1, barrier2);
        new Thread(cyclicBarrierRunnable1).start();
        new Thread(cyclicBarrierRunnable2).start();


    }
}
