package com.example.gaurav.travelexpensemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DayWiseExpenseActivity extends AppCompatActivity {
    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_wise_expense);
        tl = (TableLayout) findViewById(R.id.tablelayout);
        SQLiteDatabase db = openOrCreateDatabase("GAURAV_SAURAV_DB", MODE_PRIVATE, null);
        db.setForeignKeyConstraintsEnabled(true);
        Cursor c = db.rawQuery("SELECT t.Trip_ID,t.Origin,t.Destination," +
                "e.Date,e.Amount_Spend FROM Trip_Details as t, Expense_Details as e where" +
                " t.Trip_ID=e.TRIP_ID group by e.Date order by t.Trip_ID",null);
        while ((c.moveToNext())) {
            String p = c.getString(0);
            String q = c.getString(1);
            String r = c.getString(2);
            String s = c.getString(3);
            String t = c.getString(4);
            TableRow tr = new TableRow(this);
            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);
            TextView tv3 = new TextView(this);
            TextView tv4 = new TextView(this);
            TextView tv5 = new TextView(this);
            tv1.setText(p);
            tv1.setTextSize(20);
            tv2.setText(q);
            tv2.setTextSize(20);
            tv3.setText(r);
            tv3.setTextSize(20);
            tv4.setText(s);
            tv4.setTextSize(20);
            tv5.setText(t);
            tv5.setTextSize(20);
            tr.addView(tv1);
            tr.addView(tv2);
            tr.addView(tv3);
            tr.addView(tv4);
            tr.addView(tv5);
            tl.addView(tr);
        }
    }
}
