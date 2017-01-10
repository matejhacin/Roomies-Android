package com.matejhacin.roomies.activities;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.matejhacin.roomies.R;
import com.matejhacin.roomies.models.User;
import com.matejhacin.roomies.rest.clients.StatisticsClient;
import com.matejhacin.roomies.rest.interfaces.UsersListener;
import com.matejhacin.roomies.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class StatisticsActivity extends AppCompatActivity implements UsersListener {

    @BindView(R.id.barChart) BarChart barChart;

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

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            List<BarEntry> entries = new ArrayList<>();
            BarEntry barEntry = new BarEntry((float) i, (float) user.getPoints());
            entries.add(barEntry);
            BarDataSet barDataSet = new BarDataSet(entries, user.getUsername());
            barDataSet.setColor(Color.parseColor(colors[i]));
            sets.add(barDataSet);
        }

        BarData data = new BarData(sets);
        data.setBarWidth(0.9f);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();
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
