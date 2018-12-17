package com.lswd.youpin.model;

import java.io.Serializable;

/**
 * Created by liuhao on 2017/10/28.
 */
public class AssociatorShare implements Serializable {
    private Integer id;
    private String associatorId;
    private String shareId;
    private Integer count;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getAssociatorId() {return associatorId;}

    public void setAssociatorId(String associatorId) {this.associatorId = associatorId==null?null:associatorId.trim();}

    public String getShareId() {return shareId;}

    public void setShareId(String shareId) {this.shareId = shareId==null?null:shareId.trim();}

    public Integer getCount() {return count;}

    public void setCount(Integer count) {this.count = count;}
}
