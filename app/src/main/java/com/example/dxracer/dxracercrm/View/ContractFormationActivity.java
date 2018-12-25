package com.example.dxracer.dxracercrm.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.ContractFormationAdapter;
import com.example.dxracer.dxracercrm.Interface.ContractFormationInterface;
import com.example.dxracer.dxracercrm.Model.ContractFormationModel;
import com.example.dxracer.dxracercrm.Model.ContracttobeconfirmedModel;
import com.example.dxracer.dxracercrm.Presenter.ContractFormationPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContractFormationActivity extends AppCompatActivity implements ContractFormationInterface.View ,View.OnClickListener {

    public RecyclerViewEmptySupport recyclerView;
    public ContractFormationPresenter presenter;
    public RefreshLayout refreshLayout;
    public ContractFormationAdapter adapter;
    public static final int  INTENT1=1005;
    private Toolbar toolbar;
    public TextView toolbar_title;
    public Dialog dialog1;
    public Intent intent;
    private java.util.Calendar cal;
    private int mYear,mMonth,mDay;
    private  TextView sendDate;
    public String contractStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contractformation);
        intent = getIntent();
        contractStatus = intent.getStringExtra("contractStatus");
        //获取当前日期
        getDate();
        presenter = new ContractFormationPresenter(this,this);
        initUI();
    }


    private void getDate(){
        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
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
        recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.recyclerView);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case INTENT1:
                    if(data.getStringExtra("statue").equals("1")){
                        refreshLayout.autoRefresh();
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //合同作废
    public void showPopwindowscancel(final List<ContractFormationModel.FormationBean> modes, final int i){

        dialog1 = new Dialog(this);
        View contentView1 = LayoutInflater.from(this).inflate(
                R.layout.cancellationof_contract, null);
        dialog1.setContentView(contentView1);
        dialog1.setTitle("作废合同");
        dialog1.setCanceledOnTouchOutside(true);
        TextView contractNo = (TextView) contentView1.findViewById(R.id.contractNo);
        TextView supplierName = (TextView) contentView1.findViewById(R.id.supplierName);
        final EditText closeNote = (EditText) contentView1.findViewById(R.id.closeNote);
        Button close_btn = (Button) contentView1.findViewById(R.id.close_btn);
        Button save_btn = (Button) contentView1.findViewById(R.id.save_btn);
        contractNo.setText("合同编号:    "+modes.get(i).getContractNo());
        supplierName.setText("客户全称:    "+modes.get(i).getSupplierName());
        save_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String id = String.valueOf(modes.get(i).getId());
                if(TextUtils.isEmpty(closeNote.getText().toString())){
                    Toast.makeText(getApplicationContext(),"作废原因不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    presenter.cancel(id,closeNote.getText().toString());
                }

            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog1.dismiss();
            }
        });
        dialog1.show();


    }




    //邮寄合同
    public void showPopwindowsMailcontract(final List<ContractFormationModel.FormationBean> modes, final int i){

        dialog1 = new Dialog(this);
        View contentView1 = LayoutInflater.from(this).inflate(
                R.layout.mailcontract_dialog, null);
        dialog1.setContentView(contentView1);
        dialog1.setTitle("作废合同");
        dialog1.setCanceledOnTouchOutside(true);
        final EditText sendExpressNo = (EditText) contentView1.findViewById(R.id.sendExpressNo);
        sendDate = (TextView) contentView1.findViewById(R.id.sendDate);
        sendDate.setOnClickListener(this);
        sendDate.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        Button close_btn = (Button) contentView1.findViewById(R.id.close_btn);
        Button save_btn = (Button) contentView1.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String id = String.valueOf(modes.get(i).getId());
                if(TextUtils.isEmpty(sendExpressNo.getText().toString())){
                    Toast.makeText(getApplicationContext(),"合同寄出快递单号不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    presenter.Mailcontract(id,sendExpressNo.getText().toString(),sendDate.getText().toString());
                }

            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog1.dismiss();
            }
        });
        dialog1.show();


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

    @Override
    public void onClick(View v) {
        if(v == sendDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(ContractFormationActivity.this,
                    R.style.MyDatePickerDialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mYear = year;
                            mMonth = month;
                            mDay = dayOfMonth;
                            String datetime = mYear + "-" + (mMonth + 1) + "-" + mDay;
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                // 注意格式需要与上面一致，不然会出现异常
                                date = sdf.parse(datetime);
                                @SuppressLint("SimpleDateFormat") String sdate=(new SimpleDateFormat("yyyy-MM-dd")).format(date);
                                sendDate.setText(sdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
