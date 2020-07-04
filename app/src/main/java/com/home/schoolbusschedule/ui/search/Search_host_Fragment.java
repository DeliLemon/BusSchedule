package com.home.schoolbusschedule.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.database.schoolbusschedule.BusSchedule;
import com.database.schoolbusschedule.DBAdapter;
import com.example.schoolbusschedule.R;
import com.home.schoolbusschedule.ui.home.ItemFragment;

public class Search_host_Fragment extends Fragment {
    View hostView;
    FragmentManager fragmentManager;
    LayoutInflater myInflater;
    private ListView listView;
    private BusSchedule busSchedule;
    private ScheduleAdapter scheduleAdapter=null;
    private EditText searchLine;
    private Button searchBtn;
    private int size=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        hostView=inflater.inflate(R.layout.fragment_search_host,container,false);
        myInflater=inflater;
        init();
        return hostView;
    }

    private void init() {
        initParameter();
        initBtn();
        initSearchLine();
        initItemClick();
    }

    private void initSearchLine() {
        searchLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                size=0;
                scheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemFragment itemFragment=new ItemFragment();
                itemFragment.setSchedule(busSchedule);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_search_content,itemFragment)
                        .commit();
            }
        });
    }

    private void initBtn() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scheduleID=searchLine.getText().toString();
                DBAdapter dbAdapter=new DBAdapter(getContext());
                busSchedule=null;
                dbAdapter.open();
                busSchedule=dbAdapter.querySchedule(Integer.parseInt(scheduleID));
                if(busSchedule==null){
                    Toast.makeText(getContext(),"查无结果",Toast.LENGTH_SHORT).show();
                }else{
                    size=1;
                    scheduleAdapter.notifyDataSetChanged();
                }
                dbAdapter.close();
            }
        });
    }

    private void initParameter(){
        searchBtn=hostView.findViewById(R.id.button_search);
        searchLine=hostView.findViewById(R.id.editText_search_host);
        listView=hostView.findViewById(R.id.search_host_listView);
        scheduleAdapter=new ScheduleAdapter();
        listView.setAdapter(scheduleAdapter);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    class ScheduleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object getItem(int position) {
            return busSchedule;
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
                convertView= myInflater.inflate(R.layout.schedule_item,parent,false);
                TextView scid=(TextView)convertView.findViewById(R.id.textView4_item_ID);
                TextView scStatus=(TextView)convertView.findViewById(R.id.textView5_item_status);
                viewHolder.scId=scid;
                viewHolder.scStatus=scStatus;
                convertView.setTag(viewHolder);
            }else{//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //格式化数据
            if(busSchedule!=null){
                String status="出发："+busSchedule.getDeparturePlace()+"--目的地："+busSchedule.getDepartureEnd()+"\n剩余座位："+busSchedule.getRemain_seat();
                //设置数据
                viewHolder.scId.setText("编号："+busSchedule.getScheduleID());
                viewHolder.scStatus.setText(status);
            }
            return convertView;
        }
    }
    class  ViewHolder{
        public TextView scStatus;
        public TextView scId;

    }
}
