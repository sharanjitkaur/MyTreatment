package com.sharanjit.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Doctor {
    private SQLiteDatabase treatmentDB;

    // DB Fields
    public static final String KEY_PARENT = "_id";

    public static final String COL_TID = "tid";
    public static final String COL_DNAME = "dname";
    public static final String COL_NOTES = "notes";
    public static final String COL_ADD1 = "add1";
    public static final String COL_ADD2 = "add2";
    public static final String COL_PHONE = "phone";

    public static final String[] DOCTOR_ALL_KEYS = new String[] {KEY_PARENT, COL_TID, COL_DNAME, COL_NOTES, COL_ADD1, COL_ADD2, COL_PHONE};

    public static final String TABLE_NAME = "tdoctors";

    public static final String DOCTOR_CREATE_SQL =
            "create table " + TABLE_NAME
                    + " (" + KEY_PARENT + " integer primary key autoincrement, "
                    + COL_TID + " integer, "
                    + COL_DNAME + " text not null, "
                    + COL_NOTES + " test, "
                    + COL_ADD1 + " text, "
                    + COL_ADD2 + " text, "
                    + COL_PHONE + " text, "
                    + "foreign key (" + COL_TID + ") references " + Treatment.TABLE_NAME + " (" + Treatment.KEY_PARENT + ") "
                    + ");";

    public Doctor() {
    }

    public Doctor(SQLiteDatabase pTreatmentDB) {
        treatmentDB = pTreatmentDB;
    }

    public long doctorInsert(int ptid, String pname, String pnotes, String padd1, String padd2, String pphone) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COL_TID, ptid);
        initialValues.put(COL_DNAME, pname);
        initialValues.put(COL_NOTES, pnotes);
        initialValues.put(COL_ADD1, padd1);
        initialValues.put(COL_ADD2, padd2);
        initialValues.put(COL_PHONE, pphone);

        return treatmentDB.insert(TABLE_NAME, null, initialValues);
    }

    public boolean doctorDeleteRow(long rowId) {
        String where = KEY_PARENT + " = " + rowId;
        return treatmentDB.delete(TABLE_NAME, where, null) != 0;
    }

    public boolean doctorDeleteRowForTreatment(long tId) {
        String where = COL_TID + " = " + tId;
        return treatmentDB.delete(TABLE_NAME, where, null) != 0;
    }

    public void doctorDeleteAll() {
        Cursor c = doctorGetAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_PARENT);
        if (c.moveToFirst()) {
            do {
                doctorDeleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public Cursor doctorGetAllRows() {
        String where = null;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, DOCTOR_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor doctorGetRowsForTreatment(int tid) {
        String where = COL_TID + "=" + tid;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, DOCTOR_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor doctorGetRow(long rowId) {
        String where = KEY_PARENT + "=" + rowId;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, DOCTOR_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean doctorUpdateRow(long rowId, int ptid, String pname, String pnotes, String padd1, String padd2, String pphone) {
        String where = KEY_PARENT + "=" + rowId + " and " + COL_TID + " = " + ptid;

        ContentValues newValues = new ContentValues();
        //newValues.put(COL_TID, ptid);
        newValues.put(COL_DNAME, pname);
        newValues.put(COL_NOTES, pnotes);
        newValues.put(COL_ADD1, padd1);
        newValues.put(COL_ADD2, padd2);
        newValues.put(COL_PHONE, pphone);

        return treatmentDB.update(TABLE_NAME, newValues, where, null) != 0;
    }

}
