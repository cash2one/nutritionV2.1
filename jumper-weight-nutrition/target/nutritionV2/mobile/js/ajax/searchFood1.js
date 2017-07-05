/**
 * 膳食调查js
 */
var input_width = 300;
 
$(document).ready(function(){
	var width = $("#search-food-input").css("width");
	input_width = parseInt(width.substring(0,width.length-2));
	var mealsType = GetQueryString("mealsType");
	var param = {
			userId:userId,
			mealsType:mealsType
	};
	$.ajax({
		url:basePath+"/diet/findUserMealsInfoList",
		type:"POST",
		dataType:"json",
		data:param,
		success:function(json){
			var data = json.data;
			var html = "";
			for(var i=0;i<data.length;i++){
				var unitRemark = data[i].unitRemark;
				if(unitRemark != null && unitRemark != ""){
					unitRemark = "【"+unitRemark+"】";
				}else{
					unitRemark = "";
				}
				html += '<a class="addFoodList"><span class="addFoodName">'+data[i].foodName+'</span><span class="addNum">'+data[i].foodWeight+'克'+unitRemark+'</span><span class="removeMenu fr red mui-icon mui-icon-trash" mealsId="'+data[i].id+'" foodId="'+data[i].foodId+'" foodName="'+data[i].foodName+'" foodWeight="'+data[i].foodWeight+'" calorie="'+data[i].calorie+'" onclick="removeP(this)"></span></a>';
			}
			if(html != ""){
				$("#foodList").append(html);
	            $(".searchBoxText").html('可以继续添加食材 ~');
	            menuLength();
	            if (searchBox.style.opacity==0){searchBoxShow();}
			}
			
		}
	});
});

var remark = "";
//查找食物自动补全
$("#search-food-input").autocomplete(basePath+"/diet/searchFood",{
	dataType:"json",
	minChars:1,
	max:15,
	autoFill:false,
	matchContains:true,
	width:input_width,
	scrollHeight:330,
	parse:function(data){
		return $.map(data,function(row){
			return {
				data:row,
				value:row.name,
				result:row.name
			};
		});
	},
	formatItem:function(row,i,max){
		if(row.unitRemark != null && row.unitRemark != ""){
			remark = "【"+row.unitRemark+"】";
		}else{
			remark = "";
		}
		return row.name+remark;
	}
}).result(function(e,row){
	var btnArray = ['取消', '确定'];
    if (aName.length==0){
        prompt(row.id,row.name,btnArray,remark,row.calorie);
    }else {
        var success=false;
        $(".addFoodName").each(function (){if(row.name == $(this).text()){success = true;return false;}});
        success?mui.toast("有这个食材了"):prompt(row.id,row.name,btnArray,remark,row.calorie);
    }
    setTimeout(function(){
    	$(".inputNum").focus();
    },200);
});
/**
 * 点击搜索框时隐藏已添加食材
 */
$("#search-food-input").on("tap",function(){
	searchBox.style.bottom="0";
    searchBox.style.opacity=0;
    arrow.style.transform="rotate(180deg)";
    flag =true;
});

$("#saveMealsInfo").on("click",function(){
	var mealsMsg = new Array();
	var mealsType = GetQueryString("mealsType");
	$(".addFoodList").each(function(){
		var html_span = $(this).find(".removeMenu");
		var mealsId = html_span.attr("mealsId");
		var foodId = html_span.attr("foodId");
		var foodName = html_span.attr("foodName");
		var foodWeight = html_span.attr("foodWeight");
		var calorie = html_span.attr("calorie");
		var msg = {
			mealsType:mealsType,
			foodId:foodId,
			foodName:foodName,
			foodWeight:foodWeight,
			calorie:calorie
		};
		mealsMsg.push(msg);
	});
	if(JSON.stringify(mealsMsg) == "[]"){
		window.location.href = "dietSurvey.html";
	}else{
		var param = {
				userId:userId,
				eatDate:getDateByDaysLate(new Date(),-1),
				mealsMsg:JSON.stringify(mealsMsg)
		};
		$.ajax({
			url:basePath+"/diet/saveUserMealsInfo",
			type:"POST",
			dataType:"json",
			data:param,
			success:function(json){
				if(json.msg==1){
					window.location.href = "dietSurvey.html";
				}else if(json.msg==0){
					mui.toast("保存失败，请稍后重试");
				}
			}
		});
	}
	
});

 