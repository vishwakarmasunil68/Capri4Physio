package com.capri4physio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.capri4physio.R;
import com.capri4physio.pojo.exams.SensoryExam;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 28-09-2017.
 */

public class SensoryDermatomeFragment extends Fragment {

    @BindView(R.id.rbc2impaired)
    RadioButton rbc2impaired;
    @BindView(R.id.rbc2intact)
    RadioButton rbc2intact;
    @BindView(R.id.rbc3intact)
    RadioButton rbc3intact;
    @BindView(R.id.rbc3impaired)
    RadioButton rbc3impaired;
    @BindView(R.id.rbc4intact)
    RadioButton rbc4intact;
    @BindView(R.id.rbc4impaired)
    RadioButton rbc4impaired;
    @BindView(R.id.rbc5intact)
    RadioButton rbc5intact;
    @BindView(R.id.rbc5impaired)
    RadioButton rbc5impaired;
    @BindView(R.id.rbc6intact)
    RadioButton rbc6intact;
    @BindView(R.id.rbc6impaired)
    RadioButton rbc6impaired;
    @BindView(R.id.rbc7intact)
    RadioButton rbc7intact;
    @BindView(R.id.rbc7impaired)
    RadioButton rbc7impaired;
    @BindView(R.id.rbc8intact)
    RadioButton rbc8intact;
    @BindView(R.id.rbc8impaired)
    RadioButton rbc8impaired;
    @BindView(R.id.rbt1intact)
    RadioButton rbt1intact;
    @BindView(R.id.rbt1impaired)
    RadioButton rbt1impaired;
    @BindView(R.id.rbt2intact)
    RadioButton rbt2intact;
    @BindView(R.id.rbt2impaired)
    RadioButton rbt2impaired;
    @BindView(R.id.rbt4intact)
    RadioButton rbt4intact;
    @BindView(R.id.rbt4impaired)
    RadioButton rbt4impaired;
    @BindView(R.id.rbt6intact)
    RadioButton rbt6intact;
    @BindView(R.id.rbt6impaired)
    RadioButton rbt6impaired;
    @BindView(R.id.rbt10intact)
    RadioButton rbt10intact;
    @BindView(R.id.rbt10impaired)
    RadioButton rbt10impaired;
    @BindView(R.id.rbt12intact)
    RadioButton rbt12intact;
    @BindView(R.id.rbt12impaired)
    RadioButton rbt12impaired;
    @BindView(R.id.rbl2intact)
    RadioButton rbl2intact;
    @BindView(R.id.rbl2impaired)
    RadioButton rbl2impaired;
    @BindView(R.id.rbl3intact)
    RadioButton rbl3intact;
    @BindView(R.id.rbl3impaired)
    RadioButton rbl3impaired;
    @BindView(R.id.rbl4intact)
    RadioButton rbl4intact;
    @BindView(R.id.rbl4impaired)
    RadioButton rbl4impaired;
    @BindView(R.id.rbl5intact)
    RadioButton rbl5intact;
    @BindView(R.id.rbl5impaired)
    RadioButton rbl5impaired;
    @BindView(R.id.rbs1intact)
    RadioButton rbs1intact;
    @BindView(R.id.rbs1impaired)
    RadioButton rbs1impaired;
    @BindView(R.id.rbs2intact)
    RadioButton rbs2intact;
    @BindView(R.id.rbs2impaired)
    RadioButton rbs2impaired;
    @BindView(R.id.rbs5intact)
    RadioButton rbs5intact;
    @BindView(R.id.rbs5impaired)
    RadioButton rbs5impaired;

    @BindView(R.id.rg_c2)
    RadioGroup rg_c2;
    @BindView(R.id.rg_c3)
    RadioGroup rg_c3;
    @BindView(R.id.rg_c4)
    RadioGroup rg_c4;
    @BindView(R.id.rg_c5)
    RadioGroup rg_c5;
    @BindView(R.id.rg_c6)
    RadioGroup rg_c6;
    @BindView(R.id.rg_c7)
    RadioGroup rg_c7;
    @BindView(R.id.rg_c8)
    RadioGroup rg_c8;
    @BindView(R.id.rg_t1)
    RadioGroup rg_t1;
    @BindView(R.id.rg_t2)
    RadioGroup rg_t2;
    @BindView(R.id.rg_t4)
    RadioGroup rg_t4;
    @BindView(R.id.rg_t6)
    RadioGroup rg_t6;
    @BindView(R.id.rg_t10)
    RadioGroup rg_t10;
    @BindView(R.id.rg_t12)
    RadioGroup rg_t12;
    @BindView(R.id.rg_l2)
    RadioGroup rg_l2;
    @BindView(R.id.rg_l3)
    RadioGroup rg_l3;
    @BindView(R.id.rg_l4)
    RadioGroup rg_l4;
    @BindView(R.id.rg_l5)
    RadioGroup rg_l5;
    @BindView(R.id.rg_s1)
    RadioGroup rg_s1;
    @BindView(R.id.rg_s2)
    RadioGroup rg_s2;
    @BindView(R.id.rg_s5)
    RadioGroup rg_s5;


