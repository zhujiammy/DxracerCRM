package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dxracer.dxracercrm.Adapter.CommunicationRecordAdapter;
import com.example.dxracer.dxracercrm.Interface.CommunicationRecordInterface;
import com.example.dxracer.dxracercrm.Presenter.CommunicationRecordPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class CommunicationRecordActivity extends AppCompatActivity implements CommunicationRecordInterface.View {

    //沟通记录

    private Toolbar toolbar;
    public RecyclerViewEmptySupport recyclerView;
    public CommunicationRecordPresenter presenter;
    public RefreshLayout refreshLayout;
    public Intent intent;
    public String leadNo;
    public int id;
    public CommunicationRecordAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communicationrecord);
        intent = getIntent();
        leadNo = intent.getStringExtra("leadNo");
        id = intent.getIntExtra("id",0);
        presenter = new CommunicationRecordPresenter(this,this);
        initUI();
    }

    private void initUI(){
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

        recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.LoadListData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                presenter.LoadMoreData();
            }
        });
    }

    @Override
    public void succeed() {

    }

    @Override
    protected void onResume() {
        App app = (App)getApplication();
        if(app.isIsrecordRefresh()){
            refreshLayout.autoRefresh();
            app.setIsrecordRefresh(false);
        }
        super.onResume();
    }

    @Override
    public void failed() {
        refreshLayout.finishRefresh(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        refreshLayout.finishRefresh();//结束刷新
    }

    @Override
    public void onLoadMore() {
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(true);
    }

    @Override
    public void onNothingData() {
        //没有更多数据了
        refreshLayout.finishLoadMoreWithNoMoreData();
    }
}
