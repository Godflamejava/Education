package com.example.education;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ClassroomActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        //link views
        getViews();

        //setting toolbar

        //adapter setup
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        //attaching fragments to adapter
        pagerAdapter.addFragment(new PeopleFragment(),"People");
        pagerAdapter.addFragment(new ClassWorkFragment(),"Classwork");
        pagerAdapter.addFragment(new StreamFragment(),"Lectures");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void getViews() {
        tabLayout = findViewById(R.id.mTabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(ClassroomActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}