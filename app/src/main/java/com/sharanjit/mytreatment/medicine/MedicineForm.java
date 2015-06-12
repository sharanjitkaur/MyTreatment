package com.sharanjit.mytreatment.medicine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sharanjit.dbtables.Medicine;
import com.sharanjit.mytreatment.DBAdapter;
import com.sharanjit.mytreatment.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MedicineForm extends ActionBarActivity {
    private String formFlag = "";
    private int tid = -1;
    private int mid = -1;

    private EditText startDate;
    private DatePickerDialog startDateDatePicker;
    private EditText endDate;
    private DatePickerDialog endDateDatePicker;

    private Button delb;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private EditText mName;
    private EditText notes;
    private Switch active;
    private Switch reminder;

    private String frequency = "D";

    private EditText hmt;
    private EditText starttime;
    private TimePickerDialog starttimeTimePicker;


    private CheckBox suncb;
    private EditText suntime;
    private TimePickerDialog sunTimePicker;
    private CheckBox moncb;
    private EditText montime;
    private TimePickerDialog monTimePicker;
    private CheckBox tuecb;
    private EditText tuetime;
    private TimePickerDialog tueTimePicker;
    private CheckBox wedcb;
    private EditText wedtime;
    private TimePickerDialog wedTimePicker;
    private CheckBox thucb;
    private EditText thutime;
    private TimePickerDialog thuTimePicker;
    private CheckBox fricb;
    private EditText fritime;
    private TimePickerDialog friTimePicker;
    private CheckBox satcb;
    private EditText sattime;
    private TimePickerDialog satTimePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_form);

        mName = (EditText) findViewById(R.id.mfmnamet);
        notes = (EditText) findViewById(R.id.mfnotest);
        startDate = (EditText) findViewById(R.id.mfsdatet);
        endDate = (EditText) findViewById(R.id.mfedatet);
        hmt = (EditText) findViewById(R.id.mfhmtt);
        starttime = (EditText) findViewById(R.id.mfstarttimet);

        active = (Switch)findViewById(R.id.mfactivet);
        reminder = (Switch)findViewById(R.id.mfremindert);

        delb = (Button) findViewById(R.id.mfdelb);


        suncb = (CheckBox)findViewById(R.id.mfsuncb);
        suntime = (EditText)findViewById(R.id.mfsundayt);
        moncb = (CheckBox)findViewById(R.id.mfmoncb);
        montime = (EditText)findViewById(R.id.mfmondayt);
        tuecb = (CheckBox)findViewById(R.id.mftuecb);
        tuetime = (EditText)findViewById(R.id.mftuesdayt);
        wedcb = (CheckBox)findViewById(R.id.mfwedcb);
        wedtime = (EditText)findViewById(R.id.mfwednesdayt);
        thucb = (CheckBox)findViewById(R.id.mfthucb);
        thutime = (EditText)findViewById(R.id.mfthursdayt);
        fricb = (CheckBox)findViewById(R.id.mffricb);
        fritime = (EditText)findViewById(R.id.mffridayt);
        satcb = (CheckBox)findViewById(R.id.mfsatcb);
        sattime = (EditText)findViewById(R.id.mfsaturdayt);


        try {
            Bundle params = getIntent().getExtras();
            if ( params != null ) {
                formFlag = params.getString("MedicineFormFlag");
            }

            tid = params.getInt("MedicineFormTID");
            if (formFlag.equalsIgnoreCase("modify")) {
                mid = params.getInt("MedicineFormMID");

                delb.setVisibility(View.VISIBLE);

                loaddata();

                RadioButton rbd = (RadioButton)findViewById(R.id.mfdailyrb);
                RadioButton rbw = (RadioButton)findViewById(R.id.mfweeklyrb);
                View dView = (View)this.findViewById(R.id.mfdailylayout);
                View wView = (View)this.findViewById(R.id.mfweeklylayout);

                if ( this.frequency.equalsIgnoreCase("D") ) {
                    rbd.setChecked(true);
                    rbw.setChecked(false);

                    dView.setVisibility(View.VISIBLE);
                    wView.setVisibility(View.INVISIBLE);
                } else {
                    rbd.setChecked(false);
                    rbw.setChecked(true);

                    dView.setVisibility(View.INVISIBLE);
                    wView.setVisibility(View.VISIBLE);
                }

            } else {
                delb.setVisibility(View.INVISIBLE);

                //Show Daily Layout and hide Weekly Layout
                View wView = (View)this.findViewById(R.id.mfweeklylayout);
                wView.setVisibility(View.INVISIBLE);
            }
        }catch(Exception tfe){
        }
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        Calendar starttimeCalendar = Calendar.getInstance();

        Calendar sunCalendar = Calendar.getInstance();
        Calendar monCalendar = Calendar.getInstance();
        Calendar tueCalendar = Calendar.getInstance();
        Calendar wedCalendar = Calendar.getInstance();
        Calendar thuCalendar = Calendar.getInstance();
        Calendar friCalendar = Calendar.getInstance();
        Calendar satCalendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);

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

