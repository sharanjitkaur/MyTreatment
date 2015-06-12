package com.sharanjit.mytreatment.notes;

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

import com.sharanjit.dbtables.Notes;
import com.sharanjit.mytreatment.DBAdapter;
import com.sharanjit.mytreatment.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteForm extends ActionBarActivity {
    private String formFlag = "";
    private int nid = -1;
    private int tid = -1;

    private Button delb;

    private EditText notes;
    private EditText weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);

        notes = (EditText) findViewById(R.id.nfnotest);
        weight = (EditText) findViewById(R.id.nfweightt);

        delb = (Button) findViewById(R.id.nfdelb);

        try {
            Bundle params = getIntent().getExtras();
            if ( params != null ) {
                formFlag = params.getString("NoteFormFlag");
            }

            tid = params.getInt("NoteFormTID");
            if (formFlag.equalsIgnoreCase("modify")) {
                nid = params.getInt("NoteFormNID");

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
        getMenuInflater().inflate(R.menu.menu_note_form, menu);
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

    public void nfokbuttonc(View v) {
        String dnote = notes.getText().toString();
        String dweight = weight.getText().toString();

        if ( dnote != null && dnote.trim().length() > 0 ) {
            DBAdapter treatmentDB = null;
            try {
                treatmentDB = new DBAdapter(this);
                treatmentDB.open();
                treatmentDB.OpenNotes();

                Calendar d1 = Calendar.getInstance();

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");

                if ( formFlag.equalsIgnoreCase("modify") ) {
                    treatmentDB.notesTable.noteUpdateRow(nid, tid, dnote, null, dweight);
                } else {
                    treatmentDB.notesTable.noteInsert(tid, dnote, sd1.format(d1.getTime()), dweight);
                }

                treatmentDB.close();

                sd1 = null;
                d1 = null;

                finish();

            }catch(Exception ee1) {
                try {
                    treatmentDB.close();
                }catch(Exception ee2){
                }
                Toast t1 = Toast.makeText(NoteForm.this, "Error while submitting Note", Toast.LENGTH_LONG);
                t1.setGravity(Gravity.CENTER, 0, 0);
                t1.show();
                t1 = null;
            }

        } else {
            Toast t1 = Toast.makeText(NoteForm.this, "Note cannot be blank", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }
    public void nfcancelbuttonc(View v) {
        //Log.d("TREATMENTTAG","Doctor Form Cancel Button Click.");
        finish();
    }

    public void loaddata() {
        DBAdapter treatmentDB = null;
        //Log.d("TREATMENTTAG", "loading data for: " + tid);
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();

            treatmentDB.OpenNotes();

            Cursor tc = treatmentDB.notesTable.noteGetRow(nid);

            tc.moveToFirst();

            NoteData td1 = new NoteData(tc.getInt(tc.getColumnIndex(Notes.KEY_PARENT)), tc.getInt(tc.getColumnIndex(Notes.COL_TID)), tc.getString(tc.getColumnIndex(Notes.COL_NOTE)), tc.getString(tc.getColumnIndex(Notes.COL_NDATE)), tc.getString(tc.getColumnIndex(Notes.COL_WEIGHT)));

            this.notes.setText(td1.getNote());
            this.weight.setText(td1.getWeight());

            treatmentDB.close();

        }catch(Exception ee1) {
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(NoteForm.this, "Error while loading Note data", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }

    }

    public void DeleteNoteButtonClick(View v) {
        DBAdapter treatmentDB = null;
        try {
            treatmentDB = new DBAdapter(this);
            treatmentDB.open();
            treatmentDB.OpenNotes();

            treatmentDB.notesTable.noteDeleteRow(nid);

            treatmentDB.close();

            finish();
        }catch(Exception dele1){
            try {
                treatmentDB.close();
            }catch(Exception ee2){
            }
            Toast t1 = Toast.makeText(NoteForm.this, "Error while deleting Note", Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;
        }
    }
}
