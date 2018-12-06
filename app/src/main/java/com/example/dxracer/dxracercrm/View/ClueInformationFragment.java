package com.example.dxracer.dxracercrm.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dxracer.dxracercrm.Adapter.ClueInformationAdapter;
import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Interface.ClueInformationInterface;
import com.example.dxracer.dxracercrm.Presenter.ClueInformationPresenter;
import com.example.dxracer.dxracercrm.Presenter.PublicCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

//线索信息
public class ClueInformationFragment extends Fragment implements ClueInformationInterface.View {

    public RecyclerViewEmptySupport recyclerView;
    public ClueInformationPresenter presenter;
    public RefreshLayout refreshLayout;
    public ClueInformationAdapter adapter;
    public static final int  INTENT=1004;
    private View view;
    public String leadNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.clueinformationfragment,container,false);
        presenter = new ClueInformationPresenter(this,this);
        initUI();
        return view;


    }

    private void initUI(){

        recyclerView = (RecyclerViewEmptySupport)view.findViewById(R.id.recyclerView);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        leadNo = ((QuotationDetailsActivity)context).getleadNo();
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
