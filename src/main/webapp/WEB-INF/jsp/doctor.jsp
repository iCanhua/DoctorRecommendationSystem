<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
		<script src="/js/jquery-1.11.3/jquery.min.js"></script>
		<script src="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>
	</head>
	<body>
    <div data-role="page" id="pageone">
      <div data-role="header">
        <a href="/symptom" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
        <h1>医生推荐列表</h1>
      </div>
      <div data-role="main" class="ui-content">
        <ul data-role="listview" data-inset="true">
        	<!-- 循环打印推荐医生列表 -->
			<c:forEach items="${doctors}" var="doctor">
			  <li>
			  	<a href="#">
		            <h2>${doctor.name} ${doctor.profession}</h2>
		            <p>${doctor.field}</p>
		            <p>擅长：${doctor.expert}</p>
	            </a>
	          </li>
            </c:forEach>
        </ul>
      </div>
    </div>
	</body>
</html>