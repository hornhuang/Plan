package com.example.plan.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plan.R;
import com.example.plan.activities.PushReviewActivity;
import com.example.plan.adapters.LessonAdapter;
import com.example.plan.bmobclass.Lesson;
import com.example.plan.utils.Dater;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class ReviewFragment extends BaseFragment {

    private final String TAG = "ReviewListFragment";

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<Lesson> mLessonLists;

    private LessonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.bind(this, view);
        iniViews();
        iniRecyclerView();
        return view;
    }

    private void iniViews(){
        toolbar.setTitle("复习");
        toolbar.setTitleTextColor(Color.WHITE);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    @OnClick(R.id.fab)
    public void fabOnClick(View view){
        PushReviewActivity.actionStart(getActivity());
    }

    private void iniRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        mLessonLists = new ArrayList<>();
        adapter = new LessonAdapter(mLessonLists, getActivity());
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData(){
        BmobQuery<Lesson> query = new BmobQuery<>();
        query.order("-createdAt")
                .findObjects(new FindListener<Lesson>() {
                    @Override
                    public void done(List<Lesson> object, BmobException e) {
                        if (e == null) {
                            resetPlanLists(object);
                            mRefreshLayout.setRefreshing(false);
                        } else {
                            toast("失败！" + e.getMessage());
                        }
                    }
                });
    }

    private void resetPlanLists(List<Lesson> initialLessons){
        List<Lesson> resetList = new ArrayList<>();
        for (Lesson plan : initialLessons){
            int days = Dater.getDiscrepantDays(plan.getDate(), new Date());
            if( days == 0 || days == 1 || days == 2 || days == 4 || days == 5 || days == 15  ){
                resetList.add(plan);
            }
            if (days > 15){
                removeDBPlan(plan);
            }
        }
        mLessonLists.clear();
        mLessonLists.addAll(resetList);
        adapter.notifyDataSetChanged();
    }

    private void removeDBPlan(final Lesson expiredLesson){
        new Thread(){
            @Override
            public void run() {
                super.run();
                expiredLesson.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.d(TAG, "删除成功:" + expiredLesson.getUpdatedAt());
                        }else{
                            Log.d(TAG,"删除失败：" + e.getMessage());
                        }
                    }

                });
            }
        }.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
        super.onActivityResult(requestCode, resultCode, data);
    }

}
