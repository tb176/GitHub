package com.mo9.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.IllegalDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mo9.data.cashmere.bean.CashBankCardInfo;
import com.mo9.data.cashmere.bean.CashDeal;
import com.mo9.data.cashmere.bean.CashDeal.CashmereDealType;
import com.mo9.data.cashmere.bean.CashInvitation;
import com.mo9.data.cashmere.bean.CashInvitation.InvitationStatus;
import com.mo9.data.cashmere.bean.CashOutOrder;
import com.mo9.data.cashmere.bean.CashOutOrder.CashOutOrderStatus;
import com.mo9.data.cashmere.bean.CashUser;
import com.mo9.data.cashmere.bean.RiskBuyer;
import com.mo9.data.cashmere.dao.ICashOutOrderDao;
import com.mo9.data.cashmere.dto.CashOutOrderDto;
import com.mo9.data.cashmere.dto.DealAcctingDto;
import com.mo9.data.cashmere.dto.InvitationDto;
import com.mo9.data.cashmere.service.ICashInvitationService;
import com.mo9.data.cashmere.service.ICashOutOrderEngine;
import com.mo9.data.cashmere.service.ICashmereActionHookService;
import com.mo9.data.cashmere.service.ICashmereBankCardInfoService;
import com.mo9.data.cashmere.service.ICashmereDealEngine;
import com.mo9.data.cashmere.service.ICashmereUserService;
import com.mo9.web.vo.DrawCashVo;
import com.mo9.webUtils.GetRequest;
import com.mo9.webUtils.MD5;
import com.mo9.webUtils.Md5Encrypt;
import com.mo9.webUtils.Page;
import com.mo9.webUtils.RandomUtils;
import com.mo9.webUtils.RenderUtil;
import com.mo9.webUtils.memcache.Mo9MemcachedClient;

/**
 * 分享提现的controller
 * @author xgao
 *
 */
@Controller
@RequestMapping(value = "/cashMere")
public class CashMereController {
	private static Logger logger = Logger.getLogger(CashMereController.class);
	
	@Autowired
	private ICashOutOrderEngine cashOutOrderEngine;
	@Autowired
	private ICashmereActionHookService hookService;//为邀请者计算奖励接口
	
	@Autowired
	private ICashmereUserService cashmereUserService;//邀请有礼用户
	
	@Autowired
	private ICashInvitationService cashInvitationService;//邀请有礼邀请关系
	
	@Autowired
	private ICashmereDealEngine cashmereDealEngine;
	
	@Autowired
	private ICashOutOrderDao cashOutOrderDao;
	
	@Resource
	private Mo9MemcachedClient mo9MemcachedClient;
	
	
	@Autowired
	private ICashmereBankCardInfoService cashmereBankCardInfoService;
	
	private int width = 136;//定义图片的width
	
	private int height = 40;//定义图片的height
	
	private int codeCount = 4;//定义图片上显示验证码的个数
	
	private int xx = 25;
	
	private int fontHeight = 25;
	
	private int codeY = 30;
	
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	      'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	      'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	private static String[] segmentArr = {"134","135","136","137","138","139","150","151","152","154","157","158","159","130","131","132","155","156","133","153","189"};
	
	private static String[] msgArr = {"&nbsp;&nbsp;成功邀请一位好友注册+1元&nbsp;&nbsp;","&nbsp;&nbsp;成功邀请一位好友借款+10元&nbsp;&nbsp;","&nbsp;&nbsp;成功邀请一位好友还款+10元&nbsp;&nbsp;","&nbsp;&nbsp;成功提现到账&nbsp;&nbsp;"}; 
	
	private static final String cashKey = "cashmere";//签名的key
	
	private static final String share_domain = "https://mo9intercept.share.cashmere";//app拦截的域名
	
	
	/**
	 * 邀请有礼首页
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/longInvite.do")
	public ModelAndView longInvite(HttpServletRequest request,HttpSession session) throws Exception{
		ModelAndView view = new ModelAndView("/mereCash/long-invite");
		String cashmereMobile = request.getParameter("cashmereMobile");//邀请人手机号码
		String cashmereDeviceId = request.getParameter("cashmereDeviceId");//邀请人设备号
		String cashmerePlatform = request.getParameter("cashmerePlatform");//邀请人设备平台
		String sign = request.getParameter("sign");//签名
		
		String key = cashmereMobile+cashmereDeviceId+cashmerePlatform+cashKey;
		String newSign = Md5Encrypt.encrypt(key);
		logger.info("==[onRegister]==APP签名==sign="+sign+" ===ownSign="+newSign);
		if(!sign.equalsIgnoreCase(newSign)){
			logger.info("==[longInvite]==mobile="+cashmereMobile+"签名错误~");
			return new ModelAndView("/mereCash/common-error");//公共错误页面
		}
		logger.info("==[longInvite]==mobile="+cashmereMobile+"签名验证成功~");
		String share_url = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/"))+"/newBenefit.do";//分享好友url
		String qrcode_url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/"))+"/invite.do";//微信二维码url
		//String share_domain = "https://mo9intercept.share.cashmere";//分享域名
		String result = share_domain+"?share_url="+share_url+"&qrcode_url="+qrcode_url+"&title=你有10块钱等待领取&desc=真朋友就是钱借你花,没钱帮你想办法的那个人";
		BigDecimal balance = new BigDecimal("0");//余额声明为浮点型
		// 获取前台参数非空判断
		if (StringUtils.isBlank(cashmereMobile) 
							|| StringUtils.isBlank(sign)
							|| StringUtils.isBlank(cashmereDeviceId)
							|| StringUtils.isBlank(cashmerePlatform)) {
			logger.info("==[longInvite]==邀请首页域名手机号码mobile=" + cashmereMobile + "获取参数异常~");
			return new ModelAndView("/mereCash/common-error");// 公共错误页面
		} else {
				try{
					request.getSession().setAttribute("cashmereMobile", cashmereMobile);//绑定邀请用户到session
					//根据手机号查询邀请有礼用户
					CashUser cashUser = cashmereUserService.getUserByTel(cashmereMobile);
					if(cashUser == null){
						 view.addObject("balance", "0");//前台展示金额
						//如果邀请人没有注册邀请有礼用户就新建用户-->> 后续可以发放奖金
						 cashUser = new CashUser();
						 cashUser.setMobile(cashmereMobile);//邀请人手机号码
						 cashUser.setBalance(0);//用户余额
						 cashUser.setCreateTime(new Date());// 创建时间
						 cashUser.setUpdateTime(new Date());// 被邀请时间
						 cashmereUserService.save(cashUser);
						
					}else{
						
						balance = new BigDecimal(cashUser.getBalance());
						view.addObject("balance", balance.divide(BigDecimal.valueOf(100)));//账户余额
					}
				}catch(Exception e){
					logger.warn("==[longInvite]==邀请首页出现异常~",e);
				}
			
			view.addObject("url",result);//渲染到首页 
			logger.info("==[longInvite]==邀请首页域名拦截的url=="+result);
			
		}
		//动态的跑马灯接口
		List<String> marqueeList = new ArrayList<String>();
		marqueeList = marquee(marqueeList,20);
		view.addObject("marqueeList",marqueeList);
		return view;
	}
	
	
	/**
	 * 邀请二维码界面
	 * @return
	 * @throws Exception 
	 */
	
