package com.confress.lovewall.model;


import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by admin on 2016/3/7.
 */
public class User extends BmobUser implements Serializable{
    private String sex;
    private String nick;
    private Integer age;
    private String tel;
    private String icon;
    //用户收藏夹
    private BmobRelation likes;
    private BmobRelation friends;
    private BmobRelation tricks;

    public BmobRelation getTricks() {
        return tricks;
    }

    public void setTricks(BmobRelation tricks) {
        this.tricks = tricks;
    }

    public BmobRelation getFriends() {
        return friends;
    }

    public void setFriends(BmobRelation friends) {
        this.friends = friends;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
