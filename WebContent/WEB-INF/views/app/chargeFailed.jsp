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
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <a class="mui-icon mui-icon-closeempty mui-pull-left" href = "<%=path%>/appmain"></a>
	    <h1 class="mui-title" style="color:white">充值结果</h1>
	 </header>
     <div class="mui-content" style="background-color:white">
	  	 <div style="padding-top:40px">
	  	 	充值失败！原因：${message}
	  	 </div>
	  	 <div style="padding-top:20px">
	  	 	<button type="button" onclick="javascript:backToMyInfo()" class="mui-btn mui-btn-danger mui-btn-outlined">查看我的积分</button>&nbsp;&nbsp;&nbsp;
	  	 	<button type="button" onclick="javascript:backToMain()" class="mui-btn mui-btn-danger mui-btn-outlined">返回首页</button>
	  	 </div>
	</div>
  <script>  
	function backToMain(){
		window.location.href = "<%=path%>/appmain";
	}
	function backToMyInfo(){
		window.location.href = "<%=path%>/app/myInfo";
	}
  </script>
  </body>
</html>