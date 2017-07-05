/**
 * 孕妇管理js
 */
//分页数据
var totalPage = 0;//总页数
var curr = 1;//当前页
var list = [];//数据

//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	var data = {hospitalId : hospitalId, page : curr, limit : CONST.tabPageSize};
	//初始化分页
	initPage(data);
	
	//模糊查询
	$("#search-btn1").click(function() {
		var query = $("input[name='query']").val().trim();
		//清空其他赛选条件
		$("#weightStatus").val("");
		$("#weightExceptionType").val("");
		$("#startExpDate").val("");
		$("#endExpDate").val("");
		data.weightStatus = $("#weightStatus").val();
		data.weightExceptionType = $("#weightExceptionType").val();
		data.startExpDate = $("#startExpDate").val();
		data.endExpDate = $("#endExpDate").val();
		data.page = 1;
		data.query = query;
		//分页查询
		initPage(data);
	});
	//回车事件触发查询
	$(document).keydown(function() {
    	var key = event.keyCode;
    	switch (key) {
		case 13:// 回车按钮
			$(".enterBtnEvent").click();
			break;
		default:
			break;
		}
	});
	//多条件赛选
	$("#search-btn2").click(function() {
		var query = $("input[name='query']").val().trim();
		data.page = 1;
		data.query = query;
		data.weightStatus = $("#weightStatus").val();
		data.weightExceptionType = $("#weightExceptionType").val();
		data.startExpDate = $("#startExpDate").val();
		data.endExpDate = $("#endExpDate").val();
		//分页查询
		initPage(data);
	});
	
	//点击姓名跳转到用户信息页面
	$(document).on("click", ".userInfoTr", function() {
		var userId = $(this).attr("userId");
		window.location.href = "viewUserInformation.html?userId=" + userId + "&menuType="+CONST.menuType[1];
	});
	
});


//初始化分页
function initPage(data) {
	//初始分页信息
	function init(data) {
		$.post(basePath + "/userManage/listUserManageByPage", data, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time : 1000});
				return;
			}
			totalPage = ret.data.pages;
			curr = ret.data.pageNum;
			list = ret.data.list;
		}, "json");
	}
	init(data);
	//展示分页列表
	layui.use(["laypage"], function() {
		layui.laypage({
			cont : "page",
			pages : totalPage,
			curr : data.page,
			skip : true,
			jump : function(obj) {
				//得到了当前页，用于向服务端请求对应数据
				curr = obj.curr;
				data.page = curr;
				//跳转到某页，并初始化分页数据
				init(data);
				var html = "";
				if (totalPage == 0 && !$.isEmptyObject(data.query)) {
					html = "<tr><td colspan='9' class='noResultTd'>没有符合关键词的结果</td></tr>";
					$("#pageList").html(html);
					return;
				}
				for ( var int = 0; int < list.length; int++) {
					var obj = list[int];
					html += 
						"<tr class='userInfoTr' userId=" + obj.voUserInfo.userId + ">" +
							"<td><a>" + obj.voUserInfo.realName + "</a></td>" +
							"<td>" + obj.voUserInfo.pregnantWeek[0] + "周" + obj.voUserInfo.pregnantWeek[1] + "天</td>" +
							"<td>" + obj.voUserInfo.expectedDate + "</td>" +
							"<td>" + obj.voWeightRecord.bmi + "(" + CONST.weightStatus[obj.voWeightRecord.weightStatus] + ")" + "</td>" +
							"<td>" + obj.voUserInfo.height + "</td>" +
							"<td>" + obj.voUserInfo.weight + "</td>" +
							"<td>" + obj.voWeightRecord.averageValue + "</td>" +
							"<td>" + obj.voUserInfo.age + "</td>" +
							"<td>" + obj.voUserInfo.mobile + "</td>" +
						"</tr>";
				}
				$("#pageList").html(html);
			}
		});
	});
}