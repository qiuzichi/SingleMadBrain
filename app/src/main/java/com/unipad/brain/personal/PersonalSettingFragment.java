package com.unipad.brain.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.unipad.AppContext;
import com.unipad.brain.R;
import com.unipad.brain.personal.bean.Pwd;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.common.widget.HIDDialog;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ActivityCollector;
import com.unipad.utils.MD5Utils;
import com.unipad.utils.ToastUtil;

/**
 * 个人中心之设置中心
 * Created by Wbj on 2016/4/27.
 */
public class PersonalSettingFragment extends PersonalCommonFragment implements IDataObserver {
    private EditText mEditSuggest;
    private int mSelectedViewId = -1;
    private int mNormalColor, mRedColor, mTextHint;

    private PersonCenterService service;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        //
        service.registerObserver(HttpConstant.UPDATA_LOGIN_PWD,this);

        mRedColor = mActivity.getResources().getColor(R.color.red);
        mNormalColor = mActivity.getResources().getColor(R.color.personal_setting_text);
        mTextHint = mActivity.getResources().getColor(R.color.edit_text_hint);

        mActivity.findViewById(R.id.text_system_setting).setOnClickListener(this);
        mActivity.findViewById(R.id.text_about_us).setOnClickListener(this);
        mActivity.findViewById(R.id.text_suggest_feedback).setOnClickListener(this);
        mActivity.findViewById(R.id.text_modify_login_pwd).setOnClickListener(this);
        mActivity.findViewById(R.id.text_modify_pay_pwd).setOnClickListener(this);
        mActivity.findViewById(R.id.text_exit_app).setOnClickListener(this);

