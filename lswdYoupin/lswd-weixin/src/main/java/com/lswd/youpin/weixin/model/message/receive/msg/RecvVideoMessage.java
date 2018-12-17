package com.lswd.youpin.weixin.model.message.receive.msg;

import com.lswd.youpin.weixin.model.message.receive.RecvMessageType;

/**
 * 视频消息
 *hanxiaoyu
 * Date: 9/11/15
 */
public class RecvVideoMessage extends RecvMsg {

    private static final long serialVersionUID = -3750491257934605285L;

    /**
     * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String mediaId;

    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String thumbMediaId;

    public RecvVideoMessage(RecvMsg m){
        super(m);
        this.MsgId = m.MsgId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    @Override
    public String getMsgType() {
        return RecvMessageType.VIDEO.value();
    }

    @Override
    public String toString() {
        return "RecvVideoMessage{" +
                "mediaId='" + mediaId + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                "} " + super.toString();
    }
}
