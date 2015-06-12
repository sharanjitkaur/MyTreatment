package com.sharanjit.mytreatment.doctor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sharanjit.dbtables.Doctor;
import com.sharanjit.mytreatment.DBAdapter;
import com.sharanjit.mytreatment.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorList extends ActionBarActivity {
    DBAdapter treatmentDB;
    private int tid = -1;

    ListView listview;
    List<DoctorData> doctorListData;
    DoctorListAdapter tla;

    boolean listRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        Bundle params = getIntent().getExtras();
        tid = params.getInt("TreatmentID");

        openDB();

        SetList();

        params = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_treatment_list, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
        listRefresh = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( !listRefresh ) {
            //Log.d("TREATMENTTAG", "Refreshing List");
            SetList();
        }

    }

    private void openDB() {
        treatmentDB = new DBAdapter(this);
        treatmentDB.open();

        treatmentDB.OpenDoctor();
    }
    private void closeDB() {
        treatmentDB.close();
    }

    public void SetDoctorListData(){
        DoctorData td = null;

        doctorListData = new ArrayList<DoctorData>();

        try {
            Cursor tc = treatmentDB.doctorTable.doctorGetRowsForTreatment(tid);
            tc.moveToFirst();

            if ( tc.getCount() > 0) {
                do {
                    doctorListData.add(new DoctorData(tc.getInt(tc.getColumnIndex(Doctor.KEY_PARENT)), tid, tc.getString(tc.getColumnIndex(Doctor.COL_DNAME)), tc.getString(tc.getColumnIndex(Doctor.COL_NOTES)), tc.getString(tc.getColumnIndex(Doctor.COL_ADD1)), tc.getString(tc.getColumnIndex(Doctor.COL_ADD2)), tc.getString(tc.getColumnIndex(Doctor.COL_PHONE))));
                } while (tc.moveToNext());

            } else {
                doctorListData.add(new DoctorData(-1, tid, "No Data", null, null, null, null));
            }
        }catch(Exception ee1) {
            Log.e("TREATMENTTAG", "Error: " + ee1);
        }
    }

    public void doctorlistaddbuttonc(View v) {
        Intent intent = new Intent(DoctorList.this, DoctorForm.class);
        intent.putExtra("DoctorFormFlag", "add");
        intent.putExtra("DoctorFormTID", tid);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void doctorlistcancelbuttonc(View v) {
        Log.d("TREATMENTTAG", "Cancel called from Doctor List");
        finish();
    }

    private void SetList() {
        listview = (ListView)findViewById(R.id.doctorlistview);

        SetDoctorListData();

        tla = new DoctorListAdapter(this, doctorListData);

        listview.setAdapter(tla);

        listRefresh = true;
    }
}
