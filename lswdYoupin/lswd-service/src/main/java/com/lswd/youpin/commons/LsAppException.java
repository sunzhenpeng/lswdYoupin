package com.lswd.youpin.commons;

/**
 * Created by liruilong on 2017/4/12.
 */
public class LsAppException extends RuntimeException {

    private static final long serialVersionUID = -4787502668715026060L;

    private CodeMessage codeMessage;

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(CodeMessage codeMessage) {
        this.codeMessage = codeMessage;
    }

    public LsAppException(CodeMessage codeMessage) {
        super(codeMessage.getMsg());
        this.codeMessage = codeMessage;
    }

    public LsAppException(CodeMessage codeMessage, Throwable throwable) {
        super(codeMessage.getMsg(),throwable);
        this.codeMessage = codeMessage;
    }

    public LsAppException(String msg) {
        super(msg);
    }

    public LsAppException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

