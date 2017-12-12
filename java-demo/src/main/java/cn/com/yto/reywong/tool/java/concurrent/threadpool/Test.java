package cn.com.yto.reywong.tool.java.concurrent.threadpool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 5000;

        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());

        Set<Callable<String>> set = new HashSet<Callable<String>>();
        set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " execute ="+i+" !");
                }
                return "A1";
            }
        });
        set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " execute ="+i+" !");
                }
                return "A2";
            }
        });
        set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " execute ="+i+" !");
                }
                return "A3";
            }
        });
        set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() +" execute ="+i+" !");
                }
                return "A4";
            }
        }); set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() +" execute ="+i+" !");
                }
                return "A5";
            }
        }); set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " execute ="+i+" !");
                }
                return "A6";
            }
        });
        set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " execute ="+i+" !");
                }
                return "A7";
            }
        });
        set.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " execute ="+i+" !");
                }
                return "A8";
            }
        });
        List<Future<String>> futureList= threadPoolExecutor.invokeAll(set);
        for(Future<String> future:futureList){
            System.out.println(future.get());

        }

        threadPoolExecutor.shutdown();

    }
}
