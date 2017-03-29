package com.example.kbb12.dms.mainMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.model.UserModel;
import com.example.kbb12.dms.model.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by kbb12 on 29/03/2017.
 */

public class GraphFragment extends Fragment {

    private LineChart mChart;
    private UserModel model;
    private SimpleDateFormat form;
    List<BGReading> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_fragment, container, false);
        form = new SimpleDateFormat("hh:mm", Locale.getDefault());
        model = ModelHolder.model;
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.add(Calendar.HOUR, -12);
        list = model.getHistoryBetween(then, now);
        Log.d("GRAPHING", list.size() + "");
        if(list.size()>0) {
            mChart = (LineChart) v.findViewById(R.id.chart1);
            mChart.getDescription().setEnabled(false);
            mChart.setBackgroundColor(Color.TRANSPARENT);
            mChart.setDrawGridBackground(false);
            mChart.setFocusable(false);
            mChart.setClickable(false);
            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setAxisMinimum(-0.25f);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {


                    return form.format(list.get((int) (list.size() -(value+1))).getTime().getTime());
                }
            });
            LineDataSet bds = new LineDataSet(populateChart(), "");
            bds.setLineWidth(4.0f);
            bds.setValueTextSize(0f);
            bds.setCubicIntensity(3f);
            bds.setCircleRadius(0f);
            bds.setCircleHoleRadius(0f);
            mChart.setData(new LineData(bds));
        }
        return v;
    }

    private ArrayList<Entry> populateChart() {

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i=list.size()-1, j=0; i>=0; i--, j++){
            entries.add(new Entry(j, (float) list.get(i).getReading()));
        }
        return entries;
    }


}
