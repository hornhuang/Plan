package com.example.plan.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plan.R;
import com.example.plan.bmobclass.Plan;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    private final int PASSLINE = 80;

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
        if (plan.getFrom() != null)
            viewHolder.from.setText(plan.getFrom()[0] + " : " + plan.getFrom()[1]);
        if (plan.getTo()   != null)
            viewHolder.to.setText(plan.getTo()[0] + " : " + plan.getTo()[1]);
        viewHolder.name.setText(plan.getName());
        setImg(viewHolder.degree, plan);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setImg(ImageView Img, Plan plan) {
        if (plan.isCompleted()){
            int degree = plan.getDegree();
            if ( degree > PASSLINE || degree == PASSLINE ){
                Img.setImageResource(R.drawable.completed);
            }else{
                Img.setImageResource(R.drawable.failed);
            }
        }else {
            Img.setImageResource(R.drawable.undo);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView degree;

        private TextView from;

        private TextView to;

        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            degree = itemView.findViewById(R.id.degree);
            name   = itemView.findViewById(R.id.name);
            from   = itemView.findViewById(R.id.from);
            to     = itemView.findViewById(R.id.to);

        }
    }
}
