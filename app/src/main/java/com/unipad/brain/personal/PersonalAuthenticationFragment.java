package com.unipad.brain.personal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unipad.brain.App;
import com.unipad.brain.R;
import com.unipad.utils.ToastUtil;

import java.io.File;

/**
 * 个人中心之实名认证
 * Created by Wbj on 2016/4/26.
 */
public class PersonalAuthenticationFragment extends PersonalCommonFragment {
    private static final String PHOTO_POSTFIX = ".jpg".trim();
    private static final int MAX_STEP = 4;
    private View mTextSelectedSex;
    private View mTextSelectedRole;
    private RelativeLayout mLayoutStep123Parent;
    private ViewGroup mLayoutStep1, mLayoutStep2, mLayoutStep3, mLayoutStep3Up, mLayoutStep3Down;
    private ViewStub mViewStub;
    private ImageView mImageIdentityPositive, mImageIdentityNegative;
    private ImageView mImageCertificate1, mImageCertificate2;
    private TextView mLayoutStep3UpTitle, mLayoutStep3DownTitle;
    private Button mBtnNextStep;
    /**
     * 当前处于第几步，共四步，默认从第一步开始
     */
    private int mCurrentStep = 1;
    private SparseArray<String> mPhotoFileList = new SparseArray<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleBarRightText = mActivity.getString(R.string.next_step);
        mLayoutStep123Parent = (RelativeLayout) mActivity.findViewById(R.id.layout_step123_parent);

        mLayoutStep1 = (ViewGroup) mLayoutStep123Parent.findViewById(R.id.layout_step1);
        View view = mLayoutStep1.findViewById(R.id.radio_men);
        view.setOnClickListener(this);
        view.setSelected(true);
        mTextSelectedSex = view;
        mLayoutStep1.findViewById(R.id.radio_women).setOnClickListener(this);
        view = mLayoutStep1.findViewById(R.id.radio_player);
        view.setOnClickListener(this);
        view.setSelected(true);
        mTextSelectedRole = view;
        mLayoutStep1.findViewById(R.id.radio_coach).setOnClickListener(this);
        mLayoutStep1.findViewById(R.id.radio_judge).setOnClickListener(this);

