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
	 <header class="mui-bar mui-bar-nav"  style="display:none;background-color:red;box-shadow: 0 1px 6px red">
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
	  	 <div style="background:#f10215;text-align:left;vertical-align:middle">
	  	 	<div class="headIconContainer">
	  	 		<span><img class="headIcon" src='<%=path%>/assets/img/app/headIcon.png'></span><br>
	  	 	</div>
	  	 	<div style="width:100%;height:130px;text-align:left;padding:30px">
	  	 		<span style="color:white;font-size:22px">${customer.phone}</span><br>
	  	 		<span style="color:white;font-size:14px">我的积分：${customer.point}&nbsp;分</span><br> 
	  	 		<span style="color:white;font-size:14px">所在省份：
		  	 		<c:forEach items="${customer.customerCityByPriority}" var="city" varStatus="status">
						<c:if test="${status.first}">${city.name}</c:if>
					</c:forEach>
	  	 		</span><br> 
	  	 	</div>
	  	 </div>
	  	 <div style="text-align:left">
			 <ul class="mui-table-view">
			    <li class="mui-table-view-cell">
			        <a class="mui-navigate-right" href="<%=path%>/business/app/toChargePage">积分充值
			       	 	<span style="background: #f10215" class="mui-badge mui-badge-danger">仅支持e兑卡</span>
			        </a>
			    </li>
			    <li class="mui-table-view-cell">
			        <a class="mui-navigate-right" href="<%=path%>/record/app/myRecord">我的订单</a>
			    </li>
			    <li class="mui-table-view-cell">
			        <a class="mui-navigate-right" href="<%=path%>/serviceInfo/app/myService">我的预约</a>
			    </li>
			    <li class="mui-table-view-cell">
			        <a class="mui-navigate-right" href="<%=path%>/app/toPasswordPage">修改密码</a>
			    </li>
 			    <li class="mui-table-view-cell">
			        <a class="mui-navigate-right" href="<%=path%>/appindex">退出系统</a>
			    </li>
			</ul>
		</div>
	</div>
  <script>  
  	document.getElementById('a1').addEventListener('tap', function() {
	  	mui.openWindow({
	  	url: '<%=path%>/appmain', //目标页面地址
	  	id:'a1' //触发新打开页面的id
	  	});
  	});
  </script>
  </body>
</html>