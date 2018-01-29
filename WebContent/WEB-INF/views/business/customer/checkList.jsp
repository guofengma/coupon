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
				<li class="active"><i class="icon-unlock"></i> 已审核客户</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
							<div class="form-group">
								<div class="col-sm-5" style="padding-bottom:5px">
									<label class="col-sm-3 control-label no-padding-left" for="condition" style="padding-left:0px;text-align:left;">查询条件：</label>
									<input style="height:35px;" type="text" id="condition" class="col-xs-6 col-sm-6" name="condition" value="${condition}"  placeholder="姓名、电话号码模糊查询">
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
							<%-- <div class="btn-group">
									<a href="<c:url value=''/>" class="btn-sm btn-app btn-success no-radius">
										<i class="icon-plus bigger-200">测试按钮</i>
									</a>
							</div> --%>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>姓名</th>
											<th>电话号码</th>
											<th>密码</th>
											<th>剩余优米数量</th>
											<th>审核员工</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${customers.items}" var="item">
											<tr class="odd gradeX">
													<td>${item[0].name }</td>
													<td>${item[0].phone }</td>
													<td>${item[0].password }</td>
													<td>${item[0].point }</td>
													<td>${item[0].checkUser.displayName}</td>
													<td>
														<p>
															<a href="javascript:edit('${item[0].id}')" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-edit bigger-200"></i>
																编辑
															</a>&nbsp;&nbsp;
															<shiro:hasPermission name="recharge:recharge">
																<a href="javascript:showModal('${item[0].name}','${item[0].id}');" class="btn-sm btn-app btn-primary no-radius" >
																	<i class="icon-plus bigger-200"></i>
																	充值
																</a>&nbsp;&nbsp;
															</shiro:hasPermission>
															<a href="<c:url value="/search/record/findByCustomerId?id=${item[0].id}"/>" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-eye-open bigger-200"></i>
																点击查看
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

<div class="modal fade" id="rechargeInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">后台充值</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="rechargeForm"> 
							<input type="hidden" value="" name="id" id="id"/>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="phone">客户姓名：</label>
								<div class="col-sm-9">
									<input type="text" id="name" class="col-xs-10 col-sm-10" name="name" readonly>
								</div>
							 </div> 
							 <div class="space-4"></div> 
							 <div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="point">充值分数：</label>
									<div class="col-sm-9">
										<input type="text" id="point" class="col-xs-10 col-sm-10" name="point">
									</div>
							 </div> 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="recharge()">
									<i class="icon-ok bigger-200"></i>
									充值
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
								<label class="col-sm-3 control-label no-padding-right" for="form-points">优米数量：</label>
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
								<label class="col-sm-3 control-label no-padding-right" for="form-password1">密码：</label>
								<div class="col-sm-9">
									<input type="text" id="form-password1" class="col-xs-10 col-sm-10" name="password">
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
									 <select class="selectpicker" id="sCity" multiple name="sCity">
									 </select>
								</div>
							</div>
							
							<div class="space-4"></div> 	
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-bankAddress">兑换支行：</label>
								<div class="col-sm-9">
									<input type="text" id="form-bankAddress" class="col-xs-10 col-sm-10" name="bankAddress">
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
$(function(){
	$("#fCity").selectpicker({
		'noneSelectedText':'选择省份'
	});
	$("#sCity").selectpicker({
		'noneSelectedText':'选择市县'
	});
});

var isNew = false ;
var id ='';

function showModal(name,id){
	$("#id").val(id);
	$("#name").val(name);
	$("#point").val('');
	openModal("#rechargeInfo");
}

function recharge(){
	var point = $("#point").val();
	var id = $("#id").val();
	$.ajax({
		url:"<%=path%>/business/customer/recharge",    //请求的url地址
	    dataType:"json",   
	    async:false,
		    data:{"id":id,
			"point":point
		},
	    type:"GET",   //请求方式
	    success:function(result){
			$("#rechargeInfo").modal("hide");
			alert("充值成功，等待审核！")
	    },
	    error:function(){
			alert("充值失败！")
	    }
	});
}

function search(){
		var condition = $("#condition").val();
		var url ="<%=path%>/business/customer/list?statu=check&condition="+condition;
		window.location.href = url ;
}

function cancle(){
	$("#rechargeInfo").modal("hide");
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
			$("#form-phone").val(result.phone);
			$("#form-points").val(result.points);
			$("#form-password1").val(result.password);
			$("#form-remark").val(result.remark);
			$("#fCity").val(result.fCityId).trigger("change");
			$('#sCity').selectpicker('val', result.sCityId);
			$("#form-bankAddress").val(result.bankAddress);
	    },
	    error:function(){
			alert("读取客户信息失败！")
	    }
	});
	openModal("#customerInfo");
	id = param ;
	isNew = false ;
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
	/* $('#bank').selectpicker('refresh'); */
}

function clearSCity(){//清空二级城市下拉框所有内容（第一个默认的option不清除）
	$("#sCity").empty();
}

function saveCustomerInfo(){
	$.ajax({
		url:"<%=path%>/business/customer/uniquenessCheck",    //电话唯一性检查
		    dataType:"json",   
		    async:false,
		    data:{
			"id":id,
			"isNew":isNew,
			"phone":$("#form-phone").val(),
			},
		    type:"POST",   //请求方式
		    success:function(result){
				if(result.flag){ //唯一性检查通过，做保存
					console.log(result.flag)
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
						"password":$("#form-password1").val(),
						"remark":$("#form-remark").val(),
						"fCity":$("#fCity").val(),
						"sCity":$("#sCity").val()==null?'null':$("#sCity").val().join(","),
						"bankAddress":$("#form-bankAddress").val()
						},
					    type:"POST",   //请求方式
					    success:function(result){
							if(isNew)
								alert("新建客户信息成功，等待审核！");
							else
								alert("修改客户信息成功");
							window.location.href="<%=path%>/business/customer/list?statu=check&condition="+$("#condition").val();
					    },
					    error:function(){
							alert("保存客户信息失败！")
					    }
					});
				}else{
					alert("该号码已经存在！")
				}
		    },
		    error:function(){
				alert("检查出错！")
		    }
		});
}
</script>
</html>
	