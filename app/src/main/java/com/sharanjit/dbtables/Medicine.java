package com.sharanjit.dbtables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Medicine {
    private SQLiteDatabase treatmentDB;

    // DB Fields
    public static final String KEY_PARENT = "_id";

    public static final String COL_TID = "tid";
    public static final String COL_MNAME = "mname";
    public static final String COL_NOTES = "notes";
    public static final String COL_SDATE = "sdate";
    public static final String COL_EDATE = "edate";
    public static final String COL_REMINDER = "reminder";      //To show Notification
    public static final String COL_ACTIVE = "active";        //Is Medicine Active or not used anymore or suspended.
    public static final String COL_FREQ = "freq";          //Daily or Weekly (Monthly in Phase 2) "D"/"W"
    public static final String COL_NUMTIMES = "numtimes";      //Number of Times
    public static final String COL_STARTTIME = "starttime";     //For Daily

    public static final String COL_SUNCB = "suncb";
    public static final String COL_SUNTIME = "suntime";
    public static final String COL_MONCB = "moncb";
    public static final String COL_MONTIME = "montime";
    public static final String COL_TUECB = "tuecb";
    public static final String COL_TUETIME = "tuetime";
    public static final String COL_WEDCB = "wedcb";
    public static final String COL_WEDTIME = "wedtime";
    public static final String COL_THUCB = "thucb";
    public static final String COL_THUTIME = "thutime";
    public static final String COL_FRICB = "fricb";
    public static final String COL_FRITIME = "fritime";
    public static final String COL_SATCB = "satcb";
    public static final String COL_SATTIME = "sattime";

    public static final String[] MEDICINE_ALL_KEYS = new String[] {KEY_PARENT, COL_TID, COL_MNAME, COL_NOTES, COL_SDATE, COL_EDATE, COL_REMINDER, COL_ACTIVE, COL_FREQ, COL_NUMTIMES, COL_STARTTIME, COL_SUNCB, COL_SUNTIME, COL_MONCB, COL_MONTIME, COL_TUECB, COL_TUETIME, COL_WEDCB, COL_WEDTIME, COL_THUCB, COL_THUTIME, COL_FRICB, COL_FRITIME, COL_SATCB, COL_SATTIME};

    public static final String TABLE_NAME = "tmedicines";

    public static final String MEDICINE_CREATE_SQL =
            "create table " + TABLE_NAME
                    + " (" + KEY_PARENT + " integer primary key autoincrement, "
                    + COL_TID + " integer, "
                    + COL_MNAME + " text not null, "
                    + COL_NOTES + " test, "
                    + COL_SDATE + " text, "
                    + COL_EDATE + " text, "
                    + COL_REMINDER + " text, "
                    + COL_ACTIVE + " text, "
                    + COL_FREQ + " text, "
                    + COL_NUMTIMES + " text, "
                    + COL_STARTTIME + " text, "
                    + COL_SUNCB + " text, "
                    + COL_SUNTIME + " text, "
                    + COL_MONCB + " text, "
                    + COL_MONTIME + " text, "
                    + COL_TUECB + " text, "
                    + COL_TUETIME + " text, "
                    + COL_WEDCB + " text, "
                    + COL_WEDTIME + " text, "
                    + COL_THUCB + " text, "
                    + COL_THUTIME + " text, "
                    + COL_FRICB + " text, "
                    + COL_FRITIME + " text, "
                    + COL_SATCB + " text, "
                    + COL_SATTIME + " text, "
                    + "foreign key (" + COL_TID + ") references " + Treatment.TABLE_NAME + " (" + Treatment.KEY_PARENT + ") "
                    + ");";

    public Medicine() {
    }

    public Medicine(SQLiteDatabase pTreatmentDB) {
        treatmentDB = pTreatmentDB;
    }

    public long medicineInsert(int ptid, String pname, String pnotes, String psdate, String pedate, String preminder, String pactive, String pfreq, String pnumtimes, String pstarttime
            ,String psuncb, String psunt,String pmoncb, String pmont,String ptuecb, String ptuet,String pwedcb, String pwedt,String pthucb, String pthut,String pfricb, String pfrit,String psatcb, String psatt) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COL_TID, ptid);
        initialValues.put(COL_MNAME, pname);
        initialValues.put(COL_NOTES, pnotes);
        initialValues.put(COL_SDATE, psdate);
        initialValues.put(COL_EDATE, pedate);
        initialValues.put(COL_REMINDER, preminder);
        initialValues.put(COL_ACTIVE, pactive);
        initialValues.put(COL_FREQ, pfreq);
        initialValues.put(COL_NUMTIMES, pnumtimes);
        initialValues.put(COL_STARTTIME, pstarttime);
        initialValues.put(COL_SUNCB, psuncb);
        initialValues.put(COL_SUNTIME, psunt);
        initialValues.put(COL_MONCB, pmoncb);
        initialValues.put(COL_MONTIME, pmont);
        initialValues.put(COL_TUECB, ptuecb);
        initialValues.put(COL_TUETIME, ptuet);
        initialValues.put(COL_WEDCB, pwedcb);
        initialValues.put(COL_WEDTIME, pwedt);
        initialValues.put(COL_THUCB, pthucb);
        initialValues.put(COL_THUTIME, pthut);
        initialValues.put(COL_FRICB, pfricb);
        initialValues.put(COL_FRITIME, pfrit);
        initialValues.put(COL_SATCB, psatcb);
        initialValues.put(COL_SATTIME, psatt);

        return treatmentDB.insert(TABLE_NAME, null, initialValues);
    }

    public boolean medicineDeleteRow(long rowId) {
        String where = KEY_PARENT + " = " + rowId;
        return treatmentDB.delete(TABLE_NAME, where, null) != 0;
    }

    public boolean medicineDeleteRowForTreatment(long tId) {
        String where = COL_TID + " = " + tId;
        return treatmentDB.delete(TABLE_NAME, where, null) != 0;
    }

    public void medicineDeleteAll() {
        Cursor c = medicineGetAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_PARENT);
        if (c.moveToFirst()) {
            do {
                medicineDeleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public Cursor medicineGetAllRows() {
        String where = null;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, MEDICINE_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor medicineGetRowsForTreatment(int tid) {
        String where = COL_TID + "=" + tid;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, MEDICINE_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor medicineGetRow(long rowId) {
        String where = KEY_PARENT + "=" + rowId;
        Cursor c = 	treatmentDB.query(true, TABLE_NAME, MEDICINE_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean medicineUpdateRow(long rowId, int ptid, String pname, String pnotes, String psdate, String pedate, String preminder, String pactive, String pfreq, String pnumtimes, String pstarttime
            ,String psuncb, String psunt,String pmoncb, String pmont,String ptuecb, String ptuet,String pwedcb, String pwedt,String pthucb, String pthut,String pfricb, String pfrit,String psatcb, String psatt) {
        String where = KEY_PARENT + "=" + rowId + " and " + COL_TID + " = " + ptid;

        ContentValues newValues = new ContentValues();
        //newValues.put(COL_TID, ptid);
        newValues.put(COL_MNAME, pname);
        newValues.put(COL_NOTES, pnotes);
        newValues.put(COL_SDATE, psdate);
        newValues.put(COL_EDATE, pedate);
        newValues.put(COL_REMINDER, preminder);
        newValues.put(COL_ACTIVE, pactive);
        newValues.put(COL_FREQ, pfreq);
        newValues.put(COL_NUMTIMES, pnumtimes);
        newValues.put(COL_STARTTIME, pstarttime);
        newValues.put(COL_SUNCB, psuncb);
        newValues.put(COL_SUNTIME, psunt);
        newValues.put(COL_MONCB, pmoncb);
        newValues.put(COL_MONTIME, pmont);
        newValues.put(COL_TUECB, ptuecb);
        newValues.put(COL_TUETIME, ptuet);
        newValues.put(COL_WEDCB, pwedcb);
        newValues.put(COL_WEDTIME, pwedt);
        newValues.put(COL_THUCB, pthucb);
        newValues.put(COL_THUTIME, pthut);
        newValues.put(COL_FRICB, pfricb);
        newValues.put(COL_FRITIME, pfrit);
        newValues.put(COL_SATCB, psatcb);
        newValues.put(COL_SATTIME, psatt);

        return treatmentDB.update(TABLE_NAME, newValues, where, null) != 0;
    }

}
