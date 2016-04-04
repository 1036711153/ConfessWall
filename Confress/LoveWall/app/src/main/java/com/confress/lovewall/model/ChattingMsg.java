package com.confress.lovewall.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/3/17.
 */
public class ChattingMsg extends BmobObject {
    private String uid1;
    private String uid2;
    private int type;//0代表发送的，1代表接受的消息
    private String content;//消息内容


    public String getUid1() {
        return uid1;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1;
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

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