    SensoryExam sensoryExam;
    boolean is_update=false;
    SensoryDermatomeFragment(SensoryExam sensoryExam,boolean is_update) {
        this.sensoryExam = sensoryExam;
        this.is_update=is_update;
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sensory_dermatome, container, false);
        this.view = view;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(is_update) {
            setValues();
        }
    }

    public void setValues() {
        setRadioValues(rbc2impaired, rbc2intact, sensoryExam.getSensoryOccipitalPort());
        setRadioValues(rbc3intact, rbc3impaired, sensoryExam.getSensorySupraFossa());
        setRadioValues(rbc4intact, rbc4impaired, sensoryExam.getSensoryAcromJoint());
        setRadioValues(rbc5intact, rbc5impaired, sensoryExam.getSensoryAnteFossa());
        setRadioValues(rbc6intact, rbc6impaired, sensoryExam.getSensoryThumb());
        setRadioValues(rbc7intact, rbc7impaired, sensoryExam.getSensoryMiddleFinger());
        setRadioValues(rbc8intact, rbc8impaired, sensoryExam.getSensoryLittleFinger());
        setRadioValues(rbt1intact, rbt1impaired, sensoryExam.getSensoryMedialSide());
        setRadioValues(rbt2intact, rbt2impaired, sensoryExam.getSensoryApexaxilla());
        setRadioValues(rbt4intact, rbt4impaired, sensoryExam.getSensoryNipples());
        setRadioValues(rbt6intact, rbt6impaired, sensoryExam.getSensoryXiphisternum());
        setRadioValues(rbt10intact, rbt10impaired, sensoryExam.getSensoryUmbilicus());
        setRadioValues(rbt12intact, rbt12impaired, sensoryExam.getSensoryMidpointInguinal());
        setRadioValues(rbl2intact, rbl2impaired, sensoryExam.getSensoryMidAnterior());
        setRadioValues(rbl3intact, rbl3impaired, sensoryExam.getSensoryMedialEpicondyle());
        setRadioValues(rbl4intact, rbl4impaired, sensoryExam.getSensoryMedialMalleolus());
        setRadioValues(rbl5intact, rbl5impaired, sensoryExam.getSensoryDorsumFoot());
        setRadioValues(rbs1intact, rbs1impaired, sensoryExam.getSensoryLateralHeel());
        setRadioValues(rbs2intact, rbs2impaired, sensoryExam.getSensoryPoplitealFossa());
        setRadioValues(rbs5intact, rbs5impaired, sensoryExam.getSensoryPerianal());
    }

    public String getSelectedRadioValue(RadioGroup radioGroup) {
        try {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
            return radioButton.getText().toString();
        }catch (Exception e){
            return "";
        }
    }

    public void setRadioValues(RadioButton radiointact, RadioButton radioimpaired, String value) {
        if (value.equalsIgnoreCase("intact")) {
            radiointact.setChecked(true);
        } else if (value.equalsIgnoreCase("Impaired")) {
            radioimpaired.setChecked(true);
        }
    }

    public String getRg_c2() {
        return getSelectedRadioValue(rg_c2);
    }

    public String getRg_c3() {
        return getSelectedRadioValue(rg_c3);
    }

    public String getRg_c4() {
        return getSelectedRadioValue(rg_c4);
    }

    public String getRg_c5() {
        return getSelectedRadioValue(rg_c5);
    }

    public String getRg_c6() {
        return getSelectedRadioValue(rg_c6);
    }

    public String getRg_c7() {
        return getSelectedRadioValue(rg_c7);
    }

    public String getRg_c8() {
        return getSelectedRadioValue(rg_c8);
    }

    public String getRg_t1() {
        return getSelectedRadioValue(rg_t1);
    }

    public String getRg_t2() {
        return getSelectedRadioValue(rg_t2);
    }

    public String getRg_t4() {
        return getSelectedRadioValue(rg_t4);
    }

    public String getRg_t6() {
        return getSelectedRadioValue(rg_t6);
    }

    public String getRg_t10() {
        return getSelectedRadioValue(rg_t10);
    }

    public String getRg_t12() {
        return getSelectedRadioValue(rg_t12);
    }

    public String getRg_l2() {
        return getSelectedRadioValue(rg_l2);
    }

    public String getRg_l3() {
        return getSelectedRadioValue(rg_l3);
    }

    public String getRg_l4() {
        return getSelectedRadioValue(rg_l4);
    }

    public String getRg_l5() {
        return getSelectedRadioValue(rg_l5);
    }

    public String getRg_s1() {
        return getSelectedRadioValue(rg_s1);
    }

    public String getRg_s2() {
        return getSelectedRadioValue(rg_s2);
    }

    public String getRg_s5() {
        return getSelectedRadioValue(rg_s5);
    }
}
