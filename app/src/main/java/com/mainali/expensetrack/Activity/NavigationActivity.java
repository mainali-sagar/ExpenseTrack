package com.mainali.expensetrack.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mainali.expensetrack.Fragments.FragmentCategory;
import com.mainali.expensetrack.Fragments.FragmentExpense;
import com.mainali.expensetrack.Fragments.FragmentReminder;
import com.mainali.expensetrack.Fragments.FragmentStats;
import com.mainali.expensetrack.Model.Reminder;
import com.mainali.expensetrack.R;
import com.mainali.expensetrack.Model.Category;
import com.mainali.expensetrack.Model.Expenses;

import java.io.IOException;
import java.util.Date;


@RequiresApi(api = Build.VERSION_CODES.N)
public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText etDate;
    EditText etAmount;
    EditText etDesc;
    EditText categoryName;
    Spinner mSpinner;


    EditText rTitle;
    EditText rDate;
    EditText rTime;
    EditText rDesc;

    DatabaseReference databaseReference;

    String today = new SimpleDateFormat("dd/MM/YYYY").format(new Date());

    //DatabaseReference databaseExpense;

    Integer fragmentId;
    FloatingActionButton fab;
    String selectedDate = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Expense Track");
        setSupportActionBar(toolbar);

        // FLoating action button on click action

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override

            public void onClick(View view) {

                if (fragmentId == R.id.nav_expense) {

                    dialogExpense();
                } else if (fragmentId == R.id.nav_category) {

                    dialogCategory();
                } else if (fragmentId == R.id.nav_reminder) {

                    // ToDo: Add reminder dialog box
                    dialogReminder();
                    //Toast.makeText(NavigationActivity.this,"Need to add reminder",Toast.LENGTH_LONG).show();
                } else {

                    fab.setVisibility(View.VISIBLE);
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // setting default screen to load at start

        displaySelectedScreen(R.id.nav_expense);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle menu_navigation view item clicks here.
        int id = item.getItemId();

        Log.w("Item id check", (String.valueOf(id)));

        displaySelectedScreen(id);
        return true;
    }


    //Function for displaying selected fragment

    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;
        fab.setVisibility(View.VISIBLE);
        switch (itemId) {
            case R.id.nav_expense:
                fragment = new FragmentExpense();
                fragmentId = itemId;
                break;

            case R.id.nav_category:
                fragment = new FragmentCategory();
                fragmentId = itemId;
                break;

            case R.id.nav_stats:
                fragment = new FragmentStats();
                fragmentId = itemId;
                fab.setVisibility(View.INVISIBLE);
                break;

            case R.id.nav_reminder:
                fragment = new FragmentReminder();
                fragmentId = itemId;
                break;
        }

        //replacing the fragment

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_nav, fragment);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


////////////////////////////////// Custom Dialog Boxes //////////////////////////////////

    //Add Expense dialog method
    public void dialogExpense() {

        // Custom Input Dialog Box
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NavigationActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_expense, null);

        mBuilder.setTitle("Add New Expense");

        //final Spinner mSpinner = mView.findViewById(spinnerCategory);
        etDate = (EditText) mView.findViewById(R.id.et_dialog_date);
        etAmount = (EditText) mView.findViewById(R.id.et_dialog_amount);
        etDesc = (EditText) mView.findViewById(R.id.et_dialog_desc);
        mSpinner = (Spinner) mView.findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NavigationActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.CategoryList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        etDate.setText(today);

        etDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();

                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(NavigationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedDate = Integer.toString(selectedmonth) + "/" + Integer.toString(selectedday) + "/" + Integer.toString(selectedyear);
                        etDate.setText(selectedDate);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });


        mBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose a category…")) {

                    addExpense();

                    Toast.makeText(NavigationActivity.this, "Expense Added", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();

                }
            }
        });

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


    //Add category dialog method
    private void dialogCategory() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NavigationActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_category, null);

        //Setting title for dialogbox
        mBuilder.setTitle("Add New Category");

        categoryName = (EditText) mView.findViewById(R.id.et_dialog_categoryname);


        // Positive button action for dialog box
        mBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                addCategory();
                Toast.makeText(NavigationActivity.this, "Category Added", Toast.LENGTH_LONG).show();
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


    //Add Reminder dialog method
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dialogReminder() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NavigationActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_reminder, null);
        rTitle = (EditText) mView.findViewById(R.id.reminderTitle);
        rDate = (EditText) mView.findViewById(R.id.reminderDate);
        rDesc = (EditText) mView.findViewById(R.id.reminderDesc);
        rTime = (EditText) mView.findViewById(R.id.reminderTime);

        //Setting title for dialogbox
        mBuilder.setTitle("Add New Reminder");
        rDate.setText(today);

        rDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate = Calendar.getInstance();

                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                Log.e("Tag", String.valueOf(mYear + "/" + mMonth + "/" + mDay));

                DatePickerDialog mDatePicker = new DatePickerDialog(NavigationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedDate = Integer.toString(selectedmonth) + "/" + Integer.toString(selectedday) + "/" + Integer.toString(selectedyear);
                        rDate.setText(selectedDate);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });


        rTime.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NavigationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        rTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                //Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        // Positive button action for dialog box
        mBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //addCategory();
                Toast.makeText(NavigationActivity.this, "Reminder Added", Toast.LENGTH_LONG).show();
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


    ////////////////////// Saving Data into Firebase Database //////////////////////////////////

    // Method for saving Expense data
    public void addExpense() {


        String date = etDate.getText().toString().trim();
        String expenseAmount = etAmount.getText().toString().trim();
        String expenseDesc = etDesc.getText().toString().trim();
        String category = mSpinner.getSelectedItem().toString().trim();


        databaseReference = FirebaseDatabase.getInstance().getReference("Expenses");
        if (!TextUtils.isEmpty(expenseAmount)) {

            String id = databaseReference.push().getKey();

            Expenses expense = new Expenses(id, date, expenseAmount, category, expenseDesc);

            databaseReference.child(id).setValue(expense);

        } else {

            Toast.makeText(this, "Please enter all required field", Toast.LENGTH_LONG).show();


        }

    }


    // Method for saving Category data
    public void addCategory() {

        String catName = categoryName.getText().toString().trim();

        databaseReference = FirebaseDatabase.getInstance().getReference("Category");

        if (!TextUtils.isEmpty(catName)) {

            String id = databaseReference.push().getKey();

            Category category = new Category(id, catName);

            databaseReference.child(id).setValue(category);

            Log.e("Tag", "id  " + id);
            Log.e("Tag", "category name  " + catName);

        } else {

            Toast.makeText(this, "Please enter all required field", Toast.LENGTH_LONG).show();


        }
    }

    // Method for saving Reminder data
    public void addReminder() {

        String title = rTitle.getText().toString().trim();
        String date = rDate.getText().toString().trim();
        String time = rTime.getText().toString().trim();
        String desc = rDesc.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference("Reminder");

        databaseReference = FirebaseDatabase.getInstance().getReference("Reminder");
        if (!TextUtils.isEmpty(date)) {

            String id = databaseReference.push().getKey();

            Reminder reminder = new Reminder(id, title, date, time, desc);

            databaseReference.child(id).setValue(reminder);

        } else {

            Toast.makeText(this, "Please enter all required field", Toast.LENGTH_LONG).show();


        }
    }


}
