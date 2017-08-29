package cn.com.yto56.coresystem.common.stl.framework.quartz.impl;

import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import cn.com.yto56.coresystem.common.stl.framework.quartz.IScheduleService;
import cn.com.yto56.coresystem.common.stl.framework.quartz.JobBean;
import cn.com.yto56.coresystem.common.stl.framework.quartz.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.text.SimpleDateFormat;
import java.util.*;

public class ScheduleServiceImpl implements IScheduleService, ApplicationContextAware {

    private Scheduler scheduler;
    private Properties properties = new Properties();
    private Map<String, Class<?>> jobClassMap = new HashMap<String, Class<?>>();

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    protected final String DEFUALT_JOB_STATUS = "1";

    @SuppressWarnings("unchecked")
    public Map<String, Object> addJob(String className, String suffix) {
        Map<String, Object> result = new HashMap<String, Object>();
        JobDetail jobDetail = null;
        String newJobName = null;
        try {
            jobDetail = scheduler.getJobDetail(new JobKey(className + "_" + suffix, className + ".group"));
            if (jobDetail != null) {
                ScheduleJob job = (ScheduleJob) jobDetail.getJobDataMap().get(className + ".group");
                ScheduleJob newJob = new ScheduleJob();
                newJob.setJobId(StringUtils.getUUID());
                newJobName = className + "_" + newJob.getJobId();
                newJob.setCronExpression(job.getCronExpression());
                newJob.setDesc(job.getDesc());
                newJob.setJobGroup(job.getJobGroup());
                newJob.setJobName(className);
                newJob.setJobStatus(job.getJobStatus());
                newJob.setParam1(job.getParam1());
                newJob.setParam2(job.getParam2());
                newJob.setRepeatSecond(job.getRepeatSecond());

                Class<?> clazz = jobClassMap.get(className);
                if (clazz != null) {
                    startJob(newJob, 1, (Class<? extends Job>) clazz);
                }
            } else {
                newJobName = createJob(className, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
            result.put("message", e.getMessage());
            return result;
        }
        result.put("success", "true");
        result.put("jobName", newJobName);
        return result;
    }

    @SuppressWarnings("unchecked")
    public String createJob(String className, boolean create) throws ClassNotFoundException {
        /**
         * 获取线程数
         */
        Class<?> clazz = null;
        clazz = Class.forName(className);
        if (clazz == null) {
            return null;
        }
        JobBean jobBean = clazz.getAnnotation(JobBean.class);
        if (jobBean == null) {
            return null;
        }

        String threadNumStr = jobBean.thread();
        int threadNum = 1;
        String propertiesKey = null;
        if (create) {
            propertiesKey = StringUtils.parsePropertiesKey(threadNumStr);
            if (StringUtils.isNotEmpty(propertiesKey)) {
                threadNumStr = properties.getProperty(propertiesKey);
            }
            threadNum = Integer.parseInt(threadNumStr);
        }

        ScheduleJob scheduleJob = new ScheduleJob();
        String jobName = clazz.getSimpleName();
        if (!create) {
            scheduleJob.setJobId(StringUtils.getUUID());
            jobName = clazz.getSimpleName() + "_" + scheduleJob.getJobId();
        }
        scheduleJob.setJobName(clazz.getSimpleName());
        scheduleJob.setJobGroup(clazz.getSimpleName() + ".group");

        /**
         * 获取corn表达式
         */
        String cronExpression = jobBean.cron();
        propertiesKey = StringUtils.parsePropertiesKey(cronExpression);
        if (StringUtils.isNotEmpty(propertiesKey)) {
            cronExpression = properties.getProperty(propertiesKey);
        }
        scheduleJob.setCronExpression(cronExpression);

        /**
         * Simple间隔秒
         */
        String repeatSecond = jobBean.repeatSecond();
        propertiesKey = StringUtils.parsePropertiesKey(repeatSecond);
        if (StringUtils.isNotEmpty(propertiesKey)) {
            repeatSecond = properties.getProperty(repeatSecond);
        }
        scheduleJob.setRepeatSecond(repeatSecond);

        /**
         * 参数1
         */
        String param1 = jobBean.param1();
        if (StringUtils.isEmpty(param1)) {
            propertiesKey = clazz.getSimpleName() + ".param1";
        } else {
            propertiesKey = StringUtils.parsePropertiesKey(param1);
        }
        if (StringUtils.isNotEmpty(propertiesKey)) {
            param1 = properties.getProperty(propertiesKey);
        }
        scheduleJob.setParam1(param1);

        /**
         * 参数2
         */
        String param2 = jobBean.param2();
        if (StringUtils.isEmpty(param2)) {
            propertiesKey = clazz.getSimpleName() + ".param2";
        } else {
            propertiesKey = StringUtils.parsePropertiesKey(param2);
        }
        if (StringUtils.isNotEmpty(propertiesKey)) {
            param2 = properties.getProperty(propertiesKey);
        }
        scheduleJob.setParam2(param2);

        String desc = jobBean.desc();
        scheduleJob.setDesc(desc);

        jobClassMap.put(clazz.getSimpleName(), clazz);
        startJob(scheduleJob, threadNum, (Class<? extends Job>) clazz);
        return jobName;
    }

    public boolean startJob(ScheduleJob scheduleJob, int threadNum,
                            Class<? extends Job> jobClass) {
        for (int i = 0; i < threadNum; i++) {
            ScheduleJob job = new ScheduleJob();
            String jobId = scheduleJob.getJobId();
            if (StringUtils.isEmpty(jobId)) {
                jobId = StringUtils.getUUID();
            }
            job.setJobName(scheduleJob.getJobName() + "_" + jobId);
            job.setJobGroup(scheduleJob.getJobGroup());
            job.setJobStatus(DEFUALT_JOB_STATUS);
            job.setCronExpression(scheduleJob.getCronExpression());
            job.setDesc(scheduleJob.getDesc());
            job.setParam1(scheduleJob.getParam1());
            job.setParam2(scheduleJob.getParam2());
            job.setRepeatSecond(scheduleJob.getRepeatSecond());

            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            Trigger trigger = null;
            try {
                trigger = scheduler.getTrigger(triggerKey);
            } catch (SchedulerException e1) {
                e1.printStackTrace();
            }

            //不存在，创建一个
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(jobClass)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put(job.getJobGroup(), job);

                //表达式调度构建器
                if (StringUtils.isNotEmpty(job.getCronExpression())) {
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                            .getCronExpression());

                    //按新的cronExpression表达式构建一个新的trigger
                    trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(),
                            job.getJobGroup()).withSchedule(scheduleBuilder).build();
                }
                //按SimpleSchedule 间隔时间调度
                else if (StringUtils.isNotEmpty(job.getRepeatSecond())) {
                    SimpleScheduleBuilder simpleBuilder = SimpleScheduleBuilder
                            .repeatSecondlyForever(Integer.parseInt(job.getRepeatSecond()));

                    //按SimpleScheduleBuilder 间隔时间创建trigger
                    trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(),
                            job.getJobGroup()).withSchedule(simpleBuilder).build();
                } else {
                    continue;
                }
                try {
                    scheduler.scheduleJob(jobDetail, trigger);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            } else {
                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                if (StringUtils.isNotEmpty(job.getCronExpression())) {
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                            .getCronExpression());

                    //按新的cronExpression表达式重新构建trigger
                    trigger = ((CronTrigger) trigger).getTriggerBuilder().withIdentity(triggerKey)
                            .withSchedule(scheduleBuilder).build();
                    /**
                     trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                     .withSchedule(scheduleBuilder).build();
                     **/
                } else if (StringUtils.isNotEmpty(job.getRepeatSecond())) {
                    SimpleScheduleBuilder simpleBuilder = SimpleScheduleBuilder
                            .repeatSecondlyForever(Integer.parseInt(job.getRepeatSecond()));

                    trigger = ((SimpleTrigger) trigger).getTriggerBuilder().withIdentity(triggerKey)
                            .withSchedule(simpleBuilder).build();
                }
                //按新的trigger重新设置job执行
                try {
                    getScheduler().rescheduleJob(triggerKey, trigger);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public boolean pausseJob(String name, String group) {
        try {
            JobKey jobKey = getJobKey(name, group);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean resumeJob(String name, String group) {
        try {
            JobKey jobKey = getJobKey(name, group);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteJob(String name, String group) {
        try {
            JobKey jobKey = getJobKey(name, group);
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public JobKey getJobKey(String name, String group) {
        return new JobKey(name, group);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        properties = applicationContext.getBean("configProperties", Properties.class);
    }

    public Map<String, Object> getJobState() {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, ScheduleJob> scheduleMap = new HashMap<String, ScheduleJob>();
        List<ScheduleJob> listSchedule = new ArrayList<ScheduleJob>();
        Map<String, String> groupClass = new HashMap<String, String>();
        try {
            List<String> list = scheduler.getJobGroupNames();
            for (String groupName : list) {
                Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
                for (JobKey jobKey : jobKeys) {
                    ScheduleJob scheduleJob = (ScheduleJob) scheduler.getJobDetail(jobKey)
                            .getJobDataMap().get(jobKey.getGroup());
                    groupClass.put(groupName, scheduler.getJobDetail(jobKey).getJobClass().getName());
                    String state = scheduler.getTriggerState(new TriggerKey(jobKey.getName(), jobKey.getGroup())).toString();
                    ScheduleJob sjob = new ScheduleJob(jobKey.getName(), null, state);
                    sjob.setParam1(scheduleJob.getParam1());
                    sjob.setParam2(scheduleJob.getParam2());
                    sjob.setLastExecuteTime(scheduleJob.getLastExecuteTime());
                    sjob.setLastTimeConsuming(scheduleJob.getLastTimeConsuming());
                    sjob.setExecuteTotalCount(scheduleJob.getExecuteTotalCount());
                    scheduleMap.put(jobKey.getName(), sjob);
                    listSchedule.add(sjob);
                }
            }
            result.put("groupClass", groupClass);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<JobExecutionContext> jobList = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext jobExecutionContext : jobList) {
                String jobName = jobExecutionContext.getJobDetail().getKey().getName();
                ScheduleJob job = scheduleMap.get(jobName);
                if (job != null) {
                    job.setNextfireTime(sdf.format(jobExecutionContext.getNextFireTime()));
                    try {
                        job.setPreviousFireTime(sdf.format(jobExecutionContext.getPreviousFireTime()));
                    } catch (Exception e) {
                    }
                    ;
                }
            }
            result.put("schedules", listSchedule);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateJobParam(String param1, String param2, String name, String group) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(name, group));
            ScheduleJob scheduleJob = (ScheduleJob) jobDetail.getJobDataMap().get(group);
            if (StringUtils.isNotEmpty(param1)) {
                scheduleJob.setParam1(param1);
            }
            if (StringUtils.isNotEmpty(param2)) {
                scheduleJob.setParam2(param2);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
