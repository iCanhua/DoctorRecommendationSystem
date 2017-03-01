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
		       <div class="ui-field-contain">
			        <label for="info">病症信息:</label>
			        <textarea name="sickness" id="sickness" placeholder="请输入病症信息，例如先天性心脏病，心力衰竭"></textarea>
		       </div>
		       <div class="ui-field-contain">
			        <label for="info">病情介绍:</label>
			        <textarea name="symptom" id="symptom" placeholder="请输入病情描述，例如失眠，头昏"></textarea>
		       </div>
		       <button class="ui-btn" type="button" onclick="submitText();">匹配</button>
		  </div>
		  
		  <div data-role="popup" id="detailDialog">
			  <a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn ui-icon-delete ui-btn-icon-notext ui-btn-right">关闭</a>
			  
		      <div data-role="header">
		        <h1 id="name">模糊匹配</h1>
		      </div>
		
		      <div data-role="main" class="ui-content">
		        <form method="post" action="<%=request.getContextPath()%>/searchSubmit" data-ajax="false">
			        <fieldset id="content" data-role="controlgroup">
			          <legend>根据输入，系统模糊匹配到如下词条，请选择：</legend>	
			      	</fieldset>
			      	<input type="submit" data-inline="true" value="提交">
			    </form>
		      </div>

		  </div>
		</div>
	</body>
	<script type="text/javascript">
	function submitText(){
		 //显示加载器
	     $.mobile.loading('show', {  
	        text: '加载中...', //加载器中显示的文字  
	        textVisible: true, //是否显示文字  
	        theme: 'a',        //加载器主题样式a-e  
	        textonly: false,   //是否只显示文字  
	        html: ""           //要显示的html内容，如图片等  
	     });
		 $link = "<%=request.getContextPath()%>/parse";
		 $.post($link,
	   		{
	   			description:$("#sickness").val()+","+$("#symptom").val()
	   		},	
	   		function($data){
	   			//隐藏加载器
	   			$.mobile.loading('hide');
	   			$("#content").html("");
	   			$("#content").append("<legend>根据输入，系统模糊匹配到如下词条，请选择：</legend>	");
	   			$.each($data, function($index1, $item){
	   				if($index1=="hasSymptoms"||$index1=="hasBodySigns"||$index1=="hasPathogeny"||$index1=="hasMedicalHistory"){
		   				$.each($item, function($index2, $choose){
		   					if($choose!=null){
		   						$name=$choose.iri.split("#")[1];
		   						$("#content").append("<label for='"+$index2+$name+"'>"+$name+"</label><input type='checkbox' name='"+$index1+"' id='"+$index2+$name+"' value='"+$choose.iri+"'>");
		   						$("#content").trigger("create");
		   					}
				 		});
	   				}
		 		});
	   			$("#detailDialog").popup("open");
		  });
	}
	</script>
</html>