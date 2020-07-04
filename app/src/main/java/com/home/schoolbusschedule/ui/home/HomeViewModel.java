package com.home.schoolbusschedule.ui.home;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private Activity activity;
    private Context context;

    public HomeViewModel() {

    }

    public HomeViewModel(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }
}