package com.unipad.brain.personal;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.personal.view.ChatFunctionView;
import com.unipad.http.HttpConstant;
import com.unipad.utils.FileUtil;
import com.unipad.utils.PicUtil;
import com.unipad.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Wbj on 2016/4/26.
 */
public abstract class PersonalCommonFragment extends Fragment implements View.OnClickListener{

    protected PersonalActivity mActivity;
    protected ViewGroup mParentLayout;
    protected String mTitleBarRightText;

    private ImageView user_photo;

    /**
     * 对应Fragment的布局(不包括上半部分)的id
     *
     * @return 布局Id
     */
    public abstract int getLayoutId();

    /**
     * Activity的TitleBar的右侧按钮监听事件
     */
    public abstract void clickTitleBarRightText();

    /**
     * 获取Activity的TitleBar的右侧按钮的显示文字
     */
    public abstract String getTitleBarRightText();

    /**
     * 为了编码的规范：当用户有数据要提交到服务器时，覆写此方法
     */
    protected void saveInfoToServer() {
    }
    TextView txtName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentLayout = (ViewGroup) inflater.inflate(R.layout.personal_frg_common, container, false);
        mParentLayout.addView(inflater.inflate(this.getLayoutId(), null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        txtName = (TextView)mParentLayout.findViewById(R.id.user_name);
        user_photo = (ImageView)mParentLayout.findViewById(R.id.user_photo);
        user_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mActivity.showScPic();
            }
        });

        if(!TextUtils.isEmpty(AppContext.instance().loginUser.getPhoto()))
            x.image().bind(user_photo, HttpConstant.PATH_FILE_URL + AppContext.instance().loginUser.getPhoto(), new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable drawable) {
                    Bitmap map =  PicUtil.drawableToBitmap(drawable);
                    user_photo.setImageBitmap(PicUtil.getRoundedCornerBitmap(map, 360));
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        setNameText(AppContext.instance().loginUser.getUserName());
        return mParentLayout;
    }


    public void setImageBitmap(Bitmap map){
        if(null != user_photo)
            user_photo.setImageBitmap(PicUtil.getRoundedCornerBitmap(map, 360));
    }


    /**
     *  设置用户名字
     * @param strName
     */
    public void setNameText(String strName){
        if(txtName != null){
            txtName.setText(strName);
        }
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (PersonalActivity) getActivity();
    }


    protected void hidePersonalInfoLayout() {
        mParentLayout.findViewById(R.id.personal_info_layout).setVisibility(View.GONE);
    }

}
