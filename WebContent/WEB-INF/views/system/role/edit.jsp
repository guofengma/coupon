<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>

<link rel="stylesheet" href="<%=path %>/assets/ztree/css/metroStyle.css" type="text/css">
<script src="<%=path %>/assets/ztree/js/jquery.ztree.all.min.js"></script>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>礼品兑换系统</title>
    <%
    	boolean isAdd = request.getAttribute("role")==null;
    	System.out.println(isAdd);
    %>
</head>
<html>
<body>
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
					<a href='<c:url value="/system/role/list"/>'><i class="icon-key"></i> 角色管理</a>
				</li>
				<li class="active">
					<c:if test='<%=!isAdd %>'>编辑角色</c:if>
					<c:if test='<%=isAdd %>'>添加角色</c:if>
				</li>
			</ul>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<form class="form-horizontal" role="form" action="<%=path%>/system/role/save" method="post">
					<input type="hidden" value="<%=isAdd%>" name="isAdd"/>
					<input type="hidden" value="${role.id }" name="id"/>
				
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">角色名称</label>

						<div class="col-sm-9">
							<input type="text"  placeholder="角色名称" class="col-xs-10 col-sm-5" name="name" value="${role.name }">
						</div>
					</div>

					<div class="space-4"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">排列顺序</label>

						<div class="col-sm-9">
							<input type="text"  placeholder="排列顺序" class="col-xs-10 col-sm-5" name="priority" value="${role.priority }">
						</div>
					</div>
                    
                    <div class="space-4"></div>
                    
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">分配权限</label>
						<div class="col-sm-9">
							<%@ include file="perms.jsp"%>
							<input type="hidden" id="perms" placeholder="" class="col-xs-10 col-sm-5" name="perms" value="${role.perms }">
						</div>
  					</div>
					
					<div class="clearfix form-actions">
						<div class="col-md-12">
							<button class="btn-sm btn-success no-radius" type="submit" onclick="submitNodes()">
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
</body>
<script type="text/javascript">
var perms = $("#perms").val();
$(function(){
	$("#btnAdd").click(function(){
		window.location.href="<%=path%>/system/role/add";
	});
	
	disablePerms();
	$("input[name=isSuper]").bind("click",function(){
		disablePerms();
	});
	
	//标识选中状态
	checkNodes();
});

function disablePerms() {
	var nodes = permsTree.getNodes();
	if($("input[name=isSuper]:checked").val()=="true") {
		permsTree.checkAllNodes(true);
		for(var i=0;i<nodes.length;i++){
			permsTree.setChkDisabled(nodes[i], true,false,true);
		}
	} else {
		for(var i=0;i<nodes.length;i++){
			permsTree.setChkDisabled(nodes[i], false,false,true);
		}
		permsTree.checkAllNodes(false);
	}
}
var isChecked = function(perm) {
	return perms.indexOf(perm)!=-1;
}



function setChkDisabled(disabled){
	for(var i=0;i<nodes.length;i++){
		permsTree.setChkDisabled(nodes[i],disabled,false,true);
	}
}

function submitNodes(){
	var nodes = permsTree.getCheckedNodes(true);
	var str = "";
	var perms = "";
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].perm!=null){
		    str += nodes[i].id+ ",";
		    perms += nodes[i].perm+ ",";
		}
	}
	if(perms.length>0) {
		perms = perms.substring(0,perms.length-1);
	}
	$("#perms").val(perms);
}




//标识选中状态
function checkNodes(){
	var nodes = permsTree.getCheckedNodes(false);
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].perm!=null){
			var nodePerms=nodes[i].perm.split(",");
			if(perms.indexOf(nodes[i].perm)!=-1){
				//单菜单权限节点
				permsTree.checkNode(nodes[i], true, false);
			}else if((nodePerms.length>1&&perms.indexOf(nodePerms[0])!=-1)&&nodes[i].id!=1){
				//alert(nodes[i].perm);
				//除根节点的带多个权限菜单的节点
				permsTree.checkNode(nodes[i], true, false);
			}
		}
	}
}
</script>
</html>
	