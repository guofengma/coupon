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
				<li class="active"><i class="icon-edit"></i> 已处理预约</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
					</div>
					<div class="portlet-body">					
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="record-table">
									<thead>
										<tr>
											<th>客户名称</th>
											<th>预约商品</th>
											<th>使用兑换码</th>
											<th>预约时间</th>
											<th>服务时间</th>
											<th>联系电话</th>
											<th>备注</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${serviceInfos.items}" var="item">
											<tr class="odd gradeX">
												<td>${item.record.customer.name}</td>
												<td>${item.record.product.name}</td>
												<td>${item.record.redeemCode.code}</td>
												<td>${fn:substring(item.reservationTime,0,10)} ${item.amOrPm}</td>
												<td>${fn:substring(item.confirmReservationTime,0,16)}</td>
												<td>${item.contact}</td>
												<td>${item.comments}</td>
												<td>
													<p>
														<a href="javascript:showServiceInfo('${item.id}')" class="btn-sm btn-app btn-success no-radius" >
															<i class="icon-edit bigger-200"></i>
															修改
														</a>&nbsp;&nbsp;
													</p>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="serviceInfos"/>
		        				<c:param name="urlAddress" value="/business/serviceInfo/dealList"/>
	       				 	</c:import>
						</div>
					</div>
            	</div>
            </div>
		</div>
	</div>	
</div>

<!-- 确认订单模态框 -->
<div class="modal fade" id="serviceInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header" style="padding-top:40px">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">回复订单</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="serviceInfoForm" action="<%=path%>/business/serviceInfo/redeal" method="post">
							<input name="serviceInfoId" id="serviceInfoId" type="hidden"/> 
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="confirmReservationTime">服务时间：</label>
								<div class="col-sm-9 input-append date form_datetime " id='datetimepicker'>
									<input size="16" type="text" id="confirmReservationTime" class="col-xs-10 col-sm-10" name="confirmReservationTime" readonly>
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
							</div>
							<div class="space-4"></div> 
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="confirmReservationAddress">服务地点：</label>
								<div class="col-sm-9">
									<textArea style="height:100px;resize:none" id="confirmReservationAddress" class="col-xs-10 col-sm-10" name="confirmReservationAddress"></textArea>
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="reservationPerson">服务人员：</label>
								<div class="col-sm-9">
									<input type="text" id="reservationPerson" class="col-xs-10 col-sm-10" name="reservationPerson">
								</div>
							 </div>
							 
							 <div class="space-4"></div> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="reservationPersonContact">服务人员电话：</label>
								<div class="col-sm-9">
									<input type="text" id="reservationPersonContact" class="col-xs-10 col-sm-10" name="reservationPersonContact">
								</div>
							 </div>
							 
							  <div class="space-4"></div> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="confirmComment">备注信息：</label>
								<div class="col-sm-9">
									<textArea  style="height:100px;resize:none" id="confirmComment" class="col-xs-10 col-sm-10" name="confirmComment"></textArea>
								</div>
							 </div>
							 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="dealServiceInfo()">
									<i class="icon-ok bigger-200"></i>
									修改
								</button>
								<button class="btn-sm btn-success no-radius" type="button" onclick="cancel()">
									<i class="icon-remove bigger-200"></i>
									取消
								</button>
							</div>
						</form> 
					</div>
				</div>
			</div>
			<div class="modal-header">
				<h3 class="modal-title">预约信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal">
							<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="productName">商品信息：</label>
							<div class="col-sm-9">
								<input type="text" id="productName" class="col-xs-10 col-sm-10" name="productName" readonly>
							</div>
							</div>
							<div class="space-4"></div> 
							 
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="redeemCode">兑换号码：</label>
								<div class="col-sm-9">
									<input type="text" id="redeemCode" class="col-xs-10 col-sm-10" name="redeemCode" readonly>
								</div>
							 </div> 
							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="reservationTime">预约时间：</label>
								<div class="col-sm-9">
									<input type="text" id="reservationTime" class="col-xs-10 col-sm-10" name="reservationTime" readonly>
								</div>
							</div>
							
							 <div class="space-4"></div> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="reservationAddress">预约地址：</label>
								<div class="col-sm-9">
									<textArea id="reservationAddress" style="height:100px;resize:none" class="col-xs-10 col-sm-10" name="reservationAddress" readonly></textArea>
								</div>
							 </div>
							 
							  <div class="space-4"></div> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="contact">联系方式：</label>
								<div class="col-sm-9">
									<input type="text" id="contact" class="col-xs-10 col-sm-10" name="contact" readonly>
								</div>
							 </div>
							 
							 <div class="space-4"></div> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="comments">客户留言：</label>
								<div class="col-sm-9">
									<textArea id="comments" style="height:100px;resize:none" class="col-xs-10 col-sm-10" name="comments" readonly></textArea>
								</div>
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
	format: "yyyy-mm-dd hh:ii",
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left",
    language:"zh-CN",
    minView:0,
    minuteStep:1
});
    
function showServiceInfo(serviceInfoId){
	$.ajax({
		url:"<%=path%>/business/serviceInfo/getServiceInfoById",    //请求的url地址
	    dataType:"json",   
	    async:false,
	    data:{"id":serviceInfoId},
	    type:"GET",
	    success:function(result){
			$("#confirmReservationTime").val(result.confirmReservationTime);
			$("#confirmReservationAddress").val(result.confirmReservationAddress);
			$("#reservationPerson").val(result.reservationPerson);
			$("#reservationPersonContact").val(result.reservationPersonContact);
			$("#confirmComment").val(result.confirmComment);
			$("#serviceInfoId").val(serviceInfoId);
			$("#productName").val(result.productName);
			$("#redeemCode").val(result.redeemCode);
			$("#reservationTime").val(result.reservationTime);
			$("#reservationAddress").val(result.reservationAddress);
			$("#contact").val(result.contact);
			$("#comments").val(result.comments);
	    },
	    error:function(){
			alert("读取预约信息失败！")
	    }
	});
	$("#serviceInfo").modal("show");
}

function dealServiceInfo(){
	$("#serviceInfoForm").submit();
}

function cancel(){
	$("#serviceInfo").modal("hide");
}
</script>
</html>
	