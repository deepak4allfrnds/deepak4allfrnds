package com.bhavesh.surveyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnx.surveyapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartSurveyAdapter extends RecyclerView.Adapter<StartSurveyAdapter.MyViewHolder> {
    Context context;
    List<String> list_survey;
   Callback adapterCallback;

    public StartSurveyAdapter(Context context) {
        this.context = context;
        try {
            adapterCallback = ((Callback) context);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        list_survey=new ArrayList<>();
        list_survey.add("Market Survey");
        list_survey.add("Election Survey");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survery_choose_item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.tv_survey.setText(list_survey.get(position));
    holder.itemView.setOnClickListener(v -> {
        adapterCallback.onItemClick(position);
    });
    }

    @Override
    public int getItemCount() {
        return list_survey.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_survey)
        TextView tv_survey;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {
        void onItemClick(int postion);
    }

}
