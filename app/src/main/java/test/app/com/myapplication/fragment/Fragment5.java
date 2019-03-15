package test.app.com.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import test.app.com.myapplication.Data.RandomData;
import test.app.com.myapplication.R;
/**
 *
 *  @横条
 *  @author fyh
 *  @time 2019 3 15
 *
 *  @实时刷新
 * */
public class Fragment5 extends Fragment {
    HorizontalBarChart hBarChart;
    private List<String> chartTitle;
    private List<BarEntry> chartData;
    private final String title[] = new String[]{"2017","2018","2019"};
    private BarDataSet barDataSet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_5,null);
        initView(view);
        for (String i:title) { chartTitle.add(i); }
        refresh();
        return view;
    }
    private void refresh() {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setProperty();
                initData();
                setChartData();
                hBarChart.invalidate(); //刷新图表
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.currentThread().sleep(5000);
                        chartData.clear();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    private void initView(View view) {
        hBarChart = view.findViewById(R.id.h_bar_chart);

        chartData = new ArrayList<>();
        chartTitle = new ArrayList<>();
    }
    private void setProperty() {
        hBarChart.setDescription("");  //设置说明
        Legend legend = hBarChart.getLegend();  //获取图例
        legend.setTextSize(20f);
        legend.setFormSize(30f);  //设置图例大小
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT); //设置图例位置
    }
    private void initData() {
        for (int i = 0; i <title.length; i++)
        {
            chartData.add(new BarEntry(RandomData.getRandomData(),i));
        }
    }
    private void setChartData() {
        barDataSet = new BarDataSet(chartData,"");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        barDataSet.setValueFormatter(new PercentFormatter());//设置百分比显示
        BarData barData = new BarData(chartTitle,barDataSet);
        hBarChart.setData(barData);
    }
}
