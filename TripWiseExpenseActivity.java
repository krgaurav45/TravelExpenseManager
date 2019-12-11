package com.example.gaurav.travelexpensemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TripWiseExpenseActivity extends AppCompatActivity {
    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_wise_expense);
        tl = (TableLayout) findViewById(R.id.tablelayout);
        SQLiteDatabase db = openOrCreateDatabase("GAURAV_SAURAV_DB",MODE_PRIVATE,null);
        db.setForeignKeyConstraintsEnabled(true);
        Cursor c = db.rawQuery("SELECT t.Trip_ID,t.Origin,t.Destination," +
                "t.Date_of_Start,t.Date_of_End,sum(e.Amount_Spend),t.Approved_Budget-sum(e.Amount_Spend) as Balanced_Budget" +
                " FROM Trip_Details as t," +
                "Expense_Details as e where t.Trip_ID=e.TRIP_ID group by t.Trip_ID",null);
        while ((c.moveToNext())){
            String p = c.getString(0);
            String q = c.getString(1);
            String r = c.getString(2);
            String s = c.getString(3);
            String t = c.getString(4);
            String u = c.getString(5);
            String v = c.getString(6);
            TableRow tr = new TableRow(this);
            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);
            TextView tv3 = new TextView(this);
            TextView tv4 = new TextView(this);
            TextView tv5 = new TextView(this);
            TextView tv6 = new TextView(this);
            TextView tv7 = new TextView(this);
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
            tv6.setText(u);
            tv6.setTextSize(20);
            tv7.setText(v);
            tv7.setTextSize(20);
            tr.addView(tv1);
            tr.addView(tv2);
            tr.addView(tv3);
            tr.addView(tv4);
            tr.addView(tv5);
            tr.addView(tv6);
            tr.addView(tv7);
            tl.addView(tr);
        }
    }
}
