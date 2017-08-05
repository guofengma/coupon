<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@include file="/WEB-INF/views/init.jsp" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>礼品兑换系统</title>
    <style type="text/css">
   	 	.bootstrap-select .dropdown-toggle{width:162%}
    </style>
    <%
    	boolean isAdd = request.getAttribute("user")==null;
    %>
</head>
<html>
<body style="text-align:left">
<div id="wrapper">
<!-- 网站头及导航栏 -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="z-index:1080">
		<%@ include file="../../main/main_header.jsp"%>
		<%@ include file="../../main/main_nav.jsp"%>
	</nav>
	<!--网页主体 -->
	
	<div id="page-wrapper" style="height:100%;">
		<div class="breadcrumbs" id="breadcrumbs" style="text-align: left;">
			<ul class="breadcrumb">
				<li>
					<a href='<c:url value="/system/user/list"/>'><i class="icon-user"></i> 员工管理</a>
				</li>
				<li class="active">
					<c:if test='<%=!isAdd %>'>编辑员工</c:if>
					<c:if test='<%=isAdd %>'>添加员工</c:if>
				</li>
			</ul>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<form class="form-horizontal" role="form" action="<%=path%>/system/user/save" method="post">
					<input type="hidden" value="<%=isAdd%>" name="isAdd"/>
					<input type="hidden" value="${user.id }" name="id"/>
				
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">登录账号</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-1" placeholder="登录账号" class="col-xs-10 col-sm-5" name="name" value="${user.name}">
						</div>
					</div>	
					
					<div class="space-4"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-2">员工姓名</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-2" placeholder="员工姓名" class="col-xs-10 col-sm-5" name="displayName" value="${user.displayName}">
						</div>
					</div>				

					<c:if test='<%=isAdd %>'>
						<div class="space-4"></div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 密码 </label>
	
							<div class="col-sm-9">
								<input type="password" id="form-field-3" placeholder="密码" class="col-xs-10 col-sm-5" name="password">
								<!-- <span class="help-inline col-xs-12 col-sm-7">
									<span class="middle">Inline help text</span>
								</span> -->
							</div>
						</div>
	
						<div class="space-4"></div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-4"> 重复输入 </label>
	
							<div class="col-sm-9">
								<input type="password" id="form-field-4" placeholder="重复输入密码" class="col-xs-10 col-sm-5">
								<!-- <span class="help-inline col-xs-12 col-sm-7">
									<span class="middle">Inline help text</span>
								</span> -->
							</div>
						</div>
						
						<div class="space-4"></div>		
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5"> 所在城市 </label>
							<div class="col-sm-9">
								<select class="selectpicker" id="fCity" name="fCity" onchange="fCityChange()">
									 <option value="null">选择省份</option>
									 <c:forEach items="${fCityUsedList}" var="item">
									  	<option value="${item.id}">${item.name}</option>
									 </c:forEach>
								 </select>
							</div>
						</div>
						
						<div class="space-4"></div>		
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">  </label>
							<div class="col-sm-9">
								 <select class="selectpicker" id="sCity" name="sCity">
									 <option value="null">选择市县</option>
								 </select>
							</div>
						</div>
					</c:if>
					<div class="form-group">
                    	<label class="col-sm-3 control-label no-padding-right" for="form-field-6">角色</label>
                    	
                    	<div class="col-sm-9 control-label" style="text-align:left;">
                    		<c:forEach items="${roleList}" var="role">
                    			<c:if test="${!role.isSuper}">
		                    		<label class="checkbox-inline">
			                            <input type="checkbox" name="roleIds" value="${role.id}" 
			                            <c:if test='${userRoleList.contains(role) }'>
			                            	checked="checked" 
			                            </c:if>
			                            >${role.name}
			                        </label>
		                        </c:if>
                    		</c:forEach>
                    	</div>
                    </div>
					<div class="clearfix form-actions">
						<div class="col-md-3"></div>
						<div class="col-md-9">
							<button class="btn-sm btn-success no-radius" type="submit" onclick="test()" >
								<i class="icon-ok bigger-200"></i>
								确认
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn-sm btn-success no-radius" type="reset">
								<i class="icon-undo bigger-200"></i>
								重置
							</button>
						</div>
					</div>
				</form>
			</div><!-- /span -->
		</div><!-- /row -->
	</div>
</div>
<script type="text/javascript">
$(function(){
	var same = '${same}';
	if(same=='0')
		alert("该员工名已经存在");	
	
	$("#btnAdd").click(function(){
		window.location.href="<%=path%>/system/user/add";
	});
});

function fCityChange(){
	var id = $("#fCity").val();
	clearSCity();
	if(id!='null'){
		$.ajax({
			url:"<%=path%>/system/city/getSCityUsedByFCityId",    //请求的url地址
		    dataType:"json",   
		    async:false,
		    data:{"id":id},
		    type:"GET",   //请求方式
		    success:function(result){
			console.log(result)
		        for(var i=0;i<result.length;i++){
					$("#sCity").append("<option value='"+result[i].id+"'>"+result[i].name+"</option>");
				}
		    },
		    error:function(){
				alert("请求下级城市失败！")
		    }
		});
	}
	$('#sCity').selectpicker('refresh');
}

function test(){
}

function clearSCity(){//清空二级城市下拉框所有内容（第一个默认的option不清除）
	$("#sCity").empty();
	$("#sCity").append("<option value='null'>选择市县</option>"); 
}
</script>
</body>

</html>
	