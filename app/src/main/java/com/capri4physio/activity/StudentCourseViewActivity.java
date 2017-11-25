package com.capri4physio.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capri4physio.R;
import com.capri4physio.Services.WebServiceBase;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.model.cources.CourcesResultPOJO;
import com.capri4physio.model.studentcourse.StudentCourseResultPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentCourseViewActivity extends AppCompatActivity implements View.OnClickListener, WebServicesCallBack {
    private static final String UPDATE_STUDENT = "update_student";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.et_s_name)
    EditText et_s_name;
    @BindView(R.id.et_s_email)
    EditText et_s_email;
    @BindView(R.id.iv_application_status)
    ImageView iv_application_status;
    @BindView(R.id.iv_photo_upload)
    ImageView iv_photo_upload;
    @BindView(R.id.iv_id_card_upload)
    ImageView iv_id_card_upload;
    @BindView(R.id.iv_registration_fees)
    ImageView iv_registration_fees;
    @BindView(R.id.iv_full_fees)
    ImageView iv_full_fees;
    @BindView(R.id.iv_certificatie_upload)
    ImageView iv_certificatie_upload;

    @BindView(R.id.iv_confirm_app)
    ImageView iv_confirm_app;
    @BindView(R.id.iv_confirm_photo)
    ImageView iv_confirm_photo;
    @BindView(R.id.iv_confirm_cert)
    ImageView iv_confirm_cert;
    @BindView(R.id.iv_confirm_id)
    ImageView iv_confirm_id;
    @BindView(R.id.iv_confirm_reg)
    ImageView iv_confirm_reg;
    @BindView(R.id.iv_confirm_full)
    ImageView iv_confirm_full;
    @BindView(R.id.iv_view_app)
    ImageView iv_view_app;
    @BindView(R.id.iv_view_photo)
    ImageView iv_view_photo;
    @BindView(R.id.iv_view_cert)
    ImageView iv_view_cert;
    @BindView(R.id.iv_view_id)
    ImageView iv_view_id;
    @BindView(R.id.iv_view_reg)
    ImageView iv_view_reg;
    @BindView(R.id.iv_view_full_fees)
    ImageView iv_view_full_fees;
    @BindView(R.id.iv_rem_fees)
    ImageView iv_rem_fees;
    @BindView(R.id.iv_rem_confirm)
    ImageView iv_rem_confirm;
    @BindView(R.id.iv_rem_view)
    ImageView iv_rem_view;

    @BindView(R.id.et_remark)
    EditText et_remark;

    StudentCourseResultPOJO studentCourseResultPOJO;
    CourcesResultPOJO courcesResultPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        studentCourseResultPOJO = (StudentCourseResultPOJO) getIntent().getSerializableExtra("studentcourse");
        courcesResultPOJO = (CourcesResultPOJO) getIntent().getSerializableExtra("coursepojo");

        if (studentCourseResultPOJO != null && courcesResultPOJO != null) {
            checkStatus();
        } else {
            finish();
        }

        iv_confirm_app.setOnClickListener(this);
        iv_confirm_photo.setOnClickListener(this);
        iv_confirm_cert.setOnClickListener(this);
        iv_confirm_id.setOnClickListener(this);
        iv_confirm_reg.setOnClickListener(this);
        iv_confirm_full.setOnClickListener(this);
        iv_rem_confirm.setOnClickListener(this);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_remark.getText().toString().length() > 0) {
                    studentCourseResultPOJO.setAdminStatus(et_remark.getText().toString());
                    updateStudent(false);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please write some remark");
                }
            }
        });
    }

    public void checkStatus() {
        if (studentCourseResultPOJO != null) {
            et_s_name.setText(studentCourseResultPOJO.getScSname());
            et_s_email.setText(studentCourseResultPOJO.getScSemail());
            if (studentCourseResultPOJO.getScSapplicationformFill().length() == 0) {
                iv_application_status.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_app.setVisibility(View.GONE);
                iv_view_app.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminApplicationForm().equals("false")) {
                    iv_application_status.setImageResource(R.drawable.ic_filled);
                    iv_confirm_app.setVisibility(View.VISIBLE);
                    iv_view_app.setVisibility(View.VISIBLE);
                } else {
                    iv_application_status.setImageResource(R.drawable.ic_approved);
                    iv_confirm_app.setVisibility(View.GONE);
                    iv_view_app.setVisibility(View.VISIBLE);
                }

                iv_view_app.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StudentCourseViewActivity.this, StudentCourseApplicationActivity.class);
                        intent.putExtra("studentcourseresultpojo", studentCourseResultPOJO);
                        startActivity(intent);
//                        Log.d(TagUtils.getTag(),"application course id:-"+studentCourseResultPOJO.getScCid());
//                        Log.d(TagUtils.getTag(),"application student id:-"+studentCourseResultPOJO.getScSid());

                    }
                });
            }

            if (studentCourseResultPOJO.getScPhotoUpload().length() == 0) {
                iv_photo_upload.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_photo.setVisibility(View.GONE);
                iv_view_photo.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminPhotoUpload().equals("false")) {
                    iv_photo_upload.setImageResource(R.drawable.ic_filled);
                    iv_confirm_photo.setVisibility(View.VISIBLE);
                    iv_view_photo.setVisibility(View.VISIBLE);
                } else {
                    iv_photo_upload.setImageResource(R.drawable.ic_approved);
                    iv_confirm_photo.setVisibility(View.GONE);
                    iv_view_photo.setVisibility(View.VISIBLE);
                }
                iv_view_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StudentCourseViewActivity.this, ImageViewActivity.class);
                        intent.putExtra("url", studentCourseResultPOJO.getScPhotoUpload());
                        startActivity(intent);
                    }
                });
            }

            if (studentCourseResultPOJO.getScCerifiatoUpload().length() == 0) {
                iv_certificatie_upload.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_cert.setVisibility(View.GONE);
                iv_view_cert.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminCertificateUpload().equals("false")) {
                    iv_certificatie_upload.setImageResource(R.drawable.ic_filled);
                    iv_confirm_cert.setVisibility(View.VISIBLE);
                    iv_view_cert.setVisibility(View.VISIBLE);
                } else {
                    iv_certificatie_upload.setImageResource(R.drawable.ic_approved);
                    iv_confirm_cert.setVisibility(View.GONE);
                    iv_view_cert.setVisibility(View.VISIBLE);
                }
                iv_view_cert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StudentCourseViewActivity.this, ImageViewActivity.class);
                        intent.putExtra("url", studentCourseResultPOJO.getScCerifiatoUpload());
                        startActivity(intent);
                    }
                });
            }

            if (studentCourseResultPOJO.getScIdcardUpload().length() == 0) {
                iv_id_card_upload.setImageResource(R.drawable.ic_not_filled);
                iv_confirm_id.setVisibility(View.GONE);
                iv_view_id.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminIcard().equals("false")) {
                    iv_id_card_upload.setImageResource(R.drawable.ic_filled);
                    iv_confirm_id.setVisibility(View.VISIBLE);
                    iv_view_id.setVisibility(View.VISIBLE);
                } else {
                    iv_id_card_upload.setImageResource(R.drawable.ic_approved);
                    iv_confirm_id.setVisibility(View.GONE);
                    iv_view_id.setVisibility(View.VISIBLE);
                }
                iv_view_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StudentCourseViewActivity.this, ImageViewActivity.class);
                        intent.putExtra("url", studentCourseResultPOJO.getScIdcardUpload());
                        startActivity(intent);
                    }
                });
            }
            checkRegistrationFeesStatus();
            checkRemainingFeesStatus();
            checkFullFeesStatus();

