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
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.QuotationRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.ClueInformationFragment;
import com.example.dxracer.dxracercrm.View.PrivateCue;
import com.example.dxracer.dxracercrm.View.QuotationRecordFragment;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class QuotationRecordAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private QuotationRecordFragment context;
    private  List<QuotationRecordModel.QuotationRecorBean> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public QuotationRecordAdapter(List<QuotationRecordModel.QuotationRecorBean> data, QuotationRecordFragment context){
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quotationrecord_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){
            ((lineViewHolder) holder).auditPersonName.setText(data.get(position).getAuditPersonName());
            if(data.get(position).getOppoBillStatus().equals("pendding")){
                ((lineViewHolder) holder).oppoBillStatus.setText("已审核");
            }

            if(data.get(position).getOppoBillStatus().equals("confirmed")){
                ((lineViewHolder) holder).oppoBillStatus.setText("已确认");
            }
            if(data.get(position).getOppoBillStatus().equals("closed")){
                ((lineViewHolder) holder).oppoBillStatus.setText("已作废");
                ((lineViewHolder) holder).oppoBillStatus.setTextColor(context.getResources().getColor(R.color.red));
            }

            if(data.get(position).getOppoBillStatus().equals("created")){
                ((lineViewHolder) holder).oppoBillStatus.setText("待审核");
                ((lineViewHolder) holder).oppoBillStatus.setTextColor(context.getResources().getColor(R.color.red));
            }

            if(data.get(position).getOppoBillStatus().equals("finished")){
                ((lineViewHolder) holder).oppoBillStatus.setText("已完成");
                ((lineViewHolder) holder).oppoBillStatus.setTextColor(context.getResources().getColor(R.color.blue1));
            }
            ((lineViewHolder) holder).oppoCreatePersonName.setText(data.get(position).getOppoCreatePersonName());
            ((lineViewHolder) holder).oppoPriceNo.setText(data.get(position).getOppoBillNo());
            ((lineViewHolder) holder).auditTime.setText("报价日期: "+data.get(position).getAuditTime());
            ((lineViewHolder) holder).supplierCompanyName.setText("供应商公司名称:  "+data.get(position).getSupplierCompanyName());
            ((lineViewHolder) holder).supplierContactsPersonName.setText("供应商联系人姓名:  "+data.get(position).getSupplierContactsPersonName());
            ((lineViewHolder) holder).supplierContactsPersonMobile.setText("供应商联系人电话:  "+data.get(position).getSupplierContactsPersonMobile());
            ((lineViewHolder) holder).dealerCompanyName.setText("采购方公司名称:  "+data.get(position).getDealerCompanyName());
            ((lineViewHolder) holder).dealerContactsPersonName.setText("采购方联系人姓名:  "+data.get(position).getDealerContactsPersonName());
            ((lineViewHolder) holder).oppoBillTotalFee.setText("产品费用:  ¥"+data.get(position).getOppoBillTotalFee());
            ((lineViewHolder) holder).discountPoint.setText("产品折扣率:  "+data.get(position).getDiscountPoint()+"%");
            ((lineViewHolder) holder).oppoProductTotalFee.setText("报价总费用:  ¥"+data.get(position).getOppoProductTotalFee());
            //更多
            ((lineViewHolder)holder).more_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //更多
                    context.showPopwindows(data,position);
                }
            });
        }
        holder.itemView.setTag(position);

    }


    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 1;
        }
        return data.size();
    }


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
        private TextView auditPersonName;
        private TextView oppoBillStatus;
        private TextView oppoCreatePersonName;
        private TextView contactsCommunicateNum;
        private TextView auditTime;
        private TextView oppoPriceNo;
        private TextView supplierCompanyName;
        private TextView supplierContactsPersonName;
        private TextView supplierContactsPersonMobile;
        private TextView dealerCompanyName;
        private TextView dealerContactsPersonName;
        private TextView oppoBillTotalFee;
        private TextView discountPoint;
        private TextView oppoProductTotalFee;

        private TextView edit;
        private TextView more_btn;

        public lineViewHolder(View itemView) {
            super(itemView);
            auditPersonName = (TextView) itemView.findViewById(R.id.auditPersonName);
            oppoBillStatus = (TextView) itemView.findViewById(R.id.oppoBillStatus);
            oppoCreatePersonName = (TextView) itemView.findViewById(R.id.oppoCreatePersonName);
            contactsCommunicateNum = (TextView) itemView.findViewById(R.id.contactsCommunicateNum);
            auditTime = (TextView) itemView.findViewById(R.id.auditTime);
            oppoPriceNo = (TextView) itemView.findViewById(R.id.oppoPriceNo);
            supplierCompanyName = (TextView) itemView.findViewById(R.id.supplierCompanyName);
            supplierContactsPersonName = (TextView) itemView.findViewById(R.id.supplierContactsPersonName);
            supplierContactsPersonMobile = (TextView) itemView.findViewById(R.id.supplierContactsPersonMobile);
            dealerContactsPersonName = (TextView) itemView.findViewById(R.id.dealerContactsPersonName);
            dealerCompanyName = (TextView) itemView.findViewById(R.id.dealerCompanyName);
            oppoBillTotalFee = (TextView) itemView.findViewById(R.id.oppoBillTotalFee);
            discountPoint = (TextView) itemView.findViewById(R.id.discountPoint);
            oppoProductTotalFee = (TextView) itemView.findViewById(R.id.oppoProductTotalFee);
            edit = (TextView) itemView.findViewById(R.id.edit);
            more_btn = (TextView) itemView.findViewById(R.id.more_btn);

        }
    }
}
