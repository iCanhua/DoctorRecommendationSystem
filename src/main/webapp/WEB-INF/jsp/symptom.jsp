<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" style="text/css" href="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css"/>" />
		<script src="<c:url value="/js/jquery-1.11.3/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"/>"></script>
		<script src="<c:url value="/js/jquery.rwdImageMaps.min.js"/>"></script>
	    <style>
	      .choose
	      {
	        background-color: #373737;
	        color: #FFFFFF;
	      }
	      .people_main
		  {
			text-align: center;
		  }
	      #pic 
		  {
			border: none;
			height: auto;
			max-width: 100%;
			width: auto;
		  }
	    </style>
	</head>
	<body>
		<div data-role="page" id="pagethree">
			<div data-role="header">
			   <h1>人体图</h1>
			   <a href="#pagetwo" id="enter" class="ui-btn ui-btn-right ui-corner-all ui-shadow ui-icon-bullets ui-btn-icon-left">列表选择</a>
			</div>
			<div data-role="main" class="people_main">
				<img id="pic" src="<c:url value="/images/man_face.png"/>" width="638" height="1160" usemap="#Map1">
			</div>
			<div data-role="footer">
			   <a id="side" href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-icon-left" side="正面">背面</a>
			   <a id="sex" href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-icon-left" sex="男">男</a>
			</div>
		</div>	
	
		<div data-role="page" id="pageone">
		  <div data-role="header">
		    <a href="#pagethree" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left">人体图选择</a>
		    <h1>症状</h1>
		    <a href="#pagetwo" class="ui-btn ui-btn-right ui-corner-all ui-shadow ui-icon-plus ui-btn-icon-left">列表选择</a>
		  </div>
		  <div data-role="content">
		    <ul data-role="listview" id="symptom">
		    </ul>
		  </div>
		  <form id="form" action="<%=request.getContextPath()%>/questionListJsp" method="post" onsubmit="return diagnose()">
			<input id="symptoms" name="symptoms" type="hidden" value=""/>
			<input class="ui-btn" type="submit" value="诊断">
		  </form>
		
		</div>

		<div data-role="page" id="pagetwo">
		  <div data-role="header">
		    <a href="#pagethree" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left">人体图选择</a>
		    <h1>医学词典</h1>
		    <a href="#pageone" id="enter" class="ui-btn ui-btn-right ui-corner-all ui-shadow ui-icon-check ui-btn-icon-left">确定</a>
		  </div>
		  <div data-role="main">
		    <div class="partone" data-role="collapsible-set">
		      <div id="全身" data-role="collapsible">
		        <h3>全身</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="四肢" data-role="collapsible">
		        <h3>四肢</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="头部" data-role="collapsible">
		        <h3>头部</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="胸部" data-role="collapsible">
		        <h3>胸部</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="腰部" data-role="collapsible">
		        <h3>腰部</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="腹部" data-role="collapsible">
		        <h3>腹部</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="颈部" data-role="collapsible">
		        <h3>颈部</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="排泄部位" data-role="collapsible">
		        <h3>排泄部位</h3>
		        <ul data-role='listview'></ul>
		      </div>
		      <div id="生殖部位" data-role="collapsible">
		        <h3>生殖部位</h3>
		        <ul data-role='listview'></ul>
		      </div>
		    </div>
		  </div>
		</div>



		<div data-role="page" id="pagefour">
			<div data-role="header">
			   <a href="#pagethree" class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left">返回</a>
			   <h1>部位症状</h1>
			   <a href="#pageone" id="enter" class="ui-btn ui-btn-right ui-corner-all ui-shadow ui-icon-check ui-btn-icon-left">确定</a>
			</div>
			
			<div data-role="main" class="ui-content">
				<ul data-role="listview">
				</ul>
			</div>
		</div>
		</body>
		<map name="Map1" id="Map1">
		  <area shape="rect" coords="253,28,364,181" href="#pagefour" part="头部"/>
		  <area shape="poly" coords="174,230,121,360,23,486,7,600,55,628,111,514,145,462,170,405,198,368,211,336,206,212" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="420,210,496,276,556,408,608,472,626,556,611,630,576,628,528,548,484,468,458,418,420,372,412,325" href="#pagefour" part="四肢"/>
		  <area shape="rect" coords="213,216,408,337" href="#pagefour" part="胸部"/>
		  <area shape="rect" coords="234,180,396,216" href="#pagefour" part="颈部"/>
		  <area shape="rect" coords="217,345,406,480" href="#pagefour" part="腹部"/>
		  <area shape="rect" coords="219,485,412,574" href="#pagefour" part="生殖部位"/>
		  <area shape="poly" coords="215,576,199,741,143,1088,210,1132,250,954,270,774,307,628,315,592" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="418,581,484,1092,419,1144,360,797,324,617,320,593" href="#pagefour" part="四肢"/>
		</map>
		<map name="Map2" id="Map2">
		  <area shape="rect" coords="247,32,372,164" href="#pagefour" part="胸部"/>
		  <area shape="rect" coords="238,165,400,212" href="#pagefour" part="颈部"/>
		  <area shape="rect" coords="223,392,419,480" href="#pagefour" part="腰部"/>
		  <area shape="rect" coords="215,496,424,588" href="#pagefour" part="排泄部位"/>
		  <area shape="poly" coords="211,605,151,1093,218,1133,241,985,263,840,287,730,314,625,320,601" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="226,209,137,282,62,472,2,538,13,637,77,600,154,456,203,380,219,341" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="422,210,512,306,584,480,639,557,612,629,555,592,467,432,430,362,416,326" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="424,600,484,1090,422,1138,324,616,323,601" href="#pagefour" part="四肢"/>
		</map>
		<map name="Map3" id="Map3">
		  <area shape="rect" coords="226,33,395,180" href="#pagefour" part="头部"/>
		  <area shape="rect" coords="209,181,416,220" href="#pagefour" part="颈部"/>
		  <area shape="rect" coords="223,246,399,360" href="#pagefour" part="胸部"/>
		  <area shape="rect" coords="214,376,412,481" href="#pagefour" part="腹部"/>
		  <area shape="rect" coords="195,498,435,572" href="#pagefour" part="生殖部位"/>
		  <area shape="poly" coords="195,229,135,380,58,516,3,581,2,638,89,589,158,464,213,357" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="420,218,630,609,616,641,554,584,506,509,463,453,434,392,406,332" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="197,589,235,806,229,1117,295,1118,302,1036,307,880,309,588" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="412,585,391,1110,324,1121,313,588" href="#pagefour" part="四肢"/>
		</map>
		<map name="Map4" id="Map4">
		  <area shape="rect" coords="247,32,387,197" href="#pagefour" part="头部"/>
		  <area shape="rect" coords="219,200,424,224" href="#pagefour" part="颈部"/>
		  <area shape="rect" coords="227,369,411,456" href="#pagefour" part="腰部"/>
		  <area shape="rect" coords="207,478,432,585" href="#pagefour" part="排泄部位"/>
		  <area shape="poly" coords="210,230,7,581,3,644,63,604,231,340" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="428,233,620,588,631,636,578,620,407,360" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="210,596,237,808,227,1106,302,1120,314,592" href="#pagefour" part="四肢"/>
		  <area shape="poly" coords="416,596,391,786,400,1104,324,1116,315,592" href="#pagefour" part="四肢"/>
		</map>
