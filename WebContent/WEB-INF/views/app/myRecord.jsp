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
	    <h1 class="mui-title" style="color:white">我的订单</h1>
	 </header>
     <div class="mui-content" style="background-color:white">  
	    <ul class="mui-table-view" style="margin-top:0px">
	    	<c:forEach items="${myRecords}" var="item">
			    <li class="mui-table-view-cell mui-media">
			        <a href="<%=path%>/record/app/recordDetail?id=${item.id}">
			            <img class="mui-media-object mui-pull-left" src='<%=path%>/img/${fn:replace(item.product.picPath,"\\","/")}' '>
			            <div class="mui-media-body">
			                ${item.product.name}
			                <p class='mui-ellipsis'>有效期至：${fn:substring(item.redeemCode.parent.endTime,0,10)}</p>
			            </div>
			        </a>
			    </li>
			</c:forEach>
		</ul>
		<div style="margin-top:10px">没有更多订单了</div>
	 </div>
  <script>  
 
  </script>
  </body>
</html>