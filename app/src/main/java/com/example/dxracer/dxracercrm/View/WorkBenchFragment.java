package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.example.dxracer.dxracercrm.Adapter.MyAdapter;
import com.example.dxracer.dxracercrm.Interface.WorkBenchInterface;
import com.example.dxracer.dxracercrm.Presenter.WorkBenchPresenter;
import com.example.dxracer.dxracercrm.R;

public class WorkBenchFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView home_rv;
    private WorkBenchPresenter presenter;
    private LinearLayout publiccue_lin;
    private LinearLayout privatecue_lin;
    private LinearLayout Sampleconfirmation_lin;
    private LinearLayout Schemeconfirmed_lin;
    private LinearLayout Quotationconfirmed_lin;
    private LinearLayout Contracttobeconfirmed;
    private LinearLayout Opportunityhasbeencancelled;
    private LinearLayout Allopportunities;
    private LinearLayout Contractformation_lin;
    private LinearLayout Contractmailing_lin;
    private LinearLayout Executiocontract_lin;
    private LinearLayout contract_is_void_lin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.workbenchfragment,container,false);
        initUI();
        return view;
    }

    private void initUI(){
        publiccue_lin = (LinearLayout) view.findViewById(R.id.publiccue_lin);
        privatecue_lin = (LinearLayout) view.findViewById(R.id.privatecue_lin);
        Sampleconfirmation_lin = (LinearLayout) view.findViewById(R.id.Sampleconfirmation_lin);
        Schemeconfirmed_lin = (LinearLayout) view.findViewById(R.id.Schemeconfirmed_lin);
        Quotationconfirmed_lin = (LinearLayout) view.findViewById(R.id.Quotationconfirmed_lin);
        Contracttobeconfirmed = (LinearLayout) view.findViewById(R.id.Contracttobeconfirmed);
        Opportunityhasbeencancelled = (LinearLayout) view.findViewById(R.id.Opportunityhasbeencancelled);
        Allopportunities = (LinearLayout) view.findViewById(R.id.Allopportunities);
        Contractformation_lin = (LinearLayout) view.findViewById(R.id.Contractformation_lin);
        Contractmailing_lin = (LinearLayout) view.findViewById(R.id.Contractmailing_lin);
        Executiocontract_lin = (LinearLayout)view.findViewById(R.id.Executiocontract_lin);
        contract_is_void_lin = (LinearLayout)view.findViewById(R.id.contract_is_void_lin);
/*

        home_rv = (RecyclerView) view.findViewById(R.id.home_Rv);
        presenter = new WorkBenchPresenter(getContext());
        final MyAdapter adapter = new MyAdapter(presenter.getdata());
        home_rv.setAdapter(adapter);
        adapter.setOnitemClickListener(new MyAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.intentActivity(view,position);
            }
        });

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        home_rv.setLayoutManager(layoutManager);*/

        publiccue_lin.setOnClickListener(this);
        privatecue_lin.setOnClickListener(this);
        Sampleconfirmation_lin.setOnClickListener(this);
        Schemeconfirmed_lin.setOnClickListener(this);
        Quotationconfirmed_lin.setOnClickListener(this);
        Contracttobeconfirmed.setOnClickListener(this);
        Opportunityhasbeencancelled.setOnClickListener(this);
        Allopportunities.setOnClickListener(this);
        Contractformation_lin.setOnClickListener(this);
        Contractmailing_lin.setOnClickListener(this);
        Executiocontract_lin.setOnClickListener(this);
        contract_is_void_lin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent ;

        if(v == publiccue_lin){
            //公有机会
            intent = new Intent(getActivity(),PublicCue.class);
            startActivity(intent);
        }
        if(v == privatecue_lin){
            //私有机会
            intent = new Intent(getActivity(),PrivateCue.class);
            startActivity(intent);
        }
        if(v == Sampleconfirmation_lin){
            //样品待确认
            intent = new Intent(getActivity(),SampleconFirmationActivity.class);
            intent.putExtra("oppoStatus","40");
            intent.putExtra("title","样品确认");
            startActivity(intent);
        }

        if(v == Schemeconfirmed_lin){
            //方案待确认
            intent = new Intent(getActivity(),SampleconFirmationActivity.class);
            intent.putExtra("oppoStatus","50");
            intent.putExtra("title","方案确认");
            startActivity(intent);
        }

        if(v == Quotationconfirmed_lin){
            //报价待确认
            intent = new Intent(getActivity(),SampleconFirmationActivity.class);
            intent.putExtra("oppoStatus","60");
            intent.putExtra("title","报价确认");
            startActivity(intent);
        }

        if(v == Contracttobeconfirmed){
            //合同生成
            intent = new Intent(getActivity(),ContracttobeconfirmedActivity.class);
            startActivity(intent);

        }

        if(v == Opportunityhasbeencancelled){
            //作废机会
            intent = new Intent(getActivity(),SampleconFirmationActivity.class);
            intent.putExtra("oppoStatus","00");
            intent.putExtra("title","作废机会");
            startActivity(intent);
        }

        if(v == Allopportunities){
            //所有机会
            intent = new Intent(getActivity(),SampleconFirmationActivity.class);
            intent.putExtra("oppoStatus","");
            intent.putExtra("title","所有机会");
            startActivity(intent);
        }

        if(v == Contractformation_lin){
            //合同已生成
            intent = new Intent(getActivity(),ContractFormationActivity.class);
            intent.putExtra("contractStatus","80");
            intent.putExtra("title","合同已生成");
            startActivity(intent);
        }
        if(v == Contractmailing_lin){
            //合同已邮寄
            intent = new Intent(getActivity(),ContractFormationActivity.class);
            intent.putExtra("contractStatus","90");
            intent.putExtra("title","合同已邮寄");
            startActivity(intent);
        }

        if(v == Executiocontract_lin){
            //合同已邮寄
            intent = new Intent(getActivity(),ContractFormationActivity.class);
            intent.putExtra("contractStatus","300");
            intent.putExtra("title","合同执行完");
            startActivity(intent);
        }

        if(v == contract_is_void_lin){
            //合同已邮寄
            intent = new Intent(getActivity(),ContractFormationActivity.class);
            intent.putExtra("contractStatus","00");
            intent.putExtra("title","合同已作废");
            startActivity(intent);
        }
    }
}
