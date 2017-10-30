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
					<i class="icon-group"></i> 客户信息查询
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
									</a>&nbsp;&nbsp;<a href="javascript:exportCustomer()" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-share-alt bigger-200">导出查询结果</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>客户电话</th>
											<th>客户姓名</th>
											<th>最近一次充值人员</th>
											<th>最近一次充值时间</th>
											<th>累计兑换积分数量</th>
											<th>剩余积分</th>
											<th>城市</th>
											<th>备注</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${customers.items}" var="item" varStatus="status">
											<tr class="odd gradeX">
												<td>${status.count}</td>
												<td>${item[0].phone}</td>
												<td>${item[0].name}</td>
												 <td>${item[0].latestChargeUser.displayName}</td>
												<td>${fn:substring(item[0].latestChargeTime,0,10)}</td>
												<td>${item[0].totalAddUp}</td>
												<td>${item[0].point}</td>
												<td>
													<c:forEach items="${item[0].customerCityByPriority}" var="city" varStatus="status">
														<c:if test="${status.first}">【</c:if>${city.name}<c:if test="${status.first}">】</c:if>
														<c:if test="${!status.last&&!status.first}">;</c:if>
													</c:forEach>
												</td>
												<td>${item[0].bankAddress}</td>
												<td>
													<p>
														<a href="<c:url value="/search/record/findByCustomerId?id=${item[0].id}"/>" class="btn-sm btn-app btn-primary no-radius">
															<i class="icon-eye-open bigger-200"></i>
															点击查看
														</a>&nbsp;&nbsp;
													</p>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="customers"/>
		        				<c:param name="urlAddress" value="/search/customer/findByCondition"/>
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
				<h3 class="modal-title">客户信息-查询条件</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="searchFrom" action="" method="post">				 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="name">客户姓名：</label>
								<div class="col-sm-9">
									<input type="text" value="${name}" id="name" class="col-xs-10 col-sm-11" name="name">
								</div>
							 </div>
							 
							 <div class="space-4"></div>
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="latestName">最近一次充值人员：</label>
								<div class="col-sm-9">
									<input type="text" value="${latestName}" id="latestName" class="col-xs-10 col-sm-11" name="latestName">
								</div>
							 </div> 
							 
							 <div class="space-4"></div>
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-points">最近一次充值时间：</label>
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
	var fCity = '${fCity}';
	var sCity = '${sCity}';
	$(".datetimepicker").datetimepicker({
		format: "yyyy-mm-dd",
	    autoclose: true,
	    todayBtn: true,
	    pickerPosition: "bottom-left",
	    language:"zh-CN",
	    minView:"month"
	    });
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
	var latestName = $("#latestName").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var fCity = $("#fCity").val();
	var sCity = $("#sCity").val();
	var phone = $("#phone").val();
	var url ="<%=path%>/search/customer/findByCondition?name="+name+"&latestName="+latestName+"&startTime="+startTime+"&endTime="+endTime+"&fCity="+fCity+"&sCity="+sCity+"&phone="+phone;
	$("#searchFrom").attr("action",url);
	$("#searchFrom").submit();
}

function exportCustomer(){
	var name = $("#name").val();
	var latestName = $("#latestName").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var fCity = $("#fCity").val();
	var sCity = $("#sCity").val();
	var phone = $("#phone").val();
	var url ="<%=path%>/search/customer/exportCustomer?name="+name+"&latestName="+latestName+"&startTime="+startTime+"&endTime="+endTime+"&fCity="+fCity+"&sCity="+sCity+"&phone="+phone;
	window.location.href = url ;
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
	