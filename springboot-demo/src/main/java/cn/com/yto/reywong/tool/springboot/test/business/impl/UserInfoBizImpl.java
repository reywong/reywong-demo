package cn.com.yto.reywong.tool.springboot.test.business.impl;

import cn.com.yto.reywong.tool.springboot.test.business.IUserInfoBiz;
import cn.com.yto.reywong.tool.springboot.test.domain.UserInfo;
import cn.com.yto.reywong.tool.springboot.test.logic.IUserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoBizImpl implements IUserInfoBiz {
    @Autowired
    private IUserInfoDao userInfoDaoImpl;

    @Override
    public UserInfo getUserInfo(Integer id) {
        return userInfoDaoImpl.getUserInfo(id);
    }
}
