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
				<li class="active"><i class="icon-truck"></i> 兑换服务地址</li>
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
										<i class="icon-plus bigger-200">添加</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="bank-table">
									<thead>
										<tr>
											<th>银行名称</th>
											<th>通讯地址</th>
											<th>联系电话</th>
											<th>所属城市</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${banks.items}" var="item">
											<tr class="odd gradeX">
												<td>${item.name }</td>
												<td>${item.address }</td>
												<td>${item.phone }</td>
												<td>
													<c:if test ="${item.city.parent!=null}">
														${item.city.parent.name}
													</c:if>
													${item.city.name}
												</td>
												<td>
														<p>
															<a href="javascript:edit('${item.id}')" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-edit bigger-200"></i>
																编辑
															</a>&nbsp;&nbsp;
															<a href="javascript:del('<c:url value='/business/bank/delete?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-trash bigger-200"></i>
																删除
															</a>
														</p>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="banks"/>
		        				<c:param name="urlAddress" value="/business/bank/list"/>
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

<div class="modal fade" id="bankInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">兑换服务地址信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form"> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-name">银行姓名：</label>
								<div class="col-sm-9">
									<input type="text" id="form-name" class="col-xs-10 col-sm-10" name="name">
								</div>
							 </div> 
							 <div class="space-4"></div> 
							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-address">通讯地址：</label>
								<div class="col-sm-9">
									<input type="text" id="form-address" class="col-xs-10 col-sm-10" name="address">
								</div>
							 </div> 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-phone">联系电话：</label>
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
									 <select class="selectpicker" id="sCity" name="sCity">
										 <option value="null">选择市县</option>
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
								<button class="btn-sm btn-success no-radius" type="button" onclick="saveBankInfo()">
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
		$("#form-address").val('');
		$("#form-phone").val('');
		$("#form-remark").val('');
		$("#fCity").val('null').trigger("change");
		$("#sCity").val('null').trigger("change");
}

function add(){
	clear();
	openModal("#bankInfo");
	isNew = true ;
}

function edit(param){
	clear();
	$.ajax({
		url:"<%=path%>/business/bank/getBankInfo",
	    dataType:"json",   
	    async:false,
	    data:{"id":param},
	    type:"GET",   //请求方式
	    success:function(result){
	        $("#form-name").val(result.name);
			$("#form-address").val(result.address);
			$("#form-phone").val(result.phone);
			$("#form-remark").val(result.remark);
			$("#fCity").val(result.fCityId).trigger("change");
			$("#sCity").val(result.sCityId).trigger("change");
	    },
	    error:function(){
			alert("读取兑换服务地址失败！")
	    }
	});
	openModal("#bankInfo");
	id = param ;
	isNew = false ;
}

function saveBankInfo(){
	$.ajax({
		url:"<%=path%>/business/bank/save",    //请求的url地址
	    dataType:"json",   
	    async:false,
	    data:{
		"id":id,
		"isNew":isNew,
		"name":$("#form-name").val(),
		"address":$("#form-address").val(),
		"phone":$("#form-phone").val(),
		"remark":$("#form-remark").val(),
		"fCityId":$("#fCity").val(),
		"sCityId":$("#sCity").val(),
		},
	    type:"POST",   //请求方式
	    success:function(result){
			alert("保存兑换服务地址成功");
			window.location.href="<%=path%>/business/bank/list?statu=uncheck";
	    },
	    error:function(){
			alert("保存兑换服务地址失败！")
	    }
	});
}

function cancle(){
	$("#bankInfo").modal("hide");
}

function del(url){
	var isDel =  confirm('确定删除该兑换服务地址吗？', '确认对话框');
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
	