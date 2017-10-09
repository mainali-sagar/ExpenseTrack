package com.mainali.expensetrack.Fragments;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mainali.expensetrack.Activity.NavigationActivity;
import com.mainali.expensetrack.Adapter.ExpenseListAdapter;
import com.mainali.expensetrack.Model.Expenses;
import com.mainali.expensetrack.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by sagarmainali on 24/9/17.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class FragmentTabDaily extends Fragment {

    ArrayList<String> itemList;
    ListView list;
    String today = new SimpleDateFormat("dd/MM/YYYY").format(new Date());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_daily, container, false);
        list = (ListView) view.findViewById(R.id.listdaily);
        itemList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Expenses");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Expenses expenseList = dataSnapshot1.getValue(Expenses.class);
                    String eCategory = expenseList.getCategory();
                    String eAmount = expenseList.getExpenseAmount();
                    String eDesc = expenseList.getExpenseDesc();

                    Expenses expenses[] = new Expenses[3];
                    Expenses expenses1 = new Expenses(eCategory, eDesc, eAmount);

                    expenses[0] = expenses1;

                       /* itemList.add(eCategory);
                        itemList.add(eAmount);
                        itemList.add(eDesc);*/

                    ExpenseListAdapter adapter = new ExpenseListAdapter(getActivity(), expenses);
                    // ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, itemList);
                    list.setAdapter(adapter);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;


    }
}
