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
     <header class="mui-bar mui-bar-nav" style="background-color:red;box-shadow: 0 1px 6px red">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
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
        <a class="mui-tab-item  mui-active" id="a2" style="background-color:red">  
            <span class="mui-tab-label" style="color:white">立即兑换</span>  
        </a>  
     </nav>
     <div class="mui-content" style="background-color:white">  
	     <div style="height:200px;padding:5px"><img height="190px" src='<%=path%>/img/${fn:replace(product.picPath,"\\","/")}' /></div>
	     <div style="text-align:left;padding:10px 0px 0px 10px">
	     	<p style="font-size:18px">${product.name}</p>
	     	<p style="color:red;font-size:20px">${product.points}积分</p>
	     	<p style="color:blue;font-size:20px">库存：${fn:length(product.canBeGivenCode)}</p>
	     </div>
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
  </script>
  </body>
</html>