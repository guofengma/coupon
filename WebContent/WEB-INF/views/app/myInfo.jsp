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
  	 <div style="background:red">
  	 	<div style="float:left;width:20%;height:150px">
  	 		<span class="mui-icon mui-icon-contact"></span> 
  	 	</div>
  	 	<div style="width:70%;height:150px">
  	 		<span style="color:white">${customer.phone}</span><br> 
  	 		<span style="color:white">我的积分：${customer.point}</span><br> 
  	 		<span style="color:white">所在地：</span><br>  
  	 	</div>
  	 </div>
	 <ul class="mui-table-view">
	    <li class="mui-table-view-cell">
	        <a class="mui-navigate-right">积分充值
	       	 	<span class="mui-badge mui-badge-danger">仅支持e兑卡</span>
	        </a>
	    </li>
	    <li class="mui-table-view-cell">
	        <a class="mui-navigate-right">我的订单</a>
	    </li>
	</ul>
  <script>  
	
  </script>
  </body>
</html>