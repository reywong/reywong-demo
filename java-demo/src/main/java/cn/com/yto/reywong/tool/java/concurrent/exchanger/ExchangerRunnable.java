package cn.com.yto.reywong.tool.java.concurrent.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerRunnable implements Runnable {
    Exchanger exchanger = null;
    Object object = null;

    public ExchangerRunnable(Exchanger exchanger, Object object) {
        this.exchanger = exchanger;
        this.object = object;
    }

    public void run() {
        Object previous = object;
        try {
            object = this.exchanger.exchange(object);
            System.out.println(Thread.currentThread().getName() + " exchanger " + previous + " for " + this.object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
