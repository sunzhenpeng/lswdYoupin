package com.lswd.youpin.weixin.model.message.receive.msg;

import com.lswd.youpin.weixin.model.message.receive.RecvMessageType;

/**
 * 图片消息
 *hanxiaoyu
 * Date: 9/11/15
 */
public class RecvImageMessage extends RecvMsg {

    private static final long serialVersionUID = 3465602607733657276L;

    /**
     * 图片链接
     */
    private String picUrl;

    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String mediaId;

    public RecvImageMessage(RecvMsg m){
        super(m);
        this.MsgId = m.MsgId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String getMsgType() {
        return RecvMessageType.IMAGE.value();
    }

    @Override
    public String toString() {
        return "RecvImageMessage{" +
                "picUrl='" + picUrl + '\'' +
                ", mediaId='" + mediaId + '\'' +
                "} " + super.toString();
    }
}
