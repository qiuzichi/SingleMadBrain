package com.unipad.brain.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.main.MainActivity;
import com.unipad.http.HitopRegist;

public class RegisterActivity extends BasicActivity implements View.OnClickListener {

    private EditText registName;
    private EditText registSex;
    private EditText registBirthday;
    private EditText registContry;
    private EditText registTel;
    private EditText registPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_aty);
    }

    @Override
    public void initData() {
        findViewById(R.id.btn_register).setOnClickListener(this);
        registName = (EditText) findViewById(R.id.register_name);
        registSex = (EditText) findViewById(R.id.register_sex);
        registBirthday = (EditText) findViewById(R.id.register_date);
        registContry = (EditText) findViewById(R.id.register_nation);
        registTel = (EditText) findViewById(R.id.register_phone);
        registPwd = (EditText) findViewById(R.id.register_pwd);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                regist();
                break;
            default:
                break;
        }
    }

    private void regist() {
        String name = registName.getText().toString().trim();
        String sex = registSex.getText().toString().trim();
        String birthday = registBirthday.getText().toString().trim();
        String contry = registContry.getText().toString().trim();
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
        httpRegist.buildRequestParams("user_password",pwd);
        httpRegist.post();

    }


}
