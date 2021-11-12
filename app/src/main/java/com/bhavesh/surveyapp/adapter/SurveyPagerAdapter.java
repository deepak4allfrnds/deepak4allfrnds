package com.bhavesh.surveyapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bhavesh.surveyapp.model.QuizModel;
import com.cnx.surveyapp.R;

import java.util.ArrayList;
import java.util.List;

public class SurveyPagerAdapter extends PagerAdapter {
    Context context;
    List<QuizModel> flag = new ArrayList<>();
    LayoutInflater inflater;
    Callback adapterCallback;
    private int lastCheckedPosition = -1;

    public interface Callback {
        void onSelect(List<QuizModel> flag);

        void onItemClick(int postion, List<QuizModel> flag);
    }

    public SurveyPagerAdapter(Context context, List<QuizModel> model) {
        this.context = context;
        this.flag = model;
        try {
            adapterCallback = ((Callback) context);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getCount() {
        return flag.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // Declare Variables
        TextView tv_quetion, tv_option1, tv_option2, tv_option3, tv_option4;



        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.survey_view_pager_item, container, false);
        tv_quetion = (TextView) itemView.findViewById(R.id.tv_quetion);
        tv_option1 = (TextView) itemView.findViewById(R.id.tv_option1);
        tv_option2 = (TextView) itemView.findViewById(R.id.tv_option2);
        tv_option3 = (TextView) itemView.findViewById(R.id.tv_option3);
        tv_option4 = (TextView) itemView.findViewById(R.id.tv_option4);

        tv_quetion.setText(flag.get(position).getQuestion());


        for (int i = 0; i < flag.get(position).getContestQNAData().size(); i++) {
            tv_option1.setText(flag.get(position).getContestQNAData().get(0).getValue());
            tv_option2.setText(flag.get(position).getContestQNAData().get(1).getValue());
            tv_option3.setText(flag.get(position).getContestQNAData().get(2).getValue());
            tv_option4.setText(flag.get(position).getContestQNAData().get(3).getValue());

        }
        tv_option1.setOnClickListener(view -> {
            flag.get(position).setAnswerSelected(tv_option1.getText().toString());
            Log.e("secletd option 1",tv_option1.getText().toString());
            adapterCallback.onItemClick(position,flag);
            tv_option1.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_selected));
            tv_option2.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option3.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option4.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
        });
        tv_option2.setOnClickListener(view -> {
            flag.get(position).setAnswerSelected(tv_option2.getText().toString());
            Log.e("secletd option 2",tv_option2.getText().toString());
            adapterCallback.onItemClick(position,flag);
            tv_option2.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_selected));
            tv_option1.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option3.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option4.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
        });
        tv_option3.setOnClickListener(view -> {
            flag.get(position).setAnswerSelected(tv_option3.getText().toString());
            Log.e("selectd option 3",tv_option3.getText().toString());
            adapterCallback.onItemClick(position,flag);
            tv_option3.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_selected));
            tv_option2.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option1.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option4.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
        });
        tv_option4.setOnClickListener(view -> {
            flag.get(position).setAnswerSelected(tv_option4.getText().toString());
            Log.e("secletd option 4",tv_option4.getText().toString());
            adapterCallback.onItemClick(position,flag);
            tv_option4.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_selected));
            tv_option2.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option3.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
            tv_option1.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_regular));
        });


//        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // This will get the radiobutton that has changed in its check state
//                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
//                // This puts the value (true/false) into the variable
//                boolean isChecked = checkedRadioButton.isChecked();
//                // If the radiobutton that has changed in check state is now checked...
//                if (isChecked) {
//                    lastCheckedPosition = checkedId;
//                    flag.get(position).setAnswerSelected(String.valueOf(checkedRadioButton.getTag()));
//                    // Changes the textview's text to "Checked: example radiobutton text"
//                    Log.e("Checked:", "" + checkedRadioButton.getTag());
//                    adapterCallback.onItemClick(lastCheckedPosition, flag);
//                    notifyDataSetChanged();
//
//                }
//            }
        //  });


//        submitAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adapterCallback.onSelect(flag);
//            }
//        });


        ((ViewPager) container).addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

}
