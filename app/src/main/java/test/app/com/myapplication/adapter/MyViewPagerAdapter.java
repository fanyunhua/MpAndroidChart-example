package test.app.com.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 *
 *  ViewPagerAdapter
 *  @author fyh
 *  @time 2019 3 15
 *
 *
 *  实时刷新
 *  未完成阈值设置，超过阈值时交叉点变色
 * */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    FragmentManager fm;
    List<Fragment> list;
    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fm = fm;
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
