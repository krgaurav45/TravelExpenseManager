package com.example.gaurav.travelexpensemanager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseTableActivity extends AppCompatActivity {
    ListView lv;
    ArrayList al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_table);
        lv = (ListView) findViewById(R.id.listview);
        SQLiteDatabase db = openOrCreateDatabase("GAURAV_SAURAV_DB",MODE_PRIVATE,null);
        db.setForeignKeyConstraintsEnabled(true);
        Cursor cur = db.rawQuery("select * from Expense_Details",null);
        al = new ArrayList();
        while(cur.moveToNext()){
            String x = cur.getString(0);
            String y = cur.getString(1);
            String z = cur.getString(2);
            String a = cur.getString(3);
            String b = cur.getString(4);
            String data = "Trip_ID : "+b+"\nExpense_ID : "+x+"\nCategory : "+y+"\nDate : "+a+"\nAmount Spent : "+z;
            al.add(data);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(adapter);
    }
}
