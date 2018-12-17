package com.lswd.youpin.weixin.model.message.receive;

import com.lswd.youpin.weixin.model.message.resp.RespMessageType;

import java.io.Serializable;

/**
 * 接收微信服务器的消息
 *hanxiaoyu
 * Date: 9/11/15
 */
public class RecvMessage implements Serializable {

    /**
     * 开发者微信号
     */
    protected String ToUserName;

    /**
     * 用户openId
     */
    protected String FromUserName;

    /**
     * 消息创建时间
     */
    protected Long CreateTime;

    /**
     * 消息类型:
     * @see RespMessageType
     */
    protected String MsgType;

    protected String URL;

    public RecvMessage(){}

    public RecvMessage(RecvMessage m){
        this.ToUserName = m.ToUserName;
        this.FromUserName = m.FromUserName;
        this.CreateTime = m.CreateTime;
        this.MsgType = m.MsgType;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        this.ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        this.CreateTime = createTime;
    }

    public void setMsgType(String msgType) {
        this.MsgType = msgType;
    }

    public String getMsgType(){
        return this.MsgType;
    }

    @Override
    public String toString() {
        return "RecvMessage{" +
                "toUserName='" + ToUserName + '\'' +
                ", fromUserName='" + FromUserName + '\'' +
                ", createTime=" + CreateTime +
                ", msgType='" + MsgType + '\'' +
                '}';
    }
}
