<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/init.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>礼品兑换系统</title>
	<link rel="stylesheet" href="<%=path%>/assets/bootstrapTable/dist/bootstrap-table.css">
	<script src="<%=path%>/assets/bootstrapTable/dist/bootstrap-table.js"></script>
	<script src="<%=path%>/assets/bootstrapTable/dist/locale/bootstrap-table-zh-CN.js"></script>
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
				<li class="active"><i class="icon-user"></i> 城市管理</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title"></div>
					<div class="portlet-body"> 
				        <div id="toolbar" class="btn-group">
				            <button id="btn_add" type="button" class="btn btn-default">
				                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				            </button>
				            <button id="btn_edit" type="button" class="btn btn-default">
				                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
				            </button>
				       </div>
        				<table id="tb_citys"></table>
					</div>
            	</div>
            </div>
		</div>
	</div>	
</div>
</body>
<script type="text/javascript">
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_citys').bootstrapTable({
            url: '<%=path%>/system/city/getFCity',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 40],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: true,                   //是否显示父子表
            columns: [{
            	checkbox:true
            },{
                field: 'name',
                title: '省级市名称'
            }, {
                field: 'priority',
                title: '排序'
            }, {
                field: 'used',
                title: '是否开放'
            }],
            onExpandRow: function (index, row, $detail) {
            	oTableInit.InitSubTable(index, row, $detail);
            }
        });
    };
    
    oTableInit.InitSubTable = function (index, row, $detail) {
	    var parentid = row.id;
	    var cur_table = $detail.html('<table></table>').find('table');
	    $(cur_table).bootstrapTable({
	        url: '<%=path%>/system/city/getSCity',
	        method: 'get',
	        queryParams: { id: parentid },
	        clickToSelect: true,
	        detailView:false,//父子表
	        uniqueId: "id",
	        columns: [{
	            checkbox:true	  
	        }, {
	            field: 'name',
	            title: '地级市名称'
	        }, {
	            field: 'priority',
	            title: '排序'
	        }, {
	            field: 'used',
	            title: '是否开放'
	        }],
	        //无线循环取子表，直到子表里面没有记录
	        onExpandRow: function (index, row, $Subdetail) {
	        	oTableInit.InitSubTable(index, row, $Subdetail);
	        }
	    });
}
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};

</script>
</html>
	