	@ResponseBody
	@RequestMapping(value = "/invite.do")
	public ModelAndView invite(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView("/mereCash/invite");
		String cashmereMobile = request.getParameter("cashmereMobile");// 邀请人邀请人手机号码
		String cashmereDeviceId = request.getParameter("cashmereDeviceId");// 被邀请人设备号
		String cashmerePlatform = request.getParameter("cashmerePlatform");// 被邀请人设备平台
		String sign = request.getParameter("sign");// 签名
		
		String key = cashmereMobile+cashmereDeviceId+cashmerePlatform+cashKey;//本地签名的key
		String newSign = Md5Encrypt.encrypt(key);//本地签名
		// 获取前台参数非空判断
		if (StringUtils.isBlank(cashmereMobile) 
							|| StringUtils.isBlank(sign)
							|| StringUtils.isBlank(cashmereDeviceId)
							|| StringUtils.isBlank(cashmerePlatform)) {
			logger.info("==[invite]==邀请二维码界面 手机号码mobile=" + cashmereMobile + "获取参数异常~");
			return new ModelAndView("/mereCash/common-error");// 公共错误页面
		}
		
		//签名验证
		if(!sign.equalsIgnoreCase(newSign)){
			 logger.debug("==[invite]==mobile="+cashmereMobile+"签名错误~");
			 return new ModelAndView("/mereCash/common-error");// 公共错误页面
		}
		//拼接二维码的url
		String url = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/"))+
				"/newBenefit.do?cashmereMobile="+cashmereMobile+"&cashmereDeviceId="+
				cashmereDeviceId+"&cashmerePlatform="+cashmerePlatform+"&sign="+sign+"&time="+System.currentTimeMillis();
		try
		{
			//生成短连接
			String reqUrl = "http://980.so/api.php?url="+ URLEncoder.encode(url,"UTF-8");
			Map map = new HashMap();
			String tinyUrl = GetRequest.getRequest(reqUrl,map);
			if(tinyUrl != null && tinyUrl.startsWith("http://980.so/"))
			{
				url = tinyUrl;
			}
		}
		catch(Exception e)
		{
			logger.warn("长连接转短链接失败~",e);
		}

		view.addObject("qrcodeUrl", url);//二维码url
		logger.info("==[invite]==邀请二维码界面 手机号码mobile=" + cashmereMobile +"=url="+url);
		return view;
	}

