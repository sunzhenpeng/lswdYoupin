package com.lswd.youpin.weixin.model.message.receive.msg;

import com.lswd.youpin.weixin.model.message.receive.RecvMessageType;

/**
 * 链接消息
 *hanxiaoyu
 * Date: 9/11/15
 */
public class RecvLinkMessage extends RecvMsg {

    private static final long serialVersionUID = -8070100690774814611L;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息描述
     */
    private String description;

    /**
     * 消息链接
     */
    private String url;

    public RecvLinkMessage(RecvMsg m){
        super(m);
        this.MsgId = m.MsgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getMsgType() {
        return RecvMessageType.LINK.value();
    }

    @Override
    public String toString() {
        return "RecvLinkMessage{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                "} " + super.toString();
    }
}
