<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- ???????????????????????? -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<!-- ? Bootstrap ?? CSS ?? -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- ???Bootstrap???????????? -->


<!-- jQuery??????bootstrap.min.js ???? -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- ??? Bootstrap ?? JavaScript ??<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/4.0.0-alpha.6/css/bootstrap.css"> -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">

<title>电影</title>
</head>
<body style="font-family:微软雅黑;">
<h3 style="text-align:center;margin-left:-50px">百度糯米,淘票票,娱票儿</h3>
<div id="searchBox" style="margin-top:30px" class="col-lg-9 col-lg-offset-3 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2" >
	<form action="searchMoive" method="post" id="searchForm">
		<input style="display:inline;width:250px" type="text" class="form-control" id="movieName" name="movieName" placeholder="请输入电影名">  
    	<input style="display:inline;width:250px" type="date" class="form-control" id="movieDate" name="movieDate" placeholder="请选择时间">  
    	
    	<button type="submit" id="search" onclick="movieSearch()" class="btn btn-primary btn-md" >搜索</button>
    	<!-- <input type="submit" id="sub" style="display:none"> -->
    </form>
</div>


	
<div id="pingtai" style="margin-top:30px" class="col-lg-10 col-lg-offset-1 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2" >
<div id="movieInfo" style="padding:30px;background-color:#726DD1;margin: 0 auto;">
	
	
	 <h1 style="text-align:center;color:white">${movie.name}</h1>
	 
	 <br><br>
	<%-- <div style="color:white;width:200px">
		<span >综合评分：<span>${movie.score} 分</span></span><br>
		<span >电影类型：<span>${movie.type}</span></span><br>
		<span >电影时长：<span>${movie.last}</span></span>
	</div>   --%>
    <%-- <h1 style="text-align:center;color:white">${movie.name}</h1> --%>
	<!-- <div style="margin: 0 auto;width:360px"> -->
	 <table style="text-align:center;color:white" class="col-lg-10 col-lg-offset-1 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2">
	
	 	
		<td><span >综合评分：<span>${movie.score}分</span></span></td>
		<td><span >电影类型：<span>${movie.type}</span></span></td>
		<td >电影时长：<span>${movie.last}</span>分钟</span></td>
		
	</table> 	
	<br><br>
	<!-- </div> -->
	
		
	
</div>
<table style="margin-top:40px;text-align:left" class="table table-striped table-hover">
    <caption>${date}</caption>
    <thead>
      <tr>
      	<th>影院</th>
        <th>地址</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>糯米价格</th>
        <th>淘票票价格</th>
        <th>娱票儿价格</th>
        
      </tr>
    </thead>
    <tbody>
    <tr>
    			<td>南京大美影院</td>
    			<td>南京市鼓楼区XXX</td>
    			
    			<td>10:00</td>
    			<td>12:00</td>
    			<td>23.5</td>
    			<td>32.6</td>
    			<td>12</td>
    			
    		</tr>
    		<tr>
    			<td>南京新街口影院</td>
    			<td>南京市XXX</td>
    			
    			<td>10:00</td>
    			<td>12:00</td>
    			<td>23.5</td>
    			<td>31.6</td>
    			<td>14</td>
    			
    		</tr>
    <%-- <c:forEach items="${OCELInfo}" var="el">
    		<tr>
    			<td>${el.date}</td>
    			<td>${el.orderNum}</td>
    			<td>${el.cancelNum}</td>
    		</tr>
    	
    </c:forEach> --%>
    </tbody>
  </table>
</div>			
	
	
	
</body>

<script src="js/login.js"></script>

</html>