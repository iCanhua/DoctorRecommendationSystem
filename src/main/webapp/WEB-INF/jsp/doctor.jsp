<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css"></c:url>">
		<link rel="stylesheet" style="text/css" href="<c:url value="/css/doctor.css"/>" />
		<script src="<c:url value="/js/jquery-1.11.3/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"/>"></script>		
	</head>
	<body>
		<c:choose>
			 <c:when test="${flag}" >
			    <div data-role="page" id="pageone">
			      <div data-role="header">
			        <a href="<%=request.getContextPath()%>/symptom" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">重新选择</a>
			        <h1>推荐列表</h1>
			      </div>
			      <div data-role="main" class="ui-content">
			        <ul data-role="listview" data-inset="true">
			        	<li data-role="list-divider">推荐医生列表</li>
			        	<!-- 循环打印推荐医生列表 -->
						<c:forEach items="${doctors}" var="entry">
						  <li>
						  	<a class="detail" href="#pagetwo" name="${fn:split(entry.key.name,'#')[1]}">
								<img src="<c:url value="/images/head.jpg"></c:url>"/>
					            <h2>${fn:split(entry.key.name,'#')[1]}</h2>
					            <p>推荐指数: ${entry.value}</p>
				            </a>
				          </li>
			            </c:forEach>
			        </ul>
			        <ul data-role="listview" data-inset="true">
			            <li data-role="list-divider">预测疾病列表</li>
			            <!-- 诊断疾病列表 -->
			            <c:forEach items="${sicks}" var="entry">
						  <li>
						  	<a href="#pagethree" onclick="checkDetail($(this).children('h2'),$(this).children('.hide'))">
					            <h2>${fn:split(entry.key.diseaseName,'#')[1]}</h2>
					            <p>患病概率: ${entry.value}</p>
 					            <p class="hide" style="display: none;">疾病介绍: ${entry.key.introduction}</p>
				            </a>
				          </li>
			            </c:forEach>       	
			        </ul>
			      </div>
			    </div>
				
				<div data-role="page" id="pagetwo">
					<div data-role="header">
				        <a href="#pageone" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
				        <h1>医生信息</h1>
			      	</div>
			      	
			      	<div data-role="main" class="ui-content">
			      		<div class="ui-grid-solo ui-responsive outside">
				      		<div class="ui-grid-a" class="block">
						      <div class="ui-block-a" id="picDiv"><img src="<c:url value="/images/head.jpg"></c:url>" id="pic"/></div>
						      <div class="ui-grid-solo" height="120px">
						      	<div class="ui-block-a" id="name"></div>
						      	<div class="ui-block-a" id="title"></div>
							  </div>
						    </div>
						    <div class="ui-grid-solo block">
						      <div class="ui-block-a head">所属组</div>
						      <div class="ui-block-a content" id="group"></div>
						    </div>
						    <div class="ui-grid-solo block">
						      <div class="ui-block-a head">擅长</div>
						      <div class="ui-block-a content" id="expert">冠心病</div>
						    </div>
						    <div class="ui-grid-solo block">
						      <div class="ui-block-a head">门诊地址</div>
						      <div class="ui-block-a content">门诊四楼D区</div>
						    </div>
						    <div id="introduction" style="display: none"></div>
						    <a href="#pagethree" class="ui-btn" onclick="checkDetail($('#name'),$('#introduction'))">详细介绍</a>
					    </div>
			      	</div>
				</div>
				
				<div data-role="page" id="pagethree">
					<div data-role="header">
						<a href="#pageone" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
				        <h1 id="detailName"></h1>
			      	</div>
			      	<div data-role="main" class="ui-content">
						<div id="detail" class="explain"></div>
			      	</div>
				</div>
			</c:when>
			<c:otherwise>
				<script type="text/javascript">
					alert("不可重复提交");
					window.location.href="<%=request.getContextPath()%>/symptom";
				</script>
			</c:otherwise>
		</c:choose>
	</body>
	<script type="text/javascript">
		$(".detail").click(function(){
			$.post("<%=request.getContextPath()%>/getDoctorDetails",
			{
				name:$(this).attr("name")	
			},
			function(doctor){
				$("#name").html(doctor.name);
				
				var title = "";
				for(i in doctor.title){
					title += doctor.title[i];
					title += "、";
				}	
				$("#title").html(title);
			
				var group = "";
				for(i in doctor.group){
					group += doctor.group[i];
					group += "、";
				}
				$("#group").html(group);
			
				var expert = "";
				for(i in doctor.expert){
					expert += doctor.expert[i];
					expert += ";";
				}
				for(i in doctor.sexpert){
					expert += doctor.sexpert[i];
					expert += ";";
				}
				$("#expert").html(expert);
			
				$("#introduction").html(doctor.introduction.replace("\n","<br/>"));				
				
			});
		});
		
		function checkDetail(name,detail){
			$("#detailName").html(name.html());
			$("#detail").html(detail.html());
		}
	</script>
</html>