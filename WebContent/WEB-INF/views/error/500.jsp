<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>
<html>
	<head>
		<title>页面未找到</title>
		<meta charset="utf-8">
		<link href="<%=path%>/assets/error/css/style.css" rel="stylesheet" type="text/css"  media="all" />
	</head>
	<body style="background-color: white;background: none;">
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
			<div class="content" id="index500"  style="display:none">
				<img src="<%=path%>/assets/error/images/error2.png" title="error"  /><br />
				<a href="<%=path%>/index">返回登录页面</a>
   			</div>
   			<%-- <div class="content" id="appindex500"  style="display:none">
				<img src="<%=path%>/assets/error/images/error2.png" title="error"  /><br />
				<a href="<%=path%>/appindex">返回登录页面</a>
   			</div> --%>
			<!--End-Cotent------>
		</div>
		<!--End-wrap--->
	</body>
	<script type="text/javascript">
		var isUser = '${cookie['isUser'].value}';
		if(isUser=='true'){
			$("#index500").show();
		}else{
			$("#index500").show();
		}
	</script>
</html>

