package cn.com.yto56.coresystem.stl.soa.logic.module.dao;

import cn.com.yto56.coresystem.stl.soa.logic.module.domain.SysInfo;

public interface SysInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysInfo record);

    int insertSelective(SysInfo record);

    SysInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysInfo record);

    int updateByPrimaryKey(SysInfo record);
}