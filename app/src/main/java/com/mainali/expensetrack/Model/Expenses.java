package com.mainali.expensetrack.Model;

/**
 * Created by sagarmainali on 24/9/17.
 */

public class Expenses {

    public String id;
    public String date;
    public String expenseAmount;
    public String category;
    public String expenseDesc;
    //private int imageResourceID;

    public Expenses(String eCategory, String eDesc, String eAmount){

    }


    public Expenses(String id, String date, String expenseAmount, String category, String expenseDesc) {
        this.id = id;
        this.date = date;
        this.expenseAmount = expenseAmount;
        this.category = category;
        this.expenseDesc = expenseDesc;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public String getCategory() {
        return category;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }
}
