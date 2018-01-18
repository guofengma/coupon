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
     <header class="mui-bar mui-bar-nav commonHeader">
	<a class="mui-icon mui-icon-left-nav mui-pull-left" id="toMain"></a>
	    <h1 class="mui-title" style="color:white">${product.name}</h1>
	    <input id="productId" type="hidden" value="${product.id}" />
	    <input id=canBeGivenCodeNum type="hidden" value="${fn:length(product.canBeGivenCode)}" />
	 </header>
	 <nav class="mui-bar mui-bar-tab " id="nav">  
      	<div class="mui-tab-item" id="a1">
      		 <div class="mui-numbox" data-numbox-min="1" data-numbox-max="${fn:length(product.canBeGivenCode)}">
				<button id="less" class="mui-btn mui-numbox-btn-minus" type="button">-</button>
				<input id="numbers" class="mui-numbox-input" type="number" disabled />
				<button id="add" class="mui-btn mui-numbox-btn-plus" type="button">+</button>
			</div>
        </div>  
        <a class="mui-tab-item  mui-active" id="a2" style="background-color:#f10215">
            <span class="mui-tab-label" style="color:white">立即兑换</span>  
        </a>  
     </nav>
     <div class="mui-content mainContent">
	     <div style="padding:5px;"><img width="100%" src='<%=path%>/img/${fn:replace(product.picPath,"\\","/")}' /></div>
	     <div style="height:30px;line-height:30px;padding:0px 5px;">
	     	<div style="display:inline-block;width:70%;background:-webkit-linear-gradient(left,#ef3390,#ef3647);"><p style="color:#ffffff;margin:0px;font-size:22px;padding-left:10px;">￥${product.points}积分</p></div>
	     	<div style="display:inline-block;width:28%;text-align:right;"><p style="color:ef3390;margin:0px;font-size:20px;">库存：${fn:length(product.canBeGivenCode)}</p></div>
	     </div>
         <div style="padding:18px 0px 0px 5px;margin-top:10px;"><p style="font-size:17px;color:#232326;padding-top:10px;padding-left:10px;border:none;margin-bottom:0px;">${product.name}</p></div>
	     <div style="text-align:left;padding:10px 0px 0px 10px">
	     	 <ul class="mui-table-view"> 
				 <li class="mui-table-view-cell mui-collapse">
				    <a class="mui-navigate-right" href="#">详情说明</a>
				      <div class="mui-collapse-content">
				          <p>${product.description}</p>
				      </div>
				 </li>
			 </ul>
	     </div>
	 </div>
  <script>  
  document.getElementById("a2").addEventListener('tap', function() {  
      var btnArray = ['取消', '确定'];  
      mui.confirm('请确认您要兑换的商品的数量', '提示', btnArray, function(e) {  
          if (e.index == 1) {
        	  var count = $("#numbers").val();
        	  var id = $("#productId").val();
        	  var maxNum = $("#canBeGivenCodeNum").val();
        	  if(maxNum == '0'){
        		  mui.toast("没有库存，等待补货！")
        	  }else{
        		  window.location.href = "<%=path%>/business/app/exchange?id="+id+"&count="+count ;
        	  }
          } else {  
                
          }  
      })  
  }); 
  
  mui('body').on('tap','#toMain',function(){
	  history.go(-1); 
  });
  </script>
  </body>
</html>