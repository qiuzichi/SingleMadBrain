package com.unipad.brain.personal.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.main.MainActivity;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

/**
 * Created by LiuPeng on 2016/4/14.
 */
public class LoginActivity extends BasicActivity implements View.OnClickListener,IDataObserver{

    private EditText userName;

    private EditText userPwd;

    private PersonCenterService service;
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
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.LOGIN_UPDATE_UI,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                this.login();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        service.unRegisterObserve(HttpConstant.LOGIN_UPDATE_UI,this);
    }

    private void login() {
        service.loginIn(userName.getText().toString().trim(), userPwd.getText().toString().trim());
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

    @Override
    public void update(int key, Object o) {
        switch (key) {

        }
        Log.e("","Login update UI");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
