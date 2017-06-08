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

<title>电影列表</title>
</head>
<body style="font-family:微软雅黑;">
<h3 style="text-align:center;margin-left:-50px">百度糯米,淘票票,娱票儿</h3>
<div id="searchBox" style="margin-top:30px" class="col-lg-8 col-lg-offset-4 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2" >
	<form action="searchMoive" method="post" id="searchForm">
		<input style="display:inline;width:320px" type="text" class="form-control" id="movieName" name="movieName" placeholder="搜电影/影院">  
    	<!-- <input style="display:inline;width:250px" type="date" class="form-control" id="movieDate" name="movieDate" placeholder="请选择时间">  
    	 -->
    	<button type="submit" id="search"  class="btn btn-primary btn-md" >搜索</button>
    	<!-- <input type="submit" id="sub" style="display:none"> -->
    </form>
</div>

<!-- 电影列表 -->
<div id="movieListResult" style="margin-top:30px" class="col-lg-8 col-lg-offset-2 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2" >
<table style="margin-top:40px;text-align:left" class="table table-striped table-hover">
    <caption>电影查询结果</caption>
    <thead>
      <tr>
      	<th>名称</th>
        <th>类型</th>
        <th>时长</th>
        <th>评分</th>
        <th>详情</th>
      </tr>
    </thead>
    <tbody>
     <c:forEach items="${newMovieList}" var="el">
    		<tr id="${el.id}">
    			<td>${el.name}</td>
    			<td>${el.type}</td>
    			<td>${el.last}</td>
    			<td>${el.averageScore}</td>
    			<td>
    			<form action="searchDetail" method="post" id="searchCinemaListForm">
					<input type="text" style="display:none" id="movieId" name="movieId" value=${el.id}>  
    				<input type="text" style="display:none" id="cinemaId" name="cinemaId" value=${newCinemaId}>  
    				
    				<button type="submit" class="btn btn-info btn-xs">详情</button>
    			</form>
    			</td>
    			
    		</tr>
    	
    </c:forEach>
    </tbody>
  </table>

	
	
</div>
	
	
	
</body>

<script src="js/login.js"></script>

</html>