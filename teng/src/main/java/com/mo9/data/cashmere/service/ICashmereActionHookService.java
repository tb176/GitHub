package com.mo9.data.cashmere.service;

import com.mo9.data.cashmere.bean.CashInvitation;
import com.mo9.data.cashmere.dto.InvitationDto;

/**
 * 江湖救急业务事件触发器
 * Created by sun on 16/8/31.
 */
public interface ICashmereActionHookService {

    /**
     * 当用户在江湖救急中注册完成是调用该方法,为邀请者计算奖励
     * 业务描述:
     *   1.检查手机号码是否有效，并且状态是否已经注册-->>需要到先玩后付查询
     *   2.检查邀请关系,该邀请关系记录的deviceID已有生效的邀请关系,则返回-->>根据deviceID查询邀请关系，设备编号可以出现多次，奖励只记录一次，超过一次就返回 不给予奖励
     *   3.该事件是否被重复处理,如处理过则返回-->>根据手机号查询邀请状态，invited表示已经处理--被邀请手机号只能出现一次 -->>返回给出提示（“fail--返回”）
     *   4.设置邀请关系的enable标志位为true-->>更改status为invited
     *   5.调用流水处理引擎,发放奖励-->>调用流水处理接口 更改账户余额（记录现金流水，更改账户余额）
     * @param mobile ：被邀请人手机号码
     * @param deviceId ： 被邀请人的设备号
     * @param platform ： 被邀请人注册平台
     */
    public abstract void onRegister(String mobile,String deviceId,String platform,String ip);


    /**
     * 当用户的订单在江湖救急中通过审核后调用该方法,为其邀请者计算奖励
     * 业务描述:
     *   1.检查订单的状态,参数等-->>检查订单是否存在.是否放款
     *   2.检查订单所属用户的有效邀请关系,如没有则返回
     *   3.该事件是否被重复处理,如已处理则返回-->>是否已经被处理过了
     *   4.调用流水处理引擎,发放奖励
     * @param dealcode 江湖救急订单号
     */
    public abstract void onApprove(String dealcode);


    /**
     * 当用户的订单在江湖救急中还清一笔贷款后调用该方法,为其邀请者计算奖励
     * 业务描述:
     *   1.检查订单的状态,参数等
     *   2.检查订单所属用户的有效邀请关系,如没有则返回
     *   3.该事件是否被重复处理,如已处理则返回
     *   4.调用流水处理引擎,发放奖励
     * @param dealcode 江湖救急订单号
     */
    public abstract void onPayoff(String dealcode);
    
    /**
     * 根据手机号码查询对应的邀请人
     */
    public abstract CashInvitation getInvitationByTargetMobile(String tel);
    
    public abstract void addInvitation(InvitationDto dto);

}
