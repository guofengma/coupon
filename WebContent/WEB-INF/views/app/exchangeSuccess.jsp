﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/appinit.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
  <head>
    <meta charset="utf-8">
    <title>兑好礼</title>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">  
    <meta name="apple-mobile-web-app-capable" content="yes">  
    <meta name="apple-mobile-web-app-status-bar-style" content="black">  

    <link rel="stylesheet" href="<%=path%>/assets/mui-master/dist/css/mui.min.css">
	<link rel="stylesheet" href="<%=path%>/assets/css/common.css">
    <script src="<%=path%>/assets/mui-master/dist/js/mui.min.js"></script>  
  </head>
  <body>
	 <header class="mui-bar mui-bar-nav commonHeader" style="background-color:#f10215">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <a class="mui-icon mui-icon-closeempty mui-pull-left" style="color:#ffffff;font-weight:bold;" href = "<%=path%>/appmain"></a>
	    <h1 class="mui-title" style="color:white">兑换结果</h1>
	 </header>
     <div class="mui-content mainContent" style="background-color:white;">
	  	 <div style="padding-top:40px;padding-left:20px;font-size:20px;">
	  	 	兑换已完成！
	  	 </div>
	  	 <div style="padding-top:20px;padding-left:20px;">
	  	 	<button type="button" onclick="javascript:backToMyRecord()" class="mui-btn mui-btn-danger mui-btn-outlined">查看订单</button>&nbsp;&nbsp;&nbsp;
	  	 	<button type="button" onclick="javascript:backToMain()"class="mui-btn mui-btn-danger mui-btn-outlined">返回首页</button>
<c:if test="${product.frontExchange}">
	  	 		&nbsp;&nbsp;&nbsp;
	  	 		<button type="button" onclick="javascript:toExchangeServicePage('${record.id}')"class="mui-btn mui-btn-danger mui-btn-outlined">预约服务</button>
	  	 	</c:if>
	  	 </div>
	</div>
  <script>  
	function backToMain(){
		window.location.href = "<%=path%>/appmain";
	}
	function backToMyRecord(){
		window.location.href = "<%=path%>/record/app/myRecord";
	}
function toExchangeServicePage(id){
		window.location.href = "<%=path%>/serviceInfo/app/toExchangeServicePage?recordId="+id;
	}
  </script>
  </body>
</html>