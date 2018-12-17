package com.lswd.youpin.weixin.model.message.receive.msg;

import com.lswd.youpin.weixin.model.message.receive.RecvMessage;

/**
 * 接收微信服务器的普通消息
 *hanxiaoyu
 * Date: 9/11/15
 */
public class RecvMsg extends RecvMessage {

    private static final long serialVersionUID = 8863935279441026878L;

    /**
     * 消息ID
     */
    protected Long MsgId;

    public void setMsgId(Long msgId){this.MsgId=msgId;}

    public Long getMsgId(){return MsgId;}


    public RecvMsg(){}

    public RecvMsg(RecvMessage e){
        super(e);
    }

    @Override
    public String toString() {
        return "RecvMsg{" +
                "msgId=" + MsgId +
                "} " + super.toString();
    }
}
