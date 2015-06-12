package com.sharanjit.mytreatment.doctor;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sharanjit.dbtables.Doctor;
import com.sharanjit.mytreatment.DBAdapter;
import com.sharanjit.mytreatment.R;

public class DoctorForm extends ActionBarActivity {
    private String formFlag = "";
    private int did = -1;
    private int tid = -1;

    private Button delb;

    private EditText doctorName;
    private EditText doctorAdd1;
    private EditText doctorAdd2;
    private EditText doctorPhone;
    private EditText doctorNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_form);

        doctorName = (EditText) findViewById(R.id.dnamet);
        doctorAdd1 = (EditText) findViewById(R.id.dadd1t);
        doctorAdd2 = (EditText) findViewById(R.id.dadd2t);
        doctorPhone = (EditText) findViewById(R.id.dphonet);
        doctorNotes = (EditText) findViewById(R.id.dnotest);

        delb = (Button) findViewById(R.id.dfdelb);

        try {
            Bundle params = getIntent().getExtras();
            if ( params != null ) {
                formFlag = params.getString("DoctorFormFlag");
            }

            tid = params.getInt("DoctorFormTID");
            if (formFlag.equalsIgnoreCase("modify")) {
                did = params.getInt("DoctorFormDID");

                delb.setVisibility(View.VISIBLE);

                loaddata();
            } else {
                delb.setVisibility(View.INVISIBLE);
            }
        }catch(Exception tfe){
        }
        Log.d("TREATMENTTAG", "Param Value: " + formFlag);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_treatment_form, menu);
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

    public void dfokbuttonc(View v) {
        String dname = doctorName.getText().toString();
        String dadd1 = doctorAdd1.getText().toString();
        String dadd2 = doctorAdd2.getText().toString();
        String dphone = doctorPhone.getText().toString();
        String dnotes = doctorNotes.getText().toString();

        if ( dname != null && dname.trim().length() > 0 ) {
            DBAdapter treatmentDB = null;
            try {
                treatmentDB = new DBAdapter(this);
                treatmentDB.open();
                treatmentDB.OpenDoctor();

                if ( formFlag.equalsIgnoreCase("modify") ) {
                    treatmentDB.doctorTable.doctorUpdateRow(did, tid, dname, dnotes, dadd1, dadd2, dphone);
                } else {
                    treatmentDB.doctorTable.doctorInsert(tid, dname, dnotes, dadd1, dadd2, dphone);
                }

                treatmentDB.close();

                finish();

            }catch(Exception ee1) {
                try {
                    treatmentDB.close();
                }catch(Exception ee2){
                }
                Toast t1 = Toast.makeText(DoctorForm.this, "Error while submitting Doctor", Toast.LENGTH_LONG);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
                t1 = null;
            }

        } else {
            Toast t1 = Toast.makeText(DoctorForm.this, "Doctor Name cannot be blank", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }
    public void doctorformcancelbuttonc(View v) {
        Log.d("TREATMENTTAG","Doctor Form Cancel Button Click.");
        finish();
    }

    public void loaddata() {
        DBAdapter treatmentDB = null;
        Log.d("TREATMENTTAG", "loading data for: " + tid);
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();

            treatmentDB.OpenDoctor();

            Cursor tc = treatmentDB.doctorTable.doctorGetRow(did);

            tc.moveToFirst();

            DoctorData td1 = new DoctorData(tc.getInt(tc.getColumnIndex(Doctor.KEY_PARENT)), tc.getInt(tc.getColumnIndex(Doctor.COL_TID)), tc.getString(tc.getColumnIndex(Doctor.COL_DNAME)), tc.getString(tc.getColumnIndex(Doctor.COL_NOTES)), tc.getString(tc.getColumnIndex(Doctor.COL_ADD1)), tc.getString(tc.getColumnIndex(Doctor.COL_ADD2)), tc.getString(tc.getColumnIndex(Doctor.COL_PHONE)));

            this.doctorName.setText(td1.getDname());
            this.doctorNotes.setText(td1.getNotes());
            this.doctorAdd1.setText(td1.getAdd1());
            this.doctorAdd2.setText(td1.getAdd2());
            this.doctorPhone.setText(td1.getPhone());

            treatmentDB.close();

        }catch(Exception ee1) {
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(DoctorForm.this, "Error while loading Doctor data", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }

    public void DeleteDoctorButtonClick(View v) {
        DBAdapter treatmentDB = null;
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();
            treatmentDB.OpenDoctor();

            treatmentDB.doctorTable.doctorDeleteRow(did);

            treatmentDB.close();

            finish();
        }catch(Exception dele1){
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(DoctorForm.this, "Error while deleting Doctor", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }
    }
}
