package com.unipad.brain.personal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipad.brain.R;

/**
 * Created by Wbj on 2016/4/26.
 */
public abstract class PersonalCommonFragment extends Fragment implements View.OnClickListener{
    protected PersonalActivity mActivity;
    protected ViewGroup mParentLayout;
    protected String mTitleBarRightText;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentLayout = (ViewGroup) inflater.inflate(R.layout.personal_frg_common, container, false);
        mParentLayout.addView(inflater.inflate(this.getLayoutId(), null),
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mParentLayout;
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
