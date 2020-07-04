package com.database.schoolbusschedule;

public class SchoolBus {
    private Integer BusNum,seat;
    private String status;

    public Integer getBusNum() {
        return BusNum;
    }

    public void setBusNum(Integer busNum) {
        BusNum = busNum;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SchoolBus(Integer busNum, Integer seat, String status) {
        BusNum = busNum;
        this.seat = seat;
        this.status = status;
    }

    public SchoolBus() {
    }

    @Override
    public String toString() {
        return "SchoolBus{" +
                "BusNum=" + BusNum +
                ", seat=" + seat +
                ", status=" + status +
                '}';
    }
}
