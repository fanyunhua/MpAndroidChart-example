package test.app.com.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import test.app.com.myapplication.adapter.MyViewPagerAdapter;
import test.app.com.myapplication.fragment.Fragment1;
import test.app.com.myapplication.fragment.Fragment2;
import test.app.com.myapplication.fragment.Fragment3;
import test.app.com.myapplication.fragment.Fragment4;
import test.app.com.myapplication.fragment.Fragment5;
import test.app.com.myapplication.fragment.Fragment6;

public class Main2Activity extends AppCompatActivity {
    private ViewPager vp;
    private List<Fragment> list;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initAdapter();
    }

    private void initAdapter() {
        fm = getSupportFragmentManager();
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(fm,list);
        vp.setAdapter(myViewPagerAdapter);
        vp.setCurrentItem(0);
    }

    private void initView() {
        vp = findViewById(R.id.main2_vp);

        Fragment1 f1 = new Fragment1();
        Fragment2 f2 = new Fragment2();
        Fragment3 f3 = new Fragment3();
        Fragment4 f4 = new Fragment4();
        Fragment5 f5 = new Fragment5();
        Fragment6 f6 = new Fragment6();

        list = new ArrayList<>();
        list.add(f1);
        list.add(f2);
        list.add(f3);
        list.add(f4);
        list.add(f5);
        list.add(f6);
    }
}
