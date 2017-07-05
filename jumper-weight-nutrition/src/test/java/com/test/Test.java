package com.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jumper.weight.common.util.FunctionUtils;

public class Test {

	public static void main(String[] args) throws IOException {
		
		/*String url = "http://192.168.0.2:8080/hospital/hospital/hospitalBase/queryHospitalHomePage";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospital_id", 42);
		HttpClient httpClient = new HttpClient(url, params);
		String ret = httpClient.get();
		System.out.println(ret);*/
		
		/*String bith = "2017-01-01";
		bith = bith.replace("-", "");
		System.out.println(bith.substring(2, 7));*/
		
		//更新userParams : {"birthday":"1993-03-03","expectedDate":"2017-12-22","height":175,"hospitalId":42,"mobile":"18320995792","outpatientNum":"55555666666777788","pregnantType":0,"realName":"小方","userId":11901,"weight":60}
		/*VoUserInfo voUser = new VoUserInfo();
		voUser.setUserId(11901);
		voUser.setMobile("18320995792");
		voUser.setRealName("小方");
		voUser.setBirthday("1993-03-03");
		voUser.setWeight(60D);
		voUser.setHeight(175D);
		voUser.setExpectedDate("2017-12-22");
		voUser.setHospitalId(42);
		voUser.setPregnantType(0);
		voUser.setOutpatientNum("55555666666777788");
		String userParams = JSON.toJSONString(voUser);
		System.out.println(userParams);*/
		String number = "42";
		while(number.length()<5){
			StringBuffer str = new StringBuffer();
			str.append("0").append(number);
			number = str.toString();
		}
		if(number.length()>=6){
			number = number.substring(number.length()-5, number.length());
		}
		System.out.println(number);
        Date date = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");  
        number += sdf.format(date);  
        System.out.println(number);  
        
        System.out.println(58D/800D*100D);
//        System.out.println(FunctionUtils.setDecimal(58f/800*100, 1));
        System.out.println(58D/700);
        Double f = 58D/800*100;
        System.out.println(f.floatValue()+"********");
        
        BigDecimal b = new BigDecimal(58f/800*100);
		float result = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		System.out.println(result);
		System.out.println("123");
	}
}
