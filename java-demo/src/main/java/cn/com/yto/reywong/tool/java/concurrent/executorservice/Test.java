package cn.com.yto.reywong.tool.java.concurrent.executorservice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Set<Callable<String>> set = new HashSet<Callable<String>>();
        set.add(new Callable() {
            public String call() throws Exception {
                return "task 1";
            }
        });
        set.add(new Callable() {
            public String call() throws Exception {
                return "task 2";
            }
        });

        set.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 3";
            }
        });

//        String result = executorService.invokeAny(set);
//
//        System.out.println("result = " + result);

        List<Future<String>> futureList = executorService.invokeAll(set);
        for(Future<String> future:futureList){
            System.out.println("future get ="+future.get());
        }
        executorService.shutdown();
    }
}
