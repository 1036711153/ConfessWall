package com.confress.lovewall.presenter.AtyPresenter;

import android.content.Context;

import com.confress.lovewall.biz.IListener.OnSuggestionListener;
import com.confress.lovewall.biz.ISuggestionBiz;
import com.confress.lovewall.biz.SuggestionBiz;
import com.confress.lovewall.view.AtyView.IContactUsView;

/**
 * Created by admin on 2016/3/15.
 */
public class ContactUsPresenter {
    private IContactUsView contactUsView;
    private Context context;
    private ISuggestionBiz suggestionBiz;

    public ContactUsPresenter(IContactUsView contactUsView, Context context) {
        this.contactUsView = contactUsView;
        this.context = context;
        this.suggestionBiz=new SuggestionBiz();
    }

    public  void uploadSuggestion(){
        if (contactUsView.getUser()==null){
            contactUsView.NeedLogin();
            return;
        }
        suggestionBiz.Suggest(contactUsView.getUser(),contactUsView.getContent(),new OnSuggestionListener() {
            @Override
            public void OnSusscess() {
             contactUsView.Success();
            }

            @Override
            public void OnFailed() {
            contactUsView.Failure();
            }
        },context);
    }
}
