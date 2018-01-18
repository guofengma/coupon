<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/appinit.jsp" %>
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
  <body style="padding: 0px 20px;">
  <header>
	  <img class="logo" src="<%=path%>/assets/img/logo.png">
	  <img style="width:144px;margin-top:16px;padding-top:18px;" src="<%=path%>/assets/img/dhl.png">
  </header>
  <div class="mui-content" style="background-color:white">
	  <form id="loginForm" action="<%=path%>/applogin" method="post" style="padding-top:50px">
		  <div class="mui-input-row">
			  <label>用户名</label>
			  <input type="text" class="mui-input-clear" placeholder="请输入用户名" name="phone" value="${phone}">
		  </div>
		  <div class="mui-input-row">
			  <label>密码</label>
			  <input type="password" class="mui-input-password" placeholder="请输入密码" name="password" value="${password}">
		  </div>
		  <div class="mui-button-row">
			  <button type="submit" class="mui-btn mui-btn-primary">登 录</button>
		  </div>
	  </form>
  </div>
  <script>
	  $(function(){
		  var loginFlag = '${loginFlag}';
		  if(loginFlag=='failed')
			  mui.toast("用户名或密码错误！");
		  if(loginFlag=='loginExpired')
			  mui.toast("口令已过期，请重新登录");
		  if(loginFlag=='uncheck')
			  mui.toast("该用户还未通过审核，请耐心等待！");
		  if(loginFlag=='passwordChange')
			  mui.toast("密码修改成功，需要重新登录！");
	  });


  </script>
  </body>
</html>