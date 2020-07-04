package com.register.schoolbusschedule;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.database.schoolbusschedule.DBAdapter;
import com.database.schoolbusschedule.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterViewModel {
    private String account="",passwd1="",passwd2="",name="",myclass="",identify="";
    private Context context;
    Thread mthred;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                isError=false;
            }else{
                isError=true;
            }
            Log.i("handler",""+isError);
        }
    };
    private boolean isError=true;
    public RegisterViewModel(){
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void dataChange(String userAccount, String userPd1, String userPd2, String name, String userClass, String userId) throws NullPointerException{
        this.account = userAccount;
        this.passwd1 = userPd1;
        this.passwd2 = userPd2;
        this.name = name;
        this.myclass = userClass;
        this.identify = userId;
    }

    public boolean verifyInfo(Context c) {

        context=c;
        boolean verify=false;
        File file=new File("data.txt");
        FileInputStream fis = null;
        String content="";
        String pattern="("+account+" "+name+" "+identify+" "+myclass+")";
        Pattern pattern2= Pattern.compile(pattern);
        try {
            fis = context.openFileInput("data.txt");
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            content=new String(buffer);
            //匹配子串
            Matcher matcher=pattern2.matcher(content);
            verify=matcher.find();
        } catch (FileNotFoundException e) {
            Log.i("findfile","没有data.txt");
        } catch (IOException e) {
            Log.i("readfile","读取失败");
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(verify){
            createAccount();
        }
        return verify;
    }

    public void isVaild(){
        //账号格式
        isError=false;
        if(account.length()<12){
            isError=true;
            return;
        }
        if(passwd1.length()<6){
            isError=true;
            return;
        }else{
            if(passwd1.matches(".*[^A-Za-z0-9._]")){
                isError=true;
                return;
            }
        }
        if(!passwd1.equals(passwd2)){
            isError=true;
            return;
        }
    }
    private void createAccount(){
        new Thread(){
            @Override
            public void run() {
                DBAdapter dbAdapter=new DBAdapter(context);
                dbAdapter.open();
                String position;
                if(identify.equals("0")){
                    position="学生";
                }else {
                    position="教师";
                }
                User user=new User(account,passwd1,name,myclass,position);
                Log.i("userinfo",user.toString());
                dbAdapter.insertAccount(user);
                dbAdapter.close();
            }
        }.start();
    }
}
