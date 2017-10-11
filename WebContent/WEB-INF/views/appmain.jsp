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
    <script src="<%=path%>/assets/mui-master/js/app.js"></script>
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
   <div class="mui-content" style="background-color:white">  
	   <div class="mui-slider">
		  <div class="mui-slider-group mui-slider-loop">
		  	<c:forEach items="${products}" var="item" varStatus="status">
		  		<c:if test="${status.last}">
		  			<div class="mui-slider-item mui-slider-item-duplicate" style="height:200px;padding:5px"><a href="<%=path%>/business/app/productDetail?id=${item.id}"><img height="190px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a></div>
		  		</c:if>
		    </c:forEach>
		  	<c:forEach items="${products}" var="item" varStatus="status">
		    	<div class="mui-slider-item" style="height:200px;padding:5px"><a href="<%=path%>/business/app/productDetail?id=${item.id}"><img height="190px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a></div>
		    </c:forEach>
		    <c:forEach items="${products}" var="item" varStatus="status">
		  		<c:if test="${status.first}">
		  			<div class="mui-slider-item mui-slider-item-duplicate" style="height:200px;padding:5px"><a href="<%=path%>/business/app/productDetail?id=${item.id}"><img height="190px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a></div>
		  		</c:if>
		    </c:forEach>
		  </div>
		</div>
		<c:forEach items="${productsAll}" var="item" varStatus="status">
			<c:if test='${status.index%2==0}'>
				<div class="mui-card" style="width:45%;float:left">
					<!--页眉，放置标题-->
					<div class="mui-card-header"><span style="font-size:14px">${item.name}</span></div>
					<!--内容区-->
					<div class="mui-card-content">
						<a href="<%=path%>/business/app/productDetail?id=${item.id}"><img src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a>
					</div>
					<!--页脚，放置补充信息或支持的操作-->
					<div class="mui-card-footer">
						<span style="color:red">${item.points}积分</span>
						<span style="color:blue">库存：${item.canBeGivenCode.size()}</span>
					</div>
				</div>
			</c:if>
			<c:if test='${status.index%2==1}'>
				<div class="mui-card" style="width:45%">
					<!--页眉，放置标题-->
					<div class="mui-card-header"><span style="font-size:14px">${item.name}</span></div>
					<!--内容区-->
					<div class="mui-card-content">
						<a href="<%=path%>/business/app/productDetail?id=${item.id}"><img src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}' /></a>
					</div>
					<!--页脚，放置补充信息或支持的操作-->
					<div class="mui-card-footer">
						<span style="color:red">${item.points}积分</span>
						<span style="color:blue">库存：${item.canBeGivenCode.size()}</span>
					</div>
				</div>
			  </c:if>		
			</c:forEach>
		</div>
    <script>  
    var gallery = mui('.mui-slider');
    gallery.slider({
      interval:5000//自动轮播周期，若为0则不自动播放，默认为0；
    });
    
    document.getElementById('a2').addEventListener('tap', function() {
    	//打开关于页面
    	mui.openWindow({
    	url: '<%=path%>/app/myInfo', //目标页面地址
    	id:'a2' //触发新打开页面的id
    	});
    	});
    </script>
  </body>
</html>