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
				<li class="active"><i class="icon-user"></i> 待审核客户</li>
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
										<i class="icon-plus bigger-200">添加客户</i>
									</a>&nbsp;&nbsp;
									<a href="javascript:multiCheck()" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-ok bigger-200">批量审核</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="customer-table">
									<thead>
										<tr>
											<th>
												<input type="checkbox" id="checkAll" onclick="checkAll()">全选
												<input type="checkbox" id="contraryCheck" onclick="contraryCheck()">反选
											</th>
											<th>姓名</th>
											<th>电话号码</th>
											<th>剩余积分数量</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${customers.items}" var="item">
											<tr class="odd gradeX">
												<td><input type="checkbox" value="${item.id}" name="customer"></td>
												<td>${item.name }</td>
												<td>${item.phone }</td>
												<td>${item.point }</td>
												<td>
														<p>
															<a href="javascript:edit('${item.id}')" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-edit bigger-200"></i>
																编辑
															</a>&nbsp;&nbsp;
															<a href="javascript:del('<c:url value='/business/customer/delete?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-trash bigger-200"></i>
																删除
															</a>&nbsp;&nbsp;
															<a href="javascript:check('<c:url value='/business/customer/check?id=${item.id}'/>');" class="btn-sm btn-app btn-success no-radius" >
																<i class="icon-ok bigger-200"></i>
																审核
															</a>
														</p>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="customers"/>
		        				<c:param name="urlAddress" value="/business/customer/list"/>
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

<div class="modal fade" id="customerInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">客户信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form"> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-name">客户姓名：</label>
								<div class="col-sm-9">
									<input type="text" id="form-name" class="col-xs-10 col-sm-5" name="name">
								</div>
							 </div> 
							 <div class="space-4"></div> 
							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-points">积分数量：</label>
								<div class="col-sm-9">
									<input type="text" id="form-points" class="col-xs-10 col-sm-5" name="points">
								</div>
							 </div> 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-phone">电话号码：</label>
								<div class="col-sm-9">
									<input type="text" id="form-phone" class="col-xs-10 col-sm-5" name="phone">
								</div>
							 </div> 
							 
							<div class="space-4"></div> 	
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-5"> 所在城市： </label>
								<div class="col-sm-9">
									<select class="selectpicker" id="fCity" name="fCity" onchange="fCityChange()">
										 <option value="null">选择省份</option>
										 <c:forEach items="${fCityUsedList}" var="item">
										  	<option value="${item.id}">${item.name}</option>
										 </c:forEach>
									 </select>
									 <select class="selectpicker" id="sCity" name="sCity">
										 <option value="null">选择市县</option>
									 </select>
								</div>
							</div>
							
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-remark">备注信息：</label>
								<div class="col-sm-9">
									<input type="text" id="form-remark" class="col-xs-10 col-sm-5" name="remark">
								</div>
							 </div>
							
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="saveCustomerInfo()">
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
var isNew = false ;
var id ;
function clear(){//清除模态框信息
		$("#form-name").val('');
		$("#form-points").val('');
		$("#form-phone").val('');
		$("#form-remark").val('');
}

function checkAll(){//全选
	   $("#customer-table").find("input[type=checkbox][name=customer]").each(function(){
		$(this).prop("checked", $('#checkAll').is(':checked'));
	});
}

function contraryCheck(){//反选
	$("#customer-table").find("input[type=checkbox][name=customer]").each(function(){
		$(this).prop("checked",!$(this).is(':checked'));
	});
}

function add(){
	$("#customerInfo").modal("show");
	isNew = true ;
}

function edit(param){
	$.ajax({
		url:"<%=path%>/business/customer/getCustomerInfo",
	    dataType:"json",   
	    async:false,
	    data:{"id":param},
	    type:"GET",   //请求方式
	    success:function(result){
	        $("#form-name").val(result.name);
			$("#form-points").val(result.points);
			$("#form-phone").val(result.phone);
			$("#form-remark").val(result.remark);
			$("#fCity").val(result.fCityId).trigger("change");
			$("#sCity").val(result.sCityId).trigger("change");
	    },
	    error:function(){
			alert("读取客户信息失败！")
	    }
	});
	$("#customerInfo").modal("show");
	id = param ;
	isNew = false ;
}

function saveCustomerInfo(){
	$.ajax({
		url:"<%=path%>/business/customer/unCheckSave",    //请求的url地址
	    dataType:"json",   
	    async:false,
	    data:{
		"id":id,
		"isNew":isNew,
		"name":$("#form-name").val(),
		"points":$("#form-points").val(),
		"phone":$("#form-phone").val(),
		"remark":$("#form-remark").val(),
		"fCityId":$("#fCity").val(),
		"sCityId":$("#sCity").val(),
		},
	    type:"POST",   //请求方式
	    success:function(result){
			alert("保存客户信息成功");
			window.location.href="<%=path%>/business/customer/list?statu=uncheck";
	    },
	    error:function(){
			alert("保存客户信息失败！")
	    }
	});
}

function multiCheck(){//批量审核
	var ids = [];
	 $("#customer-table").find("input[type=checkbox][name=customer]").each(function(){
		if($(this).is(':checked'))
			ids.push($(this).val());
	});
	if(ids.length==0){
		alert("请选择客户，再通过审核");
		return false;
	}
	var isChecked =  confirm('确定批量审核勾选的用户吗？', '确认对话框');
	if(isChecked){
		$.ajax({
			url:"<%=path%>/business/customer/multiCheck",    //请求的url地址
		    dataType:"json",   
		    async:false,
		    data:{"ids":ids.join(";")},
		    type:"GET",   //请求方式
		    success:function(result){
				alert("批量审核用户成功");
				window.location.href="<%=path%>/business/customer/list?statu=uncheck";
		    },
		    error:function(){
				alert("批量审核用户失败！")
		    }
		});
	}
}

function check(url){
	var isChecked =  confirm('确定该用户通过审核吗？', '确认对话框');
	if(isChecked){
		window.location.href=url;
	}
}

function del(url){
	var isDel =  confirm('确定删除该用户吗？', '确认对话框');
	if(isDel){
		window.location.href=url;
	}
}

function fCityChange(){
	var id = $("#fCity").val();
	clearSCity();
	if(id!='null'){
		$.ajax({
			url:"<%=path%>/system/city/getSCityUsedByFCityId",    //请求的url地址
		    dataType:"json",   
		    async:false,
		    data:{"id":id},
		    type:"GET",   //请求方式
		    success:function(result){
			console.log(result)
		        for(var i=0;i<result.length;i++){
					$("#sCity").append("<option value='"+result[i].id+"'>"+result[i].name+"</option>");
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
	