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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import test.app.com.myapplication.Data.RandomData;
import test.app.com.myapplication.R;
/**
 *
 *  @条形图
 *  @author fyh
 *  @time 2019 3 15
 *
 *  @实时刷新
 * */
public class Fragment2 extends Fragment {
    private BarChart barChart;
    private List<String> chartTitle;
    private List<BarEntry> chartData,chartData2;
    private final String title[] = new String[]{"2017","2018","2019"};
    private BarDataSet barDataSet,barDataSet2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2,null);
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
                barChart.invalidate(); //刷新图表
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
                        chartData.clear();chartData2.clear();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void setChartData() {
        barDataSet = new BarDataSet(chartData,"");
        barDataSet.setValueFormatter(new PercentFormatter());//设置百分比显示
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        barDataSet2 = new BarDataSet(chartData2,"");
        barDataSet2.setValueFormatter(new PercentFormatter());//设置百分比显示
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        List<BarDataSet> list = new ArrayList<>();
        list.add(barDataSet);
        list.add(barDataSet2);
        BarData barData = new BarData(chartTitle,list);
        barChart.setData(barData);
    }

    private void initData() {
        for (int i = 0; i <title.length ; i++)
        {
            chartData.add(new BarEntry(RandomData.getRandomData(),i));
            chartData2.add(new BarEntry(RandomData.getRandomData(),i));
        }
    }

    private void setProperty() {
        barChart.setDescription("");  //设置说明
        Legend legend = barChart.getLegend();  //获取图例
        legend.setTextSize(20f);
        legend.setFormSize(30f);  //设置图例大小
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT); //设置图例位置

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴在下方显示

    }

    private void initView(View view) {
        barChart = view.findViewById(R.id.bar_chart);
        chartData = new ArrayList<>();
        chartTitle = new ArrayList<>();
        chartData2 = new ArrayList<>();
    }
}
