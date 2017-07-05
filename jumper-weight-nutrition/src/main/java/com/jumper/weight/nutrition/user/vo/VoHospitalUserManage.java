package com.jumper.weight.nutrition.user.vo;

import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;

public class VoHospitalUserManage {
	
	private Integer id;
	/** 更新时间（最近一次完成门诊的时间） */
	private String lastOutpatientTime;
	private VoUserWeightRecord voWeightRecord;
	private VoUserInfo voUserInfo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastOutpatientTime() {
		return lastOutpatientTime;
	}
	public void setLastOutpatientTime(String lastOutpatientTime) {
		this.lastOutpatientTime = lastOutpatientTime;
	}
	public VoUserWeightRecord getVoWeightRecord() {
		return voWeightRecord;
	}
	public void setVoWeightRecord(VoUserWeightRecord voWeightRecord) {
		this.voWeightRecord = voWeightRecord;
	}
	public VoUserInfo getVoUserInfo() {
		return voUserInfo;
	}
	public void setVoUserInfo(VoUserInfo voUserInfo) {
		this.voUserInfo = voUserInfo;
	}
	
}
