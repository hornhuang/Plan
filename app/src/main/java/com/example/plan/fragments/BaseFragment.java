package com.example.plan.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.plan.activities.BaseActivity;

public class BaseFragment extends Fragment {

    public void toast(String content){
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    public void log(String TAG, String content){
        Log.d(TAG, content);
    }

}
