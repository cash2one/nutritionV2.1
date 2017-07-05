package com.jumper.weight.nutrition.user.service;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;

public interface HospitalUserInfoService extends BaseService<HospitalUserInfo> {
	/**
	 * 添加或更新医院用户信息
	 * @createTime 2017-4-27,上午10:27:00
	 * @createAuthor fangxilin
	 * @param hospUser
	 * @return
	 */
	boolean addOrUpdateHospUser(HospitalUserInfo hospUser);
}
