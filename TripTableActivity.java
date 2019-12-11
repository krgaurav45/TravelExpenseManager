package com.example.gaurav.travelexpensemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Exchanger;

public class TripTableActivity extends AppCompatActivity{
    ListView lv;
    SQLiteDatabase db;
    ArrayList al,al2,al3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_tables);
        lv = (ListView) findViewById(R.id.listview);
        db = openOrCreateDatabase("GAURAV_SAURAV_DB",MODE_PRIVATE,null);
        db.setForeignKeyConstraintsEnabled(true);
        Cursor cur = db.rawQuery("select * from Trip_Details",null);
        al = new ArrayList();
        al2 = new ArrayList();
        al3 = new ArrayList();
        while(cur.moveToNext()){
            String x = cur.getString(0);
            String y = cur.getString(1);
            String z = cur.getString(2);
            String a = cur.getString(3);
            String b = cur.getString(4);
            String c = cur.getString(5);
            String data = "Trip_ID : "+x+"\nOrigin : "+y+"\nDestination : "+z+"\nStart date : "+a+"\nEnd Date : "+b+"\nApproved Budget : "+c;
            al.add(data);
            al2.add(x);
            al3.add(b);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int x = info.position;
        int id = item.getItemId();
        if(id==R.id.item1){
            final Dialog d = new Dialog(this);
            d.setContentView(R.layout.customdialog);
            d.setTitle("Enter Details");
            d.setCancelable(false);
            d.show();
            final EditText et1 = (EditText) d.findViewById(R.id.editText2);
            final EditText et2 = (EditText) d.findViewById(R.id.editText3);
            final EditText et3 = (EditText) d.findViewById(R.id.editText4);
            final EditText et4 = (EditText) d.findViewById(R.id.editText5);
            final EditText et5 = (EditText) d.findViewById(R.id.editText6);
            Button b1 = (Button) d.findViewById(R.id.buttonsubmit);
            Button b2 = (Button) d.findViewById(R.id.buttoncancel);
            et4.setOnClickListener(new View.OnClickListener() {
                class MyDate implements DatePickerDialog.OnDateSetListener {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et4.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                }

                @Override
                public void onClick(View view) {
                    MyDate d = new MyDate();
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog dd = new DatePickerDialog(TripTableActivity.this,d,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                    dd.show();
                }
            });
            et3.setOnClickListener(new View.OnClickListener() {
                class MyDate implements DatePickerDialog.OnDateSetListener {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et3.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                }

                @Override
                public void onClick(View view) {
                    MyDate d = new MyDate();
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog dd = new DatePickerDialog(TripTableActivity.this,d,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                    dd.show();
                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        db.execSQL("update Trip_Details set Origin = '" + et1.getText().toString() + "'," +
                                "Destination = '" + et2.getText().toString() + "'," +
                                "Date_of_Start = '" + et3.getText().toString() + "'," +
                                "Date_of_End = '" + et4.getText().toString() +
                                "',Approved_Budget = '" + et5.getText().toString() + "' " +
                                "where Trip_ID = '" + al2.get(x) + "'");
                        String s = "Trip_ID : "+al2.get(x)+"\nOrigin : "+et1.getText().toString()+
                                "\nDestination : "+ et2.getText().toString() +"\nStart date : "+ et3.getText().toString() +
                                "\nEnd Date : "+ et4.getText().toString() +"\nApproved Budget : "+ et5.getText().toString();
                        al.remove(al.get(x));
                        al.add(s);
                        ArrayAdapter adapter = new ArrayAdapter(TripTableActivity.this, android.R.layout.simple_list_item_1, al);
                        lv.setAdapter(adapter);
                        registerForContextMenu(lv);
                        d.cancel();
                    }
                    catch (Exception e){
                        Toast.makeText(TripTableActivity.this, "Error Updating", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d.cancel();
                }
            });
        }
        else{
            try {
                db.setForeignKeyConstraintsEnabled(false);
                db.execSQL("delete from Trip_Details where Trip_ID = '" + al2.get(x) + "'");
                db.execSQL("delete from Expense_Details where Trip_ID = '" + al2.get(x) + "'");
                al.remove(al.get(x));
                ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
                lv.setAdapter(adapter);
                registerForContextMenu(lv);
            }
            catch (Exception e){
                Toast.makeText(this,"Error : "+e, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onContextItemSelected(item);
    }
}
