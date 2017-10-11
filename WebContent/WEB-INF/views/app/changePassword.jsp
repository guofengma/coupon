<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/appinit.jsp" %>
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
	    <h1 class="mui-title" style="color:white">修改密码</h1>
	 </header>
	 <div class="mui-content" style="background-color:white">
	    <form class="mui-input-group" action="<%=path%>/app/changePassword" method="post" style="padding-top:50px">
		    <div class="mui-input-row">
		        <label>旧密码</label>
		        <input type="password" class="mui-input-password" placeholder="请输入旧密码" name="oldPassword">
		    </div>
		     <div class="mui-input-row">
		        <label>新密码</label>
		        <input type="password" class="mui-input-password" placeholder="请输入新密码" name="newPassword">
		    </div>
		    <div class="mui-button-row">
		        <button type="submit" class="mui-btn mui-btn-primary" >确认</button>
		    </div>
		</form>
	</div>
    <script>  
	    $(function(){
			var status = '${status}';
			if(status=='oldPasswordError')
				mui.toast("旧密码错误！");
		});
	    
	    function backToMyInfo(){
			window.location.href = "<%=path%>/app/myInfo";
		}
    </script>
  </body>
</html>