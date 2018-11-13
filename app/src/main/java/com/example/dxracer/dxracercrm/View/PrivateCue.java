package com.example.dxracer.dxracercrm.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapters;
import com.example.dxracer.dxracercrm.Interface.PrivateCueInterface;
import com.example.dxracer.dxracercrm.Interface.PublicCueInterface;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Presenter.PrivateCuePresenter;
import com.example.dxracer.dxracercrm.Presenter.PublicCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Date;
import java.util.List;

public class PrivateCue extends Fragment implements PrivateCueInterface.View {

    private View view;
    public RecyclerViewEmptySupport recyclerView;
    public PrivateCuePresenter presenter;
    public RefreshLayout refreshLayout;
    public PublicCueAdapters adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.privatecue,container,false);
        presenter = new PrivateCuePresenter(getActivity(),PrivateCue.this,this);

        initUI();
/*        Log.e("TAG", "onCreateView: "+presenter.isMainThread() );
        adapter = new PublicCueAdapters(presenter.getData());
        recyclerView.setAdapter(adapter);*/
        return view;
    }

    private void initUI(){
        recyclerView = (RecyclerViewEmptySupport) view.findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
