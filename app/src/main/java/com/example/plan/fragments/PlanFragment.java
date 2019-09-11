package com.example.plan.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plan.R;
import com.example.plan.activities.MainActivity;
import com.example.plan.adapters.PlanAdapter;
import com.example.plan.bmobclass.Day;
import com.example.plan.bmobclass.Plan;
import com.example.plan.utils.Gallery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlanFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = "PlanFragment";

    private boolean pageFlag = false;

    /** 主界面
     *
     */

    private SwipeRefreshLayout refreshLayout;

    private Toolbar toolbar;

    private LinearLayout linearlayout;

    private RecyclerView recyclerView;

    private TextView conclusion;

    private Button save;

    private FloatingActionButton mAddFab;

    /** 隐藏页面
     *
     *  内容：昨天
     */

    private RelativeLayout holdView;

    private TextView mYesConclusion;

    private ImageView mYesBackground;

    /** RecyclerView
     *
     * @param savedInstanceState
     */

    private Day day;

    private List<Plan> planList;

    private PlanAdapter planAdapter;

    private void getPlan(String planId){
        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<Plan> bmobQuery = new BmobQuery<Plan>();
        bmobQuery.getObject(planId, new QueryListener<Plan>() {
            @Override
            public void done(Plan object,BmobException e) {
                if(e==null){
                    planList.add(object);
                    toast("查询成功");
                }else{
                    toast("查询失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        iniViews(view);
        iniRecycler();
        load();
//        if (day == null)
        return view;
    }

    private void iniViews(View view){
        holdView       = view.findViewById(R.id.yes_page);
        mYesConclusion = view.findViewById(R.id.yes_conclusion);
        mYesBackground = view.findViewById(R.id.yes_bac);

        refreshLayout  = view.findViewById(R.id.refresh);
        toolbar        = view.findViewById(R.id.toolbar);
        linearlayout   = view.findViewById(R.id.linearlayout);
        recyclerView   = view.findViewById(R.id.recycler);
        conclusion     = view.findViewById(R.id.conclusion);
        save           = view.findViewById(R.id.save_change);
        mAddFab        = view.findViewById(R.id.fab);

        if (toolbar != null){
            ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        }
        setHasOptionsMenu(true);

        save.setOnClickListener(this);
        mAddFab.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                planAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 某个昨天以后的计划
     */
    private void load() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        BmobDate bmobCreatedAtDate = new BmobDate(cal.getTime());

        Log.d(TAG, "started");

        BmobQuery<Day> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<Day>() {
            @Override
            public void done(List<Day> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0){
                        // ...
                        return;
                    }else {
                        day = object.get(0);
                        Observable.fromIterable(day.getPlanList())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(new Observer<String>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(String plan) {
                                        containAll(plan);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                } else {
                    Log.d(TAG, "failed" + e.toString());
                }
            }
        });
    }

    /**
     * 查询计划
     */
    private void containAll(String objectId) {
        BmobQuery<Plan> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objectId, new QueryListener<Plan>() {
            @Override
            public void done(Plan object,BmobException e) {
                if(e==null){
                    planList.add(object);
                    planAdapter.notifyDataSetChanged();
                }else{

                }
            }
        });
    }

    private void iniRecycler(){
        planList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        planAdapter = new PlanAdapter(planList, getActivity());
        recyclerView.setAdapter(planAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        RequestOptions options = new RequestOptions()
                .transform(new BlurTransformation(25, 5));

        int id = item.getItemId();
        switch (id){
            case R.id.yesterday:
                if (!pageFlag){
                    int index = (int) (Calendar.getInstance().getTimeInMillis() % Gallery.size());
                    Glide.with(getActivity())
                            .load(Gallery.pictureUri[index])
                            .thumbnail(Glide.with(getContext()).load(R.drawable.loading))
                            .fitCenter()
                            .apply(options)
                            .into(mYesBackground);
                    item.setIcon(R.drawable.moon);
                    holdView.setVisibility(View.VISIBLE);
                    linearlayout.setVisibility(View.GONE);
                    pageFlag = !pageFlag;
                }else {
                    item.setIcon(R.drawable.sun);
                    holdView.setVisibility(View.GONE);
                    linearlayout.setVisibility(View.VISIBLE);
                    pageFlag = !pageFlag;
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.save_change:
                pushNewDay();
                break;
            case R.id.fab:
                dialogShow();
                break;
        }
    }

    /**
     * 提交空 Day
     * 开启新的一天
     */
    private void pushNewDay(){
        new Day().save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    toast("Day :"+objectId);
                }else{
                    toast("创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    private void savePlan(Plan p){
        p.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    log(TAG, "添加数据成功，返回objectId为："+objectId);
                    if (day == null){
                        save();
                    }else {
                        upDate();
                    }
                }else{
                    log(TAG, "创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    private void save(){
        day = new Day();
        day.setCreateDate(new Date());
        List<String> planIds = new ArrayList<>();
        for (Plan plan : planList){
            planIds.add(plan.getObjectId());
        }
        day.setPlanList(planIds);
        day.setConclusion(conclusion.getText().toString());
        int degree = 0;
        for (Plan plan : planList){
            degree += plan.getDegree();
        }
        degree /= planList.size();
        day.setDegree(degree);
        day.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    toast("Day :"+objectId);
                }else{
                    toast("创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    private void upDate(){
        day.setCreateDate(new Date());
        List<String> planIds = new ArrayList<>();
        for (Plan plan : planList){
            planIds.add(plan.getObjectId());
        }
        day.setPlanList(planIds);
        day.setConclusion(conclusion.getText().toString());
        int degree = 0;
        for (Plan plan : planList){
            degree += plan.getDegree();
        }
        degree /= planList.size();
        day.setDegree(degree);
        day.update(day.getObjectId(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("更新成功:");
                }else{
                    toast("更新失败：" + e.getMessage());
                    Log.d(TAG, "更新失败" + e.toString());
                }
            }

        });
    }

    /**
     * 自定义布局
     * setView()只会覆盖AlertDialog的Title与Button之间的那部分，而setContentView()则会覆盖全部，
     * setContentView()必须放在show()的后面
     */
    private void dialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.push_item_dialog, null);
        final EditText fromHour    = v.findViewById(R.id.start_hour);
        final EditText fromMinutes = v.findViewById(R.id.start_minutes);
        final EditText toHour      = v.findViewById(R.id.end_hour);
        final EditText toMinutes   = v.findViewById(R.id.end_minutes);
        final EditText planName      = v.findViewById(R.id.plan_name);
        Button btn_sure   = v.findViewById(R.id.dialog_btn_sure);
        Button btn_cancel = v.findViewById(R.id.dialog_btn_cancel);
        // builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        // dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        btn_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Plan plan = new Plan()
                        .setFrom(new String[]{fromHour.getText().toString(), fromMinutes.getText().toString()})
                        .setTo(new String[]{toHour.getText().toString(), toMinutes.getText().toString()})
                        .setName(planName.getText().toString());
                planList.add(plan);
                planAdapter.notifyDataSetChanged();
                savePlan(plan);
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }
}
