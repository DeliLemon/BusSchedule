package com.home.schoolbusschedule.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.schoolbusschedule.R;

public class SearchFragment extends Fragment {

    private SearchViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        Search_host_Fragment search_host_fragment=new Search_host_Fragment();
        search_host_fragment.setFragmentManager(getFragmentManager());
        getFragmentManager().beginTransaction().add(R.id.fragment_search_content,search_host_fragment).commit();
        return root;
    }
}