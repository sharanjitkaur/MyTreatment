package com.sharanjit.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Notes {
    private SQLiteDatabase treatmentDB;

    // DB Fields
    public static final String KEY_PARENT = "_id";

    public static final String COL_TID = "tid";
    public static final String COL_NOTE = "note";
    public static final String COL_NDATE = "ndate";
    public static final String COL_WEIGHT = "weight";

    public static final String[] NOTES_ALL_KEYS = new String[] {KEY_PARENT, COL_TID, COL_NOTE, COL_NDATE, COL_WEIGHT};

    public static final String TABLE_NAME = "notes";

    public static final String NOTES_CREATE_SQL =
            "create table " + TABLE_NAME
                    + " (" + KEY_PARENT + " integer primary key autoincrement, "
                    + COL_TID + " integer, "
                    + COL_NOTE + " test, "
                    + COL_NDATE + " integer, "
                    + COL_WEIGHT + " text, "
                    + "foreign key (" + COL_TID + ") references " + Treatment.TABLE_NAME + " (" + Treatment.KEY_PARENT + ") "
                    + ");";

    public Notes() {
    }

    public Notes(SQLiteDatabase pTreatmentDB) {
        treatmentDB = pTreatmentDB;
    }

    public long noteInsert(int ptid, String pnote, String pndate, String pweight) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COL_TID, ptid);
        initialValues.put(COL_NOTE, pnote);
        initialValues.put(COL_NDATE, pndate);
        initialValues.put(COL_WEIGHT, pweight);

        return treatmentDB.insert(TABLE_NAME, null, initialValues);
    }

    public boolean noteDeleteRow(long rowId) {
        String where = KEY_PARENT + " = " + rowId;
        return treatmentDB.delete(TABLE_NAME, where, null) != 0;
    }

    public boolean noteDeleteRowForTreatment(long tId) {
        String where = COL_TID + " = " + tId;
        return treatmentDB.delete(TABLE_NAME, where, null) != 0;
    }

    public void noteDeleteAll() {
        Cursor c = noteGetAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_PARENT);
        if (c.moveToFirst()) {
            do {
                noteDeleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public Cursor noteGetAllRows() {
        String where = null;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, NOTES_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor noteGetRowsForTreatment(int tid) {
        String where = COL_TID + "=" + tid;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, NOTES_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor noteGetRow(long rowId) {
        String where = KEY_PARENT + "=" + rowId;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, NOTES_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean noteUpdateRow(long rowId, int ptid, String pnote, String pndate, String pweight) {
        String where = KEY_PARENT + "=" + rowId + " and " + COL_TID + " = " + ptid;

        ContentValues newValues = new ContentValues();
        //newValues.put(COL_TID, ptid);
        newValues.put(COL_NOTE, pnote);
        //newValues.put(COL_NDATE, pndate);
        newValues.put(COL_WEIGHT, pweight);

        return treatmentDB.update(TABLE_NAME, newValues, where, null) != 0;
    }

}
