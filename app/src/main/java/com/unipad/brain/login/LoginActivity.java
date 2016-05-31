package com.unipad.brain.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.unipad.AppContext;
import com.unipad.UserDetailEntity;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.main.MainActivity;
import com.unipad.common.MobileInfo;
import com.unipad.http.HitopLogin;

/**
 * Created by LiuPeng on 2016/4/14.
 */
public class LoginActivity extends BasicActivity implements View.OnClickListener {

    private EditText userName;

    private EditText userPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_aty);
    }

    @Override
    public void initData() {
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.text_new_user).setOnClickListener(this);
        findViewById(R.id.text_forget_pwd).setOnClickListener(this);
        userName = (EditText) findViewById(R.id.edit_login_name);
        userPwd = (EditText) findViewById(R.id.edit_login_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                this.openMainActivity();
                break;
            case R.id.text_new_user:
                this.openRegisterActivity();
                break;
            case R.id.text_forget_pwd:
                this.openForgetPwdActivity();
                break;
            default:
                break;
        }
    }

    private void openMainActivity() {

        HitopLogin httpLogin = new HitopLogin();

        httpLogin.buildRequestParams("user_name",userName.getText().toString().trim());
        httpLogin.buildRequestParams("user_password",userPwd.getText().toString().trim());
        httpLogin.buildRequestParams("device_name", MobileInfo.getDeviceName());
        httpLogin.buildRequestParams("device_did", MobileInfo.getDeviceId());
        AppContext.instance().loginUser = httpLogin.post();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openForgetPwdActivity() {
        Intent intent = new Intent(this, ForgetPwdActivity.class);
        startActivity(intent);
        finish();
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
