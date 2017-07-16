<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!-- CSS -->
<style type="text/css">
    html, body{ margin:0; height:100%; color: #000;}
    .contentDiv{
        font-family: '宋体';
    	font-size: 16px;
    	font-weight: 300;
    	color: #000;
    	line-height: 30px;
    	text-align: left; 
}
    }
</style>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="<%=path %>/assets/bootstrap/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="<%=path %>/assets/bootstrap/css/bootstrap-theme.css" type="text/css">
<link rel="stylesheet" href="<%=path %>/assets/bootstrap/css/bootstrap-select.min.css" type="text/css">
<link rel="stylesheet" href="<%=path %>/assets/font-awesome/css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="<%=path %>/assets/font-awesome/css/font-awesome1.min.css" type="text/css">
<link rel="stylesheet" href="<%=path %>/assets/css/form-elements.css" type="text/css">
<link rel="stylesheet" href="<%=path %>/assets/css/style.css" type="text/css">
<!-- Morris Charts CSS -->
<link rel="stylesheet" href="<%=path %>/assets/bootstrap/css/plugins/morris.css" >
 <!-- Custom CSS -->
<link rel="stylesheet" href="<%=path %>/assets/css/sb-admin.css" >
<%-- <link rel="stylesheet" href="<%=path %>/assets/css/ace.min.css" /> --%>
<%-- <link rel="stylesheet" href="<%=path %>/assets/css/ace-rtl.min.css" /> --%>
<%-- <link rel="stylesheet" href="<%=path %>/assets/css/ace-skins.min.css" /> --%>

<!-- Javascript -->
<script src="<%=path %>/assets/js/jquery-1.11.1.min.js"></script>
<script src="<%=path %>/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/assets/bootstrap/js/bootstrap-select.min.js"></script>
<script src="<%=path %>/assets/js/jquery.backstretch.min.js"></script>
<script src="<%=path %>/assets/js/scripts.js"></script>
<script src="<%=path %>/assets/js/menus.js"></script>
<%-- <script src="<%=path %>/js/main.js"></script> --%>

<%-- <script src="<%=path %>/assets/js/ace-extra.min.js"></script> --%>
<!-- Morris Charts JavaScript -->
<%-- <script src="<%=path %>/assets/js/plugins/morris/raphael.min.js"></script> --%>
<%-- <script src="<%=path %>/assets/js/plugins/morris/morris.min.js"></script> --%>
<%-- <script src="<%=path %>/assets/js/plugins/morris/morris-data.js"></script> --%>

<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<!--[if lte IE 8]>
  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->



<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<%-- <script src="<%=path %>/assets/js/html5shiv.js"></script> --%>
<%-- <script src="<%=path %>/assets/js/respond.min.js"></script> --%>

<script type="text/javascript">
function main_center_load(url){
	$("#main_center").load("<%=path%>"+url);
}
$(function(){
	$("#wrapper").css("height",document.body.scrollHeight);
});
</script>

 