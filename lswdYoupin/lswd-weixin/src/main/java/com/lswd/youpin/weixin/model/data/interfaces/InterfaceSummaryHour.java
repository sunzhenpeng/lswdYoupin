package com.lswd.youpin.weixin.model.data.interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *hanxiaoyu
 * Date: 20/11/15
 */
public class InterfaceSummaryHour extends InterfaceSummary {

    private static final long serialVersionUID = 7437638903080411923L;

    /**
     * 数据的小时
     */
    @JsonProperty("ref_hour")
    private Integer hour;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "InterfaceSummaryHour{" +
                "hour=" + hour +
                "} " + super.toString();
    }
}
