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
					 兑换码发放
				</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
						发放批次： <select class="selectpicker" id="batch" onchange="batchChange()">
							 <c:forEach items="${batch}" var="item">
							  	<option value="${item.id}">${item.batch}</option>
							 </c:forEach>
						 </select>
						
						 <div class="timeSelect input-append date form_datetime datetimepicker" style="float:left">
							 实际发放时间：
							 <input size="16" value="" type="text" id="fafangTime" readonly>
							<span class="add-on"><i class="icon-remove"></i></span>
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
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
$(function(){
	$("#batch").selectpicker({
		'noneSelectedText':'选择批次'
	});
	$(".datetimepicker").datetimepicker({
		format: "yyyy-mm-dd",
	    autoclose: true,
	    todayBtn: true,
	    pickerPosition: "bottom-left",
	    language:"zh-CN",
	    minView:"month"
	    });
})
var tableHeight;
/*function generateSwitch(id,used){
	if(used)
		return "<input value='"+id+"'name='switch"+"' data-on-text='已兑换' data-off-text='未兑换' type='checkbox' checked/>";
	else
		return "<input value='"+id+"'name='switch"+"' data-on-text='已兑换' data-off-text='未兑换' type='checkbox'/>";
}*/ 
function generateSwitch1(id,grant,used){
	if(grant)
		return "<input value='"+id+"'name='switch1"+"' data-on-text='已发放' data-off-text='未发放' type='checkbox' checked "+(used?'disabled':'')+"/>";
	else
		return "<input value='"+id+"'name='switch1"+"' data-on-text='已发放' data-off-text='未发放' type='checkbox' "+ (used?'disabled':'')+"/>";
} 

function generateSwitch2(id,statu){
	if(statu)
		return "<input value='"+id+"'name='switch2"+"' data-on-text='启用' data-off-text='停用' type='checkbox' checked/>";
	else
		return "<input value='"+id+"'name='switch2"+"' data-on-text='启用' data-off-text='停用' type='checkbox'/>";
}

$(function(){
	tableHeight = $(document.body).height()-200;
	initTable();
});

function initTable() {
	var batchId = $("#batch").val();
	$('#tb_rechargeCode').bootstrapTable('destroy'); 
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
       // search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        minimumCountColumns: 2,    //最少允许的列数
        clickToSelect: true,    //是否启用点击选中行
        searchOnEnterKey: false,
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
            title: "发放状态",
            field: "given",
            searchable:false,
            formatter: function (value, row, index) {
            	return generateSwitch1(row.id,value,row.used);
             }
        },
        {
            title: "使用状态",
            field: "used",
            searchable:false,
            formatter: function (value, row, index) {
            	/* return generateSwitch(row.id,value); */
            	return value?"<font color='#00FF00'>已兑换</font>":"<font color='#0000FF'>未兑换</font>"
            }
        }
        ],
        onLoadSuccess:function(){
	      	$("input[name='switch1']").each(function(){
	      		$(this).bootstrapSwitch({});
	      		$(this).on('switchChange.bootstrapSwitch', function (event,state) {
          		   var change = true ;  
            	   var id = $(this).val();
          		   var fafangTime = $("#fafangTime").val();
          		   $.ajax({
          			   url:"<%=path%>/business/rechargeCode/changeGiven",
          			   data:{
          				   id:id,
          				   state:state,
          				   fafangTime:fafangTime
          			   },
          			   async:false,
          			   dataType:"json",
          			   type:"GET",
          			   success:function(result){
          				  $('#tb_rechargeCode').bootstrapTable('refresh'); 
          			   },
 	      			   error:function(){
	      					alert("状态切换失败！")
	      			    }
          		   });
          		   if(!change){
          			   $(this).bootstrapSwitch('state',!state, true);
          		   }
	          });
	      	});
        }
    });
}



function batchChange(){
	initTable();
}
</script>
</html>
	