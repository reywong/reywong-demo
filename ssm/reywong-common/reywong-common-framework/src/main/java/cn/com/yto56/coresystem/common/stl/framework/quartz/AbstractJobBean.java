package cn.com.yto56.coresystem.common.stl.framework.quartz;

import cn.com.yto56.coresystem.common.stl.framework.base.BeanHolder;
import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * 抽象JOB类,方便获取JOB信息及指定Service
 *
 * @author lihui
 *         2016-12-20
 */
public abstract class AbstractJobBean implements Job {

    private Class<?> clazz;
    private Class<?> serviceClazz;
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractJobBean.class);

    /**
     * 当前任务组总的线程数,组中各线程都可见
     */
    protected int groupThreadCount = 0;

    /**
     * 子类是否需要看到当前任务组的任务总数
     */
    protected boolean lookGroupThreadCount = false;

    public AbstractJobBean() {
    }

    public AbstractJobBean(Class<?> clazz, Class<?> serviceClazz) {
        this.clazz = clazz;
        this.serviceClazz = serviceClazz;
    }

    public AbstractJobBean(Class<?> clazz, Class<?> serviceClazz, boolean lookGroupThreadCount) {
        this.clazz = clazz;
        this.serviceClazz = serviceClazz;
        this.lookGroupThreadCount = lookGroupThreadCount;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = null;
        long begin = System.currentTimeMillis();
        try {
            if (clazz != null) {
                scheduleJob = (ScheduleJob) context.getJobDetail().getJobDataMap().get(clazz.getSimpleName() + ".group");
                if (lookGroupThreadCount) {
                    try {
                        Set<JobKey> jobKeys = context.getScheduler().getJobKeys(GroupMatcher.jobGroupEquals(scheduleJob.getJobGroup()));
                        if (jobKeys != null) {
                            groupThreadCount = jobKeys.size();
                        }
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                }
            }
            Object service = null;
            if (serviceClazz != null) {
                service = BeanHolder.getBean(StringUtils.firstToLowerCase(serviceClazz.getSimpleName()));
            }
            if (LOG.isDebugEnabled() && scheduleJob != null) {
                LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>JOB:" + clazz.getSimpleName() + ", current JobName:" + scheduleJob.getJobName() + " Begin");
            }
            /**
             * 调用JOB
             */
            begin = System.currentTimeMillis();
            run(context, scheduleJob, service);
            if (LOG.isDebugEnabled() && scheduleJob != null) {
                LOG.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<JOB:" + clazz.getSimpleName() + ", current JobName:" + scheduleJob.getJobName() + " End");
            }
        } finally {
            if (scheduleJob != null) {
                scheduleJob.setLastTimeConsuming(System.currentTimeMillis() - begin);
                scheduleJob.setLastExecuteTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                scheduleJob.setExecuteTotalCount(scheduleJob.getExecuteTotalCount() + 1);
            }
        }
    }

    /**
     * 调用执行
     *
     * @param context
     * @param scheduleJob
     * @param service
     */
    public abstract void run(JobExecutionContext context, ScheduleJob scheduleJob, Object service);
}