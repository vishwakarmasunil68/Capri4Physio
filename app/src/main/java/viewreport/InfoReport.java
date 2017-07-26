package viewreport;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;

import java.util.ArrayList;

/**
 * Created by emobi5 on 4/15/2016.
 */
public class InfoReport {
    public String getPainSide() {
        return PainSide;
    }

    public void setPainSide(String PainSide) {
        PainSide = PainSide;
    }

    public String PainSide;

    public String getPatient_name() {
        return Patient_name;
    }

    public void setPatient_name(String patient_name) {
        Patient_name = patient_name;
    }

    public String Patient_name;

    public String getPro_pic() {
        return Pro_pic;
    }

    public void setPro_pic(String pro_pic) {
        Pro_pic = pro_pic;
    }

    public String Pro_pic;
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String Email;

    public String getSensory_shoulder_elevation() {
        return sensory_shoulder_elevation;
    }

    public void setSensory_shoulder_elevation(String sensory_shoulder_elevation) {
        this.sensory_shoulder_elevation = sensory_shoulder_elevation;
    }

    public String getSensory_ante_fossa() {
        return sensory_ante_fossa;
    }

    public void setSensory_ante_fossa(String sensory_ante_fossa) {
        this.sensory_ante_fossa = sensory_ante_fossa;
    }

    public String sensory_ante_fossa;
    public String sensory_shoulder_abduction;
    public String sensory_umbilicus;

    public String getSensory_shoulder_abduction() {
        return sensory_shoulder_abduction;
    }

    public void setSensory_shoulder_abduction(String sensory_shoulder_abduction) {
        this.sensory_shoulder_abduction = sensory_shoulder_abduction;
    }

    public String getSensory_umbilicus() {
        return sensory_umbilicus;
    }

    public void setSensory_umbilicus(String sensory_umbilicus) {
        this.sensory_umbilicus = sensory_umbilicus;
    }

    public String getSensory_hip_flexion() {
        return sensory_hip_flexion;
    }

    public void setSensory_hip_flexion(String sensory_hip_flexion) {
        this.sensory_hip_flexion = sensory_hip_flexion;
    }

    public String sensory_hip_flexion;
    public String sensory_shoulder_elevation;

    public String getSensory_thumb() {
        return sensory_thumb;
    }

    public void setSensory_thumb(String sensory_thumb) {
        this.sensory_thumb = sensory_thumb;
    }

    public String getSensory_biceps_supin_wrist() {
        return sensory_biceps_supin_wrist;
    }

    public void setSensory_biceps_supin_wrist(String sensory_biceps_supin_wrist) {
        this.sensory_biceps_supin_wrist = sensory_biceps_supin_wrist;
    }

    public String getSensory_biceps_brachi1() {
        return sensory_biceps_brachi1;
    }

    public void setSensory_biceps_brachi1(String sensory_biceps_brachi1) {
        this.sensory_biceps_brachi1 = sensory_biceps_brachi1;
    }

    public String getSensory_middle_finger() {
        return sensory_middle_finger;
    }

    public void setSensory_middle_finger(String sensory_middle_finger) {
        this.sensory_middle_finger = sensory_middle_finger;
    }

    public String getSensory_triceps() {
        return sensory_triceps;
    }

    public void setSensory_triceps(String sensory_triceps) {
        this.sensory_triceps = sensory_triceps;
    }

    public String getSensory_wrist_flex() {
        return sensory_wrist_flex;
    }

    public void setSensory_wrist_flex(String sensory_wrist_flex) {
        this.sensory_wrist_flex = sensory_wrist_flex;
    }

    public String getSensory_little_finger() {
        return sensory_little_finger;
    }

    public void setSensory_little_finger(String sensory_little_finger) {
        this.sensory_little_finger = sensory_little_finger;
    }

    public String getSensory_thumb_extensors() {
        return sensory_thumb_extensors;
    }

    public void setSensory_thumb_extensors(String sensory_thumb_extensors) {
        this.sensory_thumb_extensors = sensory_thumb_extensors;
    }

