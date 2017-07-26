package com.capri4physio.fragment.assessment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;

import java.util.ArrayList;

/**
 * Created by emobi5 on 4/15/2016.
 */
public class InfoPain {
    public String getPainSide() {
        return PainSide;
    }

    public void setPainSide(String PainSide) {
        PainSide = PainSide;
    }

    public String PainSide;

    public String getSeverityPain() {
        return SeverityPain;
    }

    public void setSeverityPain(String SeverityPain) {
        this.SeverityPain = SeverityPain;
    }

    public String SeverityPain;

    public String getPressurePain() {
        return PressurePain;
    }

    public void setPressurePain(String PressurePain) {
        PressurePain = PressurePain;
    }

    public String PressurePain;

    public String getThresholdSite() {
        return ThresholdSite;
    }

    public void setThresholdSite(String ThresholdSite) {
        ThresholdSite = ThresholdSite;
    }

    public String ThresholdSite;
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap image;

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String pc;
    public String getPainNature() {
        return PainNature;
    }

    public void setPainNature(String PainNature) {
        PainNature = PainNature;
    }

    public String PainNature;
    public String getPainOnset() {
        return PainOnset;
    }

    public void setPainOnset(String PainOnset) {
        PainOnset = PainOnset;
    }

    public String PainOnset;

    public String getPainDuration() {
        return PainDuration;
    }

    public void setPainDuration(String PainDuration) {
        this.PainDuration = PainDuration;
    }

    public String PainDuration;

    public String getDiurnalVariation() {
        return DiurnalVariation;
    }

    public void setDiurnalVariation(String DiurnalVariation) {
        this.DiurnalVariation = DiurnalVariation;
    }

    public String DiurnalVariation;

    public String getTriggerPoint() {
        return TriggerPoint;
    }

    public void setTriggerPoint(String TriggerPoint) {
        this.TriggerPoint = TriggerPoint;
    }

    public String TriggerPoint;
    public String getDataAdd() {
        return DataAdd;
    }

    public void setDataAdd(String dataAdd) {
        DataAdd = dataAdd;
    }

    public void setData(String data) {
        Data = data;
    }

    private String DataAdd;
    public String Data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;
    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getAggrevationFactors() {
        return AggrevationFactors;
    }

    public void setAggrevationFactors(String aggrevationFactors) {
        AggrevationFactors = aggrevationFactors;
    }

    public String AggrevationFactors;

    public String getRelieivingFactors() {
        return RelieivingFactors;
    }

    public void setRelieivingFactors(String relieivingFactors) {
        RelieivingFactors = relieivingFactors;
    }

    public String RelieivingFactors;
    public String exp;
    public String getStr4() {
        return str4;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String str4;

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String doctorid;
    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String patientid;
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String number;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int time;
    public String getAppname() {
        return Appname;
    }

    public void setAppname(String appname) {
        Appname = appname;
    }

    private String Appname;

    public Bitmap getAppbmp() {
        return Appbmp;
    }

    public void setAppbmp(Bitmap appbmp) {
        Appbmp = appbmp;
    }

    public Bitmap Appbmp;

    public StringBuilder getData() {
        return data;
    }

    public void setData(StringBuilder data) {
        this.data = data;
    }

    private StringBuilder data;

    public Address getDataadd() {
        return dataadd;
    }

    public void setDataadd(Address dataadd) {
        this.dataadd = dataadd;
    }

    private Address dataadd;

    public ArrayList<Address> getArraylisAddr() {
        return arraylisAddr;
    }

    public void setArraylisAddr(ArrayList<Address> arraylisAddr) {
        this.arraylisAddr = arraylisAddr;
    }

    private ArrayList<Address> arraylisAddr;
    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    private Drawable appicon;

    public String getPackPainOnsetnameApps() {
        return PackPainOnsetnameApps;
    }

    public void setPackPainOnsetnameApps(String packPainOnsetnameApps) {
        PackPainOnsetnameApps = packPainOnsetnameApps;
    }

    private String PackPainOnsetnameApps;
}
