package cn.com.yto.reywong.tool.springboot.test.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzDemo implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("执行我.......");

    }
}
