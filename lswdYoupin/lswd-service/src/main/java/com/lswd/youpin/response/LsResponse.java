package com.lswd.youpin.response;

import com.lswd.youpin.commons.CodeMessage;

/**
 * Created by liruilong on 2017/04/12.
 */
public class LsResponse implements Response {
    private boolean success = true; // 响应状态,布尔值, false为失败,true为成功,只准出现这两个值,不是字符串
    private String errorCode = ""; // 错误码,由接口自己定义, success 为false时生效
    private String message = ""; // 可为空,由接口设置
    private Object data = null;  //响应结果数据,对象/array类型,对应后端的 Bean/List
    private Integer totalCount = 0;// 总数据条数,数字,如果涉及到分页以及多条数据

    public static LsResponse newInstance() {
        return new LsResponse();
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置为成功状态
     */
    public LsResponse setAsSuccess() {
        this.success = true;
        return this;
    }

    public static LsResponse createSuccess() {
        LsResponse data = new LsResponse();
        data.success = true;
        return data;
    }

    public static LsResponse createFailture() {
        LsResponse data = new LsResponse();
        data.success = false;
        return data;
    }

    public void setAsFailure() {
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public LsResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public LsResponse checkSuccess(boolean success, String codeMessageName) {
        this.success = success;
        if (!success) {
            try {
                CodeMessage codeMessage = CodeMessage.valueOf(codeMessageName);
                this.errorCode = codeMessage.getCode();
                this.message = codeMessage.getMsg();
            } catch (IllegalArgumentException e) {
                this.message = codeMessageName;
            }
        }
        return this;
    }

    public LsResponse setAsFailure(String codeMessageName) {
        return this.checkSuccess(false, codeMessageName);
    }

    public LsResponse checkSuccess(boolean success, String errorName, String successName) {
        this.success = success;
        if (success) {
            CodeMessage codeMessage = CodeMessage.valueOf(successName);
            this.errorCode = codeMessage.getCode();
            this.message = codeMessage.getMsg();
        } else {
            CodeMessage codeMessage = CodeMessage.valueOf(errorName);
            this.errorCode = codeMessage.getCode();
            this.message = codeMessage.getMsg();
        }
        return this;
    }


}