	/**
	 * 新人领券 新人可以输入手机号码来来领取红包
	 * 存入用户的session信息
	 * @param request
	 * @param session 
	 * @return view
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/newBenefit.do")
	public ModelAndView newBenefit(HttpServletRequest request,HttpSession session,Model model) throws Exception{
		ModelAndView view = new ModelAndView("/mereCash/new-benefit");
 		String cashmereMobile = request.getParameter("cashmereMobile");//邀请人手机号码
		String cashmereDeviceId = request.getParameter("cashmereDeviceId");//设备号
		String cashmerePlatform = request.getParameter("cashmerePlatform");//设备平台
		String sign = request.getParameter("sign");//签名
		String key = cashmereMobile+cashmereDeviceId+cashmerePlatform+cashKey;
		String newSign = Md5Encrypt.encrypt(key);
		
		// 获取前台参数非空判断
		if (StringUtils.isBlank(cashmereMobile) 
							|| StringUtils.isBlank(sign)
							|| StringUtils.isBlank(cashmereDeviceId)
							|| StringUtils.isBlank(cashmerePlatform)) {
			logger.info("==[newBenefit]==新人领券 手机号码mobile=" + cashmereMobile + "获取参数异常~");
			return new ModelAndView("/mereCash/common-error");// 公共错误页面
		}
		
		if(!sign.equalsIgnoreCase(newSign)){
			 logger.info("==[newBenefit]==新人领券 手机号码mobile="+cashmereMobile+"签名验证失败");
			 return new ModelAndView("/mereCash/common-error");//公共错误页面
			 
		}
		logger.info("==[newBenefit]==新人领券 手机号码mobile="+cashmereMobile+"签名验证成功~");
		//保存分享人的信息
		session.setAttribute("cashmereMobile", cashmereMobile);//保存分享人的手机号码
		session.setAttribute("cashmereDeviceId", cashmereDeviceId);//设备编号
		session.setAttribute("cashmerePlatform", cashmerePlatform);//注册平台
		String token = UUID.randomUUID().toString();//随机数uuid
		session.setAttribute("server_token_wechat", token);
		logger.info("===[newBenefit]===邀请人手机号码="+cashmereMobile+"放入session的token="+session.getAttribute("server_token_wechat"));
		model.addAttribute("token", token);
		return view;
		
	}
	
	/**
	 * 新人领券之后跳转该方法
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getBenefit.do")
	public ModelAndView getBenefit(HttpServletRequest request,HttpSession session) throws Exception{
		
		String targetmobile = request.getParameter("targetmobile");//被邀请人手机号码
	    String selfmobile = (String) session.getAttribute("cashmereMobile");//邀请人手机号
	    String token = request.getParameter("token");//前台获取token
	    String server_token = (String)session.getAttribute("server_token_wechat");//上一步session存的token
	    Pattern p = Pattern.compile("^\\d{11}");//11位数字  
	    Boolean flag = p.matcher(targetmobile).matches();
	    logger.info("==[getBenefit]==targetmobile="+targetmobile+"===token="+token + "| server_token="+server_token);
	   //请求参数的非空检查
		if(StringUtils.isBlank(selfmobile) || StringUtils.isBlank(targetmobile) || StringUtils.isBlank(token)){
			 logger.debug("==[getBenefit]==被邀请人手机号mobile="+targetmobile+"获取用户参数异常~");
			 return new ModelAndView("/mereCash/common-error");//公共异常页面
			 
		}
		//输入手机号码是否合法
		 if(!flag){
	    	 logger.debug("==[getBenefit]==被邀请人手机号mobile="+targetmobile+"输入不合法~");
	    	 return new ModelAndView("/mereCash/common-error");//公共异常页面
	    }
		
		
		/**
		 * 逻辑处理
		 * 1、江湖救急用户查询
		 * 2、先玩后付用户查询
		 */
		//1、江湖救急用户查询
		Map<String,Object> buyerParams = new HashMap<String,Object>();
		buyerParams.put("mobile",targetmobile);
		//buyerParams.put("merchantId", ServletContextFilter.merchantId);
		RiskBuyer riskBuyer = null;//Mo9RestTemplate.doGet("/limit/buyer/v1.0/queryAccountByMobile/{merchantId}/{mobile}", buyerParams, RiskBuyer.class).getObjects();
		logger.info("============江湖救急用户查询=========[riskBuyer]="+riskBuyer);
		//2、先玩后付用户查询
		String mo9Domain = "";//DictUtil.getDictNameByCd("MO9_URL", "domain");
		String url = mo9Domain+"/innerApi/user/getBuyerInfo";
		Map<String,String> map = new HashMap<String,String>();
		map.put("mobile", targetmobile);
		boolean mo9CanInvite = this.canInviteMo9User(url, map);//true表示不是先玩后付用户
		logger.info("============先玩后付用户查询=========[mo9CanInvite]="+mo9CanInvite);
		//查询被邀请人有无邀请记录
		//用来防止重复执行，第二次操作的时候session为空 返回静态页面
		if(!token.equals(server_token)){
			if(riskBuyer==null &&  mo9CanInvite){//不是江湖救急用户 并且不是 先玩后付用户
				
				ModelAndView view = new ModelAndView("/mereCash/benefit-access");
				view.addObject("mobile", targetmobile);
				return view;
			}else{
				return new ModelAndView("/mereCash/benefit-unuse");
			}
			
		}
		session.removeAttribute("server_token_wechat");
		
