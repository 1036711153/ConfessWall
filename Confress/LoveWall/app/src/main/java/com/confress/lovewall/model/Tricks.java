package com.confress.lovewall.model;

/**
 * Created by admin on 2016/3/17.
 */
public class Tricks {
    private int type;//0代表发送的，1代表接受的消息
    private String content;//消息内容

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
