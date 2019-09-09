package com.example.plan.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plan.R;
import com.example.plan.bmobclass.Plan;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    private List<Plan> mList;

    private Activity context;

    public PlanAdapter(List<Plan> mList, Activity context){
        this.mList   = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_plan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    final Plan plan = mList.get(i);
        viewHolder.from.setText(plan.getFrom());
        viewHolder.to.setText(plan.getTo());
        viewHolder.name.setText(plan.getName());
        viewHolder.degree.setText(plan.getDegree());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView from;

        private TextView to;

        private TextView name;

        private TextView degree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from   = itemView.findViewById(R.id.from);
            to     = itemView.findViewById(R.id.to);
            name   = itemView.findViewById(R.id.name);
            degree = itemView.findViewById(R.id.degree);

        }
    }
}
