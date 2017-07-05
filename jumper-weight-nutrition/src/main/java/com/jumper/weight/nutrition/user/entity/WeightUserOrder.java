package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class WeightUserOrder extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
    private Integer userId;

    private String mobile;

    private String realName;

    private Integer height;

    private Double weight;

    private Integer pWeek;

    private Date expectedDate;

    private Double currentWeight;

    private Integer weightExceptionType;

    private Date birthday;
    
    private Double currentBmi;
    /**当前体重状态 0：偏低，1：正常，2超重*/
    private Integer weightStatus;
    /**最近一次秤体重时间*/
    private Date lastWeightTime;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getpWeek() {
        return pWeek;
    }

    public void setpWeek(Integer pWeek) {
        this.pWeek = pWeek;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Integer getWeightExceptionType() {
		return weightExceptionType;
	}

	public void setWeightExceptionType(Integer weightExceptionType) {
		this.weightExceptionType = weightExceptionType;
	}

	public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

	public Double getCurrentBmi() {
		return currentBmi;
	}

	public void setCurrentBmi(Double currentBmi) {
		this.currentBmi = currentBmi;
	}

	public Integer getWeightStatus() {
		return weightStatus;
	}

	public void setWeightStatus(Integer weightStatus) {
		this.weightStatus = weightStatus;
	}

	public Date getLastWeightTime() {
		return lastWeightTime;
	}

	public void setLastWeightTime(Date lastWeightTime) {
		this.lastWeightTime = lastWeightTime;
	}
}