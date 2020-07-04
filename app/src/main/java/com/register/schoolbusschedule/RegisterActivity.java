package com.register.schoolbusschedule;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolbusschedule.R;
import com.login.schoolbusschedule.LoginActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText account,passwd1,passwd2,name,myclass;
    private RadioButton identify;
    private RegisterViewModel registerViewModel=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    /**
     * 初始化控件
     * */
    private void init(){
        account=(EditText)findViewById(R.id.editText5_register_account);
        passwd1=(EditText)findViewById(R.id.editText6_register_passwd1);
        passwd2=(EditText)findViewById(R.id.editText7_register_passwd2);
        name=(EditText)findViewById(R.id.editText8_register_name);
        myclass=(EditText)findViewById(R.id.editText9_register_class);
        identify=(RadioButton) findViewById(R.id.radioButton3_register_identify);
        registerViewModel=new RegisterViewModel();

        account.addTextChangedListener(new TextWatcherAdapter());
        passwd1.addTextChangedListener(new TextWatcherAdapter());
        passwd2.addTextChangedListener(new TextWatcherAdapter());
        name.addTextChangedListener(new TextWatcherAdapter());
        myclass.addTextChangedListener(new TextWatcherAdapter());
        identify.addTextChangedListener(new TextWatcherAdapter());

        account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(((EditText) v).getText().toString().length()<12){
                        showRegisterMess("账号长度小于12");
                        registerViewModel.setError(true);
                    }
                }
            }
        });
        passwd1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String value=((EditText) v).getText().toString();
                    if(value.length()<6){
                        showRegisterMess("密码长度小于6");
                        registerViewModel.setError(true);
                    }else if(value.matches(".*[^A-Za-z0-9._]")){
                        registerViewModel.setError(true);
                        showRegisterMess("密码格式：0-9、a-z、._");
                    }
                }
            }
        });
        passwd2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String value1=passwd1.getText().toString();
                String value2=passwd2.getText().toString();
                if(!hasFocus){
                    if(!value1.equals(value2)){
                        registerViewModel.setError(true);
                        showRegisterMess("两次输入密码不一致");
                    }
                }
            }
        });

    }

    public void sendRegister(View view) {
        //总体格式检查

        registerViewModel.isVaild();
        if(!registerViewModel.isError()){
            //学校信息档案匹配
            if(registerViewModel.verifyInfo(RegisterActivity.this)){
                showRegisterMess("注册成功");
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                showRegisterMess("查无匹配信息");
            }
        }else{
            showRegisterMess("请输入正确格式信息");
        }

    }

    public void changeId(View view) {
        String userAccount=account.getText().toString();
        String userPd1=passwd1.getText().toString();
        String userPd2=passwd2.getText().toString();
        String userName=name.getText().toString();
        String userClass=myclass.getText().toString();
        String userId=null;
        if(identify.isChecked()){
            userId="0";
        }else{
            userId="1";
        }
        try{
            registerViewModel.dataChange(userAccount,userPd1,userPd2,userName,userClass,userId);
        } catch (NullPointerException e) {

        }
    }

    class TextWatcherAdapter implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String userAccount=account.getText().toString();
            String userPd1=passwd1.getText().toString();
            String userPd2=passwd2.getText().toString();
            String userName=name.getText().toString();
            String userClass=myclass.getText().toString();
            String userId=null;
            if(identify.isChecked()){
                userId="0";
            }else{
                userId="1";
            }
            try{
                registerViewModel.dataChange(userAccount,userPd1,userPd2,userName,userClass,userId);
            } catch (NullPointerException e) {

            }
        }
    }
    /**
     * 显示信息
     * */
    private void showRegisterMess(String errorString) {
        Toast.makeText(getApplicationContext(),errorString, Toast.LENGTH_SHORT).show();
    }
}
