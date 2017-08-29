package cn.com.yto56.coresystem.common.stl.framework.domain;

import java.io.Serializable;

/**
 * Created by user on 2016/12/13.
 */
public class AppResult implements Serializable {
    private static final long serialVersionUID = 1792241905841405422L;
    private Boolean resultFlag;
    private String resultCode;
    private String resultMessage;

    private Object datas;

    public AppResult() {

    }

    public AppResult(Boolean resultFlag, String resultMessage) {
        this.resultFlag = resultFlag;
        this.resultMessage = resultMessage;
    }

    public AppResult(Boolean resultFlag, String resultMessage, Object datas) {
        this.resultFlag = resultFlag;
        this.resultMessage = resultMessage;
        this.datas = datas;
    }

    public AppResult(Boolean resultFlag, String resultCode, String resultMessage) {
        this.resultFlag = resultFlag;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public AppResult(Boolean resultFlag, String resultCode, String resultMessage, Object datas) {
        this.resultFlag = resultFlag;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.datas = datas;
    }


    public Boolean getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(Boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