    public String getSensory_triceps1() {
        return sensory_triceps1;
    }

    public void setSensory_triceps1(String sensory_triceps1) {
        this.sensory_triceps1 = sensory_triceps1;
    }

    public String getSensory_medial_side() {
        return sensory_medial_side;
    }

    public void setSensory_medial_side(String sensory_medial_side) {
        this.sensory_medial_side = sensory_medial_side;
    }

    public String getSensory_apexaxilla() {
        return sensory_apexaxilla;
    }

    public void setSensory_apexaxilla(String sensory_apexaxilla) {
        this.sensory_apexaxilla = sensory_apexaxilla;
    }

    public String getSensory_nipples() {
        return sensory_nipples;
    }

    public void setSensory_nipples(String sensory_nipples) {
        this.sensory_nipples = sensory_nipples;
    }

    public String getSensory_xiphisternum() {
        return sensory_xiphisternum;
    }

    public void setSensory_xiphisternum(String sensory_xiphisternum) {
        this.sensory_xiphisternum = sensory_xiphisternum;
    }

    public String getSensory_midpoint_inguinal() {
        return sensory_midpoint_inguinal;
    }

    public void setSensory_midpoint_inguinal(String sensory_midpoint_inguinal) {
        this.sensory_midpoint_inguinal = sensory_midpoint_inguinal;
    }

    public String getSensory_mid_anterior() {
        return sensory_mid_anterior;
    }

    public void setSensory_mid_anterior(String sensory_mid_anterior) {
        this.sensory_mid_anterior = sensory_mid_anterior;
    }

    public String getSensory_patellar() {
        return sensory_patellar;
    }

    public void setSensory_patellar(String sensory_patellar) {
        this.sensory_patellar = sensory_patellar;
    }

    public String getSensory_medial_epicondyle() {
        return sensory_medial_epicondyle;
    }

    public void setSensory_medial_epicondyle(String sensory_medial_epicondyle) {
        this.sensory_medial_epicondyle = sensory_medial_epicondyle;
    }

    public String getSensory_knee_extension() {
        return sensory_knee_extension;
    }

    public void setSensory_knee_extension(String sensory_knee_extension) {
        this.sensory_knee_extension = sensory_knee_extension;
    }

    public String getSensory_painslr() {
        return sensory_painslr;
    }

    public void setSensory_painslr(String sensory_painslr) {
        this.sensory_painslr = sensory_painslr;
    }

    public String getSensory_medial_malleolus() {
        return sensory_medial_malleolus;
    }

    public void setSensory_medial_malleolus(String sensory_medial_malleolus) {
        this.sensory_medial_malleolus = sensory_medial_malleolus;
    }

    public String getSensory_ankle_dorsi() {
        return sensory_ankle_dorsi;
    }

    public void setSensory_ankle_dorsi(String sensory_ankle_dorsi) {
        this.sensory_ankle_dorsi = sensory_ankle_dorsi;
    }

    public String getSensory_dorsum_foot() {
        return sensory_dorsum_foot;
    }

    public void setSensory_dorsum_foot(String sensory_dorsum_foot) {
        this.sensory_dorsum_foot = sensory_dorsum_foot;
    }

    public String getSensory_extensor() {
        return sensory_extensor;
    }

    public void setSensory_extensor(String sensory_extensor) {
        this.sensory_extensor = sensory_extensor;
    }

    public String getSensory_Lateral_heel() {
        return sensory_Lateral_heel;
    }

    public void setSensory_Lateral_heel(String sensory_Lateral_heel) {
        this.sensory_Lateral_heel = sensory_Lateral_heel;
    }

    public String getSensory_ankle_plantar() {
        return sensory_ankle_plantar;
    }

    public void setSensory_ankle_plantar(String sensory_ankle_plantar) {
        this.sensory_ankle_plantar = sensory_ankle_plantar;
    }

    public String getSensory_limitedslr() {
        return sensory_limitedslr;
    }

    public void setSensory_limitedslr(String sensory_limitedslr) {
        this.sensory_limitedslr = sensory_limitedslr;
    }

