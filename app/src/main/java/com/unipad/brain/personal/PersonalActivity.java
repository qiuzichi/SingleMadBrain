package com.unipad.brain.personal;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;

/**
 * Created by Wbj on 2016/4/26.
 */
public class PersonalActivity extends BasicActivity  {
    private TextView mTextSelected, mTextRight;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private PersonalInfoFragment mInfoFragment = new PersonalInfoFragment();
    private PersonalAuthenticationFragment mAuthenticationFragment;
    private PersonalRecordFragment mRecordFragment;
    private PersonalFavoriteFragment mFavoriteFragment;
    private PersonalMsgFragment mMsgFragment;
    private PersonalWalletFragment mWalletFragment;
    private PersonalSettingFragment mSettingFragment;
    private PersonalCommonFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_aty);
    }

    @Override
    public void initData() {
        findViewById(R.id.title_bar_left_text).setOnClickListener(this);
        mTextRight = (TextView) findViewById(R.id.title_bar_right_text);
        mTextRight.setOnClickListener(this);

        mFragmentManager = getFragmentManager();
        this.setTextViewSelected((TextView) findViewById(R.id.personal_info));
    }

    public void personalItemClick(View view) {
        TextView textCurrent = (TextView) view;
        if (mTextSelected == textCurrent) {
            return;
        }

        if (mTextSelected != null) {
            mTextSelected.setTextColor(getResources().getColor(R.color.black));
            mTextSelected.setSelected(false);
        }

        this.setTextViewSelected(textCurrent);
    }

    private void setTextViewSelected(TextView textCurrent) {
        if (textCurrent == null) {
            return;
        }

        textCurrent.setSelected(true);
        textCurrent.setTextColor(getResources().getColor(R.color.personal_item_selected));
        mTextSelected = textCurrent;

        this.switchFragment(textCurrent.getId());
    }

    private void switchFragment(int textViewId) {
        PersonalCommonFragment fragment = null;
        switch (textViewId) {
            case R.id.personal_info:
                if (mInfoFragment == null) {
                    mInfoFragment = new PersonalInfoFragment();
                }
                fragment = mInfoFragment;
                break;
            case R.id.personal_authentication:
                if (mAuthenticationFragment == null) {
                    mAuthenticationFragment = new PersonalAuthenticationFragment();
                }
                fragment = mAuthenticationFragment;
                break;
            case R.id.personal_record:
                if (mRecordFragment == null) {
                    mRecordFragment = new PersonalRecordFragment();
                }
                fragment = mRecordFragment;
                break;
            case R.id.personal_favorite:
                if (mFavoriteFragment == null) {
                    mFavoriteFragment = new PersonalFavoriteFragment();
                }
                fragment = mFavoriteFragment;
                break;
            case R.id.personal_wallet:
                if (mWalletFragment == null) {
                    mWalletFragment = new PersonalWalletFragment();
                }
                fragment = mWalletFragment;
                break;
            case R.id.personal_msg:
                if (mMsgFragment == null) {
                    mMsgFragment = new PersonalMsgFragment();
                }
                fragment = mMsgFragment;
                break;
            case R.id.personal_setting:
                if (mSettingFragment == null) {
                    mSettingFragment = new PersonalSettingFragment();
                }
                fragment = mSettingFragment;
                break;
            default:
                break;
        }

        if (fragment == null) {
            return;
        }

        mFragmentTransaction = mFragmentManager.beginTransaction();
        if (mCurrentFragment != null) {
            mFragmentTransaction.hide(mCurrentFragment);
        }
        if (!fragment.isAdded()) {
            mFragmentTransaction.add(R.id.personal_frg_container, fragment);
        } else {
            mFragmentTransaction.show(fragment);
        }
        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();

        this.setRightText(fragment.getTitleBarRightText());
        mCurrentFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_left_text:
                finish();
                break;
            case R.id.title_bar_right_text:
                if (mCurrentFragment != null) {
                    mCurrentFragment.clickTitleBarRightText();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置TitleBar的右侧按钮的显示文字
     *
     * @param text 要显示的文字，传空值表示没有
     */
    public void setRightText(String text) {
        mTextRight.setText(TextUtils.isEmpty(text) ? "" : text);
    }

}
