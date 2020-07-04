package com.home.schoolbusschedule.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.database.schoolbusschedule.BusSchedule;
import com.database.schoolbusschedule.DBAdapter;
import com.example.schoolbusschedule.R;
import com.home.schoolbusschedule.HomeActivity;
import com.welcome.schoolbusschedule.InitParameters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ItemFragment extends Fragment{
    View myView=null;
    FragmentManager fragmentManager;
    Button button;
    Bundle message;
    private EditText id,deplace,end,date,driver,phone,seat,status;
    private BusSchedule schedule;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       myView=inflater.inflate(R.layout.fragment_item_more,container,false);
       init();
       return myView;
    }
    public void init(){
        initParameters();
        initDate();
        initClick_btn();
    }
    private void initClick_btn(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(signUpRule()){
                   String btnText=button.getText().toString();
                   Integer re_seat=schedule.getRemain_seat();
                   DBAdapter dbAdapter=new DBAdapter(getContext());
                   dbAdapter.open();
                   if(btnText.equals("预约")){
                       re_seat--;
                       button.setText("已预约/取消预约");
                       dbAdapter.addRelation(message.getString("account"),schedule.getScheduleID());
                   }else{
                       re_seat++;
                       button.setText("预约");
                       dbAdapter.deleteRelation(message.getString("account"),schedule.getScheduleID());
                   }
                   schedule.setRemain_seat(re_seat);
                   dbAdapter.updateSchedule(schedule);
                   dbAdapter.close();

                   seat.setText(re_seat+"");
               }else{
                   Toast.makeText(getContext(),"预约失败，未在指定时间",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
    private void initDate(){
        if(schedule!=null){
            Integer scheduleID=schedule.getScheduleID();
            String userAccount=message.getString("account");
            id.setText(scheduleID+"");
            deplace.setText(schedule.getDeparturePlace());
            end.setText(schedule.getDepartureEnd());
            date.setText(schedule.getDepartureDate());
            driver.setText(schedule.getDriverName());
            phone.setText(schedule.getDriverPhone());
            status.setText(schedule.getStatus());
            seat.setText(schedule.getRemain_seat()+"");
            DBAdapter dbAdapter=new DBAdapter(getContext());
            dbAdapter.open();
            boolean isSign=dbAdapter.queryRelation(userAccount,scheduleID);
            if(isSign){
                button.setText("已预约/取消预约");
            }
            dbAdapter.close();
        }
    }
    private void initParameters(){

        id=myView.findViewById(R.id.editText4_item_more_id);
        deplace=myView.findViewById(R.id.editText5_item_moredePlace);
        end=myView.findViewById(R.id.editText6_item_more_dpend);
        date=myView.findViewById(R.id.editText7_item_more_date);
        driver=myView.findViewById(R.id.editText8_item_more_driver);
        phone=myView.findViewById(R.id.editText9_item_more_phone);
        seat=myView.findViewById(R.id.editText10_item_more_reSeat);
        status=myView.findViewById(R.id.editText10_item_more_status);
        button=myView.findViewById(R.id.button_item_more);

        message=((HomeActivity)getActivity()).getMessage();
    }
    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }
    public void setBundle(Bundle bundle){
        this.message=bundle;
    }
    public void  setSchedule(BusSchedule schedule){
        this.schedule=schedule;
    }
    private boolean signUpRule(){
        boolean ispermit=false;
        String position=message.getString("position");
        String departurDate=schedule.getDepartureDate();

        Calendar calendar=new GregorianCalendar();
        int currentHour=calendar.get(GregorianCalendar.HOUR_OF_DAY);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd/HH:mm");
        int limitHour=0;
        try {
            calendar.setTime(simpleDateFormat.parse(departurDate));
            limitHour=calendar.get(GregorianCalendar.HOUR_OF_DAY);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //判读是否为校车
        if(schedule.getOwner_account().equals(InitParameters.PUBLIC_ACCOUNT)){
            //是否为教师，教师通道优先
            if(position.equals("教师")){
                if(limitHour-currentHour>=0){
                    //教师规定都可以等记
                    ispermit=true;
                }
            }else{
                if(2>=limitHour-currentHour && limitHour-currentHour>=0){
                    //学生开车前两小时可以等记
                    ispermit=true;
                }
            }
            Log.i("permit",message.getString("account"));
        }else{
            if(limitHour-currentHour>=0){
                Log.i("permit","true");
                ispermit=true;
            }
        }
        //为了展示，这里设置为true
        return ispermit;
    }
}
