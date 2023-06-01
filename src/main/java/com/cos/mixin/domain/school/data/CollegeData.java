package com.cos.mixin.domain.school.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CollegeData {
    @JsonProperty("college_name")
    private String collegeName;
    
    
    private List<String> departments;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }
}
