package com.confress.lovewall.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by admin on 2016/3/26.
 */
public class FriendObject extends BmobObject {
    private User user;
    private BmobRelation tricks;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BmobRelation getTricks() {
        return tricks;
    }

    public void setTricks(BmobRelation tricks) {
        this.tricks = tricks;
    }

}
