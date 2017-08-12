<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <script type="text/javascript" src="<c:url value='/assets/js/plugins/data-tables/jquery.dataTables.js'/>"></script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>礼品兑换系统</title>
    <style type="text/css">
   	 	.bootstrap-select .dropdown-toggle{width:158%}
    </style>
    <link rel="stylesheet" href="<%=path %>/assets/ztree/css/metroStyle.css" type="text/css">
	<script src="<%=path %>/assets/ztree/js/jquery.ztree.all.min.js"></script>
</head>
<html>
<body style="margin-top:0px">
<div id="wrapper">
<!-- 网站头及导航栏 -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="z-index:1080">
		<%@ include file="../../main/main_header.jsp"%>
		<%@ include file="../../main/main_nav.jsp"%>
	</nav>
	<!--网页主体 -->
	<div id="page-wrapper" style="height:100%;padding-top:60px">
		<div class="breadcrumbs" id="breadcrumbs" style="text-align: left;">
			<ul class="breadcrumb">
				<li class="active"><i class="icon-comment"></i> 待办事项</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
					</div>
					<div class="portlet-body">
						
						<div class="table-toolbar" style="text-align: right;">
							<!-- <div class="btn-group">
									<a href="javascript:add()" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-plus bigger-200">添加商品</i>
									</a>
							</div> -->
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>送审员工</th>
											<th>审核员工</th>
											<th>客户姓名</th>
											<th>充值时间</th>
											<th>充值积分数</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${records.items}" var="item" varStatus="statu">
											<tr class="odd gradeX">
													<td>${statu.count}</td>
													<td>${item.user.displayName}</td>
													<td>${item.checkUser.displayName}</td>
													<td>${item.customer.name}</td>
													<td>${fn:substringBefore(item.createTime,".")}</td>
													<td>${item.points}</td>
													<td>
														<c:if test ="${item.deal}">
														已处理，未通过
														</c:if>
														<c:if test ="${!item.deal}">
															未处理
														</c:if>
													</td>
													<td>
														<p>
															<c:if test="${!item.deal}">
																<a href="javascript:check('<c:url value='/business/record/check?id=${item.id}&pass=true'/>');" class="btn-sm btn-app btn-success no-radius" >
																	<i class="icon-thumbs-up bigger-200"></i>
																	审核通过
																</a>&nbsp;&nbsp;
																<a href="javascript:check('<c:url value='/business/record/check?id=${item.id}&pass=false'/>');" class="btn-sm btn-app btn-success no-radius" >
																	<i class="icon-thumbs-down bigger-200"></i>
																	审核不通过
																</a>&nbsp;&nbsp;
															</c:if>
															<c:if test="${item.deal}">
																<a href="<c:url value='/business/record/requestCheck?id=${item.id}'/>" class="btn-sm btn-app btn-success no-radius" >
																	<i class="icon-share-alt bigger-200"></i>
																	重新发送审核请求
																</a>&nbsp;&nbsp;
															</c:if>
														</p>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="records"/>
		        				<c:param name="urlAddress" value="/business/record/undeal"/>
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
function check(url){
	var pass = url.substring(url.length-4,url.length)=='true';
	var msg = pass?"你确定通过该条记录的审核吗？":"你确定不通过该条记录的审核吗？"
	var isChecked =  confirm(msg, '确认对话框');
	if(isChecked){
		window.location.href=url;
	}
}
</script>
</html>
	