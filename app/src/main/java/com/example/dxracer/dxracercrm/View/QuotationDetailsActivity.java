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

//报价详情页面

public class QuotationDetailsActivity extends AppCompatActivity {

    private TabLayout course_search_tablayout;
    private ViewPager search_pager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    private Toolbar toolbar;
    public TextView toolbar_title;
    private Intent intent;
    String oppoBillStatus;
    String contractNo = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auotationdetails);
        intent = getIntent();

        initView();

        ClueInformationFragment fragment1 = new ClueInformationFragment();
        ContactsFragment fragment2 = new ContactsFragment();
        CommunicationRecordFragment fragment3 = new CommunicationRecordFragment();
        QuotationRecordFragment fragment4 = new QuotationRecordFragment();
        OrderFragment fragment5 = new OrderFragment();
        AddressFragament fragment6 = new AddressFragament();
        CollectionReceiptFragment fragment7 = new CollectionReceiptFragment();
        InstallationInformationFragment fragment8 = new InstallationInformationFragment();


        if(intent.getStringExtra("oppoStatus") != null){
            if(intent.getStringExtra("oppoStatus").equals("40")){
                course_search_tablayout.setTabMode(TabLayout.MODE_FIXED);
                oppoBillStatus = "";
                mFragments.add(fragment1);
                mFragments.add(fragment2);
                mFragments.add(fragment3);

                list.add("线索信息");
                list.add("联系人");
                list.add("沟通记录");

            }

            if(intent.getStringExtra("oppoStatus").equals("50")){
                course_search_tablayout.setTabMode(TabLayout.MODE_FIXED);
                oppoBillStatus = "";
                mFragments.add(fragment1);
                mFragments.add(fragment2);
                mFragments.add(fragment3);

                list.add("线索信息");
                list.add("联系人");
                list.add("沟通记录");

            }



            if(intent.getStringExtra("oppoStatus").equals("60")){
                course_search_tablayout.setTabMode(TabLayout.MODE_FIXED);
                oppoBillStatus = "";
                mFragments.add(fragment1);
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                list.add("线索信息");
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
            }

            if(intent.getStringExtra("oppoStatus").equals("00")){
                course_search_tablayout.setTabMode(TabLayout.MODE_FIXED);
                oppoBillStatus = "";
                mFragments.add(fragment1);
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                list.add("线索信息");
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
            }

            if(intent.getStringExtra("oppoStatus").equals("")){
                course_search_tablayout.setTabMode(TabLayout.MODE_FIXED);
                oppoBillStatus = "";
                mFragments.add(fragment1);
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                list.add("线索信息");
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
            }

        }

        if(intent.getStringExtra("contractStatus")!= null){
            if(intent.getStringExtra("contractStatus").equals("70")){
                course_search_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                oppoBillStatus = "finished";
                contractNo = intent.getStringExtra("contractNo");
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                mFragments.add(fragment5);
                mFragments.add(fragment6);
                mFragments.add(fragment7);
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
                list.add("订单信息");
                list.add("地址列表");
                list.add("收款单据");
            }

            if(intent.getStringExtra("contractStatus").equals("80")){
                course_search_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                oppoBillStatus = "finished";
                contractNo = intent.getStringExtra("contractNo");
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                mFragments.add(fragment5);
                mFragments.add(fragment6);
                mFragments.add(fragment7);
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
                list.add("订单信息");
                list.add("地址列表");
                list.add("收款单据");
            }

            if(intent.getStringExtra("contractStatus").equals("90")){
                course_search_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                oppoBillStatus = "finished";
                contractNo = intent.getStringExtra("contractNo");
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                mFragments.add(fragment5);
                mFragments.add(fragment6);
                mFragments.add(fragment7);
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
                list.add("订单信息");
                list.add("地址列表");
                list.add("收款单据");
            }

            if(intent.getStringExtra("contractStatus").equals("100")){
                course_search_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                oppoBillStatus = "finished";
                contractNo = intent.getStringExtra("contractNo");
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                mFragments.add(fragment5);
                mFragments.add(fragment6);
                mFragments.add(fragment7);
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
                list.add("订单信息");
                list.add("地址列表");
                list.add("收款单据");
            }

            if(intent.getStringExtra("contractStatus").equals("200")){
                course_search_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                oppoBillStatus = "finished";
                contractNo = intent.getStringExtra("contractNo");
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                mFragments.add(fragment5);
                mFragments.add(fragment6);
                mFragments.add(fragment7);
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
                list.add("订单信息");
                list.add("地址列表");
                list.add("收款单据");
            }

            if(intent.getStringExtra("contractStatus").equals("300")){
                course_search_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                oppoBillStatus = "finished";
                contractNo = intent.getStringExtra("contractNo");
                mFragments.add(fragment2);
                mFragments.add(fragment3);
                mFragments.add(fragment4);
                mFragments.add(fragment5);
                mFragments.add(fragment6);
                mFragments.add(fragment7);
                mFragments.add(fragment8);
                list.add("联系人");
                list.add("沟通记录");
                list.add("报价记录");
                list.add("订单信息");
                list.add("地址列表");
                list.add("收款单据");
                list.add("安装信息");
            }
        }



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

    public String getoppoBillNo(){
        String oppoBillNo = intent.getStringExtra("oppoBillNo");
        return oppoBillNo;
    }

    public String getoppoBillStatus(){
        return  oppoBillStatus;
    }
    public String getcontractNo(){
        return contractNo;
    }
}
