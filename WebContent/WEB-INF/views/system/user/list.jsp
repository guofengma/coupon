<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
 <script type="text/javascript" src="<c:url value='/assets/js/plugins/data-tables/jquery.dataTables.js'/>"></script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>礼品兑换系统</title>
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
				<li class="active"><i class="icon-user"></i> 用户管理</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
					</div>
					<div class="portlet-body">
						
						<div class="table-toolbar" style="text-align: right;">
							<div class="btn-group">
									<a href="<c:url value='/system/user/add'/>" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-plus bigger-200">添加用户</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>登录名</th>
											<th>用户名</th>
											<th>角色</th>
											<th>添加时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${users.items}" var="item">
											<tr class="odd gradeX">
													<td>${item.name}</td>
													<td>${item.displayName}</td>
													<td>
														<c:forEach items="${item.roles}" var="role" varStatus="status">
															${role.name}<c:if test="${!status.last}">;</c:if>
														</c:forEach>
													</td>
													<td>${item.createTime.toLocaleString()}</td> 
													<td>
														<c:if test="${item.name!=currentUser}">
															<p>
																<a href="<c:url value='/system/user/edit?id=${item.id}'/>" class="btn-sm btn-app btn-primary no-radius">
																	<i class="icon-edit bigger-200"></i>
																	编辑
																</a>&nbsp;&nbsp;
																<a href="javascript:del('<c:url value='/system/user/delete?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																	<i class="icon-trash bigger-200"></i>
																	删除
																</a>
															</p>
															</c:if>
															<c:if test="${item.name==currentUser}">不能对当前登录用户进行操作</c:if>
													</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="users"/>
		        				<c:param name="urlAddress" value="/user/list"/>
	       				 	</c:import>
	       				 	
	       				 	<!-- 模态框（Modal） -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
							   aria-labelledby="myModalLabel" aria-hidden="true"  >
								<div class="modal-dialog">
									<div class="modal-content">
									</div><!-- /.modal-content -->
								</div><!-- /.modal -->
	       				 	</div>
						</div>
					</div>
            	</div>
            </div>
		</div>
	</div>	
</div>
</body>
<script type="text/javascript">


function del(url){
	var isDel =  confirm('确定删除该用户吗？', '确认对话框');
	if(isDel){
		window.location.href=url;
	}
}
</script>
</html>
	