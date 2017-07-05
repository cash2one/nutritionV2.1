package com.jumper.weight.nutrition.hospital.mapper;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;


public interface WeightHospitalSettingMapper extends BaseMapper<WeightHospitalSetting> {
	/**
	 * 通过医院id查询
	 * @createTime 2017-6-22,上午10:06:23
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	WeightHospitalSetting findSettingByHospId(int hospitalId);
}