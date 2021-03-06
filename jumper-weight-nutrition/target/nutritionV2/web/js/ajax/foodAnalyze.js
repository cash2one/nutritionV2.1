/**
 * 营养素分析js
 */
var userId = GetQueryString("userId");

//初始化表格
$(function(){
	setTimeout(function(){
		$("#analyzeTable").load("common/foodAnalyze.html");
		//初始化页面数据
		foodAnalyze();
	},200);
});

function foodAnalyze(){
	var str = new Array();
	 $("#table-food-list input").each(function(){
		 var food_id = $(this).attr("food-id");
		 var weight = $(this).val()==""?0:$(this).val();
		 var subStr = {'foodId':food_id,'weight':weight};
		 str.push(subStr);
	 });
	 if(userId == null || userId == ""){
		 var param = {
			 voList:JSON.stringify(str)
		 }
	 }else{
		 var param = {
			 userId:userId,
			 voList:JSON.stringify(str)
		 }
	 }
	 $.ajax({
		 type:"POST",
		 url:basePath+"/foodAnalyze/nutritionAnalyze",
		 data:param,
		 dataType:"json",
		 cache:false,
		 success:function(json){
			 if(json.msg==1){
				 var data = json.data;
				 $("#energyIntake").text(data.energyIntake);
				 $("#energyRecommend").text(data.energyRecommend);
				 $("#energyPer").text(data.energyPer);
				 $("#proteinIntake").text(data.proteinIntake);
				 $("#proteinRecommend").text(data.proteinRecommend[0]+"~"+data.proteinRecommend[1]);
				 $("#proteinPer").text(data.proteinPer[0]+"~"+data.proteinPer[1]);
				 $("#fatIntake").text(data.fatIntake);
				 $("#fatRecommend").text(data.fatRecommend[0]+"~"+data.fatRecommend[1]);
				 $("#fatPer").text(data.fatPer[0]+"~"+data.fatPer[1]);
				 $("#carbonIntake").text(data.carbonIntake);
				 $("#carbonRecommend").text(data.carbonRecommend[0]+"~"+data.carbonRecommend[1]);
				 $("#carbonPer").text(data.carbonPer[0]+"~"+data.carbonPer[1]);
				 $("#caIntake").text(data.caIntake);
				 $("#caRecommend").text(data.caRecommend);
				 $("#caPer").text(data.caPer);
				 $("#feIntake").text(data.feIntake);
				 $("#feRecommend").text(data.feRecommend);
				 $("#fePer").text(data.fePer);
				 $("#znIntake").text(data.znIntake);
				 $("#znRecommend").text(data.znRecommend);
				 $("#znPer").text(data.znPer);
				 $("#seIntake").text(data.seIntake);
				 $("#seRecommend").text(data.seRecommend);
				 $("#sePer").text(data.sePer);
				 $("#cuIntake").text(data.cuIntake);
				 $("#cuRecommend").text(data.cuRecommend);
				 $("#cuPer").text(data.cuPer);
				 $("#naIntake").text(data.naIntake);
				 $("#naRecommend").text(data.naRecommend);
				 $("#naPer").text(data.naPer);
				 $("#iIntake").text(data.iIntake);
				 $("#iRecommend").text(data.iRecommend);
				 $("#iPer").text(data.iPer);
				 $("#pIntake").text(data.pIntake);
				 $("#pRecommend").text(data.pRecommend);
				 $("#pPer").text(data.pPer);
				 $("#kIntake").text(data.kIntake);
				 $("#kRecommend").text(data.kRecommend);
				 $("#kPer").text(data.kPer);
				 $("#mgIntake").text(data.mgIntake);
				 $("#mgRecommend").text(data.mgRecommend);
				 $("#mgPer").text(data.mgPer);
				 $("#mnIntake").text(data.mnIntake);
				 $("#mnRecommend").text(data.mnRecommend);
				 $("#mnPer").text(data.mnPer);
				 $("#vaIntake").text(data.vaIntake);
				 $("#vaRecommend").text(data.vaRecommend);
				 $("#vaPer").text(data.vaPer);
				 $("#vbIntake").text(data.vbIntake);
				 $("#vbRecommend").text(data.vbRecommend);
				 $("#vbPer").text(data.vbPer);
				 $("#vcIntake").text(data.vcIntake);
				 $("#vcRecommend").text(data.vcRecommend);
				 $("#vcPer").text(data.vcPer);
				 //配置方案时隐藏推荐量和百分比
				 if(userId == null || userId == ""){
					 $("#analyzeTable").find('table tr').find('td:eq(3)').hide();
					 $("#analyzeTable").find('table tr').find('th:eq(3)').hide();
					 $("#analyzeTable").find('table tr').find('td:eq(2)').hide();
					 $("#analyzeTable").find('table tr').find('th:eq(2)').hide();
				 }
			 }
		 }
	 });
}
/**
 * 营养素分析(修改分量时调用)
 */
$(document).on("blur","#table-food-list input",function(){
	var weight = $(this).val();
	if(weight=="."){
//		 layer.msg("请输入数字！");
		 $(this).val("");
	 }else{
		 foodAnalyze();
	 }
});

/**
 * 删除食材时触发营养素分析
 */
 $(document).on("click",".delete-meals-food",function(){
	setTimeout(function(){
		foodAnalyze();
	},100);
 });

//回车键触发enterBtnEvent按钮单击事件
 $(".leftPlan").keydown(function(){
 	var key=event.keyCode;
 	if(key == 13){
 		$(".enterBtnEvent").click();
 	}
 });
 //制定页面按enter向下操作
 $(".rightPlanDetail").keydown(function(){
 	keyDown(event);
 });
 
 function keyDown(event) {
	var inputs=document.getElementsByClassName("rightPlanDetail")[0].getElementsByTagName("INPUT");
	var inputsText = new Array();
	for(var i=0; i<inputs.length; i++){
		if(inputs[i].type=="text"){
			inputsText.push(inputs[i]);
		}
	}
	var focus=document.activeElement; 
	var key=event.keyCode; 
	var event=window.event||event;
	for(var i=0; i<inputsText.length; i++){
		if(inputsText[i]===focus) break; 
	}
	if(key == 13){
		if(i <inputsText.length-1) {
			inputsText[i+1].focus();
			setTimeout(function(){
				inputsText[i+1].select();
			},100);
		}else{
			setTimeout(function(){
				inputsText[0].select();
			},100);
		}
	}
 } 
