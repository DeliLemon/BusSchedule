package com.home.schoolbusschedule.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.database.schoolbusschedule.BusSchedule;
import com.database.schoolbusschedule.DBAdapter;
import com.example.schoolbusschedule.R;
import com.home.schoolbusschedule.HomeActivity;

public class AddScheduleFragment extends Fragment {
    View add_schedule_view;
    Button positive,negative;
    FragmentManager fragmentManager;
    private Bundle message;
    private BusSchedule schedule=null;
    private EditText deplace,end,date,driver,phone,seat,status;
    private boolean isError=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        add_schedule_view=inflater.inflate(R.layout.frafment_add_schedule,container,false);
        init();
        return add_schedule_view;
    }
//
    private void init() {
        initParameter();
        initButton();
        initCheckRule();
    }

    private void initButton() {
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSchedule();
                if(schedule!=null&&!isError){
                    DBAdapter dbAdapter=new DBAdapter(getContext());
                    dbAdapter.open();
                    dbAdapter.insertSchedule(schedule);
                    dbAdapter.close();
                    fragmentManager.popBackStack();
                }
            }
        });
        negative.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });
    }
    private void initSchedule(){
        String userAccount=message.getString("account");
        schedule=new BusSchedule();
        schedule.setScheduleID(null);
        schedule.setDeparturePlace(deplace.getText().toString());
        schedule.setDepartureEnd(end.getText().toString());
        schedule.setDepartureDate(date.getText().toString());
        schedule.setDriverName(driver.getText().toString());
        schedule.setDriverPhone(phone.getText().toString());
        schedule.setStatus(status.getText().toString());
        schedule.setRemain_seat(Integer.parseInt(seat.getText().toString()));
        schedule.setOwner_account(userAccount);
    }
    private void initParameter() {
        positive=add_schedule_view.findViewById(R.id.button3_positive_add);
        negative=add_schedule_view.findViewById(R.id.button2_cancel_add);
        deplace=add_schedule_view.findViewById(R.id.editText5_add_item_more_dePlace);
        end=add_schedule_view.findViewById(R.id.editText6_add_item_more_dpend);
        date=add_schedule_view.findViewById(R.id.editText7_add_item_more_date);
        driver=add_schedule_view.findViewById(R.id.editText8_add_item_more_driver);
        phone=add_schedule_view.findViewById(R.id.editText9_add_item_more_phone);
        seat=add_schedule_view.findViewById(R.id.editText10_add_item_more_reSeat);
        status=add_schedule_view.findViewById(R.id.editText10_manage_add_more_status);
        message=((HomeActivity)getActivity()).getMessage();
    }
    private void initCheckRule(){
        this.isError=false;
        //Todo
    }
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
