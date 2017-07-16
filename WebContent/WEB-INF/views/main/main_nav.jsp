<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%
	String basePath = request.getContextPath();
    String timeStamp = System.currentTimeMillis()+"";
%>
<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
<div id="navDiv" class="collapse navbar-collapse navbar-ex1-collapse" align="left">
    <ul class="nav navbar-nav side-nav pull-left">      
        <shiro:hasPermission name="user:Management">
            <li>
                <a href="<c:url value='/system/user/list'/>"><i class="icon-user"></i> 用户管理 </a>
            </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="role:Management">
        	<li>
        		<a href="<c:url value='/system/role/list'/>"><i class="icon-key"></i> 角色管理 </a>
            </li> 
        </shiro:hasPermission>
        <shiro:hasPermission name="customer:Management">
        	<li>
        		<a href=""><i class="icon-group"></i> 客户管理 </a>
            </li> 
        </shiro:hasPermission>
        <shiro:hasPermission name="redeem:Management">
        	<li>
        		<a href=""><i class="icon-qrcode"></i> 兑换码管理 </a>
            </li> 
        </shiro:hasPermission>
        <shiro:hasPermission name="product:Management">
        	<li>
        		<a href=""><i class="icon-gift"></i> 商品管理 </a>
            </li> 
        </shiro:hasPermission>
        <shiro:hasPermission name="recharge:Management">
        	<li>
        		<a href=""><i class="icon-money"></i> 后台充值 </a>
            </li> 
        </shiro:hasPermission>
    </ul>
</div>
<script type="text/javascript">
 
</script>