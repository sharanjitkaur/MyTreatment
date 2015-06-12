package com.sharanjit.mytreatment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sharanjit.mytreatment.doctor.AllDoctorList;
import com.sharanjit.mytreatment.notes.AllNoteList;


public class MainActivity extends ActionBarActivity {
    DBAdapter treatmentDB;

    Button treatmentbutton;
    Button Doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();

        treatmentbutton = (Button)findViewById(R.id.button1);

        treatmentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), TreatmentList.class);
                    startActivity(i);
                }catch(Exception ee1){
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    private void openDB() {
        treatmentDB = new DBAdapter(this);
        treatmentDB.open();
    }
    private void closeDB() {
        treatmentDB.close();
    }

    public void chartb(View v) {
        try {
            Intent i = new Intent(getApplicationContext(), Chart.class);
            startActivity(i);
        }catch(Exception ee1){
        }
    }

    public void alldoclist(View v) {
        try {
            Intent i = new Intent(getApplicationContext(), AllDoctorList.class);
            startActivity(i);
        }catch(Exception ee1){
        }
    }

    public void allnotelist(View v) {
        try {
            Intent i = new Intent(getApplicationContext(), AllNoteList.class);
            startActivity(i);
        }catch(Exception ee1){
        }
    }
}
