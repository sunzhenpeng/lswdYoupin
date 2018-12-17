package com.lswd.youpin.model.lsyp;

/**
 * Created by liuhao on 2017/8/12.
 */
public class EvaluateImg {
    private Integer id;
    private String img;
    private Integer commentId;

    public Integer getId() {return id;}

    public String getImg() {return img;}

    public Integer getCommentId() {return commentId;}

    public void setId(Integer id) {this.id = id;}

    public void setImg(String img) {this.img = img==null?null:img.trim();}

    public void setCommentId(Integer commentId) {this.commentId = commentId;}

}
