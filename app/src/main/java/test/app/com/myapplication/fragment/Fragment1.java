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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import test.app.com.myapplication.Data.RandomData;
import test.app.com.myapplication.R;
/**
 *
 *  折线图
 *  @author fyh
 *  @time 2019 3 15
 *
 *  实时刷新
 * */
public class Fragment1 extends Fragment {
    LineChart lineChart;
    List<Entry> chartData;
    List<Entry> chartData2;
    List<String> chartTitle;
    String title[] = new String[]{"2017","2018","2019","2020","2021"};
    float data[] ;
    float data2[];
    LineDataSet lineDataSet,lineDataSet1;
    List<LineDataSet> lineDataSets;
    LineData lineData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1,null);
        chartData2 = new ArrayList<>();
        chartTitle = new ArrayList<>();
        chartData = new ArrayList<>();
        lineChart = view.findViewById(R.id.line_chart);

        for (String tit: title) { chartTitle.add(tit); }

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                Toast.makeText(Main2Activity.this,"图像数据更新成功",Toast.LENGTH_SHORT).show();
                setProperty();
                initData();
                makeData();
                lineChart.invalidate(); //刷新图表
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.currentThread().sleep(5000);
                        lineDataSet.clear();lineDataSet1.clear();  //清理数据   *****
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return view;
    }
    private void makeData() {

        lineDataSet = new LineDataSet(chartData,"1");  //添加一号数据
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setCircleColor(Color.BLUE);

        lineDataSet1 = new LineDataSet(chartData2,"2");  //添加二号数据
        lineDataSet1.setColor(Color.RED);

        lineDataSets = new ArrayList<>();  //使用list存储绘图数据
        lineDataSets.add(lineDataSet);
        lineDataSets.add(lineDataSet1);

        lineData = new LineData(chartTitle, lineDataSets);  //设置x轴和数据

        lineChart.setData(lineData);   //设置数据
    }

    private void setProperty() {
        lineChart.setDescription("");  //设置说明
        Legend legend = lineChart.getLegend();  //获取图例
        legend.setTextSize(20f);
        legend.setFormSize(30f);  //设置图例大小
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT); //设置图例位置
        XAxis xAxis = lineChart.getXAxis(); //设置x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //设置x轴在下方显示

    }
    void initData()
    {
        data = new float[]{RandomData.getRandomData(),
                RandomData.getRandomData(),
                RandomData.getRandomData(),
                RandomData.getRandomData(),
                RandomData.getRandomData()};
        data2 = new float[]{RandomData.getRandomData()
                ,RandomData.getRandomData()
                ,RandomData.getRandomData()
                ,RandomData.getRandomData(),
                RandomData.getRandomData()};
        for (int i = 0; i <data.length ; i++)
        {
            chartData.add(new Entry(data[i],i));  // 添加数据
            chartData2.add(new Entry(data2[i],i));
        }
    }
}
