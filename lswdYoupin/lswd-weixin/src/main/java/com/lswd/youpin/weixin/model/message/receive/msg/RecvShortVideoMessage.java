package com.lswd.youpin.weixin.model.message.receive.msg;

import com.lswd.youpin.weixin.model.message.receive.RecvMessageType;

/**
 * 小视频消息
 *hanxiaoyu
 * Date: 9/11/15
 */
public class RecvShortVideoMessage extends RecvVideoMessage {

    private static final long serialVersionUID = 4589295453710532536L;

    public RecvShortVideoMessage(RecvMsg m){
        super(m);
        this.MsgId = m.MsgId;
    }

    @Override
    public String getMsgType() {
        return RecvMessageType.SHORT_VIDEO.value();
    }
}
