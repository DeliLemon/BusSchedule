package com.home.schoolbusschedule.ui.manage;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.database.schoolbusschedule.BusSchedule;
import com.database.schoolbusschedule.DBAdapter;
import com.example.schoolbusschedule.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.home.schoolbusschedule.HomeActivity;

public class Item_More_Manage_Fragment extends Fragment {
    FragmentManager fragmentManager;
    Bundle message;
    private EditText id,deplace,end,date,driver,phone,seat,status;
    private BusSchedule schedule;
    private View item_more_manage_view;
    private LayoutInflater myiInflater;
    private FloatingActionButton edit_button;
    private boolean isEdite=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        item_more_manage_view=inflater.inflate(R.layout.fragment_more_manage,container,false);
        myiInflater=inflater;
        init();
        return item_more_manage_view;
    }

    private void init(){
        initParameters();
        initMyView();
        initeditButton();
    }
    private void initParameters(){
        id=item_more_manage_view.findViewById(R.id.editText4_manage_item_more_id);
        deplace=item_more_manage_view.findViewById(R.id.editText5_manage_item_moredePlace);
        end=item_more_manage_view.findViewById(R.id.editText6_manage_item_more_dpend);
        date=item_more_manage_view.findViewById(R.id.editText7_manage_item_more_date);
        driver=item_more_manage_view.findViewById(R.id.editText8_manage_item_more_driver);
        phone=item_more_manage_view.findViewById(R.id.editText9_manage_item_more_phone);
        seat=item_more_manage_view.findViewById(R.id.editText10_manage_item_more_reSeat);
        status=item_more_manage_view.findViewById(R.id.editText10_manage_item_more_status);
        edit_button=item_more_manage_view.findViewById(R.id.floatingActionButton_item_more_manage);
        message=((HomeActivity)getActivity()).getMessage();
    }
    private void initMyView(){
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
        }
    }
    private void initeditButton(){
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEdite){
                    deplace.setEnabled(true);
                    end.setEnabled(true);
                    date.setEnabled(true);
                    driver.setEnabled(true);
                    phone.setEnabled(true);
                    status.setEnabled(true);
                    seat.setEnabled(true);
                    isEdite=true;
                    ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.colorAccent);
                    edit_button.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    edit_button.setBackgroundTintList(colorStateList);
                }else{
                    deplace.setEnabled(false);
                    end.setEnabled(false);
                    date.setEnabled(false);
                    driver.setEnabled(false);
                    phone.setEnabled(false);
                    status.setEnabled(false);
                    seat.setEnabled(false);
                    isEdite=false;
                    updateSChedule();
                    ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.colorDarkGreen);
                    edit_button.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    edit_button.setBackgroundTintList(colorStateList);
                }
            }
        });
    }
    private void updateSChedule(){
        schedule.setDeparturePlace(deplace.getText().toString());
        schedule.setDepartureEnd(end.getText().toString());
        schedule.setDepartureDate(date.getText().toString());
        schedule.setDriverName(driver.getText().toString());
        schedule.setDriverPhone(phone.getText().toString());
        schedule.setStatus(status.getText().toString());
        schedule.setRemain_seat(new Integer(seat.getText().toString()));
        DBAdapter dbAdapter=new DBAdapter(getContext());
        dbAdapter.open();
        dbAdapter.updateSchedule(schedule);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setMessage(Bundle message) {
        this.message = message;
    }
    public void  setSchedule(BusSchedule schedule){
        this.schedule=schedule;
    }
}
