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
    	    <a href="#system" data-toggle="collapse" class="nav-header collapsed" ><i class="glyphicon glyphicon-list" ></i> 客户管理 <i class="fa fa-fw fa-caret-down"></i></a>
            <ul id="system" class="nav nav-list collapse" style="height:0px;" >
	            <li>
	           	 	<a href="<c:url value='/business/customer/list?statu=check'/>"><i class="glyphicon glyphicon-star"></i> 已审核<i></i></a>
	        	</li>
	        	<li>
	           	 	<a href="<c:url value='/business/customer/list?statu=uncheck'/>"><i class="glyphicon glyphicon-star"></i> 审核中<i></i></a>
	        	</li>
           </ul>
         </li>
         <li>
            <a href="<c:url value=''/>"><i class="glyphicon glyphicon-star"></i> 商品管理<i></i></a>
         </li> 
         <li>
            <a href="<c:url value='/system/user/list'/>"><i class="glyphicon glyphicon-star"></i> 员工管理<i></i></a>
         </li>     
         <li>
            <a href="#system" data-toggle="collapse" class="nav-header collapsed" ><i class="glyphicon glyphicon-list" ></i> 系统管理 <i class="fa fa-fw fa-caret-down"></i></a>
            <ul id="system" class="nav nav-list collapse" style="height:0px;" >
                <li>
            		<a href="<c:url value='/system/role/list'/>"><i class="glyphicon glyphicon-star"></i> 角色管理<i></i></a>
         		</li> 
                <li>
                    <a href="<c:url value='/system/city/list'/>" ><i class="glyphicon glyphicon-pencil"></i> 城市管理 </a>
                </li>
                <li>
                    <a href="<c:url value=''/>" ><i class="glyphicon glyphicon-pencil"></i> 地址管理 </a>
                </li>
                <li>
                    <a href="<c:url value=''/>" ><i class="glyphicon glyphicon-pencil"></i> 充值码管理 </a>
                </li>
           </ul>
         </li>
    </ul>
</div>
<script type="text/javascript">
 
</script>