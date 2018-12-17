package com.lswd.youpin.model.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by liruilong on 2017/12/11.
 */
public class Nodes {
    private Integer id;
    private Integer menuType;
    private Integer pId;
    private String text;
    private String href;
    private String icon;
    private Integer level;
    private JSONObject state;
    private List<Nodes> nodes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Nodes> getNodes() {
        return nodes;
    }

    public void setNodes(List<Nodes> nodes) {
        this.nodes = nodes;
    }

    public JSONObject getState() {
        return state;
    }

    public void setState(JSONObject state) {
        this.state = state;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
