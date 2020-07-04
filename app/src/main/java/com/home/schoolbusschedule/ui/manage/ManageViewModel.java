package com.home.schoolbusschedule.ui.manage;

import android.content.Context;
import android.widget.ListView;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import com.database.schoolbusschedule.BusSchedule;

import java.util.List;

public class ManageViewModel extends ViewModel {

    private Context context;
    private List<BusSchedule> scheduleslist;
    private ListView schedulesListView;
    private FragmentManager fragmentManager;
    public ManageViewModel(Context context) {
        this.context = context;
    }

    public ManageViewModel() {

    }
    public void search(){

    }

}