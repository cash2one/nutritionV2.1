//手机端h5或web端第一个页面赋初始session值
var hospitalId = GetQueryString("hospitalId");
var userId = GetQueryString("userId");
$.cookie('hospitalId', hospitalId);
$.cookie('userId', userId);

//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	document.activeElement.blur();//收回软键盘
	var data = {hospitalId : hospitalId, userId : userId};
	//判断是否是初诊，并返回basePath
	$.post(basePath + "/outpatient/isFirstOutpatient", data, function(ret) {
		if (ret.msg == 1) {
			//$.session.set('basePath', ret.data.basePath);
			if (ret.data.isFirst) {//初诊
				$(".liucheng").show();
				$(".checkInfo").addClass("isFirst");
			} else {
				$(".h4Title").show();
				$(".pText2").show();
				$(".checkInfo").addClass("notFirst");
			}
		} else {
			mui.toast(ret.msgbox);
		}
	}, "json");
	
	//加载用户信息
	$.post(basePath + "/user/findUserByIdMobile", data, function(ret) {
		if (ret.msg == 1) {
			//alert(JSON.stringify(ret));
			$(".hospitalName").text(ret.data.hospitalName);
			$(".realName").text(ret.data.realName);
			$(".mobile").text(ret.data.mobile);
		} else {
			mui.toast(ret.msgbox);
		}
	}, "json");
	
	$(".checkInfo").click(function() {
		if ($(this).hasClass("isFirst")) {
			window.location.href = "fillInUserInfo.html";
		} else {
			//添加一条复诊记录
			var opData = {hospitalId : hospitalId, userId : userId};
			var isSuccess = true;
			$.post(basePath + "/outpatient/addOutpatient", opData, function(ret) {
				if (ret.msg != 1) {
					isSuccess = false;
					mui.toast(ret.msgbox);
				}
			});
			if (!isSuccess) {
				return;
			}
			//跳转
			window.location.href = "repeatCheck.html";
		}
	});
});
