package com.example.plan.bmobclass;

import cn.bmob.v3.BmobObject;

public class Plan extends BmobObject {

    private String from[] = new String[2];

    private String to[] = new String[2];

    private String name = "";

    private int degree = 0;

    private boolean completed = false;

    public Plan Plan() {
        return new Plan();
    }

    public String[] getFrom() {
        return from;
    }

    public Plan setFrom(String[] from) {
        this.from = from;
        return this;
    }

    public String[] getTo() {
        return to;
    }

    public Plan setTo(String[] to) {
        this.to = to;
        return this;
    }

    public String getName() {
        return name;
    }

    public Plan setName(String name) {
        this.name = name;
        return this;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
