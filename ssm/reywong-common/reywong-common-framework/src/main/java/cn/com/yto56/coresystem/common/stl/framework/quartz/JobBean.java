package cn.com.yto56.coresystem.common.stl.framework.quartz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置JobBean任务类
 * @author lihui
 * 2016-12-16
 * </br>
 * 
 * 
 * 使用时需在springmvc-plugin.xml中加:<br/>
 *&lt;bean id="scheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean"/&gt;<br/>
 *&lt;bean id="scheduleService" class="cn.com.yto56.coresystem.framework.quartz.impl.ScheduleServiceImpl"&gt;<br/>
 *	&lt;property name="scheduler" ref="scheduler"/&gt;<br/>
 *&lt;/bean&gt;<br/>
 *&lt;bean class="cn.com.yto56.coresystem.framework.quartz.ScheduleScannerConfigurer"&gt;<br/>
 *	&lt;property name="scheduleService" ref="scheduleService"/&gt;<br/>
 *&lt;/bean&gt;<br/>
 *&lt;!-- 加载启动JobBean end--&gt;
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JobBean {
	
	/**
	 * 线程数可使用${}配置例:${test.job.threadNum}
	 * @return
	 */
	String thread() default "1";
	
	/**
	 * cron表达式可使用${}配置例:${test.job.corn}
	 * @return
	 */
	String cron() default "";
	
	/**
	 * Simple 指示间隔多少秒执行单位秒
	 * @return
	 */
	String repeatSecond() default "";
	
	/**
	 * Job描述
	 * @return
	 */
	String desc() default "";
	
	/**
	 * 参数1可使用${}
	 * @return
	 */
	String param1() default "";
	
	/**
	 * 参数2可使用${}
	 * @return
	 */
	String param2() default "";
	
}
