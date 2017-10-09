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
   <%--  <div class="mui-content-padded" style="margin: 5px;">  
      <form id="loginInfo" class="mui-input-group" action="<%=path%>/applogin" method="post">  
        <div class="mui-input-row">  
            <label>用户名</label>  
            <input type="text" id="phone" placeholder="用户名" name="phone">  
        </div>  
        <div class="mui-input-row">  
            <label>密码</label>  
            <input type="password" id="password" placeholder="密码" name="password">  
        </div>  
     </form>  
    </div>  
    <div style="margin-top:20px;text-align: center;">  
        <button class="mui-btn mui-btn-primary" id="loginBtn" onclick="submit()">登录</button>  
    </div> --%>  
    
    <form class="mui-input-group" action="<%=path%>/applogin" method="post">
	    <div class="mui-input-row">
	        <label>用户名</label>
	    <input type="text" class="mui-input-clear" placeholder="请输入用户名" name="phone">
	    </div>
	    <div class="mui-input-row">
	        <label>密码</label>
	        <input type="password" class="mui-input-password" placeholder="请输入密码" name="password">
	    </div>
	    <div class="mui-button-row">
	        <button type="submit" class="mui-btn mui-btn-primary" >登录</button>
	    </div>
	</form>
    <script>  
	    $(function(){
			var loginFlag = '${loginFlag}';
			if(loginFlag=='failed')
				alert("用户名或密码错误！");
			if(loginFlag=='loginExpired')
				alert("口令已过期，请重新登录");
		});
	    
	    /* function submit(){
	    	$("#loginInfo").submit();
	    } */
    </script>
  </body>
</html>