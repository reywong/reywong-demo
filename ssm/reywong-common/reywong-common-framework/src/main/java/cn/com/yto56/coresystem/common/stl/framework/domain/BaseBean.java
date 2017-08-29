package cn.com.yto56.coresystem.common.stl.framework.domain;

import java.util.Date;

/**
 * Created by wangrui on 2017/2/15.
 */
public class BaseBean {
    Integer id;
    String status;
    Date create_time;
    Date update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
