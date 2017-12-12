package cn.com.yto.reywong.tool.java.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MyRecursiveAction extends RecursiveAction {
    private long workLoad = 0;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if (this.workLoad > 16) {
            System.out.println("Splitting workLoad:" + this.workLoad);
            List<MyRecursiveAction> subTasks = new ArrayList<MyRecursiveAction>();

            subTasks.addAll(createSubTasks());

            for (RecursiveAction recursiveAction : subTasks) {
                recursiveAction.fork();

            }

        } else {
            System.out.println("Doing workLoad myself:" + this.workLoad);
        }
    }

    private List<MyRecursiveAction> createSubTasks() {
        List<MyRecursiveAction> subTasks = new ArrayList<MyRecursiveAction>();
        MyRecursiveAction myRecursiveAction1 = new MyRecursiveAction(this.workLoad / 2);
        MyRecursiveAction myRecursiveAction2 = new MyRecursiveAction(this.workLoad / 2);
        subTasks.add(myRecursiveAction1);
        subTasks.add(myRecursiveAction2);
        return subTasks;
    }

    public static void main(String[] args) {
        MyRecursiveAction myRecursiveAction=new MyRecursiveAction(48);
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        forkJoinPool.invoke(myRecursiveAction);
    }
}
