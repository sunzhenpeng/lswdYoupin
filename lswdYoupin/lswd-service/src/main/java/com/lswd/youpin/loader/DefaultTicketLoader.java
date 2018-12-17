package com.lswd.youpin.loader;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.dao.wxTokenMapper;
import com.lswd.youpin.model.wxToken;
import com.lswd.youpin.weixin.loader.TicketLoader;
import com.lswd.youpin.weixin.model.js.Ticket;
import com.lswd.youpin.weixin.model.js.TicketType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Ticket加载器
 *liruilong
 * Date: 17/04/11
 * @since 1.3.0
 */
public class DefaultTicketLoader implements TicketLoader {

    @Autowired
    private wxTokenMapper tokenMapper;

    @Override
    public String get(TicketType type) {
        wxToken token = tokenMapper.selectByName("jsApiTicket");
        if(Dates.isAfterNow(token.getEndDate())){
            return token.getValue();
        }
        return null;
    }

    @Override
    public void refresh(Ticket ticket) {
        wxToken token = new wxToken();
        token.setName("jsApiTicket");
        token.setValue(ticket.getTicket());
        token.setStartDate(Dates.now());
        // 微信返回的过期时间是7200秒，为了保证AccessToken始终有效，设置提前5分钟过期
        int expire = ticket.getExpire() - 300;
        token.setEndDate(Dates.addDays(Dates.now(),expire));
        tokenMapper.updateByPrimaryKey(token);
    }
}
