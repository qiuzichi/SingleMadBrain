package com.unipad.brain.personal.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;

import com.unipad.AppContext;
import com.unipad.UserDetailEntity;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.home.dialog.ShowDialog;
import com.unipad.brain.home.util.SharedPreferencesUtil;
import com.unipad.brain.main.MainActivity;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.common.Constant;
import com.unipad.common.widget.HIDDialog;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.ToastUtil;

/**
 * Created by LiuPeng on 2016/4/14.
 */
public class LoginActivity extends BasicActivity implements View.OnClickListener,IDataObserver,ShowDialog.OnShowDialogClick{

    private EditText userName;

    private EditText userPwd;

    private PersonCenterService service;

    //  自定义Dialog 对象
    private ShowDialog showDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_aty);
    }

    @Override
    public void initData() {
        showDialog = new ShowDialog(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.text_new_user).setOnClickListener(this);
        findViewById(R.id.text_forget_pwd).setOnClickListener(this);
        userName = (EditText) findViewById(R.id.edit_login_name);
        userPwd = (EditText) findViewById(R.id.edit_login_pwd);
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.LOGIN_UPDATE_UI,this);
        service.registerObserver(HttpConstant.LOGIN_WRONG_MSG,this);
        if(Constant.isfirstRun(this,new SharedPreferencesUtil(this))){
            View view = View.inflate(this,R.layout.first_login_dialog,null);
            showDialog.showDialog(view,ShowDialog.TYPE_CENTER);
            showDialog.setOnShowDialogClick(this);
            showDialog.bindOnClickListener(view,new int[]{R.id.img_close});
        }
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
        service.unRegisterObserve(HttpConstant.LOGIN_UPDATE_UI, this);
        service.unRegisterObserve(HttpConstant.LOGIN_WRONG_MSG,this);
    }

    private void login() {
        String name = userName.getText().toString().trim();
        String pwd = userPwd.getText().toString().trim();
        if ("".equals(name)) {
            ToastUtil.showToast("用户名不能为空");
            return;
        }
        if ("".equals(pwd)) {
            ToastUtil.showToast("密码不能为空");
            return;
        }
        ToastUtil.createWaitingDlg(this,null,Constant.LOGIN_WAIT_DLG).show(15);
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
        Log.e("", "Login update UI");
        switch (key) {
            case HttpConstant.LOGIN_UPDATE_UI:
                HIDDialog.dismissAll();
                AppContext.instance().loginUser = (UserDetailEntity)o;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case HttpConstant.LOGIN_WRONG_MSG:
                HIDDialog.dismissAll();
                userName.setText((String)o);
                break;
            default:
                break;
        }
    }

    @Override
    public void dialogClick(int id) {
        if(id != 0 && showDialog != null){
            showDialog.dismiss();
        }
    }
}
