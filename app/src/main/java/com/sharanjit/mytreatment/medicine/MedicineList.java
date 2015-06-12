package com.sharanjit.mytreatment.medicine;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sharanjit.dbtables.Medicine;
import com.sharanjit.mytreatment.DBAdapter;
import com.sharanjit.mytreatment.R;

import java.util.ArrayList;
import java.util.List;

public class MedicineList extends ActionBarActivity {
    DBAdapter treatmentDB;
    private int tid = -1;

    ListView listview;
    List<MedicineData> medicineListData;
    MedicineListAdapter tla;

    boolean listRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        Bundle params = getIntent().getExtras();
        tid = params.getInt("TreatmentID");

        openDB();

        SetList();

        params = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicine_list, menu);
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

        treatmentDB.OpenMedicine();
    }
    private void closeDB() {
        treatmentDB.close();
    }

    public void SetMedicineListData(){
        MedicineData td = null;

        medicineListData = new ArrayList<MedicineData>();

        try {
            Cursor tc = treatmentDB.medicineTable.medicineGetRowsForTreatment(tid);
            tc.moveToFirst();

            if ( tc.getCount() > 0) {
                do {
                    medicineListData.add(new MedicineData(tc.getInt(tc.getColumnIndex(Medicine.KEY_PARENT)), tid, tc.getString(tc.getColumnIndex(Medicine.COL_MNAME)), tc.getString(tc.getColumnIndex(Medicine.COL_NOTES)), tc.getString(tc.getColumnIndex(Medicine.COL_SDATE)), tc.getString(tc.getColumnIndex(Medicine.COL_EDATE)), tc.getString(tc.getColumnIndex(Medicine.COL_REMINDER)), tc.getString(tc.getColumnIndex(Medicine.COL_ACTIVE)), tc.getString(tc.getColumnIndex(Medicine.COL_FREQ)), tc.getString(tc.getColumnIndex(Medicine.COL_NUMTIMES)), tc.getString(tc.getColumnIndex(Medicine.COL_STARTTIME))));
                } while (tc.moveToNext());

            } else {
                medicineListData.add(new MedicineData(-1, tid, "No Data", null, null, null, null, null, null, null, null));
            }
        }catch(Exception ee1) {
            Log.e("TREATMENTTAG", "Error: " + ee1);
        }
    }

    public void medicinelistaddbuttonc(View v) {
        Intent intent = new Intent(MedicineList.this, MedicineForm.class);
        intent.putExtra("MedicineFormFlag", "add");
        intent.putExtra("MedicineFormTID", tid);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void medicinelistcancelbuttonc(View v) {
        //Log.d("TREATMENTTAG", "Cancel called from Doctor List");
        finish();
    }

    private void SetList() {
        listview = (ListView)findViewById(R.id.medicinelistview);

        SetMedicineListData();

        tla = new MedicineListAdapter(this, medicineListData);

        listview.setAdapter(tla);

        listRefresh = true;
    }
}
