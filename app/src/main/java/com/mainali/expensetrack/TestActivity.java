package com.mainali.expensetrack;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mainali.expensetrack.Model.Category;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {

    FloatingActionButton mfab;
    EditText categoryName;
    ListView list;
    DatabaseReference databaseReference;
    ArrayList<String > itemList;
    ArrayAdapter<String> adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        list = (ListView) findViewById(R.id.categoryListView);

        itemList= new ArrayList<>();

        // Initializing firebase database instance
        databaseReference = FirebaseDatabase.getInstance().getReference("Category");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    Category categoryList= dataSnapshot1.getValue(Category.class);

                    itemList.add(categoryList.categoryName);

                    Log.e("Category Name",categoryList.categoryName);

                    //Toast.makeText(TestActivity.this,"CategoryName"+categoryList.categoryName,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mfab = (FloatingActionButton) findViewById(R.id.fab);


       // On click action
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TestActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_category,null);

               //Setting title for dialogbox
                mBuilder.setTitle("Add New Category");

                categoryName= (EditText) mView.findViewById(R.id.et_dialog_categoryname);



               // Positive button action for dialog box
                mBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                            addCategory();
                        Toast.makeText(TestActivity.this,"Category Added",Toast.LENGTH_LONG).show();
                            dialogInterface.dismiss();
                        }

                });



                //  negative button action for dialog box

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }


   // Method for adding category
    public void addCategory() {

        String catName = categoryName.getText().toString().trim();



        if (!TextUtils.isEmpty(catName)){

            String id= databaseReference.push().getKey();

            Category category = new Category(id,catName);

            databaseReference.child(id).setValue(category);

            Log.e("Tag","id  "+id);
            Log.e("Tag","category name  "+catName);

        }

        else {

            Toast.makeText(this,"Please enter all required field",Toast.LENGTH_LONG).show();


        }
    }
}

