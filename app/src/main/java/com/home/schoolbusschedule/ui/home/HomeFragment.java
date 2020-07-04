package com.home.schoolbusschedule.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.schoolbusschedule.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return root;
    }
    public void init(){
        FragmentManager fragmentManager=getFragmentManager();
        SchedulesFragment schedulesFragment=new SchedulesFragment();
        schedulesFragment.setFragmentManager(fragmentManager);
        fragmentManager.beginTransaction().add(R.id.layout_schedule_fragment,schedulesFragment).commit();
    }
}