/**
 * 统计页面
 */
$(function() {
	$.post(basePath + "/outpatient/getOutpStatistics", {hospitalId : hospitalId}, function(ret) {
		if (ret.msg == 1) {
			/*$('#Num_1').animateNumber({number: ret.data.dayFirstOutPNum, 'font-size': '50px', easing: 'easeInQuad'}, 800);
	        $('#Num_2').animateNumber({number: ret.data.monthFirstOutPNum, 'font-size': '50px', easing: 'easeInQuad'}, 800);
	        $('#Num_3').animateNumber({number: ret.data.dayOutPNum, 'font-size': '50px', easing: 'easeInQuad'}, 800);
	        $('#Num_4').animateNumber({number: ret.data.monthOutPNum, 'font-size': '50px', easing: 'easeInQuad'}, 800);*/
	        $('#Num_1').text(ret.data.dayFirstOutPNum);
	        $('#Num_2').text(ret.data.monthFirstOutPNum);
	        $('#Num_3').text(ret.data.dayOutPNum);
	        $('#Num_4').text(ret.data.monthOutPNum);
		} else {
			layer.msg(ret.msgbox, {time : 1000});
		}
	});
});