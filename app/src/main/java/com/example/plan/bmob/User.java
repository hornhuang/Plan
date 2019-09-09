package com.example.plan.bmob;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    // 用户名
    private String mNickName;
    // 简介
    private String mIntroduce;

    public User(){}

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getmIntroduce() {
        return mIntroduce;
    }

    public void setmIntroduce(String mIntroduce) {
        this.mIntroduce = mIntroduce;
    }

}
