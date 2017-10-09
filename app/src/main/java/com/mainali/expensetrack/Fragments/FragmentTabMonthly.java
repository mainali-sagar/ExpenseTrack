package com.mainali.expensetrack.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mainali.expensetrack.R;

/**
 * Created by sagarmainali on 24/9/17.
 */

public class FragmentTabMonthly extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_monthly, container, false);
        return view;
    }
}
