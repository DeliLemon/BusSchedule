package com.home.schoolbusschedule.ui.home;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.database.schoolbusschedule.BusSchedule;
import com.database.schoolbusschedule.DBAdapter;
import com.example.schoolbusschedule.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.home.schoolbusschedule.HomeActivity;

import java.util.List;

public class SchedulesFragment extends Fragment {
    private FragmentManager fragmentManager;
    private Bundle message;
    private HomeViewModel homeViewModel;
    private List<BusSchedule> scheduleslist;
    private ListView schedulesListView;
    private LayoutInflater myinflater;
    private  View schedulesView;
    private FloatingActionButton addButton;
    private ScheduleAdapter scheduleAdapter=null;
    private String[] test =new String[]{"Song","Football","Computer","Android","Apple"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        schedulesView=inflater.inflate(R.layout.fragment_schedules,container,false);
        myinflater =inflater;
        init();
        return  schedulesView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setBundle(Bundle bundle){
        this.message=bundle;
    }
    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }
    @SuppressLint("ResourceType")
    private void init(){
        initParameters();
        initListView();
        initAddButton();
    }

    private void initAddButton() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddScheduleFragment addScheduleFragment=new AddScheduleFragment();
                addScheduleFragment.setFragmentManager(fragmentManager);
                fragmentManager.beginTransaction()
                        .replace(R.id.layout_schedule_fragment,addScheduleFragment)
                        .addToBackStack(null)//返回时会再调用一次获取view
                        .commit();
            }
        });
    }

    private void initParameters(){
        message=((HomeActivity)getActivity()).getMessage();
        addButton=schedulesView.findViewById(R.id.floatbutton_add_home);
    }
    private void initListView(){
        String account=message.getString("account");
        DBAdapter dbAdapter=new DBAdapter(getContext());
        dbAdapter.open();
        scheduleslist=dbAdapter.querySchedules(account,"201710214626");
        dbAdapter.close();

        scheduleAdapter=new ScheduleAdapter();
        schedulesListView=(ListView)schedulesView.findViewById(R.id.schedule_list);
        schedulesListView.setAdapter(scheduleAdapter);
        schedulesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusSchedule schedule=scheduleslist.get(position);
                ItemFragment itemFragment=new ItemFragment();
                itemFragment.setSchedule(schedule);
                fragmentManager.beginTransaction()
                        .replace(R.id.layout_schedule_fragment,itemFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
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
                convertView= myinflater.inflate(R.layout.schedule_item,parent,false);
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
