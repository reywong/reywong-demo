package cn.com.yto56.coresystem.stl.soa.business.module.job;

import cn.com.yto56.coresystem.common.stl.framework.quartz.AbstractJobBean;
import cn.com.yto56.coresystem.common.stl.framework.quartz.JobBean;
import cn.com.yto56.coresystem.common.stl.framework.quartz.ScheduleJob;
import cn.com.yto56.coresystem.stl.soa.business.module.biz.ISoaJobBiz;
import cn.com.yto56.coresystem.stl.soa.business.module.biz.impl.SoaJobBizImpl;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 串行
 */
@DisallowConcurrentExecution
@JobBean(cron = "*/5 * * * * ?", thread = "1")
public class SoaDemoJob extends AbstractJobBean {
    private static final Logger logger = LoggerFactory.getLogger(SoaDemoJob.class);

    public SoaDemoJob() {
        super(SoaDemoJob.class, SoaJobBizImpl.class);
    }

    /**
     * 执行Job
     *
     * @param context
     * @param scheduleJob
     * @param service
     */
    public void run(JobExecutionContext context, ScheduleJob scheduleJob, Object service) {
        logger.info(this.getClass().getSimpleName() + "show---> begin()");
        ((ISoaJobBiz) service).show();
        logger.info(this.getClass().getSimpleName() + "show---> end()");
    }
}
