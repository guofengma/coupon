<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>礼品兑换系统</title>
    <style type="text/css">
   	 	.bootstrap-select .dropdown-toggle{width:165%}
    </style>
    <link rel="stylesheet" href="<%=path %>/assets/timepicker/css/bootstrap-datetimepicker.min.css">
	<script src="<%=path%>/assets/timepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script src="<%=path%>/assets/timepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
				<li>
					<a href='<c:url value="/search/customer/findByCondition"/>'><i class="icon-qrcode"></i> 客户信息查询</a>
				</li>
				<li class="active">
					客户姓名：${customer.name}，电话号码：${customer.phone}，剩余积分：${customer.point}
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
							<!-- <div class="btn-group">
									<a href="javascript:showModal()" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-search bigger-200">选择查询条件</i>
									</a>
							</div> -->
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>所属员工</th>
											<th>操作</th>
											<th>兑换商品</th>
											<th>商品兑换码</th>
											<th>发生时间</th>
											<th>状态</th>
											<th>积分数量</th>
											<th>备注</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${records.items}" var="item" varStatus="status">
											<tr class="odd gradeX">
												<td>${status.count}</td>
												<td>${item.user.displayName}</td>
												<td>
													<c:if test ="${item.product!=null}">
														兑换
													</c:if>
													<c:if test ="${item.rechargeCode!=null}">
														${item.rechargeCode.code}
													</c:if>
													<c:if test ="${item.rechargeCode==null&&item.product==null}">
														后台充值
													</c:if>
												</td>
												<td>
													<c:if test ="${item.product!=null}">
														${item.product.name}
													</c:if>
												</td>
												<td>
													<c:if test ="${item.redeemCode!=null}">
														${item.redeemCode.code}
													</c:if>
												</td>
												<td>${fn:substringBefore(item.createTime,".")}</td>
												<th>
													<c:if test="${!item.deal}">
														<font color="#0000FF">待审核</font>
													</c:if>
													<c:if test="${item.deal}">
														<c:if test="${!item.statu}">
															<font color="#FF0000">已审核，未通过</font>
														</c:if>
														<c:if test="${item.statu}">
															<font color="#00FF00">成功</font>
														</c:if>
													</c:if>
												</th>
												<td>
													<c:if test="${!item.raise}">
													-
													</c:if>${item.points}
												</td>
												<td>
													${item.description}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="records"/>
		        				<c:param name="urlAddress" value="/search/record/findByCustomerId"/>
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
	