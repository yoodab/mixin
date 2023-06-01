package com.cos.mixin.domain.school.data;

import java.util.List;

public class DepartmentData {
    private String schoolName;
    private List<CollegeData> colleges;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<CollegeData> getColleges() {
        return colleges;
    }

    public void setColleges(List<CollegeData> colleges) {
        this.colleges = colleges;
    }
}