//Time Settings for Time Picker.
//Set Initial values.
        if ( starttime.getText() != null && starttime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(starttime.getText().toString());
                starttimeCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        starttime.setInputType(InputType.TYPE_NULL);
        starttime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    starttimeTimePicker.show();
                }
            }
        });
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                starttimeTimePicker.show();
            }
        });
        starttimeTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                starttime.setText(timeFormat.format(newDate.getTime()));
            }

        },starttimeCalendar.get(Calendar.HOUR_OF_DAY), starttimeCalendar.get(Calendar.MINUTE), false);

//Weekly Timer Dialog Set.    **************************************************************************************************
        if ( suntime.getText() != null && suntime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(suntime.getText().toString());
                sunCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        suntime.setInputType(InputType.TYPE_NULL);
        suntime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    sunTimePicker.show();
                }
            }
        });
        suntime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                sunTimePicker.show();
            }
        });
        sunTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                suntime.setText(timeFormat.format(newDate.getTime()));
            }

        },sunCalendar.get(Calendar.HOUR_OF_DAY), sunCalendar.get(Calendar.MINUTE), false);

//Monday Time Set
        if ( montime.getText() != null && montime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(montime.getText().toString());
                monCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        montime.setInputType(InputType.TYPE_NULL);
        montime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    monTimePicker.show();
                }
            }
        });
        montime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                monTimePicker.show();
            }
        });
        monTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                montime.setText(timeFormat.format(newDate.getTime()));
            }

        },monCalendar.get(Calendar.HOUR_OF_DAY), monCalendar.get(Calendar.MINUTE), false);

//Tuesday Time Set
        if ( tuetime.getText() != null && tuetime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(tuetime.getText().toString());
                tueCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        tuetime.setInputType(InputType.TYPE_NULL);
        tuetime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    tueTimePicker.show();
                }
            }
        });
        tuetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                tueTimePicker.show();
            }
        });
        tueTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                tuetime.setText(timeFormat.format(newDate.getTime()));
            }

        },tueCalendar.get(Calendar.HOUR_OF_DAY), tueCalendar.get(Calendar.MINUTE), false);

//Wed Time Set
        if ( wedtime.getText() != null && wedtime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(wedtime.getText().toString());
                wedCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        wedtime.setInputType(InputType.TYPE_NULL);
        wedtime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    wedTimePicker.show();
                }
            }
        });
        wedtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                wedTimePicker.show();
            }
        });
        wedTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                wedtime.setText(timeFormat.format(newDate.getTime()));
            }

        },wedCalendar.get(Calendar.HOUR_OF_DAY), wedCalendar.get(Calendar.MINUTE), false);

//Thu Time Set
        if ( thutime.getText() != null && thutime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(thutime.getText().toString());
                thuCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        thutime.setInputType(InputType.TYPE_NULL);
        thutime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    thuTimePicker.show();
                }
            }
        });
        thutime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                thuTimePicker.show();
            }
        });
        thuTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                thutime.setText(timeFormat.format(newDate.getTime()));
            }

        },thuCalendar.get(Calendar.HOUR_OF_DAY), thuCalendar.get(Calendar.MINUTE), false);

//Fri Time Set
        if ( fritime.getText() != null && fritime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(fritime.getText().toString());
                friCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        fritime.setInputType(InputType.TYPE_NULL);
        fritime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    friTimePicker.show();
                }
            }
        });
        fritime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                friTimePicker.show();
            }
        });
        friTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                fritime.setText(timeFormat.format(newDate.getTime()));
            }

        },friCalendar.get(Calendar.HOUR_OF_DAY), friCalendar.get(Calendar.MINUTE), false);

