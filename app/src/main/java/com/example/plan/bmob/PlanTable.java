package com.example.plan.bmob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class PlanTable extends BmobObject {

    // 计划列表项
    private List<PlanItem> items = new ArrayList<>();
    // 计划时间
    private Date date;
    // 计划分数
    private int goals;
    // 所属用户
    private User user;

    public PlanTable(){

    }

    public List<PlanItem> getItems() {
        return items;
    }

    public void setItems(List<PlanItem> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
