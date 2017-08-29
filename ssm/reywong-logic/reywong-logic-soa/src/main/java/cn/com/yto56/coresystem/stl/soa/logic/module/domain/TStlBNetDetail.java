package cn.com.yto56.coresystem.stl.soa.logic.module.domain;

import java.io.Serializable;
import java.util.Date;

public class TStlBNetDetail implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6237000654915347652L;
	
	private String waybillNo;
	private String billNo;
	private Date createTime;
	private Date modifyTime;
	private Date billCreateTime;
	private String billCreateEmpNo;
	private String billCreateEmpName;
	private String billCreateOrgCode;
	private String billCreateOrgName;
	private int quantity;
	private String effectiveTypeCode;//揽收时效
	private String desOrgCode;//揽收目的网点
	private String desOrgName;
	private String desCityCode;//揽收目的网点城市代码
	private Double takingWeight;
	private Double receiveWeight;
	private Double diffWeight;
	private Date takingTime;
	private Date receiveTime;
	private String sourceOrgCode;//揽收始发网点
	private String sourceOrgName;//揽收始发网点
	private String sourceCityCode;//始发网点城市代码
	private String receiveEmpNo;
	private String receiveEmpName;
	private String takingEmpNo;
	private String takingEmpName;
	private String empBelongOrgCode;
	private String empBelongOrgName;
	private Double receiveAmount;
	private Double referAmount;
	private Double balanceAmount;
	private String balanceType;
	private String delKey;
	private String genFlg;
	private String sendProvCode;
	private String sendProvName;
	private String channelCode;
	private String channelName;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getBillCreateTime() {
		return billCreateTime;
	}
	public void setBillCreateTime(Date billCreateTime) {
		this.billCreateTime = billCreateTime;
	}
	public String getBillCreateEmpNo() {
		return billCreateEmpNo;
	}
	public void setBillCreateEmpNo(String billCreateEmpNo) {
		this.billCreateEmpNo = billCreateEmpNo;
	}
	public String getBillCreateEmpName() {
		return billCreateEmpName;
	}
	public void setBillCreateEmpName(String billCreateEmpName) {
		this.billCreateEmpName = billCreateEmpName;
	}
	public String getBillCreateOrgCode() {
		return billCreateOrgCode;
	}
	public void setBillCreateOrgCode(String billCreateOrgCode) {
		this.billCreateOrgCode = billCreateOrgCode;
	}
	public String getBillCreateOrgName() {
		return billCreateOrgName;
	}
	public void setBillCreateOrgName(String billCreateOrgName) {
		this.billCreateOrgName = billCreateOrgName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getEffectiveTypeCode() {
		return effectiveTypeCode;
	}
	public void setEffectiveTypeCode(String effectiveTypeCode) {
		this.effectiveTypeCode = effectiveTypeCode;
	}
	public String getDesOrgCode() {
		return desOrgCode;
	}
	public void setDesOrgCode(String desOrgCode) {
		this.desOrgCode = desOrgCode;
	}
	public String getDesOrgName() {
		return desOrgName;
	}
	public void setDesOrgName(String desOrgName) {
		this.desOrgName = desOrgName;
	}
	public String getDesCityCode() {
		return desCityCode;
	}
	public void setDesCityCode(String desCityCode) {
		this.desCityCode = desCityCode;
	}
	public Double getTakingWeight() {
		return takingWeight;
	}
	public void setTakingWeight(Double takingWeight) {
		this.takingWeight = takingWeight;
	}
	public Double getReceiveWeight() {
		return receiveWeight;
	}
	public void setReceiveWeight(Double receiveWeight) {
		this.receiveWeight = receiveWeight;
	}
	public Double getDiffWeight() {
		return diffWeight;
	}
	public void setDiffWeight(Double diffWeight) {
		this.diffWeight = diffWeight;
	}
	public Date getTakingTime() {
		return takingTime;
	}
	public void setTakingTime(Date takingTime) {
		this.takingTime = takingTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getSourceOrgCode() {
		return sourceOrgCode;
	}
	public void setSourceOrgCode(String sourceOrgCode) {
		this.sourceOrgCode = sourceOrgCode;
	}
	public String getSourceOrgName() {
		return sourceOrgName;
	}
	public void setSourceOrgName(String sourceOrgName) {
		this.sourceOrgName = sourceOrgName;
	}
	public String getSourceCityCode() {
		return sourceCityCode;
	}
	public void setSourceCityCode(String sourceCityCode) {
		this.sourceCityCode = sourceCityCode;
	}
	public String getReceiveEmpNo() {
		return receiveEmpNo;
	}
	public void setReceiveEmpNo(String receiveEmpNo) {
		this.receiveEmpNo = receiveEmpNo;
	}
	public String getReceiveEmpName() {
		return receiveEmpName;
	}
	public void setReceiveEmpName(String receiveEmpName) {
		this.receiveEmpName = receiveEmpName;
	}
	public String getTakingEmpNo() {
		return takingEmpNo;
	}
	public void setTakingEmpNo(String takingEmpNo) {
		this.takingEmpNo = takingEmpNo;
	}
	public String getTakingEmpName() {
		return takingEmpName;
	}
	public void setTakingEmpName(String takingEmpName) {
		this.takingEmpName = takingEmpName;
	}
	public String getEmpBelongOrgCode() {
		return empBelongOrgCode;
	}
	public void setEmpBelongOrgCode(String empBelongOrgCode) {
		this.empBelongOrgCode = empBelongOrgCode;
	}
	public String getEmpBelongOrgName() {
		return empBelongOrgName;
	}
	public void setEmpBelongOrgName(String empBelongOrgName) {
		this.empBelongOrgName = empBelongOrgName;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public Double getReferAmount() {
		return referAmount;
	}
	public void setReferAmount(Double referAmount) {
		this.referAmount = referAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	public String getDelKey() {
		return delKey;
	}
	public void setDelKey(String delKey) {
		this.delKey = delKey;
	}
	public String getGenFlg() {
		return genFlg;
	}
	public void setGenFlg(String genFlg) {
		this.genFlg = genFlg;
	}
	public String getSendProvCode() {
		return sendProvCode;
	}
	public void setSendProvCode(String sendProvCode) {
		this.sendProvCode = sendProvCode;
	}
	public String getSendProvName() {
		return sendProvName;
	}
	public void setSendProvName(String sendProvName) {
		this.sendProvName = sendProvName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	
	
	

}
