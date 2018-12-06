package com.example.dxracer.dxracercrm.View;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Adapter.ContactsListAdapter;
import com.example.dxracer.dxracercrm.Interface.MaillistInterface;
import com.example.dxracer.dxracercrm.Model.MaillistModel;
import com.example.dxracer.dxracercrm.Presenter.ContactsFragmentPresenter;
import com.example.dxracer.dxracercrm.Presenter.MaillistPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.example.dxracer.dxracercrm.common.FloatingBarItemDecoration;
import com.example.dxracer.dxracercrm.common.IndexBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ContactsFragment extends Fragment implements MaillistInterface.View {

    //通讯录

    private ContactsFragmentPresenter presenter;
    public LinearLayoutManager mLayoutManager;

    public ContactsListAdapter mAdapter;
    public PopupWindow mOperationInfoDialog;
    public View mLetterHintView;
    public View view;
    public RecyclerViewEmptySupport mRecyclerView;
    public RefreshLayout refreshLayout;
    public  IndexBar indexBar;
    private Intent intent;
    public String leadNo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maillistfragment,container,false);
        presenter = new ContactsFragmentPresenter(this,this);
        presenter.fetchData();
        initView();
        return view;
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            App app = (App)getActivity().getApplication();
            if(app.isMaillisRefresh()){
                presenter.fetchData();
                app.setMaillisRefresh(false);
            }
        }
    }



    private void initView() {
        mRecyclerView = (RecyclerViewEmptySupport)view.findViewById(R.id.share_add_contact_recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
        indexBar = (IndexBar)view.findViewById(R.id.share_add_contact_sidebar);
    }


    private void initAdapter() {
        mAdapter = new ContactsListAdapter(LayoutInflater.from(getActivity()),
                presenter.mContactList);
        mAdapter.setOnContactsBeanClickListener(new ContactsListAdapter.OnContactsBeanClickListener() {
            @Override
            public void onContactsBeanClicked(MaillistModel.MaillistBean bean) {
                intent = new Intent(getActivity(),ContactsDetailsActivity.class);
                intent.putExtra("id",bean.getId());
                intent.putExtra("personName",bean.getPersonName());
                intent.putExtra("nickName",bean.getNickName());
                intent.putExtra("position",bean.getPosition());
                intent.putExtra("sex",bean.getSex());
                intent.putExtra("mobile",bean.getMobile());
                intent.putExtra("email",bean.getEmail());
                intent.putExtra("wechat",bean.getWechat());
                intent.putExtra("birthday",bean.getBirthday());
                intent.putExtra("BusinessCar",bean.getBusinessCard());
                intent.putExtra("leadNo",bean.getLeadNo());
                startActivity(intent);
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

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onNothingData() {

    }


}
