<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@include file="/WEB-INF/views/init.jsp" %>
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
				<li class="active"><i class="icon-key"></i> 角色管理</li>
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
								<a href="<c:url value='/system/role/add'/>" class="btn-sm btn-app btn-success no-radius">
									<i class="icon-plus bigger-200">添加角色</i>
								</a>
							</div>
						</div>
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>角色名</th>
											<th>排序</th>
											<th>创建时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${roles}" var="item">
											<c:if test="${!item.isSuper}">
												<tr class="odd gradeX">
													<td>${item.name}</td>
													<td>${item.priority}</td>
													<td>${item.createTime.toLocaleString()}</td>
													<td>
														<p>
															<a  href="<c:url value='/system/role/edit?id=${item.id}'/>" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-edit bigger-200"></i>
																编辑
															</a>&nbsp;&nbsp;
															<a href="javascript:del('<c:url value='/system/role/delete?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-trash bigger-200"></i>
																删除
															</a>
														</p>
													</td>
												</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
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
	var isDel =  confirm('确定删除该角色吗？', '确认对话框');
	if(isDel){
		window.location.href=url;
	}
}
</script>
</html>
	