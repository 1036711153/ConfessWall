package com.confress.lovewall.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/3/18.
 */
public class MsgGosn implements Serializable{
    private String nick;
    private String id;
    private String msg;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
