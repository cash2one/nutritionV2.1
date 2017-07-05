package com.jumper.weight.nutrition.hospital.service;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;

public interface WeightHospitalSettingService extends BaseService<WeightHospitalSetting> {
	/**
	 * 通过医院id查询
	 * @createTime 2017-6-22,上午10:06:23
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	WeightHospitalSetting findSettingByHospId(int hospitalId);
	
	/**
	 * 保存医院设置
	 * @createTime 2017-6-22,上午10:17:42
	 * @createAuthor fangxilin
	 * @param setting
	 * @return
	 */
	boolean saveSetting(WeightHospitalSetting setting);
}
