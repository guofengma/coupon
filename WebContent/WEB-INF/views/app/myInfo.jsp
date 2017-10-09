<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/appinit.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <meta charset="utf-8">
    <title>礼品兑换手机登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">  
    <meta name="apple-mobile-web-app-capable" content="yes">  
    <meta name="apple-mobile-web-app-status-bar-style" content="black">  

    <link rel="stylesheet" href="<%=path%>/assets/mui-master/dist/css/mui.min.css">  
    <script src="<%=path%>/assets/mui-master/dist/js/mui.min.js"></script>  
    <script src="<%=path%>/assets/mui-master/js/app.js"></script>
  </head>
  <body>
	 <header class="mui-bar mui-bar-nav" style="background-color:red">
	    <h1 class="mui-title" style="color:white">我的</h1>
	 </header>
     <nav class="mui-bar mui-bar-tab " id="nav">  
        <a class="mui-tab-item" id="a1">  
            <span class="mui-icon mui-icon-home"></span>  
            <span class="mui-tab-label">首页</span>  
        </a>  
        <a class="mui-tab-item  mui-active" id="a2" href="<%=path%>/app/myInfo">  
            <span class="mui-icon mui-icon-person"></span>  
            <span class="mui-tab-label">我的</span>  
        </a>  
     </nav>
     <div class="mui-content" style="background-color:white">
	  	 <div style="background:red;text-align:left;vertical-align:middle">
	  	 	<div style="float:left;width:35%;height:150px;padding:10px"> 		
	  	 		<span class="mui-icon mui-icon-contact" style="font-size:100px"></span><br>
	  	 		<span style="color:white">${customer.name}</span>
	  	 	</div>
	  	 	<div style="width:100%;height:150px;text-align:left;padding:50px">
	  	 		<span style="color:white">${customer.phone}</span><br> 
	  	 		<span style="color:white">我的积分：${customer.point}</span><br> 
	  	 		<span style="color:white">所在地：</span><br> 
	  	 	</div>
	  	 </div>
	  	 <div style="text-align:left">
			 <ul class="mui-table-view">
			    <li class="mui-table-view-cell">
			        <a class="mui-navigate-right" href="<%=path%>/business/app/toChargePage">积分充值
			       	 	<span class="mui-badge mui-badge-danger">仅支持e兑卡</span>
			        </a>
			    </li>
			    <li class="mui-table-view-cell">
			        <a class="mui-navigate-right" href="<%=path%>/record/app/myRecord">我的订单</a>
			    </li>
			</ul>
		</div>
	</div>
  <script>  
  	document.getElementById('a1').addEventListener('tap', function() {
  	//打开关于页面
  	mui.openWindow({
  	url: '<%=path%>/appmain', //目标页面地址
  	id:'a1' //触发新打开页面的id
  	});
  	});
  </script>
  </body>
</html>