package com.login.schoolbusschedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.database.schoolbusschedule.User;
import com.example.schoolbusschedule.R;
import com.home.schoolbusschedule.HomeActivity;
import com.register.schoolbusschedule.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar pb=null;
    private LoginViewModel loginViewModel=new LoginViewModel();
    private EditText editAccount =null;
    private EditText editPassword=null;
    private Button loginbtn=null;
    private Button regiseter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        if(!isFirst()){
            SharedPreferences sp = getSharedPreferences("userLogin",
                    Context.MODE_PRIVATE);
            String account=sp.getString("account",null);
            String passwd=sp.getString("passwd",null);
            if(account!=null&&passwd!=null){
                editAccount.setText(account);
                editPassword.setText(passwd);
            }
        }

    }

    /**
     * 判断是否第一次登录
     * */
    public boolean isFirst() {
        SharedPreferences sp = getSharedPreferences("userLogin",
                Context.MODE_PRIVATE);
        boolean isFirst=sp.getBoolean("FirstLogin", true);
        return isFirst;
    }

    /**
     * 登录按钮的回调函数
     * */
    public void login(View view) {
        //检查输入格式
        if(loginViewModel.getLoginStatus()==LoginViewModel.LOGIN_SUCCEED){
            pb.setVisibility(View.VISIBLE);
            //验证账号
            User user=loginViewModel.verifyAccount(LoginActivity.this);
            try{
                if(user==null) {
                    throw new NullPointerException();
                } else{
                    SharedPreferences sp = getSharedPreferences("userLogin",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sp.edit();
                    editor.putBoolean("FirstLogin",false);
                    editor.putString("account",editAccount.getText().toString());
                    editor.putString("passwd",editPassword.getText().toString());
                    editor.commit();
                    Bundle info=new Bundle();
                    info.putString("account",user.getAccount());
                    info.putString("userName",user.getName());
                    info.putString("myClass",user.getMyclass());
                    info.putString("position",user.getPosition());
                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtras(info);
                    startActivity(intent);
                    finish();
                }
            }catch (NullPointerException e){
                showLoginFailed("登录失败，账号或密码错误");
            }

        }
    }


    /**
     * 初始化视图控件
     *
     * */

    private void init(){
        pb=(ProgressBar)findViewById(R.id.progressBar1_login);
        editAccount =(EditText)findViewById(R.id.editText1_loginAccount);
        editPassword=(EditText)findViewById(R.id.editText2_loginPassword);
        loginbtn=(Button)findViewById(R.id.button1_login);
        regiseter=(Button)findViewById(R.id.button2_register);
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                loginViewModel.LoginDataChanged(editAccount.getText().toString(),editPassword.getText().toString());

            }
        };

        editAccount.addTextChangedListener(afterTextChangedListener);
        editPassword.addTextChangedListener(afterTextChangedListener);

        editAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(loginViewModel.getLoginStatus()!=LoginViewModel.LOGIN_SUCCEED && !hasFocus){
                    showLoginFailed(loginViewModel.getLoginError());
                }
            }
        });
        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showLoginFailed(loginViewModel.getLoginError());
                }
                return false;
            }
        });
    }
    /**
     * 显示信息
     * */
    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(),errorString, Toast.LENGTH_SHORT).show();
    }

    /**注册
     * */
    public void register(View view) {
        Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
