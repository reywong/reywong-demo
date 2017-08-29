package cn.com.yto56.coresystem.common.stl.framework.quartz;

import java.util.Map;


public interface IScheduleService {
	
	/**
	 * 增加JOB
	 * @param className
	 * @param suffix
	 * @return
	 */
	public Map<String, Object> addJob(String className, String suffix);
	
	/**
	 * 创建JOB
	 * @param className
	 * @return
	 */
	public String createJob(String className, boolean create)throws ClassNotFoundException;
	
	/**
	 * 启动JOB
	 * @param scheduleJob
	 * @param threadNum
	 * @param jobClass
	 * @return
	 */
	public boolean startJob(ScheduleJob scheduleJob, int threadNum, Class<? extends org.quartz.Job> jobClass);
	
	/**
	 * 暂停JOB
	 * @param name
     * @param group
	 * @return
	 */
	public boolean pausseJob(String name, String group);
	
	/**
	 * 恢复JOB
	 * @param name
     * @param group
	 * @return
	 */
	public boolean resumeJob(String name, String group);
	
	/**
	 * 移除JOB
	 * @param name
     * @param group
	 * @return
	 */
	public boolean deleteJob(String name, String group);
	
	/**
	 * 获取JOB状态
	 * @return
	 */
	public Map<String, Object> getJobState();
	
	/**
	 * 修改JOB参数
	 * @param param1
	 * @param param2
	 * @param name
	 * @param group
	 * @return
	 */
	public boolean updateJobParam(String param1, String param2, String name, String group);
	
}
