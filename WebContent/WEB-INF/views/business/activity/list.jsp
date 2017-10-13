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
    <style type="text/css">
   	 	.bootstrap-select .dropdown-toggle{width:158%}
    </style>
    <link rel="stylesheet" href="<%=path %>/assets/ztree/css/metroStyle.css" type="text/css">
	<script src="<%=path %>/assets/ztree/js/jquery.ztree.all.min.js"></script>
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
				<li class="active"><i class="icon-bullhorn"></i>活动管理</li>
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
										<i class="icon-plus bigger-200">添加活动</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>活动名称</th>
											<th>超链接</th>
											<!-- <th>描述信息</th> -->
											<th>banner图</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${activitys.items}" var="item">
											<tr class="odd gradeX">
													<td>${item.name}</td>
													<td>${item.url}</td>
													<%-- <td>${item.description}</td> --%>
													<td><img width="100px" height="100px" src='<%=path%>/img/${fn:replace(item.picPath,"\\","/")}'/></td> 
													<td>
															<p>
																<a href="javascript:edit('${item.id}')" class="btn-sm btn-app btn-primary no-radius">
																	<i class="icon-edit bigger-200"></i>
																	编辑
																</a>&nbsp;&nbsp;							
																<a href="javascript:del('<c:url value='/business/activity/delete?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
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
		        				<c:param name="pageModelName" value="activitys"/>
		        				<c:param name="urlAddress" value="/business/activity/list"/>
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

<div class="modal fade" id="activityInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">活动信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form enctype="multipart/form-data" class="form-horizontal" role="form" id="activityFrom" action="<%=path%>/business/activity/save" method="post">
							<input name="oldId" id="oldId" type="hidden" value="null"/> 
							<input name="fileChanged" id="fileChanged" type="hidden" value="false"/>
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">活动名称：</label>
								<div class="col-sm-9">
									<input type="text" id="name" class="col-xs-10 col-sm-10" name="name" value="${activity.name}">
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">超链接：</label>
								<div class="col-sm-9">
									<input type="text" id="url" class="col-xs-10 col-sm-10" name="url">
								</div>
							 </div> 
					
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-2">选择图片：</label>
								<div class="col-sm-9">
									<input accept="image/*" type="file" id="form-file" class="col-xs-10 col-sm-10" name="file" onchange="imgPreview(this)">
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-2">预览效果：</label>
								<div class="col-sm-9" style="text-align:left">
									<img id="preview" height="150" width="150">
								</div>
							 </div> 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="saveActivityInfo()">
									<i class="icon-ok bigger-200"></i>
									保存
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
		</div>
	</div>			
</div>
</body>
<script type="text/javascript">
var setting = {
		  view: {
	    selectedMulti: true
	  },
	  check: {
	    enable: true
	  },
	  data: {
	    simpleData: {
	      enable: true
	    }
	  },
	  edit: {
	    enable: false
	  }
};



function saveActivityInfo(){
	$("#activityFrom").submit(); 
}

function cancel(){
	$("#activityInfo").modal("hide");
}

function imgPreview(fileDom){
    //判断是否支持FileReader
    if (window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }

    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    if (!imageType.test(file.type)) {
        alert("请选择图片！");
        return;
    }
    //读取完成
    reader.onload = function(e) {
        //获取图片dom
        var img = document.getElementById("preview");
        //图片路径设置为读取的图片
        img.src = e.target.result;
    };
    reader.readAsDataURL(file);
    $("#fileChanged").val('true');
}

function del(url){
	var isDel =  confirm('确定删除该活动吗？', '确认对话框');
	if(isDel){
		window.location.href=url;
	}
}


function add(){
	 $("#name").val('');
	 $("#url").val('');
	 $("#oldId").val('null');
	 document.getElementById("preview").src = "";
	 openModal("#activityInfo");
}

function edit(param){
	$("#oldId").val(param);
	$.ajax({
		url:"<%=path%>/business/activity/getActivityInfo",
	    dataType:"json",   
	    async:false,
		data:{"id":param},
	    type:"GET",   //请求方式
	    success:function(result){
	       $("#name").val(result.name);
		   $("#url").val(result.url);
		   document.getElementById("preview").src = "<%=path%>/img/"+result.picPath;

	    },
	    error:function(){
			alert("读取商品信息失败！")
	    }
	});
	openModal("#activityInfo");
}

</script>
</html>
	