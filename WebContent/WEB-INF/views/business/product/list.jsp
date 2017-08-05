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
				<li class="active"><i class="icon-user"></i> 商品管理</li>
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
										<i class="icon-plus bigger-200">添加商品</i>
									</a>
							</div>
						</div>
						
						<div class="dataTables_wrapper form-inline" role="grid">
							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover" id="data-table">
									<thead>
										<tr>
											<th>商品名称</th>
											<th>所需积分</th>
											<th>活动有效期</th>
											<th>可用城市</th>
											<th>剩余数量</th>
											<th>预览图</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${product.items}" var="item">
											<tr class="odd gradeX">
													<%-- <td>${item.name}</td>
													<td></td>
													<td>2017.07.19--2017.12.19</td>
													<td>
													   <c:forEach items="${item.citys}" var="itemCity">
													   	${itemCity.name}
													   </c:forEach>北京，上海，武汉
													</td>
													<td>12</td>
													<td><img src='<%=path%>/assets/2.jpg'/></td> 
													<td>
															<p>
																<a href="<c:url value='/business/product/edit?id=${item.id}'/>" class="btn-sm btn-app btn-primary no-radius">
																	<i class="icon-edit bigger-200"></i>
																	编辑
																</a>&nbsp;&nbsp;
																<a href="javascript:del('<c:url value='/business/product/delete?id=${item.id}'/>');" class="btn-sm btn-app btn-danger no-radius" >
																	<i class="icon-trash bigger-200"></i>
																	删除
																</a>
															</p>
													</td> --%>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:import url ="../../common/paging.jsp">
		        				<c:param name="pageModelName" value="products"/>
		        				<c:param name="urlAddress" value="/business/product/list"/>
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
<div class="modal fade" id="productInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 1600px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 class="modal-title">商品信息</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form"> 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">商品名称：</label>
								<div class="col-sm-9">
									<input type="text" id="form-username" class="col-xs-10 col-sm-5" name="name" value="${product.name}">
								</div>
							 </div> 
							 <div class="space-4"></div> 
							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1">所需积分：</label>
								<div class="col-sm-9">
									<input type="text" id="form-displayName" class="col-xs-10 col-sm-5" name="points">
								</div>
							 </div> 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-2">选择图片：</label>
								<div class="col-sm-9">
									<input type=file id="form-password" class="col-xs-10 col-sm-5" name="file" onchange="imgPreview(this)">
								</div>
							 </div> 
							 
							 <div class="space-4"></div> 							 
							 <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-2">预览效果：</label>
								<div class="col-sm-9">
									<img id="preview" height="200" width="200">
								</div>
							 </div> 
							 <div class="col-md-12">
								<button class="btn-sm btn-success no-radius" type="button" onclick="saveProductInfo()">
									<i class="icon-edit bigger-200"></i>
									保存修改
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
}

function del(url){
	var isDel =  confirm('确定删除该用户吗？', '确认对话框');
	if(isDel){
		window.location.href=url;
	}
}

function add(){
	$("#productInfo").modal("show");
}
</script>
</html>
	