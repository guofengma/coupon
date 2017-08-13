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
    <style type="text/css">
   	 	.bootstrap-select .dropdown-toggle{width:150%}
    </style>
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
				<li class="active"><i class="icon-lock"></i> 待审核客户</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
						<div class="form-group">
							<div class="col-sm-5" style="padding-bottom:5px">
								<label class="col-sm-3 control-label no-padding-left" for="condition" style="padding-left:0px;text-align:left;">查询条件：</label>
								<input style="height:35px" type="text" id="condition" class="col-xs-6 col-sm-6" name="condition" value="${condition}" placeholder="姓名、电话号码模糊查询">
								<button class="btn-sm btn-success no-radius" type="button" onclick="search()">
									<i class="icon-search bigger-200"></i>
									查询
								</button>
							</div>
							<div class="col-sm-7"></div>
						 </div> 
					</div>
					<div class="portlet-body">
						<div class="table-toolbar" style="text-align: right;">
							<div class="btn-group">
									<a href="javascript:add()" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-plus bigger-200">添加客户</i>
									</a>&nbsp;&nbsp;
									<a href="javascript:multiCheck('true')" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-thumbs-up bigger-200">批量审核通过</i>
									</a>&nbsp;&nbsp;
									<a href="javascript:multiCheck('false')" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-thumbs-down bigger-200">批量审核不通过</i>
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
											<th>所属城市</th>
											<th>兑换服务地址</th>
											<th>处理状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${customers.items}" var="item">
											<tr class="odd gradeX">
												<td>
													<c:if test="${!item.deal}">
														<input type="checkbox" value="${item.id}" name="customer">
													</c:if>
												</td>
												<td>${item.name }</td>
												<td>${item.phone }</td>
												<td>${item.point }</td>
												<td>
													<c:if test ="${item.city.parent!=null}">
														${item.city.parent.name}
													</c:if>
													${item.city.name}
												</td>
												<td>${item.bank.name }</td>
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
															<a href="javascript:edit('${item.id}')" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-edit bigger-200"></i>
																编辑
															</a>&nbsp;&nbsp;
															<a href="javascript:del('<c:url value='/business/customer/delete?condition=${condition}&id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-trash bigger-200"></i>
																删除
															</a>&nbsp;&nbsp;
															<c:if test="${!item.deal}">
																<a href="javascript:check('<c:url value='/business/customer/check?condition=${condition}&id=${item.id}&pass=true'/>');" class="btn-sm btn-app btn-success no-radius" >
																	<i class="icon-thumbs-up bigger-200"></i>
																	审核通过
																</a>&nbsp;&nbsp;
																<a href="javascript:check('<c:url value='/business/customer/check?condition=${condition}&id=${item.id}&pass=false'/>');" class="btn-sm btn-app btn-success no-radius" >
																	<i class="icon-thumbs-down bigger-200"></i>
																	审核不通过
																</a>&nbsp;&nbsp;
															</c:if>
															<c:if test="${item.deal}">
																<a href="<c:url value='/business/customer/requestCheck?condition=${condition}&id=${item.id}'/>" class="btn-sm btn-app btn-success no-radius" >
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
									<input type="text" id="form-name" class="col-xs-10 col-sm-10" name="name">
								</div>
							 </div> 
							 <div class="space-4"></div> 
							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-points">积分数量：</label>
								<div class="col-sm-9">
									<input type="text" id="form-points" class="col-xs-10 col-sm-10" name="points">
								</div>
							 </div> 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-phone">电话号码：</label>
								<div class="col-sm-9">
									<input type="text" id="form-phone" class="col-xs-10 col-sm-10" name="phone">
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
									 <select class="selectpicker" id="sCity" name="sCity" onchange="sCityChange()">
										 <option value="null">选择市县</option>
									 </select>
								</div>
							</div>
							
							<div class="space-4"></div> 	
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">兑换服务：</label>
								<div class="col-sm-9" style="text-align:left">
									 <select class="selectpicker" id="bank" name="bank">
										 <option value="null">选择兑换服务地址</option>
									 </select>
								</div>
							</div>
							
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-remark">备注信息：</label>
								<div class="col-sm-9">
									<input type="text" id="form-remark" class="col-xs-10 col-sm-10" name="remark">
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
		$("#fCity").val('null').trigger("change");
		$("#sCity").val('null').trigger("change");
		$("#bank").val('null').trigger("change");
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
	clear();
	openModal("#customerInfo");
	isNew = true ;
}

