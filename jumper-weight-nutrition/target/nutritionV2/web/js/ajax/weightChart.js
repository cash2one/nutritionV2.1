/**
 * 体重曲线图js---viewUserInformation.html
 */
//获取数据
var safeWeightList = [], userWeightList = [];
var expectedDate = new Date();

$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	var data = {userId : userId, type : 0};//默认显示近7天
	initWeightChart(data);
	//切换近7天和全部时期
	$(".sevenWeightData, .allWeightData").click(function() {
		$(this).addClass("active");
		if($(this).hasClass("sevenWeightData")) {//this.className
			$(".allWeightData").removeClass("active");
			data.type = 0;
		} else {
			$(".sevenWeightData").removeClass("active");
			data.type = 1;
		}
		initWeightChart(data);
	});
});

//加载曲线图数据
function initWeightChart(data) {
	var xMin = 0, xMax = 301, tickInterval = 7;
	//图表数据获取
	$.post(basePath + "/physical/getWeightChartData", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time: 600});
			return;
		}
		safeWeightList = ret.data.safeWeightList;
		userWeightList = ret.data.userWeightList;
		expectedDate = stringToDate(ret.data.expectedDate);
		if (data.type == 0) {//近7天
			xMin = ret.data.sevenPweek[0];
			xMax = ret.data.sevenPweek[1];
			tickInterval = 1;
		}
		var weightIncrease = ret.data.weightIncrease.toFixed(1);
		$(".weightIncrease").text(weightIncrease + "KG");
		var suggestIncrease = ret.data.suggestIncrease;
		$(".suggestIncrease").text(suggestIncrease[0].toFixed(1) + "~" + suggestIncrease[1].toFixed(1) + "KG");
	});
	
	//图表数据展示
    $('#container-weight').highcharts({
        chart: {
            type: 'spline'
        },
        title: {
            text: '体重数据管理',
            x: -20 ,
            style: {
                color: '#000',
                font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
            }
        },
        xAxis: {
            min:xMin,
            max:xMax,
            gridLineWidth: 1,
            tickInterval:tickInterval,
            pointPadding:1,
            // pointStart: Date.UTC(2010, 0, 1),
            gridLineColor:'rgb(241,137,168)',//网格竖线颜色
            minorGridLineColor:'rgb(241,137,168)',
            lineColor: 'rgb(241,137,168)',//x轴颜色
            tickColor: 'rgb(241,137,168)',//x轴出头线颜色
            tickPixelInterval:20,
            minorGridLineColor:'rgb(241,137,168)',
            title: {
                text: '孕周(week)',
                style: {
                    color: '#333',
                    fontWeight: 'bold',
                    fontSize: '15px',
                    fontFamily: 'Trebuchet MS, Verdana, sans-serif'
                }
            },
            labels: {
                formatter: function() {
                	if (data.type == 0) {
                		return "怀孕" + parseInt(this.value / 7) + "周" + this.value % 7 + "天";
					}
                    return this.value/7;
                }
            }
        },
        yAxis: {
        	/*min:initweight-5,
            max:initweight+30,*/
            lineWidth: 1,
            tickWidth: 1,
            tickInterval:5,
            gridLineColor:'rgb(241,137,168)',//网格竖线颜色
            minorGridLineColor:'rgb(241,137,168)',
            lineColor: 'rgb(241,137,168)',//x轴颜色
            tickColor: 'rgb(241,137,168)',//x轴出头线颜色
            title: {
                text: '体重(kg)',
                style: {
                	color: '#333',
                    fontSize: '14px',
                    fontFamily: '宋体'
                }
            },
            labels: {
                formatter: function() {
                    return this.value 
                }
            }
        },
        tooltip: {
            /*crosshairs: true,//竖线
            headerFormat: '{point.x}周0天(2016年2月10日)<br/>',
            pointFormat: '健康体重'+start+'kg~'+endweight+'kg<br/>',
            footerFormat:'孕妇体重：{point.y}kg'*/
        	crosshairs: true,
        	shared:true,
            crosshairs:true,
            formatter:function(){
                var pre_date=new Date(new Date(expectedDate)-(280-this.x)*3600*24*1000);
                pre_date=pre_date.getFullYear()+'年'+(pre_date.getMonth()+1)+'月'+pre_date.getDate()+'日';
                var week=parseInt(this.x/7);
                var day=(this.x%7==0)?0:this.x%7;
                var s="<b>"+week+"周"+day+"天("+pre_date+"):</b><br>";
                if(this.points.length==1&&this.points[0]['color']!="#00FF66"){//代表体重值的点
                    $.each(this.points,function(){
                        if (this.x <= 280) {
                        	var safe = safeWeightList[this.x];
                        	s += '<br><b>健康体重:</b>' + safe[1].toFixed(1) + 'kg' + '~' + safe[2].toFixed(1) + 'kg';
						}
                        s += '<br><b>' + this.series.name + ':</b>' + this.y.toFixed(1) + 'kg';
                    });
                }else{//代表安全体重的点
                    $.each(this.points,function(){
                    	if (this.x <= 280) {
                    		var low = Number(this.point.low).toFixed(1), high = Number(this.point.high).toFixed(1);
                    		if (low == high) {
                                s += '<br><b>' + this.series.name + ':</b>' + Math.round(this.y * 100) / 100 + 'kg';
                            } else {
                            	s += '<br><b>' + this.series.name + ':</b>' + low + 'kg' + '~' + high + 'kg';
                            }
						}
                    });
                }
                return s;
            }
        },
        plotOptions: {
            spline: {
                marker: {
                    radius: 4,
                    lineColor: '#E98F4B',
                    lineWidth: 1
                }
            },
            series: {
                cursor: 'pointer',
                marker: {
                    lineWidth: 1,
                    enabled: true
                }
            }
        },
        legend: {
        	layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            itemStyle: {
                font: '9pt Trebuchet MS, Verdana, sans-serif',
                color: 'black'
            }
        },
        series: [{
            type:'arearange',
            name:'健康体重',
            data:safeWeightList,
            color: '#00FF66',
            z_index:0
        },{
            name:'孕妇体重',
            marker: {
                fillColor: '#71C0EE',
                lineWidth: 2,
                lineColor: '14F8FB'
            	 /*fillColor: 'white',
                 lineWidth: 4,
                 radius: 1,
                 symbol: 'circle',
                 lineColor: 'gray'*/
            },
            z_index:3,
            data: userWeightList
            // time = Date.getTime();   //time = 1384442746960 (ms)  当前时间为 2013-11-14 23:25:46
            // pointStart: Date.UTC(2010, 0, 1),
            // pointInterval: 24 * 3600 * 1000, // one day
        }],
        credits:{
            enabled:false
        },
        exporting:{
            enabled:false
        }
    });
}

