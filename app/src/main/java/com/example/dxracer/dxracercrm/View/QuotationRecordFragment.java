package com.example.dxracer.dxracercrm.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.ClueInformationAdapter;
import com.example.dxracer.dxracercrm.Adapter.QuotationRecordAdapter;
import com.example.dxracer.dxracercrm.Interface.QuotationRecordInterface;
import com.example.dxracer.dxracercrm.Model.QuotationRecordModel;
import com.example.dxracer.dxracercrm.Model.SampleconFirmationMode;
import com.example.dxracer.dxracercrm.Presenter.ClueInformationPresenter;
import com.example.dxracer.dxracercrm.Presenter.QuotationRecordPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.MyBottomSheetDialog;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


//报价记录
public class QuotationRecordFragment extends Fragment implements QuotationRecordInterface.View {

    private View view;
    public RecyclerViewEmptySupport recyclerView;
    public QuotationRecordPresenter presenter;
    public RefreshLayout refreshLayout;
    public QuotationRecordAdapter adapter;
    public static final int  INTENT=1004;
    public String oppoBillNo;
    public String oppoBillStatus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quotationrecordfragment,container,false);
        presenter = new QuotationRecordPresenter(this,this);
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
        oppoBillNo = ((QuotationDetailsActivity)context).getoppoBillNo();
        oppoBillStatus= ((QuotationDetailsActivity)context).getoppoBillStatus();
        Log.e("TAG", "onAttach: "+oppoBillStatus );
    }



    //更多
    public void showPopwindows(final List<QuotationRecordModel.QuotationRecorBean> modes, final int i){

        final MyBottomSheetDialog dialog = new MyBottomSheetDialog(getContext());
        View box_view = LayoutInflater.from(getActivity()).inflate(R.layout.quotationrecordialog,null);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
        dialog.setContentView(box_view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        TextView Invalidquotation = (TextView) box_view.findViewById(R.id.Invalidquotation);
        TextView Customerconfirmation = (TextView) box_view.findViewById(R.id.Customerconfirmation);
        TextView Quotationcompleted = (TextView) box_view.findViewById(R.id.Quotationcompleted);
        TextView Previewquote = (TextView) box_view.findViewById(R.id.Previewquote);
        TextView cancel_btn = (TextView) box_view.findViewById(R.id.cancel_btn);
        TextView Delquotation = (TextView) box_view.findViewById(R.id.Delquotation);
        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {

                String id = String.valueOf(modes.get(i).getId());
                Intent intent ;
                switch (v.getId()) {
                    //删除报价
                    case R.id.Delquotation:
                        if(modes.get(i).getOppoBillStatus().equals("created")){
                            presenter.showNormalDialog(id,"确认删除吗？","oppo/price/apply/close");
                        }else {
                            Toast.makeText(getContext(),"只能删除未审核数据",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    //作废报价
                    case R.id.Invalidquotation:
                        if(modes.get(i).getOppoBillStatus().equals("created") || modes.get(i).getOppoBillStatus().equals("pendding")||modes.get(i).getOppoBillStatus().equals("confirmed")||modes.get(i).getOppoBillStatus().equals("finished")){
                            presenter.showNormalDialog(id,"确认作废该报价单吗？","oppo/price/apply/close");
                        }else if(modes.get(i).getOppoBillStatus().equals("closed")){
                            Toast.makeText(getContext(),"报价已作废",Toast.LENGTH_SHORT).show();
                        }


                        break;
                    //客户确认
                  case R.id.Customerconfirmation:

                      if(modes.get(i).getOppoBillStatus().equals("pendding")){
                          presenter.showNormalDialog(id,"确定将此报价标记为客户已确认吗？","oppo/price/apply/confirm");
                      }else {
                          Toast.makeText(getContext(),"报价还未审核，不允许确认",Toast.LENGTH_SHORT).show();
                      }

                        break;

                    //报价完成
                    case R.id.Quotationcompleted:
                        if(modes.get(i).getOppoBillStatus().equals("confirmed")){
                            presenter.showNormalDialog(id,"确定将此报价标记为已完成吗？","oppo/price/apply/finish");
                        }else {
                            Toast.makeText(getContext(),"报价还未确认，不允许完成",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    //预览报价
                    case R.id.Previewquote:
                        break;
                    case R.id.cancel_btn:
                        dialog.dismiss();
                        break;

                }
                dialog.dismiss();
            }
        };

        Invalidquotation.setOnClickListener(listener);
        cancel_btn.setOnClickListener(listener);
        Customerconfirmation.setOnClickListener(listener);
        Quotationcompleted.setOnClickListener(listener);
        Previewquote.setOnClickListener(listener);
        Delquotation.setOnClickListener(listener);
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
