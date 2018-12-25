package com.example.dxracer.dxracercrm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Model.ContractFormationModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.DateUtilsTool;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.ContractFormationActivity;
import com.example.dxracer.dxracercrm.View.PrivateCue;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class ContractFormationAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private ContractFormationActivity context;
    private  List<ContractFormationModel.FormationBean> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public ContractFormationAdapter(List<ContractFormationModel.FormationBean> data, ContractFormationActivity context){
        this.context = context;
        this.data = data;
    }
    private OnitemClickListener onitemClickListener=null;

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    @Override
    public void onClick(View v) {
        if(onitemClickListener!=null){
            onitemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public static interface OnitemClickListener{
        void onItemClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view_tab, viewGroup, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        View baseView;
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contractformation_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){

            ((lineViewHolder) holder).supplierName.setText(data.get(position).getSupplierName());
            ((lineViewHolder) holder).contractNo.setText(data.get(position).getContractNo());
            ((lineViewHolder) holder).leadNo.setText(data.get(position).getLeadNo());
            ((lineViewHolder) holder).oppoBillNo.setText(data.get(position).getOppoBillNo());
            ((lineViewHolder) holder).opppPriceNo.setText("报价单号:   "+data.get(position).getOpppPriceNo());
            ((lineViewHolder) holder).createPerson.setText(data.get(position).getCreatePerson());
            ((lineViewHolder) holder).contractFee.setText("  ¥"+DateUtilsTool.Format(data.get(position).getContractFee()));
            ((lineViewHolder) holder).paymentDeposit.setText("  ¥"+DateUtilsTool.Format(data.get(position).getPaymentDeposit()));
            ((lineViewHolder) holder).paymentFinal.setText("   ¥"+DateUtilsTool.Format(data.get(position).getPaymentFinal()));
            ((lineViewHolder) holder).createDate.setText("创建日期:  "+data.get(position).getCreateDate());

            ((lineViewHolder) holder).genDate.setText("合同生成日期:  "+data.get(position).getGenDate());
            ((lineViewHolder) holder).sendDate.setText("合同邮寄日期:  "+data.get(position).getSendDate());
            ((lineViewHolder) holder).sendLogistic.setText("快递公司:  "+data.get(position).getSendLogistic());
            ((lineViewHolder) holder).sendExpressNo.setText("快递单号:  "+data.get(position).getSendExpressNo());

            ((lineViewHolder) holder).signDate.setText("合同回签日期:  "+data.get(position).getSignDate());
            ((lineViewHolder) holder).excuteDate.setText("合同执行日期:  "+data.get(position).getExcuteDate());
            ((lineViewHolder) holder).finishedDate.setText("合同完成日期:  "+data.get(position).getFinishedDate());
            ((lineViewHolder) holder).contractManagePerson.setText("合同管理人:  "+data.get(position).getContractManagePerson());
            ((lineViewHolder) holder).contractManagePlace.setText("合同存放位置:  "+data.get(position).getContractManagePlace());

            if(context.intent.getStringExtra("contractStatus").equals("80")){
                ((lineViewHolder) holder).sendDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).sendLogistic.setVisibility(View.GONE);
                ((lineViewHolder) holder).sendExpressNo.setVisibility(View.GONE);

                ((lineViewHolder) holder).signDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).excuteDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).finishedDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).contractManagePerson.setVisibility(View.GONE);
                ((lineViewHolder) holder).contractManagePlace.setVisibility(View.GONE);
            }
            if(context.intent.getStringExtra("contractStatus").equals("90")){
                ((lineViewHolder) holder).signDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).excuteDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).finishedDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).contractManagePerson.setVisibility(View.GONE);
                ((lineViewHolder) holder).contractManagePlace.setVisibility(View.GONE);

                ((lineViewHolder) holder).opppPriceNo.setVisibility(View.GONE);
                ((lineViewHolder) holder).Mailcontract_btn.setVisibility(View.GONE);
                ((lineViewHolder)holder).Voidcontract_btn.setVisibility(View.GONE);
            }

            if(context.intent.getStringExtra("contractStatus").equals("100")){
                ((lineViewHolder) holder).sendLogistic.setVisibility(View.GONE);
                ((lineViewHolder) holder).sendExpressNo.setVisibility(View.GONE);
                ((lineViewHolder) holder).excuteDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).finishedDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).Mailcontract_btn.setVisibility(View.GONE);
                ((lineViewHolder)holder).Voidcontract_btn.setVisibility(View.GONE);

                ((lineViewHolder)holder).paymentDeposit.setVisibility(View.GONE);
                ((lineViewHolder)holder).paymentFinal.setVisibility(View.GONE);

                ((lineViewHolder)holder).paymentDeposit_lin.setVisibility(View.GONE);
                ((lineViewHolder)holder).paymentFinal_lin.setVisibility(View.GONE);

                ((lineViewHolder)holder).leadNo_lin.setVisibility(View.VISIBLE);
                ((lineViewHolder)holder).oppoBillNo_lin.setVisibility(View.VISIBLE);
            }

            if(context.intent.getStringExtra("contractStatus").equals("300")){
                ((lineViewHolder) holder).genDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).sendDate.setVisibility(View.GONE);
                ((lineViewHolder) holder).sendLogistic.setVisibility(View.GONE);
                ((lineViewHolder) holder).sendExpressNo.setVisibility(View.GONE);
                ((lineViewHolder) holder).opppPriceNo.setVisibility(View.GONE);
                ((lineViewHolder) holder).Mailcontract_btn.setVisibility(View.GONE);
                ((lineViewHolder)holder).Voidcontract_btn.setVisibility(View.GONE);
            }

            //邮寄合同
            ((lineViewHolder) holder).Mailcontract_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.showPopwindowsMailcontract(data,position);
                }
            });



            //作废合同
            ((lineViewHolder)holder).Voidcontract_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //更多
                   context.showPopwindowscancel(data,position);

                }
            });
        }

        holder.itemView.setTag(position);


    }


    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     * @return
     */
    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 1;
        }
        return data.size();
    }
    /**
     *
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //如果是0，就是头，否则则是其他的item

        if (data.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        //如果有数据，则使用ITEM的布局
        return VIEW_TYPE_ITEM;
    }

    public class lineViewHolder extends RecyclerView.ViewHolder{
        private TextView supplierName;
        private TextView contractNo;
        private TextView opppPriceNo;
        private TextView createPerson;
        private TextView contractFee;
        private TextView paymentDeposit;
        private TextView paymentFinal;
        private TextView createDate;
        private TextView genDate;
        private TextView sendDate;
        private TextView sendLogistic;
        private TextView sendExpressNo;

        private TextView signDate;
        private TextView excuteDate;
        private TextView finishedDate;
        private TextView contractManagePerson;
        private TextView contractManagePlace;

        private TextView leadNo;
        private TextView oppoBillNo;

        private TextView Mailcontract_btn;
        private TextView Voidcontract_btn;

        private LinearLayout leadNo_lin;
        private LinearLayout oppoBillNo_lin;
        private LinearLayout paymentDeposit_lin;
        private LinearLayout paymentFinal_lin;

        public lineViewHolder(View itemView) {
            super(itemView);
            supplierName = (TextView) itemView.findViewById(R.id.supplierName);
            contractNo = (TextView) itemView.findViewById(R.id.contractNo);
            opppPriceNo = (TextView) itemView.findViewById(R.id.opppPriceNo);
            createPerson = (TextView) itemView.findViewById(R.id.createPerson);
            contractFee = (TextView) itemView.findViewById(R.id.contractFee);
            paymentDeposit = (TextView) itemView.findViewById(R.id.paymentDeposit);
            paymentFinal = (TextView) itemView.findViewById(R.id.paymentFinal);
            createDate = (TextView) itemView.findViewById(R.id.createDate);
            genDate = (TextView) itemView.findViewById(R.id.genDate);
            sendDate = (TextView) itemView.findViewById(R.id.sendDate);
            sendLogistic = (TextView) itemView.findViewById(R.id.sendLogistic);
            sendExpressNo = (TextView) itemView.findViewById(R.id.sendExpressNo);

            signDate = (TextView) itemView.findViewById(R.id.signDate);
            excuteDate = (TextView) itemView.findViewById(R.id.excuteDate);
            finishedDate = (TextView) itemView.findViewById(R.id.finishedDate);
            contractManagePerson = (TextView) itemView.findViewById(R.id.contractManagePerson);
            contractManagePlace = (TextView) itemView.findViewById(R.id.contractManagePlace);
            leadNo = (TextView) itemView.findViewById(R.id.leadNo);
            oppoBillNo = (TextView) itemView.findViewById(R.id.oppoBillNo);

            leadNo_lin = (LinearLayout) itemView.findViewById(R.id.leadNo_lin);
            oppoBillNo_lin = (LinearLayout) itemView.findViewById(R.id.oppoBillNo_lin);
            paymentDeposit_lin = (LinearLayout) itemView.findViewById(R.id.paymentDeposit_lin);
            paymentFinal_lin = (LinearLayout) itemView.findViewById(R.id.paymentFinal_lin);
            //点击按钮
            Mailcontract_btn = (TextView) itemView.findViewById(R.id.Mailcontract_btn);
            Voidcontract_btn = (TextView) itemView.findViewById(R.id.Voidcontract_btn);


        }
    }
}
