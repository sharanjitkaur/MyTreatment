package com.sharanjit.mytreatment.notes;

public class NoteData {
    int tid;
    int nid;
    String note;
    String ndate;
    String weight;

    public NoteData() {
    }

    public NoteData(int id, int ptid, String pnotes, String pndate, String pweight) {
        nid = id;
        tid = ptid;
        note = pnotes;
        ndate = pndate;
        weight = pweight;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
