package cn.com.yto.reywong.tool.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangrui on 2017/5/3.
 */
public class MsgResult {
    private String interfaceName;
    private String yto_msg_appId;
    private Boolean responseFlag;
    private String responseMessage;
    private Object datas;
    private String responseJson;
    private String requestJson;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getYto_msg_appId() {
        return yto_msg_appId;
    }

    public void setYto_msg_appId(String yto_msg_appId) {
        this.yto_msg_appId = yto_msg_appId;
    }

    public Boolean getResponseFlag() {
        return responseFlag;
    }

    public void setResponseFlag(Boolean responseFlag) {
        this.responseFlag = responseFlag;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    /**
     * 获取请求json
     *
     * @param interfaceName 接口名称
     * @param yto_msg_appId appId
     * @param datas         接口数据
     * @return
     */
    public String getRequestJson(String interfaceName, String yto_msg_appId, Object datas) {
        Map requestMap = new HashMap();
        requestMap.put("interface", interfaceName);
        Map requestInfo = new HashMap();
        requestInfo.put("yto_msg_appId", yto_msg_appId);
        requestInfo.put("datas", datas);
        requestMap.put("request", requestInfo);
        requestJson = DataTypeChangeTool.mapToJson(requestMap);
        return requestJson;
    }

    /**
     * 设置参数json
     *
     * @param paramJson
     */
    public void setParamJson(String paramJson) {
        Map requestMap = DataTypeChangeTool.jsonToMap(paramJson);
        interfaceName = (String) requestMap.get("interface");
        Map requestInfo = (Map) requestMap.get("request");
        if (requestInfo != null) {
            setRequestJson(paramJson);
        } else {
            setResponseJson(paramJson);
        }
    }

    public void setRequestJson(String requestJson) {
        Map requestMap = DataTypeChangeTool.jsonToMap(requestJson);
        interfaceName = (String) requestMap.get("interface");
        Map requestInfo = (Map) requestMap.get("request");
        yto_msg_appId = (String) requestInfo.get("yto_msg_appId");
        datas = requestInfo.get("datas");
    }

    /**
     * 获取请求信息
     *
     * @return
     */
    public String getRequestJson() {
        return getRequestJson(interfaceName, yto_msg_appId, datas);
    }


    /**
     * 获取返回json
     *
     * @param interfaceName   接口名称
     * @param responseFlag    返回结果状态
     * @param responseMessage 返回描述信息
     * @param datas           数据
     * @return
     */
    public String getResponseJson(String interfaceName, Boolean responseFlag, String responseMessage, Object datas) {
        Map responseMap = new HashMap();
        responseMap.put("interface", interfaceName);
        Map responseInfo = new HashMap();
        responseInfo.put("resultFlag", responseFlag);
        responseInfo.put("resultMessage", responseMessage);
        responseInfo.put("datas", datas);
        responseMap.put("response", responseInfo);
        responseJson = DataTypeChangeTool.mapToJson(responseMap);
        return responseJson;
    }

    /**
     * 获取返回信息
     *
     * @return
     */
    public String getResponseJson() {
        return getResponseJson(interfaceName, responseFlag, responseMessage, datas);
    }


    /**
     * 获取返回json
     *
     * @param interfaceName
     * @param appResult
     * @return
     */
    public String getResponseJson(String interfaceName, AppResult appResult) {
        return getResponseJson(interfaceName, appResult.getResultFlag(), appResult.getResultMessage(), appResult.getDatas());
    }

    /**
     * 将返回json转换成对应数据
     *
     * @param responseJson
     */
    public void setResponseJson(String responseJson) {
        Map responseMap = DataTypeChangeTool.jsonToMap(responseJson);
        interfaceName = (String) responseMap.get("interface");
        Map responseInfo = (Map) responseMap.get("response");
        responseFlag = (Boolean) responseInfo.get("responseFlag");
        responseMessage = (String) responseInfo.get("responseMessage");
        datas = responseInfo.get("datas");
    }


}
