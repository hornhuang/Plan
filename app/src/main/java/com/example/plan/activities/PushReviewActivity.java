package com.example.plan.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plan.R;
import com.example.plan.bmobclass.Lesson;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PushReviewActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText mCourseEdit;

    private TextView mDateText;

    private EditText mUriLink;

    private Button   mPushBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_review);

        iniViews();
    }

    private void iniViews(){
        toolbar     = findViewById(R.id.toolbar);
        mCourseEdit = findViewById(R.id.course);
        mDateText   = findViewById(R.id.date);
        mUriLink    = findViewById(R.id.url);
        mPushBtn    = findViewById(R.id.push);

        setSupportActionBar(toolbar);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        mDateText.setText(date);

        mPushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushPlan();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void pushPlan(){
        Lesson plan = new Lesson();
        plan.setCourse(mCourseEdit.getText().toString());
        plan.setDate(new Date());
        plan.setUri(mUriLink.getText().toString());
        plan.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(PushReviewActivity.this, "添加数据成功，返回objectId为："+objectId, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(PushReviewActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void actionStart(Activity activity){
        Intent intent = new Intent(activity, PushReviewActivity.class);
        activity.startActivity(intent);
    }
}
