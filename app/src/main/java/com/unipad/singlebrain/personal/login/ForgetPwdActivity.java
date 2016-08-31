package com.unipad.singlebrain.personal.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unipad.AppContext;
import com.unipad.singlebrain.BasicActivity;
import com.unipad.singlebrain.R;
import com.unipad.singlebrain.dialog.ShowDialog;
import com.unipad.singlebrain.personal.dao.PersonCenterService;
import com.unipad.singlebrain.view.WheelMainView;
import com.unipad.common.Constant;
import com.unipad.http.HitopForgetPwd;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.MD5Utils;
import com.unipad.utils.ToastUtil;

public class ForgetPwdActivity extends BasicActivity implements View.OnClickListener,
        TextWatcher ,WheelMainView.OnChangingListener, IDataObserver {
    private Button modify;
    private EditText forget_name;
    private EditText forget_pwd;
    private Spinner forget_Nation;
    private EditText forget_Tel;
    private PersonCenterService service;
    private TextView modify_men;
    private TextView modify_women;
    private View mTextSelectedSex;
    private int intSex;
    private TextView modify_Birthday;
    private Spinner modify_Nation;
    private EditText modify_Tel;
    private String modify_name;
    private String modify_pwd;
    private WheelMainView wheelMainView;
    private ShowDialog showDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pwd_aty);
    }

    @Override
    public void initData() {
        (modify = (Button) findViewById(R.id.btn_confirm_modify)).setOnClickListener(this);
        (forget_name = (EditText) findViewById(R.id.register_name)).addTextChangedListener(this);
        (forget_pwd = (EditText) findViewById(R.id.register_pwd)).addTextChangedListener(this);
        modify_Nation = (Spinner) findViewById(R.id.register_nation);
        modify_Tel = (EditText) findViewById(R.id.register_phone);
        (modify_Birthday = (TextView) findViewById(R.id.register_day)).setOnClickListener(this);
        (modify_men = (TextView) findViewById(R.id.radio_men)).setOnClickListener(this);
        (modify_women = (TextView) findViewById(R.id.radio_women)).setOnClickListener(this);
        showDialog = new ShowDialog(this);
        if (showDialog.getDialog()!=null) {
            showDialog.getDialog().setCanceledOnTouchOutside(true);
        }
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.MODIFY_FILED, this);
        service.registerObserver(HttpConstant.MODIFY_OK, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_modify:
                modify();
                break;
            case R.id.radio_men:
            case R.id.radio_women:
                if (mTextSelectedSex != null) {
                    mTextSelectedSex.setSelected(false);
                }
                v.setSelected(true);
                mTextSelectedSex = v;
                if (v.getId() == R.id.radio_men) {
                    intSex = 0;
                } else {
                    intSex = 1;
                }
                break;
            case R.id.register_day:
                wheelMainView = new WheelMainView(this);
                wheelMainView.setChangingListener(this);
                showDialog.showDialog(wheelMainView,ShowDialog.TYPE_CENTER,getWindowManager(),0.3f,0.6f);
            default:
                break;
        }
    }

    private void modify() {
        modify_name = forget_name.getText().toString().trim();
        modify_pwd = forget_pwd.getText().toString().trim();
        String modify_sex = String.valueOf(intSex);
        String modify_nation = modify_Nation.getSelectedItem().toString().trim();
        String birthday = modify_Birthday.getText().toString().trim();
        String tel = modify_Tel.getText().toString().trim();
        if (TextUtils.isEmpty(modify_name)) {
            ToastUtil.showToast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(modify_sex)) {
            ToastUtil.showToast("性别不能为空");
            return;
        }
        if (TextUtils.isEmpty(birthday)) {
           ToastUtil.showToast("出身日期不能为空");
            return;
        }
        if (TextUtils.isEmpty(modify_nation)) {
           ToastUtil.showToast("国籍不能为空");
            return;
        }
        if (TextUtils.isEmpty(tel)) {
            ToastUtil.showToast("联系方式不能为空");
            return;
        }
        if (TextUtils.isEmpty(modify_pwd)) {
            ToastUtil.showToast("密码不能为空");
            return;
        }
        HitopForgetPwd httpForget=new HitopForgetPwd();
        httpForget.buildRequestParams("user_name", modify_name);
        httpForget.buildRequestParams("user_country", modify_nation);
        httpForget.buildRequestParams("user_sex", modify_sex);
        httpForget.buildRequestParams("user_contact", tel);
        httpForget.buildRequestParams("user_born", birthday);
        httpForget.buildRequestParams("user_password", MD5Utils.MD5_two(modify_pwd));
        httpForget.post();

    }
    @Override
    public void onChanging(String changStr) {
        modify_Birthday.setText(changStr);
    }

    @Override
    protected void onDestroy() {
        service.unregistDataChangeListenerObj(this);
        super.onDestroy();
    }

    @Override
    public void update(int key, Object o) {
        switch (key){
            case HttpConstant.MODIFY_OK:
                       Intent intent=new Intent();
                        intent.putExtra("modify_name",modify_name);
                        intent.putExtra("modify_pwd",modify_pwd);
                        this.setResult(1003,intent);
                        this.finish();
                break;
            case HttpConstant.MODIFY_FILED:
                break;
            default:
                break;

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
