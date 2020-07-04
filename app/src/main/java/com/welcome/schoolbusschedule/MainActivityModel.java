package com.welcome.schoolbusschedule;

import android.content.Context;

public class MainActivityModel {
    private Context context;
    public MainActivityModel(){
    }
    public MainActivityModel(Context context){
        this.context=context;
    }
    public void init(){
        initSchedules();
    }
    public void initSchedules(){
//        DBAdapter dbAdapter=new DBAdapter(context);
//        dbAdapter.open();
//        List<BusSchedule>list=dbAdapter.querySchedules(InitParameters.PUBLIC_ACCOUNT);
//        Date now=new Date();
//        SimpleDateFormat mattch= new SimpleDateFormat("yyyy-MM-dd/HH:mm");
//        int index=0;
//        while (index<list.size()){
//            BusSchedule busSchedule=list.get(index);
//            String old=busSchedule.getDepartureDate();
//            long result;
//            try {
//                Date past=mattch.parse(old);
//                result=now.getTime()-past.getTime();
//                if(result>=0){
//                    busSchedule.setStatus("出发");
//                }else if(result<0){
//                    busSchedule.setStatus("等待");
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dbAdapter.updateSchedule(busSchedule);
//            index++;
//        }
//        dbAdapter.close();
    }
    public void updateScheduleRule(){

    }
}
