package com.jumper.weight.nutrition.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.record.service.UserWeightRecordService;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.record.vo.VoWeightChart;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 体检数据信息controller
 * @Description TODO
 * @author fangxilin
 * @date 2017-4-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@RestController
@RequestMapping("/physical")
@Api(value = "/physical", description = "体检数据信息")
public class PhysicalController extends BaseController {
	
	@Autowired
	private UserWeightRecordService userWeightRecordService;
	
	/**
	 * 获取用户最近一条体检数据
	 * @createTime 2017-5-5,下午1:59:50
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findUserLastphysical")
	@ApiOperation(value = "获取用户最近一条体检数据", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户最近一条体检数据", position = 1)
	public ReturnMsg findUserLastphysical(@ApiParam(value = "用户id") @RequestParam int userId) {
		try {
			VoUserWeightRecord data = userWeightRecordService.findUserLastWeight(userId);
			//Object data = (user != null) ? user : new ArrayList<>();
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户最近一条体检数据成功", data);
		} catch (Exception e) {
			logger.error("获取用户最近一条体检数据异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户最近一条体检数据异常");
		}
	}
	
	/**
	 * 获取用户体重曲线图数据
	 * @createTime 2017-5-5,下午2:58:09
	 * @createAuthor fangxilin
	 * @param userId
	 * @param type
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getWeightChartData")
	@ApiOperation(value = "获取用户体重曲线图数据", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户体重曲线图数据", position = 2)
	public ReturnMsg getWeightChartData(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "获取类型（0：近7天，1：所有）") @RequestParam int type) {
		try {
			VoWeightChart data = userWeightRecordService.getWeightChartData(userId, type);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户体重曲线图数据成功", data);
		} catch (Exception e) {
			logger.error("获取用户体重曲线图数据异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户体重曲线图数据异常");
		}
	}
	
	/**
	 * 获取报告中用户体重曲线图数据
	 * @createTime 2017-5-5,下午2:58:09
	 * @createAuthor fangxilin
	 * @param userId
	 * @param type
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getRepWeiChartData")
	@ApiOperation(value = "获取报告中用户体重曲线图数据", httpMethod = "POST", response = ReturnMsg.class, notes = "获取报告中用户体重曲线图数据", position = 2)
	public ReturnMsg getRepWeiChartData(@ApiParam(value = "报告id") @RequestParam int reportId) {
		try {
			VoWeightChart data = userWeightRecordService.getRepWeiChartData(reportId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取报告中用户体重曲线图数据成功", data);
		} catch (Exception e) {
			logger.error("获取报告中用户体重曲线图数据异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取报告中用户体重曲线图数据异常");
		}
	}
}
