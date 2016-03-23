package com.confress.lovewall.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/3/15.
 */
public class Suggestion extends BmobObject {
    private User user;
    private String advice;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "user=" + user +
                ", advice='" + advice + '\'' +
                '}';
    }
}
