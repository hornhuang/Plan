package com.example.plan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.KeyEventDispatcher;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.plan.R;
import com.example.plan.activities.MainActivity;
import com.example.plan.utils.Gallery;

import java.util.Calendar;
import java.util.Timer;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlanFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = "PlanFragment";

    private boolean pageFlag = false;

    /** 主界面
     *
     */

    private Toolbar toolbar;

    private LinearLayout linearlayout;

    private RecyclerView recyclerView;

    private TextView conclusion;

    private Button save;

    /** 隐藏页面
     *
     *  内容：昨天
     */

    private RelativeLayout holdView;

    private TextView mYesConclusion;

    private ImageView mYesBackground;

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
        return view;
    }

    private void iniViews(View view){
        holdView = view.findViewById(R.id.yes_page);
        mYesConclusion = view.findViewById(R.id.yes_conclusion);
        mYesBackground = view.findViewById(R.id.yes_bac);

        toolbar = view.findViewById(R.id.toolbar);
        linearlayout = view.findViewById(R.id.linearlayout);
        recyclerView = view.findViewById(R.id.recycler);
        conclusion = view.findViewById(R.id.conclusion);
        save = view.findViewById(R.id.save_change);

        if (toolbar != null){
            ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        }
        setHasOptionsMenu(true);
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

    private void iniRecycler(){

    }

    @Override
    public void onClick(View v) {

    }
}
