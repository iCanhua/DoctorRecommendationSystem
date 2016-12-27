<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css"/>">
		<script src="<c:url value="/js/jquery-1.11.3/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"/>"></script>
		<style>
			.question {
				color: #ff8f00; 
				font-weight: bold;
			}
			.cell {
				height: 40px;
		        vertical-align: middle; 
		        display: inline-block;
		        line-height: 30px;
			}
		</style>
	</head>
	<body>
	    <div data-role="page" id="pageone">
	      <div data-role="header">
	        <a href="/symptom" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
	        <h1>问题(<span id="nowNumber">1</span>/<span id="allNumber">${fn:length(questions)}</span>)</h1>
	      </div>
	      <div data-role="main" class="ui-content">
            <!-- 循环打印问题列表 -->
			<c:forEach items="${questions}" var="question" varStatus="status1">
				<fieldset data-role="controlgroup" index="${status1.count}">
					
			  		<div class="cell"><img src="<%=request.getContextPath()%>/images/question.png"></div><div class="cell"><legend class="question">${question.description}</legend></div>
			  		<c:forEach items="${question.choices }" var="choice" varStatus="status2">
			  			<label for="${status1.count }${choice }">${choice }</label>
			  			<input type="checkbox" class="answer" id="${status1.count }${choice }" value="${choice }" questionType="${question.type }">
			  		</c:forEach>
			  	</fieldset>		          
            </c:forEach>
	      </div>
	      
		  
          <div data-role="footer" data-position="fixed" style="text-align:center;">
			  
		      <div data-role="controlgroup" data-type="horizontal">
			    <form id="form" action="<%=request.getContextPath()%>/recommendJsp" method="post" onsubmit="return recommend()" data-ajax="false">
				    <input id="symptoms" name="symptoms" type="hidden" value=""/>
				    <input id="bodySigns" name="bodySigns" type="hidden" value=""/>
				    <input id="pathogenys" name="pathogenys" type="hidden" value=""/>
				    <input id="diseases" name="diseases" type="hidden" value=""/>
				    <input id="submit" type="submit" class="ui-btn" value="提交">
			    </form>
		        <button id="previous" class="ui-btn ui-icon-arrow-l">上一页</button>
		        <button id="next" class="ui-btn ui-icon-arrow-r">下一页</button>
		      </div>
		  </div>
	    </div>
	</body>
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
      			$original = $("#pathogenys").attr("value");
      			$new = $original + $(this).attr("value") + ",";
      			$("#pathogenys").attr("value",$new);
      		}
      		if($(this).attr("questionType")=="4"){
      			$original = $("#diseases").attr("value");
      			$new = $original + $(this).attr("value") + ",";
      			$("#diseases").attr("value",$new);
      		}
      	});
      }
	  
	  //初始化分页
	  function start(){
		  $("fieldset").hide();
		  $("fieldset[index=1]").show();
		  $("#previous").attr("disabled","true");
		  $("#submit").parent("div").css('display','none');
	  }
	  
	  function previousPage(){
		  var nowNumber = $("#nowNumber").html();
		  $("#nowNumber").html(nowNumber-1);
		  changePage($("#nowNumber").html());
	  }
	  
	  function nextPage(){
		  var nowNumber = parseInt($("#nowNumber").html());
		  $("#nowNumber").html(nowNumber+1);
		  changePage($("#nowNumber").html());
	  }
	  	  
	  function changePage(nowNumber){
		  var allNumber = $("#allNumber").html();
		  $("fieldset").hide();
		  $("fieldset[index="+nowNumber+"]").show();
		  if(nowNumber==1){
			  $("#previous").attr("disabled","true");
			  return;
		  }
		  if(nowNumber==allNumber){
			  $("#next").attr("disabled","true");
			  $("#submit").parent("div").css('display','block');
			  return;
		  }
		  $("#submit").parent("div").css('display','none');
		  $("#previous").removeAttr("disabled");
		  $("#next").removeAttr("disabled");
	  }
	  
	  $(document).on("pagecreate","#pageone",function(){
		  start();
		  $("#previous").click(previousPage);
		  $("#next").click(nextPage);
	  });
	</script>
</html>