package com.jumper.weight.nutrition.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.common.service.sms.SMSService;
import com.jumper.common.utils.CommonReturnMsg;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.MD5EncryptUtils;
import com.jumper.weight.common.util.SignatureUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.hospital.entity.HospitalInfo;
import com.jumper.weight.nutrition.hospital.mapper.HospitalInfoMapper;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.entity.PregnantHealthySetting;
import com.jumper.weight.nutrition.user.entity.UserExtraInfo;
import com.jumper.weight.nutrition.user.entity.UserInfo;
import com.jumper.weight.nutrition.user.mapper.HospitalUserInfoMapper;
import com.jumper.weight.nutrition.user.mapper.PregnantHealthySettingMapper;
import com.jumper.weight.nutrition.user.mapper.UserExtraInfoMapper;
import com.jumper.weight.nutrition.user.mapper.UserInfoMapper;
import com.jumper.weight.nutrition.user.service.HospitalUserInfoService;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private UserExtraInfoMapper userExtraInfoMapper;
	@Autowired
	private HospitalUserInfoMapper hospitalUserInfoMapper;
	@Autowired
	private PregnantHealthySettingMapper pregnantHealthySettingMapper;
	@Autowired
	private SMSService sMSService;
	@Autowired
	private HospitalUserInfoService hospitalUserInfoService;
	@Autowired
	private HospitalInfoMapper hospitalInfoMapper;
	
	@Override
	protected BaseMapper<UserInfo> getDao() {
		return userInfoMapper;
	}
	
	@Override
	public boolean updateFindRollBack(int uId1, int uId2) {
		UserInfo u1 = new UserInfo();
		u1.setNickName("rollBackName");
		u1.setId(uId1);
		try {
			Integer ret = userInfoMapper.update(u1);
			int a = 3/0;//模拟异常
			return (ret>0);
		} catch (Exception e) {
			logger.error("异常，将进行回滚");
			throw new RuntimeException(e);
		}
	}

	@Override
	public VoUserInfo findVoUserByUId(int userId, Integer hospitalId) {
		UserInfo userInfo = userInfoMapper.findUserByUserId(userId);
		if (userInfo == null) {
			return null;
		}
		HospitalUserInfo hospUser = null;
		String hospitalName = null;
		if (hospitalId != null) {
			HospitalInfo hosp = hospitalInfoMapper.findById(hospitalId);
			hospitalName = (hosp != null) ? hosp.getName() : null;
			hospUser = hospitalUserInfoMapper.findHospUserByUIdHospId(userId, hospitalId);
		}
		VoUserInfo vo = getVoUserInfo(userInfo, hospUser);
		vo.setHospitalName(hospitalName);
		return vo;
	}
	
	@Override
	public List<VoUserInfo> listVoUserByUId(List<Integer> userIds, Integer hospitalId) {
		List<VoUserInfo> voList = new ArrayList<VoUserInfo>();
		if (ArrayUtils.isEmpty(userIds)) {
			return voList;
		}
		List<UserInfo> userList = userInfoMapper.listUserByUserId(userIds);
		List<HospitalUserInfo> hospUserList = null;
		if (hospitalId != null) {
			hospUserList = hospitalUserInfoMapper.listHospUserByUIdHospId(userIds, hospitalId);
		}
		
		if (ArrayUtils.isEmpty(hospUserList)) {
			for (UserInfo user : userList) {
				VoUserInfo vo = getVoUserInfo(user, null);
				voList.add(vo);
			}
			return voList;
		}
		//有医院用户信息
		HospitalUserInfo hospUser = null;
		for (UserInfo user : userList) {
			for (HospitalUserInfo hu : hospUserList) {
				if (user.getId().intValue() == hu.getUserId()) {
					hospUser = hu;
					break;
				}
			}
			VoUserInfo vo = getVoUserInfo(user, hospUser);
			voList.add(vo);
		}
		return voList;
	}
	
	@Override
	public VoUserInfo findVoUserByMobile(String mobile, Integer hospitalId) {
		UserInfo userInfo = userInfoMapper.findByMobile(mobile);
		if (userInfo == null) {
			return null;
		}
		HospitalUserInfo hospUser = null;
		if (hospitalId != null && userInfo != null) {
			hospUser = hospitalUserInfoMapper.findHospUserByUIdHospId(userInfo.getId(), hospitalId);
		}
		return getVoUserInfo(userInfo, hospUser);
	}
	
	private VoUserInfo getVoUserInfo(UserInfo userInfo, HospitalUserInfo hospUser) {
		if (userInfo == null) {
			return null;
		}
		VoUserInfo vo = new VoUserInfo();
		vo.setUserId(userInfo.getId());
		vo.setMobile(userInfo.getMobile());
		vo.setRealName(userInfo.getRealName());
		vo.setBirthday(TimeUtils.dateFormatToString(userInfo.getBirthday(), Const.YYYYMMDD));
		Integer age = (userInfo.getBirthday() != null) ? 
			TimeUtils.getTowDateMinusYear(new Date(), userInfo.getBirthday()) : userInfo.getAge();
		vo.setAge(age);
		vo.setWeight(userInfo.getWeight());
		vo.setHeight(userInfo.getHeight());
		vo.setLastPeriod(TimeUtils.dateFormatToString(userInfo.getLastPeriod(), Const.YYYYMMDD));
		vo.setExpectedDate(TimeUtils.dateFormatToString(userInfo.getExpectedDateOfConfinement(), Const.YYYYMMDD));
		vo.setCurrentIdentity(userInfo.getCurrentIdentity());
		int[] pweek = FunctionUtils.calPregnantWeek(new Date(), userInfo.getExpectedDateOfConfinement());
		vo.setPregnantWeek(pweek);
		String pregnantStage = FunctionUtils.calPregnantStage(pweek[2], userInfo.getCurrentIdentity());
		vo.setPregnantStage(pregnantStage);
		double bmi = FunctionUtils.getBMI(userInfo.getHeight(), userInfo.getWeight());
		vo.setBmi(bmi);
		vo.setWeightStatus(FunctionUtils.getStatusByBmi(bmi));
		//设置医院用户信息
		if (hospUser != null) {
			vo.setPregnantType(hospUser.getPregnantType());
			vo.setOutpatientNum(hospUser.getOutpatientNum());
		}
		return vo;
	}

	@Override
	public int addOrUpdteUser(VoUserInfo vo) {
		UserInfo user = new UserInfo();
		user.setExpectedDateOfConfinement(TimeUtils.stringFormatToDate(vo.getExpectedDate(), Const.YYYYMMDD));
		
		UserExtraInfo userExt = new UserExtraInfo();
		Date birth = TimeUtils.stringFormatToDate(vo.getBirthday(), Const.YYYYMMDD);
		userExt.setUserId(vo.getUserId());
		userExt.setBirthday(birth);
		userExt.setAge(TimeUtils.getTowDateMinusYear(new Date(), birth));
		userExt.setWeight(vo.getWeight());
		userExt.setHeight(vo.getHeight());
		userExt.setRealName(vo.getRealName());
		Date lastPeriod = FunctionUtils.getLastPeriodByExp(TimeUtils.stringFormatToDate(vo.getExpectedDate(), Const.YYYYMMDD));
		userExt.setLastPeriod(lastPeriod);
		Integer userId = vo.getUserId();
		try {
			if (vo.getUserId() != null) {
				//更新
				if (vo.getExpectedDate() != null) {
					user.setId(vo.getUserId());
					userInfoMapper.update(user);
				}
				userExtraInfoMapper.update(userExt);
			} else {
				//添加
				user.setMobile(vo.getMobile());
				String nickName = "天使用户" + RandomStringUtils.random(8, false, true);
				user.setNickName(nickName);
				user.setStatus(1);
				user.setRegTime(new Date());
				user.setIsSwitchPushMsg(1);
				//String pass = vo.getBirthday().replace("-", "");
				String pass = "123456";//默认的初始密码
				user.setPassword(MD5EncryptUtils.getMd5Value(pass));
				Integer num = userInfoMapper.insert(user);
				if (num != 1) {
					logger.info("---------插入UserInfo信息失败！");
					return 0;
				}
				userExt.setUserId(user.getId());
				userExt.setCurrentIdentity((byte) 0);
				userExt.setContactPhone(vo.getMobile());
				userExt.setCommonHospital(vo.getHospitalId());
				userExtraInfoMapper.insert(userExt);
				//插入默认的pregnant_healthy_setting集合
				insertDefaultHealthySet(user.getId());
				//调取发送短信的接口
				String content = String.format(Const.NEW_BOOKBUILD, vo.getMobile(), pass);
				//CommonReturnMsg retMsg = sMSService.sengSMSMsg("体重营养新建档", vo.getMobile(), content);
				Map<String, String> params = SignatureUtils.getSignatureParams(vo.getHospitalId(), vo.getMobile(), content);
				String sign = SignatureUtils.getSignature(params);
				CommonReturnMsg retMsg = sMSService.sendSMSByHb(vo.getHospitalId().longValue(), Long.valueOf(vo.getMobile()), content, sign);
				logger.info("----------建档发送短信的结果：" + retMsg.getMsgbox());
				userId = user.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//插入或更新hospital_user_info
		HospitalUserInfo hospUser = new HospitalUserInfo();
		hospUser.setHospitalId(vo.getHospitalId());
		hospUser.setUserId(userId);
		hospUser.setOutpatientNum(vo.getOutpatientNum());
		hospUser.setPregnantType(vo.getPregnantType());
		hospUser.setAddTime(new Date());
		hospitalUserInfoService.addOrUpdateHospUser(hospUser);
		return userId;
	}

	@Override
	public boolean insertDefaultHealthySet(int userId) {
		int[] project = { 1, 2, 3, 5, 6, 7, 8, 10 };
		List<PregnantHealthySetting> plist = new ArrayList<PregnantHealthySetting>();
		for (int i = 0; i < project.length; i++) {
			PregnantHealthySetting pregnantHealthySetting = new PregnantHealthySetting();
			pregnantHealthySetting.setUserId(userId);
			pregnantHealthySetting.setProject(project[i]);
			pregnantHealthySetting.setState(0);
			pregnantHealthySetting.setAddTime(new Date());
			plist.add(pregnantHealthySetting);
		}
		Integer num = 0;
		try {
			num = pregnantHealthySettingMapper.insertBatch(plist);
			if (num == 0) {
				logger.info("----------插入默认健康设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (num > 0);
	}

}
