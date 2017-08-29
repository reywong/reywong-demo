package cn.com.yto56.coresystem.stl.soa.business.module.biz.impl;

import cn.com.yto56.coresystem.stl.soa.business.module.biz.ISoaJobBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoaJobBizImpl implements ISoaJobBiz {
    private static final Logger logger = LoggerFactory.getLogger(SoaJobBizImpl.class);

    @Override
    public void show() {
        logger.info("SoaJobBizImpl-show");
    }
}
