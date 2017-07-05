package com.jumper.weight.nutrition.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.diet.mapper.DietSurveyMapper;
import com.jumper.weight.nutrition.recipes.mapper.UserRecipesPlanMapper;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.report.mapper.WeightReportMapper;
import com.jumper.weight.nutrition.sport.mapper.SportSurveyMapper;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.entity.HospitalUserManage;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;
import com.jumper.weight.nutrition.user.mapper.HospitalUserManageMapper;
import com.jumper.weight.nutrition.user.service.HospitalUserManageService;
import com.jumper.weight.nutrition.user.vo.VoHospitalUserManage;
import com.jumper.weight.nutrition.user.vo.VoQueryUserManage;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;
import com.jumper.weight.nutrition.user.vo.VoWeightChartSta;

@Service
public class HospitalUserManageServiceImpl extends	BaseServiceImpl<HospitalUserManage> implements HospitalUserManageService {

	@Autowired
	private HospitalUserManageMapper hospitalUserManageMapper;
	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;
	@Autowired
	private UserRecipesPlanMapper userRecipesPlanMapper;
	@Autowired
	private SportSurveyMapper sportSurveyMapper;
	@Autowired
	private DietSurveyMapper dietSurveyMapper;
	@Autowired
	private WeightReportMapper weightReportMapper;
	
	@Override
	protected BaseMapper<HospitalUserManage> getDao() {
		return hospitalUserManageMapper;
	}

	@Override
	public PageInfo<VoHospitalUserManage> listUserManageByPage(VoQueryUserManage voQuery) {
		PageHelper.startPage(voQuery.getPage(), voQuery.getLimit());
		List<HospitalUserManage> userMaList = hospitalUserManageMapper.listUserManageByPage(voQuery);
		PageInfo pageInfo = new PageInfo(userMaList);
		if (ArrayUtils.isEmpty(userMaList)) {
			return pageInfo;
		}
		List<VoHospitalUserManage> returnList = new ArrayList<VoHospitalUserManage>();
		for (HospitalUserManage um : userMaList) {
			VoHospitalUserManage vo = new VoHospitalUserManage();
			vo.setId(um.getId());
			//设置用户信息
			VoUserInfo user = new VoUserInfo();
			user.setUserId(um.getUserId());
			user.setRealName(um.getRealName());
			int[] pweek = {um.getpWeek() / 7, um.getpWeek() % 7};
			user.setPregnantWeek(pweek);
			user.setExpectedDate(TimeUtils.dateFormatToString(um.getExpectedDate(), Const.YYYYMMDD));
			user.setHeight(um.getHeight());
			user.setWeight(um.getWeight());
			user.setAge(TimeUtils.getTowDateMinusYear(new Date(), um.getBirthday()));
			user.setMobile(um.getMobile());
			vo.setVoUserInfo(user);
			//设置体重信息
			VoUserWeightRecord record = new VoUserWeightRecord();
			record.setAverageValue(um.getCurrentWeight());
			//record.setBmi(um.getCurrentBmi());
			//record.setWeightStatus(FunctionUtils.getStatusByBmi(um.getCurrentBmi()));
			record.setWeightStatus(um.getWeightStatus());
			record.setAddTime(TimeUtils.dateFormatToString(um.getLastWeightTime(), Const.YYYYMMDD));
			vo.setVoWeightRecord(record);
			returnList.add(vo);
		}
		pageInfo.setList(returnList);
		return pageInfo;
	}

	@Override
	public boolean saveUserManage(int hospitalId, int userId) throws Exception {
		HospitalUserManage userManage = hospitalUserManageMapper.findByHospUser(hospitalId, userId);
		HospitalUserManage bean = new HospitalUserManage();
		bean.setLastOutpatientTime(new Date());
		int num = 0;
		if (userManage == null) {
			bean.setAddTime(new Date());
			bean.setHospitalId(hospitalId);
			bean.setUserId(userId);
			num = hospitalUserManageMapper.insert(bean);
		} else {
			bean.setId(userManage.getId());
			num = hospitalUserManageMapper.update(bean);
		}
		return (num > 0);
	}

	@Override
	public VoWeightChartSta getWeightStatistics(int hospitalId) {
		List<HospitalUserManage> uMlist = hospitalUserManageMapper.listUmByDuring(hospitalId, null, null);
		int lowWeightNum = 0, highWeightNum = 0, excWeightNum = 0;
		for (HospitalUserManage um : uMlist) {
			if (um.getWeightStatus() == 0) {
				lowWeightNum++;
			} else if (um.getWeightStatus() == 2) {
				highWeightNum++;
			}
		}
		VoWeightChartSta vo = new VoWeightChartSta();
		excWeightNum = lowWeightNum + highWeightNum;
		vo.setLowWeightNum(lowWeightNum);
		vo.setHighWeightNum(highWeightNum);
		vo.setExcWeightNum(excWeightNum);
		return vo;
	}

	@Override
	public List<VoWeightChartSta> getWeightChartSta(int hospitalId, String startDate, String endDate) {
		List<HospitalUserManage> uMlist = hospitalUserManageMapper.listUmByDuring(hospitalId, startDate, endDate);
		List<Date> dateList = TimeUtils.getDatesBetweenTwoDate(TimeUtils.stringFormatToDate(startDate, Const.YYYYMMDD), TimeUtils.stringFormatToDate(endDate, Const.YYYYMMDD));
		List<VoWeightChartSta> returnList = new ArrayList<VoWeightChartSta>();
		VoWeightChartSta vo = null;
		for (Date date : dateList) {
			vo = new VoWeightChartSta();
			String dateStr = TimeUtils.dateFormatToString(date, Const.YYYYMMDD);
			vo.setDate(dateStr);
			int lowWeightNum = 0, highWeightNum = 0, excWeightNum = 0;
			for (HospitalUserManage um : uMlist) {
				if (!TimeUtils.isSameDay(date, um.getLastWeightTime())) {
					continue;
				}
				if (um.getWeightStatus() == 0) {
					lowWeightNum++;
				} else if (um.getWeightStatus() == 2) {
					highWeightNum++;
				}
			}
			excWeightNum = lowWeightNum + highWeightNum;
			vo.setLowWeightNum(lowWeightNum);
			vo.setHighWeightNum(highWeightNum);
			vo.setExcWeightNum(excWeightNum);
			returnList.add(vo);
		}
		return returnList;
	}

	@Override
	public boolean deleteUserManage(int hospitalId, int userId) {
		try {
			//删除孕妇管理表
			hospitalUserManageMapper.deleteByHospUId(hospitalId, userId);
			List<HospitalOutpatient> outpList = hospitalOutpatientMapper.listUserOutpatient(hospitalId, userId, null);
			List<Integer> ids = new ArrayList<Integer>();
			//删除所有门诊记录，方案，运动调查，膳食调查以及报告
			for (HospitalOutpatient outp : outpList) {
				ids.add(outp.getId());
				userRecipesPlanMapper.deleteUserRecipesPlans(outp.getId());
				sportSurveyMapper.deleteByOutpId(outp.getId());
				dietSurveyMapper.deleteUserDietSurveyByOutpId(outp.getId());
				//删除报告
				weightReportMapper.deleteWeightReportByOutpatientId(outp.getId());
			}
			//删除门诊记录
			hospitalOutpatientMapper.deleteBatch(ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
