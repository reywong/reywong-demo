package cn.com.yto.reywong.tool.java.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTask extends RecursiveTask<Long> {
    private long wordLoad = 0;

    public MyRecursiveTask(long wordLoad) {
        this.wordLoad = wordLoad;
    }

    @Override
    protected Long compute() {
        if (wordLoad > 16) {
            System.out.println("Splitting workLoad:" + this.wordLoad);
            List<MyRecursiveTask> subTasks = new ArrayList<MyRecursiveTask>();
            subTasks.addAll(createSubTasks());

            for (MyRecursiveTask subtask : subTasks) {
                subtask.fork();
            }
            long result = 0;
            for (MyRecursiveTask subtask : subTasks) {
                result += subtask.join();
            }
            return result;
        } else {
            System.out.println("Doing workLoad myself:" + this.wordLoad);
            return wordLoad * 3;
        }
    }

    private List<MyRecursiveTask> createSubTasks() {
        List<MyRecursiveTask> result = new ArrayList<MyRecursiveTask>();
        MyRecursiveTask subTask1 = new MyRecursiveTask(this.wordLoad / 2);
        MyRecursiveTask subTask2 = new MyRecursiveTask(this.wordLoad / 2);
        result.add(subTask1);
        result.add(subTask2);
        return result;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyRecursiveTask recursiveTask = new MyRecursiveTask(24);
        System.out.println(forkJoinPool.invoke(recursiveTask));
    }
}
