package com.example.dxracer.dxracercrm.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Adapter.AddressFragamentAdapter;
import com.example.dxracer.dxracercrm.Adapter.OrderDetailsAdapter;
import com.example.dxracer.dxracercrm.Adapter.OrderFragmentAdapter;
import com.example.dxracer.dxracercrm.Interface.OrderFragmentInterface;
import com.example.dxracer.dxracercrm.Interface.PublicInterface;
import com.example.dxracer.dxracercrm.Presenter.AddressFragamentPresenter;
import com.example.dxracer.dxracercrm.Presenter.OrderDetailsPresenter;
import com.example.dxracer.dxracercrm.Presenter.OrderFragmentPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class OrderDetails extends AppCompatActivity implements PublicInterface.View {

    //订单明细
    public RecyclerViewEmptySupport recyclerView;
    public RefreshLayout refreshLayout;
    private OrderDetailsPresenter presenter;
    public OrderDetailsAdapter adapter;
    public static final int  INTENT=1004;
    public String orderId;
    public Intent intent ;
    private Toolbar toolbar;
    public TextView toolbar_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetails);
        intent = getIntent();
        presenter = new OrderDetailsPresenter(this,this);
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
        recyclerView = (RecyclerViewEmptySupport)findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.loadListData();
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
