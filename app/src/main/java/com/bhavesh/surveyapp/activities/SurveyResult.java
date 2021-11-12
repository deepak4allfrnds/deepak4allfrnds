package com.bhavesh.surveyapp.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bhavesh.surveyapp.utils.Utills;
import com.cnx.surveyapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurveyResult extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.piechart)
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_result);
        ButterKnife.bind(this);
        settoolbar();
        intialize();

    }

    private void settoolbar() {
        tv_name.setText("Start Survey");
        iv_back.setVisibility(View.GONE);
    }

    private void intialize() {
        setPieChart();
    }
    private void setPieChart() {
        List<PieEntry> pieEntires = new ArrayList<>();
     //   pieEntires.add(new PieEntry(right));
      //  pieEntires.add(new PieEntry(wrong));
        PieDataSet dataSet = new PieDataSet(pieEntires, "");
        dataSet.setValueFormatter(new MyValueFormatter());
        dataSet.setColors(getResources().getColor(R.color.pie_orange), getResources().getColor(R.color.pie_green));
        PieData data = new PieData(dataSet);
        data.setValueTextSize(18f);
        data.setValueTextColor(getResources().getColor(R.color.white));
        //Get the chart
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setCenterText("RESULT");
        pieChart.setCenterTextSize(24f);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterTextColor(getResources().getColor(R.color.white));
        pieChart.setDrawSlicesUnderHole(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setContentDescription("");
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(true);
        pieChart.getLegend().setEnabled(false);
        // enable rotation of the chart by touch
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);
        //pieChart.setDrawMarkers(true);
        //pieChart.setMaxHighlightDistance(34);
        pieChart.setEntryLabelTextSize(7f);
        pieChart.setHoleRadius(60);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(10);
        pieChart.getRenderer().getPaintRender().setShadowLayer(10, 0, 24, ContextCompat.getColor(this, R.color.pie_orange));
        pieChart.setHoleColor(getResources().getColor(R.color.black));
        //legend attributes
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(20);
        legend.setFormSize(20);
        legend.setFormToTextSpace(2);

    }

    public class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + "%"; // e.g. append a dollar-sign
        }
    }
}