<script>
   
   $(document).on("pagecreate","#pagetwo",function(){
     //添加到已选择的症状列表(列表页面)
     $("#pagetwo #enter").on("tap",function(){
       clonePartOneSymptom();
     });                
   });
   
   $(document).on("pagecreate","#pagethree",function(){
		$("#pic").rwdImageMaps();             
   });
   
   $(document).on("pageinit","#pagefour",function(){
     //添加到已选择的症状列表(人体图页面)
     $("#pagefour #enter").on("tap",function(){
       clonePartTwoSymptom();
     });               
   }); 
   
   function clonePartOneSymptom(){
   	   var $newli = $("#pagetwo .choose").clone();
       $newli.css("background-color","#FFFFFF");
       $newli.css("color","#333333");
       $newli.removeClass("choose");
       $("#symptom").append($newli);
   }
   
    function clonePartTwoSymptom(){
   	   var $newli = $("#pagefour .choose").clone();
       $newli.css("background-color","#FFFFFF");
       $newli.css("color","#333333");
       $newli.removeClass("choose");
       $("#symptom").append($newli);
   }
   
   //为列表(partone)折叠块添加展开事件，动态添加症状
   $(".partone div").on("collapsibleexpand",expand);
   
   //为人体图(parttwo)添加点击事件
   $("map area").click(function(){
      var id = $(this).attr("part");
      $link = "<%=request.getContextPath()%>/getSymptoms?position=" + id;
      $ul = $("#pagefour ul");
      $ul.empty();
      $ul.listview();
      $.post($link,function(data){
 		$.each(data, function(i, item){
   			$ul.append("<li data-icon='false'>"+item+"</li>");
   			$ul.listview("refresh");
 		});
	  });
	  //必须解绑之前绑定的事件
	  $ul.undelegate();
	  $ul.delegate('li','tap',changeColor);
   }); 
   
   //展开事件
   function expand(){
	   	if($(this).hasClass("opened")){
	     	return false;
	     }
	   	$link = "<%=request.getContextPath()%>/getSymptoms?position=" + $(this).attr("id");
	   	$(this).addClass("opened");
	   	var $ul = $(this).find("ul");
	   	$.post($link,function(data){
	 		$.each(data, function(i, item){
	   			$ul.append("<li data-icon='false'>"+item+"</li>");
	   			$ul.listview("refresh");
	 		});
		});
		$ul.delegate('li','tap',changeColor);
   }
   
   //改变li颜色
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
   
   
  
   $("#side").click(function(){
   	if($(this).attr("side")=="正面"){
   		$(this).attr("side","背面");
   		$("#side").text("正面");
   	}else{
   		$(this).attr("side","正面");
   		$("#side").text("背面");
   	}
   		choosePic($(this).attr("side"),$("#sex").attr("sex"));
   });
   
   $("#sex").click(function(){
   	if($(this).attr("sex")=="男"){
   		$(this).attr("sex","女");
   		$("#sex").text("女");
   	}else{
   		$(this).attr("sex","男");
   		$("#sex").text("男");
   	}
   		choosePic($("#side").attr("side"),$(this).attr("sex"));
   });
   
   function choosePic(a,b){
   	if(a=="正面"&&b=="男"){
   		$("#pic").attr("src","<c:url value="/images/man_face.png"/>");
   		$("#pic").attr("usemap","#Map1");
   	}
   	if(a=="正面"&&b=="女"){
   		$("#pic").attr("src","<c:url value="/images/woman_face.png"/>");
   		$("#pic").attr("usemap","#Map3");
   	}
   	if(a=="背面"&&b=="男"){
   		$("#pic").attr("src","<c:url value="/images/man_back.png"/>");
   		$("#pic").attr("usemap","#Map2");
   	}
   	if(a=="背面"&&b=="女"){
   		$("#pic").attr("src","<c:url value="/images/woman_back.png"/>");
   		$("#pic").attr("usemap","#Map4");
   	}
    $("#pic").rwdImageMaps();
   }

   //跳转到问题列表页面
   function diagnose(){
   	if($("#symptom li").length==0){
   		alert("请选择至少一个症状");
   		return false;
   	}
   	$symptom = "";
   	$("#symptom li").each(function(){
   		$symptom += $(this).text()+",";
   	});
   	$("#symptoms").attr("value",$symptom);
   	return true;
   }
   
 </script>
</html>