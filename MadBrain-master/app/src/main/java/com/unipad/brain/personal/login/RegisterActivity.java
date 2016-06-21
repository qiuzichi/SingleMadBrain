package com.unipad.brain.personal.login;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.home.dialog.ShowDialog;
import com.unipad.brain.view.WheelMainView;
import com.unipad.http.HitopRegist;
import com.unipad.utils.MD5Utils;

public class RegisterActivity extends BasicActivity implements View.OnClickListener ,
        WheelMainView.OnChangingListener {
    private EditText registName;
    private Spinner registSex;
    private TextView registBirthday;
    private Spinner registContry;
    private EditText registTel;
    private EditText registPwd;
    private WheelMainView wheelMainView;
    private ShowDialog showDialog;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_aty);
    }
    @Override
    public void initData() {
        (register=(Button) findViewById(R.id.btn_register)).setOnClickListener(this);
        registName = (EditText) findViewById(R.id.register_name);
        registSex=(Spinner)findViewById(R.id.register_sex);
        (registBirthday=(TextView) findViewById(R.id.register_day)).setOnClickListener(this);
        registContry = (Spinner) findViewById(R.id.register_nation);
        registTel = (EditText) findViewById(R.id.register_phone);
        registPwd = (EditText) findViewById(R.id.register_pwd);
        showDialog = new ShowDialog(this);
        if (showDialog.getDialog()!=null) {
            showDialog.getDialog().setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                regist();
                break;
            case R.id.register_day:
                wheelMainView = new WheelMainView(this);
                wheelMainView.setChangingListener(this);
                showDialog.showDialog(wheelMainView,ShowDialog.TYPE_CENTER,getWindowManager());
                break;
            default:
                break;
        }
    }
    private void regist() {
        String name = registName.getText().toString().trim();
        String sex=registSex.getSelectedItem().toString().trim();
        String contry=registContry.getSelectedItem().toString().trim();
        String birthday=registBirthday.getText().toString().trim();
        String tel = registTel.getText().toString().trim();
        String pwd = registPwd.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(sex)){
            Toast.makeText(this,"性别不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(birthday)){
            Toast.makeText(this,"出身日期不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(contry)){
            Toast.makeText(this,"国籍不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(tel)){
            Toast.makeText(this,"联系方式不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT);
            return;
        }
        HitopRegist httpRegist = new HitopRegist();
        httpRegist.buildRequestParams("user_name",name);
        httpRegist.buildRequestParams("user_country",contry);
        httpRegist.buildRequestParams("user_sex",sex);
        httpRegist.buildRequestParams("user_contact",tel);
        httpRegist.buildRequestParams("user_born",birthday);
        httpRegist.buildRequestParams("user_password", MD5Utils.MD5_two(pwd));
        httpRegist.post();
    }
    @Override
    public void onChanging(String changStr) {
       registBirthday.setText(changStr);
    }
}
