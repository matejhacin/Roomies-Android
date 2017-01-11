package com.matejhacin.roomies.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.matejhacin.roomies.R;
import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.clients.StatisticsClient;
import com.matejhacin.roomies.rest.interfaces.UserListListener;
import com.matejhacin.roomies.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.paperdb.Paper;

public class StatisticsActivity extends AppCompatActivity implements UserListListener {

    @BindView(R.id.barChart)
    BarChart barChart;

    @BindView(R.id.pieChart)
    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        ButterKnife.bind(this);
        getStatistics();
    }

    // UsersListener

    @Override
    public void onSuccess(ArrayList<User> users) {
        String[] colors = getApplicationContext().getResources().getStringArray(R.array.random_colors);
        List<IBarDataSet> sets = new ArrayList<>();
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            List<BarEntry> entries = new ArrayList<>();
            BarEntry barEntry = new BarEntry((float) i, (float) user.getPoints());
            entries.add(barEntry);
            BarDataSet barDataSet = new BarDataSet(entries, user.getUsername());
            barDataSet.setColor(Color.parseColor(colors[i]));
            barDataSet.setValueTextSize(14);
            sets.add(barDataSet);

            PieEntry pieEntry = new PieEntry(user.getTasksNumber(), user.getUsername(), i);
            pieEntries.add(pieEntry);

        }

        BarData data = new BarData(sets);
        data.setBarWidth(0.9f);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.setTouchEnabled(false);
        barChart.invalidate();

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setColors(data.getColors());
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(14);
        pieChart.setHoleRadius(10f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTransparentCircleRadius(15f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setData(new PieData(dataSet));
    }

    @OnCheckedChanged({R.id.radioPoints, R.id.radioPercent})
    protected void onChecked(CompoundButton view, boolean checked) {
        if (checked) {
            if (view.getId() == R.id.radioPoints) {
                barChart.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
            } else {
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onFailure() {
        finish(); // haha
    }

    // Private

    private void getStatistics() {
        User user = Paper.book().read(Constants.KEY_USER);
        new StatisticsClient().getStatistics(user.getRoom().getId(), this);
    }
}
