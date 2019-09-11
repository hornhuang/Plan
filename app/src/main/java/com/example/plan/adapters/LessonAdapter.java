package com.example.plan.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plan.R;
import com.example.plan.activities.WebActivity;
import com.example.plan.bmobclass.Lesson;
import com.example.plan.utils.Dater;

import java.util.Date;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private List<Lesson> mList;

    private Activity context;

    public LessonAdapter(List<Lesson> mList, Activity context){
        this.mList   = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_review, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Lesson plan = mList.get(i);
        viewHolder.mName.setText(plan.getCourse());
        viewHolder.mCourse.setText(plan.getUri());
        viewHolder.mDate.setText(Dater.getYMDString(plan.getDate()));
        String str = "距离首次学习："+ Dater.getDiscrepantDays(plan.getDate(), new Date())+"天";
        viewHolder.mDatePoor.setText(str);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(context, plan.getUri());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;

        private EditText mCourse;

        private TextView mDate;

        private TextView mDatePoor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName     = itemView.findViewById(R.id.name);
            mCourse   = itemView.findViewById(R.id.course);
            mDate     = itemView.findViewById(R.id.date);
            mDatePoor = itemView.findViewById(R.id.date_poor);

        }
    }
}
