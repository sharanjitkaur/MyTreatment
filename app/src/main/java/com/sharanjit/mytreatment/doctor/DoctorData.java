package com.sharanjit.mytreatment.doctor;

public class DoctorData {
    int tid;
    int did;
    String dname;
    String notes;
    String add1;
    String add2;
    String phone;

    public DoctorData() {
    }

    public DoctorData(int id, int ptid, String pname, String pnotes, String padd1, String padd2, String pphone) {
        did = id;
        tid = ptid;
        dname = pname;
        notes = pnotes;
        add1 = padd1;
        add2 = padd2;
        phone = pphone;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
