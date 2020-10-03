package com.neory.marketsimplifieddemoapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.neory.marketsimplifieddemoapp.ui.dashboard.DashboardFragment;
import com.neory.marketsimplifieddemoapp.ui.home.HomeFragment;
import com.neory.marketsimplifieddemoapp.ui.notifications.NotificationsFragment;

public class BottomNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    NetworkChangeReceiver networkChangeReceiver= new NetworkChangeReceiver();
    BottomNavigationView navView;
    FragmentTransaction fragmentTransaction;
    HomeFragment homeFragment=new HomeFragment();
    DashboardFragment dashboardFragment=new DashboardFragment();
    NotificationsFragment notificationsFragment=new NotificationsFragment();
    private Fragment active=homeFragment;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        navView = findViewById(R.id.nav_view);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.nav_host_fragment,notificationsFragment,"Notification").hide(notificationsFragment).commit();
        manager.beginTransaction().add(R.id.nav_host_fragment,dashboardFragment,"Dash").hide(dashboardFragment).commit();
        manager.beginTransaction().add(R.id.nav_host_fragment,homeFragment,"Home").commit();
        navView.setOnNavigationItemSelectedListener(this);
    }

    protected void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }
    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(networkChangeReceiver);
    }

    public void ShowSneak(Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (noConnectivity) {
                Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "Disconnected", Snackbar.LENGTH_LONG);
                snackBar.show();
            } else {
                Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "Connected", Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount()>0){
          manager.popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.navigation_home:
                manager.beginTransaction().hide(active).show(homeFragment).commit();
                active=homeFragment;
                return  true;
            case R.id.navigation_dashboard:
                manager.beginTransaction().hide(active).show(dashboardFragment).commit();
                active=dashboardFragment;
                return  true;
            case R.id.navigation_notifications:
                manager.beginTransaction().hide(active).show(notificationsFragment).commit();
                active=notificationsFragment;
                return  true;
        }
        return false;
    }
}