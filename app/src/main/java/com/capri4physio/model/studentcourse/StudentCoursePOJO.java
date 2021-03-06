package com.capri4physio.model.studentcourse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 31-07-2017.
 */

public class StudentCoursePOJO implements Serializable{
    @SerializedName("Success")
    String Success;
    @SerializedName("Result")
    List<StudentCourseResultPOJO> studentCourseResultPOJOList;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public List<StudentCourseResultPOJO> getStudentCourseResultPOJOList() {
        return studentCourseResultPOJOList;
    }

    public void setStudentCourseResultPOJOList(List<StudentCourseResultPOJO> studentCourseResultPOJOList) {
        this.studentCourseResultPOJOList = studentCourseResultPOJOList;
    }

    @Override
    public String toString() {
        return "StudentCoursePOJO{" +
                "Success='" + Success + '\'' +
                ", studentCourseResultPOJOList=" + studentCourseResultPOJOList +
                '}';
    }
}