    public String getSensory_popliteal_fossa() {
        return sensory_popliteal_fossa;
    }

    public void setSensory_popliteal_fossa(String sensory_popliteal_fossa) {
        this.sensory_popliteal_fossa = sensory_popliteal_fossa;
    }

    public String getSensory_knee_flexion() {
        return sensory_knee_flexion;
    }

    public void setSensory_knee_flexion(String sensory_knee_flexion) {
        this.sensory_knee_flexion = sensory_knee_flexion;
    }

    public String getSensory_limitedslr_achilles() {
        return sensory_limitedslr_achilles;
    }

    public void setSensory_limitedslr_achilles(String sensory_limitedslr_achilles) {
        this.sensory_limitedslr_achilles = sensory_limitedslr_achilles;
    }

    public String getSensory_perianal() {
        return sensory_perianal;
    }

    public void setSensory_perianal(String sensory_perianal) {
        this.sensory_perianal = sensory_perianal;
    }

    public String getSensory_bladder_rectum() {
        return sensory_bladder_rectum;
    }

    public void setSensory_bladder_rectum(String sensory_bladder_rectum) {
        this.sensory_bladder_rectum = sensory_bladder_rectum;
    }

    public String sensory_thumb;
    public String sensory_biceps_supin_wrist;
    public String sensory_biceps_brachi1;
    public String sensory_middle_finger;
    public String sensory_triceps;
    public String sensory_wrist_flex;
    public String sensory_little_finger;
    public String sensory_thumb_extensors;
    public String sensory_triceps1;
    public String sensory_medial_side;
    public String sensory_apexaxilla;
    public String sensory_nipples;
    public String sensory_xiphisternum;
    public String sensory_midpoint_inguinal;
    public String sensory_mid_anterior;
    public String sensory_patellar;
    public String sensory_medial_epicondyle;
    public String sensory_knee_extension;
    public String sensory_painslr;
    public String sensory_medial_malleolus;
    public String sensory_ankle_dorsi;
    public String sensory_dorsum_foot;
    public String sensory_extensor;
    public String sensory_Lateral_heel;
    public String sensory_ankle_plantar;
    public String sensory_limitedslr;
    public String sensory_popliteal_fossa;
    public String sensory_knee_flexion;
    public String sensory_limitedslr_achilles;
    public String sensory_perianal;
    public String sensory_bladder_rectum;

    public String getSensory_biceps_brachi() {
        return sensory_biceps_brachi;
    }

    public void setSensory_biceps_brachi(String sensory_biceps_brachi) {
        this.sensory_biceps_brachi = sensory_biceps_brachi;
    }

    public String sensory_biceps_brachi;
    public String getSensory_neck_lateral() {
        return sensory_neck_lateral;
    }

    public String getSensory_acrom_joint() {
        return sensory_acrom_joint;
    }

    public void setSensory_acrom_joint(String sensory_acrom_joint) {
        this.sensory_acrom_joint = sensory_acrom_joint;
    }

    public String sensory_acrom_joint;
    public void setSensory_neck_lateral(String sensory_neck_lateral) {
        this.sensory_neck_lateral = sensory_neck_lateral;
    }

    public String sensory_neck_lateral;
    public String getSensory_supra_fossa() {
        return sensory_supra_fossa;
    }

    public void setSensory_supra_fossa(String sensory_supra_fossa) {
        this.sensory_supra_fossa = sensory_supra_fossa;
    }

    public String sensory_supra_fossa;
    public String getSensory_neck_musc() {
        return sensory_neck_musc;
    }

    public void setSensory_neck_musc(String sensory_neck_musc) {
        this.sensory_neck_musc = sensory_neck_musc;
    }

    public String sensory_neck_musc;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;

    public String getOccipital() {
        return occipital;
    }

    public void setOccipital(String occipital) {
        this.occipital = occipital;
    }

    public String occipital;
    public String getTreatment_type() {
        return Treatment_type;
    }

    public void setTreatment_type(String treatment_type) {
        Treatment_type = treatment_type;
    }

    public String Treatment_type;
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
