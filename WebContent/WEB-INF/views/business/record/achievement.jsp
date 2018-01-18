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
					业绩统计
				</li>
			</ul>
		</div>
		<div class="row">
            <div class="col-md-12">
            	<div class="portlet box light-grey">
					<div class="portlet-title">
					<table>
						<tr>
							<td>
								<label class="control-label no-padding-right">员工：</label>
							</td>
							<td> <select class="selectpicker" id="yuangong" onchange="conditionChanged()">
										 <c:forEach items="${yuangong}" var="item">
										  	<option value="${item.id}">${item.displayName}</option>
										 </c:forEach>
									 </select>
							</td>
							<td>
								<label class="control-label no-padding-right">&nbsp;&nbsp;&nbsp;&nbsp;时间：</label>
							</td>
							<td>
								<div class="timeSelect input-append date form_datetime datetimepicker" id="datetimepicker1" date-date="">
									<input size="16" type="text" id="startTime" name="startTime" onchange="conditionChanged()" readonly>
									<!-- <span class="add-on"><i class="icon-remove"></i></span> -->
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							</td>
							<td>
								<label class="control-label no-padding-right">&nbsp;&nbsp;至&nbsp;&nbsp;</label>
							</td>
							<td>
								<div class="timeSelect input-append date form_datetime datetimepicker" id="datetimepicker2" date-date="">
									<input size="16" type="text" id="endTime" name="endTime" onchange="conditionChanged()" readonly>
									<!-- <span class="add-on"><i class="icon-remove"></i></span> -->
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							</td>
							<td>
							&nbsp;&nbsp;
							<a href="javascript:exportAchievement()" class="btn-sm btn-app btn-success no-radius">
								<i class="icon-share-alt bigger-200">业绩导出</i>
							</a>&nbsp;&nbsp;
							</td>
						</tr>
					</table>
					</div>
					<div class="portlet-body">
						<table id="tb_achievement"></table>
					</div>
            	</div>
            </div>
		</div>
	</div>	
</div>
</body>
<script type="text/javascript">
var tableHeight;
$(function(){
	$("#yuangong").selectpicker({
		'noneSelectedText':'选择员工'
	});
	var now = new Date();
	var startTime = getLastMonthYestdy();
	var endTime = getCurrentDate();
	$("#startTime").val(startTime);
	$("#endTime").val(endTime);
	$(".datetimepicker").datetimepicker({
		format: "yyyy-mm-dd",
	    autoclose: true,
	    todayBtn: true,
	    pickerPosition: "bottom-left",
	    language:"zh-CN",
	    minView:"month",
	    });
	tableHeight = $(document.body).height()-200;
	initTable();
})

function initTable() {
	var yuangongId = $("#yuangong").val();
	$('#tb_achievement').bootstrapTable('destroy'); 
    $('#tb_achievement').bootstrapTable({
        method: 'get',
        queryParams: { id: yuangongId,
		        	   startTime:$("#startTime").val(),
		        	   endTime:$("#endTime").val()
			        },
        detailView:false,//父子表
        uniqueId: "id",
        toolbar: '#toolbar',    //工具按钮用哪个容器
        height:tableHeight,
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,     //是否显示分页（*）
        sortable: true,      //是否启用排序
        sortOrder: "used asc",     //排序方式
        url: "<%=path%>/business/record/findAchievementByStaff",//这个接口需要处理bootstrap table传递的固定参数
       // search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        minimumCountColumns: 2,    //最少允许的列数
        clickToSelect: true,    //是否启用点击选中行
        searchOnEnterKey: false,
        columns: [
         {
            title: "客户",
            field: "name"
        },
        {
            title: "充值分数",
            field: "points"
        },{
        	 title: "充值类型",
             field: "type"
        },
        {
            title: "时间",
            field: "time",
        }
        ]
    });
}



function conditionChanged(){
	initTable();
}

function exportAchievement(){
	var id = $("#yuangong").val();
	if(id==null||id==""||id=="null"){
		alert("没有选择员工");
		return false;
	}
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var url ="<%=path%>/business/record/exportAchievement?id="+id+"&startTime="+startTime+"&endTime="+endTime;
	window.location.href = url ;	
}

function getCurrentDate(){
	var date = new Date();
    var strYear = date.getFullYear();    
    var strDay = date.getDate();    
    var strMonth = date.getMonth()+1;  
    if(strMonth<10)    
    {    
       strMonth="0"+strMonth;    
    }  
    if(strDay<10)    
    {    
       strDay="0"+strDay;    
    }  
    datastr = strYear+"-"+strMonth+"-"+strDay;  
    return datastr; 
}

function getLastMonthYestdy(){
    var date = new Date();
    var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);  
    var strYear = date.getFullYear();    
    var strDay = date.getDate();    
    var strMonth = date.getMonth()+1;  
    if(strYear%4 == 0 && strYear0 != 0){  
       daysInMonth[2] = 29;  
    }  
    if(strMonth - 1 == 0)  
    {  
       strYear -= 1;  
       strMonth = 12;  
    }  
    else  
    {  
       strMonth -= 1;  
    }  
    strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];  
    if(strMonth<10)    
    {    
       strMonth="0"+strMonth;    
    }  
    if(strDay<10)    
    {    
       strDay="0"+strDay;    
    }  
    datastr = strYear+"-"+strMonth+"-"+strDay;  
    return datastr;  
 }  

</script>
</html>
	