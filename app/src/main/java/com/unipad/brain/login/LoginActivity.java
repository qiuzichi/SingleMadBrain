package com.unipad.brain.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.main.MainActivity;

/**
 * Created by LiuPeng on 2016/4/14.
 */
public class LoginActivity extends BasicActivity implements View.OnClickListener {

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
