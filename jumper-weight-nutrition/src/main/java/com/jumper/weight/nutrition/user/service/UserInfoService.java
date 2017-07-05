package com.jumper.weight.nutrition.user.service;

import java.util.List;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.user.entity.UserInfo;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
	boolean updateFindRollBack (int uId1, int uId2);
	
	/**
	 * 通过userId或hospitalId查询VoUserInfo
	 * @createTime 2017-4-26,上午11:54:18
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId 当不需查询医院用户信息时可传null
	 * @return
	 */
	VoUserInfo findVoUserByUId(int userId, Integer hospitalId);
	
	/**
	 * 通过userIds集合或hospitalId查询VoUserInfo集合
	 * @createTime 2017-4-26,下午3:56:59
	 * @createAuthor fangxilin
	 * @param userIds
	 * @param hospitalId 当不需查询医院用户信息时可传null
	 * @return
	 */
	List<VoUserInfo> listVoUserByUId(List<Integer> userIds, Integer hospitalId);
	
	/**
	 * 通过手机号查询
	 * @createTime 2017-4-27,下午2:25:56
	 * @createAuthor fangxilin
	 * @param mobile
	 * @param hospitalId 当不需查询医院用户信息时可传null 
	 * @return
	 */
	VoUserInfo findVoUserByMobile(String mobile, Integer hospitalId);
	
	/**
	 * 添加或更新用户信息
	 * @createTime 2017-4-26,下午5:45:09
	 * @createAuthor fangxilin
	 * @param vo
	 * @return 成功返回userId，失败返回0
	 */
	int addOrUpdteUser(VoUserInfo vo);
	
	/**
	 * 新增用户时，给用户默认的设置
	 * @createTime 2017-4-27,上午9:27:16
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	boolean insertDefaultHealthySet(int userId);
	
}