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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
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
 *  @饼图 （空心）
 *  @author fyh
 *  @time 2019 3 15
 *
 *  @实时刷新
 * */
public class Fragment4 extends Fragment {
    private PieChart pieChart;
    private List<String> chartTitle;
    private List<Entry> chartData;
    private final String title[] = new String[]{"2017","2018","2019"};
    private PieDataSet pieDataSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, null);
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
                pieChart.invalidate(); //刷新图表
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
        pieChart = view.findViewById(R.id.pie_chart2);

        chartData = new ArrayList<>();
        chartTitle = new ArrayList<>();
    }
    private void setProperty() {
        pieChart.setDescription("");  //设置说明
        Legend legend = pieChart.getLegend();  //获取图例
//        pieChart.setDrawHoleEnabled(false);//去掉中间孔
        pieChart.setHoleColor(Color.GREEN); //中间孔颜色
        legend.setTextSize(20f);
        legend.setFormSize(30f);  //设置图例大小
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT); //设置图例位置
    }
    private void initData() {
        for (int i = 0; i <title.length ; i++)
        {
            chartData.add(new Entry(RandomData.getRandomData(),i));
        }
    }
    private void setChartData() {
        pieDataSet = new PieDataSet(chartData,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueFormatter(new PercentFormatter());//设置百分比显示
        PieData pieData = new PieData(chartTitle,pieDataSet);
        pieChart.setData(pieData);
    }
}
