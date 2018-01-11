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
					<i class="icon-barcode"></i> 积分码批次管理
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
										<i class="icon-plus bigger-200">添加批次</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>批次名称</th>
											<th>状态</th>
											<th>有效期</th>
											<th>积分码总数量</th>
											<th>积分码未领取数量</th>
											<th>制作状态</th>
											<th>描述信息</th>
											<th>执行人</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${rechargeCodes.items}" var="item">
											<tr class="odd gradeX">
													<td>${item.batch}</td>
													<td>
														<input name='switch' value='${item.id}' data-on-text='启用' data-off-text='禁用' type='checkbox' <c:if test="${item.statu}">checked</c:if>/>
													</td>
													<td>${fn:substring(item.endTime,0,10)}</td>
													<td>${fn:length(item.children)}</td>
													<td>${fn:length(item.unGivenChildren)}</td>
													<td>
														<c:if test="${item.made}">已制作</c:if>
														<c:if test="${!item.made}">未制作</c:if>
													</td>
													<td>${item.remark}</td>
													<td>登录名:${item.user.name}；用户名:${item.user.displayName}</td>
													<td>
														<p>
															<a href="javascript:edit('${item.id}')" class="btn-sm btn-app btn-primary no-radius">
																<i class="icon-edit bigger-200"></i>
																编辑
															</a>&nbsp;
															<%-- <a href="javascript:del('<c:url value='/business/rechargeCode/deleteBatch?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																<i class="icon-trash bigger-200"></i>
																删除
															</a>&nbsp; --%>
															<%-- <a href="javascript:openMakeModal('${item.id}');" class="btn-sm btn-app btn-success no-radius" >
																<i class="icon-random bigger-200"></i>
																生成积分码
															</a>&nbsp; --%>
															<a href="<c:url value='/business/rechargeCode/list?id=${item.id}'/>" class="btn-sm btn-app btn-success no-radius" >
																<i class="icon-barcode bigger-200"></i>
																管理积分码
															</a>&nbsp;
															<a href="<c:url value='/business/rechargeCode/exportExcel?id=${item.id}'/>" class="btn-sm btn-app btn-success no-radius" >
																<i class="icon-share-alt bigger-200"></i>
																导出为excel
															</a>&nbsp;
														</p>
													</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="rechargeCodes"/>
		        				<c:param name="urlAddress" value="/business/rechargeCode/batchlist"/>
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
<%-- 
<div class="modal fade" id="makemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">制作积分码</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="makeFrom" action="<%=path%>/business/rechargeCode/makerechargeCode" method="post">
							<input name="fatherId" id="fatherId" type="hidden" value=""/> 					 
							 
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-points">积分分值：</label>
								<div class="col-sm-9">
									<input type="text" id="form-points" class="col-xs-10 col-sm-10" name="points">
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-count">制作数量：</label>
								<div class="col-sm-9">
									<input type="text" id="form-count" class="col-xs-10 col-sm-10" name="count">
								</div>
							 </div> 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="make()">
									<i class="icon-ok bigger-200"></i>
									制作
								</button>
								<button class="btn-sm btn-success no-radius" type="button" onclick="cancleMake()">
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
</div> --%>

<!-- 批次信息模态框 -->
<div class="modal fade" id="batchInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">积分码批次信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="batchFrom" action="<%=path%>/business/rechargeCode/batchSave" method="post">
							<input name="oldId" id="oldId" type="hidden" value="null"/>							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="points">生成方式：</label>
								<div class="col-sm-9">
									<label class="col-sm-5 no-padding-right"><input type="radio" name="source" value="1">后台生成</label>
									<label class="col-sm-5 no-padding-right"><input type="radio" name="source" value="2">前台生成</label>
								</div>
							</div> 
							 
							<div class="space-4"></div> 	
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">批次信息：</label>
								<div class="col-sm-9">
									<input type="text" id="batch" class="col-xs-10 col-sm-10" name="batch">
								</div>
							 </div> 
							 
							 <div class="source">
								 <div class="space-4"></div> 	
								 <div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="points">积分分值：</label>
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
//选择前台、后台生成的时候，隐藏现实div
$("input[name='source']").click(function(){
	if($(this).val()=='1')
		$(".source").show();
	if($(this).val()=='2')
		$(".source").hide();
});

$("input[name='switch']").each(function(){
	$(this).bootstrapSwitch();
	$(this).on('switchChange.bootstrapSwitch', function (event,state) {
		$.ajax({
			url:"<%=path%>/business/rechargeCode/changeBatchState",
		    dataType:"json",   
		    async:false,
			data:{	"id":$(this).val(),
					"state":state
				},
		    type:"GET",   //请求方式
		    success:function(result){
		      	alert(result.msg);
		    },
		    error:function(){
				alert("改变批次可用状态失败！")
		    }
		});
	});
});

$("#datetimepicker").datetimepicker({
	format: "yyyy-mm-dd",
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left",
    language:"zh-CN",
    minView:"month"
    });

function add(){
	$(".source").show();
	$("input[name='source']").each(function(){  
		$(this).attr("disabled",false);
	});
	document.getElementById("points").readOnly=false;
	document.getElementById("count").readOnly=false;
	$("input[name='source'][value='1']").prop("checked",true); 
	$("#oldId").val('null');
	$("#batch").val('');
	$("#points").val('');
	$("#count").val('');
	$("#remark").val('');
	$("#endTime").val('');
	openModal("#batchInfo");
}

function cancle(){
	$("#batchInfo").modal("hide");
}

function edit(param){
	$("#oldId").val(param);
	$.ajax({
		url:"<%=path%>/business/rechargeCode/editBatch",
	    dataType:"json",   
	    async:false,
		data:{"id":param},
	    type:"GET",   //请求方式
	    success:function(result){
			$("input[name='source']").each(function(){  
				$(this).attr("disabled",false);
			});
		   $("input[name='source'][value='"+result.source+"']").prop("checked",true); 
	       $("#batch").val(result.batch);
		   $("#remark").val(result.remark);
		   $("#endTime").val(result.endTime);
		   if(result.source=="1"){
			   $("input[name='source'][value='1']").prop("checked",true); 
			   $("input[name='source'][value='2']").attr("disabled", true);
			   $(".source").show();
			   $("#points").val(result.points);
			   $("#count").val(result.count);
			   document.getElementById("points").readOnly = true;
			   document.getElementById("count").readOnly = true;
		   }
		   if(result.source=="2"){
			   $("input[name='source'][value='2']").prop("checked",true); 
			   $("input[name='source'][value='1']").attr("disabled", true);
			   $(".source").hide();
		   }	   
	    },
	    error:function(){
			alert("读取批次信息失败！")
	    }
	});
	openModal("#batchInfo");
}

function saveBatchInfo(){
	$("#batchFrom").submit();
}

function del(url){
	var isDel =  confirm('确定删除该批次的兑换码吗？', '确认对话框');
	if(isDel){
		window.location.href=url;
	}
}

function openMakeModal(param){
	$("#form-count").val('');
	$("#form-points").val('');
	$("#fatherId").val(param);
	//$("#makemodal").modal("show");
	openModal("#makemodal");
}

function cancleMake(){
	$("#makemodal").modal("hide");
}

function back(param){
	window.location.href="<%=path%>/business/rechargeCode/list?id="+param;
}

function make(){
	$("#makeFrom").submit();
}
</script>
</html>
	