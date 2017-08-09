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
	<link rel="stylesheet" href="<%=path%>/assets/bootstrapTable/dist/bootstrap-table.css">
	<script src="<%=path%>/assets/bootstrapTable/dist/bootstrap-table.js"></script>
	<script src="<%=path%>/assets/bootstrapTable/dist/locale/bootstrap-table-zh-CN.js"></script>
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
				<li>
					<a href='<c:url value="/business/rechargeCode/batchlist"/>'><i class="icon-qrcode"></i> 积分批次码管理</a>
				</li>
				<li class="active">
					${batch.batch}--积分码管理
				</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
					</div>
					<div class="portlet-body">
						<table id="tb_rechargeCode"></table>
					</div>
            	</div>
            </div>
		</div>
	</div>	
</div>
</body>
<script type="text/javascript">
var tableHeight;
function generateSwitch(id,used){
	if(used)
		return "<input value='"+id+"'name='switch"+"' data-on-text='已兑换' data-off-text='未兑换' type='checkbox' checked/>";
	else
		return "<input value='"+id+"'name='switch"+"' data-on-text='已兑换' data-off-text='未兑换' type='checkbox'/>";
}

$(function(){
	tableHeight = $(document.body).height()-200;
	initTable();
});

function initTable() {
	var batchId = '${batch.id}';
    $('#tb_rechargeCode').bootstrapTable({
        method: 'get',
        queryParams: { id: batchId },
        detailView:false,//父子表
        uniqueId: "id",
        toolbar: '#toolbar',    //工具按钮用哪个容器
        height:tableHeight,
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,     //是否显示分页（*）
        sortable: true,      //是否启用排序
        sortOrder: "used asc",     //排序方式
        url: "<%=path%>/business/rechargeCode/getRechargeCodeByBatch",//这个接口需要处理bootstrap table传递的固定参数
        search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        minimumCountColumns: 2,    //最少允许的列数
        clickToSelect: true,    //是否启用点击选中行
        searchOnEnterKey: false,
        search:true,
        columns: [
         {
            title: "兑换码",
            field: "code",
            searchable:true
        },
        {
            title: "积分分值",
            field: "points",
            searchable:false
        },
        {
            title: "状态",
            field: "used",
            searchable:false,
            formatter: function (value, row, index) {
            	return generateSwitch(row.id,value);
            }
        },{
        	title: "操作",
            field: "used",
            searchable:false,
            formatter: function (value, row, index) {
            	if(value)
            		return '';
            	else
            		return "<a href=\"javascript:deleterechargeCode('"+row.id+"')\" class='btn-sm btn-app btn-danger no-radius'><i class='icon-trash bigger-200'></i>删除</a>&nbsp;&nbsp;"
            				+"<a href=\"javascript:editrechargeCode("+row.id+"')\" class='btn-sm btn-app btn-primary no-radius'><i class='icon-edit bigger-200'></i>修改</a>";
            		
            }
        }
        ],
        onLoadSuccess:function(){
	      	  $("input[name='switch']").each(function(){
	      		$(this).bootstrapSwitch({
	      			disabled:true
	      		});
	         });
        },
        onSearch: function (text) {
        	 $("input[name='switch']").each(function(){
 	      		$(this).bootstrapSwitch({
 	      			disabled:true
 	      		});
 	         });
        },

    });
}

function deleterechargeCode(param){
	var isDel =  confirm('确定删除该兑换码吗？', '确认对话框');
	if(isDel){
		$.ajax({
			url:"<%=path%>/business/rechargeCode/deleterechargeCode",
		    dataType:"json",   
		    async:false,
			data:{"id":param},
		    type:"GET",   //请求方式
		    success:function(result){
		       	 $('#tb_rechargeCode').bootstrapTable('refresh');   
		    },
		    error:function(){
				alert("读取批次信息失败！")
		    }
		});
	}
}
</script>
</html>
	