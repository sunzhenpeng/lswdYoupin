package com.lswd.youpin.weixin.model.data.article;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 图文统计数据
 *hanxiaoyu
 * Date: 20/11/15
 */
public class ArticleSummary extends CommonSummary {

    private static final long serialVersionUID = -5668724641461918373L;

    @JsonProperty("ref_date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ArticleSummary{" +
                "date='" + date + '\'' +
                "} " + super.toString();
    }
}
