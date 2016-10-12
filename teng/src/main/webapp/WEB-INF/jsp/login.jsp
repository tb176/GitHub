<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gamax" uri="/gamax.tld" %>

<html>
<head>
<meta charset="utf-8">
<meta content="telephone=no" name="format-detection">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1" />
<title>登录</title>
</head>
<body>
	<form id="form" action="<%=request.getContextPath()%>/login/login.do" method="post">
	<div class="regist-wrap">
		<p><span style="color: red;font-size: small;"><c:out value="${domain.errorMsg}"></c:out></span></p> 
		<div id="mobileDiv" class="inputbox">
			<input id="mobile" name="mobile" value="<c:out value="${domain.mobile}"/>" type="text" placeholder="请输入您的手机号" validate="[{'type':'tel','msg':'电话格式不对','require':false}]" >
		</div>
		<div class="inputbox inputbox01">
			<input id="pinCode" name="pinCode" type="text" value="<c:out value="${domain.pinCode}"/>"  placeholder="请输入短信验证码"  validate="[{'type':'number','value':'6,6','msg':'验证码格式不对'}]">
			<a href="#" id="sendPinCode" class="btn-bgblue" onclick="sendPinCode();">免费获取验证码</a>
		</div>
		<h3 style="margin-bottom:10px;font-size: 14px;">推荐人信息（选填）</h3>
        <div class="inputbox">
			<input type="text" id="referrer" name="referrer" placeholder="请输入推荐人手机号"  validate="[{'type':'tel','msg':'电话格式不对','require':false}]">
		</div>
		<ul style="margin-bottom:10px;font-size: 14px;color:#497CBD;">
			<li style="list-style-type:disc;margin-left:15px;">新用户成功登陆后,可以得到10元抵用劵;</li>
			<li style="list-style-type:disc;margin-left:15px;">填写推荐人手机号并且成功借款，推荐人也可以获得10元抵用券</li>
		</ul>
		<div><a href="#" class="btn btn-blue" onclick="validate();">确&nbsp;认&nbsp;登&nbsp;录</a></div>
		
	</div>
	</form>
<script type="text/javascript">

var wait=60;    
function time(o) {    
        if (wait == 0) {    
        	o.attr('onclick', 'javascript:sendPinCode();');
            o.html("免费获取验证码");
            wait=60;
        } else {    
            o.attr('onclick', 'javascript:void(0);');
            o.html(wait+"秒后可点击");    
            wait--;    
            setTimeout(function() {    
                time(o);    
            },    
            1000);    
        }    
}    


function sendPinCode(){
	if($('#mobile').val()==""||$('#mobileDiv').val()==null){
		alert("请输入手机号码");
		return;
	}
	if(!$('#mobileDiv').validate()){
		 return;
	}
	
	var sendPinCode = $('#sendPinCode');
	
	time(sendPinCode);
	$.ajax({
		   type: "POST",
		   url: "<%=request.getContextPath()%>/login/sendPincode.do",
		   data:{"mobile":$('#mobile').val(),"pinCode":sendPinCode.val()},
		   dataType:"json",
		   success: function(msg){
		   }
	});
}



function validate(){
	
	var tel = $('#mobile').val();
	if(tel.indexOf(":")>0){
		tel = tel.substr(tel.indexOf(":")+1);
		$('#form').submit();
     	return true;
	}
	
	
	if(!$('#form').validate()){
		 return;
	}
	
	
	var isExist = false;
	var referrer = $('#referrer').val();
	if(referrer.length > 0){
		$.ajax({
			 type:'POST',
			 url:"<%=request.getContextPath()%>/login/checkBuyerIsExist.do",
			 dataType:'json',
			 data:{"mobile":$('#referrer').val()},
			 success:function(data){
				 isExist = data;
				 if(isExist){
					 $('#form').submit();
					 return true;
				 }
				 else{
					 alert("该推荐人手机号不存在！");
					 $("#referrer").parent().css("border"," 2px red solid");
					 return false;
				 }
			 }
		 });
		
	}else{
		$('#form').submit();
	}
	 
	
	 
	
	
}


</script>
</body>
</html>
