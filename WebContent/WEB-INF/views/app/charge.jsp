<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	 <header class="mui-bar mui-bar-nav commonHeader">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title" style="color:#fff;font-size: 20px">优米充值</h1>
	 </header>
     <div class="mui-content mainContent">
	  	 <form id="chargeForm" class="mui-input-group" action="<%=path%>/business/app/charge" method="post" style="padding-top:20px;line-height: 40px;font-size: 20px;">
			<span style="padding-left:20px">请输入e兑卡密码：</span>
		    <div class="mui-input-row">
		    <input id="keyt" type="text" class="mui-input-clear" placeholder="卡密码不区分大小写" name="keyt">
		    </div>
		    <div class="mui-button-row">
		        <button type="button" onclick="checkEmpty()" class="mui-btn mui-btn-danger">立即充值</button>
		    </div>
		</form>
	</div>
  <script>
	function checkEmpty(){
		if($("#keyt").val()==''){
			mui.toast("请填写兑换码后再充值！");
			return false;
		}
		$("#chargeForm").submit();
	}
  </script>
  </body>
</html>