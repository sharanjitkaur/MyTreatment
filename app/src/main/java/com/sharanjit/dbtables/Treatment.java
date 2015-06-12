package com.sharanjit.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Treatment {
    private SQLiteDatabase treatmentDB;

    // DB Fields
    public static final String KEY_PARENT = "_id";

    public static final String COL_NAME = "name";
    public static final String COL_STARTDATE = "startdate";
    public static final String COL_ENDDATE = "enddate";
    public static final String COL_DESC = "desc";

    public static final String[] TREATMENT_ALL_KEYS = new String[] {KEY_PARENT, COL_NAME, COL_STARTDATE, COL_ENDDATE, COL_DESC};

    public static final String TABLE_NAME = "treatments";

    public static final String TREATMENT_CREATE_SQL =
            "create table " + TABLE_NAME
                    + " (" + KEY_PARENT + " integer primary key autoincrement, "
                    + COL_NAME + " text not null, "
                    + COL_STARTDATE + " integer, "
                    + COL_ENDDATE + " integer, "
                    + COL_DESC + " text"
                    + ");";

    public Treatment() {
    }

    public Treatment(SQLiteDatabase pTreatmentDB) {
        treatmentDB = pTreatmentDB;
    }

    public long treatmentInsert(String pname, String pstartdate, String penddate, String pdesc) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COL_NAME, pname);
        initialValues.put(COL_STARTDATE, pstartdate);
        initialValues.put(COL_ENDDATE, penddate);
        initialValues.put(COL_DESC, pdesc);

        return treatmentDB.insert(TABLE_NAME, null, initialValues);
    }

    public boolean treatmentDeleteRow(long rowId) {
        String where = KEY_PARENT + " = " + rowId;
        return treatmentDB.delete(TABLE_NAME, where, null) != 0;
    }

    public void treatmentDeleteAll() {
        Cursor c = treatmentGetAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_PARENT);
        if (c.moveToFirst()) {
            do {
                treatmentDeleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public Cursor treatmentGetAllRows() {
        String where = null;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, TREATMENT_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor treatmentGetRow(long rowId) {
        String where = KEY_PARENT + "=" + rowId;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, TREATMENT_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean treatmentUpdateRow(long rowId, String pname, String pstartdate, String penddate, String pdesc) {
        String where = KEY_PARENT + "=" + rowId;

        ContentValues newValues = new ContentValues();
        newValues.put(COL_NAME, pname);
        newValues.put(COL_STARTDATE, pstartdate);
        newValues.put(COL_ENDDATE, penddate);
        newValues.put(COL_DESC, pdesc);

        return treatmentDB.update(TABLE_NAME, newValues, where, null) != 0;
    }

}
