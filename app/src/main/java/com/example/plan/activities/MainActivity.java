package com.example.plan.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import com.example.plan.R;
import com.example.plan.fragments.ChartFragment;
import com.example.plan.fragments.PlanFragment;
import com.example.plan.fragments.ReviewFragment;

import java.util.ArrayList;
import java.util.List;

/**四大部分
 * 1。当天计划表
 * 2。得分折线图
 * 3。待办事项
 * 4。侧拉框我的历史信息
 */

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitleList = new ArrayList<String>();//页卡标题集合
    private List<Fragment> mFragments = new ArrayList<>();//页卡视图集合

    final int[] ICONS = new int[]{
            R.drawable.tab_plan,
            R.drawable.tab_review,
            R.drawable.tab_chart
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);mViewPager = (ViewPager) findViewById(R.id.view_paper);
        mTabLayout = findViewById(R.id.tab_layout);

        //添加页卡视图
        mFragments.add(new PlanFragment());
        mFragments.add(new ReviewFragment());
        mFragments.add(new ChartFragment());

        //添加页卡标题
        mTitleList.add("plan");
        mTitleList.add("review");
        mTitleList.add("chart");

        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)),false);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)),true);// 由于其他两模块移除，此模块设为首选
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)),false);

        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);

        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        //添加图片
        mTabLayout.getTabAt(0).setIcon(ICONS[0]);
        mTabLayout.getTabAt(1).setIcon(ICONS[1]);
        mTabLayout.getTabAt(2).setIcon(ICONS[2]);
    }

    //ViewPager适配器
    class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public int getCount() {
            return mFragments.size();//页卡数
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }
}
