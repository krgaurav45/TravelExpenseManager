package com.example.gaurav.travelexpensemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BalancedBudgetActivity extends AppCompatActivity {
    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balanced_budget);
        tl = (TableLayout) findViewById(R.id.tablelayout);
        SQLiteDatabase db = openOrCreateDatabase("GAURAV_SAURAV_DB",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT t.Trip_ID,t.Approved_Budget-sum(e.Amount_Spend) as Balanced_Budget" +
                " FROM Trip_Details as t,Expense_Details as e WHERE t.Trip_ID = E.TRIP_ID GROUP BY t.Trip_ID",null);
        while ((c.moveToNext())){
            String x = c.getString(0);
            String y = c.getString(1);
            TableRow tr = new TableRow(this);
            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);
            tv1.setText(x);
            tv2.setText(y);
            tv1.setTextSize(20);
            tv2.setTextSize(20);
            tr.addView(tv1);
            tr.addView(tv2);
            tl.addView(tr);
        }
    }
}
