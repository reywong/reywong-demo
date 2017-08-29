package cn.com.yto56.coresystem.common.stl.framework.quartz;

public class ScheduleJob {

    public ScheduleJob() {
    }

    public ScheduleJob(String jobName, String jobGroup, String jobStatus) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobStatus = jobStatus;
    }

    /**
     * 任务id *
     */
    private String jobId;

    /**
     * 任务名称 *
     */
    private String jobName;

    /**
     * 任务分组 *
     */
    private String jobGroup;

    /**
     * 任务状态 0禁用 1启用 2删除*
     */
    private String jobStatus;

    /**
     * 任务运行时间表达式 *
     */
    private String cronExpression;

    /**
     * 间隔描述 *
     */
    private String repeatSecond;

    /**
     * 任务描述 *
     */
    private String desc;

    private String param1;
    private String param2;

    private String nextfireTime;
    private String previousFireTime;
    private String jobRunTime;

    private String jobClassName;

    /**
     * 最后执行时间
     */
    private String lastExecuteTime;

    /**
     * 总执行次数
     */
    private int executeTotalCount;

    /**
     * 最后一次执行总耗时
     */
    private long lastTimeConsuming;


    public String getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(String lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public int getExecuteTotalCount() {
        return executeTotalCount;
    }

    public void setExecuteTotalCount(int executeTotalCount) {
        this.executeTotalCount = executeTotalCount;
    }

    public long getLastTimeConsuming() {
        return lastTimeConsuming;
    }

    public void setLastTimeConsuming(long lastTimeConsuming) {
        this.lastTimeConsuming = lastTimeConsuming;
    }

    public String getNextfireTime() {
        return nextfireTime;
    }

    public void setNextfireTime(String nextfireTime) {
        this.nextfireTime = nextfireTime;
    }

    public String getPreviousFireTime() {
        return previousFireTime;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public void setPreviousFireTime(String previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public String getJobRunTime() {
        return jobRunTime;
    }

    public void setJobRunTime(String jobRunTime) {
        this.jobRunTime = jobRunTime;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getRepeatSecond() {
        return repeatSecond;
    }

    public void setRepeatSecond(String repeatSecond) {
        this.repeatSecond = repeatSecond;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}