package com.example.plan.bmobclass;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Day extends BmobObject {

    private List<Plan> planList;

    private String conclusion;

    private int degree;

    public Day() {
    }

    public List<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
