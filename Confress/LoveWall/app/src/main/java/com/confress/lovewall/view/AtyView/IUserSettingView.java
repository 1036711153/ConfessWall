package com.confress.lovewall.view.AtyView;

import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/14.
 */
public interface IUserSettingView {
    String getNickName();

    void setNickName(User user);

    String getSex();

    void setSex(User user);

    int getAge();

    void setAge(User user);

    String getTel();

    void setTel(User user);

    String getUserIcon(User user);

    void setUserIcon(User user);

    User getCurrentUser();

    void Success();

    void Failure();

    void UploadIconSuccess();

    void UploadIconFailure();

    void  showtvProgress(int progress);


    void successTvprogress();

}
