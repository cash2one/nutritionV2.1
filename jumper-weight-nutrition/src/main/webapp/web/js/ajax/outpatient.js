//手机端h5或web端第一个页面赋初始session值
var hospitalId = GetQueryString("hospitalId");
//$.session.set('hospitalId', hospitalId);
$.cookie('hospitalId', hospitalId);

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
	
	setInterval(function() {
		var query = $("input[name='query']").val().trim();
		if (query != "" || curr != totalPage) {
			return;
		}
		//没有搜索条件，并且当前页为最后一页时执行
		data.page = curr;
		delete data["query"];
		initPage(data);
	}, 3000);
	
	//模糊查询
	$("#search-btn").click(function() {
		var query = $("input[name='query']").val().trim();
		data.page = 1;
		data.query = query;
		//分页查询
		initPage(data);
	});
	
	//回车事件触发查询
	$(document).keydown(function(e) {
		e = e||event;
    	var key = e.keyCode;
    	switch (key) {
		case 13:// 回车按钮
			$(".enterBtnEvent").click();
			break;
		default:
			break;
		}
	});
	
	//跳转用户详情页面
	$(document).on("click", ".userInfoTr", function(event) {
		event.stopPropagation();
		var userId = $(this).attr("userId");
		var status = $(this).attr("status");
		var outpatientId = $(this).attr("outpId");
		if (status == 0) {//初诊
			window.location.href = "fillInUserInfo.html?userId=" + userId + "&outpatientId=" + outpatientId;
		} else {//复诊
			window.location.href = "viewUserInformation.html?userId=" + userId + "&outpatientId=" + outpatientId + "&menuType=" + CONST.menuType[0];
		}
	});
	
	//新建档
	$("#bookbuild").click(function() {
		window.location.href = "fillInUserInfo.html";
	});
	
	//点击删除
	$(document).on("click", ".delOutp, .delOutpBatch", function(event) {
	//$(".delOutp, .delOutpBatch").click(function() {
		event.stopPropagation();
		var outpIds = [], target = new Object();
		if ($(this).hasClass("delOutp")) {//单个删除
			outpIds.push($(this).attr("outpId"));
			target = $(this);
		} else {//多个删除
			target = $("input[name='record']:checked");
			target.each(function(i) {
				outpIds.push($(this).val());
			});
		}
		if (outpIds.length == 0) {
			layer.msg('请勾选要删除的孕妇', {time: 1000});
			return;
		}
		layer.alert('确定要删除么？', {
            icon: 0,
            title: '提示',
            btn: ['删除'],
            btnAlign: 'c',
            yes: function (index, layero) {
            	checkedDel(outpIds, target);//执行删除
                layer.close(index);
                layer.msg('操作成功', {time: 600});
            }
            //键盘 回车 ECS控制弹窗~
            /*success: function (layero, index) {
                $(document).on('keydown', function (e) {
                    if ($(".layui-layer").hasClass("layui-layer")) {
                        if (e.keyCode == 13) {
                            layer.close(index);
                            checkedDel(outpIds, target);//执行删除
                            layer.msg('操作成功', {time: 600});
                        }
                        if (e.keyCode == 27) {
                            layer.close(index);
                        }
                    }
                });
            }*/
        });
		//确定删除outpIds：门诊id集合
        function checkedDel(outpIds, target) {
            target.each(function() { // 遍历选中的checkbox
                $(this).parents("tr").remove();  // 获取checkbox后删除按钮所在的行
            });
           $.post(basePath + "/outpatient/deleteOutpatient", {idList : outpIds}, function(ret) {
				if (ret.msg != 1) {
					layer.msg(ret.msgbox, {time: 600});
				}
			}, "json");
        }
	});
});


//初始化分页
function initPage(data) {
	//初始分页信息
	function init(data) {
		$.post(basePath + "/outpatient/listOutpatientUser", data, function(ret) {
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
					html = "<tr><td colspan='13' class='noResultTd'>没有符合关键词的结果</td></tr>";
					$("#pageList").html(html);
					return;
				}
				for (var int = 0; int < list.length; int++) {
					var outp = list[int];
					html += 
						"<tr class='userInfoTr' userId=" + outp.voUserInfo.userId + " status=" + outp.status + " outpId=" + outp.id + ">" +
							"<td><input class='checkIn' type='checkbox' name='record' value=" + outp.id + "><a class='realName'>" + outp.voUserInfo.realName + "</a></td>" +
							"<td>" + CONST.outpType[outp.status] + "</td>" + 
							"<td>" + outp.voUserInfo.outpatientNum + "</td>" +
							"<td>" + outp.outpatientTime + "</td>" +
							"<td>" + outp.voUserInfo.pregnantWeek[0] + "周" + outp.voUserInfo.pregnantWeek[1] + "天</td>" +
							"<td>" + outp.voUserInfo.expectedDate + "</td>" +
							"<td>" + CONST.weightStatus[outp.voWeightRecord.weightStatus] + "</td>" +
							"<td>" + outp.voUserInfo.height + "</td>" +
							"<td>" + outp.voUserInfo.weight + "</td>" +
							"<td>" + outp.voWeightRecord.averageValue + "</td>" +
							"<td>" + outp.voUserInfo.age + "</td>" +
							"<td>" + outp.voUserInfo.mobile + "</td>" +
							"<td><i class='delTr layui-icon delOutp' outpId=" + outp.id + ">&#xe640;</i></td>" +
						"</tr>";
				}
				//记录下选中的栏目
				var checkedArr = [];
				$(".userInfoTr").each(function() {
					if ($(this).find("input[name='record']").is(":checked")) {
						checkedArr.push($(this).attr("userId"));
					}
				});
				$("#pageList").html(html);
				$(".userInfoTr").each(function(i) {
					var trBackColor = (i % 2 == 0) ? "evenTr" : "oddTr";
					$(this).addClass(trBackColor);
					var checked = false;
					for (var i = 0; i < checkedArr.length; i++) {
						if ($(this).attr("userId") == checkedArr[i]) {
							checked = true;
							break;
						}
					}
					if (checked) {
						$(this).find("input[name='record']").attr("checked", true);
						$(this).css("color", "#ff334b");//设置选中样式
					}
				});
			}
		});
	});
}
