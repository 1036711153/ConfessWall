package com.confress.lovewall.model;

import java.io.Serializable;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by admin on 2016/3/11.
 */
public class MessageWall extends BmobObject implements Serializable {
    private String Confess_content;//表白内容
    private String Confess_image;//表白图片
    private int Collection_count;//被收藏的数目
    private int Comment_count;//被评论的数目
    private int Support_count;//被赞的数目
    private boolean Is_Anonymous;//是否匿名
    private User user;

//    private BmobRelation likes;
//
//
//    public BmobRelation getLikes() {
//        return likes;
//    }
//
//    public void setLikes(BmobRelation likes) {
//        this.likes = likes;
//    }

    public String getConfess_content() {
        return Confess_content;
    }
    public void setConfess_content(String confess_content) {
        Confess_content = confess_content;
    }
    public String getConfess_image() {
        return Confess_image;
    }
    public void setConfess_image(String confess_image) {
        Confess_image = confess_image;
    }
    public int getCollection_count() {
        return Collection_count;
    }
    public void setCollection_count(int collection_count) {
        Collection_count = collection_count;
    }
    public int getComment_count() {
        return Comment_count;
    }
    public void setComment_count(int comment_count) {
        Comment_count = comment_count;
    }
    public int getSupport_count() {
        return Support_count;
    }
    public void setSupport_count(int support_count) {
        Support_count = support_count;
    }
    public boolean isIs_Anonymous() {
        return Is_Anonymous;
    }
    public void setIs_Anonymous(boolean is_Anonymous) {
        Is_Anonymous = is_Anonymous;
    }

    public boolean is_Anonymous() {
        return Is_Anonymous;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "MessageWall{" +
                "Confess_content='" + Confess_content + '\'' +
                ", Confess_image='" + Confess_image + '\'' +
                ", Collection_count=" + Collection_count +
                ", Comment_count=" + Comment_count +
                ", Support_count=" + Support_count +
                ", Is_Anonymous=" + Is_Anonymous +
                ", user=" + user +
//                ", likes=" + likes +
                '}';
    }
}