//            if (studentCourseResultPOJO.getAdminRegFees().equals("false")) {
//
//                if (studentCourseResultPOJO.getScRegFees().length() == 0) {
//                    iv_registration_fees.setImageResource(R.drawable.ic_not_filled);
//                    iv_confirm_reg.setVisibility(View.GONE);
//                } else {
//                    iv_registration_fees.setImageResource(R.drawable.ic_filled);
//                    iv_confirm_reg.setVisibility(View.VISIBLE);
//                }
//            } else {
//                iv_registration_fees.setImageResource(R.drawable.ic_approved);
//                iv_confirm_reg.setVisibility(View.GONE);
//            }
//
//
//            if (studentCourseResultPOJO.getScRemFees().length() == 0) {
//                iv_rem_fees.setImageResource(R.drawable.ic_not_filled);
//                iv_rem_confirm.setVisibility(View.GONE);
//            } else {
//                if (studentCourseResultPOJO.getSc_admin_rem_fees().equals("false")) {
//                    iv_rem_fees.setImageResource(R.drawable.ic_filled);
//                    iv_rem_confirm.setVisibility(View.VISIBLE);
//                } else {
//                    iv_rem_fees.setImageResource(R.drawable.ic_approved);
//                    iv_rem_confirm.setVisibility(View.GONE);
//                }
//
//            }
//
//            if (studentCourseResultPOJO.getScFullfees().length() == 0) {
//                iv_full_fees.setImageResource(R.drawable.ic_not_filled);
//                iv_confirm_full.setVisibility(View.GONE);
//            } else {
//                if (studentCourseResultPOJO.getAdminFullFees().equals("false")) {
//                    iv_full_fees.setImageResource(R.drawable.ic_filled);
//                    iv_confirm_full.setVisibility(View.VISIBLE);
//                } else {
//                    iv_full_fees.setImageResource(R.drawable.ic_approved);
//                    iv_confirm_full.setVisibility(View.GONE);
//                }
//
//            }
//
//
//            if (studentCourseResultPOJO.getScRegFees().length() > 0
//                    && studentCourseResultPOJO.getScRemFees().length() > 0
//                    && studentCourseResultPOJO.getSc_admin_rem_fees().equals("true")
//                    && studentCourseResultPOJO.getAdminRegFees().equals("true")
//                    ) {
//                iv_registration_fees.setImageResource(R.drawable.ic_approved);
//                iv_rem_fees.setImageResource(R.drawable.ic_approved);
//                iv_full_fees.setImageResource(R.drawable.ic_approved);
//
//                iv_confirm_reg.setVisibility(View.GONE);
//                iv_confirm_full.setVisibility(View.GONE);
//                iv_rem_confirm.setVisibility(View.GONE);
//            } else {
//                if (studentCourseResultPOJO.getScRegFees().length() > 0
//                        && studentCourseResultPOJO.getScRemFees().length() > 0
//                        && studentCourseResultPOJO.getSc_admin_rem_fees().equals("false")
//                        && studentCourseResultPOJO.getAdminRegFees().equals("false")
//                        ) {
//                    iv_registration_fees.setImageResource(R.drawable.ic_filled);
//                    iv_rem_fees.setImageResource(R.drawable.ic_filled);
//                    iv_full_fees.setImageResource(R.drawable.ic_filled);
//
//                    iv_confirm_reg.setVisibility(View.VISIBLE);
//                    iv_confirm_full.setVisibility(View.VISIBLE);
//                    iv_rem_confirm.setVisibility(View.VISIBLE);
//                } else {
//
//                }
//            }
//
//
//            if (studentCourseResultPOJO.getScFullfees().length() > 0
//                    && studentCourseResultPOJO.getAdminFullFees().equals("true")) {
//                iv_registration_fees.setImageResource(R.drawable.ic_approved);
//                iv_rem_fees.setImageResource(R.drawable.ic_approved);
//                iv_full_fees.setImageResource(R.drawable.ic_approved);
//
//                iv_confirm_reg.setVisibility(View.GONE);
//                iv_confirm_full.setVisibility(View.GONE);
//                iv_rem_confirm.setVisibility(View.GONE);
//            } else {
//                if (studentCourseResultPOJO.getScFullfees().length() > 0
//                        && studentCourseResultPOJO.getAdminFullFees().equals("false")) {
//                    iv_registration_fees.setImageResource(R.drawable.ic_filled);
//                    iv_rem_fees.setImageResource(R.drawable.ic_filled);
//                    iv_full_fees.setImageResource(R.drawable.ic_filled);
//
//                    iv_confirm_reg.setVisibility(View.VISIBLE);
//                    iv_confirm_full.setVisibility(View.VISIBLE);
//                    iv_rem_confirm.setVisibility(View.VISIBLE);
//                } else {
//
//                }
//            }


            iv_view_reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (studentCourseResultPOJO.getScFullfees().length() > 0) {
                        if (studentCourseResultPOJO.getScFullfees().equals("online")) {
                            showOnlineDialog("Full fees has been paid by Pay U Money");
                        } else {
                            showOnlineDialog("Full fees has been pain and transaction id is " + studentCourseResultPOJO.getScFullfees());
                        }
                    } else {
                        if (studentCourseResultPOJO.getScRegFees().length() > 0) {
                            if (studentCourseResultPOJO.getScRegFees().equals("online")) {
                                showOnlineDialog("Registration Fees has bees paid by Pay U Money");
                            } else {
                                showOnlineDialog("Registration Fees has bees paid and transaction id is " + studentCourseResultPOJO.getScRegFees());
                            }
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), "Registration fees has not been submitted");
                        }
                    }
                }
            });


            iv_view_full_fees.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (studentCourseResultPOJO.getScFullfees().length() > 0) {
                        if (studentCourseResultPOJO.getScFullfees().equals("online")) {
                            showOnlineDialog("Full Fees has bees paid by Pay U Money");
                        } else {
                            showOnlineDialog("Full Fees has bees paid and transaction id is " + studentCourseResultPOJO.getScFullfees());
                        }
                    } else {
                        if (studentCourseResultPOJO.getScRegFees().length() > 0
                                && studentCourseResultPOJO.getScRemFees().length() > 0) {
                            String reg_fees_status = "";
                            String rem_fees_status = "";
                            if (studentCourseResultPOJO.getScRegFees().equals("online")) {
                                reg_fees_status = "Registration fees has been paid by Pay U Money";
                            } else {
                                reg_fees_status = "Registration fees has been pain and transaction id is " + studentCourseResultPOJO.getScRegFees();
                            }

                            if (studentCourseResultPOJO.getScRemFees().equals("online")) {
                                rem_fees_status = "Remaining fees has been paid by Pay U Money";
                            } else {
                                rem_fees_status = "Remaining fees has been pain and transaction id is " + studentCourseResultPOJO.getScRemFees();
                            }

                            String fees = reg_fees_status + " and " + rem_fees_status;
                            showOnlineDialog(fees);
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), "Full fees has not been submitted");
                        }
                    }
                }
            });


            iv_rem_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (studentCourseResultPOJO.getScFullfees().length() > 0) {
                        if (studentCourseResultPOJO.getScFullfees().equals("online")) {
                            showOnlineDialog("Full fees has been paid by Pay U Money");
                        } else {
                            showOnlineDialog("Full fees has been pain and transaction id is " + studentCourseResultPOJO.getScFullfees());
                        }
                    } else {
                        if (studentCourseResultPOJO.getScRemFees().length() > 0) {
                            if (studentCourseResultPOJO.getScRemFees().equals("online")) {
                                showOnlineDialog("Remaining Fees has bees paid by Pay U Money");
                            } else {
                                showOnlineDialog("Remaining Fees has bees paid and transaction id is " + studentCourseResultPOJO.getScRemFees());
                            }
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), "Remaining fees has not been submitted");
                        }
                    }
                }
            });


        } else {
            finish();
        }
    }

    public void checkRegistrationFeesStatus() {
        if (studentCourseResultPOJO != null) {
            if (studentCourseResultPOJO.getAdminFullFees().equals("true")) {
                iv_registration_fees.setImageResource(R.drawable.ic_approved);
                iv_confirm_reg.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminRegFees().equals("true")) {
                    iv_registration_fees.setImageResource(R.drawable.ic_approved);
                    iv_confirm_reg.setVisibility(View.GONE);
                } else {
                    if (studentCourseResultPOJO.getAdminRegFees().equals("false")
                            && studentCourseResultPOJO.getScRegFees().length() > 0) {
                        iv_registration_fees.setImageResource(R.drawable.ic_filled);
                        iv_confirm_reg.setVisibility(View.VISIBLE);
                    } else {
                        iv_registration_fees.setImageResource(R.drawable.ic_not_filled);
                        iv_confirm_reg.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    public void checkRemainingFeesStatus() {
        if (studentCourseResultPOJO != null) {
            if (studentCourseResultPOJO.getAdminFullFees().equals("true")) {
                iv_rem_fees.setImageResource(R.drawable.ic_approved);
                iv_rem_confirm.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getSc_admin_rem_fees().equals("true")) {
                    iv_rem_fees.setImageResource(R.drawable.ic_approved);
                    iv_rem_confirm.setVisibility(View.GONE);
                } else {
                    if (studentCourseResultPOJO.getSc_admin_rem_fees().equals("false")
                            && studentCourseResultPOJO.getScRemFees().length() > 0) {
                        iv_rem_fees.setImageResource(R.drawable.ic_filled);
                        iv_rem_confirm.setVisibility(View.VISIBLE);
                    } else {
                        iv_rem_fees.setImageResource(R.drawable.ic_not_filled);
                        iv_rem_confirm.setVisibility(View.GONE);
                    }
                }
            }
        }
    }


    public void checkFullFeesStatus() {
        if (studentCourseResultPOJO != null) {
            if (studentCourseResultPOJO.getAdminFullFees().equals("true")) {
                iv_full_fees.setImageResource(R.drawable.ic_approved);
                iv_confirm_full.setVisibility(View.GONE);
            } else {
                if (studentCourseResultPOJO.getAdminRegFees().equals("true")
                        && studentCourseResultPOJO.getSc_admin_rem_fees().equals("true")) {
                    iv_full_fees.setImageResource(R.drawable.ic_approved);
                    iv_confirm_full.setVisibility(View.GONE);
                } else {
                    if (studentCourseResultPOJO.getScFullfees().length() > 0) {
                        iv_full_fees.setImageResource(R.drawable.ic_filled);
                        iv_confirm_full.setVisibility(View.VISIBLE);
                    } else {
                        if (studentCourseResultPOJO.getScRemFees().length() > 0
                                && studentCourseResultPOJO.getScRegFees().length() > 0) {
                            iv_full_fees.setImageResource(R.drawable.ic_filled);
                            iv_confirm_full.setVisibility(View.VISIBLE);
                        } else {
                            iv_full_fees.setImageResource(R.drawable.ic_not_filled);
                            iv_confirm_full.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }


    public void showOnlineDialog(String message) {
        final Dialog dialog1 = new Dialog(StudentCourseViewActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_fees_paid);
        dialog1.setTitle("Fees Paid");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_fees = (TextView) dialog1.findViewById(R.id.tv_fees);
        Button btn_ok = (Button) dialog1.findViewById(R.id.btn_ok);

        tv_fees.setText(message);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    public void setListenerNull() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateStudent(boolean deduct_seat) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("sc_sid", studentCourseResultPOJO.getScSid()));
        nameValuePairs.add(new BasicNameValuePair("sc_sname", studentCourseResultPOJO.getScSname()));
        nameValuePairs.add(new BasicNameValuePair("sc_semail", studentCourseResultPOJO.getScSemail()));
        nameValuePairs.add(new BasicNameValuePair("sc_sapplicationform_fill", studentCourseResultPOJO.getScSapplicationformFill()));
        nameValuePairs.add(new BasicNameValuePair("sc_photo_upload", studentCourseResultPOJO.getScPhotoUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_cerifiato_upload", studentCourseResultPOJO.getScCerifiatoUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_idcard_upload", studentCourseResultPOJO.getScIdcardUpload()));
        nameValuePairs.add(new BasicNameValuePair("sc_reg_fees", studentCourseResultPOJO.getScRegFees()));
        nameValuePairs.add(new BasicNameValuePair("sc_rem_fees", studentCourseResultPOJO.getScRemFees()));
        nameValuePairs.add(new BasicNameValuePair("sc_fullfees", studentCourseResultPOJO.getScFullfees()));
        nameValuePairs.add(new BasicNameValuePair("sc_cid", studentCourseResultPOJO.getScCid()));
        nameValuePairs.add(new BasicNameValuePair("sc_id", studentCourseResultPOJO.getScId()));
        nameValuePairs.add(new BasicNameValuePair("sc_date", studentCourseResultPOJO.getSc_date()));
        nameValuePairs.add(new BasicNameValuePair("sc_time", studentCourseResultPOJO.getSc_time()));
        nameValuePairs.add(new BasicNameValuePair("admin_application_form", studentCourseResultPOJO.getAdminApplicationForm()));
        nameValuePairs.add(new BasicNameValuePair("admin_photo_upload", studentCourseResultPOJO.getAdminPhotoUpload()));
        nameValuePairs.add(new BasicNameValuePair("admin_certificate_upload", studentCourseResultPOJO.getAdminCertificateUpload()));
        nameValuePairs.add(new BasicNameValuePair("admin_icard", studentCourseResultPOJO.getAdminIcard()));
        nameValuePairs.add(new BasicNameValuePair("admin_status", studentCourseResultPOJO.getAdminStatus()));
        nameValuePairs.add(new BasicNameValuePair("admin_reg_fees", studentCourseResultPOJO.getAdminRegFees()));
        nameValuePairs.add(new BasicNameValuePair("sc_admin_rem_fees", studentCourseResultPOJO.getSc_admin_rem_fees()));
        nameValuePairs.add(new BasicNameValuePair("admin_full_fees", studentCourseResultPOJO.getAdminFullFees()));
        new WebServiceBase(nameValuePairs, this, UPDATE_STUDENT).execute(ApiConfig.update_student_course);
        if (deduct_seat) {
            updateCourse();
        }
    }

    public void updateCourse() {
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("c_name", courcesResultPOJO.getC_name()));
            nameValuePairs.add(new BasicNameValuePair("c_comt", courcesResultPOJO.getC_comt()));
            nameValuePairs.add(new BasicNameValuePair("c_from_date", courcesResultPOJO.getC_from_date()));
            nameValuePairs.add(new BasicNameValuePair("c_to_date", courcesResultPOJO.getC_to_date()));
            nameValuePairs.add(new BasicNameValuePair("c_place", courcesResultPOJO.getC_place()));
            nameValuePairs.add(new BasicNameValuePair("c_sheet_available", courcesResultPOJO.getC_sheet_available()));
            int seat_rem = Integer.parseInt(courcesResultPOJO.getC_rem_seat());
            seat_rem = seat_rem - 1;
            nameValuePairs.add(new BasicNameValuePair("c_rem_seat", String.valueOf(seat_rem)));
            int showing_seat = Integer.parseInt(courcesResultPOJO.getC_showing_sheet());
            if (showing_seat > 1) {
                showing_seat = showing_seat - 1;
            } else {
                if (showing_seat == 1) {
                    if (seat_rem > 2) {
                        showing_seat = 3;
                    } else {
                        showing_seat = seat_rem;
                    }
                }
            }
            nameValuePairs.add(new BasicNameValuePair("c_showing_sheet", String.valueOf(showing_seat)));
            nameValuePairs.add(new BasicNameValuePair("c_reg_fees", courcesResultPOJO.getC_reg_fees()));
            nameValuePairs.add(new BasicNameValuePair("c_rem_fees", courcesResultPOJO.getC_rem_fees()));
            nameValuePairs.add(new BasicNameValuePair("c_fees", courcesResultPOJO.getC_fees()));
            nameValuePairs.add(new BasicNameValuePair("c_pno", courcesResultPOJO.getC_pno()));
            nameValuePairs.add(new BasicNameValuePair("c_id", courcesResultPOJO.getC_id()));
            new WebServiceBase(nameValuePairs, StudentCourseViewActivity.this, UPDATE_COURCE).execute(ApiConfig.update_course_api);
            sendNotification(studentCourseResultPOJO.getScSid(), courcesResultPOJO.getC_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(String student_id, String course_name) {
        ArrayList<NameValuePair> arrayList = new ArrayList<>();
        arrayList.add(new BasicNameValuePair("student_id", student_id));
        arrayList.add(new BasicNameValuePair("course_name", course_name));
        new WebServiceBase(arrayList, this, "send_notification").execute(ApiConfig.SEND_NOTIFICATION);
    }

    private static final String UPDATE_COURCE = "update_course";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_confirm_app:
                studentCourseResultPOJO.setAdminApplicationForm("true");
                updateStudent(false);
                break;
            case R.id.iv_confirm_photo:
                studentCourseResultPOJO.setAdminPhotoUpload("true");
                updateStudent(false);
                break;
            case R.id.iv_confirm_cert:
                studentCourseResultPOJO.setAdminCertificateUpload("true");
                updateStudent(false);
                break;
            case R.id.iv_confirm_id:
                studentCourseResultPOJO.setAdminIcard("true");
                updateStudent(false);
                break;
            case R.id.iv_confirm_reg:
                studentCourseResultPOJO.setAdminRegFees("true");
                updateStudent(false);
                break;
            case R.id.iv_confirm_full:
                studentCourseResultPOJO.setAdminFullFees("true");
                updateStudent(true);
                break;

            case R.id.iv_rem_confirm:
                if (studentCourseResultPOJO.getAdminRegFees().equals("false")) {
                    ToastClass.showShortToast(getApplicationContext(), "Please First Confirm the Registration Fees.");
                } else {
                    studentCourseResultPOJO.setSc_admin_rem_fees("true");
                    studentCourseResultPOJO.setAdminFullFees("true");
                    updateStudent(true);
                }
                break;
        }
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case UPDATE_STUDENT:
                parseUpdateStudent(response);
                break;
            case UPDATE_COURCE:
                parseUpdateCourseActivity(response);
        }
    }

    public void parseUpdateCourseActivity(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        try {
            if (new JSONObject(response).optString("Success").equals("true")) {
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    public void parseUpdateStudent(String response) {
        Log.d(TagUtils.getTag(), "update response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("Success").equals("true")) {
                Gson gson = new Gson();
                studentCourseResultPOJO = gson.fromJson(jsonObject.optJSONObject("Result").toString(), StudentCourseResultPOJO.class);
                checkStatus();
                ToastClass.showShortToast(getApplicationContext(), "Updated Successfully");
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Updation Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }
}
