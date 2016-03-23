package com.confress.lovewall.biz;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnSuggestionListener;
import com.confress.lovewall.model.Suggestion;
import com.confress.lovewall.model.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 2016/3/15.
 */
public class SuggestionBiz implements ISuggestionBiz {
    @Override
    public void Suggest(User user, String advice, final OnSuggestionListener suggestionListener, Context context) {
        Suggestion suggestion=new Suggestion();
        suggestion.setUser(user);
        suggestion.setAdvice(advice);
        suggestion.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                suggestionListener.OnSusscess();
            }

            @Override
            public void onFailure(int i, String s) {
               suggestionListener.OnFailed();
            }
        });
    }
}
