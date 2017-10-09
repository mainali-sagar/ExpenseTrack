package com.mainali.expensetrack.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mainali.expensetrack.Model.Expenses;
import com.mainali.expensetrack.R;

import java.util.ArrayList;

/**
 * Created by sagarmainali on 9/10/17.
 */

public class ExpenseListAdapter extends BaseAdapter {

    private Context ctx;
    private Expenses[] expenses;
    private LayoutInflater mInflater;

    public ExpenseListAdapter(Context ctx, Expenses[] expenses) {
        this.ctx = ctx;
        this.expenses = expenses;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return expenses.length;
    }

    @Override
    public Object getItem(int i) {
        return expenses[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (view == null) {
            vi = mInflater.inflate(R.layout.expense_list_view, null);
        }
        TextView category = (TextView) vi.findViewById(R.id.tvCategoryname);
        TextView desc = (TextView) vi.findViewById(R.id.tvDesc);
        TextView amount = (TextView) vi.findViewById(R.id.tvAmount);

        category.setText(""+expenses[i].getCategory());
        desc.setText("" + expenses[i].getExpenseDesc());
        amount.setText(""+expenses[i].getExpenseAmount());

        return vi;
    }
}
