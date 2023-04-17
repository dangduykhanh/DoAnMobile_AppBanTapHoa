package com.example.doan_ltddnc_appbantaphoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration ;
    Toolbar toolbar ;
    NavController navController ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar =findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        navController = Navigation.findNavController(this ,R.id.nav_sign);
        appBarConfiguration =new AppBarConfiguration.Builder(R.id.fullnameFragment ,R.id.usernamePasswordFragment).build();
        NavigationUI.setupActionBarWithNavController(this ,navController ,appBarConfiguration);
        NavigationUI.setupWithNavController(toolbar,navController);
    }
}