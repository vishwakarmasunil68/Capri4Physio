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

public class SensoryMyotomeFragment extends Fragment {

    @BindView(R.id.rb_normal_c2)
    RadioButton rb_normal_c2;
    @BindView(R.id.rb_weak_c2)
    RadioButton rb_weak_c2;
    @BindView(R.id.rb_normal_c3)
    RadioButton rb_normal_c3;
    @BindView(R.id.rb_weak_c3)
    RadioButton rb_weak_c3;
    @BindView(R.id.rb_normal_c4)
    RadioButton rb_normal_c4;
    @BindView(R.id.rb_weak_c4)
    RadioButton rb_weak_c4;
    @BindView(R.id.rb_normal_c5)
    RadioButton rb_normal_c5;
    @BindView(R.id.rb_weak_c5)
    RadioButton rb_weak_c5;
    @BindView(R.id.rb_normal_c6)
    RadioButton rb_normal_c6;
    @BindView(R.id.rb_weak_c6)
    RadioButton rb_weak_c6;
    @BindView(R.id.rb_normal_c7)
    RadioButton rb_normal_c7;
    @BindView(R.id.rb_weak_c7)
    RadioButton rb_weak_c7;
    @BindView(R.id.rb_normal_c8)
    RadioButton rb_normal_c8;
    @BindView(R.id.rb_weak_c8)
    RadioButton rb_weak_c8;
    @BindView(R.id.rb_normal_l2)
    RadioButton rb_normal_l2;
    @BindView(R.id.rb_weak_l2)
    RadioButton rb_weak_l2;
    @BindView(R.id.rb_normal_l3)
    RadioButton rb_normal_l3;
    @BindView(R.id.rb_weak_l3)
    RadioButton rb_weak_l3;
    @BindView(R.id.rb_normal_l4)
    RadioButton rb_normal_l4;
    @BindView(R.id.rb_weak_l4)
    RadioButton rb_weak_l4;
    @BindView(R.id.rb_normal_l5)
    RadioButton rb_normal_l5;
    @BindView(R.id.rb_weak_l5)
    RadioButton rb_weak_l5;
    @BindView(R.id.rb_normal_s1)
    RadioButton rb_normal_s1;
    @BindView(R.id.rb_weak_s1)
    RadioButton rb_weak_s1;
    @BindView(R.id.rb_normal_s2)
    RadioButton rb_normal_s2;
    @BindView(R.id.rb_weak_s2)
    RadioButton rb_weak_s2;
    @BindView(R.id.rb_normal_s5)
    RadioButton rb_normal_s5;
    @BindView(R.id.rb_weak_s5)
    RadioButton rb_weak_s5;

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
    boolean is_update = false;

    SensoryMyotomeFragment(SensoryExam sensoryExam, boolean is_update) {
        this.sensoryExam = sensoryExam;
        this.is_update = is_update;
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sensory_myotome, container, false);
        this.view = view;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (is_update) {
            setValues();
        }
    }

    public void setValues() {
        setRadioValues(rb_normal_c2, rb_weak_c2, sensoryExam.getSensoryNeckMusc());
        setRadioValues(rb_normal_c3, rb_weak_c3, sensoryExam.getSensoryNeckLateral());
        setRadioValues(rb_normal_c4, rb_weak_c4, sensoryExam.getSensoryShoulderElevation());
        setRadioValues(rb_normal_c5, rb_weak_c5, sensoryExam.getSensoryShoulderAbduction());
        setRadioValues(rb_normal_c6, rb_weak_c6, sensoryExam.getSensoryBicepsSupinWrist());
        setRadioValues(rb_normal_c7, rb_weak_c7, sensoryExam.getSensoryWristFlex());
        setRadioValues(rb_normal_c8, rb_weak_c8, sensoryExam.getSensoryThumbExtensors());
        setRadioValues(rb_normal_l2, rb_weak_l2, sensoryExam.getSensoryHipFlexion());
        setRadioValues(rb_normal_l3, rb_weak_l3, sensoryExam.getSensoryKneeExtension());
        setRadioValues(rb_normal_l4, rb_weak_l4, sensoryExam.getSensoryAnkleDorsi());
        setRadioValues(rb_normal_l5, rb_weak_l5, sensoryExam.getSensoryExtensor());
        setRadioValues(rb_normal_s1, rb_weak_s1, sensoryExam.getSensoryAnklePlantar());
        setRadioValues(rb_normal_s2, rb_weak_s2, sensoryExam.getSensoryKneeFlexion());
        setRadioValues(rb_normal_s5, rb_weak_s5, sensoryExam.getSensoryBladderRectum());
    }


    public String getSelectedRadioValue(RadioGroup radioGroup) {
        try {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
            return radioButton.getText().toString();
        } catch (Exception e) {
            return "";
        }
    }

    public void setRadioValues(RadioButton normal, RadioButton weak, String value) {
        if (value.equalsIgnoreCase("normal")) {
            normal.setChecked(true);
            weak.setChecked(false);
        } else if (value.equalsIgnoreCase("weak")) {
            normal.setChecked(false);
            weak.setChecked(true);
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