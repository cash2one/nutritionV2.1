/** 菜单栏公共跳转js */
$(function() {
	//门诊
	$("#outpatient").click(function() {
		window.location.href = "outpatient.html?hospitalId=" + hospitalId;
	});
	//孕妇管理
	$("#userManage").click(function() {
		window.location.href = "userManage.html";
	});
	//配置食谱
	$("#standardRecipes").click(function(){
		window.location.href = "standardRecipes.html";
	});
	//统计
	$("#statistics").click(function() {
		window.location.href = "statistics.html";
	});
	//设置
	$("#settings").click(function(){
		window.location.href = "settings.html";
	});
	//返回按钮
    $(".go-back").on("click",function(){
    	history.back(); //返回上一页
    });
    
    //用户信息页面--体重、饮食记录
    $("#user-dietRecord").click(function() {
    	window.location.href = "viewUserInformation.html?userId=" + userId + "&outpatientId=" + outpatientId + "&menuType=" + menuType;
	});
    //用户信息页面--运动记录 
    $("#user-sportRecord").click(function() {
		window.location.href = "userSportRecord.html?userId=" + userId + "&outpatientId=" + outpatientId + "&menuType=" + menuType;
	});
    //用户信息页面--目前方案
    $("#user-currentPlan").click(function() {
		window.location.href = "currentPlan.html?userId=" + userId + "&outpatientId=" + outpatientId + "&menuType=" + menuType;
	});
    //用户信息页面--诊疗历史
    $("#user-treatHistory").click(function() {
		window.location.href = "treatHistory.html?userId=" + userId + "&outpatientId=" + outpatientId + "&menuType=" + menuType;
	});
    //复诊完成诊断
    $(document).on("click","#completeDiagnosis",function(){
    	var doctorAdvice = $("#doctorAdvice").val();
    	$.ajax({
     		url: basePath+"/outpatient/updateOutpatient",
     		type: "POST",
     		dataType: "json",
     		data: {outpatientId:outpatientId,isFinish:1,doctorAdvice:doctorAdvice},
     		async:false,
     		success:function(json){
     			if(json.msg == 1){
     				//返回门诊页面
     				window.location.href = "outpatient.html?hospitalId=" + hospitalId;
     			}else{
     				layer.msg(json.msgbox,{time:1000});
     			}
     		}
     	});
    });
    
    //回车键触发弹框确定取消事件
    $(document).keydown(function() {
    	var key=event.keyCode; 
    	switch(key) {
	  	case 13: // 回车按钮
	  		if($(".layui-layer").hasClass("layui-layer")){
	  			$(".layui-layer-btn0").click();
	  		}
	  		break;
		case 27: // Esc按钮，去掉弹框
			if($(".layui-layer").hasClass("layui-layer")) {
	  			$(".layui-layer-close").click();
	  		}
    	} 
	});
    /*if(document.documentElement.clientWidth<1600){
   	 $(".container-right").width(window.screen.width);
   }*/
    setTimeout(function(){
   	 //表格hover样式
       tableHover($(".layui-table tbody").find("tr"),'#fff','#f4f4f4','#DCEFFA');
       tableHover($('.daoruList li'),'#fff','#f4f4f4','#DCEFFA');
   },1000);
});

function isShowAdvice(){
	//复诊显示医生建议和完成诊断
	if(menuType == CONST.menuType[0]){
    	$("#adviceDiv").css("display","block");
    	$("#finishDiv").css("display","block");
    	$("#modifyPlanDiv").css("display","block");//修改方案按钮
    	$("#mealsInfo").css("margin-bottom","20px");//门诊时距离底部20px
    	//切换门诊菜单和孕妇管理菜单
    	$("#outpatient").addClass("active");
    	$("#userManage").removeClass("active");
    }
}
//table 交互样式
function tableHover(target,color1,color2,color3){
    var  atr=target;
    var  arr= [color1,color2];
    for (var i=0;i<atr.length;i++){
        atr[i].index=i;
        atr[i].style.background=arr[i%arr.length];
        atr[i].onmouseover=function(){this.style.background=color3;}
        atr[i].onmouseout=function(){this.style.background=arr[this.index%arr.length]; }
    }
}

/**
 * 文本域字数统计
 */
function countDietAdvice(textareaName,spanName)
{
    var textNam = document.getElementById(textareaName);
    var spanNam=  document.getElementById(spanName);
    if(textNam.value.length >= 300){
        textNam.value=textNam.value.substring(0, 300);
    }
    spanNam.innerHTML =textNam.value.length;
}

function keyDown(event) {
	var inputs=document.getElementsByTagName("INPUT");
	var inputsText = new Array();
	for(var i=0; i<inputs.length; i++){
		if(inputs[i].type=="text"){
			inputsText.push(inputs[i]);
		}
	}
	var focus=document.activeElement; 
	var key=event.keyCode; 
//  if(!document.getElementById("table-food-list").contains(focus)) return;
	var event=window.event||event;
	for(var i=0; i<inputsText.length; i++){
		if(inputsText[i]===focus) break; 
	}
	switch(key) {
	  	case 13: // 回车按钮
	  		if($(".layui-layer").hasClass("layui-layer")){
	  			$(".layui-layer-btn0").click();
	  		}else{
	  			$(".enterBtnEvent").click();
	  		}
	  		break;
	  	case 38: // ↑按钮
	  		if(i>0) inputsText[i-1].focus();
	  		break; 
	  	case 40: // ↓按钮
	  		if(i <inputsText.length-1) inputsText[i+1].focus();
	  		break;
		case 27: // Esc按钮，去掉弹框
			if($(".layui-layer").hasClass("layui-layer")) {
	  			$(".layui-layer-close").click();
	  		}
	} 
} 

//动态获取window的高度/宽度设置给container层//////////////////////////////////////////////////
/*var heights=$(window).height();
$(".container-left,.container,.container-right").height(heights);
window.onresize = function(){
	heights=$(window).height();
	$(".container-left,.container,.container-right").height(heights);
}*/
//ie6 hover兼容
/*窗口处理*/
//ie6
var isIE = !!window.ActiveXObject;
var isIE6 = isIE && !window.XMLHttpRequest;
if (isIE) {
    window.onresize = function(){
        //动态获取window的高度/宽度设置给container层
        var heights=$(window).height();
        $(".container-left,.container,.container-right").height(heights+60) /*ie6 iframe下 页面底部显示不全*/;
        if (isIE6) {
            var widths=window.screen.width-20;
            if(document.documentElement.clientWidth<1220){
                /*$("body,.head").width(window.screen.width);*/
                /*$(".container-right").width(widths);*/
                $(".con-right-ul ul li").width("24.8%");
            }else{
                /*$("body,.head").width(window.screen.width);*/
               /* $(".container-right").width(widths);*/
                $(".cen-table").width(widths-80);
            }
           
        }
    }
}
