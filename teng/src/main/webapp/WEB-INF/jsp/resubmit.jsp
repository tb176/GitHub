<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<meta charset="utf-8">
<meta content="telephone=no" name="format-detection">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1" />
<title>登录</title>
</head>
<body>
<section>
	<div class="authent-mailwrap">		
	 	<p id="errorMsg" class="mt10"><font style="font-size: small;">订单不能修改或者不要重复提交</font></p>
		<div class="clearfix">
		<a class="abtn btn-blue" href="<%=request.getContextPath()%>/login/index.do" style="font-size: medium;">返回</a>
		</div>	
	</div>
</section>
</body>
</html>
