package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class ActivityInstructor extends AppCompatActivity {


    private TextView welcome;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    InstructorFragmentAdpater fragmentAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);


        welcome = (TextView) findViewById(R.id.textViewWelcome_Instructor);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_Instructor);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager_Instructor);


        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.INTENT_LOGIN_USERNAME);
        welcome.setText("Welcome Instructor "+ name+"!");

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdpater = new InstructorFragmentAdpater(fm,getLifecycle(),name);
        viewPager2.setAdapter(fragmentAdpater);

        tabLayout.addTab(tabLayout.newTab().setText("All Courses"));
        tabLayout.addTab(tabLayout.newTab().setText("My Courses"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos =tab.getPosition();
                if(pos!=2) {
                    viewPager2.setCurrentItem(pos);
                    fragmentAdpater = new InstructorFragmentAdpater(fm,getLifecycle(),name);
                    viewPager2.setAdapter(fragmentAdpater);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}