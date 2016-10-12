<%@ page language="java" contentType="text/html;charset=utf-8"    pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gamax" uri="/gamax.tld" %>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<meta content="telephone=no" name="format-detection">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1" />
<title>江湖救急</title>
<gamax:resource src="/css/banner/royalslider.css"/>
<gamax:resource src="/css/banner/skin-white.css"/>
</head>
<body>
	<div class="index-wrap">
        <header>
            <%-- <a class="banner" id="bannerId" href="<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=1" ><img id="imgId" src="<%=request.getContextPath()%>/images/banner/1.jpg"></a>
            <ol id="bannerCtrl" class="flex-control-nav flex-control-paging">
                <li onclick="changeimg(1);"><a id="banner1" class="activated">1</a></li>
                <li onclick="changeimg(2);"><a id="banner2">2</a></li>
                <li onclick="changeimg(3);"><a id="banner3">3</a></li>
			</ol> --%>
            <div class="sliderContainer fullWidth clearfix">
			  <div id="full-width-slider" class="royalSlider heroSlider rsMinW">
			    <div class="rsContent">
			    	<a href="<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=1">
			    		<img class="rsImg" src="<%=request.getContextPath()%>/images/banner/1.jpg" alt="" />
			    	</a>
		    	</div>
			    <div class="rsContent">
			    	<a href="<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=2">
			    		<img class="rsImg" src="<%=request.getContextPath()%>/images/banner/2.jpg" alt="" />
			    	</a>
		    	</div>
		    	<div class="rsContent">
			    	<a href="<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=3">
			    		<img class="rsImg" src="<%=request.getContextPath()%>/images/banner/3.jpg" alt="" />
			    	</a>
		    	</div>
			  </div>
			</div>
        </header>
        <!-- div class="tip-message">
                <span style="border-right:#f2f2f2 solid 1px;padding-right:5px;"><img src="<%=request.getContextPath()%>/images/newindex/r-text.png"></span>
                <span><img src="<%=request.getContextPath()%>/images/newindex/tip.png"></span>
                <span id="info" style="line-height:25px;">张**  130****2389成功借款<b class="r-font">1000</b>元</span>
        </div-->
        <ul>
            <li onclick="submit();" id="jianghujiuji">
                <h3>申请借款</h3>
                <p>
					<c:if test="${status eq 'pending'}">
							提交资料
					</c:if>
					<c:if test="${status eq 'pendingExamine'}">
							资料已提交
					</c:if>
					<c:if test="${status eq 'firstPass'}">
							资料审核中
					</c:if>
					<c:if test="${status eq 'pass'}">
							资料审核通过
					</c:if>
					<c:if test="${status eq 'reject'}">
							资料审核拒绝
							<c:if test="${lastDays gt 0 }">
							<br/>你可以在${lastDays}天以后重新申请
							</c:if>
					</c:if>
					<c:if test="${status eq 'sendBack'}">
							重新提交资料
					</c:if>
				</p>
                <a><img src="<%=request.getContextPath()%>/images/newindex/save.png"></a>
            </li>
            <li onclick="myaccount();" id="wodejiekuan">
                <h3>我的借款</h3>
                <p>查看/取消借款</p>
                <a><img src="<%=request.getContextPath()%>/images/newindex/myloan.png"></a>
          </li>
          <li style="border:none;" onclick="repayment();" id="zizhuhuankuan">
                <h3>自助还款</h3>
                <p>有借有还&nbsp;再借不难</p>
                <a><img src="<%=request.getContextPath()%>/images/newindex/self-repay.png"></a>
          </li>
        </ul>
        <%-- <div class="notice">您当前有<b id="count" class="r-font">1</b>张抵用券<a class="r-notice"><img src="<%=request.getContextPath()%>/images/newindex/r-circle.png"></a></div> --%>
        <a class="notice" href="<%=request.getContextPath()%>/myaccount/coupons.do">您当前有<b id="count" class="r-font">0</b>张抵用券</a>
	</div>
	
<script src="<%=request.getContextPath()%>/js/banner/jquery.royalslider.min.js?v=9.3.6"></script>
<script>
$(document).ready(function($){
	$('#full-width-slider').royalSlider({
		arrowsNav: true,
		loop: true,
		keyboardNavEnabled: true,
		controlsInside: false,
		imageScaleMode: 'fill',
		arrowsNavAutoHide: false,
		autoScaleSlider: true, 
		autoScaleSliderWidth: 1000,     
		autoScaleSliderHeight: 500,
		controlNavigation: 'bullets',
		thumbsFitInViewport: false,
		navigateByClick: true,
		startSlideId: 0,
		autoPlay: {
			enabled: true,
			stopAtAction: false,
			pauseOnHover: false,
			delay:2000,
		},
		transitionType:'move',
		globalCaption: true,
		deeplinking: {
			enabled: true,
			change: false
		},
		imgWidth: 848,
		imgHeight: 424
	});
});
</script>	
	
