package com.mainali.expensetrack;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.mainali.expensetrack.Adapter.SectionPageAdapter;
import com.mainali.expensetrack.Fragments.FragmentTabDaily;
import com.mainali.expensetrack.Fragments.FragmentTabMonthly;
import com.mainali.expensetrack.Fragments.FragmentTabWeekly;
import com.mainali.expensetrack.R;


public class TabbedActivity extends AppCompatActivity {

    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        mSectionPageAdapter= new SectionPageAdapter(getSupportFragmentManager());

        //Setup ViewPager with the Section Adapter

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    private void setupViewPager(ViewPager viewPager){
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentTabDaily(),"Daily");
        adapter.addFragment(new FragmentTabWeekly(),"Weekly");
        adapter.addFragment(new FragmentTabMonthly(),"Monthly");
        viewPager.setAdapter(adapter);

    }



    }

