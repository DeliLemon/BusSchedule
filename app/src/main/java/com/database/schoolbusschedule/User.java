package com.database.schoolbusschedule;

public class User {
    private String account ,passwd,name,myclass,position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMyclass() {
        return myclass;
    }

    public void setMyclass(String myclass) {
        this.myclass = myclass;
    }

    @Override
    public String toString() {
        return "User{" +
                "account=" + account +
                ", name=" + name +
                ", myclass=" + myclass +
                ", position="+position+
                '}';
    }

    public User() {
    }

    public User( String account,String passwd, String name, String myclass,String postion) {
        this.passwd = passwd;
        this.name = name;
        this.myclass = myclass;
        this.account = account;
        this.position=postion;
    }
}
