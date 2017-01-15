<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" style="text/css" href="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css"/>" />
		<link rel="stylesheet" style="text/css" href="<c:url value="/css/doctor.css"/>" />
		<script src="<c:url value="/js/jquery-1.11.3/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.rwdImageMaps.min.js"/>"></script>
	</head>
	<body>
		<div data-role="page">
		  <div data-role="header">
		  	<a href="<%=request.getContextPath()%>/home" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回主页</a>
		  	<h1>有问必答</h1>
		  </div>
		
		  <div data-role="main" class="ui-content">
		    <form action="<%=request.getContextPath()%>/searchSubmit" method="post" data-ajax="false">
		       <div class="ui-field-contain">
		        <label for="info">病症信息:</label>
		        <textarea name="sickness" id="sickness" placeholder="请输入病症信息，例如先天性心脏病，心力衰竭"></textarea>
		       </div>
		       <div class="ui-field-contain">
		        <label for="info">病情介绍:</label>
		        <textarea name="symptom" id="symptom" placeholder="请输入病情描述，例如失眠，头昏"></textarea>
		       </div>
		        <input class="ui-btn" type="submit" value="提交">
		    </form>
		  </div>
		</div>
	</body>
</html>