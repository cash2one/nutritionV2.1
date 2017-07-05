package com.jumper.weight.nutrition.hospital.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.GenerateQRCode;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalSettingService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/settings")
@Api(value = "/settings", description = "医院设置")
public class SettingsController extends BaseController {
	
	@Autowired
	private WeightHospitalSettingService weightHospitalSettingService;
	
	/**
	 * 生成二维码
	 * @createTime 2017-5-23,上午11:43:55
	 * @createAuthor fangxilin
	 * @param response
	 * @param hospitalId
	 * @throws IOException
	 */
	@RequestMapping("/v1.0/generateQRCode")
	public void generateQRCode(HttpServletResponse response,
			@RequestParam(value = "hospitalId") int hospitalId) throws IOException {
		//设置页面不缓存  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        //设置输出的内容的类型为png图像  
        response.setContentType("image/png");
        String baseUrl=Consts.BASE_PATH + "/mobile/checkPage.html";
        logger.debug("baseUrl:"+baseUrl);
        String encodeUrl = URLEncoder.encode(baseUrl, "UTF-8");
        encodeUrl.toLowerCase();
        logger.debug("encodeUrl:" + encodeUrl);
        //owneId=0，表示是天使用户
        String content = Consts.USER_PORTAL_URL + "?hospitalId=" + hospitalId + "&owneId=0&url=" + encodeUrl;
        logger.debug(content);
        String filePath = GenerateQRCode.generate(content);
        File file = new File(filePath);
        BufferedImage bufferedImage = ImageIO.read(file);
        //写给浏览器  
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
        //最后删除临时文件
        FileUtils.deleteQuietly(file);
	}
	
	@RequestMapping("/generateQRCode")
	public void getQRCode(HttpServletResponse response,
			@RequestParam(value = "hospitalId") int hospitalId) throws Exception {
		//设置页面不缓存  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        //设置输出的内容的类型为png图像  
        response.setContentType("image/png");
        String baseUrl=Consts.BASE_PATH + "/mobile/checkPage.html";
        logger.debug("baseUrl:"+baseUrl);
        String encodeUrl = URLEncoder.encode(baseUrl, "UTF-8");
        encodeUrl.toLowerCase();
        logger.debug("encodeUrl:"+encodeUrl);
        //owneId=0，表示是天使用户
        String content = Consts.USER_PORTAL_URL + "?hospitalId=" + hospitalId + "&owneId=0&url=" + encodeUrl;
        logger.debug(content);
        GenerateQRCode.generate(content,response.getOutputStream());
	}
	
	/**
	 * 下载二维码
	 * @createTime 2017-5-23,下午12:02:07
	 * @createAuthor fangxilin
	 * @param response
	 * @param hospitalId
	 * @throws IOException
	 */
	@RequestMapping("/downloadQRCode")
	public void downloadQRCode(HttpServletResponse response,
			@RequestParam(value = "hospitalId") int hospitalId) throws IOException {
		String fileName = "二维码.png";
		String encodeUrl = URLEncoder.encode(Consts.BASE_PATH + "/mobile/checkPage.html", "UTF-8");
		//owneId=0，表示是天使用户
        String content = Consts.USER_PORTAL_URL + "?owneId=0&hospitalId=" + hospitalId + "&url=" + encodeUrl;
        String filePath = GenerateQRCode.generate(content);
        OutputStream outStream = null;
        InputStream inStream = null;
        try {
	    	inStream = new BufferedInputStream(new FileInputStream(filePath));
		    byte[] buffer = new byte[inStream.available()];
		    inStream.read(buffer);
		    response.reset();
		    //去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		    response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso-8859-1"));
		    outStream = new BufferedOutputStream(response.getOutputStream());
		    response.setContentType("application/octet-stream");
		    outStream.write(buffer);// 输出文件
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inStream, outStream);
			 /** 最后将临时文件删除 **/
        	File file = new File(filePath);
        	FileUtils.deleteQuietly(file);
		}
	}
	
	/**
	 * 获取医院设置
	 * @createTime 2017-6-22,上午10:34:38
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findSettingByHospId")
	@ApiOperation(value = "获取医院设置", httpMethod = "POST", response = ReturnMsg.class, notes = "获取医院设置", position = 1)
	public ReturnMsg findSettingByHospId(@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			WeightHospitalSetting data = weightHospitalSettingService.findSettingByHospId(hospitalId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取医院设置成功", data);
		} catch (Exception e) {
			logger.error("获取医院设置异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取医院设置异常");
		}
	}
	
	/**
	 * 保存医院设置
	 * @createTime 2017-6-22,上午11:24:46
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param hideDiet
	 * @param hideSport
	 * @param hideFoodtab
	 * @param hideExchange
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveSetting")
	@ApiOperation(value = "保存医院设置", httpMethod = "POST", response = ReturnMsg.class, notes = "保存医院设置", position = 1)
	public ReturnMsg saveSetting(@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "隐藏膳食调查（0：否，1：是）") @RequestParam int hideDiet,
			@ApiParam(value = "隐藏运动调查（0：否，1：是）") @RequestParam int hideSport,
			@ApiParam(value = "隐藏食物表（0：否，1：是）") @RequestParam int hideFoodtab,
			@ApiParam(value = "隐藏交换份（0：否，1：是）") @RequestParam int hideExchange) {
		try {
			WeightHospitalSetting setting = new WeightHospitalSetting(hospitalId, hideDiet, hideSport, hideFoodtab, hideExchange);
			boolean ret = weightHospitalSettingService.saveSetting(setting);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "保存医院设置失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "保存医院设置成功");
		} catch (Exception e) {
			logger.error("保存医院设置异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "保存医院设置异常");
		}
	}
}
