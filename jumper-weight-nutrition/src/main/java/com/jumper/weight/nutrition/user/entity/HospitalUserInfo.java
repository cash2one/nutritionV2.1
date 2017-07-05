package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class HospitalUserInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer hospitalId;

    private Integer userId;

    private String outpatientNum;

    private Integer pregnantType;

    private Date addTime;

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOutpatientNum() {
        return outpatientNum;
    }

    public void setOutpatientNum(String outpatientNum) {
        this.outpatientNum = outpatientNum == null ? null : outpatientNum.trim();
    }

    public Integer getPregnantType() {
        return pregnantType;
    }

    public void setPregnantType(Integer pregnantType) {
        this.pregnantType = pregnantType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}