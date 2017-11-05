<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/appinit.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <meta charset="utf-8">
    <title>礼品兑换系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">  
    <meta name="apple-mobile-web-app-capable" content="yes">  
    <meta name="apple-mobile-web-app-status-bar-style" content="black">  

    <link rel="stylesheet" href="<%=path%>/assets/mui-master/dist/css/mui.min.css">  
    <script src="<%=path%>/assets/mui-master/dist/js/mui.min.js"></script>
  </head>
  <body>
  <header class="mui-bar mui-bar-nav" style="background-color:red;box-shadow: 0 1px 6px red">
    <h1 class="mui-title" style="color:white">e兑</h1>
  </header>
  <nav class="mui-bar mui-bar-tab " id="nav">  
      <a class="mui-tab-item mui-active" id="a1">  
          <span class="mui-icon mui-icon-home"></span>  
          <span class="mui-tab-label">首页</span>  
      </a>  
      <a class="mui-tab-item " id="a2" href="<%=path%>/app/myInfo">  
          <span class="mui-icon mui-icon-person"></span>  
          <span class="mui-tab-label">我的</span>  
      </a>  
   </nav>
  <div class="mui-content mui-scroll-wrapper" id="product" style="background-color:white;padding-top:50px;width:100%;">  	 
	   <div class="mui-scroll">
	   <div class="mui-slider">
		  <div class="mui-slider-group mui-slider-loop">
		  	<c:forEach items="${activitys}" var="item" varStatus="status">
		  		<c:if test="${status.last}">
		  			<div class="mui-slider-item mui-slider-item-duplicate" style="height:200px;padding:5px"><a href="<%=path%>/activity/app/toActivityPage?id=${item.id}"><img height="190px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a></div>
		  		</c:if>
		    </c:forEach>
		  	<c:forEach items="${activitys}" var="item" varStatus="status">
		    	<div class="mui-slider-item" style="height:200px;padding:5px"><a href="<%=path%>/activity/app/toActivityPage?id=${item.id}"><img height="190px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a></div>
		    </c:forEach>
		    <c:forEach items="${activitys}" var="item" varStatus="status">
		  		<c:if test="${status.first}">
		  			<div class="mui-slider-item mui-slider-item-duplicate" style="height:200px;padding:5px"><a href="<%=path%>/activity/app/toActivityPage?id=${item.id}"><img height="190px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a></div>
		  		</c:if>
		    </c:forEach>
		  </div>
		</div>
		<div style="width:100%;" id="addProduct">
			<c:forEach items="${productsFour}" var="item" varStatus="status">
				<div style="width:50%;float:left">
					<div class="mui-card" style="height:200px">
						<!--页眉，放置标题-->
						<div class="mui-card-header" style="height:15%"><span style="font-size:14px">${item.name}</span></div>
						<!--内容区-->
						<div class="mui-card-content" style="height:60%">
							<a href="<%=path%>/business/app/productDetail?id=${item.id}"><img style="height:110px;padding-top:5px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a>
						</div>
						<!--页脚，放置补充信息或支持的操作-->
						<div class="mui-card-footer" style="height:25%">
							<span style="color:red">${item.points}积分</span>
							<span style="color:blue">库存：${fn:length(item.canBeGivenCode)}</span>
						</div>
					</div>
				</div>	
			</c:forEach>
		</div>
		<div style="width:100%;height:180px">
		
		</div>
		</div>
	</div>
    <script>  
    mui('body').on('tap','a',function(){document.location.href=this.href;});
    var gallery = mui('.mui-slider');
    gallery.slider({
      interval:5000//自动轮播周期，若为0则不自动播放，默认为0；
    });
    document.getElementById('a2').addEventListener('tap', function() {
	    	mui.openWindow({
	    	url: '<%=path%>/app/myInfo', //目标页面地址
	    	id:'a2' //触发新打开页面的id
    	});
    	});
   
       mui.init({
        pullRefresh: {
            container: '#product',
            up: {//上拉加载
            	height:50,//可选,默认50.触发下拉刷新拖动距离,
                auto:false,//可选,默认false.自动上拉加载一次
                contentup:'上拉可以刷新...',
                contentrefresh: '正在加载...',
                contentnomore:'没有更多数据了...',//可选，请求完毕若没有更多数据时显示的提醒内容；
                callback: pullupRefresh
            }
        }
    });

    
    /**
     * 上拉刷新具体业务实现
     */
     
      function pullupRefresh() {  
              setTimeout(function () {  
                  getProduct();//实现更新页面的操作  
              }, 150);  
			  mui('body').on('tap','a',function(){document.location.href=this.href;});
          }  
  	 
     var page = 1;//默认第一页
     var count = 4;//每次4条
     
     function getProduct(){
   		$.ajax({
    		 url:"<%=path%>/getRefreshProduct",    //请求的url地址
   		     dataType:"json",   //返回格式为json
   		     async:false,//请求是否异步，默认为异步，这也是ajax重要特性
   		     data:{"page":page,"count":count},    //参数值
   		     type:"GET",
    		 success:function(result){
    			 if(result.length>0){
    				 page = page + 1;
    				 for(var i=0;i<result.length;i++){
    					 $("#addProduct").append("<div style='width:50%;float:left'><div class='mui-card' style='height:200px'>"+
    								"<div class='mui-card-header' style='height:15%'><span style='font-size:14px'>"+result[i].name+
    								"</span></div><div class='mui-card-content' style='height:60%'>"+
    								"<a href='<%=path%>/business/app/productDetail?id="+result[i].id+"'><img style='height:110px;padding-top:5px' src='<%=path%>/img/"+result[i].picPath+
    								"''/></a></div>"+
    								"<div class='mui-card-footer' style='height:25%'>"+
    								"<span style='color:red'>"+result[i].points+"积分</span>"+
    								"<span style='color:blue'>库存："+result[i].canBeGivenCode+"</span></div></div></div>")
    					 mui('#product').pullRefresh().endPullupToRefresh((false));  
    				 }
	    		}else{
	    				mui('#product').pullRefresh().endPullupToRefresh((true))
	    		}
    		}
	    			
    	});
     }
   
    </script>
  </body>
</html>