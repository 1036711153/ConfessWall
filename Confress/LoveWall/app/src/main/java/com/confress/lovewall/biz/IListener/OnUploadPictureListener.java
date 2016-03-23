package com.confress.lovewall.biz.IListener;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2016/3/12.
 */
public interface OnUploadPictureListener {
    void OnSusscess( BmobFile file);
    void OnProgress(int progress);
    void OnFailed();
}
