package com.example.gaurav.travelexpensemanager;

import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    Button b1,b2,b3,b4,b5;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        db = openOrCreateDatabase("GAURAV_SAURAV_DB",MODE_PRIVATE,null);
        db.setForeignKeyConstraintsEnabled(true);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        try {
            db.execSQL("create table if not exists Trip_Details(Trip_ID varchar(20) primary key unique," +
                    "Destination varchar(25),Origin varchar(25),Date_of_Start date," +
                    "Date_of_End date,Approved_Budget number(15))");
        } catch (Exception e) {
            Toast.makeText(this, "Error : " + e, Toast.LENGTH_SHORT).show();
        }
        try {
            db.execSQL("create table if not exists " +
                    "Expense_Details(Expense_ID varchar(20) primary key,Category varchar(20)," +
                    "Amount_Spend number(15),Date date,TRIP_ID varchar(20)," +
                    "foreign key (TRIP_ID) references Trip_Details(Trip_ID))");
        } catch (Exception e) {
            Toast.makeText(this, "Error : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(1,0,1,"Exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finishAffinity();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == b1) {
            Intent i = new Intent(this, AddTripDetailsActivity.class);
            startActivityForResult(i, 10);
        }
        else if (view == b2) {
            Intent i = new Intent(this, TripTableActivity.class);
            startActivity(i);
        }
        else if (view == b3) {
            Intent i = new Intent(this, ExpenseDetailsActivity.class);
            startActivityForResult(i, 10);
        }
        else if (view == b4) {
            Intent i = new Intent(this, BalancedBudgetActivity.class);
            startActivity(i);
        }
        else if (view == b5) {
            PopupMenu pop = new PopupMenu(this,view);
            MenuInflater inflater = pop.getMenuInflater();
            inflater.inflate(R.menu.popup,pop.getMenu());
            pop.show();
            pop.setOnMenuItemClickListener(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b = data.getExtras();
        Toast.makeText(this,b.getString("key"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.item1){
            Intent i = new Intent(this,TripWiseExpenseActivity.class);
            startActivity(i);
        }
        else if(id==R.id.item2){
            Intent i = new Intent(this,CategoryWiseExpensesActivity.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(this,DayWiseExpenseActivity.class);
            startActivity(i);
        }
        return false;
    }
}
