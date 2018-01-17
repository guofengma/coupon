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
    <link rel="stylesheet" href="<%=path%>/assets/mui-master/dist/css/mui.picker.min.css">  
    <script src="<%=path%>/assets/mui-master/dist/js/mui.min.js"></script>    
    <script src="<%=path%>/assets/mui-master/dist/js/mui.picker.min.js"></script>  
  </head>
  <body>
	 <header class="mui-bar mui-bar-nav" style="background-color:red">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title" style="color:white">预约 ${product.name}</h1>
	 </header>
     <div class="mui-content" style="background-color:white;text-align:left">
	  	 <form id="exchangeServiceForm" class="mui-input-group" action="<%=path%>/serviceInfo/app/exchangeService" method="post" style="padding-top:20px">
		    <div class="mui-input-row">
		    	<label style="padding-left:5px">兑换号码：</label>
	    		<input id="redeemCode" type="text" name="redeemCode" value="${record.redeemCode.code}" readonly>
	    		<input id="recordId" type="hidden" name="recordId" value="${record.id}">
		    </div>
		    <div class="mui-input-row">
		    	<label style="padding-left:5px">预约时间：</label>
		    	<input id="reservationTime" type="text" name="reservationTime" readonly>
		    </div>
		    <div class="mui-input-row" style="height:auto">
		    	<label style="padding-left:5px">服务地址：</label>
	    		<textarea id="reservationAddress" rows="4" name="reservationAddress"></textarea>
		    </div>
		    <div class="mui-input-row">
		    	<label style="padding-left:5px">联系方式：</label>
		    	<input id="contact" type="text" name="contact">
		    </div>
		    <div class="mui-input-row" style="height:auto">
		    	<label style="padding-left:5px">客户留言：</label>
		    	<textarea id="comments" name="comments" rows="4"></textarea>
		    </div>
		    <div class="mui-button-row">
		        <button type="button" onclick="checkEmpty()" class="mui-btn mui-btn-danger" >预约服务</button>
		        <button type="button" onclick="backToMyRecord()" class="mui-btn mui-btn-danger" >返回我的订单</button>
		    	<button type="button" onclick="backToMyInfo()" class="mui-btn mui-btn-danger" >返回我的信息</button>
		    </div>
		</form>
	</div>
<script>
var currentDate = new Date();
var delayDays = ${record.product.delayDays}

function backToMyRecord(){
	window.location.href = "<%=path%>/record/app/myRecord";
}

function backToMyInfo(){
	window.location.href = "<%=path%>/app/myInfo";
}

function checkEmpty(){	
	if($("#reservationTime").val()==''){
		mui.toast("请填写服务时间再预约！");
		return false;
	}
	if($("#reservationAddress").val()==''){
		mui.toast("请填写服务地址再预约！");
		return false;
	}
	if($("#contact").val()==''){
		mui.toast("请填写联系方式再预约！");
		return false;
	}
	$("#exchangeServiceForm").submit();
}

var dtpicker = new mui.DtPicker({ 
    "type": "hour",
    "beginDate": new Date(currentDate.getTime()+delayDays*24*3600*1000),//设置开始日期 
    "endDate": new Date(currentDate.getTime()+(delayDays+30)*24*3600*1000),//设置结束日期 
    "customData": {
        "h": [ 
            { value: "am", text: "上午" },
            { value: "pm", text: "下午" },
        ]
    },
    "labels":["年", "月", "日", "时段"]
})

document.getElementById('reservationTime').addEventListener('tap',function () {
    dtpicker.show(function(rs) { 
    	$('#reservationTime').val(rs.text);
    }) 
})
 </script>
  </body>
</html>