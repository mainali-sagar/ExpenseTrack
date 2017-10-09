package com.mainali.expensetrack.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mainali.expensetrack.Adapter.SectionPageAdapter;
import com.mainali.expensetrack.R;

import java.util.ArrayList;

import static com.mainali.expensetrack.R.string.date;

/**
 * Created by sagarmainali on 23/9/17.
 */

public class FragmentExpense extends Fragment {

    TextView dollarTv;
    TextView currentDate;
    DatabaseReference databaseReference;
    ArrayList<String> itemList;
    ListView list;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        dollarTv = (TextView) view.findViewById(R.id.textViewAmount);
        currentDate = (TextView) view.findViewById(R.id.textViewDate);
        databaseReference = FirebaseDatabase.getInstance().getReference("Expenses");
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);

        currentDate.setText(date);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            int x = 120;
            int y = 2 * x;
            int z = 2 * y;
            String value = String.valueOf(x);
            String vale2 = String.valueOf(y);

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    dollarTv.setText("$" + value);

                }

                if (position == 1) {
                    dollarTv.setText("$" + vale2);
                }

                if (position == 2) {
                    dollarTv.setText("$" + String.valueOf(z));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout mTabLayout = mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Expenses");


    }


    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentTabDaily(), "Daily");
        adapter.addFragment(new FragmentTabWeekly(), "Weekly");
        adapter.addFragment(new FragmentTabMonthly(), "Monthly");
        viewPager.setAdapter(adapter);


    }

}

