package com.home.schoolbusschedule;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.schoolbusschedule.R;
import com.google.android.material.navigation.NavigationView;
import com.home.schoolbusschedule.ui.home.HomeViewModel;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle message;
    private HomeViewModel homeViewModel;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_schedule, R.id.nav_search,
                R.id.nav_settings, R.id.nav_share, R.id.nav_service)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        init();
    }
    private void init(){
        initParameters();
        initHeader();
    }
    private void initParameters(){
        message=getIntent().getExtras();
    }
    private void initHeader(){
        View header=navigationView.getHeaderView(0);
        String account=message.getString("account");
        String name=message.getString("userName");
        TextView textView_account=header.findViewById(R.id.textView_header_account);
        TextView textView_name=header.findViewById(R.id.textView_header_name);
        textView_account.setText(account);
        textView_name.setText(name);
//        Button head=header.findViewById(R.id.imageView_header);
//        head.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(HomeActivity.this,"头像",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setMessage(Bundle bundle){
        this.message=bundle;
    }
    public android.os.Bundle getMessage() {
        return message;
    }

    public void test(View view) {
        navController.navigate(R.id.nav_personage);
        drawer.closeDrawer(Gravity.LEFT);

    }


    public void switchView(View view) {
        switch (view.getId()){
            case R.id.button_home_schedules:
                navController.navigate(R.id.nav_home);
                break;
            case R.id.button2_manage_schedules:
                navController.navigate(R.id.nav_schedule);
                break;
            case R.id.button3_search_schedules:
                navController.navigate(R.id.nav_search);
                break;
            case R.id.button4_settings_schedules:
                navController.navigate(R.id.nav_settings);
                break;
        }
    }
}
