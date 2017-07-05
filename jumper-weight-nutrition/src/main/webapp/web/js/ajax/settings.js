/**
 * 设置页面js
 */
$(function() {
	//加载二维码
	$("#QRCode").attr("src", basePath + "/settings/generateQRCode?hospitalId=" + hospitalId);
	
	//下载二维码
	$("#download").click(function() {
		window.location.href = basePath + "/settings/downloadQRCode?hospitalId=" + hospitalId
	});
	//加载设置数据
	$.ajax({
		url:basePath+"/settings/findSettingByHospId",
		type:"post",
		dataType:"json",
		data:{hospitalId:hospitalId},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var hideDiet = json.data.hideDiet;
				var hideSport = json.data.hideSport;
				var hideFoodtab = json.data.hideFoodtab;
				var hideExchange = json.data.hideExchange;
				$("#hideDiet").prop("checked",hideDiet);
				$("#hideSport").prop("checked",hideSport);
				$("#hideFoodtab").prop("checked",hideFoodtab);
				$("#hideExchange").prop("checked",hideExchange);
			}
		}
	});
	
	$("#saveSettings").click(function(){
		var hideDiet = $("#hideDiet").prop("checked")==true?1:0;
		var hideSport = $("#hideSport").prop("checked")==true?1:0;
		var hideFoodtab = $("#hideFoodtab").prop("checked")==true?1:0;
		var hideExchange = $("#hideExchange").prop("checked")==true?1:0;
		var param = {
				hospitalId:hospitalId,
				hideDiet:hideDiet,
				hideSport:hideSport,
				hideFoodtab:hideFoodtab,
				hideExchange:hideExchange
		};
		$.ajax({
			url:basePath+"/settings/saveSetting",
			type:"post",
			dataType:"json",
			data:param,
			async:false,
			success:function(json){
				if(json.msg == 1){
					layer.msg("保存成功",{time:1000});
				}
			}
		});
	});
});