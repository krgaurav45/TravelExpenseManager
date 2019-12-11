package com.example.gaurav.travelexpensemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTripDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    Button b;
    SQLiteDatabase db;
    EditText et1,et2,et3,et4,et5,et6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_details);
        b = (Button) findViewById(R.id.buttonsubmit);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        et5 = (EditText) findViewById(R.id.editText5);
        et6 = (EditText) findViewById(R.id.editText6);
        db = openOrCreateDatabase("GAURAV_SAURAV_DB",MODE_PRIVATE,null);
        db.setForeignKeyConstraintsEnabled(true);
        b.setOnClickListener(this);
        et4.setOnClickListener(this);
        et5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==b) {
            try {
                db.execSQL("insert into Trip_Details values('" + et1.getText().toString() + "','" + et2.getText().toString() + "','" + et3.getText().toString() + "','" + et4.getText().toString() + "','" + et5.getText().toString() + "','" + et6.getText().toString() + "')");
                Intent i = new Intent();
                String s = "Trip Added";
                i.putExtra("key", s);
                setResult(100, i);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Error : Trip_ID must be unique" , Toast.LENGTH_LONG).show();
            }
        }
        else if (view==et4){
            MyDate d = new MyDate();
            Calendar c = Calendar.getInstance();
            DatePickerDialog dd = new DatePickerDialog(this,d,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            dd.show();
        }
        else{
            MyDate1 d = new MyDate1();
            Calendar c = Calendar.getInstance();
            DatePickerDialog dd = new DatePickerDialog(this,d,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            dd.show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        String s = "No Trip Added";
        i.putExtra("key",s);
        setResult(100,i);
        finish();
    }

    private class MyDate implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            et4.setText(i2+"/"+(i1+1)+"/"+i);
        }
    }

    private class MyDate1 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            et5.setText(i2+"/"+(i1+1)+"/"+i);
        }
    }
}
