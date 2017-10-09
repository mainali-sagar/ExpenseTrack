package com.mainali.expensetrack.Model;

/**
 * Created by sagarmainali on 21/9/17.
 */

public class Category {

    String id;
    public String categoryName;



public Category(){



        }

    public Category(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
