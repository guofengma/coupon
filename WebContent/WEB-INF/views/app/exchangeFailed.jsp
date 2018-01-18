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
	 <header class="mui-bar mui-bar-nav commonHeader" >
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title" style="color:white">兑换结果</h1>
	 </header>
     <div class="mui-content mainContent" style="background-color:white">
	  	 <div style="padding-top:40px;padding-left:20px;font-size:20px;">
	  	 	兑换失败！原因：${message}
	  	 </div>
	</div>
  <script>  

  </script>
  </body>
</html>