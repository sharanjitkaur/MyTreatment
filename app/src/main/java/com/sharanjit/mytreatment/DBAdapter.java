package com.sharanjit.mytreatment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sharanjit.dbtables.Doctor;
import com.sharanjit.dbtables.Medicine;
import com.sharanjit.dbtables.Notes;
import com.sharanjit.dbtables.Treatment;

public class DBAdapter {

    public static final String DATABASE_NAME = "TreatmentDb";

    public static final int DATABASE_VERSION = 7;

    private final Context context;

    private DBHelper treatmentDBHelper;
    private SQLiteDatabase treatmentDB;

//Set all Tables here
    public Treatment treatmentTable = null;
    public Doctor doctorTable = null;
    public Notes notesTable = null;
    public Medicine medicineTable = null;


    public DBAdapter(Context ctx) {
        this.context = ctx;
        treatmentDBHelper = new DBHelper(context);
    }

    public DBAdapter open() {
        treatmentDB = treatmentDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {

        treatmentDBHelper.close();

//Set all Table pointers to Null;
        treatmentTable = null;
        doctorTable = null;
        notesTable = null;
        medicineTable = null;
    }

    public void OpenTreatment() {
        treatmentTable = new Treatment(treatmentDB);
    }
    public void OpenDoctor() {
        doctorTable = new Doctor(treatmentDB);
    }
    public void OpenNotes() {
        notesTable = new Notes(treatmentDB);
    }
    public void OpenMedicine() {
        medicineTable = new Medicine(treatmentDB);
    }

/*
 * Private helper class which handles database creation and upgrades.
 */
    private static class DBHelper extends SQLiteOpenHelper
    {
        DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {

            _db.execSQL(Treatment.TREATMENT_CREATE_SQL);

            _db.execSQL(Doctor.DOCTOR_CREATE_SQL);

            _db.execSQL(Notes.NOTES_CREATE_SQL);

            _db.execSQL(Medicine.MEDICINE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w("TREATMENTTAG", "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + Treatment.TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + Doctor.TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + Notes.TABLE_NAME);
            _db.execSQL("DROP TABLE IF EXISTS " + Medicine.TABLE_NAME);

            // Recreate new database:
            onCreate(_db);
/*
Can use this to port old data.
Can do all sorts of things here, like, instead of dropping tables, alter tables, etc. to keep data
            _db.execSQL("insert into " + DATABASE_TABLE + "(name, studentNum, favColour) values ('test', 1234, 'Blue')");
*/
        }
    }
}
