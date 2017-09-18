package cn.com.yto.reywong.tool.springboot.test.logic;

import cn.com.yto.reywong.tool.springboot.test.domain.UserInfo;

public interface IUserInfoDao {
    UserInfo getUserInfo(Integer id);
}