//Sat Time Set
        if ( sattime.getText() != null && sattime.getText().toString().trim().length() >= 0 ) {
            try {
                java.util.Date d1 = timeFormat.parse(sattime.getText().toString());
                satCalendar.setTime(d1);
                d1 = null;
            }catch(Exception ee1){
            }
        }
        sattime.setInputType(InputType.TYPE_NULL);
        sattime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.d("TREATMENTDATE", "StartDate Focus: " + startDate.getText().toString());
                    satTimePicker.show();
                }
            }
        });
        sattime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TREATMENTDATE", "StartDate Click: " + startDate.getText().toString());
                satTimePicker.show();
            }
        });
        satTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                sattime.setText(timeFormat.format(newDate.getTime()));
            }

        },satCalendar.get(Calendar.HOUR_OF_DAY), satCalendar.get(Calendar.MINUTE), false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicine_form, menu);
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

    public void mffreqchange(View v) {
//Log.d("TREATMENTTAGR", "Called Freq Change");

        RadioGroup rg = (RadioGroup)this.findViewById(R.id.mffreqrg);
        RadioButton rb =  (RadioButton)this.findViewById(rg.getCheckedRadioButtonId());

        if ( rb.getText().toString().equalsIgnoreCase("Daily") ) {
            View wView = (View)this.findViewById(R.id.mfweeklylayout);
            wView.setVisibility(View.INVISIBLE);

            wView = (View)this.findViewById(R.id.mfdailylayout);
            wView.setVisibility(View.VISIBLE);

            frequency = "D";
        } else {
            View wView = (View)this.findViewById(R.id.mfweeklylayout);
            wView.setVisibility(View.VISIBLE);

            wView = (View)this.findViewById(R.id.mfdailylayout);
            wView.setVisibility(View.INVISIBLE);

            frequency = "W";
        }
    }

    public void mfokbuttonc(View v) {
        String mname = mName.getText().toString();

        String tactive = ((active.isChecked())?"Y":"N");
        String treminder = ((reminder.isChecked())?"Y":"N");

        String sunv = ((suncb.isChecked())?"Y":"N");
        String monv = ((moncb.isChecked())?"Y":"N");
        String tuev = ((tuecb.isChecked())?"Y":"N");
        String wedv = ((wedcb.isChecked())?"Y":"N");
        String thuv = ((thucb.isChecked())?"Y":"N");
        String friv = ((fricb.isChecked())?"Y":"N");
        String satv = ((satcb.isChecked())?"Y":"N");

        //Log.d("TREATMENTTAGOK", "Values: Active: " + tactive + ", reminder: " + treminder + ", Suncb: " + sunv + ", freq: " + frequency);


        if ( mname != null && mname.trim().length() > 0 ) {
            DBAdapter treatmentDB = null;

            try {
                treatmentDB = new DBAdapter(this);
                treatmentDB.open();
                treatmentDB.OpenMedicine();

                if ( formFlag.equalsIgnoreCase("modify") ) {
                    treatmentDB.medicineTable.medicineUpdateRow(mid, tid, mname, notes.getText().toString(), startDate.getText().toString(), endDate.getText().toString()
                                                                , treminder, tactive, frequency, hmt.getText().toString(), starttime.getText().toString(),
                                                                sunv, suntime.getText().toString(), monv, montime.getText().toString(), tuev, tuetime.getText().toString(),
                                                                wedv, wedtime.getText().toString(), thuv, thutime.getText().toString(), friv, fritime.getText().toString(),
                                                                satv, sattime.getText().toString());
                } else {
                    treatmentDB.medicineTable.medicineInsert(tid, mname, notes.getText().toString(), startDate.getText().toString(), endDate.getText().toString()
                            , treminder, tactive, frequency, hmt.getText().toString(), starttime.getText().toString(),
                            sunv, suntime.getText().toString(), monv, montime.getText().toString(), tuev, tuetime.getText().toString(),
                            wedv, wedtime.getText().toString(), thuv, thutime.getText().toString(), friv, fritime.getText().toString(),
                            satv, sattime.getText().toString());
                }

                treatmentDB.close();

                finish();

            }catch(Exception ee1) {
                try {
                    treatmentDB.close();
                }catch(Exception ee2){
                }
                Toast t1 = Toast.makeText(MedicineForm.this, "Error while submitting Medicine", Toast.LENGTH_LONG);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
                t1 = null;
            }

        } else {
            Toast t1 = Toast.makeText(MedicineForm.this, "Medicine Name cannot be blank", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }
    public void mfcancelbuttonc(View v) {
        Log.d("TREATMENTTAG", "Doctor Form Cancel Button Click.");
        finish();
    }

    public void loaddata() {
        DBAdapter treatmentDB = null;
        Log.d("TREATMENTTAG", "loading medicine data for: " + tid);
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();

            treatmentDB.OpenMedicine();

            Cursor tc = treatmentDB.medicineTable.medicineGetRow(mid);

            tc.moveToFirst();

            MedicineData td1 = new MedicineData(tc.getInt(tc.getColumnIndex(Medicine.KEY_PARENT)), tc.getInt(tc.getColumnIndex(Medicine.COL_TID)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_MNAME)), tc.getString(tc.getColumnIndex(Medicine.COL_NOTES)), tc.getString(tc.getColumnIndex(Medicine.COL_SDATE)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_EDATE)), tc.getString(tc.getColumnIndex(Medicine.COL_REMINDER)), tc.getString(tc.getColumnIndex(Medicine.COL_ACTIVE)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_FREQ)), tc.getString(tc.getColumnIndex(Medicine.COL_NUMTIMES)), tc.getString(tc.getColumnIndex(Medicine.COL_STARTTIME)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_SUNCB)), tc.getString(tc.getColumnIndex(Medicine.COL_SUNTIME)), tc.getString(tc.getColumnIndex(Medicine.COL_MONCB)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_MONTIME)), tc.getString(tc.getColumnIndex(Medicine.COL_TUECB)), tc.getString(tc.getColumnIndex(Medicine.COL_TUETIME)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_WEDCB)), tc.getString(tc.getColumnIndex(Medicine.COL_WEDTIME)), tc.getString(tc.getColumnIndex(Medicine.COL_THUCB)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_THUTIME)), tc.getString(tc.getColumnIndex(Medicine.COL_FRICB)), tc.getString(tc.getColumnIndex(Medicine.COL_FRITIME)),
                    tc.getString(tc.getColumnIndex(Medicine.COL_SATCB)), tc.getString(tc.getColumnIndex(Medicine.COL_SATTIME)));

            this.mName.setText(td1.getMname());
            this.notes.setText(td1.getNotes());
            this.startDate.setText(td1.getSdate());
            this.endDate.setText(td1.getEdate());
            this.hmt.setText(td1.getNumtimes());
            this.starttime.setText(td1.getStarttime());
            this.frequency = td1.getFreq();

            if ( td1.getReminder().equalsIgnoreCase("Y") ) {
                this.reminder.setChecked(true);
            } else {
                this.reminder.setChecked(false);
            }
            if ( td1.getActive().equalsIgnoreCase("Y") ) {
                this.active.setChecked(true);
            } else {
                this.active.setChecked(false);
            }

            this.suntime.setText(td1.getSunt());
            this.montime.setText(td1.getMont());
            this.tuetime.setText(td1.getTuet());
            this.wedtime.setText(td1.getWedt());
            this.thutime.setText(td1.getThut());
            this.fritime.setText(td1.getFrit());
            this.sattime.setText(td1.getSatt());

            if ( td1.getSuncb().equalsIgnoreCase("Y") ) {
                this.suncb.setChecked(true);
            } else {
                this.suncb.setChecked(false);
            }
            if ( td1.getMoncb().equalsIgnoreCase("Y") ) {
                this.moncb.setChecked(true);
            } else {
                this.moncb.setChecked(false);
            }
            if ( td1.getTuecb().equalsIgnoreCase("Y") ) {
                this.tuecb.setChecked(true);
            } else {
                this.tuecb.setChecked(false);
            }
            if ( td1.getWedcb().equalsIgnoreCase("Y") ) {
                this.wedcb.setChecked(true);
            } else {
                this.wedcb.setChecked(false);
            }
            if ( td1.getThucb().equalsIgnoreCase("Y") ) {
                this.thucb.setChecked(true);
            } else {
                this.thucb.setChecked(false);
            }
            if ( td1.getFricb().equalsIgnoreCase("Y") ) {
                this.fricb.setChecked(true);
            } else {
                this.fricb.setChecked(false);
            }
            if ( td1.getSatcb().equalsIgnoreCase("Y") ) {
                this.satcb.setChecked(true);
            } else {
                this.satcb.setChecked(false);
            }

            treatmentDB.close();

        }catch(Exception ee1) {
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(MedicineForm.this, "Error while loading Medicine data", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }

    public void DeleteMedicineButtonClick(View v) {
        DBAdapter treatmentDB = null;
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();
            treatmentDB.OpenMedicine();

            treatmentDB.medicineTable.medicineDeleteRow(mid);

            treatmentDB.close();

            finish();
        }catch(Exception dele1){
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(MedicineForm.this, "Error while deleting Medicine", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }
    }
}
