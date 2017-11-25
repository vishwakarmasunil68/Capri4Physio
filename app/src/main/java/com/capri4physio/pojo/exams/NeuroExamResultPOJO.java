package com.capri4physio.pojo.exams;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 29-09-2017.
 */

public class NeuroExamResultPOJO implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("date")
    private String date;
    @SerializedName("gcseye")
    private String gcseye;
    @SerializedName("gcsverbal")
    private String gcsverbal;
    @SerializedName("gcsmotor")
    private String gcsmotor;
    @SerializedName("ndtulnarleft")
    private String ndtulnarleft;
    @SerializedName("ndtulnarright")
    private String ndtulnarright;
    @SerializedName("ndtradialleft")
    private String ndtradialleft;
    @SerializedName("ndtradialright")
    private String ndtradialright;
    @SerializedName("ndtmedianleft")
    private String ndtmedianleft;
    @SerializedName("nedtmedianright")
    private String nedtmedianright;
    @SerializedName("ndtmuscleft")
    private String ndtmuscleft;
    @SerializedName("ndtmusright")
    private String ndtmusright;
    @SerializedName("ndtsciaticleft")
    private String ndtsciaticleft;
    @SerializedName("ndtsciaticright")
    private String ndtsciaticright;
    @SerializedName("ndttibialleft")
    private String ndttibialleft;
    @SerializedName("ndttibialright")
    private String ndttibialright;
    @SerializedName("ndtcpleft")
    private String ndtcpleft;
    @SerializedName("ndtcpright")
    private String ndtcpright;
    @SerializedName("ndtfemoralleft")
    private String ndtfemoralleft;
    @SerializedName("ndtfemoralright")
    private String ndtfemoralright;
    @SerializedName("ndtcutaneousleft")
    private String ndtcutaneousleft;
    @SerializedName("ndtcutaneousright")
    private String ndtcutaneousright;
    @SerializedName("ndtobturator")
    private String ndtobturator;
    @SerializedName("ndtsuralleft")
    private String ndtsuralleft;
    @SerializedName("ndtsuralright")
    private String ndtsuralright;
    @SerializedName("ndtsaphenousleft")
    private String ndtsaphenousleft;
    @SerializedName("ndtsaphneousright")
    private String ndtsaphneousright;
    @SerializedName("st")
    private String st;
    @SerializedName("stdescription")
    private String stdescription;
    @SerializedName("adlname")
    private String adlname;
    @SerializedName("adldescription")
    private String adldescription;
    @SerializedName("ntpulnarleft")
    private String ntpulnarleft;
    @SerializedName("ntpulnarright")
    private String ntpulnarright;
    @SerializedName("ntpradialleft")
    private String ntpradialleft;
    @SerializedName("ntpradialright")
    private String ntpradialright;
    @SerializedName("ntpmedianleft")
    private String ntpmedianleft;
    @SerializedName("ntpmedianright")
    private String ntpmedianright;
    @SerializedName("ntpsciaticleft")
    private String ntpsciaticleft;
    @SerializedName("ntpsciaticright")
    private String ntpsciaticright;
    @SerializedName("ntptibialleft")
    private String ntptibialleft;
    @SerializedName("ntptibialright")
    private String ntptibialright;
    @SerializedName("ntpperonialleft")
    private String ntpperonialleft;
    @SerializedName("ntpperonialright")
    private String ntpperonialright;
    @SerializedName("ntpfemoralleft")
    private String ntpfemoralleft;
    @SerializedName("ntpfemoralright")
    private String ntpfemoralright;
    @SerializedName("ntpsuralleft")
    private String ntpsuralleft;
    @SerializedName("ntpsuralright")
    private String ntpsuralright;
    @SerializedName("ntpsaphenousleft")
    private String ntpsaphenousleft;
    @SerializedName("ntpsaphenousright")
    private String ntpsaphenousright;
    @SerializedName("ctfntimetaken")
    private String ctfntimetaken;
    @SerializedName("ctfnspeed")
    private String ctfnspeed;
    @SerializedName("ctfnnoerr")
    private String ctfnnoerr;
    @SerializedName("cthstimetaken")
    private String cthstimetaken;
    @SerializedName("cthsspeed")
    private String cthsspeed;
    @SerializedName("cthsnoerr")
    private String cthsnoerr;
    @SerializedName("gaillevel")
    private String gaillevel;
    @SerializedName("gaitspeed")
    private String gaitspeed;
    @SerializedName("gaithorizontalhead")
    private String gaithorizontalhead;
    @SerializedName("gaitveticalhead")
    private String gaitveticalhead;
    @SerializedName("stepoverobstacle")
    private String stepoverobstacle;
    @SerializedName("sterparoundobstacle")
    private String sterparoundobstacle;
    @SerializedName("steps")
    private String steps;
    @SerializedName("balanceandmovement")
    private String balanceandmovement;
    @SerializedName("bowel")
    private String bowel;
    @SerializedName("bladder")
    private String bladder;
    @SerializedName("gromming")
    private String gromming;
    @SerializedName("toilet")
    private String toilet;
    @SerializedName("feeding")
    private String feeding;
    @SerializedName("transfer")
    private String transfer;
    @SerializedName("mobility")
    private String mobility;
    @SerializedName("dressing")
    private String dressing;
    @SerializedName("stairs")
    private String stairs;
    @SerializedName("bathing")
    private String bathing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGcseye() {
        return gcseye;
    }

    public void setGcseye(String gcseye) {
        this.gcseye = gcseye;
    }

    public String getGcsverbal() {
        return gcsverbal;
    }

    public void setGcsverbal(String gcsverbal) {
        this.gcsverbal = gcsverbal;
    }

    public String getGcsmotor() {
        return gcsmotor;
    }

    public void setGcsmotor(String gcsmotor) {
        this.gcsmotor = gcsmotor;
    }

    public String getNdtulnarleft() {
        return ndtulnarleft;
    }

    public void setNdtulnarleft(String ndtulnarleft) {
        this.ndtulnarleft = ndtulnarleft;
    }

    public String getNdtulnarright() {
        return ndtulnarright;
    }

    public void setNdtulnarright(String ndtulnarright) {
        this.ndtulnarright = ndtulnarright;
    }

    public String getNdtradialleft() {
        return ndtradialleft;
    }

    public void setNdtradialleft(String ndtradialleft) {
        this.ndtradialleft = ndtradialleft;
    }

    public String getNdtradialright() {
        return ndtradialright;
    }

    public void setNdtradialright(String ndtradialright) {
        this.ndtradialright = ndtradialright;
    }

    public String getNdtmedianleft() {
        return ndtmedianleft;
    }

    public void setNdtmedianleft(String ndtmedianleft) {
        this.ndtmedianleft = ndtmedianleft;
    }

    public String getNedtmedianright() {
        return nedtmedianright;
    }

    public void setNedtmedianright(String nedtmedianright) {
        this.nedtmedianright = nedtmedianright;
    }

    public String getNdtmuscleft() {
        return ndtmuscleft;
    }

    public void setNdtmuscleft(String ndtmuscleft) {
        this.ndtmuscleft = ndtmuscleft;
    }

    public String getNdtmusright() {
        return ndtmusright;
    }

    public void setNdtmusright(String ndtmusright) {
        this.ndtmusright = ndtmusright;
    }

    public String getNdtsciaticleft() {
        return ndtsciaticleft;
    }

    public void setNdtsciaticleft(String ndtsciaticleft) {
        this.ndtsciaticleft = ndtsciaticleft;
    }

    public String getNdtsciaticright() {
        return ndtsciaticright;
    }

    public void setNdtsciaticright(String ndtsciaticright) {
        this.ndtsciaticright = ndtsciaticright;
    }

    public String getNdttibialleft() {
        return ndttibialleft;
    }

    public void setNdttibialleft(String ndttibialleft) {
        this.ndttibialleft = ndttibialleft;
    }

    public String getNdttibialright() {
        return ndttibialright;
    }

    public void setNdttibialright(String ndttibialright) {
        this.ndttibialright = ndttibialright;
    }

    public String getNdtcpleft() {
        return ndtcpleft;
    }

    public void setNdtcpleft(String ndtcpleft) {
        this.ndtcpleft = ndtcpleft;
    }

    public String getNdtcpright() {
        return ndtcpright;
    }

    public void setNdtcpright(String ndtcpright) {
        this.ndtcpright = ndtcpright;
    }

    public String getNdtfemoralleft() {
        return ndtfemoralleft;
    }

    public void setNdtfemoralleft(String ndtfemoralleft) {
        this.ndtfemoralleft = ndtfemoralleft;
    }

    public String getNdtfemoralright() {
        return ndtfemoralright;
    }

    public void setNdtfemoralright(String ndtfemoralright) {
        this.ndtfemoralright = ndtfemoralright;
    }

    public String getNdtcutaneousleft() {
        return ndtcutaneousleft;
    }

    public void setNdtcutaneousleft(String ndtcutaneousleft) {
        this.ndtcutaneousleft = ndtcutaneousleft;
    }

    public String getNdtcutaneousright() {
        return ndtcutaneousright;
    }

    public void setNdtcutaneousright(String ndtcutaneousright) {
        this.ndtcutaneousright = ndtcutaneousright;
    }

    public String getNdtobturator() {
        return ndtobturator;
    }

    public void setNdtobturator(String ndtobturator) {
        this.ndtobturator = ndtobturator;
    }

    public String getNdtsuralleft() {
        return ndtsuralleft;
    }

    public void setNdtsuralleft(String ndtsuralleft) {
        this.ndtsuralleft = ndtsuralleft;
    }

    public String getNdtsuralright() {
        return ndtsuralright;
    }

    public void setNdtsuralright(String ndtsuralright) {
        this.ndtsuralright = ndtsuralright;
    }

    public String getNdtsaphenousleft() {
        return ndtsaphenousleft;
    }

    public void setNdtsaphenousleft(String ndtsaphenousleft) {
        this.ndtsaphenousleft = ndtsaphenousleft;
    }

    public String getNdtsaphneousright() {
        return ndtsaphneousright;
    }

    public void setNdtsaphneousright(String ndtsaphneousright) {
        this.ndtsaphneousright = ndtsaphneousright;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getStdescription() {
        return stdescription;
    }

    public void setStdescription(String stdescription) {
        this.stdescription = stdescription;
    }

    public String getAdlname() {
        return adlname;
    }

    public void setAdlname(String adlname) {
        this.adlname = adlname;
    }

    public String getAdldescription() {
        return adldescription;
    }

    public void setAdldescription(String adldescription) {
        this.adldescription = adldescription;
    }

    public String getNtpulnarleft() {
        return ntpulnarleft;
    }

    public void setNtpulnarleft(String ntpulnarleft) {
        this.ntpulnarleft = ntpulnarleft;
    }

    public String getNtpulnarright() {
        return ntpulnarright;
    }

    public void setNtpulnarright(String ntpulnarright) {
        this.ntpulnarright = ntpulnarright;
    }

    public String getNtpradialleft() {
        return ntpradialleft;
    }

    public void setNtpradialleft(String ntpradialleft) {
        this.ntpradialleft = ntpradialleft;
    }

    public String getNtpradialright() {
        return ntpradialright;
    }

    public void setNtpradialright(String ntpradialright) {
        this.ntpradialright = ntpradialright;
    }

    public String getNtpmedianleft() {
        return ntpmedianleft;
    }

    public void setNtpmedianleft(String ntpmedianleft) {
        this.ntpmedianleft = ntpmedianleft;
    }

    public String getNtpmedianright() {
        return ntpmedianright;
    }

    public void setNtpmedianright(String ntpmedianright) {
        this.ntpmedianright = ntpmedianright;
    }

    public String getNtpsciaticleft() {
        return ntpsciaticleft;
    }

    public void setNtpsciaticleft(String ntpsciaticleft) {
        this.ntpsciaticleft = ntpsciaticleft;
    }

    public String getNtpsciaticright() {
        return ntpsciaticright;
    }

    public void setNtpsciaticright(String ntpsciaticright) {
        this.ntpsciaticright = ntpsciaticright;
    }

    public String getNtptibialleft() {
        return ntptibialleft;
    }

    public void setNtptibialleft(String ntptibialleft) {
        this.ntptibialleft = ntptibialleft;
    }

    public String getNtptibialright() {
        return ntptibialright;
    }

    public void setNtptibialright(String ntptibialright) {
        this.ntptibialright = ntptibialright;
    }

    public String getNtpperonialleft() {
        return ntpperonialleft;
    }

    public void setNtpperonialleft(String ntpperonialleft) {
        this.ntpperonialleft = ntpperonialleft;
    }

    public String getNtpperonialright() {
        return ntpperonialright;
    }

    public void setNtpperonialright(String ntpperonialright) {
        this.ntpperonialright = ntpperonialright;
    }

    public String getNtpfemoralleft() {
        return ntpfemoralleft;
    }

    public void setNtpfemoralleft(String ntpfemoralleft) {
        this.ntpfemoralleft = ntpfemoralleft;
    }

    public String getNtpfemoralright() {
        return ntpfemoralright;
    }

    public void setNtpfemoralright(String ntpfemoralright) {
        this.ntpfemoralright = ntpfemoralright;
    }

    public String getNtpsuralleft() {
        return ntpsuralleft;
    }

    public void setNtpsuralleft(String ntpsuralleft) {
        this.ntpsuralleft = ntpsuralleft;
    }

    public String getNtpsuralright() {
        return ntpsuralright;
    }

    public void setNtpsuralright(String ntpsuralright) {
        this.ntpsuralright = ntpsuralright;
    }

    public String getNtpsaphenousleft() {
        return ntpsaphenousleft;
    }

    public void setNtpsaphenousleft(String ntpsaphenousleft) {
        this.ntpsaphenousleft = ntpsaphenousleft;
    }

    public String getNtpsaphenousright() {
        return ntpsaphenousright;
    }

    public void setNtpsaphenousright(String ntpsaphenousright) {
        this.ntpsaphenousright = ntpsaphenousright;
    }

    public String getCtfntimetaken() {
        return ctfntimetaken;
    }

    public void setCtfntimetaken(String ctfntimetaken) {
        this.ctfntimetaken = ctfntimetaken;
    }

    public String getCtfnspeed() {
        return ctfnspeed;
    }

    public void setCtfnspeed(String ctfnspeed) {
        this.ctfnspeed = ctfnspeed;
    }

    public String getCtfnnoerr() {
        return ctfnnoerr;
    }

    public void setCtfnnoerr(String ctfnnoerr) {
        this.ctfnnoerr = ctfnnoerr;
    }

    public String getCthstimetaken() {
        return cthstimetaken;
    }

    public void setCthstimetaken(String cthstimetaken) {
        this.cthstimetaken = cthstimetaken;
    }

    public String getCthsspeed() {
        return cthsspeed;
    }

    public void setCthsspeed(String cthsspeed) {
        this.cthsspeed = cthsspeed;
    }

    public String getCthsnoerr() {
        return cthsnoerr;
    }

    public void setCthsnoerr(String cthsnoerr) {
        this.cthsnoerr = cthsnoerr;
    }

    public String getGaillevel() {
        return gaillevel;
    }

    public void setGaillevel(String gaillevel) {
        this.gaillevel = gaillevel;
    }

    public String getGaitspeed() {
        return gaitspeed;
    }

    public void setGaitspeed(String gaitspeed) {
        this.gaitspeed = gaitspeed;
    }

    public String getGaithorizontalhead() {
        return gaithorizontalhead;
    }

    public void setGaithorizontalhead(String gaithorizontalhead) {
        this.gaithorizontalhead = gaithorizontalhead;
    }

    public String getGaitveticalhead() {
        return gaitveticalhead;
    }

    public void setGaitveticalhead(String gaitveticalhead) {
        this.gaitveticalhead = gaitveticalhead;
    }

    public String getStepoverobstacle() {
        return stepoverobstacle;
    }

    public void setStepoverobstacle(String stepoverobstacle) {
        this.stepoverobstacle = stepoverobstacle;
    }

    public String getSterparoundobstacle() {
        return sterparoundobstacle;
    }

    public void setSterparoundobstacle(String sterparoundobstacle) {
        this.sterparoundobstacle = sterparoundobstacle;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getBalanceandmovement() {
        return balanceandmovement;
    }

    public void setBalanceandmovement(String balanceandmovement) {
        this.balanceandmovement = balanceandmovement;
    }

    public String getBowel() {
        return bowel;
    }

    public void setBowel(String bowel) {
        this.bowel = bowel;
    }

    public String getBladder() {
        return bladder;
    }

    public void setBladder(String bladder) {
        this.bladder = bladder;
    }

    public String getGromming() {
        return gromming;
    }

    public void setGromming(String gromming) {
        this.gromming = gromming;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getFeeding() {
        return feeding;
    }

    public void setFeeding(String feeding) {
        this.feeding = feeding;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public String getDressing() {
        return dressing;
    }

    public void setDressing(String dressing) {
        this.dressing = dressing;
    }

    public String getStairs() {
        return stairs;
    }

    public void setStairs(String stairs) {
        this.stairs = stairs;
    }

    public String getBathing() {
        return bathing;
    }

    public void setBathing(String bathing) {
        this.bathing = bathing;
    }
}
