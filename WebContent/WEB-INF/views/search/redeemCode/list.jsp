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
				<li class="active">
					<i class="icon-qrcode"></i> 商品兑换码查询
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
									<a href="javascript:showModal()" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-search bigger-200">选择查询条件</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>批次ID</th>
											<th>商品名称</th>
											<th>卡号密钥</th>
											<th>客户兑换时间</th>
											<th>到期时间</th>
											<th>兑换状态</th>
											<th>兑换客户手机号</th>
											<th>兑换客户所在城市</th>
											<th>备注</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${redeemCodes.items}" var="item" varStatus="status">
											<tr class="odd gradeX">
												<td>${status.count}</td>
												<td>${item.parent.batch}</td>
												<td>${item.parent.product.name}</td>
												<td>${item.code}</td>
												<td>
													<c:if test="${item.record!=null}">
														${fn:substring(item.record.createTime,0,10)}
													</c:if>
												</td>
												<td>${fn:substring(item.parent.endTime,0,10)}</td>
												<td>
													<c:if test="${item.parent.endTime>=date}">
														<c:if test="${item.used}">已兑换</c:if>
														<c:if test="${!item.used}">未兑换</c:if>
													</c:if>
													<c:if test="${item.parent.endTime<date}">
														已过期
													</c:if>
												</td>
												<td>
													<c:if test="${item.record!=null}">
														${item.record.customer.phone}
													</c:if>
												</td>
												<td>
													<c:if test="${item.record!=null}">
														${item.record.customer.city.name}
													</c:if>
												</td>
												<td>
													${item.parent.remark}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="redeemCodes"/>
		        				<c:param name="urlAddress" value="/search/redeemCode/findByCondition"/>
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

<!-- 查询模态框 -->
<div class="modal fade" id="searchmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">商品兑换码-查询条件</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="searchFrom" action="<%=path%>/search/redeemCode/findByCondition" method="post">				 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="name">商品名称：</label>
								<div class="col-sm-9">
									<input type="text" value="${name}" id="name" class="col-xs-10 col-sm-11" name="name">
								</div>
							 </div>
							 
							 <div class="space-4"></div>
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="code">卡号密钥：</label>
								<div class="col-sm-9">
									<input type="text" value="${code}" id="code" class="col-xs-10 col-sm-11" name="code">
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">兑换状态：</label>
								<div class="col-sm-9" style="text-align:left">
									 <select class="selectpicker" id="statu" name="statu">
										 <option value="null">选择兑换状态</option>
										 <option value="1">未兑换</option>
										 <option value="2">已兑换</option>
										 <option value="3">已过期</option>
									 </select>
								</div>
							 </div>
							 
							   <div class="space-4"></div>
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-points">到期时间：</label>
								<div class="timeSelect col-sm-4 input-append date form_datetime datetimepicker">
									<input size="16" value="${startTime}" type="text" id="startTime" class="col-xs-10 col-sm-10" name="startTime" readonly>
									<span class="add-on"><i class="icon-remove"></i></span>
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
								<label class="col-sm-1 control-label no-padding-right">至</label>
								<div class="timeSelect col-sm-4 input-append date form_datetime datetimepicker">
									<input size="16" value="${endTime}" type="text" id="endTime" class="col-xs-10 col-sm-10" name="endTime" readonly>
									<span class="add-on"><i class="icon-remove"></i></span>
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							 </div>
							<div class="space-4"></div> 
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">兑换时间：</label>
								<div class="timeSelect col-sm-4 input-append date form_datetime datetimepicker">
									<input size="16" value="${exStartTime}" type="text" id="exStartTime" class="col-xs-10 col-sm-10" name="exStartTime" readonly>
									<span class="add-on"><i class="icon-remove"></i></span>
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
								<label class="col-sm-1 control-label no-padding-right">至</label>
								<div class="timeSelect col-sm-4 input-append date form_datetime datetimepicker">
									<input size="16" value="${exEndTime}" type="text" id="exEndTime" class="col-xs-10 col-sm-10" name="exEndTime" readonly>
									<span class="add-on"><i class="icon-remove"></i></span>
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 	
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-5"> 所在城市： </label>
								<div class="col-sm-9" style="text-align:left;">
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
								<div class="col-sm-9" style="text-align:left">
									 <select class="selectpicker" id="sCity" name="sCity">
										 <option value="null">选择市县</option>
									 </select>
								</div>
							</div>
							
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="phone">客户手机号：</label>
								<div class="col-sm-9">
									<input type="text" id="phone" value="${phone}" class="col-xs-10 col-sm-11" name="phone">
								</div>
							 </div> 
							 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="search()">
									<i class="icon-search bigger-200"></i>
									确定
								</button>
								<button class="btn-sm btn-success no-radius" type="button" onclick="cancleSearch()">
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
$(function(){
	var statu = '${statu}';
	var fCity = '${fCity}';
	var sCity = '${sCity}';
	console.log(fCity+sCity+statu)
	$(".datetimepicker").datetimepicker({
		format: "yyyy-mm-dd",
	    autoclose: true,
	    todayBtn: true,
	    pickerPosition: "bottom-left",
	    language:"zh-CN",
	    minView:"month"
	    });
	$("#statu").val(statu).trigger("change");
	$("#fCity").val(fCity).trigger("change");
	$("#sCity").val(sCity).trigger("change");
})


function showModal(){
	openModal("#searchmodal");
}

function cancleSearch(){
	$("#searchmodal").modal("hide");
}

function search(){
	var name = $("#name").val();
	var code = $("#code").val();
	var statu = $("#statu").val();
	var exStartTime = $("#exStartTime").val();
	var exEndTime = $("#exEndTime").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var fCity = $("#fCity").val();
	var sCity = $("#sCity").val();
	var phone = $("#phone").val();
	var url ="<%=path%>/search/redeemCode/findByCondition?name="+name+"&code="+code+"&statu="+statu+"&exStartTime="+exStartTime+"&exEndTime="+exEndTime+"&startTime="+startTime+"&endTime="+endTime+"&fCity="+fCity+"&sCity="+sCity+"&phone="+phone;
	$("#searchFrom").attr("action",url)
	$("#searchFrom").submit();
}

function fCityChange(){
	var id = $("#fCity").val();
	clearSCity();
	console.log(id)
	if(id!='null'){
		$.ajax({
			url:"<%=path%>/system/city/getSCityUsedByFCityId",    //请求的url地址
		    dataType:"json",   
		    async:false,
		    data:{"id":id},
		    type:"GET",   //请求方式
		    success:function(result){
			console.log(result)
		        for(var i=0;i<result.sCity.length;i++){
					$("#sCity").append("<option value='"+result.sCity[i].id+"'>"+result.sCity[i].name+"</option>");
				}
		    },
		    error:function(){
				alert("请求下级城市失败！")
		    }
		});
	}
	$('#sCity').selectpicker('refresh');
}

function clearSCity(){//清空二级城市下拉框所有内容（第一个默认的option不清除）
	$("#sCity").empty();
	$("#sCity").append("<option value='null'>选择市县</option>"); 
}
</script>
</html>
	