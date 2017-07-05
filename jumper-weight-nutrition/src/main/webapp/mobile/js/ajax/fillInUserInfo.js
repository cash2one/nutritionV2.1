//页面所需后台的数据
$(function() {
	var data = {hospitalId : hospitalId, userId : userId};
	//加载用户信息
	$.post(basePath + "/user/findUserByIdMobile", data, function(ret) {
		if (ret.msg == 1) {
			//alert(JSON.stringify(ret));
			$(".birthday").text(ret.data.birthday);
			birthday = ret.data.birthday;//赋值，生日弹框默认值要用到
			$("input[name='weight']").val(ret.data.weight);
			$("input[name='height']").val(ret.data.height);
			$(".pregnantType").text(CONST.pregnantType[ret.data.pregnantType]);
			$("input[name='outpatientNum']").val(ret.data.outpatientNum);
		} else {
			mui.toast(ret.msgbox);
		}
	}, "json");

	//下一步，数据验证并且保存跳转
	$(".nextStep").click(function() {
		if ($(".birthday").text() == "") {
			mui.toast("请输入生日");
			return;
		}
		var weight = $("input[name='weight']").val();
		if (weight == "") {
			mui.toast("请输入正确的孕前体重");
			return;
		}
		if (weight < 20 || weight > 150) {
			mui.toast("孕前体重不在20~150kg");
			return;
		}
		var currWeight = $("input[name='averageValue']").val();
		if (currWeight == "") {
			mui.toast("请输入正确的目前体重");
			return;
		}
		if (currWeight < 20 || currWeight > 150) {
			mui.toast("目前体重不在20~150kg");
			return;
		}
		var height = $("input[name='height']").val();
		if (height == "") {
			mui.toast("请输入身高");
			return;
		}
		if (height < 50 || height > 200) {
			mui.toast("身高不在50~200cm");
			return;
		}
		if ($(".pregnantType").text() == "") {
			mui.toast("请输入怀孕类型");
			return;
		}
		/*if ($("input[name='outpatientNum']").val() == "") {
			mui.toast("请输入就诊卡号");
			return;
		}*/
		/*if (!/^[0-9a-zA-Z]+$/.test($("input[name='outpatientNum']").val())) {
			mui.toast("请输入正确的就诊卡号");
			return;
		}*/
		//怀孕类型
		var pregnantType = 0;
		for (var i = 0; i < CONST.pregnantType.length; i++) {
			if ($(".pregnantType").text() == CONST.pregnantType[i]) {
				pregnantType = i;
			}
		}
		var userParams = {
			userId : userId,
			hospitalId : hospitalId,
			birthday : $(".birthday").text(),
			weight : $("input[name='weight']").val(),
			height : $("input[name='height']").val(),
			pregnantType : pregnantType,
			outpatientNum : $("input[name='outpatientNum']").val().trim(),
			isAddOutp : 0
		}
		var physicalParams = {averageValue : $("input[name='averageValue']").val()};
		var postData = {userParams : JSON.stringify(userParams), physicalParams : JSON.stringify(physicalParams)};
		//保存数据
		$.post(basePath + "/user/addOrUpdateUserInfo", postData, function(ret) {
			if (ret.msg == 1) {
				//保存成功并跳转
				window.location.href = "dietSurvey.html";
			} else {
				mui.toast(ret.msgbox);
			}
		}, "json");
		//var data = $("#postForm").serialize();
	});
});
