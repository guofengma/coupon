<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%
	String basePath = request.getContextPath();
    String timeStamp = System.currentTimeMillis()+"";
%>
<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
<div id="navDiv" class="collapse navbar-collapse navbar-ex1-collapse" align="left">
    <ul class="nav navbar-nav side-nav pull-left">  
    	 <li>
    	    <a href="#customer" data-toggle="collapse" class="nav-header collapsed" ><i class="icon-group" ></i> 客户管理 <i class="icon-caret-down"></i></a>
            <ul id="customer" class="nav nav-list collapse" style="height:0px;" >
	            <li>
	           	 	<a href="<c:url value='/business/customer/list?statu=check'/>"><i class="icon-unlock"></i> 已审核<i></i></a>
	        	</li>
	        	<li>
	           	 	<a href="<c:url value='/business/customer/list?statu=uncheck'/>"><i class="icon-lock"></i> 待审核<i></i></a>
	        	</li>
	        	<li>
	           	 	<a href="<c:url value='/business/customer/recharegeCodeGive'/>"><i class="icon-barcode"></i> 发放积分码<i></i></a>
	        	</li>
           </ul>
         </li>
         <shiro:hasPermission name="product:management">
	         <li>
	            <a href="<c:url value='/business/product/list'/>"><i class="icon-gift"></i> 商品管理<i></i></a>
	         </li> 
         </shiro:hasPermission>
         <shiro:hasPermission name="user:management">
	         <li>
	            <a href="<c:url value='/system/user/list'/>"><i class="icon-user"></i> 员工管理<i></i></a>
	         </li> 
         </shiro:hasPermission>
         <shiro:hasPermission name="rechargeCode:apply">
	         <li>
	            <a href="<c:url value='/business/rechargeCodeApply/list'/>"><i class="icon-barcode"></i> 积分码申请<i></i></a>
	         </li> 
         </shiro:hasPermission>
         <shiro:hasPermission name="serviceInfo:deal">
	         <li>
	            <a href="#serviceInfo1" data-toggle="collapse" class="nav-header collapsed" ><i class="icon-phone" ></i> 预约处理 <i class="icon-caret-down"></i></a>
	            <ul id="serviceInfo1" class="nav nav-list collapse" style="height:0px;" >
	            	<li>
	            	    <a href="<c:url value='/business/serviceInfo/undealList'/>"><i class="icon-circle-blank"></i> 待处理<i></i></a>
	            	</li>
	            	<li>
	            	    <a href="<c:url value='/business/serviceInfo/dealList'/>"><i class="icon-circle"></i> 已处理<i></i></a>
	            	</li>
	            </ul>
	            </li> 
         </shiro:hasPermission>
         <li>
            <a href="#search" data-toggle="collapse" class="nav-header collapsed" ><i class="icon-search" ></i> 信息查询 <i class="fa fa-fw fa-caret-down"></i></a>
            <ul id="search" class="nav nav-list collapse" style="height:0px;">
             	<shiro:hasPermission name="search:redeemCode">
	                <li>
	            		<a href="<c:url value='/search/redeemCode/findByCondition'/>"><i class="icon-qrcode"></i> 商品兑换码<i></i></a>
	         		</li> 
         		</shiro:hasPermission>
         		<shiro:hasPermission name="search:customerInfo">
	                <li>
	                    <a href="<c:url value='/search/customer/findByCondition'/>" ><i class="icon-group"></i> 客户信息 </a>
	                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="search:rechargeCode">
	                <li>
	                    <a href="<c:url value='/search/rechargeCode/findByCondition'/>" ><i class="icon-barcode"></i> 积分兑换码 </a>
	                </li>
                </shiro:hasPermission>
           </ul>
         </li>  
          <shiro:hasPermission name="system:management">
	         <li>
	            <a href="#system" data-toggle="collapse" class="nav-header collapsed" ><i class="icon-cog" ></i> 系统管理 <i class="icon-caret-down"></i></a>
	            <ul id="system" class="nav nav-list collapse" style="height:0px;" >
	                <li>
	            		<a href="<c:url value='/system/role/list'/>"><i class="icon-key"></i> 角色管理<i></i></a>
	         		</li> 
	                <li>
	                    <a href="<c:url value='/system/city/list'/>" ><i class="icon-building"></i> 城市管理 </a>
	                </li>
	               <%--  <li>
	                    <a href="<c:url value='/business/bank/list'/>" ><i class="icon-truck"></i> 地址管理 </a>
	                </li> --%>
	                <li>
	                    <a href="<c:url value='/business/rechargeCode/batchlist'/>" ><i class="icon-barcode"></i> 积分码管理 </a>
	                </li>
	                 <li>
	                    <a href="<c:url value='/business/activity/list'/>" ><i class="icon-bullhorn"></i> 活动管理 </a>
	                </li>
	                <li>
	                    <a href="<c:url value='/business/record/achievement'/>" ><i class="icon-bar-chart"></i> 业绩统计 </a>
	                </li>
	          	 </ul>
	         	</li>
	         </shiro:hasPermission>
    </ul>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">消息</h4>
            </div>
            <div class="modal-body">
				有新的预约待处理，<a href="<%=basePath%>/business/serviceInfo/undealList">去处理</a>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script type="text/javascript">
toastr.options = {  
        closeButton: false,  
        debug: false,  
        progressBar: false,  
        positionClass: "toast-bottom-right",  
        onclick: false,  
        showDuration: "300",  
        hideDuration: "1000",  
        timeOut: "3000",  
        extendedTimeOut: "2000",  
        showEasing: "swing",  
        hideEasing: "linear",  
        showMethod: "fadeIn",  
        hideMethod: "fadeOut"  
    };

function checkServiceInfo(){
	$.ajax({
		url:"<%=basePath%>/business/serviceInfo/countUndealService",    //请求的url地址
	    dataType:"json",   
	    async:false,
	    type:"POST",   //请求方式
	    success:function(result){
			if(result.count!=0)
				toastr.info("您有"+result.count+"条预约待处理!"); 
	    },
	    error:function(){
			window.location.href = "<%=basePath%>/index"
	    }
	});
}
checkServiceInfo();
window.setInterval('checkServiceInfo()',1000*60*10);
</script>