package com.sharanjit.mytreatment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.sharanjit.dbtables.Treatment;
import com.sharanjit.mytreatment.doctor.DoctorList;
import com.sharanjit.mytreatment.medicine.MedicineList;
import com.sharanjit.mytreatment.notes.NoteList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TreatmentForm extends ActionBarActivity {
    private String formFlag = "";
    private int tid = -1;

    private EditText startDate;
    private DatePickerDialog startDateDatePicker;
    private EditText endDate;
    private DatePickerDialog endDateDatePicker;

    private Button delb;
    private Button medb;
    private Button ptb;
    private Button docb;
    private Button ntb;

    private SimpleDateFormat dateFormat;

    private EditText treatmentName;
    private EditText treatmentDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_form);

        startDate = (EditText) findViewById(R.id.sdtf);
        endDate = (EditText) findViewById(R.id.edtf);
        treatmentName = (EditText) findViewById(R.id.namet);
        treatmentDesc = (EditText) findViewById(R.id.desctf);

        delb = (Button) findViewById(R.id.treatmentdelb);

        medb = (Button) findViewById(R.id.tfmedicineb);
        ptb = (Button) findViewById(R.id.tfptb);
        docb = (Button) findViewById(R.id.tfdoctorsb);
        ntb = (Button) findViewById(R.id.tfnotesb);

        try {
            Bundle params = getIntent().getExtras();
            if ( params != null ) {
                formFlag = params.getString("TreatmentFormFlag");
            }

            if (formFlag.equalsIgnoreCase("modify")) {
                tid = params.getInt("TreatmentFormID");

                delb.setVisibility(View.VISIBLE);

                medb.setVisibility(View.VISIBLE);
                ptb.setVisibility(View.VISIBLE);
                docb.setVisibility(View.VISIBLE);
                ntb.setVisibility(View.VISIBLE);

                loaddata();
            } else {
                delb.setVisibility(View.INVISIBLE);

                medb.setVisibility(View.INVISIBLE);
                ptb.setVisibility(View.INVISIBLE);
                docb.setVisibility(View.INVISIBLE);
                ntb.setVisibility(View.INVISIBLE);
            }
        }catch(Exception tfe){
        }
        Log.d("TREATMENTTAG", "Param Value: " + formFlag);

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

//Set Initial value for Start Date and End Date.
        if ( startDate.getText() != null && startDate.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = dateFormat.parse(startDate.getText().toString());
                startCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        if ( endDate.getText() != null && endDate.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = dateFormat.parse(endDate.getText().toString());
                endCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }

        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    startDateDatePicker.show();
                }
            }
        });
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                startDateDatePicker.show();
            }
        });
        startDateDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(dateFormat.format(newDate.getTime()));
            }

        },startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

        endDate.setInputType(InputType.TYPE_NULL);
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "EndDate Focus: " + endDate.getText().toString());
                    endDateDatePicker.show();
                }
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "EndDate Click: " + endDate.getText().toString());
                endDateDatePicker.show();
            }
        });
        endDateDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormat.format(newDate.getTime()));
            }

        },endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));

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

    public void okbuttonc(View v) {
        String tname = treatmentName.getText().toString();

        if ( tname != null && tname.trim().length() > 0 ) {
            DBAdapter treatmentDB = null;
            try {
                treatmentDB = new DBAdapter(this);
                treatmentDB.open();
                treatmentDB.OpenTreatment();

                if ( formFlag.equalsIgnoreCase("modify") ) {
                    treatmentDB.treatmentTable.treatmentUpdateRow(tid, tname, startDate.getText().toString(), endDate.getText().toString(), treatmentDesc.getText().toString());
                } else {
                    treatmentDB.treatmentTable.treatmentInsert(tname, startDate.getText().toString(), endDate.getText().toString(), treatmentDesc.getText().toString());
                }

                treatmentDB.close();

                finish();

            }catch(Exception ee1) {
                try {
                    treatmentDB.close();
                }catch(Exception ee2){
                }
                Toast t1 = Toast.makeText(TreatmentForm.this, "Error while submitting Treatment", Toast.LENGTH_LONG);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
                t1 = null;
            }

        } else {
            Toast t1 = Toast.makeText(TreatmentForm.this, "Treatment Name cannot be blank", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }
    public void treatformcancelbuttonc(View v) {
        finish();
    }

    public void loaddata() {
        DBAdapter treatmentDB = null;
        Log.d("TREATMENTTAG", "loading data for: " + tid);
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();

            treatmentDB.OpenTreatment();

            Cursor tc = treatmentDB.treatmentTable.treatmentGetRow(tid);

            tc.moveToFirst();

            TreatmentData td1 = new TreatmentData(tc.getInt(tc.getColumnIndex(Treatment.KEY_PARENT)), tc.getString(tc.getColumnIndex(Treatment.COL_NAME)), tc.getString(tc.getColumnIndex(Treatment.COL_STARTDATE)), tc.getString(tc.getColumnIndex(Treatment.COL_ENDDATE)), tc.getString(tc.getColumnIndex(Treatment.COL_DESC)));

            this.treatmentName.setText(td1.getName());
            this.treatmentDesc.setText(td1.getDesc());
            this.startDate.setText(td1.getSdate());
            this.endDate.setText(td1.getEdate());

            treatmentDB.close();

        }catch(Exception ee1) {
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(TreatmentForm.this, "Error while loading Treatment data", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }

    public void DeleteTreatmentButtonClick(View v) {
        DBAdapter treatmentDB = null;
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();
            treatmentDB.OpenTreatment();
            treatmentDB.OpenMedicine();
            treatmentDB.OpenNotes();
            treatmentDB.OpenDoctor();

            treatmentDB.treatmentTable.treatmentDeleteRow(tid);

            //Delete all Doctors, Notes and Medicines associated to this Treatment.
            treatmentDB.medicineTable.medicineDeleteRowForTreatment(tid);
            treatmentDB.doctorTable.doctorDeleteRowForTreatment(tid);
            treatmentDB.notesTable.noteDeleteRowForTreatment(tid);

            treatmentDB.close();

            finish();
        }catch(Exception dele1){
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(TreatmentForm.this, "Error while deleting Treatment", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }
    }

    public void tfdocbclick(View v) {
        try {
            Intent i = new Intent(this, DoctorList.class);
            i.putExtra("TreatmentID", tid);
            //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }catch(Exception ee1){
        }
    }

    public void tfnotebclick(View v) {
        try {
            Intent i = new Intent(this, NoteList.class);
            i.putExtra("TreatmentID", tid);
            //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }catch(Exception ee1){
        }
    }

    public void tfmedbclick(View v) {
        try {
            Intent i = new Intent(this, MedicineList.class);
            i.putExtra("TreatmentID", tid);
            //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }catch(Exception ee1){
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TREATMENTTAG", "On Pause of Treatment Form");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TREATMENTTAG", "On Resume of Treatment Form");
    }
}
