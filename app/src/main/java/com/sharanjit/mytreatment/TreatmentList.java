package com.sharanjit.mytreatment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sharanjit.dbtables.Treatment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TreatmentList extends ActionBarActivity {
    DBAdapter treatmentDB;

    ListView listview;
    List<TreatmentData> treatmentListData;
    TreatmentListAdapter tla;

    boolean listRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_list);

        openDB();

        SetList();

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

        treatmentDB.OpenTreatment();
    }
    private void closeDB() {
        treatmentDB.close();
    }

    public void SetTreatmentListData(){
        TreatmentData td = null;

        treatmentListData = new ArrayList<TreatmentData>();
/*
        td = new TreatmentData("Flu", "22-JUN-2014", "22-JUL-2014", "Flu Treatment");
        treatmentListData.add(td);
        td = new TreatmentData("Flu 2", "22-AUG-2014", "22-SEP-2014", "Flu Treatment");
        treatmentListData.add(td);
        td = new TreatmentData("Flu 3", "22-NOV-2014", "22-DEC-2014", "Flu Treatment");
        treatmentListData.add(td);
*/
        /*for ( int i = 1; i <= 20; i++) {
            treatmentDB.treatmentInsert("Name " + i, new java.sql.Date(new java.util.Date().getTime()), new java.sql.Date(new java.util.Date().getTime()), "Name Desc for Treatment: " + i);
        }*/
        //treatmentDB.treatmentDeleteAll();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Cursor tc = treatmentDB.treatmentTable.treatmentGetAllRows();
            tc.moveToFirst();
/*
            long sdt = -1;
            long edt = -1;
            java.sql.Date sdtd = null;
            java.sql.Date edtd = null;
            if ( tc.getCount() > 0) {
                do {
                    sdt = tc.getLong(tc.getColumnIndex(DBAdapter.TREATMENT_COL_STARTDATE));
                    edt = tc.getLong(tc.getColumnIndex(DBAdapter.TREATMENT_COL_ENDDATE));
                    if (sdt > 0) {
                        sdtd = new java.sql.Date(sdt);
                    }
                    if (sdt > 0) {
                        edtd = new java.sql.Date(edt);
                    }
                    treatmentListData.add(new TreatmentData(tc.getString(tc.getColumnIndex(DBAdapter.TREATMENT_COL_NAME)), sdtd, edtd, tc.getString(tc.getColumnIndex(DBAdapter.TREATMENT_COL_DESC))));
                } while (tc.moveToNext());
*/
            if ( tc.getCount() > 0) {
                do {
                    treatmentListData.add(new TreatmentData(tc.getInt(tc.getColumnIndex(Treatment.KEY_PARENT)), tc.getString(tc.getColumnIndex(Treatment.COL_NAME)), tc.getString(tc.getColumnIndex(Treatment.COL_STARTDATE)), tc.getString(tc.getColumnIndex(Treatment.COL_ENDDATE)), tc.getString(tc.getColumnIndex(Treatment.COL_DESC))));
                } while (tc.moveToNext());

            } else {
                treatmentListData.add(new TreatmentData(-1, "No Data", null, null, ""));
            }
        }catch(Exception ee1) {
            Log.e("TREATMENTTAG", "Error: " + ee1);
        }
    }

    public void treatmentlistaddbuttonc(View v) {
        Intent intent = new Intent(TreatmentList.this, TreatmentForm.class);
        intent.putExtra("TreatmentFormFlag", "add");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void treatmentlistcancelbuttonc(View v) {
        finish();
    }

    private void SetList() {
        listview = (ListView)findViewById(R.id.treatmentlistview);

        SetTreatmentListData();

        tla = new TreatmentListAdapter(this, treatmentListData);

        listview.setAdapter(tla);

        listRefresh = true;
    }

}
