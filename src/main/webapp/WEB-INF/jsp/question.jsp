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
	        <h1>问题</h1>
	      </div>
	      <div data-role="main" class="ui-content">
	        <form>
	            <!-- 循环打印问题列表 -->
				<c:forEach items="${questions}" var="question" varStatus="status1">
					<fieldset data-role="controlgroup">
				  		<legend>${question.description}</legend>
				  		<c:forEach items="${question.choices }" var="choice" varStatus="status2">
				  			<label for="${status1.count }${choice }">${choice }</label>
				  			<input type="checkbox" class="answer" id="${status1.count }${choice }" value="${choice }" questionType="${question.type }">
				  		</c:forEach>
				  	</fieldset>		          
	            </c:forEach>
	          <a href="javascript:void(0)" class="ui-btn" onclick="recommend()">提交</a>
	        </form>
	      </div>
	    </div>
	</body>
	<form id="form" action="/recommendJsp" method="post">
		<input id="symptoms" name="symptoms" type="hidden" value=""/>
		<input id="bodySigns" name="bodySigns" type="hidden" value=""/>
		<input id="pathogenys" name="pathogeny" type="hidden" value=""/>
		<input id="diseases" name="disease" type="hidden" value=""/>
	</form>
	<script>
	  //跳转到推荐医生页面
      function recommend(){
      	$("#symptoms").attr("value","");
      	$("#bodySigns").attr("value","");
      	$("#pathogenys").attr("value","");
      	$("#diseases").attr("value","");
      	$(".answer:checked").each(function(){
      		if($(this).attr("questionType")=="1"){
      			$original = $("#symptoms").attr("value");
      			$new = $original + $(this).attr("value") + ",";
      			$("#symptoms").attr("value",$new);
      		}
      		if($(this).attr("questionType")=="2"){
      			$original = $("#bodySigns").attr("value");
      			$new = $original + $(this).attr("value") + ",";
      			$("#bodySigns").attr("value",$new);
      		}
      		if($(this).attr("questionType")=="3"){
      			$original = $("#pathogeny").attr("value");
      			$new = $original + $(this).attr("value") + ",";
      			$("#pathogeny").attr("value",$new);
      		}
      		if($(this).attr("questionType")=="4"){
      			$original = $("#disease").attr("value");
      			$new = $original + $(this).attr("value") + ",";
      			$("#disease").attr("value",$new);
      		}
      	});
    	$("#form").submit();
      	return false;
      }
	</script>
</html>