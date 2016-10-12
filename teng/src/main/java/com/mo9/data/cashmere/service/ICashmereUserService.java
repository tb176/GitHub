package com.mo9.data.cashmere.service;

import com.mo9.data.cashmere.bean.CashInvitation;
import com.mo9.data.cashmere.bean.CashUser;

/**
 *  邀请有礼用户处理接口
 * Created by sun on 16/8/31.
 */
public interface ICashmereUserService {

    /**
     * 建立邀请关系
     * 业务描述:King
     *   1.检查是否已有相关邀请关系记录,如果是返回false
     *   2.检查被邀请人是否已经在mo9注册,如已经注册则返回false
     *   3.新建邀请关系,状态设置为pending
     *
     * @param inviterMobile 邀请人手机号码
     * @param inviteeMobile 被邀请人手机号码
     * @return
     */
    public boolean invite(CashInvitation invitation);
    
    /**
	 * 赠送有礼用户保存
	 * @param cashUser
	 * @return
	 */
    public CashUser save(CashUser cashUser);
    
    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    public CashUser getUserByTel(String mobile);
}
