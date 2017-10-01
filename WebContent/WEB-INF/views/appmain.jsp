<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/appinit.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>礼品兑换系统手机端</title>
</head>
<html>
<body style="margin-top: 0px;">

<div>
	<c:forEach items="${products}" var="item" varStatus="status">
		<div id="lunbo${status.index}" class="carousel slide" style="height:30%;width: 100%">
			<div class="carousel-inner">
				<div class="item">
					<img src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}'/>
				</div>
			</div>
			<a class="carousel-control left" href="#lunbo${status.index}"
				data-slide="prev" style="padding-top: 10%;font-size:50px;">
				<i class="fa fa-angle-left"></i>
			</a>
			<a class="carousel-control right" href="#lunbo${status.index}"
				data-slide="next" style="padding-top: 10%;font-size:50px;">
				<i class="fa fa-angle-right"></i>
			</a>
		</div>
	</c:forEach>
</div>	
<div id="myCarousel" class="carousel slide">
    <!-- 轮播（Carousel）指标 -->
    <ol class="carousel-indicators">
    	<c:forEach items="${products}" var="item" varStatus="status">
	        <li data-target="#myCarousel" data-slide-to="${status.index}" <c:if test='${status.index==1}'>class="active"</c:if>></li>
	    </c:forEach>
    </ol>   
    <!-- 轮播（Carousel）项目 -->
    <div class="carousel-inner">
    	<c:forEach items="${products}" var="item" varStatus="status">
	        <div <c:if test='${status.index==1}'>class="item active"</c:if><c:if test='${status.index!=1}'>class="item"</c:if>>
	            <img width="100%" height="100px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}'/>
	        </div>
	    </c:forEach>
    </div>
    <!-- 轮播（Carousel）导航 -->
    <a class="carousel-control left" href="#myCarousel" 
        data-slide="prev">&lsaquo;
    </a>
    <a class="carousel-control right" href="#myCarousel" 
        data-slide="next">&rsaquo;
    </a>
</div>

</body>
</html>

