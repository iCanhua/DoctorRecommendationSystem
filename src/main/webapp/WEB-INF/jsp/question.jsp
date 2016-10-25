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
	        <a href="01.html" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
	        <h1>问题</h1>
	      </div>
	      <div data-role="main" class="ui-content">
	        <form method="post" action="demoform.php">
	            <!-- 循环打印问题列表 -->
				<c:forEach items="${questions}" var="question" varStatus="status1">
					<fieldset data-role="controlgroup">
				  		<legend>${question.description}</legend>
				  		<c:forEach items="${question.choices }" var="choice" varStatus="status2">
				  			<label for="${choice }">${choice }</label>
				  			<input type="checkbox" class="answer" id="${choice }" value="${choice }">
				  		</c:forEach>
				  	</fieldset>		          
	            </c:forEach>
	          <a href="javascript:void(0)" class="ui-btn" data-ajax="false" onclick="recommend()">提交</a>
	        </form>
	      </div>
	    </div>
	</body>
	<script>
	  //跳转到推荐医生页面
      function recommend(){
      	var $link = "recommendJsp?symptoms=";
      	//之前患者选择的症状
      	$link += "${symptoms}";
      	$(".answer:checked").each(function(){
      		$link += $(this).attr("value")+",";
      	});
      	location.href = $link;
      	return false;
      }
	</script>
</html>