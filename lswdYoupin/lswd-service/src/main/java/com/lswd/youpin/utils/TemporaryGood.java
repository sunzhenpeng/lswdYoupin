package com.lswd.youpin.utils;

public class TemporaryGood {

    private Integer number;
    private String img;
    private String msg;
    private boolean checked = true;
    private float price;

    public TemporaryGood() {
        super();
    }

    public TemporaryGood(Integer number, String img, String msg, boolean checked, float price) {
        this.number = number;
        this.img = img;
        this.msg = msg;
        this.checked = checked;
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
