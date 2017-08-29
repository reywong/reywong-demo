package cn.com.yto56.coresystem.stl.soa.business.module.biz.impl;

import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import cn.com.yto56.coresystem.common.stl.framework.domain.AppResult;
import cn.com.yto56.coresystem.stl.soa.business.module.biz.ISysInfoBiz;
import cn.com.yto56.coresystem.stl.soa.logic.module.dao.SysInfoMapper;
import cn.com.yto56.coresystem.stl.soa.logic.module.domain.SysInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangrui on 2017/8/15.
 */
@Service
public class SysInfoBizImpl implements ISysInfoBiz {
    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Transactional
    public AppResult register(String stl_soa_sysname, String stl_soa_sysremark, String stl_soa_key) {
        AppResult result = new AppResult(false, "fail", "系统错误", null);
        String stl_soa_appid = StringUtils.getUUID();
        SysInfo sysInfo = new SysInfo();
        sysInfo.setStlSoaAppid(stl_soa_appid);
        sysInfo.setStlSoaSysname(stl_soa_sysname);
        sysInfo.setStlSoaSysremark(stl_soa_sysremark);
        sysInfo.setStlSoaKey(stl_soa_key);
        sysInfo.setCreateTime(new Date());
        int t = sysInfoMapper.insertSelective(sysInfo);
        if (t > 0) {

            Map datas = new HashMap();
            datas.put("stl_soa_appid", stl_soa_appid);
            result = new AppResult(true, "sucess", "执行成功", datas);
        } else {
            result = new AppResult(false, "fail", "执行失败", null);
        }
        return result;
    }
}
