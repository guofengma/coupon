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
  <body class="recordDetail">
     <header class="mui-bar mui-bar-nav commonHeader">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title" style="color:white">订单详情</h1>
	 </header>
     <div class="mui-content mainContent" style="padding:5px" >
	     <div style="width:100%;"><img width="100%" src='<%=path%>/img/${fn:replace(record.product.picPath,"\\","/")}' /></div>
	     <div style="text-align:left;padding:10px 0px 0px 5px;">
	     	<p class="title" style="font-size:18px;font-weight:bold">${record.product.name}</p>
	     </div>
	     <div style="text-align:left;padding-left:5px;">
            <p>兑换日期：<span class="val">${fn:substring(record.createTime ,0,10)}</span></p>
            <p>积分：<span class="val">${record.points}</span></p>
	     	<p>有效期至：<span class="val">${fn:substring(record.redeemCode.parent.endTime,0,10)}</span></p>
	     	<p style="border:none;">券号：<span class="val">${record.redeemCode.code}</span></p>
	     </div>
	     <div style="text-align:left;padding:10px 0px 0px 5px">
	     	 <ul class="mui-table-view"> 
				 <li class="mui-table-view-cell mui-collapse">
				    <a class="mui-navigate-right" href="#">详情说明</a>
				      <div class="mui-collapse-content">
				          <p>${record.product.description}</p>
				      </div>
				 </li>
			 </ul>
	     </div>
	 </div>
  <script>  
 
  </script>
  </body>
</html>