        mBtnNextStep = (Button) mActivity.findViewById(R.id.next_step);
        mBtnNextStep.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_frg_authentication;
    }

    @Override
    public void clickTitleBarRightText() {
        this.setNextStep();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_men:
            case R.id.radio_women:
                if (mTextSelectedSex != null) {
                    mTextSelectedSex.setSelected(false);
                }

                v.setSelected(true);
                mTextSelectedSex = v;
                break;
            case R.id.radio_player:
            case R.id.radio_coach:
            case R.id.radio_judge:
                if (mTextSelectedRole != null) {
                    mTextSelectedRole.setSelected(false);
                }

                v.setSelected(true);
                mTextSelectedRole = v;
                break;
            case R.id.next_step:
                this.setNextStep();
                break;
            case R.id.grade_certificate_pic1:
            case R.id.grade_certificate_pic2:
            case R.id.grade_certificate_pic3:
            case R.id.grade_certificate_pic4:
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                File file = new File(App.getContext().getTakePhotoFile(), v.getId() + PHOTO_POSTFIX);
                if (file.exists()) {
                    file.delete();
                }
                mPhotoFileList.put(v.getId(), file.getPath());

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));// 设置系统相机拍摄照片完成后图片文件的存放地址
                startActivityForResult(intent, v.getId());
                break;
            case R.id.authentication_confirm_sure_btn:
                this.saveInfoToServer();
                break;
            default:
                break;
        }
    }

    private void setNextStep() {
        if ((++mCurrentStep) > MAX_STEP) {
            mCurrentStep = MAX_STEP;
            this.saveInfoToServer();
            return;
        }

        TextView textCurrent;
        switch (mCurrentStep) {
            case 2:
                mLayoutStep123Parent.removeView(mLayoutStep1);

                textCurrent = (TextView) mActivity.findViewById(R.id.text_step2);
                textCurrent.setSelected(true);
                textCurrent.setTextColor(Color.parseColor("#853EC6"));
                mActivity.findViewById(R.id.line_step2).setBackgroundResource(R.drawable.personal_authentication_step_line2);

                mViewStub = (ViewStub) mActivity.findViewById(R.id.view_shade_step2);
                mLayoutStep2 = (ViewGroup) mViewStub.inflate();
                this.setNextBtnLayoutParams(R.id.layout_step2);
                break;
            case 3:
                mLayoutStep123Parent.removeView(mLayoutStep2);

                textCurrent = (TextView) mActivity.findViewById(R.id.text_step3);
                textCurrent.setSelected(true);
                textCurrent.setTextColor(Color.parseColor("#C020B4"));
                mActivity.findViewById(R.id.line_step3).setBackgroundResource(R.drawable.personal_authentication_step_line3);

                mViewStub = (ViewStub) mActivity.findViewById(R.id.view_shade_step3);
                mLayoutStep3 = (ViewGroup) mViewStub.inflate();
                this.setNextBtnLayoutParams(R.id.layout_step3);

                mImageIdentityPositive = (ImageView) mLayoutStep3.findViewById(R.id.grade_certificate_pic1);
                mImageIdentityPositive.setOnClickListener(this);
                mImageIdentityNegative = (ImageView) mLayoutStep3.findViewById(R.id.grade_certificate_pic2);
                mImageIdentityNegative.setOnClickListener(this);
                mImageCertificate1 = (ImageView) mLayoutStep3.findViewById(R.id.grade_certificate_pic3);
                mImageCertificate1.setOnClickListener(this);
                mImageCertificate2 = (ImageView) mLayoutStep3.findViewById(R.id.grade_certificate_pic4);
                mImageCertificate2.setOnClickListener(this);
                mLayoutStep3Up = (ViewGroup) mLayoutStep3.findViewById(R.id.layout_step3_up);
                mLayoutStep3UpTitle = (TextView) mLayoutStep3.findViewById(R.id.layout_step3_up_title);
                mLayoutStep3Down = (ViewGroup) mLayoutStep3.findViewById(R.id.layout_step3_down);
                mLayoutStep3DownTitle = (TextView) mLayoutStep3.findViewById(R.id.layout_step3_down_title);
                //把几个空格放在文字前面，目的是为了让“等级证书”与“身份证照片”右对齐，因为前者比后者少一个字
                mLayoutStep3DownTitle.setText(mActivity.getString(R.string.grade_certificate, "    "));
                break;
            case 4:
                mLayoutStep3Up.removeView(mLayoutStep3UpTitle);
                mLayoutStep3.removeView(mLayoutStep3Up);
                mLayoutStep3Down.removeView(mLayoutStep3DownTitle);
                mLayoutStep3.removeView(mLayoutStep3Down);
                mLayoutStep123Parent.removeView(mLayoutStep3);

                mLayoutStep123Parent.removeView(mBtnNextStep);
                ((ViewGroup) mActivity.findViewById(R.id.authentication_frg_layout)).removeView(mLayoutStep123Parent);

                textCurrent = (TextView) mActivity.findViewById(R.id.text_step4);
                textCurrent.setSelected(true);
                textCurrent.setTextColor(Color.parseColor("#CB4A42"));

                mTitleBarRightText = mActivity.getString(R.string.confirm_commit);
                mActivity.setRightText(mTitleBarRightText);

                mViewStub = (ViewStub) mActivity.findViewById(R.id.view_shade_step4);
                this.buildLayoutStep4((RelativeLayout) mViewStub.inflate());
                break;
            default:
                break;
        }
    }

    /**
     * 构建步骤4的布局
     */
    private void buildLayoutStep4(RelativeLayout layout) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLayoutStep1.getLayoutParams();
        layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.divide_line_frg);
        layoutParams.setMargins(mActivity.getResources().getDimensionPixelOffset(R.dimen.binary_line_layout_padding),
                0, 0, mActivity.getResources().getDimensionPixelOffset(R.dimen.common_lf_margin));
        layout.addView(mLayoutStep1);

        layoutParams = (RelativeLayout.LayoutParams) mLayoutStep2.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, R.id.layout_step1);
        layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.layout_step1);
        layout.addView(mLayoutStep2);

        ViewGroup viewGroup = (ViewGroup) layout.findViewById(R.id.authentication_step4_ste3);
        viewGroup.addView(mLayoutStep3UpTitle);
        viewGroup.addView(mLayoutStep3Up);
        mLayoutStep3DownTitle.setText(mActivity.getString(R.string.grade_certificate, ""));
        viewGroup.addView(mLayoutStep3DownTitle);
        viewGroup.addView(mLayoutStep3Down);

        layout.findViewById(R.id.authentication_confirm_sure_btn).setOnClickListener(this);
    }

    /**
     * 根据认证步骤的变化，确保让“下一步”按钮位于认证项的父布局的下方
     *
     * @param stepLayoutId 认证项的父布局的id
     */
    private void setNextBtnLayoutParams(int stepLayoutId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBtnNextStep.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, stepLayoutId);
        layoutParams.topMargin = mActivity.getResources().getDimensionPixelOffset(R.dimen.login_margin_top);
        mBtnNextStep.setLayoutParams(layoutParams);
    }

    @Override
    protected void saveInfoToServer() {
        super.saveInfoToServer();
        ToastUtil.showToast(R.string.confirm_commit);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case R.id.grade_certificate_pic1:
            case R.id.grade_certificate_pic2:
            case R.id.grade_certificate_pic3:
            case R.id.grade_certificate_pic4:
                this.setTakePhotoPicture(requestCode);
                break;
            default:
                break;
        }
    }

    private void setTakePhotoPicture(int viewId) {
        Drawable drawable = Drawable.createFromPath(mPhotoFileList.get(viewId));
        if (drawable == null) {
            return;
        }

        switch (viewId) {
            case R.id.grade_certificate_pic1:
                mImageIdentityPositive.setBackground(null);
                mImageIdentityPositive.setBackground(drawable);
//akhghh
//-----------------
//r
                break;
            case R.id.grade_certificate_pic2:
                mImageIdentityNegative.setBackground(null);
                mImageIdentityNegative.setBackground(drawable);
                break;
            case R.id.grade_certificate_pic3:
                mImageCertificate1.setBackground(null);
                mImageCertificate1.setBackground(drawable);
                break;
            case R.id.grade_certificate_pic4:
                mImageCertificate2.setBackground(null);
                mImageCertificate2.setBackground(drawable);
                break;
            default:
                break;
        }
    }

}
