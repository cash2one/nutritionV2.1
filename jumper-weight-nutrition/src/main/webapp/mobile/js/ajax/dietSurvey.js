/**
 * 膳食调查js
 */
 $(document).ready(function(){
    	var param = {userId:userId};
    	$.ajax({
    		url:basePath+"/diet/findUserMealsInfoList",
    		type:"POST",
    		dataType:"json",
    		data:param,
    		success:function(json){
    			var data = json.data;
    			var html = "",breakfast="",breakfastAdd="",lunch="",lunchAdd="",dinner="",dinnerAdd="";
    			for(var i=0;i<data.length;i++){
    				var unitRemark = data[i].unitRemark;
    				if(unitRemark != null && unitRemark != ""){
    					unitRemark = "【"+unitRemark+"】";
    				}else{
    					unitRemark = "";
    				}
    				html = "<li><span>"+data[i].foodName+"</span><span class='fr'>"+data[i].foodWeight+"克"+unitRemark+"</span></li>";
    				if(data[i].mealsType==1){breakfast += html;}
    				if(data[i].mealsType==2){breakfastAdd += html;}
    				if(data[i].mealsType==3){lunch += html;}
    				if(data[i].mealsType==4){lunchAdd += html;}
    				if(data[i].mealsType==5){dinner += html;}
    				if(data[i].mealsType==6){dinnerAdd += html;}
    			}
    			if(breakfast!=""){
    				$("#breakfast").html(breakfast);
    				$("#breakfast").attr("data","1");
    			}
    			if(breakfastAdd!=""){
    				$("#breakfastAdd").html(breakfastAdd);
    				$("#breakfastAdd").attr("data","1");
    			}
    			if(lunch!=""){
    				$("#lunch").html(lunch);
    				$("#lunch").attr("data","1");
    			}
    			if(lunchAdd!=""){
    				$("#lunchAdd").html(lunchAdd);
    				$("#lunchAdd").attr("data","1");
    			}
    			if(dinner!=""){
    				$("#dinner").html(dinner);
    				$("#dinner").attr("data","1");
    			}
    			if(dinnerAdd!=""){
    				$("#dinnerAdd").html(dinnerAdd);
    				$("#dinnerAdd").attr("data","1");
    			}
    			
    		}
    	});
    });
 /**
  * 选择食物跳转搜索页面
  */
 $(document).on("click",".chooseFood",function(){
	 var mealsType = $(this).attr("mealsType");
	 window.location.href = "searchFood.html?mealsType="+mealsType;
 })
 
 /**
  * 点击下一步，跳转运动调查
  */
 $(document).on("click","#diet_survey_next",function(){
	 var breakfast = $("#breakfast").attr("data");
	 var lunch = $("#lunch").attr("data");
	 var dinner = $("#dinner").attr("data");
	 if(breakfast == "0"){
		 mui.toast("请添加早餐食物");
	 }else if(lunch == "0"){
		 mui.toast("请添加午餐食物");
	 }else if(dinner == "0"){
		 mui.toast("请添加晚餐食物");
	 }else{
		 window.location.href = "sportSurvey.html";
	 }
 });
 
 /**
  * 点击跳过
  */
 $(document).on("click","#jumperOver",function(){
	 window.location.href = "sportSurvey.html";
 });
 