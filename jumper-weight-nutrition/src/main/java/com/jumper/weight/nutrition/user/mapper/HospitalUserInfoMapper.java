package com.jumper.weight.nutrition.user.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;


public interface HospitalUserInfoMapper extends BaseMapper<HospitalUserInfo> {
	
	/**
	 * 通过医院id用户id查询医院用户
	 * @createTime 2017-4-26,下午2:44:07
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	HospitalUserInfo findHospUserByUIdHospId(@Param("userId") Integer userId, @Param("hospitalId") Integer hospitalId);
	
	/**
	 * 通过医院id用户id集合查询医院用户列表
	 * @createTime 2017-4-26,下午4:28:13
	 * @createAuthor fangxilin
	 * @param userIds
	 * @param hospitalId
	 * @return
	 */
	List<HospitalUserInfo> listHospUserByUIdHospId(@Param("userIds") List<Integer> userIds, @Param("hospitalId") Integer hospitalId);
}