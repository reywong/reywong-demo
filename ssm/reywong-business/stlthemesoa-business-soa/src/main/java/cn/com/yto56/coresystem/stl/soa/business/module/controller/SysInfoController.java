package cn.com.yto56.coresystem.stl.soa.business.module.controller;

import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import cn.com.yto56.coresystem.common.stl.framework.domain.AppResult;
import cn.com.yto56.coresystem.stl.soa.business.module.biz.ISysInfoBiz;
import cn.com.yto56.coresystem.stl.soa.business.module.soa.ISysInfoController;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangrui on 2017/6/12.
 */
@Controller
@RequestMapping(value = "/sysInfoController")
public class SysInfoController implements ISysInfoController {
    private static final Logger logger = LoggerFactory.getLogger(SysInfoController.class);
    @Autowired
    private ISysInfoBiz sysInfoBizImpl;

    @RequestMapping(value = "/register")
    @ResponseBody
    public String register(String requestParam) {
        long startTime = new Date().getTime();
        logger.debug("接口【sysInfoController-register】请求参数：" + requestParam);
        Boolean result_flag = false;
        String result_code = "fail";
        String result_message = "";
        Map datas = null;
        //将传递的json参数转换成map
        Map requstMap = JSON.parseObject(requestParam, Map.class);
        if (requstMap != null) {
            String stl_soa_sysname = (String) requstMap.get("stl_soa_sysname");
            String stl_soa_sysremark = (String) requstMap.get("stl_soa_sysremark");
            String stl_soa_key = (String) requstMap.get("stl_soa_key");
            if (StringUtils.isEmpty(stl_soa_sysname)) {
                result_message = "参数[stl_soa_sysname]不能为空";
            } else if (StringUtils.isEmpty(stl_soa_key)) {
                result_message = "参数[stl_soa_key]不能为空";
            } else {
                AppResult appResult = sysInfoBizImpl.register(stl_soa_sysname, stl_soa_sysremark, stl_soa_key);
                result_flag = appResult.getResultFlag();
                result_code = appResult.getResultCode();
                result_message = appResult.getResultMessage();
                datas = (Map) appResult.getDatas();
            }
        } else {
            result_message = "请检查json格式是否有误,传递的参数为:" + requestParam;
        }

        //转换成json返回
        Map responseMap = new HashMap();
        responseMap.put("result_flag", result_flag);
        responseMap.put("result_code", result_code);
        responseMap.put("result_message", result_message);
        responseMap.put("result_datas", datas);
        String reuslt = JSON.toJSONString(responseMap);
        long endTime = new Date().getTime();
        logger.debug("接口【sysInfoController-register】返回参数：" + requestParam+",接口处理耗时："+(endTime-startTime)/1000.0+"秒");
        return reuslt;
    }
}
