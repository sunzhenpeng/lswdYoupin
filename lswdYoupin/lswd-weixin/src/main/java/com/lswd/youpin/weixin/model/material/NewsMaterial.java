package com.lswd.youpin.weixin.model.material;

/**
 * 图文素材类
 *hanxiaoyu
 * Date: 12/11/15
 */
public class NewsMaterial extends Material {

    private static final long serialVersionUID = 8483540691949616866L;

    private NewsContent content;

    public NewsContent getContent() {
        return content;
    }

    public void setContent(NewsContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsMaterial{" +
                "content=" + content +
                "} " + super.toString();
    }
}