function edit(param){
	clear();
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
			$("#bank").val(result.bankId).trigger("change");
	    },
	    error:function(){
			alert("读取客户信息失败！")
	    }
	});
	openModal("#customerInfo");
	//$("#customerInfo").modal("show");
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
		"bankId":$("#bank").val()
		},
	    type:"POST",   //请求方式
	    success:function(result){
			if(isNew)
				alert("新建客户信息成功，等待审核！");
			else
				alert("修改客户信息成功");
			window.location.href="<%=path%>/business/customer/list?statu=uncheck&condition="+$("#condition").val();
	    },
	    error:function(){
			alert("保存客户信息失败！")
	    }
	});
}

function cancle(){
	$("#customerInfo").modal("hide");
}

function multiCheck(pass){//批量审核
	var condition = $("#condition").val();
	var ids = [];
	 $("#customer-table").find("input[type=checkbox][name=customer]").each(function(){
		if($(this).is(':checked'))
			ids.push($(this).val());
	});
	if(ids.length==0){
		alert("请选择客户，再进行审核");
		return false;
	}
	var isChecked =  confirm('确定批量审核勾选的用户吗？', '确认对话框');
	if(isChecked){
		$.ajax({
			url:"<%=path%>/business/customer/multiCheck",    //请求的url地址
		    dataType:"json",   
		    async:false,
		    data:{"ids":ids.join(";"),
				"pass":pass},
		    type:"GET",   //请求方式
		    success:function(result){
				alert("批量审核用户成功");
				window.location.href="<%=path%>/business/customer/list?statu=uncheck&condition="+$("#condition").val();
		    },
		    error:function(){
				alert("批量审核用户失败！")
		    }
		});
	}
}

function check(url){
	var pass = url.substring(url.length-4,url.length)=='true';
	var msg = pass?"你确定通过该用户的审核吗？":"你确定不通过该用户的审核吗？"
	var isChecked =  confirm(msg, '确认对话框');
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
	clearBank();
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
				for(var i=0;i<result.bank.length;i++){
					$("#bank").append("<option value='"+result.bank[i].id+"'>"+result.bank[i].name+"</option>");
				}
		    },
		    error:function(){
				alert("请求下级城市失败！")
		    }
		});
	}
	$('#sCity').selectpicker('refresh');
	$('#bank').selectpicker('refresh');
}

function sCityChange(){
	var id = $("#sCity").val();
	clearBank()
	if(id!='null'){
		$.ajax({
			url:"<%=path%>/system/city/getBankBySCityId",    //请求的url地址
		    dataType:"json",   
		    async:false,
		    data:{"id":id},
		    type:"GET",   //请求方式
		    success:function(result){
				for(var i=0;i<result.length;i++){
					$("#bank").append("<option value='"+result[i].id+"'>"+result[i].name+"</option>");
				}
		    },
		    error:function(){
				alert("请求兑换服务地址失败！")
		    }
		});
	}
	$('#bank').selectpicker('refresh');
}

function clearSCity(){//清空二级城市下拉框所有内容（第一个默认的option不清除）
	$("#sCity").empty();
	$("#sCity").append("<option value='null'>选择市县</option>"); 
}

function clearBank(){
	$("#bank").empty();
	$("#bank").append("<option value='null'>选择服务兑换地址</option>"); 
}

function search(){
	var condition = $("#condition").val();
	var url ="<%=path%>/business/customer/list?statu=uncheck&condition="+condition;
	window.location.href = url ;
}
</script>
</html>
	