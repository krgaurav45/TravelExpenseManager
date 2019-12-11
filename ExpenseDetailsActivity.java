package com.example.gaurav.travelexpensemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class ExpenseDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button b;
    SQLiteDatabase db;
    Spinner s;
    EditText et1,et2,et3,et4;
    String[] category = {"Select Category","Travelling","Meal","Lodging","Misc."};
    String select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);
        b = (Button) findViewById(R.id.buttonsubmit);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText4);
        et4 = (EditText) findViewById(R.id.editText5);
        s = (Spinner) findViewById(R.id.editText3);
        db = openOrCreateDatabase("GAURAV_SAURAV_DB",MODE_PRIVATE,null);
        db.setForeignKeyConstraintsEnabled(true);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,category);
        s.setAdapter(adapter);
        b.setOnClickListener(this);
        et3.setOnClickListener(this);
        s.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==b) {
            try {
                db.execSQL("insert into Expense_Details" +
                        " values('" + et2.getText().toString() + "','"+select+"'," +
                        "'" + Integer.parseInt(et4.getText().toString())+ "','" + et3.getText().toString() +"'" +
                        ",'" + et1.getText().toString() + "')");
                Intent i = new Intent();
                String s = "Expense Added";
                i.putExtra("key", s);
                setResult(100, i);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Error : Check Trip_Id or Expense_ID", Toast.LENGTH_LONG).show();
            }
        }
        else if (view==et3){
            MyDate d = new MyDate();
            Calendar c = Calendar.getInstance();
            DatePickerDialog dd = new DatePickerDialog(this,d,c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            dd.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i!=0)
            select = category[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class MyDate implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            et3.setText(i2+"/"+(i1+1)+"/"+i);
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        String s = "No Expense Added";
        i.putExtra("key",s);
        setResult(100,i);
        finish();
    }
}
