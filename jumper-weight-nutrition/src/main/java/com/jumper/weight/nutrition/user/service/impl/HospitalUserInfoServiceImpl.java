package com.jumper.weight.nutrition.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.mapper.HospitalUserInfoMapper;
import com.jumper.weight.nutrition.user.service.HospitalUserInfoService;

@Service
public class HospitalUserInfoServiceImpl extends BaseServiceImpl<HospitalUserInfo> implements HospitalUserInfoService {
	
	@Autowired
	private HospitalUserInfoMapper hospitalUserInfoMapper;

	@Override
	protected BaseMapper<HospitalUserInfo> getDao() {
		return hospitalUserInfoMapper;
	}

	@Override
	public boolean addOrUpdateHospUser(HospitalUserInfo hospUser) {
		HospitalUserInfo info = hospitalUserInfoMapper.findHospUserByUIdHospId(hospUser.getUserId(), hospUser.getHospitalId());
		int num = 0;
		try {
			if (info != null) {
				//更新
				hospUser.setId(info.getId());
				num = hospitalUserInfoMapper.update(hospUser);
			} else {
				//添加
				num = hospitalUserInfoMapper.insert(hospUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (num > 0);
	}


}
