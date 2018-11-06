package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.R;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationController navigationController;
    private TextView tvToolTitle;
    private FragmentManager manager;
    private NewsFragment newsFragment;
    private MaillistFragment maillistFragment;
    private WorkBenchFragment workBenchFragment;
    private StatisticsFragment statisticsFragment;
    private MoreFragment moreFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        tvToolTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        initUI();
    }


    private void initUI(){
        tvToolTitle.setText(getResources().getString(R.string.news));
        tvToolTitle.setTextColor(Color.WHITE);
        PageBottomTabLayout tab = (PageBottomTabLayout) findViewById(R.id.tab);
        navigationController =tab.custom()
                .addItem(newItem(R.mipmap.news,R.mipmap.news_selected,getResources().getString(R.string.news)))
                .addItem(newItem(R.mipmap.maillist,R.mipmap.maillist_selected,getResources().getString(R.string.maillist)))
                .addItem(newItem(R.mipmap.workbench,R.mipmap.workbench_selected,getResources().getString(R.string.workbench)))
                .addItem(newItem(R.mipmap.statistics,R.mipmap.statistics_selected,getResources().getString(R.string.statistics)))
                .addItem(newItem(R.mipmap.more,R.mipmap.more_selected,getResources().getString(R.string.more)))
                .build();
        navigationController.addTabItemSelectedListener(listener);


        newsFragment = new NewsFragment();
        maillistFragment = new MaillistFragment();
        workBenchFragment = new WorkBenchFragment();
        statisticsFragment = new StatisticsFragment();
        moreFragment = new MoreFragment();

        manager=getSupportFragmentManager();
        //初次登陆，显示工作台，隐藏其他
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.main_content,newsFragment);
        transaction.add(R.id.main_content,maillistFragment);
        transaction.add(R.id.main_content,workBenchFragment);
        transaction.add(R.id.main_content,statisticsFragment);
        transaction.add(R.id.main_content,moreFragment);
        transaction.show(newsFragment);
        transaction.hide(moreFragment);
        transaction.hide(statisticsFragment);
        transaction.hide(maillistFragment);
        transaction.hide(workBenchFragment);
        transaction.commit();
    }


    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView =new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.blue2));
        return  normalItemView;

    }


    OnTabItemSelectedListener listener=new OnTabItemSelectedListener() {
        @Override
        public void onSelected(int index, int old) {
            FragmentTransaction transaction=manager.beginTransaction();
            switch (index){
                //当选中首页id时，显示framelayout加载首页fragment
                case 0:
                    transaction.show(newsFragment);
                    transaction.hide(maillistFragment);
                    transaction.hide(workBenchFragment);
                    transaction.hide(statisticsFragment);
                    transaction.hide(moreFragment);
                    toolbar.setVisibility(View.VISIBLE);
                    tvToolTitle.setText(getResources().getString(R.string.news));
                    transaction.commit();
                    break;

                case 1:

                    transaction.hide(newsFragment);
                    transaction.show(maillistFragment);
                    transaction.hide(workBenchFragment);
                    transaction.hide(statisticsFragment);
                    transaction.hide(moreFragment);
                    toolbar.setVisibility(View.VISIBLE);
                    tvToolTitle.setText(getResources().getString(R.string.maillist));
                    transaction.commit();
                    break;
                case 2:

                    transaction.hide(newsFragment);
                    transaction.hide(maillistFragment);
                    transaction.show(workBenchFragment);
                    transaction.hide(statisticsFragment);
                    transaction.hide(moreFragment);
                    toolbar.setVisibility(View.GONE);
                    tvToolTitle.setText(getResources().getString(R.string.workbench));
                    transaction.commit();


                    break;
                case 3:

                    transaction.hide(newsFragment);
                    transaction.hide(maillistFragment);
                    transaction.hide(workBenchFragment);
                    transaction.show(statisticsFragment);
                    transaction.hide(moreFragment);
                    toolbar.setVisibility(View.VISIBLE);
                    tvToolTitle.setText(getResources().getString(R.string.statistics));
                    transaction.commit();

                    break;

                case 4:

                    transaction.hide(newsFragment);
                    transaction.hide(maillistFragment);
                    transaction.hide(workBenchFragment);
                    transaction.hide(statisticsFragment);
                    transaction.show(moreFragment);
                    toolbar.setVisibility(View.VISIBLE);
                    tvToolTitle.setText(getResources().getString(R.string.more));
                    transaction.commit();

                    break;


            }
        }

        @Override
        public void onRepeat(int index) {

        }
    };
}