<script type="text/javascript">
	var riskOrders;
	var i=0;
	$(function(){
		
// 		$.ajax({
<%-- 			url:"<%=request.getContextPath()%>/order/getSuccOrderRandom.do", --%>
// 			type:"get",
// 			async:false,
// 			success:function(result){
// 				result = decodeURIComponent(result);
// 				riskOrders = eval(result);
// 			}
// 		});
		
		//获取当前抵用券的数量
		$.ajax({
			url:"<%=request.getContextPath()%>/buyer/getCouponsCounts.do",
			type:"get",
			async:false,
			success:function(count){
				if(count != null){
					$('#count').html(count);
				}
			}
		});
		
		
	})
	
	function loginout(){
		$.ajax({
			   type: "get",
			   url: "<%=request.getContextPath()%>/login/loginout.do",
			   success: function(msg){
				   top.location='<%=request.getContextPath()%>/login/index.do';
			   }
		});
		
	}
	
    function repayment(){
		top.location='<%=request.getContextPath()%>/repayment/index.do';
	}
	
	function resubmit(){
		top.location='<%=request.getContextPath()%>/repayment/resubmitOrder.do';
	}
	

	function submit(){
		$.ajax({
			   type: "get",
			   url: "<%=request.getContextPath()%>/login/isCanSubmit.do",
			   async: false,
			   success: function(msg){
				   if(msg==true){
					   top.location='<%=request.getContextPath()%>/order/index.do';			   
				   }else{
					   alert('资料不能修改');   
				   }
			   }
		});
	}
	
	function myaccount(){
		top.location='<%=request.getContextPath()%>/myaccount/order.do'; 
	}
	
	//getOrder();
	//轮播数据加载,动态展示借款信息
	//var play = null;
	//play = setInterval("getOrder()",1000*5);
	
	function getOrder(){
		var riskOrder;
		riskOrder = riskOrders[i];
		$('#info').html("");
		$('#info').hide();
		$('#info').animate({marginTop:"11px"});
		if(riskOrder != null){
			$('#info').html(riskOrder.name.substring(0,1) + "**  " + riskOrder.mobile.substring(0,3) + "****" + riskOrder.mobile.substring(7,11) + "成功借款<b class=\"r-font\">" + riskOrder.amount + "元");
		}
		if(i<9){
			i++;
		}else{
			i = 0;
		}
		$('#info').show();
		$('#info').animate({marginTop:"9px"});
		
	}
	
	<%-- var bannerFlag = 2;
	var banner = null;
	banner = setInterval("playBanner()",1000*5);
	function playBanner(){
		if(bannerFlag <= 3){
			if(bannerFlag == 1){
				$("#bannerId").attr("href", '<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=1');
				$("#banner1").addClass("activated");
				$("#banner2").removeClass("activated");
				$("#banner3").removeClass("activated");
			}else if(bannerFlag == 2){
				$("#bannerId").attr("href", '<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=2');
				$("#banner2").addClass("activated");
				$("#banner1").removeClass("activated");
				$("#banner3").removeClass("activated");
			}else{
				$("#bannerId").attr("href", '<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=3');
				$("#banner3").addClass("activated");
				$("#banner1").removeClass("activated");
				$("#banner2").removeClass("activated");
			}
			$("#imgId").attr("src", '<%=request.getContextPath()%>/images/banner/' + bannerFlag +'.jpg');
			bannerFlag++;
		}else{
			$("#imgId").attr("src", '<%=request.getContextPath()%>/images/banner/1.jpg');
			$("#bannerId").attr("href", '<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=1');
			$("#banner1").addClass("activated");
			$("#banner2").removeClass("activated");
			$("#banner3").removeClass("activated");
			bannerFlag = 2;
		}
		
	} --%>
	
	<%-- function  changeimg(i){
		$("#imgId").attr("src", '<%=request.getContextPath()%>/images/banner/' + i +'.jpg');
		if(i == 1){
			$("#bannerId").attr("href", '<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=1');
			$("#banner1").addClass("activated");
			$("#banner2").removeClass("activated");
			$("#banner3").removeClass("activated");
		}else if(i == 2){
			$("#bannerId").attr("href", '<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=2');
			$("#banner2").addClass("activated");
			$("#banner1").removeClass("activated");
			$("#banner3").removeClass("activated");
		}else{
			$("#bannerId").attr("href", '<%=request.getContextPath()%>/buyer/gotoBanner.do?flag=3');
			$("#banner3").addClass("activated");
			$("#banner1").removeClass("activated");
			$("#banner2").removeClass("activated");
		}
	} --%>
	</script>
</body>
</html>
