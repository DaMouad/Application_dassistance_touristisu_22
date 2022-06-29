package com.example.firstt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    public  static boolean connection ;
    public static DrawerLayout drawerLayout;

    protected static AlertDialog.Builder dialogBuilder ;
    protected static AlertDialog dialog ;

    protected static View ipad;
    public static String server;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF9454"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        ipad = getLayoutInflater().inflate(R.layout.ipadresse,null);

        dialogBuilder.setView(ipad);
        dialog = dialogBuilder.create();
        dialog.show();
        Button ipbtn = ipad.findViewById(R.id.getAd);
        EditText getip = ipad.findViewById(R.id.ip);
        ipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server=getip.getText().toString();
                dialog.hide();
            }
        });



        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }



        drawerLayout = findViewById(R.id.drawerlayout);


        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navigationView, navController);

        checkPermission();
        // Check if user has given permission to record audio, init the model after permission is granted

    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
    /*public  void checkConnection(){

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connection = true;
        }
        else
            connection = false;
    }*/
}