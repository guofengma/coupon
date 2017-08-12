<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<div class="navbar-header">
	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	</button>
	<a href="<c:url value='/main'/>" class="navbar-brand"><span style="color:white;font-size:26px;"><i class="icon-gift"></i> 礼品兑换管理系统</span></a>
</div>
<!-- Top Menu Items -->
<ul class="nav navbar-right top-nav">
	<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user">
		</i> 欢迎 , <shiro:principal/><i class="caret"></i></a>
		<ul class="dropdown-menu">
			<li><a href="<c:url value='/logout'/>"><i class="icon-off"></i> 注销</a></li>
			<li class="divider"></li>
			<li><a href="javascript:showUserInfo();"><i class="icon-cog"></i> 修改密码</a></li>
			<li class="divider"></li>
			<li><a href="<c:url value='/business/record/undeal'/>"><i class="icon-comment"></i> 待办事项</a></li>
		</ul>
	</li>
</ul>

<!-- 修改用户密码的摸态框 -->
<div class="modal fade" id="userInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">个人信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form"> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">登录名：</label>
								<div class="col-sm-9">
									<input type="text" id="form-username" class="col-xs-10 col-sm-10" name="username" value="${currentUsername}" readonly>
								</div>
							 </div> 
							 <div class="space-4"></div> 
							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">用户名：</label>
								<div class="col-sm-9">
									<input type="text" id="form-displayName" class="col-xs-10 col-sm-10" name="displayName">
								</div>
							 </div> 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-2">密码：</label>
								<div class="col-sm-9">
									<input type="text" id="form-password" class="col-xs-10 col-sm-10" name="password">
								</div>
							 </div> 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="saveUserInfo()">
									<i class="icon-edit bigger-200"></i>
									保存修改
								</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>			
</div>		
<script type="text/javascript">
/* url后传递的参数：timeStamp，是时间戳，这个保证加载的不是缓存中的数据 */
function showUserInfo()
{
	$("#form-displayName").val('');
	var getTimestamp = new Date().getTime();
	//$("#userInfo").modal("show");
	openModal("#userInfo");
 	$.ajax({
		url:"<%=request.getContextPath()%>/getUserInfo?timeStamp="+getTimestamp,
		dataType:"json",
		async:true,
		type:"GET",
		success:function(result){
			$("#form-username").val(result.username);
			$("#form-displayName").val(result.displayName);
		},
		error:function(){
			alert("出错了");
		}
	});	
}

function saveUserInfo()
{
	var password = document.getElementById("form-password").value; 
	var displayName = document.getElementById("form-displayName").value; 
	var getTimestamp = new Date().getTime();
	$.ajax({
		url:"<%=request.getContextPath()%>/system/user/changePassword?timeStamp="+getTimestamp,
		dataType:"json",
		data:{
			"password":password,
			"displayName":displayName
			},
		async:true,
		type:"POST",
		success:function(result){
			$("#userInfo").modal("hide");
			alert('保存成功，请重新登录');
			window.location.href = "<%=request.getContextPath()%>/index";			
		},
		error:function(){
			alert("修改失败，请稍后重试！");
		}
	});	
	
}
</script>