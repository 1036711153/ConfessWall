package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnSuggestionListener;
import com.confress.lovewall.model.User;

/**
 * Created by admin on 2016/3/15.
 */
public interface ISuggestionBiz {
    void Suggest(User user,String advice,OnSuggestionListener suggestionListener,Context context);
}
