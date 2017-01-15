<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>

<div data-role="page" id="pageone">
  <div data-role="header">
    <h1>欢迎来到掌上医生</h1>
  </div>

  <div data-role="main" class="ui-content">
    <div data-role="navbar">
      <ul>
        <li><a href="<%=request.getContextPath()%>/symptom" data-icon="user" data-ajax="false">疾病自查</a></li>
        <li><a href="<%=request.getContextPath()%>/search" data-icon="comment" data-ajax="false">有问必答</a></li>
      </ul>
    </div>
  </div>

  <div data-role="footer" data-position="fixed" style="text-align:center;">
    <h1>请选择功能</h1>
  </div>
</div> 

</body>
</html>
