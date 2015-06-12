package com.sharanjit.mytreatment.notes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sharanjit.dbtables.Notes;
import com.sharanjit.mytreatment.DBAdapter;
import com.sharanjit.mytreatment.R;
import com.sharanjit.mytreatment.doctor.DoctorData;

import java.util.ArrayList;
import java.util.List;

public class AllNoteList extends ActionBarActivity {
    DBAdapter treatmentDB;

    ListView listview;
    List<NoteData> noteListData;
    AllNoteListAdapter tla;

    boolean listRefresh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note_list);

        openDB();

        SetList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_note_list, menu);
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

        treatmentDB.OpenNotes();
    }
    private void closeDB() {
        treatmentDB.close();
    }

    public void SetNoteListData(){
        DoctorData td = null;

        noteListData = new ArrayList<NoteData>();

        try {
            Cursor tc = treatmentDB.notesTable.noteGetAllRows();
            tc.moveToFirst();

            if ( tc.getCount() > 0) {
                do {
                    noteListData.add(new NoteData(tc.getInt(tc.getColumnIndex(Notes.KEY_PARENT)), -1, tc.getString(tc.getColumnIndex(Notes.COL_NOTE)), tc.getString(tc.getColumnIndex(Notes.COL_NDATE)), tc.getString(tc.getColumnIndex(Notes.COL_WEIGHT))));
                } while (tc.moveToNext());

            } else {
                noteListData.add(new NoteData(-1, -1, "No Data", null, null));
            }
        }catch(Exception ee1) {
            Log.e("TREATMENTTAG", "Error: " + ee1);
        }
    }

    public void allnotelistcancelbuttonc(View v) {
        //Log.d("TREATMENTTAG", "Cancel called from Doctor List");
        finish();
    }

    private void SetList() {
        listview = (ListView)findViewById(R.id.allnotelistview);

        SetNoteListData();

        tla = new AllNoteListAdapter(this, noteListData);

        listview.setAdapter(tla);

        listRefresh = true;
    }

}
