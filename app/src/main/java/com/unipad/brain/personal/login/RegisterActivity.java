package com.unipad.brain.personal.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.home.dialog.ShowDialog;
import com.unipad.brain.personal.dao.PersonCenterService;
import com.unipad.brain.view.WheelMainView;
import com.unipad.common.Constant;
import com.unipad.http.HitopRegist;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;
import com.unipad.utils.MD5Utils;

import java.lang.reflect.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BasicActivity implements View.OnClickListener ,
        WheelMainView.OnChangingListener,IDataObserver, TextWatcher {
    private EditText registName;
    private View mTextSelectedSex;
    private TextView radio_men;
    private TextView radio_women;
    private TextView registBirthday;
    private Spinner registNation;
    private EditText registTel;
    private EditText registPwd;
    private WheelMainView wheelMainView;
    private ShowDialog showDialog;
    private Button register;
    private int intSex;
    private PersonCenterService service;
    private String name;
    private String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_aty);
    }
    @Override
    public void initData() {
         registNation = (Spinner) findViewById(R.id.register_nation);
        (radio_men=(TextView) findViewById(R.id.radio_men)).setOnClickListener(this);
        (radio_women=(TextView) findViewById(R.id.radio_women)).setOnClickListener(this);
        (register=(Button) findViewById(R.id.btn_register)).setOnClickListener(this);
        (registBirthday=(TextView) findViewById(R.id.register_day)).setOnClickListener(this);
        (registName = (EditText) findViewById(R.id.register_name)).addTextChangedListener(this);
        (registPwd = (EditText) findViewById(R.id.register_pwd)).addTextChangedListener(this);
        registTel = (EditText) findViewById(R.id.register_phone);

        showDialog = new ShowDialog(this);
        if (showDialog.getDialog()!=null) {
            showDialog.getDialog().setCanceledOnTouchOutside(true);
        }
        service = (PersonCenterService) AppContext.instance().getService(Constant.PERSONCENTER);
        service.registerObserver(HttpConstant.REGIST_FILED,this);
        service.registerObserver(HttpConstant.REGIST_OK,this);

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
                showDialog.showDialog(wheelMainView,ShowDialog.TYPE_CENTER,getWindowManager(),0.3f,0.6f);
                break;
            case R.id.radio_men:
            case R.id.radio_women:
                if (mTextSelectedSex != null) {
                    mTextSelectedSex.setSelected(false);
                }
                v.setSelected(true);
                mTextSelectedSex = v;
                if(v.getId() == R.id.radio_men){
                    intSex = 0;
                } else {
                    intSex = 1;
                }
                break;
            default:
                break;
        }
    }
    private void regist() {
        name = registName.getText().toString().trim();
        pwd = registPwd.getText().toString().trim();
        String sex=String.valueOf(intSex).trim();
        String nation=registNation.getSelectedItem().toString().trim();
        String birthday=registBirthday.getText().toString().trim();
        String tel = registTel.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sex)){
            Toast.makeText(this,"性别不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(birthday)){
            Toast.makeText(this,"出身日期不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nation)){
            Toast.makeText(this,"国籍不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tel)){
            Toast.makeText(this,"联系方式不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        HitopRegist httpRegist = new HitopRegist();
        httpRegist.buildRequestParams("user_name", name);
        httpRegist.buildRequestParams("user_country",nation);
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

    @Override
    protected void onDestroy() {
        service.unregistDataChangeListenerObj(this);
        super.onDestroy();
    }
    @Override
    public void update(int key, Object o) {
        switch (key){
            case HttpConstant.REGIST_OK:
                Intent intent=new Intent();
                intent.putExtra("user_name",name);
                intent.putExtra("user_pwd",pwd);
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                finish();
                break;
            case HttpConstant.REGIST_FILED:
                Toast.makeText(this,"注册信息有误",Toast.LENGTH_SHORT).show();
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
        String regex= "[\u4e00-\u9fa5\\w]+";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(s);
        if (matcher.matches()){
            return;
        }
    }
}

