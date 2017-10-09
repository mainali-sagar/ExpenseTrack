package com.mainali.expensetrack.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.mainali.expensetrack.Model.Category;
import com.mainali.expensetrack.R;


import java.util.ArrayList;

/**
 * Created by sagarmainali on 21/9/17.
 */

public class FragmentCategory extends Fragment {

    public ArrayList<String> itemList;
    ListView list = null;
//    DatabaseReference databaseReference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file


        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        list = (ListView) view.findViewById(R.id.categoryListView);
        itemList = new ArrayList<>();


        // Initializing firebase database instance
        // databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Category");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Category categoryList = dataSnapshot1.getValue(Category.class);

                    itemList.add(categoryList.categoryName);

                    Log.e("Category Name", categoryList.categoryName);

                    //Toast.makeText(TestActivity.this,"CategoryName"+categoryList.categoryName,Toast.LENGTH_LONG).show();

                    ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, itemList);
                    list.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Category");
    }

}
