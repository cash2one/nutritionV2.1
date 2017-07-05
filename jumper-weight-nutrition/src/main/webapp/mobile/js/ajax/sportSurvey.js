//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//运动列表赋值
	$.post(basePath + "/sport/listSportsByName", function(ret) {
		if (ret.msg == 1) {
			var html = "";
			for (var int = 0; int < ret.data.length; int++) {
				var sport = ret.data[int];
				html += "<li class='mui-table-view-cell'>" + 
		                	"<span>" + sport.name + "</span><span class='mui-badge mui-badge-primary timeLength' sportId='" + sport.id + "'></span>" +
		                "</li>";
			}
			$(".sportList").prepend(html);
		} else {
			mui.toast(ret.msgbox);
		}
	}, "json");
	
	//查询出昨日的运动信息,并赋初始值
	var yestDay = getDateByDaysLate(new Date(), -1);
	var data = {userId : userId, date : yestDay};
	$.post(basePath + "/sport/listSportRecordByDate", data, function(ret) {
		if (ret.msg != 1) {
			mui.toast(ret.msgbox);
			return;
		}
		for (var int = 0; int < ret.data.recordList.length; int++) {
			var record = ret.data.recordList[int];
			$(".timeLength").each(function(i) {
				if (record.sportId == $(this).attr("sportId")) {
					var minHour = minToHour(record.timeLength);
					var text = (minHour[0] == 0) ? minHour[1] + "分钟" : minHour[0] + "小时" + minHour[1] + "分钟";
					$(this).text(text);
				}
			});
		}
	}, "json");
	
	
	//点击完成
	$(".nextStep").click(function() {
		var recordList = new Array();
		$(".timeLength").each(function(i) {
			var timeLength = hourToMin($(this).text());
			if (timeLength > 0) {
				var record = {sportId : $(this).attr("sportId"), timeLength : timeLength};
				recordList.push(record);
			}
		});
		/*if (recordList.length == 0) {
			mui.toast("请添加运动记录");
			return;
		}*/
		//判断总时长是否超过24小时
		var totalTime = 0;
		for (var i in recordList) {
			totalTime += recordList[i].timeLength;
		}
		if (totalTime >= 24 * 60) {
			mui.toast("总时长不能超过24小时");
			return;
		}
		
		var userDefinedSport = $("input[name='userDefinedSport']").val().trim();
		if (userDefinedSport.length > 30) {
			mui.toast("自定义运动不能超过30个字符");
			return;
		}
		
		//保存运动记录
		var isSuccess = true;
		if (recordList.length > 0) {
			var data = {userId : userId, date : yestDay, recordList : JSON.stringify(recordList)};
			$.post(basePath + "/sport/addSportRecordByDate", data, function(ret) {
				if (ret.msg != 1) {
					isSuccess = false;
					mui.toast(ret.msgbox);
				}
			}, "json");
		}
		if (!isSuccess) {
			return;
		}
		//保存一条门诊记录
		var opData = {hospitalId : hospitalId, userId : userId, userDefinedSport : userDefinedSport};
		$.post(basePath + "/outpatient/addOutpatient", opData, function(ret) {
			if (ret.msg != 1) {
				isSuccess = false;
				mui.toast(ret.msgbox);
			}
		}, "json");
		if (!isSuccess) {
			return;
		}
		//跳转
		window.location.href = "firstCheck.html";
	});
	
});