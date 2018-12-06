package com.example.dxracer.dxracercrm.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.ContracttobeconfirmedAdapter;
import com.example.dxracer.dxracercrm.Interface.ContracttobeconfirmedInterface;
import com.example.dxracer.dxracercrm.Model.ContracttobeconfirmedModel;
import com.example.dxracer.dxracercrm.Model.QuotationRecordModel;
import com.example.dxracer.dxracercrm.Presenter.ContracttobeconfirmedPresenter;
import com.example.dxracer.dxracercrm.Presenter.PrivateCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.MyBottomSheetDialog;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.List;


//合同生成
public class ContracttobeconfirmedActivity extends AppCompatActivity implements ContracttobeconfirmedInterface.View {

    public RecyclerViewEmptySupport recyclerView;
    public ContracttobeconfirmedPresenter presenter;
    public RefreshLayout refreshLayout;
    public ContracttobeconfirmedAdapter adapter;
    public static final int  INTENT1=1005;
    private Toolbar toolbar;
    public TextView toolbar_title;
    public   Dialog dialog1;
    public  Spinner paymentPoint;
    public  Spinner paymentFinalDay;
    public  TextView Earnestmoney;
    public   TextView Tailmoney;
    public  TextView contractFee;
    public String contractFeestr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contracttobeconfirmed);

        presenter = new ContracttobeconfirmedPresenter(this,this);
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

    //更多
    public void showPopwindows(final List<ContracttobeconfirmedModel.ContractBean> modes, final int i){

        final MyBottomSheetDialog dialog = new MyBottomSheetDialog(ContracttobeconfirmedActivity.this,R.style.BottomSheetEdit);
        View box_view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contracttobeconfirmeddialog,null);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
        dialog.setContentView(box_view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        TextView Void_contract = (TextView) box_view.findViewById(R.id.Void_contract);
        TextView New_contacts = (TextView) box_view.findViewById(R.id.New_contacts);
        TextView New_communication_record = (TextView) box_view.findViewById(R.id.New_communication_record);
        TextView Setup_receipts = (TextView) box_view.findViewById(R.id.Setup_receipts);
        TextView cancel_btn = (TextView) box_view.findViewById(R.id.cancel_btn);
        TextView Contract_formation = (TextView) box_view.findViewById(R.id.Contract_formation);
        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {

                String id = String.valueOf(modes.get(i).getId());
                Intent intent ;
                switch (v.getId()) {
                    //作废合同
                    case R.id.Void_contract:
                        showPopwindowscancel(modes,i);
                        break;
                    //新增联系人
                    case R.id.New_contacts:
                        intent = new Intent(getApplicationContext(),AddContactsActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        startActivity(intent);
                        break;
                    //新增沟通记录
                    case R.id.New_communication_record:

                        intent = new Intent(getApplicationContext(),AddRecordActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        startActivity(intent);

                        break;

                    //设置收款
                    case R.id.Setup_receipts:
                        showPopwindowsPaymentmethod(modes,i);
                        break;
                    //生成合同
                    case R.id.Contract_formation:
                        presenter.showNormalDialog(id,"生成合同前，请确认收款方式，合同收件人，产品收货人等信息无误，确认生成合同吗？","contract/bill/pendding");
                        break;
                    case R.id.cancel_btn:
                        dialog.dismiss();
                        break;

                }
                dialog.dismiss();
            }
        };

        Void_contract.setOnClickListener(listener);
        cancel_btn.setOnClickListener(listener);
        New_contacts.setOnClickListener(listener);
        New_communication_record.setOnClickListener(listener);
        Setup_receipts.setOnClickListener(listener);
        Contract_formation.setOnClickListener(listener);
    }






    //合同作废
    public void showPopwindowscancel(final List<ContracttobeconfirmedModel.ContractBean> modes, final int i){

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



    //付款方式
    public void showPopwindowsPaymentmethod(final List<ContracttobeconfirmedModel.ContractBean> modes, final int i){

        dialog1 = new Dialog(this);
        View contentView1 = LayoutInflater.from(this).inflate(
                R.layout.paymentmethod, null);
        dialog1.setContentView(contentView1);
        dialog1.setTitle("付款方式");
        dialog1.setCanceledOnTouchOutside(true);
        TextView contractNo = (TextView) contentView1.findViewById(R.id.contractNo);
        contractFee = (TextView) contentView1.findViewById(R.id.contractFee);
        paymentPoint = (Spinner) contentView1.findViewById(R.id.paymentPoint);
        paymentFinalDay = (Spinner)contentView1.findViewById(R.id.paymentFinalDay);
        presenter.getDataListDepositratio();
        presenter.getDataLisDaysofpayment();
        Earnestmoney = (TextView) contentView1.findViewById(R.id.Earnestmoney);
        Tailmoney = (TextView) contentView1.findViewById(R.id.Tailmoney);


        final EditText closeNote = (EditText) contentView1.findViewById(R.id.closeNote);
        Button close_btn = (Button) contentView1.findViewById(R.id.close_btn);
        Button save_btn = (Button) contentView1.findViewById(R.id.save_btn);
        contractNo.setText("合同编号:    "+modes.get(i).getContractNo());
        contractFee.setText("合同总金额:     ¥"+modes.get(i).getContractFee());
        contractFeestr = String.valueOf(modes.get(i).getContractFee());
        save_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String id = String.valueOf(modes.get(i).getId());
                presenter.savePayment(id);

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
