package com.confress.lovewall.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/3/16.
 */
public class Comment extends BmobObject {
    private String content;
    private User user;
    private MessageWall messageWall;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageWall getMessageWall() {
        return messageWall;
    }

    public void setMessageWall(MessageWall messageWall) {
        this.messageWall = messageWall;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", user=" + user +
                ", messageWall=" + messageWall +
                '}';
    }
}
