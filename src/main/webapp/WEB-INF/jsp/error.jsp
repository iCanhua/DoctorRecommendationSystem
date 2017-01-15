<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>

<div data-role="page">
  <div data-role="header">
  	<a href="<%=request.getContextPath()%>/home" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回主页</a>
    <h1>提示</h1>
  </div>

  <div data-role="main" class="ui-content">
    <center><p>抱歉，什么都没找到!!</p></center>
  </div>

  <div data-role="footer" data-position="fixed" style="text-align:center;">
    <h1>请重新输入</h1>
  </div>
</div> 

</body>
</html>
