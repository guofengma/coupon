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
				<li class="active">
					<i class="icon-barcode"></i> 我申请的优米码
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
										<i class="icon-plus bigger-200">申请优米码</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>申请时间</th>
											<th>所属批次</th>
											<th>兑换码</th>
											<th>分值</th>
											<th>有效期</th>
											<th>审核状态</th>
											<th>状态</th>
											<!-- <th>操作</th> -->
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${rechargeCodes.items}" var="item">
											<tr class="odd gradeX">
												<td>${fn:substring(item.createTime,0,19)}</td>
												<td>${item.parent.batch}</td>
												<td>${item.code}</td>
												<td>${item.points}</td>
												<td>${fn:substring(item.endTime,0,10)}</td>
												<td>
													<c:if test="${item.approved=='1'}">待审核</c:if>
													<c:if test="${item.approved=='2'}">审核通过</c:if>
													<c:if test="${item.approved=='3'}">审核未通过</c:if>
												</td>
												<td>
													<c:if test="${item.statu}">可用</c:if>
													<c:if test="${!item.statu}">不可用</c:if>
												</td>
												<%-- <td>
													<p>
														<a href="javascript:edit('${item.id}')" class="btn-sm btn-app btn-primary no-radius">
															<i class="icon-edit bigger-200"></i>
															编辑
														</a>&nbsp;
													</p>
												</td> --%>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="rechargeCodes"/>
		        				<c:param name="urlAddress" value="/business/rechargeCodeApply/list"/>
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

<!-- 申请优米兑换码模态框 -->
<div class="modal fade" id="frontBatchInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">申请信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="frontBatchSave" action="<%=path%>/business/rechargeCodeApply/frontBatchSave" method="post"> 
							<div class="space-4"></div> 	
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">选择批次：</label>
								<div class="col-sm-9" style="margin-left:-35px">
									<select class="selectpicker col-xs-11 col-sm-11" id="batchId" name="batchId">
									 	<c:forEach items="${frontBatchs}" var="item">
									  		<option value="${item.id}">
												${item.batch}
											</option>
									    </c:forEach>
								 	</select>
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 	
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="points">优米分值：</label>
								<div class="col-sm-9">
									<input type="text" id="points" class="col-xs-10 col-sm-10" name="points">
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="count">制作数量：</label>
								<div class="col-sm-9">
									<input type="text" id="count" class="col-xs-10 col-sm-10" name="count">
								</div>
							 </div> 
							
							<div class="space-4"></div> 
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">有效期至：</label>
								<div class="col-sm-9 input-append date form_datetime " id='datetimepicker'>
									<input size="16" value="" type="text" id="endTime" class="col-xs-10 col-sm-10" name="endTime" readonly>
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
								 
							<div class="space-4"></div> 
							 	<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">描述信息：</label>
								<div class="col-sm-9">
									<input type="text" id="remark" class="col-xs-10 col-sm-10" name="remark">
								</div>
							 </div> 
							 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="saveBatchInfo()">
									<i class="icon-ok bigger-200"></i>
									保存
								</button>
								<button class="btn-sm btn-success no-radius" type="button" onclick="cancle()">
									<i class="icon-remove bigger-200"></i>
									取消
								</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
		</div>
	</div>			
</div>
</body>
<script type="text/javascript">
$("#datetimepicker").datetimepicker({
	format: "yyyy-mm-dd",
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left",
    language:"zh-CN",
    minView:"month"
    });

function add(){
	openModal("#frontBatchInfo");
}

function cancle(){
	$("#frontBatchInfo").modal("hide");
}


function saveBatchInfo(){
	$("#frontBatchSave").submit();
}

function del(url){
	var isDel =  confirm('确定删除该兑换码吗？', '确认对话框');
	if(isDel){
		window.location.href=url;
	}
}

</script>
</html>
	