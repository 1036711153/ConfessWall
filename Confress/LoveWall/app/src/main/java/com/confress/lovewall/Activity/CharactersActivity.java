package com.confress.lovewall.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.confress.lovewall.R;
import com.confress.lovewall.Utils.PhotoViewUtils;
import com.confress.lovewall.model.User;
import com.confress.lovewall.presenter.AtyPresenter.CharactersPresenter;
import com.confress.lovewall.view.AtyView.ICharactersView;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2016/3/8.
 */
public class CharactersActivity extends Activity implements ICharactersView, View.OnClickListener {
    @Bind(R.id.back)
    Button back;
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.message_content)
    EditText messageContent;
    @Bind(R.id.message_image)
    ImageView messageImage;
    @Bind(R.id.id_message_progress)
    ProgressBar idMessageProgress;
    @Bind(R.id.id_tv_progress)
    TextView idTvProgress;
    //相册寻找照片
    private static final int IMAGE_REQUEST_CODE1 = 0;
    //拍照
    private static final int IMAGE_REQUEST_CODE2 = 0;

    private CharactersPresenter charactersPresenter = new CharactersPresenter(this, CharactersActivity.this);
    //是否有照片
    private boolean ishasPic = false;
    //是否上传照片成功
    private boolean isuploadpic = false;
    private String uploadsuccesspicpath="hahahaha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_message_wall);
        ButterKnife.bind(this);
        initData();
        messageImage.setOnClickListener(this);
        send.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initData() {
        int i = getIntent().getIntExtra(HomeActivity.TAG, 0);
        if (i == 1) {
            ishasPic = false;
            messageImage.setVisibility(View.INVISIBLE);
        } else if (i == 2) {
            ishasPic = true;
            messageImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void back() {
        CharactersActivity.this.finish();
    }

    @Override
    public void showLoading() {
        idMessageProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        idMessageProgress.setVisibility(View.INVISIBLE);
    }

    //图片路径
    @Override
    public String getPicturepath() {
        return "";
    }

    //上传完图片的路径
    @Override
    public void setUploadPicturepath(String fileurl) {
        uploadsuccesspicpath=fileurl;
    }

    @Override
    public String getUploadPicturepath() {
        return uploadsuccesspicpath;
    }

    //表白文字内容
    @Override
    public String getWallMessage() {
        return messageContent.getText().toString();
    }

    @Override
    public Context getContext() {
        return CharactersActivity.this;
    }

    @Override
    public void EmptyOfMessage() {
        Toast.makeText(getContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public User getUser() {
        return BmobUser.getCurrentUser(this, User.class);
    }

    @Override
    public void uploadPicfailured() {
        isuploadpic = false;
        Toast.makeText(getContext(), "上传图片失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadPicSuccess() {
        isuploadpic = true;
        Toast.makeText(getContext(), "上传图片成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showtvProgress(int progress) {
        idTvProgress.setVisibility(View.VISIBLE);
        idTvProgress.setText("上传进度为：" + progress + "%");
    }

    @Override
    public void hidetvProgress() {
        idTvProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void success() {
        Toast.makeText(getContext(), "表白信息上传成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure() {
        Toast.makeText(getContext(), "表白信息上传失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                if (ishasPic ) {
                    if (isuploadpic) {
                        charactersPresenter.UploadPictureAndMessage();
                    }else {
                        Toast.makeText(getContext(), "请上传图片！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    charactersPresenter.UploadMessage();
                }
                break;
            case R.id.back:
                back();
                break;
            case R.id.message_image:
                //更换照片
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE1) {
            if (data == null) {
                return;
            } else {
                Uri uri = data.getData();
                Uri imageUri = PhotoViewUtils.convertUri(uri, "confress", CharactersActivity.this);
                messageImage.setImageURI(imageUri);
                charactersPresenter.uploadPicture(PhotoViewUtils.getImagePath());
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        charactersPresenter=null;
    }
}