		if(riskBuyer==null &&  mo9CanInvite){//不是江湖救急用户 并且不是 先玩后付用户
			CashInvitation cashInvitation = hookService.getInvitationByTargetMobile(targetmobile);
			if(cashInvitation == null){//没有邀请关系
				ModelAndView view = new ModelAndView("/mereCash/benefit-access");
				//存入邀请关系表
				InvitationDto dto = new InvitationDto();
				dto.selfmobile = selfmobile;//邀请人手机号
				dto.targetmobile = targetmobile;//被邀请人手机号码
				dto.status = InvitationStatus.pending;//邀请状态
				//因为需要存入被邀请人信息 所以这里不存了 会在app调用时更新
				dto.deviceID =(String) session.getAttribute("cashmereDeviceId");//邀请人的设备号
				dto.ip = request.getRemoteAddr();//邀请人客户端IP
				dto.paltform = (String) session.getAttribute("cashmerePlatform");// 邀请人注册平台
				dto.inviteTime = new Date();// 被邀请时间
				dto.createTime = new Date();// 创建时间
				//前台显示
				view.addObject("mobile", targetmobile);
				//保存邀请关系	
				hookService.addInvitation(dto);
				logger.info("==[getBenefit]==邀请人mobile="+selfmobile+"和被邀请人mobile="+targetmobile+"邀请关系建立成功~");
				return view;
			}else{
				logger.info("==[getBenefit]==邀请人mobile="+selfmobile+"和被邀请人mobile="+targetmobile+"邀请关系建立失败~");
				return new ModelAndView("/mereCash/benefit-unuse");
			}
			
		}else{//江湖救急用户不可以领取客户端参与邀请
			logger.info("==[getBenefit]==邀请人mobile="+selfmobile+"和被邀请人mobile="+targetmobile+"因为被邀请人为老用户无法参与领取~");
			return new ModelAndView("/mereCash/benefit-unuse");
		}
	}
	
	/**
	 * 领取成功页面
	 */
	@ResponseBody
	@RequestMapping(value = "/benefitAcess.do",method = RequestMethod.GET)
	public ModelAndView benefitAcess(HttpServletResponse response,HttpServletRequest request,Model model){
		ModelAndView view = new ModelAndView("/mereCash/benefit-access");
		String mobile = (String) request.getSession().getAttribute("");
		view.addObject("mobile",mobile);
		return view;
	}
	
	/**
	 * 无法领取页面
	 */
	@ResponseBody
	@RequestMapping(value = "/benefitUnuse.do",method = RequestMethod.GET)
	public ModelAndView benefitUnuse(HttpServletResponse response,HttpServletRequest request,Model model){
		ModelAndView view = new ModelAndView("/mereCash/benefit-unuse");
		return view;
	}
	
	/**
	 * 该手机号码是否可以被邀请，针对mo9用户
	 * 有代表不可以被邀请
	 * @param uri
	 * @param params
	 * @return
	 */
	private boolean canInviteMo9User(String uri,Map<String,String> params){
		try {
			String jsonStr = GetRequest.getRequest(uri, params);
			logger.debug("==[canInviteMo9User]==查询先玩后付用户JSON：" + jsonStr);
			JSONObject jsonObject = JSON.parseObject(jsonStr);
			String result = jsonObject.getString("result");
			if ("failed".equals(result)) {
				// 只要查询不成功，则代表手机号码未注册过
				logger.warn("==[canInviteMo9User]==查询先玩后付用户失败-->>该用户可以被邀请~");
				return true;
			}
			JSONObject userObject = jsonObject.getJSONObject("objects");
			if (userObject == null) {
				logger.warn("==[canInviteMo9User]==查询先玩后付用户格式错误。");
				return false;
			}
			String mobile = userObject.getString("mobile");
			return StringUtils.isBlank(mobile);// 手机号为空表示 没有注册过
		} catch (Exception e) {
			logger.warn("==[canInviteMo9User]==查询先玩后付用户错误", e);
			return false;
		}
	
	}

	/**
	 * 奖励明细
	 * @return
	 */
		@SuppressWarnings("unchecked")
		@ResponseBody
		@RequestMapping(value = "/awardDetail.do",method = RequestMethod.GET)
		public ModelAndView awardDetial(HttpServletResponse response,HttpServletRequest request,Model model){
			String mobile = request.getParameter("mobile");
			Page page = new Page(10);
			String pageNo = request.getParameter("pageNo");
			String rows = "10";
			if ( StringUtils.isNotBlank(pageNo) ) {
				page.setPageNo(Integer.valueOf(pageNo));
			}
			if ( StringUtils.isNotBlank(rows) ) {
				page.setPageSize(Integer.valueOf(rows));
			}
			page = cashmereDealEngine.getAwardDetialByTel(page,mobile);
			List<CashDeal> list = page.getResult();
			ModelAndView view = new ModelAndView("/mereCash/award-detail");
			
			
			String tarMobile = "";
			String cardNum = "";
			CashOutOrder order = null;
			for(int i=0;i < list.size();i++){
				
				list.get(i).setAmount(list.get(i).getAmount()/100);
				
				if(CashmereDealType.register == list.get(i).getDealType()){
					if(StringUtils.isNotBlank(list.get(i).getTargetmobile())){
						tarMobile = list.get(i).getTargetmobile().substring(0, 3) + "****" + list.get(i).getTargetmobile().substring(7, 11);
					}
					list.get(i).setTargetMsg(tarMobile);
					list.get(i).setDealMsg("注册成功");
					tarMobile = "";
				}else if(CashmereDealType.verify == list.get(i).getDealType()){
					if(StringUtils.isNotBlank(list.get(i).getTargetmobile())){
						tarMobile = list.get(i).getTargetmobile().substring(0, 3) + "****" + list.get(i).getTargetmobile().substring(7, 11);
					}
					list.get(i).setTargetMsg(tarMobile);
					list.get(i).setDealMsg("借款成功");
					tarMobile = "";
				}else if(CashmereDealType.repayment == list.get(i).getDealType()){
					if(StringUtils.isNotBlank(list.get(i).getTargetmobile())){
						tarMobile = list.get(i).getTargetmobile().substring(0, 3) + "****" + list.get(i).getTargetmobile().substring(7, 11);
					}
					list.get(i).setTargetMsg(tarMobile);
					list.get(i).setDealMsg("还款成功");
					tarMobile = "";
				}else if(CashmereDealType.cash == list.get(i).getDealType()){
					if(StringUtils.isNotBlank(list.get(i).getCashOutOrder())){
						order  = cashOutOrderEngine.getCashOutOrderByDealcode(list.get(i).getCashOutOrder());
						if(null != order && StringUtils.isNotBlank(order.getCardNo())){
							cardNum = "************" + order.getCardNo().substring(order.getCardNo().length()-4, order.getCardNo().length());
						}
					}
					list.get(i).setTargetMsg(cardNum);
					list.get(i).setDealMsg("提现成功");
					order = null;
					cardNum = "";
				}else if(CashmereDealType.reverse == list.get(i).getDealType()){
					if(StringUtils.isNotBlank(list.get(i).getCashOutOrder())){
						order  = cashOutOrderEngine.getCashOutOrderByDealcode(list.get(i).getCashOutOrder());
						if(null != order && StringUtils.isNotBlank(order.getCardNo())){
							cardNum = "************" + order.getCardNo().substring(order.getCardNo().length()-4, order.getCardNo().length());
						}
					}
					list.get(i).setTargetMsg(cardNum);
					list.get(i).setDealMsg("提现失败");
					order = null;
					cardNum = "";
				}else if(CashmereDealType.fee == list.get(i).getDealType()){
					if(StringUtils.isNotBlank(list.get(i).getCashOutOrder())){
						order  = cashOutOrderEngine.getCashOutOrderByDealcode(list.get(i).getCashOutOrder());
						if(null != order && StringUtils.isNotBlank(order.getCardNo())){
							cardNum = "************" + order.getCardNo().substring(order.getCardNo().length()-4, order.getCardNo().length());
						}
					}
					list.get(i).setTargetMsg(cardNum);
					list.get(i).setDealMsg("手续费");
					order = null;
					cardNum = "";
				}
			}
			
			view.addObject("dealList", list);
			
			//设定是否显示“加载更多”
			boolean showTag = false;
			if(page.getTotalCount() > (10 * page.getPageNo())){
				showTag = true;
			}
			view.addObject("showTag", showTag);
			view.addObject("mobile", mobile);
			
			if(StringUtils.isNotBlank(request.getParameter("fromAjax")) && "true".equals(request.getParameter("fromAjax"))){
				String newDiv= "";
				SimpleDateFormat mouthAndDay=new SimpleDateFormat("MM-dd");
				SimpleDateFormat hourAndMin=new SimpleDateFormat("HH-mm");
				String mouthAndDayStr = "";
				String hourAndMinStr = "";
				String amountStr = "";
				for(int i=0;i < list.size();i++){
					mouthAndDayStr =mouthAndDay.format(list.get(i).getUpdateTime());
					hourAndMinStr = hourAndMin.format(list.get(i).getUpdateTime());
					if(list.get(i).getAmount() >= 0){
						amountStr = "+" + String.valueOf(list.get(i).getAmount());
					}
					newDiv = newDiv + "<ul class='award-detail cfix'>" +
			             	"<li>" +
			            		"<span>" +
			            			mouthAndDayStr +
			             			"</br>" +
			             			hourAndMinStr +
			          			"</span>" +
			           		"</li>"+
			             "<li><span>"+list.get(i).getTargetMsg()+"</span></br>"+list.get(i).getDealMsg()+"</li>"+
			             "<li><span>"+amountStr+"</span></li>"+
			             "<div style='clear:both;'></div>"+
			             "</ul>";
					
					mouthAndDayStr = "";
					hourAndMinStr = "";
					amountStr = "";
				}
				
				response.setHeader("showTag", String.valueOf(showTag));
				RenderUtil.renderText(newDiv, response);
	            return null;
			}
			return view;
		}
	
	
	

	@ResponseBody
	@RequestMapping(value = "/myAward.do")
	public ModelAndView myAward(HttpServletResponse response,HttpServletRequest request){
		ModelAndView view = new ModelAndView("/mereCash/my-award");
		//String targetmobile = "18616297271";
		//String targetmobile = "13564546025";
		String targetmobile = (String)request.getSession().getAttribute("cashmereMobile");
		logger.info("[myAward]用户手机号:"+targetmobile);
		view.addObject("mobile", targetmobile);
		
		BigDecimal balance = new BigDecimal("0");
		BigDecimal taotalCash = new BigDecimal("0");
		
		if(StringUtils.isBlank(targetmobile)){
			view.addObject("balance", balance);
			view.addObject("successInvited", "0");
			view.addObject("taotalCash", taotalCash);
		}else{
			//算用户的余额
			CashUser cashUser = cashmereUserService.getUserByTel(targetmobile);
			if(cashUser == null){
				view.addObject("balance", balance);
			}else{
				balance = new BigDecimal(cashUser.getBalance());
				view.addObject("balance", balance.divide(BigDecimal.valueOf(100)));//账户余额
			}
			//计算用户成功邀请其他用户数
			List<CashInvitation> list = cashInvitationService.getSuccessInvitationBySelfMobile(targetmobile);
			if(list != null && list.size() > 0){
				view.addObject("successInvited", list.size());
			}else{
				view.addObject("successInvited", "0");
			}
			//计算用户赚的所有的钱
			int totalAmount = cashmereDealEngine.totalAmountByTel(targetmobile);
			taotalCash = new BigDecimal(totalAmount);
			view.addObject("taotalCash", taotalCash.divide(BigDecimal.valueOf(100)));
		}
		
		List<String> marqueeList = new ArrayList<String>();
		marqueeList = marquee(marqueeList,20);
		view.addObject("marqueeList",marqueeList);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/withDraw.do",method = RequestMethod.GET)
	public ModelAndView withDraw(HttpServletResponse response,HttpServletRequest request,Model model){
		ModelAndView view = new ModelAndView("/mereCash/withdraw");
		String mobile = (String)request.getSession().getAttribute("cashmereMobile");
		CashUser cashUser =StringUtils.isBlank(mobile)?null:cashmereUserService.getUserByTel(mobile);
		if(cashUser==null){
			return new ModelAndView("/mereCash/common-error");
		}
		model.addAttribute("user", cashUser);
		List<CashBankCardInfo> listCashBankCardInfo = cashmereBankCardInfoService.findListByTel(mobile);
		CashBankCardInfo  cashBankCardInfo = listCashBankCardInfo==null?new CashBankCardInfo():listCashBankCardInfo.get(0);
		String token = UUID.randomUUID().toString().replace("-", "");
		request.getSession().setAttribute("token", token);  //在服务器使用session保存token，用于防止表单重复提交
		model.addAttribute("token", token);
		model.addAttribute("bankCardInfo", cashBankCardInfo);
		return view;	
	}
	
	/**
	 * 提现下单步骤：
	 * 1、校验金额是不是在合法并大于3元，3-50元每笔收取2元手续费，大于50不收取手续费
	 * 2、判断银行卡信息是否存在在卡列表信息里，若无则将之保存在卡列表
	 * 3、调用cashOutOrderEngine生成cashOutOrder
	 * 4、调用cashOutOrderEngine的pay方法向支付网关下单
	 * @param drawCashVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/drawCase.do" ,method = RequestMethod.POST)
    public Map<String, String> drawCash(DrawCashVo drawCashVO,HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		String mobile = (String)request.getSession().getAttribute("cashmereMobile");
		CashUser cashUser = StringUtils.isBlank(mobile)?null:cashmereUserService.getUserByTel(mobile);
		//检查手机号是否合法
		if(null == cashUser){
			logger.error("手机号"+mobile+"不是分享有礼的用户");
			map.put("status", "fail");
			map.put("description", "用户非法");
			return map;
		}

		if(null == drawCashVO || StringUtils.isEmpty(drawCashVO.getBankCardNo()) 
				|| StringUtils.isEmpty(drawCashVO.getBankCardNo())
				|| StringUtils.isEmpty(drawCashVO.getIdCardNo())
				|| StringUtils.isEmpty(drawCashVO.getMobile())
				|| StringUtils.isEmpty(drawCashVO.getName())
				|| !mobile.equals(drawCashVO.getMobile())){
			logger.error("手机号"+mobile+"提现请求参数非法"+"参数："+JSON.toJSONString(drawCashVO));
			map.put("status", "fail");
			map.put("description", "请求参数非法");
			return map;
		}
		
		int amount = drawCashVO.getAmount()*100;
		//金额小于3块不允许提现
		if(amount < 300){
			logger.error("手机号"+mobile+"提现金额"+drawCashVO.getAmount()+"小于3元");
			map.put("status", "fail");
			map.put("description", "最小提现金额3元");
			return map;
		}
		//大于50块的免手续费
		int fee = amount >= 5000?0:200;		
		//检查账户余额是否可以提现
		if(cashUser.getBalance()<amount){
			logger.error("用户"+mobile+"账户余额不足，余额"+cashUser.getBalance()+",提现金额："+amount);
			map.put("status", "fail");
			map.put("description", "账户余额不足");
			return map;
		}
		String server_token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(drawCashVO.getToken()) 
			|| StringUtils.isBlank(server_token)	
			|| !drawCashVO.getToken().equals(server_token)){
			logger.error("用户"+mobile+"，表单重复提交");
			map.put("status", "fail");
			map.put("description", "表单重复提交");
			return map;
		}
		String sever_pinCode = (String)request.getSession().getAttribute("sms_pin_code");
		if(drawCashVO.getPinCode() == null || !drawCashVO.getPinCode().equals(sever_pinCode)){
			logger.error("用户"+mobile+"，短信验证码错误");
			map.put("status", "fail");
			map.put("description", "请输入正确的短信验证码");
			return map;
		}
		String server_vidateCode = ((String)request.getSession().getAttribute("validate_code")).toUpperCase();
		if(drawCashVO.getValidateCode() == null || !drawCashVO.getValidateCode().toUpperCase().endsWith(server_vidateCode)){
			logger.error("用户"+mobile+"，图片验证码错误");
			map.put("status", "fail");
			map.put("description", "请输入正确的图片验证码");
			return map;
		}
		if(!validateSignature(drawCashVO,sever_pinCode)){
			logger.error("用户"+mobile+"，参数被修改");
			map.put("status", "fail");
			map.put("description", "参数非法");
			return map;
		}
		request.getSession().removeAttribute("token");
		drawCashVO.setAmount(amount);
		drawCashVO.setFee(fee);
		drawCashVO.setUserMobile(mobile);
		//判断银行卡信息是否在卡列表中，若无则将此信息写入卡列表中
		CashBankCardInfo cashBankCardInfo = saveCardInfo(drawCashVO,mobile);
		//1、在cashoutOrder中生成订单，订单状态为pending或者reject
		Map<String, String> result = generateOutOrder(drawCashVO,cashBankCardInfo.getId());
		String dealcode = 	result.get("dealCode");	
		CashOutOrder  cashOutOrder = cashOutOrderDao.getCashOutOrderByDealcode(dealcode);		
		//只有订单状态为pending时才记录dealCash表和发起支付请求
		if(cashOutOrder.getStatus().equals(CashOutOrderStatus.pending)){
			//2、在cash_deal中插入一笔类型为cash和一笔类型为fee的记录
			generateDeal(dealcode,mobile,false);
			//3、支付网关提交支付请求
			cashOutOrderEngine.pay(dealcode);		
			map.put("status", "success");
			map.put("description", "金额将于1天内到账，请注意查收");
			return map;
		}else{
			map.put("status", "fail");
			map.put("description", result.get("description"));	
		}

		return map;
    }
	
	/**
	 * 验证提交参数的签名，防止参数篡改
	 * @param drawCashVo
	 * @param pinCode
	 * @return
	 */
	private boolean validateSignature(DrawCashVo drawCashVo,String pinCode){
		String sign = drawCashVo.getSign().toUpperCase();
		String param = drawCashVo.getAmount()+drawCashVo.getBankCardNo()+drawCashVo.getIdCardNo()+drawCashVo.getMobile()+pinCode;
		MD5 md5 = new MD5();
		String newSign = md5.MD5Encode(param).toUpperCase();
		
		return newSign.equals(sign);
	}
	
	/**
	 * 判断银行卡信息是否在卡列表中，若无则将此信息写入卡列表中
	 * @param drawCashVO
	 * @param mobile 用户
	 */
	public CashBankCardInfo saveCardInfo(DrawCashVo drawCashVO,String mobile){
		List<CashBankCardInfo> listCashBankCardInfos = cashmereBankCardInfoService.findListByCard(drawCashVO.getBankCardNo());
		if(null == listCashBankCardInfos || listCashBankCardInfos.size()==0){
			CashBankCardInfo cardInfo = new CashBankCardInfo();
			cardInfo.setCardMobile(drawCashVO.getMobile());
			cardInfo.setCardNum(drawCashVO.getBankCardNo());
			cardInfo.setCardUserName(drawCashVO.getName());
			cardInfo.setEnable(false);
			cardInfo.setIdCardNum(drawCashVO.getIdCardNo());
			cardInfo.setMobile(mobile);
			cardInfo.setCreateTime(new Date());
			return cashmereBankCardInfoService.save(cardInfo);		
		}
		return listCashBankCardInfos.get(0);
				
	}
	
	/**
	 * 在cashoutOrder中生成订单
	 * @param drawCashVO
	 * @return
	 */
	private Map<String, String> generateOutOrder(DrawCashVo drawCashVO,Long cardBankInfoId){
		CashOutOrderDto dto = new CashOutOrderDto();
		dto.setAmount(drawCashVO.getAmount()-drawCashVO.getFee());//提现金额
		dto.setBankCardMobile(drawCashVO.getMobile());//帮卡手机号
		dto.setCardNo(drawCashVO.getBankCardNo());//银行卡号
		dto.setCardUserName(drawCashVO.getName());//持卡人
		dto.setMobile(drawCashVO.getUserMobile());//用户手机号		
		dto.setIdCard(drawCashVO.getIdCardNo());//身份证
		dto.setFee(drawCashVO.getFee());//手续费
		 
		return cashOutOrderEngine.generateOutOrder(dto,cardBankInfoId);
	}
	
	/**
	 * 提现时，手续费和提现的金额分别为两笔记录；冲正时，手续费和冲正金额合并为一笔记录
	 * @param dealcode cashOutOrder的dealcode
	 * @param isReverse 是否冲正，false表示提现，true表示冲正
	 * @param userMobile 分享有礼用户的手机号
	 */
	public void generateDeal(String dealcode,String userMobile,boolean isReverse){
		CashOutOrder  cashOutOrder = cashOutOrderDao.getCashOutOrderByDealcode(dealcode);
		DealAcctingDto dto = new DealAcctingDto();
		//冲正时，手续费和提现金额合在一起
		dto.amount = isReverse==true?cashOutOrder.getAmount()+cashOutOrder.getFee():cashOutOrder.getAmount();
		dto.cashOutOrder = cashOutOrder.getDealcode();
		dto.mobile = userMobile;
		dto.type = isReverse==true?CashDeal.CashmereDealType.reverse:CashDeal.CashmereDealType.cash;
		cashmereDealEngine.accounting(dto);
		if(0 != cashOutOrder.getFee() && isReverse==false){
			dto.amount = cashOutOrder.getFee();
			dto.type = CashDeal.CashmereDealType.fee;
			dto.cashOutOrder = cashOutOrder.getDealcode();
			cashmereDealEngine.accounting(dto);
		}
	}
	
	/**
	 * 提现结果通知
	 * 1、验证签名
	 * 2、根据支付结果处理订单
	 * @throws IOException 
	 */
	@RequestMapping(value ="/paymentNotify.do" )
	public void paymentNotify(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String sign = request.getParameter("sign");
		Map<String, String>  buildParams = buildParams(request);
		String oriSign = sign(buildParams);
		if(!oriSign.equalsIgnoreCase(sign)){
			throw new IllegalArgumentException("签名验签失败,签名："+sign+",生成的签名值："+oriSign);
		}
		String status = request.getParameter("status");
		String dealCode = request.getParameter("attach");
		CashOutOrder cashOutOrder = cashOutOrderDao.getCashOutOrderByDealcode(dealCode);
		if(null ==cashOutOrder){
			throw new IllegalDataException("找不到dealcode是"+dealCode+"的cashOutOrder");
		}
		if("success".equals(status)){
			cashOutOrder.setStatus(CashOutOrderStatus.success);
			//如果支付成功这将卡列表信息的数据状态变更为enable=true
			CashBankCardInfo cashBankCardInfo = cashmereBankCardInfoService.getById(cashOutOrder.getBankCardInfoId());
			cashBankCardInfo.setEnable(true);
			cashBankCardInfo.setUpdateTime(new Date());
			cashmereBankCardInfoService.saveOrUpdate(cashBankCardInfo);
		}else if("failed".equals(status)){
			cashOutOrder.setStatus(CashOutOrderStatus.fail);
			generateDeal(cashOutOrder.getDealcode(),cashOutOrder.getMobile(),true);//冲正
		}
		cashOutOrder.setUpdateTime(new Date());
		cashOutOrder.setNotifyResult(JSON.toJSONString(buildParams));
		cashOutOrderDao.updateCashOutOrder(cashOutOrder);
		RenderUtil.renderText("OK", response);
	}
	
	private static Map<String, String>  buildParams(HttpServletRequest req){
		Enumeration<String> keys = req.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		String name = null;
		while (keys.hasMoreElements()) {
			name = keys.nextElement();// 参数
		    params.put(name,req.getParameter(name));
		}
		return params;
	}
	
	private String sign(Map<String, String> params) {
		/** 代付内部密钥secret key. */
		String key = "";//DictUtil.getDictNameByCd(DictContants.ORDER_PAY_URL, "key");
		if(StringUtils.isBlank(key)){
			throw new IllegalArgumentException("代付业务系统签名密钥未找到!");
		}
		if(null == params || params.size() < 1){
			throw new IllegalArgumentException("代付业务参数不正确!");
		}
		// 保持与网关签名一致
		return Md5Encrypt.sign(params, key);
	}

	/**
	 * 发送短信验证码
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "sendPinCode.do",method=RequestMethod.POST)
	public Map<String, String> sendPinCode(HttpServletRequest request){
		String mobile = (String)request.getSession().getAttribute("cashmereMobile");//会员手机号，非银行预留手机号
		Map<String, String> map = new HashMap<String, String>();
		CashUser cashUser = StringUtils.isBlank(mobile)?null:cashmereUserService.getUserByTel(mobile);
		//检查手机号是否合法
		if(null == cashUser){
			logger.error("手机号"+mobile+"不是分享有礼的用户");
			map.put("status", "fail");
			map.put("description", "用户非法");
			return map;
		}
		String validateCode = (String)request.getParameter("validateCode").toUpperCase();
		String validate_code = ((String)request.getSession().getAttribute("validate_code")).toUpperCase();
		request.getSession().removeAttribute("validate_code");
		if(StringUtils.isBlank(validateCode) ||!validateCode.equals(validate_code)){
			map.put("status", "fail");
			map.put("description", "请输入正确的图片验证码");
			return map;
		}
		String pinCode = mo9MemcachedClient.get(mobile);
		pinCode = RandomUtils.generateNumString(4);
		logger.info("分享有礼短信验证码："+pinCode);
		mo9MemcachedClient.set(mobile, pinCode,Mo9MemcachedClient.EXPIRE_5M);
		request.getSession().setAttribute("sms_pin_code",pinCode);
		
		Map<String,String> params = new HashMap<String,String>();
        params.put("mobile",mobile);
        params.put("pincode",pinCode);
        params.put("template_name", "FXYLPinCode");

		Map<String,String> smsMap = new HashMap<String,String>();
		smsMap.put("pincode", pinCode);
       // params.put("template_data",new JacksonConvertor().serialize(smsMap));
        params.put("template_tags","cn");
       // MsfClient.instance().requestFromServer(ServiceAddress.SNC_SMS, params, BaseResponse.class);
		map.put("status", "success");
		map.put("description", "发送验证码成功");;
        return map;
	}
	
	/**
	 * 生成验证码的图片
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	 @RequestMapping(value = "/code.do")
	  public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("validate_code");
		BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	    Graphics gd = buffImg.getGraphics();
	    Random random = new Random();
	    gd.setColor(Color.lightGray);
	    gd.fillRect(0, 0, width, height);
	    Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
	    gd.setFont(font);
	    gd.setColor(Color.white);
	    gd.drawRect(0, 0, width - 1, height - 1);
	    gd.setColor(Color.BLACK);
	    for (int i = 0; i < 20; i++) {
	      int x = random.nextInt(width);
	      int y = random.nextInt(height);
	      int xl = random.nextInt(12);
	      int yl = random.nextInt(12);
	      gd.drawLine(x, y, x + xl, y + yl);
	    }
	    StringBuffer randomCode = new StringBuffer();
	    int red = 41, green = 36, blue = 33;
	    for (int i = 0; i < codeCount; i++) {
	      String code = String.valueOf(codeSequence[random.nextInt(36)]);
	      gd.setColor(new Color(red, green, blue));
	      gd.drawString(code, (i + 1) * xx, codeY);
	      randomCode.append(code);
	    }
	    request.getSession().setAttribute("validate_code", randomCode.toString());
	    logger.info("分享有礼的图片验证码："+ randomCode.toString());
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("image/jpeg");
	    ServletOutputStream sos = response.getOutputStream();
	    ImageIO.write(buffImg, "jpeg", sos);
	    sos.close();
	  }
	 
	 
	 /**
	 * 欢乐跑马灯
	 * @param marqueeList
	 * @param num条数
	 */
	 private static List<String> marquee(List<String> marqueeList,int num){
		
		for(int i = 0;i < num; i++){
			marqueeList.add(getRandomMobile()+getRandomMsg());
		}
		
		return marqueeList;
	 }
	 
	 /**
	 * 产生0～max的随机整数 包括0 不包括max
	 *
	 * @param max
	 * 随机数的上限
	 * @return
	 */
	 public static int getRandom(int max) {
		 return new Random().nextInt(max);
	 }
	 
	 /**
	 * 产生随机手机号
	 * @return
	 */
	 public static String getRandomMobile() {
		 String randomMobile = segmentArr[getRandom(segmentArr.length)];
		return randomMobile = randomMobile + "****" + getRandom(10) + getRandom(10) + getRandom(10)+ getRandom(10);
	 }
	 
	 /**
	 * 产生随机滚动信息
	 * @return
	 */
	 public static String getRandomMsg() {
		
		String randomMsg = msgArr[getRandom(msgArr.length)];
		if(randomMsg.contains("成功提现到账")){
			randomMsg = randomMsg + Math.round(Math.random()*(100-50)+50)+"元";
		}
		
		return randomMsg = randomMsg + "累计赚" + Math.round(Math.random()*(200-100)+100)+"元";
	 }
	 
	 public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(" ");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
	 }
}
