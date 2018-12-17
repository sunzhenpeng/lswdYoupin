package com.lswd.youpin.response;

/**
 * Created by liruilong on 2017/04/12.
 */
public class LsyResponse implements Response {
    private int code = 1; // 响应状态,int, 0为失败,1为成功,只准出现这两个值
    private Object data = null;  //响应结果数据,对象/array类型,对应后端的 Bean/List
  

    public static LsyResponse newInstance() {
        return new LsyResponse();
    }


    /**
     * 设置为成功状态
     */
    public LsyResponse setAsSuccess() {
        this.code = 1;
        return this;
    }

    public static LsyResponse createSuccess() {
        LsyResponse data = new LsyResponse();
        data.code = 1;
        return data;
    }

    public static LsyResponse createFailture() {
        LsyResponse data = new LsyResponse();
        data.code = 0;
        return data;
    }

    public void setCode(boolean code) {
        this.code = 1;
    }

    public int getCode() {
        return code;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



    public LsyResponse setAsFailure() {
        this.code = 0;
        return this;
    }


}
