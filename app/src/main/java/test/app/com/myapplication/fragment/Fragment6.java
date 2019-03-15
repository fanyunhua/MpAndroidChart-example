package test.app.com.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import test.app.com.myapplication.Data.RandomData;
import test.app.com.myapplication.R;
/**
 *
 *  @气泡
 *  @author fyh
 *  @time 2019 3 15
 *
 *  @实时刷新
 * */
public class Fragment6 extends Fragment {
    BubbleChart bubbleChart;
    private List<String> chartTitle;
    private List<BubbleEntry> chartData;
    private final String title[] = new String[]{"2017","2018","2019","2020","2021","2022","2023"};
    BubbleDataSet bubbleDataSet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_6,null);
        initView(view);
        for (String a:title) { chartTitle.add(a); }
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
                bubbleChart.invalidate(); //刷新图表
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
    void setProperty()
    {
        bubbleChart.setDescription("");
        bubbleChart.setMinimumHeight(400);
        XAxis xAxis = bubbleChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    void initData()
    {
        for (int i = 0; i <title.length ; i++)
        {
            chartData.add(new BubbleEntry(i,RandomData.getRandomData(),i));
        }
        bubbleDataSet = new BubbleDataSet(chartData,"");
        bubbleDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BubbleData bubbleData = new BubbleData(chartTitle,bubbleDataSet);
        bubbleChart.setData(bubbleData);
    }
    private void initView(View view) {
        bubbleChart = view.findViewById(R.id.bubble_chart);
        chartData = new ArrayList<>();
        chartTitle = new ArrayList<>();


    }
}
