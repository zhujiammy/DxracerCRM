package com.example.dxracer.dxracercrm.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Adapter.SampleconFirmationAdapter;
import com.example.dxracer.dxracercrm.Interface.SampleConfirmationInterface;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.SampleconFirmationMode;
import com.example.dxracer.dxracercrm.Presenter.PublicCuePresenter;
import com.example.dxracer.dxracercrm.Presenter.SampleconFirmationPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.HttpUtils;
import com.example.dxracer.dxracercrm.Tools.MyBottomSheetDialog;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

public class SampleconFirmationActivity extends AppCompatActivity implements SampleConfirmationInterface.View {

    public RecyclerViewEmptySupport recyclerView;

    public RefreshLayout refreshLayout;

    public static final int  INTENT=1004;
    private Toolbar toolbar;
    public TextView toolbar_title;
    public SampleconFirmationAdapter adapter;
    private SampleconFirmationPresenter presenter;
    public Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sampleconfirmation);
        intent = getIntent();
        presenter = new SampleconFirmationPresenter(this,this);
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
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(intent.getStringExtra("title"));
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
    protected void onResume() {
        super.onResume();
        App app = (App)getApplication();
        if(app.isRefresh()){
            refreshLayout.autoRefresh();
            app.setRefresh(false);
        }
    }

    //更多
    public void showPopwindows(final List<SampleconFirmationMode.SampleMode> modes, final int i, final String customerShortName){

        final MyBottomSheetDialog dialog = new MyBottomSheetDialog(SampleconFirmationActivity.this);
        View box_view = LayoutInflater.from(SampleconFirmationActivity.this).inflate(R.layout.popsample,null);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
        dialog.setContentView(box_view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        TextView add_contacts = (TextView) box_view.findViewById(R.id.add_contacts);
        TextView add_record = (TextView) box_view.findViewById(R.id.add_record);
        TextView Tochangeinto = (TextView) box_view.findViewById(R.id.Tochangeinto);
        TextView Completionconfirmation = (TextView) box_view.findViewById(R.id.Completionconfirmation);
        TextView addQuotation = (TextView) box_view.findViewById(R.id.addQuotation);
        if(intent.getStringExtra("oppoStatus").equals("60")){
            addQuotation.setVisibility(View.VISIBLE);
        }else {
            addQuotation.setVisibility(View.GONE);
        }
        Tochangeinto.setText("转到公有线索");
        TextView cancel_btn = (TextView) box_view.findViewById(R.id.cancel_btn);
        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent ;
                switch (v.getId()) {

                    //新增联系人
                    case R.id.add_contacts:
                        intent = new Intent(SampleconFirmationActivity.this,AddContactsActivity.class);
                        intent.putExtra("type","0");//新增联系人
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        startActivity(intent);
                        break;
                    //新增沟通记录
                    case R.id.add_record:
                        intent = new Intent(SampleconFirmationActivity.this,AddRecordActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        intent.putExtra("type","0");
                        startActivity(intent);
                        break;
                    //转入公有线索
                    case R.id.Tochangeinto:
                        intent = new Intent(getApplicationContext(),OpportunityCancellation.class);
                        intent.putExtra("oppno",modes.get(i).getOppoNo());
                        intent.putExtra("customerShortName",customerShortName);
                        String id = String.valueOf(modes.get(i).getId());
                        intent.putExtra("id",id);
                        startActivityForResult(intent,INTENT);
                        break;
                    case R.id.Completionconfirmation:
                        //完成当前阶段确认
                        String ID = String.valueOf(modes.get(i).getId());
                        presenter.showNormalDialog(ID);
                        break;
                    case R.id.addQuotation:
                        //新增报价单
                        intent = new Intent(getApplicationContext(),AddQuotationActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        intent.putExtra("oppno",modes.get(i).getOppoNo());
                        startActivityForResult(intent,INTENT);
                        break;
                    case R.id.cancel_btn:
                        dialog.dismiss();
                        break;

                }
                dialog.dismiss();
            }
        };

        add_contacts.setOnClickListener(listener);
        cancel_btn.setOnClickListener(listener);
        add_record.setOnClickListener(listener);
        Tochangeinto.setOnClickListener(listener);
        Completionconfirmation.setOnClickListener(listener);
        addQuotation.setOnClickListener(listener);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case INTENT:
                    if(data.getStringExtra("statue").equals("1")){
                        refreshLayout.autoRefresh();
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
