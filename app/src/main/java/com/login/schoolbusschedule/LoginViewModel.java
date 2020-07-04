package com.login.schoolbusschedule;

import android.content.Context;

import com.database.schoolbusschedule.DBAdapter;
import com.database.schoolbusschedule.User;

public class LoginViewModel {
	final static int LOGIN_LOCK=0;
	final static int LOGIN_ACCOUNTERROR=1;
	final static int LOGIN_PASSWORDERROR=2;
	final static int LOGIN_ALLERROR=3;
	final static int LOGIN_SUCCEED=4;
	private String account=null;
	private String password=null;
	String LoginError=null;
	private int loginStatus=LOGIN_LOCK;
	
	
	public int getLoginStatus() {
		return loginStatus;
	}

	public LoginViewModel(String account, String password) {
		super();
		this.account = account;
		this.password = password;
	}
	
	public LoginViewModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void LoginDataChanged(String account,String password) {
		this.account=account;
		this.password=password;
		if(!isAccountVaild()) {
			loginStatus=LOGIN_ACCOUNTERROR;
		}
		if(!isPasswordVaild()) {
			loginStatus=LOGIN_PASSWORDERROR;
		}
		if(isAccountVaild()&&isPasswordVaild()) {
			loginStatus=LOGIN_SUCCEED;
		}
		if(!isAccountVaild()&&!isPasswordVaild()) {
			loginStatus=LOGIN_ALLERROR;
		}
	}
	public boolean isAccountVaild() {
		if(account.length() <12)
			return false;
		return true;
	}
	public boolean isPasswordVaild() {
		if(password.length()<6) {
			return false;
		}else if(password.matches(".*[^A-Za-z0-9._]")) {
			return false;
		}
		return true;
	}
	public String getLoginError() {
		switch (loginStatus) {
			case LOGIN_LOCK:
				LoginError="无输入内容";
				break;
			case LOGIN_ACCOUNTERROR:
				LoginError="账号长度小于12";
				break;
			case LOGIN_PASSWORDERROR:
				LoginError="密码长度不低于6，格式：0-9、a-z、._";
				break;
			case LOGIN_ALLERROR:
				LoginError="账号长度小于12\n密码长度不低于6，格式：0-9、a-z、._";
				break;
			case LOGIN_SUCCEED:
				LoginError=null;
				break;
		}
		return LoginError;
	}
	public User verifyAccount(Context context){
		DBAdapter dbAdapter=new DBAdapter(context);
		dbAdapter.open();
		User user=dbAdapter.queryAccount(account,password);
		//User user=new User("201710214626",null,"西瓜大人","物联网172","学生");
		dbAdapter.close();
		return user;
	}
	
}
