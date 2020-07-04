package com.home.schoolbusschedule.ui.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.schoolbusschedule.R;

public class ManageFragment extends Fragment {
    FragmentManager fragmentManager;
    private ManageViewModel galleryViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(ManageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage, container, false);

        init();


        return root;
    }
    private void init(){
        fragmentManager=getFragmentManager();
        Schedules_Manage_Fragment schedules_manage_fragment=new Schedules_Manage_Fragment();
        schedules_manage_fragment.setFragmentManager(fragmentManager);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_manage_content,schedules_manage_fragment)
                .commit();

    }
}