package com.lswd.youpin.model.lsyp;

import com.lswd.youpin.model.Associator;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhao on 2017/7/1.
 */
public class Evaluate {
    private Integer id;
    private String goodOrRecipeId;
    private String content;
    private String addContent;
    private Short commentType;
    private String associatorId;
    private Boolean isDelete;
    private Date createTime;
    private Date updateTime;
    private List<EvaluateImg>evaluateImgs;
    private String associatorName;
    private String tx;
    private String evaluateType;
    public Integer getId() {return id;}

    public String getGoodOrRecipeId() {return goodOrRecipeId;}

    public String getContent() {return content;}

    public String getAddContent() {return addContent;}

    public Short getCommentType() {return commentType;}

    public String getAssociatorId() {return associatorId;}

    public Boolean getDelete() {return isDelete;}

    public Date getCreateTime() {return createTime;}

    public Date getUpdateTime() {return updateTime;}

    public void setId(Integer id) {this.id = id;}

    public void setGoodOrRecipeId(String goodOrRecipeId) {this.goodOrRecipeId = goodOrRecipeId==null?null:goodOrRecipeId.trim();}

    public void setContent(String content) {this.content = content==null?null:content.trim();}

    public void setAddContent(String addContent) {this.addContent = addContent==null?null:addContent.trim();}

    public void setCommentType(Short commentType) {this.commentType = commentType;}

    public void setAssociatorId(String associatorId) {this.associatorId = associatorId==null?null:associatorId.trim();}

    public void setDelete(Boolean delete) {isDelete = delete;}

    public void setCreateTime(Date createTime) {this.createTime = createTime;}

    public void setUpdateTime(Date updateTime) {this.updateTime = updateTime;}

    public List<EvaluateImg> getEvaluateImgs() {return evaluateImgs;}

    public void setEvaluateImgs(List<EvaluateImg> evaluateImgs) {this.evaluateImgs = evaluateImgs;}

    public String getAssociatorName() {return associatorName;}

    public void setAssociatorName(String associatorName) {this.associatorName = associatorName==null?null:associatorName.trim();}

    public String getTx() {return tx;}

    public void setTx(String tx) {this.tx = tx==null?null:tx.trim();}

    public String getEvaluateType() {return evaluateType;}

    public void setEvaluateType(String evaluateType) {this.evaluateType = evaluateType;}

}
