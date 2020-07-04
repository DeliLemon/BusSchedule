package com.database.schoolbusschedule;

public class Relation {
    private Integer account,scheduleID;

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Relation(Integer account, Integer scheduleID) {
        this.account = account;
        this.scheduleID = scheduleID;
    }
    public Relation(){
        this.account=null;
        this.scheduleID=null;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "account=" + account +
                ", scheduleID=" + scheduleID +
                '}';
    }
}
