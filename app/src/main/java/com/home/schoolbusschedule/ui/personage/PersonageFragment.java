package com.home.schoolbusschedule.ui.personage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schoolbusschedule.R;
import com.home.schoolbusschedule.HomeActivity;
import com.login.schoolbusschedule.LoginActivity;

public class PersonageFragment extends Fragment {
    private View myView;
    private Bundle message;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.fragment_personage,container,false);
        init();
        return myView;
    }

    private void init() {
        message=((HomeActivity)getActivity()).getMessage();
        TextView account,name,myclass,position;
        account=myView.findViewById(R.id.textView3_account_personage);
        name=myView.findViewById(R.id.textView4_name_personage);
        myclass=myView.findViewById(R.id.textView5_class_personage);
        position=myView.findViewById(R.id.textView6_position_personage);
        Button exit=myView.findViewById(R.id.button_exit_personage);
        account.setText(message.getString("account"));
        name.setText(message.getString("userName"));
        myclass.setText(message.getString("myClass"));
        position.setText(message.getString("position"));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("intent","intent");
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
