<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css">
		<script src="/js/jquery-1.11.3/jquery.min.js"></script>
		<script src="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>
    <style>
      .choose{
        background-color: #373737;
        color: #FFFFFF;
      }
    </style>
	</head>
	<body>

<div data-role="page" id="pageone">
  <div data-role="header">
    <a href="#" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left">主页</a>
    <h1>症状</h1>
    <a href="#pagetwo" class="ui-btn ui-btn-right ui-corner-all ui-shadow ui-icon-plus ui-btn-icon-left">选择</a>
  </div>
  <div data-role="content">
    <ul data-role="listview" id="symptom">
    </ul>
  </div>
  <a href="javascript:void(0)" class="ui-btn" data-ajax="false" onclick="diagnose()">诊断</a>

</div>

<div data-role="page" id="pagetwo">
  <div data-role="header">
    <h1>医学词典</h1>
    <a href="#pageone" id="enter" class="ui-btn ui-btn-right ui-corner-all ui-shadow ui-icon-check ui-btn-icon-left">确定</a>
  </div>
  <div data-role="main">
    <div class="part" data-role="collapsible-set">
      <div id="头部" data-role="collapsible">
        <h3>头部</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="颈部" data-role="collapsible">
        <h3>颈部</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="胸部" data-role="collapsible">
        <h3>胸部</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="背部" data-role="collapsible">
        <h3>背部</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="上肢" data-role="collapsible">
        <h3>上肢</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="腹部" data-role="collapsible">
        <h3>腹部</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="腰部" data-role="collapsible">
        <h3>腰部</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="盆骨" data-role="collapsible">
        <h3>盆骨</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="下肢" data-role="collapsible">
        <h3>下肢</h3>
        <ul data-role='listview'></ul>
      </div>
      <div id="其他" data-role="collapsible">
        <h3>其他</h3>
        <ul data-role='listview'></ul>
      </div>
    </div>
  </div>
</div>
	</body>
	<script>
      $(document).on("pageinit","#pagetwo",function(){
        //添加到已选择的症状列表
        $("#enter").on("tap",function(){
          var $newli = $("#pagetwo .choose").clone();
          $newli.css("background-color","#FFFFFF");
          $newli.css("color","#333333");
          $("#symptom").empty().append($newli);
        });                
      });
      function changeColor(){
      	//选中效果，背景变黑
        if(!$(this).hasClass("choose")){
         $(this).css("background-color","#373737");
         $(this).css("color","#FFFFFF");
         $(this).addClass("choose");
       }else{
         //取消选中，背景变白
         $(this).css("background-color","#FFFFFF");
         $(this).css("color","#333333");
         $(this).removeClass("choose");
       }
      }
      //为所有折叠块添加展开事件，动态添加症状
      $(".part div").on("collapsibleexpand",function(){
        if($(this).hasClass("opened")){
        	return false;
        }
      	$link = "/getSymptoms?" + $(this).attr("id");
      	$(this).addClass("opened");
      	var $ul = $(this).find("ul");
      	$.getJSON($link,function(data){
    		$.each(data, function(i, item){
      			$ul.append("<li data-icon='false'>"+item+"</li>");
      			$ul.listview("refresh");
    		});
  		});
  		$ul.delegate('li','tap',changeColor);
      });
      //跳转到问题列表页面
      function diagnose(){
      	var $link = "questionListJsp?symptoms=";
      	$("#symptom li").each(function(){
      		$link += $(this).text()+",";
      	});
      	location.href = $link;
      	return false;
      }
    </script>
</html>