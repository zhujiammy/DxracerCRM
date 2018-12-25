package com.example.dxracer.dxracercrm.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dxracer.dxracercrm.Adapter.AddressFragamentAdapter;
import com.example.dxracer.dxracercrm.Adapter.OrderFragmentAdapter;
import com.example.dxracer.dxracercrm.Interface.OrderFragmentInterface;
import com.example.dxracer.dxracercrm.Interface.PublicInterface;
import com.example.dxracer.dxracercrm.Presenter.AddressFragamentPresenter;
import com.example.dxracer.dxracercrm.Presenter.OrderFragmentPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class AddressFragament extends Fragment implements PublicInterface.View {

    //地址列表
    private View view;
    public RecyclerViewEmptySupport recyclerView;
    public RefreshLayout refreshLayout;
    private AddressFragamentPresenter presenter;
    public AddressFragamentAdapter adapter;
    public static final int  INTENT=1004;
    public String contractNo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addressfragament,container,false);
        presenter = new AddressFragamentPresenter(this,this);
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
        contractNo = ((QuotationDetailsActivity)context).getcontractNo();
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
    public void onResume() {
        App app = (App)getActivity().getApplication();
        if(app.isIseditaddressRefresh()){
            refreshLayout.autoRefresh();
            app.setIseditaddressRefresh(false);
        }
        super.onResume();
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
