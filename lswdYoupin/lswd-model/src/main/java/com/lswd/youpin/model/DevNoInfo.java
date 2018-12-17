package com.lswd.youpin.model;

import java.io.Serializable;

public class DevNoInfo implements Serializable{

    private String DevNo;//设备编号
    private String DevType;//设备类型名称，比如：系统设备、台式机
    private String NetAddress;//设备ip地址
    private Integer NetNo;//设备端口号
    private String Note;//备注

    public String getDevNo() {
        return DevNo;
    }

    public void setDevNo(String devNo) {
        DevNo = devNo;
    }

    public String getDevType() {
        return DevType;
    }

    public void setDevType(String devType) {
        DevType = devType;
    }

    public String getNetAddress() {
        return NetAddress;
    }

    public void setNetAddress(String netAddress) {
        NetAddress = netAddress;
    }

    public Integer getNetNo() {
        return NetNo;
    }

    public void setNetNo(Integer netNo) {
        NetNo = netNo;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
