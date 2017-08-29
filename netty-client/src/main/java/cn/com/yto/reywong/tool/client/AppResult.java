package cn.com.yto.reywong.tool.client;

import java.io.Serializable;

/**
 * Created by user on 2016/12/13.
 */
public class AppResult implements Serializable {
    private Boolean resultFlag;

    private String resultMessage;

    private Object datas;

    public AppResult() {

    }

    public AppResult(Boolean resultFlag, String resultMessage, Object datas) {
        this.resultFlag = resultFlag;
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
}
