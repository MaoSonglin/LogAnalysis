<!--<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>统计结果</title>
        <link rel="stylesheet" href="static/css/bootstrap.min.css" />
        <link rel="stylesheet" href="static/css/bootstrap-theme.css" />
    </head>
    <body>
    	<nav class="navbar navbar-default">
    		<div class="container-fluid">
    			<div class="navbar-header">
    				<button class="navbar-toggle collapse " data-toggle="collapse" 
    					data-target="#xxxxxxx" aria-expanded="false">
    					<span class="sr-only">显示</span>
    					<span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
    				</button>
    				<a class="navbar-brand" href="#">课程统计分析</a>
    			</div>
    			<form class="navbar-form navbar-right" action="#">
			        <div class="form-group">
			          <input type="text" class="form-control" placeholder="Search">
			        </div>
			        <button type="submit" class="btn btn-default">搜索</button>
			      </form>
    			<div class="collapse navbar-collapse" id="xxxxxxx">
    				<ul class="nav navbar-nav">
    					<li class="active"><a href="javascript:;" onclick="toShow(this.parentNode,'#result-div')">统计展示</a></li>
    					<li class=""><a href="javascript:;" onclick="toShow(this.parentNode,'#upload-div')">上传日志</a></li>
    				</ul>
    			</div>
    		</div>
    	</nav>
    	<div class="container-fluid" id="result-div">
	    	<div class="col-lg-6" id="video-div" style="height: 500px;">
	    		
	    	</div>
	    	<div class="col-lg-6" id="article-div" style="height: 500px;"></div>
	    	<div class="col-lg-6" id="course-city-div" style="height: 500px;"></div>
	    	<div class="col-lg-6" id="course-traffic-div" style="height:500px"></div>
    	</div>
    	<div class="container" id="upload-div" style="display:none">
    		<div class="col-lg-7 col-lg-offset-3">
    		<div class="progress hide" id="progressbar">
			  <div id="progressvalue" class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
			    <span class="">日志分析处理中</span>
			  </div>
			</div>
    		</div>
    		<form class="form-horizontal" id="uploadform" name="uploadform" method="post" enctype="multipart/form-data" 
    			action="#">
    			<div class="form-group">
    				<label class="control-label col-lg-3 col-lg-offset-1" for="">文件路径</label>
    				<div class="col-lg-6">
    					<input id="file" class="form-control" type="file" name="file" />
    				</div>
    			</div>
    			<!-- <div class="form-group">
    				<label class="control-label col-lg-3 col-lg-offset-1" for="">上传路径</label>
    				<div class="col-lg-6">
    					<select class="form-control" id="savepath" name="savepath">
    						<option value="/">/</option>
    					</select>
    				</div>
    			</div> -->
    			<div id="" class="form-group">
    				<label class="control-label col-lg-3 col-lg-offset-1"></label>
    				<div class="col-lg-6">
    					<button class="btn btn-primary" type="button" id="upload">提交</button>
    				</div>
    			</div>
    		</form>
    		
    	</div>
 	</body>
 	<script type="text/javascript" src="static/js/jquery.js" ></script>
 	<script type="text/javascript" src="static/js/bootstrap.min.js" ></script>
 	<script type="text/javascript" src="static/js/echarts.common.min.js" ></script>
 	<script>
 		var contextPath="${pageContext.request.contextPath}";
 		$(function(){
 			$("#upload").click(function(){
 				if(!document.uploadform.file.value){
					alert("请选择文件") 					
 					return;
 				}
 				var formData = new FormData($("#uploadform")[0])
				$.ajax({
					type:"post",
					url:contextPath+"/upload.do",
					data:formData,
					cache:false,
					processData:false,
					contentType:false,
					success:function(res){
						if("success"==res){
							alert("上传成功")
							// 显示进度条
							$("#progressbar").toggleClass("hide","show")
							// 启动定时任务，更新进度条进度
							var timer = setInterval(function(){
								$.get(contextPath+"/jobshedule.do",{},function(res){
									// 跟新进度条
									$("#progressvalue").css("width",(res.schedule*100)+"%")
									// 如果进度完成
									if(res.schedule>1){
										// 清除定时任务
										clearInterval(timer);
										// 隐藏进度条
										$("#progressbar").toggleClass("hide","show")
										// 显示提交按钮
										$("#upload").show()
									}
								},"json")
							},3000)
							// 任务进行中不允许上传文件
							$("#upload").hide()
						}else{
							alert(res)
						}
						document.uploadform.file.value = "";
						
					},
					error:function(res){
						alert("上传失败")
					}
				})
 			})
 		})
 		function toShow(obj,target){
 			if($(obj).hasClass("active"))
 				return;
 			$(obj).toggleClass("active","")
 			$(obj).siblings().toggleClass("active","")
 			var a = new Array('#upload-div','#result-div');
 			for(var i in a){
 				$(a[i]).hide()
 			}
 			$(target).show();
 		}
 		$.ajax({
 			type:"get",
 			url:contextPath+"/videotop.do",
 			dataType:'json',
 			
 			async:true,
 			success:function(res){
 				console.log(res)
 				var counts = new Array();
 				var ids = new Array();
 				for(var i in res){
 					counts.push(res[i].count)
 					ids.push(res[i].id)
 				}
 				draw(counts,ids)
 			}
 		});
 		function draw(counts,ids){
	 		// 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('video-div'),'light');
	
	        // 指定图表的配置项和数据
	        var option = {
	            title: {
	                text: '视频排行榜'
	            },
	            tooltip: {},
	            legend: {
	                data:['点击量']
	            },
	            xAxis: {
	                data: ids//["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
	            },
	            yAxis: {},
	            series: [{
	                name: '点击量',
	                type: 'bar',
	                data: counts//[5, 20, 36, 10, 10, 20]
	            }]
	        };
	
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
 		}
 		
 		$.ajax({
 			type:"get",
 			url:contextPath+"/articletop.do?top=6",
 			dataType:"json",
 			async:true,
 			success:function(date){
 				var color = ['#c23531','#decc98','#a9cc32']
 				var a = new Array()
 				for(var i in date){
 					item = {value:date[i].count,name:date[i].id,itemStyle:{ color: color[i%color.length]}}
 					a.push(item) 
 				}
 				a.reverse();
 				drawArticle(a,"article-div");
 			}
 		});
 		
 		function drawArticle(info,dom,title){
 			if(!title)
 				title = "文章点击排行榜"
 			var myChart = echarts.init(document.getElementById(dom));
	 		option = {
			    //backgroundColor: '#2c343c',
			    title: {
	                text: title
	            },
			    visualMap: {
			        show: false,
			        min: 80,
			        max: 600,
			        inRange: {
			            colorLightness: [0, 1]
			        }
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius: '70%',
			            data:info,
			            roseType: 'angle',
			            label: {
			                normal: {
			                    textStyle: {
			                        color: '#2c343c'//'rgba(255, 255, 255, 0.3)'
			                    }
			                }
			            },
			            labelLine: {
			                normal: {
			                    lineStyle: {
			                        color:'#2c343c'// 'rgba(255, 255, 255, 0.3)'
			                    }
			                }
			            },
			            itemStyle: {
			                normal: {
			                    color: '#c23531',
			                    shadowBlur: 200,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
	 		myChart.setOption(option)
 		}
 		$.ajax({
 			type:"get",
 			url:contextPath+"/citytop.do",
 			dataType:'json',
 			async:true,
 			success:function(data){
 				var a = new Array();
 				var color = ['#c23531','#decc98','#a9cc32']
 				for(var i in data){
 					item = {value:data[i].count,name:data[i].city,itemStyle:{ color: color[i%color.length]}}
 					a.push(item)
 				}
 				drawArticle(a,"course-city-div","课程最受欢迎的城市统计")
 			}
 		});
 		
 		$.ajax({
 			type:"get",
 			url:contextPath+"/traffictop.do",
 			dataType:"json",
 			success:function(data){
 				var counts = new Array();
 				var ids = new Array();
 				for(var i in data){
 					counts.push(data[i].traffic)
 					ids.push(data[i].name)
 				}
 			// 基于准备好的dom，初始化echarts实例
 		        var myChart = echarts.init(document.getElementById('course-traffic-div'),'black');
 		
 		        // 指定图表的配置项和数据
 		        var option = {
 		            title: {
 		                text: '课程流量统计表'
 		            },
 		            tooltip: {},
 		            legend: {
 		                data:['流量']
 		            },
 		            xAxis: {
 		                data: ids//["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
 		            },
 		            yAxis: {},
 		            series: [{
 		                name: '流量',
 		                type: 'bar',
 		                data: counts//[5, 20, 36, 10, 10, 20]
 		            }]
 		        };
 		
 		        // 使用刚指定的配置项和数据显示图表。
 		        myChart.setOption(option);
 			}
 		})
 	</script>
</html>