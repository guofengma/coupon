<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>
<html>
	<head>
		<title>页面未找到</title>
		<meta charset="utf-8">
		<link href="<%=path%>/assets/error/css/style.css" rel="stylesheet" type="text/css"  media="all" />
	</head>
	<body>
		<!--start-wrap--->
		<div class="wrap">
			<!---start-header---->
				<!--<div class="header">
					<div class="logo">
						<h1><a href="#">åºéå¦ï¼</a></h1>
					</div>
				</div>-->
			<!---End-header---->
			<!--start-content------>
			<div class="content">
				<img src="<%=path%>/assets/error/images/error-img.png" title="error" />
				<p><span>哎呀.....</span>您访问的页面不存在</p>
				<a href="<%=path%>/index">返回登录页面</a>
   			</div>
			<!--End-Cotent------>
		</div>
		<!--End-wrap--->
	</body>
</html>

