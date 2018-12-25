package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Adapter.Adapter;
import com.example.dxracer.dxracercrm.R;

import java.util.ArrayList;

public class OpportunityDetailsActivity extends AppCompatActivity {

    private TabLayout course_search_tablayout;
    private ViewPager search_pager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    private Toolbar toolbar;
    public TextView toolbar_title;
    private Intent intent;

    //机会详情
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunitydetails);
        intent = getIntent();

        initView();
        ClueInformationFragment fragment1 = new ClueInformationFragment();
        ContactsFragment1 fragment2 = new ContactsFragment1();
        CommunicationRecordFragment fragment3 = new CommunicationRecordFragment();

        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        list.add("线索信息");
        list.add("联系人");
        list.add("沟通记录");
        course_search_tablayout.setupWithViewPager(search_pager);
        search_pager.setAdapter(new Adapter(getSupportFragmentManager(),mFragments,list));
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        course_search_tablayout = (TabLayout) findViewById(R.id.course_search_tablayout);
        search_pager = (ViewPager) findViewById(R.id.search_pager);
    }

    public String getleadNo(){
        String leadNo = intent.getStringExtra("leadNo");
        return leadNo;
    }

}
