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
     <header class="mui-bar mui-bar-nav" style="background-color:red">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title" style="color:white">订单详情</h1>
	 </header>
     <div class="mui-content" style="background-color:white">  
	     <div style="height:200px;padding:5px"><img height="190px" src='<%=path%>/img/${fn:replace(record.product.picPath,"\\","/")}' /></div>
	     <div style="text-align:left;padding:10px 0px 0px 10px">
	     	<p style="font-size:20px">${record.product.name}</p>
	     	<p style="font-size:20px">兑换日期：${fn:substring(record.createTime ,0,10)}</p>
	     	<p style="font-size:20px">积分：${record.points}</p>
	     </div>
	     <div style="text-align:left;padding:10px 0px 0px 10px">
	     	<p style="font-size:20px">有效期至：${fn:substring(record.redeemCode.parent.endTime,0,10)}</p>
	     	<p style="font-size:20px">券号：${record.redeemCode.code}</p>
	     </div>
	     <div style="text-align:left;padding:10px 0px 0px 10px">
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