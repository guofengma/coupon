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
  </head>
  <body>
     <header class="mui-bar mui-bar-nav" style="background-color:red">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title" style="color:white">我的预约</h1>
	 </header>
     <div class="mui-content" style="background-color:white">  
    	 <div class="mui-card">
		    <ul class="mui-table-view" style="margin-top:0px">
		    	<c:forEach items="${serviceInfos}" var="item">
				    <li class="mui-table-view-cell mui-collapse">
				       <a class="mui-navigate-right" href="#">
				     	  兑换码：${item.record.redeemCode.code}
			     	  	 <c:if test="${item.deal=='0'}">
			          		 	待处理
				           </c:if>
				           <c:if test="${item.deal=='1'}">
				          		 预约成功
				           </c:if>
				           <c:if test="${item.deal=='2'}">
				          		 预约失败
				           </c:if>
				       </a>
			           <div class="mui-collapse-content" id="${item.id}">
			            <form class="mui-input-group" style="padding-top:20px">
						    <div class="mui-input-row">
						    	<label style="padding-left:5px">兑换号码：</label>
					    		<input id="redeemCode" type="text" name="redeemCode" value="${item.record.redeemCode.code}" readonly>
						    </div>
						    <div class="mui-input-row">
						    	<label style="padding-left:5px">预约时间：</label>
						    	<input id="reservationTime" type="text" name="reservationTime" value="${fn:substring(item.reservationTime,0,10)}${item.amOrPm}" readonly>
						    </div>
						    <div class="mui-input-row">
						    	<label style="padding-left:5px">服务地址：</label>
						    	<textarea id="reservationAddress" rows="5" placeholder="请输入详细地址" name="reservationAddress" readonly>
						    	 ${item.reservationAddress}
						    	</textarea>
						    </div>
						    <div class="mui-input-row">
						    	<label style="padding-left:5px">联系方式：</label>
						    	<input id="contact" type="text" name="contact" value="${item.contact}" readonly>
						    </div>
						    <div class="mui-input-row">
						    	<label style="padding-left:5px">我的留言：</label>
						    	<textarea id="comments" rows="5" name="comments" readonly>
						    		${item.comments}
						    	</textarea>
						    </div>
						   </form>
	            		</div>
				    </li>
				</c:forEach>
			</ul>
		</div>
		<div style="margin-top:10px">没有更多预约信息了</div>
	 </div>
  <script>  

  </script>
  </body>
</html>