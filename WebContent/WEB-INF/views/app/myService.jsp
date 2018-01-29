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
    <link rel="stylesheet" href="<%=path%>/assets/css/common.css">
    <script src="<%=path%>/assets/mui-master/dist/js/mui.min.js"></script>  
  </head>
  <body>
     <header class="mui-bar mui-bar-nav commonHeader">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title" style="color:white">我的预约</h1>
	 </header>
     <div class="mui-content mainContent" style="background-color:white">  
    	 <div class="mui-card">
		    <ul class="mui-table-view" style="margin-top:0px">
		    	<c:forEach items="${serviceInfos}" var="item">
				    <li 
				    	<c:choose>
					    	<c:when test="${item.id==openId}">
					    		class="mui-table-view-cell mui-collapse mui-active"
					    	</c:when>
					    	<c:otherwise>
					    		class="mui-table-view-cell mui-collapse"
					    	</c:otherwise>
				    	</c:choose>
				    >
				       <a class="mui-navigate-right" href="#">
				     	  兑换商品：${item.record.product.name}
			     	  	 <c:if test="${item.deal=='0'}">
			          		 	<font color="blue">待处理</font>
				           </c:if>
				           <c:if test="${item.deal=='1'}">
				          		<font color="green">预约成功</font>
				           </c:if>
				           <c:if test="${item.deal=='2'}">
				          		<font color="red">预约失败</font>
				           </c:if>
				           <c:if test="${item.deal=='3'}">
				          		<font color="red">已申请取消预约</font>
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
						    <div class="mui-input-row" style="height:100px">
						    	<label style="padding-left:5px">服务地址：</label>
						    	<textarea id="reservationAddress" name="reservationAddress" readonly>${item.reservationAddress}</textarea>
						    </div>
						    <div class="mui-input-row">
						    	<label style="padding-left:5px">联系方式：</label>
						    	<input id="contact" type="text" name="contact" value="${item.contact}" readonly>
						    </div>
						    <div class="mui-input-row" style="height:100px">
						    	<label style="padding-left:5px">我的留言：</label>
						    	<textarea id="comments" name="comments" readonly>${item.comments}</textarea>
						    </div>
						    <c:if test="${item.deal==1}">
							    <div class="mui-input-row">
							    	<label style="padding-left:5px">服务时间：</label>
						    		<input id="confirmReservationTime" type="text" name="confirmReservationTime" value="${fn:substring(item.confirmReservationTime,0,16)}" readonly>
							    </div>
							    <div class="mui-input-row" style="height:100px">
							    	<label style="padding-left:5px">服务地点：</label>
							    	<textarea id="confirmReservationAddress" name="confirmReservationAddress" readonly>${item.confirmReservationAddress}</textarea>
							    </div>
							    <div class="mui-input-row">
							    	<label style="padding-left:5px">服务人员：</label>
							    	<input id="reservationPerson" type="text" name="reservationPerson" value="${item.reservationPerson}" readonly>
							    </div>
							    <div class="mui-input-row">
							    	<label style="padding-left:5px">服务电话：</label>
							    	<input id="reservationPersonContact" type="text" name="reservationPersonContact" value="${item.reservationPersonContact}" readonly>
							    </div>
							    <div class="mui-input-row" style="height:100px">
							    	<label style="padding-left:5px">服务留言：</label>
							    	<textarea id="confirmComment" name="confirmComment" readonly>${item.confirmComment}</textarea>
							    </div>
							</c:if>
						    <c:if test="${item.deal=='0'}"><!-- 未预约的可以取消 -->
							   <button type="button" onclick="javascript:cancelService('${item.id}')" class="mui-btn mui-btn-danger">取消预约</button>
		        			</c:if>
						    <c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
						    <c:if test="${item.deal=='1'&&item.confirmReservationTime.time-nowDate>24*3600*1000}"><!--24小时外可以取消预约-->
						       <button type="button" onclick="javascript:cancelService('${item.id}')" class="mui-btn mui-btn-danger">取消预约</button>
						    </c:if>
						   </form>
	            		</div>
				    </li>
				</c:forEach>
			</ul>
		</div>
		<div style="padding-top:40px;padding-left:20px;font-size:20px;">没有更多预约信息了</div>
	 </div>
  <script>  
	function cancelService(id){
		var btnArray = ['否', '是'];
		mui.confirm('确定取消该单预约？', '确认框', btnArray, function(e) {
	        if (e.index == 1) {//是
	        	$.ajax({
	    			url:"<%=path%>/serviceInfo/app/cancelService",    //取消服务的url
	    		    dataType:"json",   
	    		    async:false,
	    		    data:{"id":id},
	    		    type:"GET",   
	    		    success:function(result){
	    				if(result.statu){
	    					mui.alert("取消该服务成功");
		    				window.location.href="<%=path%>/serviceInfo/app/myService";
	    				}else{
	    					mui.alert("该服务24小时内不可取消！");
	    				}
	    			},
	    		    error:function(){
	    				mui.alert("取消该服务失败");
	    		    }
	    		});
	        } else {//否
	           
	        }
		})
	}
  </script>
  </body>
</html>