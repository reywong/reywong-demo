package cn.com.yto56.coresystem.stl.soa.logic.module.domain;

import java.io.Serializable;
import java.util.Date;

public class SysInfo implements Serializable {
    private Integer id;

    private String stlSoaAppid;

    private String stlSoaKey;

    private String stlSoaSysname;

    private String stlSoaSysremark;

    private Date createTime;

    private Date updateTime;

    private String status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStlSoaAppid() {
        return stlSoaAppid;
    }

    public void setStlSoaAppid(String stlSoaAppid) {
        this.stlSoaAppid = stlSoaAppid == null ? null : stlSoaAppid.trim();
    }

    public String getStlSoaKey() {
        return stlSoaKey;
    }

    public void setStlSoaKey(String stlSoaKey) {
        this.stlSoaKey = stlSoaKey == null ? null : stlSoaKey.trim();
    }

    public String getStlSoaSysname() {
        return stlSoaSysname;
    }

    public void setStlSoaSysname(String stlSoaSysname) {
        this.stlSoaSysname = stlSoaSysname == null ? null : stlSoaSysname.trim();
    }

    public String getStlSoaSysremark() {
        return stlSoaSysremark;
    }

    public void setStlSoaSysremark(String stlSoaSysremark) {
        this.stlSoaSysremark = stlSoaSysremark == null ? null : stlSoaSysremark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}