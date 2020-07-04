package com.home.schoolbusschedule.ui.manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.List;

public class Schedules_Manage_Fragment extends Fragment {
    private FragmentManager fragmentManager;
    private View scheduleManageView;
    private LayoutInflater myiInflater;
    private List<BusSchedule> scheduleslist;
    private ListView schedulesListView;
    private ScheduleAdapter scheduleAdapter=null;
    private FloatingActionButton edit_button;
    private boolean isEdite=false;
    Bundle message;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scheduleManageView=inflater.inflate(R.layout.fragment_schedule_manage,container,false);
        myiInflater=inflater;
        init();
        return scheduleManageView;
    }

    private void init() {
        initParameters();
        initListView();
        initeditButton();
    }
    private void initParameters(){
        message=((HomeActivity)getActivity()).getMessage();
        edit_button=scheduleManageView.findViewById(R.id.floatingActionButton_schedule_manage);
        String useAccount=message.getString("account");
        DBAdapter dbAdapter=new DBAdapter(getContext());
        dbAdapter.open();
        scheduleslist=dbAdapter.querySchedules(useAccount);
        dbAdapter.close();
        scheduleAdapter=new ScheduleAdapter();
        schedulesListView=(ListView)scheduleManageView.findViewById(R.id.schedule_list_manage);

    }
    private void initListView(){
        schedulesListView.setAdapter(scheduleAdapter);
        schedulesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(!isEdite){
                    BusSchedule schedule=scheduleslist.get(position);
                    Item_More_Manage_Fragment item_more_manage_fragment=new Item_More_Manage_Fragment();
                    item_more_manage_fragment.setSchedule(schedule);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_manage_content,item_more_manage_fragment)
                            .addToBackStack(null)
                            .commit();
                }else{
                    AlertDialog.Builder ask=new AlertDialog.Builder(getContext());
                    ask.setIcon(R.drawable.edit)
                            .setTitle("提示")
                            .setMessage("是否删除")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBAdapter dbAdapter=new DBAdapter(getContext());
                                    dbAdapter.open();
                                    dbAdapter.deleteSchedule(scheduleslist.get(position));
                                    dbAdapter.close();
                                    scheduleslist.remove(position);
                                    scheduleAdapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create().show();
                }
            }
        });
    }
    private void initeditButton(){
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEdite){
                    isEdite=true;
                    ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.colorAccent);
                    edit_button.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    edit_button.setBackgroundTintList(colorStateList);
                }else{
                    isEdite=false;
                    ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.colorDarkGreen);
                    edit_button.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                    edit_button.setBackgroundTintList(colorStateList);
                }
            }
        });
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }



    class ScheduleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return scheduleslist.size();
        }

        @Override
        public Object getItem(int position) {
            return scheduleslist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView ==null){
                viewHolder=new ViewHolder();
                convertView=myiInflater.inflate(R.layout.schedule_item,parent,false);
                TextView scid=(TextView)convertView.findViewById(R.id.textView4_item_ID);
                TextView scStatus=(TextView)convertView.findViewById(R.id.textView5_item_status);
                viewHolder.scId=scid;
                viewHolder.scStatus=scStatus;
                convertView.setTag(viewHolder);
            }else{//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //格式化数据
            BusSchedule busSchedule=scheduleslist.get(position);
            String status="出发："+busSchedule.getDeparturePlace()+"--目的地："+busSchedule.getDepartureEnd()+"\n剩余座位："+busSchedule.getRemain_seat();
            //设置数据
            viewHolder.scId.setText("编号："+busSchedule.getScheduleID());
            viewHolder.scStatus.setText(status);
            return convertView;
        }
    }
    class  ViewHolder{
        public TextView scStatus;
        public TextView scId;

    }

}
