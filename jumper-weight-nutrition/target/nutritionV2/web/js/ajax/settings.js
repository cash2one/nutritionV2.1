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
});