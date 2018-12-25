package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dxracer.dxracercrm.Adapter.CommunicationRecord1Adapter;
import com.example.dxracer.dxracercrm.Adapter.CommunicationRecordAdapter;
import com.example.dxracer.dxracercrm.Interface.CommunicationRecordInterface;
import com.example.dxracer.dxracercrm.Presenter.CommunicationRecord1Presenter;
import com.example.dxracer.dxracercrm.Presenter.CommunicationRecordPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

//沟通记录

public class CommunicationRecordFragment extends Fragment implements CommunicationRecordInterface.View {

    public RecyclerViewEmptySupport recyclerView;
    public CommunicationRecord1Presenter presenter;
    public RefreshLayout refreshLayout;
    public Intent intent;
    public String leadNo;
    public int id;
    public CommunicationRecord1Adapter adapter;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.communicationrecordfragment,container,false);
        intent = getActivity().getIntent();
        leadNo = intent.getStringExtra("leadNo");
        id = intent.getIntExtra("id",0);
        presenter = new CommunicationRecord1Presenter(this,this);
        initUI();
        return view;
    }

    private void initUI(){
        recyclerView = (RecyclerViewEmptySupport) view.findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void onResume() {
        App app = (App)getActivity().getApplication();
        if(app.isIsrecordRefresh()){
            refreshLayout.autoRefresh();
            app.setIsrecordRefresh(false);
        }
        super.onResume();
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
