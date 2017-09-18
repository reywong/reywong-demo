package cn.com.yto.reywong.tool.springboot.test.logic.impl;

import cn.com.yto.reywong.tool.springboot.test.domain.UserInfo;
import cn.com.yto.reywong.tool.springboot.test.logic.IUserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserInfoDaoImpl implements IUserInfoDao {
    @Autowired
    @Qualifier("slavejdbc")
    JdbcTemplate jdbcTemplate;

    @Override
    public UserInfo getUserInfo(Integer id) {
        return jdbcTemplate.queryForObject("select * from t_userinfo where id=?", new Object[]{id}, new RowMapper<UserInfo>() {
            @Override
            public UserInfo mapRow(ResultSet rs, int i) throws SQLException {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(rs.getInt("id"));
                userInfo.setUserName(rs.getString("username"));
                userInfo.setPassword(rs.getString("password"));
                return userInfo;
            }
        });
    }
}
