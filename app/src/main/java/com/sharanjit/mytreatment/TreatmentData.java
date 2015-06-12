package com.sharanjit.mytreatment;

public class TreatmentData {
    int tid;
    String name;
    String sdate;
    String edate;
    String desc;

    public TreatmentData() {
    }

    public TreatmentData(int id, String pname, String psdate, String pedate, String pdesc) {
        tid = id;
        name = pname;
        sdate = psdate;
        edate = pedate;
        desc = pdesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
