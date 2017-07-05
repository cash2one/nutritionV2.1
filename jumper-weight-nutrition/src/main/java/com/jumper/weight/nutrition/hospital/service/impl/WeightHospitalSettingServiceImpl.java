package com.jumper.weight.nutrition.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.mapper.WeightHospitalSettingMapper;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalSettingService;

@Service
public class WeightHospitalSettingServiceImpl extends BaseServiceImpl<WeightHospitalSetting> implements WeightHospitalSettingService {

	@Autowired
	private WeightHospitalSettingMapper weightHospitalSettingMapper;
	
	@Override
	protected BaseMapper<WeightHospitalSetting> getDao() {
		return weightHospitalSettingMapper;
	}

	@Override
	public WeightHospitalSetting findSettingByHospId(int hospitalId) {
		WeightHospitalSetting setting = weightHospitalSettingMapper.findSettingByHospId(hospitalId);
		if (setting == null) {//为空的话默认不隐藏
			setting = new WeightHospitalSetting(hospitalId, 0, 0, 0, 0);
		}
		return setting;
	}

	@Override
	public boolean saveSetting(WeightHospitalSetting setting) {
		WeightHospitalSetting info = weightHospitalSettingMapper.findSettingByHospId(setting.getHospitalId());
		try {
			if (info == null) {//添加
				weightHospitalSettingMapper.insert(setting);
			} else {
				setting.setId(info.getId());
				weightHospitalSettingMapper.update(setting);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
