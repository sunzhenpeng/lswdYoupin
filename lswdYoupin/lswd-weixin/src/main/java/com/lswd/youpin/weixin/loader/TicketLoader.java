package com.lswd.youpin.weixin.loader;

import com.lswd.youpin.weixin.model.js.Ticket;
import com.lswd.youpin.weixin.model.js.TicketType;

/**
 * 凭证加载器
 *liruilong
 * Date: 15/11/15
 * @since 1.3.0
 */
public interface TicketLoader {

    /**
     * 获取Ticket
     * @param type ticket类型
     *             @see TicketType
     * @return 有效的ticket，若返回""或null，则触发重新从微信请求Ticket的方法refresh
     */
    String get(TicketType type);

    /**
     * 刷新Ticket
     * @param ticket 最新获取到的Ticket
     */
    void refresh(Ticket ticket);
}