        mEditSuggest = (EditText) mActivity.findViewById(R.id.setting_feedback_edit);
        mEditSuggest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditSuggest.setHint("");
                    mEditSuggest.setGravity(Gravity.LEFT);
                } else {
                    if (TextUtils.isEmpty(mEditSuggest.getText().toString())) {
                        mEditSuggest.setHint(R.string.suggest_hint);
                        mEditSuggest.setGravity(Gravity.CENTER);
                    }
                }
            }
        });

        mActivity.findViewById(R.id.btn_confirm_modify_login).setOnClickListener(this);
        mActivity.findViewById(R.id.btn_confirm_modify_pay).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        thisShowView = 7;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        service.unregistDataChangeListenerObj(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_frg_setting;
    }

    @Override
    public void clickTitleBarRightText() {
        this.saveInfoToServer();
    }

    @Override
    public String getTitleBarRightText() {
        return mTitleBarRightText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_system_setting:
                mActivity.setRightText(mTitleBarRightText = "");
                break;
            case R.id.text_about_us:
                mActivity.setRightText(mTitleBarRightText = "");
                mActivity.findViewById(R.id.setting_feedback_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_login_pwd_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_pay_pwd_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_about_us_layout).setVisibility(View.VISIBLE);
                break;
            case R.id.text_suggest_feedback:
                mActivity.setRightText(mTitleBarRightText = mActivity.getString(R.string.commit));
                mActivity.findViewById(R.id.setting_about_us_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_login_pwd_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_pay_pwd_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_feedback_layout).setVisibility(View.VISIBLE);
                int suggestTextLen = mEditSuggest.getText().toString().length();
                if (suggestTextLen > 0) {
                    mEditSuggest.setSelection(suggestTextLen);//设置光标位置
                    //mEditSuggest.requestFocus();//显示光标
                    //自动弹出软键盘
                    // InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    // inputManager.showSoftInput(mEditSuggest, 0);
                }

                mSelectedViewId = R.id.text_suggest_feedback;
                break;
            case R.id.text_modify_login_pwd:
                mActivity.setRightText(mTitleBarRightText = mActivity.getString(R.string.confirm));
                mActivity.findViewById(R.id.setting_about_us_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_feedback_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_pay_pwd_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_login_pwd_layout).setVisibility(View.VISIBLE);

                mSelectedViewId = R.id.text_modify_login_pwd;
                break;
            case R.id.text_modify_pay_pwd:
                mActivity.setRightText(mTitleBarRightText = mActivity.getString(R.string.confirm));
                mActivity.findViewById(R.id.setting_about_us_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_feedback_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_login_pwd_layout).setVisibility(View.GONE);
                mActivity.findViewById(R.id.setting_pay_pwd_layout).setVisibility(View.VISIBLE);

                mSelectedViewId = R.id.text_modify_pay_pwd;
                break;
            case R.id.text_exit_app:
                ActivityCollector.finishAllActivity();
                //返回到桌面
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mActivity.startActivity(intent);
                break;
            case R.id.btn_confirm_modify_login:
                this.confirmModifyLoginPwd();
                break;
            case R.id.btn_confirm_modify_pay:
                this.confirmModifyPayPwd();
                break;
            default:
                break;
        }
    }

    /**
     * 确认修改支付密码
     */
    private void confirmModifyPayPwd() {
        boolean isEmpty = false;

        EditText originPwdEdit = (EditText) mActivity.findViewById(R.id.pay_origin_pwd);
        String originPwd = originPwdEdit.getText().toString();
        if (TextUtils.isEmpty(originPwd)) {
            originPwdEdit.setHintTextColor(mRedColor);
            isEmpty = true;
        } else {
            originPwdEdit.setHintTextColor(mTextHint);
        }

        EditText newPwdEdit = (EditText) mActivity.findViewById(R.id.pay_new_pwd);
        String newPwd = newPwdEdit.getText().toString();
        if (TextUtils.isEmpty(newPwd)) {
            newPwdEdit.setHintTextColor(mRedColor);
            isEmpty = true;
        } else {
            newPwdEdit.setHintTextColor(mTextHint);
        }

        EditText repeatNewPwdEdit = (EditText) mActivity.findViewById(R.id.pay_repeat_new_pwd);
        String repeatNewPwd = repeatNewPwdEdit.getText().toString();
        if (TextUtils.isEmpty(repeatNewPwd)) {
            repeatNewPwdEdit.setHintTextColor(mRedColor);
            isEmpty = true;
        } else {
            newPwdEdit.setHintTextColor(mTextHint);
        }

        if (isEmpty) {
            ToastUtil.showToast(mActivity.getString(R.string.please_fill_full_info));
            return;
        }

        if (!"123456".equals(originPwd)) {
            originPwdEdit.setTextColor(mRedColor);
            ToastUtil.showToast(mActivity.getString(R.string.origin_pwd_nomatch));
            return;
        } else {
            originPwdEdit.setTextColor(mNormalColor);
        }

        if (!newPwd.equals(repeatNewPwd)) {
            repeatNewPwdEdit.setTextColor(mRedColor);
            ToastUtil.showToast(mActivity.getString(R.string.different_pwd));
            return;
        } else {
            repeatNewPwdEdit.setTextColor(mNormalColor);
        }

        ToastUtil.showToast(R.string.pay_pwd);
        new Pwd(originPwd, newPwd, repeatNewPwd);
    }

    /**
     * 确认修改登录密码
     */
    private void confirmModifyLoginPwd() {
        boolean isEmpty = false;

        EditText originPwdEdit = (EditText) mActivity.findViewById(R.id.login_origin_pwd);
        String originPwd = originPwdEdit.getText().toString();
        if (TextUtils.isEmpty(originPwd)) {
            originPwdEdit.setHintTextColor(mRedColor);
            isEmpty = true;
        } else {
            originPwdEdit.setHintTextColor(mTextHint);
        }

        EditText newPwdEdit = (EditText) mActivity.findViewById(R.id.login_new_pwd);
        String newPwd = newPwdEdit.getText().toString();
        if (TextUtils.isEmpty(newPwd)) {
            newPwdEdit.setHintTextColor(mRedColor);
            isEmpty = true;
        } else {
            newPwdEdit.setHintTextColor(mTextHint);
        }

        EditText repeatNewPwdEdit = (EditText) mActivity.findViewById(R.id.login_repeat_new_pwd);
        String repeatNewPwd = repeatNewPwdEdit.getText().toString();
        if (TextUtils.isEmpty(repeatNewPwd)) {
            repeatNewPwdEdit.setHintTextColor(mRedColor);
            isEmpty = true;
        } else {
            newPwdEdit.setHintTextColor(mTextHint);
        }

        if (isEmpty) {
            ToastUtil.showToast(mActivity.getString(R.string.please_fill_full_info));
            return;
        }

        if (!MD5Utils.MD5_two(originPwd).equals(AppContext.instance().loginUser.getLoginPwd())) {
            originPwdEdit.setTextColor(mRedColor);
            ToastUtil.showToast(mActivity.getString(R.string.origin_pwd_nomatch));
            return;
        } else {
            originPwdEdit.setTextColor(mNormalColor);
        }

        if (!newPwd.equals(repeatNewPwd)) {
            repeatNewPwdEdit.setTextColor(mRedColor);
            ToastUtil.showToast(mActivity.getString(R.string.different_pwd));
            return;
        } else {
            repeatNewPwdEdit.setTextColor(mNormalColor);
        }
        ToastUtil.createWaitingDlg(mActivity,null,Constant.LOGIN_WAIT_DLG).show(15);
        Pwd pwd = new Pwd(originPwd, newPwd, repeatNewPwd);
        service.updataLoginPwd(AppContext.instance().loginUser.getUserId(),pwd.getOriginPwd(),pwd.getRepeatNewPwd());
    }

    @Override
    protected void saveInfoToServer() {
        super.saveInfoToServer();
        if (mSelectedViewId == R.id.text_suggest_feedback) {
            String suggestText = mEditSuggest.getText().toString();
            if (TextUtils.isEmpty(suggestText)) {
                ToastUtil.showToast(R.string.suggest_content_empty);
                return;
            }

            ToastUtil.showToast(suggestText);
        } else if (mSelectedViewId == R.id.text_modify_login_pwd) {

            this.confirmModifyLoginPwd();
        } else if (mSelectedViewId == R.id.text_modify_pay_pwd) {
            this.confirmModifyPayPwd();
        }
    }

    @Override
    public void update(int key, Object o) {
        // 此方法 用于更新界面UI
        switch (key){
            case HttpConstant.UPDATA_LOGIN_PWD:
                HIDDialog.dismissAll();
                //Log.d(this.getClass().getSimpleName(), (String) o);
                ToastUtil.showToast((String)o);
                break;
        }
    }
}
