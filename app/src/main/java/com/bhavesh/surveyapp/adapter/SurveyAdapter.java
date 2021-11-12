package com.bhavesh.surveyapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavesh.surveyapp.model.QuizModel;
import com.cnx.surveyapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.MyViewHolder>{
    Context context;
    List<QuizModel> flag = new ArrayList<>();
    Callback adapterCallback;
    private int lastCheckedPosition = -1;

    public SurveyAdapter(Context context) {
        this.context = context;
        try {
            adapterCallback = ((Callback) context);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (flag.get(position).getQuestion() != null) {
            holder.tv_quiz_question.setText(flag.get(position).getQuestion());
        }

        for (int i = 0; i < flag.get(position).getContestQNAData().size(); i++) {
            holder.radio_option_one.setText(flag.get(position).getContestQNAData().get(0).getValue());
            holder.radio_option_two.setText(flag.get(position).getContestQNAData().get(1).getValue());
            holder.radio_option_three.setText(flag.get(position).getContestQNAData().get(2).getValue());
            holder.radio_option_four.setText(flag.get(position).getContestQNAData().get(3).getValue());
        }


        holder.radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    lastCheckedPosition = checkedId;
                    flag.get(position).setAnswerSelected(String.valueOf(checkedRadioButton.getTag()));
                    // Changes the textview's text to "Checked: example radiobutton text"
                    Log.e("Checked:", "" + checkedRadioButton.getTag());
                    adapterCallback.onItemClick(lastCheckedPosition, flag);
                    notifyDataSetChanged();

                }
            }
        });

        holder.radio_option_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag.get(position).setAnswerSelected(flag.get(position).getContestQNAData().get(0).getKey());
                adapterCallback.onItemClick(lastCheckedPosition, flag);
                holder.radio_option_one.setChecked(true);
                holder.radio_option_two.setChecked(false);
                holder.radio_option_three.setChecked(false);
                holder.radio_option_four.setChecked(false);
            }
        });
        holder.radio_option_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag.get(position).setAnswerSelected(flag.get(position).getContestQNAData().get(1).getKey());
                adapterCallback.onItemClick(lastCheckedPosition, flag);
                holder.radio_option_one.setChecked(false);
                holder.radio_option_two.setChecked(true);
                holder.radio_option_three.setChecked(false);
                holder.radio_option_four.setChecked(false);
            }
        });
        holder.radio_option_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag.get(position).setAnswerSelected(flag.get(position).getContestQNAData().get(2).getKey());
                adapterCallback.onItemClick(lastCheckedPosition, flag);
                holder.radio_option_one.setChecked(false);
                holder.radio_option_two.setChecked(false);
                holder.radio_option_three.setChecked(true);
                holder.radio_option_four.setChecked(false);
            }
        });
        holder.radio_option_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag.get(position).setAnswerSelected(flag.get(position).getContestQNAData().get(3).getKey());
                adapterCallback.onItemClick(lastCheckedPosition, flag);
                holder.radio_option_one.setChecked(false);
                holder.radio_option_two.setChecked(false);
                holder.radio_option_three.setChecked(false);
                holder.radio_option_four.setChecked(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flag.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public void update(List<QuizModel> model) {
        this.flag.clear();
        this.flag.addAll(model);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.radioSelect)
        RadioGroup radio_group;
        @BindView(R.id.radio_option_one)
        RadioButton radio_option_one;
        @BindView(R.id.radio_option_two)
        RadioButton radio_option_two;
        @BindView(R.id.radio_option_three)
        RadioButton radio_option_three;
        @BindView(R.id.radio_option_four)
        RadioButton radio_option_four;
        @BindView(R.id.tv_quiz_question)
        TextView tv_quiz_question;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback {
        void onItemClick(int postion, List<QuizModel> flag);
    }
}
