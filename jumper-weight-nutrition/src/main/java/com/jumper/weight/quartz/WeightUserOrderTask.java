package com.jumper.weight.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jumper.weight.common.enums.WeightExceptionType;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.user.entity.UserInfo;
import com.jumper.weight.nutrition.user.entity.WeightUserOrder;
import com.jumper.weight.nutrition.user.mapper.UserInfoMapper;
import com.jumper.weight.nutrition.user.mapper.WeightUserOrderMapper;

/**
 * 体重营养用户定时器任务
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-5
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Component
public class WeightUserOrderTask {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private WeightUserOrderMapper weightUserOrderMapper;
	
	/**
	 * 同步用户信息到排序表
	 * @createTime 2017-5-6,下午8:49:42
	 * @createAuthor fangxilin
	 */
	@Scheduled(cron="0 0/3 * * * ?")   //每3分钟执行一次
	public void synchorWeightUser() {
		try {
			List<UserInfo> userList = userInfoMapper.listOutpUsersInfo();
			if (ArrayUtils.isEmpty(userList)) {
				logger.info("--------门诊用户信息列表为空");
				return;
			}
			List<WeightUserOrder> addList = new ArrayList<WeightUserOrder>();
			List<WeightUserOrder> updList = new ArrayList<WeightUserOrder>();
			List<WeightUserOrder> orderList = weightUserOrderMapper.listAll();
			WeightUserOrder bean = null;
			
			for (UserInfo user : userList) {
				//设置属性为空时的默认值
				user = initUser(user);
				bean = new WeightUserOrder();
				WeightUserOrder updBean = null;//是否更新的对象
				for (WeightUserOrder orUser : orderList) {
					if (user.getId().intValue() == orUser.getUserId()) {
						updBean = orUser;
						break;
					}
				}
				
				//设置未称重类型
				int weightType = 0;
				int day = TimeUtils.getTowDateMinusDay(new Date(), user.getLastWeightTime());
				if (day <= WeightExceptionType.neven.getDay()) {
					weightType = WeightExceptionType.neven.getType();
				} else if (day < WeightExceptionType.normal.getDay()) {
					weightType = WeightExceptionType.normal.getType();
				} else if (day < WeightExceptionType.three.getDay()) {
					weightType = WeightExceptionType.three.getType();
				} else if (day < WeightExceptionType.five.getDay()) {
					weightType = WeightExceptionType.five.getType();
				} else if (day < WeightExceptionType.seven.getDay()) {
					weightType = WeightExceptionType.seven.getType();
				} else {
					weightType = WeightExceptionType.fourteen.getType();
				}
				double bmi = FunctionUtils.getBMI(user.getHeight(), user.getWeight());//孕前bmi
				double currBmi = FunctionUtils.getBMI(user.getHeight(), user.getCurrentWeight());//当前bmi
				//获取上次称体重时对应的孕周
				int[] weiPweek = FunctionUtils.calPregnantWeek(user.getLastWeightTime(), user.getExpectedDateOfConfinement());
				//获取上次秤体重状态
				double[] safeWeight = FunctionUtils.getSafeWeight(bmi, user.getWeight(), weiPweek[2]);
				int weightStatus = FunctionUtils.getStatusByWeight(safeWeight, user.getCurrentWeight());
				int[] pweek = FunctionUtils.calPregnantWeek(new Date(), user.getExpectedDateOfConfinement());
				//更新的判断
				boolean f1 = false, f2 = false, f3 = false, f4 = false, f5 = false, f6 = false, f7 = false, f8 = false, f9 = false, f10 = false, f11 = false, f12 = false;
				if (updBean != null) {
					f1 = TimeUtils.isSameDay(user.getExpectedDateOfConfinement(), updBean.getExpectedDate());
					f2 = updBean.getCurrentWeight().equals(user.getCurrentWeight());
					f3 = TimeUtils.isSameDay(user.getBirthday(), updBean.getBirthday());
					f4 = updBean.getHeight().equals(user.getHeight());
					f5 = updBean.getMobile().equals(user.getMobile());
					f6 = updBean.getRealName().equals(user.getRealName());
					f7 = updBean.getWeight().equals(user.getWeight());
					f8 = updBean.getWeightExceptionType() == weightType;
					f9 = updBean.getpWeek() == pweek[2];
					f10 = updBean.getLastWeightTime().compareTo(user.getLastWeightTime()) == 0;
					f11 = updBean.getWeightStatus() == weightStatus;//时间和体重没变时，体重状态也不会变
					if (f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8 && f9 && f10 && f11) {
						continue;
					}
					f12 = updBean.getCurrentBmi() == currBmi;//身高和当前体重没有变时，当前bmi也不会变
					bean.setId(updBean.getId());
				}
				bean.setExpectedDate((f1) ? null : user.getExpectedDateOfConfinement());
				bean.setCurrentWeight((f2) ? null : user.getCurrentWeight());
				bean.setBirthday((f3) ? null : user.getBirthday());
				bean.setHeight((f4) ? null : user.getHeight());
				bean.setMobile((f5) ? null : user.getMobile());
				bean.setRealName((f6) ? null : user.getRealName());
				bean.setWeight((f7) ? null : user.getWeight());
				bean.setWeightExceptionType((f8) ? null : weightType);//取枚举值未称重类型
				bean.setpWeek((f9) ? null : pweek[2]);
				bean.setLastWeightTime((f10) ? null : user.getLastWeightTime());
				bean.setWeightStatus((f11) ? null : weightStatus);
				bean.setCurrentBmi((f12) ? null : currBmi);
				bean.setCurrentBmi(FunctionUtils.getBMI(user.getHeight(), user.getCurrentWeight()));
				if (updBean != null) {
					updList.add(bean);
				} else {
					bean.setUserId(user.getId());
					addList.add(bean);
				}
			}
			
			//添加或更新排序表
			int num1 = 1, num2 = 1;
			if (ArrayUtils.isNotEmpty(addList)) {
				num1 = weightUserOrderMapper.insertBatch(addList);
			}
			if (ArrayUtils.isNotEmpty(updList)) {
				num2 = weightUserOrderMapper.updateBatch(updList);
			}
			//判断是否成功
			if (num1 < 1 || num2 <1) {
				logger.info("------------同步用户信息到排序表失败");
			}
			
		} catch (Exception e) {
			logger.error("---------同步用户信息到排序表异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 对user可能为空的属性赋初始值，防止插入时报错
	 * @createTime 2017-5-8,下午2:50:38
	 * @createAuthor fangxilin
	 * @param user
	 * @return
	 */
	private UserInfo initUser(UserInfo user) {
		Date dt = TimeUtils.stringFormatToDate("1900-01-01", Const.YYYYMMDD);
		Date expectedDate = (user.getExpectedDateOfConfinement() != null) ? user.getExpectedDateOfConfinement() : dt;
		user.setExpectedDateOfConfinement(expectedDate);
		double currentWeight = (user.getCurrentWeight() != null) ? user.getCurrentWeight() : 0D;
		user.setCurrentWeight(currentWeight);
		Date birthday = (user.getBirthday() != null) ? user.getBirthday() : dt;
		user.setBirthday(birthday);
		Integer height = (user.getHeight() != null) ? user.getHeight() : 0;
		user.setHeight(height);
		String realName = (user.getRealName() != null) ? user.getRealName() : "";
		user.setRealName(realName);
		double weight = (user.getWeight() != null) ? user.getWeight() : 0D;
		user.setWeight(weight);
		return user;
	}
	
}
