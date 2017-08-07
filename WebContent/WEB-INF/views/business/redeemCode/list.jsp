<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
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
				<li>
					<a href='<c:url value="/business/product/list"/>'><i class="icon-gift"></i> 商品管理</a>
				</li>
				<li class="active">
					${product.name}--兑换码管理
				</li>
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
									<a href="javascript:add()" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-plus bigger-200">添加批次</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>批次名称</th>
											<th>有效期</th>
											<th>描述信息</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${redeemCodes.items}" var="item">
											<tr class="odd gradeX">
													<td>${item.name}</td>
													<td>${item.startTime}--${item.endTime}</td>
													<td>${item.description}</td>
													<td>
														<p>
															<a href="javascript:edit('${item.id}')" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-edit bigger-200"></i>
																编辑
															</a>&nbsp;&nbsp;
															<a href="javascript:offline('<c:url value='/business/product/offline?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-arrow-down bigger-200"></i>
																删除
															</a>&nbsp;&nbsp;
															<a href="javascript:offline('<c:url value='/business/product/offline?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-arrow-down bigger-200"></i>
																导入
															</a>&nbsp;&nbsp;
															<a href="javascript:offline('<c:url value='/business/product/offline?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-arrow-down bigger-200"></i>
																修改
															</a>
														</p>
													</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="redeemCodes"/>
		        				<c:param name="urlAddress" value="/business/redeemCode/list"/>
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

</script>
</html>
	