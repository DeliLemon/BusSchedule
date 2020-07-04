package com.database.schoolbusschedule;
public class BusSchedule {
    private Integer scheduleID,remain_seat;
    private String departureDate,departurePlace,departureEnd,driverName,owner_account,driverPhone,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public Integer getRemain_seat() {
        return remain_seat;
    }

    public void setRemain_seat(Integer remain_seat) {
        this.remain_seat = remain_seat;
    }

    public String getOwner_account() {
        return owner_account;
    }

    public void setOwner_account(String owner_account) {
        this.owner_account = owner_account;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getDepartureEnd() {
        return departureEnd;
    }

    public void setDepartureEnd(String departureEnd) {
        this.departureEnd = departureEnd;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return "BusSchedule{" +
                "scheduleID=" + scheduleID +
                ", departurePlace=" + departurePlace +
                ", departureEnd=" + departureEnd +
                ", departureDate=" + departureDate +
                ", driverName=" + driverName +
                ", driverPhone=" + driverPhone +
                ", remain_seat=" + remain_seat +
                ", bus_status=" +status+
                ", owner_account=" + owner_account +
                '}';
    }

    public BusSchedule() {
    }

    public BusSchedule(Integer scheduleID, String departurePlace, String departureEnd, String departureDate, String driverName, String driverPhone, Integer remain_seat,String status, String owner_account) {
        this.scheduleID = scheduleID;
        this.driverPhone = driverPhone;
        this.remain_seat = remain_seat;
        this.owner_account = owner_account;
        this.departureDate = departureDate;
        this.departurePlace = departurePlace;
        this.departureEnd = departureEnd;
        this.driverName = driverName;
        this.status=status;
    }
}
