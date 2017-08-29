package cn.com.yto56.coresystem.stl.soa.business.module.biz;

import cn.com.yto56.coresystem.common.stl.framework.domain.AppResult;
import cn.com.yto56.coresystem.stl.soa.logic.module.domain.SysInfo;

/**
 * Created by wangrui on 2017/8/15.
 */
public interface ISysInfoBiz {
    public AppResult register(String stl_soa_sysname, String stl_soa_sysremark, String stl_soa_key);
}
