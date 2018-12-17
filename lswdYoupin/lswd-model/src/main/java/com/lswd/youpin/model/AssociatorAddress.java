package com.lswd.youpin.model;

/**
 * Created by liuhao on 2017/8/8.
 */
public class AssociatorAddress {
    private Integer id;
    private Boolean checked;
    private String address;
    private String name;
    private String phonenumber;
    private String detail;

    public Integer getId() {return id;}


    public String getAddress() {return address;}

    public String getName() {return name;}

    public void setId(Integer id) {this.id = id;}

    public void setAddress(String address) {this.address = address==null?null:address.trim();}

    public void setName(String name) {this.name = name==null?null:name.trim();}

    public Boolean getChecked() {return checked;}

    public String getPhonenumber() {return phonenumber;}

    public String getDetail() {return detail;}

    public void setChecked(Boolean checked) {this.checked = checked;}

    public void setPhonenumber(String phonenumber) {this.phonenumber = phonenumber;}

    public void setDetail(String detail) {this.detail = detail;}
}
