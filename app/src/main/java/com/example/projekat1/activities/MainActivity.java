package com.example.projekat1.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.projekat1.R;
import com.example.projekat1.models.Ticket;
import com.example.projekat1.spinners.PrioritySpinnerListener;
import com.example.projekat1.viewmodels.RecyclerViewModel;
import com.example.projekat1.viewpager.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    private ViewPager viewPager;
    public static RecyclerViewModel recyclerViewModel;


    private PrioritySpinnerListener psl = new PrioritySpinnerListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // initSpinners();
        return true;
    }

    private void init(){
        initView();
        initListeners();
        initViewPager();
        initNavigation();
    }

    private void initListeners() {
        //spinnerPriority.setOnItemSelectedListener(psl);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    private void initView(){

    }

    private void initNavigation(){
        ((BottomNavigationView)findViewById((R.id.bottomNavigation))).setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_1: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                case R.id.navigation_2: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                case R.id.navigation_3: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
                case R.id.navigation_4: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_4, false); break;
            }
            return true;
        });
    }
}