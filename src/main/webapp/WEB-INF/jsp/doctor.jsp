<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css"></c:url>">
		<script src="<c:url value="/js/jquery-1.11.3/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"/>"></script>
	</head>
	<body>
		<c:choose>
			 <c:when test="${flag}" >
			    <div data-role="page" id="pageone">
			      <div data-role="header">
			        <a href="<%=request.getContextPath()%>/symptom" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
			        <h1>推荐列表</h1>
			      </div>
			      <div data-role="main" class="ui-content">
			        <ul data-role="listview" data-inset="true">
			        	<li data-role="list-divider">推荐医生列表</li>
			        	<!-- 循环打印推荐医生列表 -->
						<c:forEach items="${doctors}" var="entry">
						  <li>
						  	<a href="#">
					            <h2>${fn:split(entry.key.name,'#')[1]}</h2>
					            <p>推荐指数: ${entry.value}</p>
					            <p>医生介绍: ${entry.key.introduction}</p>
				            </a>
				          </li>
			            </c:forEach>
			        </ul>
			        <ul data-role="listview" data-inset="true">
			            <li data-role="list-divider">预测疾病列表</li>
			            <!-- 诊断疾病列表 -->
			            <c:forEach items="${sicks}" var="entry">
						  <li>
						  	<a href="#">
					            <h2>${fn:split(entry.key.diseaseName,'#')[1]}</h2>
					            <p>患病概率: ${entry.value}</p>
					            <p>疾病介绍: ${entry.key.introduction}</p>
				            </a>
				          </li>
			            </c:forEach>       	
			        </ul>
			      </div>
			    </div>
			</c:when>
			<c:otherwise>
				<script type="text/javascript">
					alert("不可重复提交");
					window.location.href="<%=request.getContextPath()%>/symptom";
				</script>
				<a href="javascript:void(0)" class="ui-btn" onclick="alert('aa')">跳转</a>
			</c:otherwise>
		</c:choose>
	</body>
</html>