package com.sharanjit.mytreatment.medicine;

public class MedicineData {
    int tid;
    int mid;
    String mname;
    String notes;
    String sdate;
    String edate;
    String reminder;
    String active;
    String freq;
    String numtimes;
    String starttime;

    String suncb;
    String sunt;
    String moncb;
    String mont;
    String tuecb;
    String tuet;
    String wedcb;
    String wedt;
    String thucb;
    String thut;
    String fricb;
    String frit;
    String satcb;
    String satt;

    public MedicineData() {
    }

    public MedicineData(int id, int ptid, String pname, String pnotes, String psdate, String pedate, String preminder, String pactive, String pfreq, String pnumtimes, String pstarttime) {
        mid = id;
        tid = ptid;
        mname = pname;
        notes = pnotes;
        sdate = psdate;
        edate = pedate;
        reminder = preminder;
        active = pactive;
        freq = pfreq;
        numtimes = pnumtimes;
        starttime = pstarttime;
    }

    public MedicineData(int id, int ptid, String pname, String pnotes, String psdate, String pedate, String preminder, String pactive, String pfreq, String pnumtimes, String pstarttime
                        ,String psuncb, String psunt,String pmoncb, String pmont,String ptuecb, String ptuet,String pwedcb, String pwedt,String pthucb, String pthut,String pfricb, String pfrit,String psatcb, String psatt) {
        mid = id;
        tid = ptid;
        mname = pname;
        notes = pnotes;
        sdate = psdate;
        edate = pedate;
        reminder = preminder;
        active = pactive;
        freq = pfreq;
        numtimes = pnumtimes;
        starttime = pstarttime;

        suncb = psuncb;
        sunt = psunt;
        moncb = pmoncb;
        mont = pmont;
        tuecb = ptuecb;
        tuet = ptuet;
        wedcb = pwedcb;
        wedt = pwedt;
        thucb = pthucb;
        thut = pthut;
        fricb = pfricb;
        frit = pfrit;
        satcb = psatcb;
        satt = psatt;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getNumtimes() {
        return numtimes;
    }

    public void setNumtimes(String numtimes) {
        this.numtimes = numtimes;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getSuncb() {
        return suncb;
    }

    public void setSuncb(String suncb) {
        this.suncb = suncb;
    }

    public String getSunt() {
        return sunt;
    }

    public void setSunt(String sunt) {
        this.sunt = sunt;
    }

    public String getMoncb() {
        return moncb;
    }

    public void setMoncb(String moncb) {
        this.moncb = moncb;
    }

    public String getMont() {
        return mont;
    }

    public void setMont(String mont) {
        this.mont = mont;
    }

    public String getTuecb() {
        return tuecb;
    }

    public void setTuecb(String tuecb) {
        this.tuecb = tuecb;
    }

    public String getTuet() {
        return tuet;
    }

    public void setTuet(String tuet) {
        this.tuet = tuet;
    }

    public String getWedcb() {
        return wedcb;
    }

    public void setWedcb(String wedcb) {
        this.wedcb = wedcb;
    }

    public String getWedt() {
        return wedt;
    }

    public void setWedt(String wedt) {
        this.wedt = wedt;
    }

    public String getThucb() {
        return thucb;
    }

    public void setThucb(String thucb) {
        this.thucb = thucb;
    }

    public String getThut() {
        return thut;
    }

    public void setThut(String thut) {
        this.thut = thut;
    }

    public String getFricb() {
        return fricb;
    }

    public void setFricb(String fricb) {
        this.fricb = fricb;
    }

    public String getFrit() {
        return frit;
    }

    public void setFrit(String frit) {
        this.frit = frit;
    }

    public String getSatcb() {
        return satcb;
    }

    public void setSatcb(String satcb) {
        this.satcb = satcb;
    }

    public String getSatt() {
        return satt;
    }

    public void setSatt(String satt) {
        this.satt = satt;
    }
}
