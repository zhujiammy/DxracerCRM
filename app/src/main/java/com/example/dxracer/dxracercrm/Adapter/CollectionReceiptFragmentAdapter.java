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

import com.example.dxracer.dxracercrm.Model.AddressFragamentModel;
import com.example.dxracer.dxracercrm.Model.CollectionReceiptFragmentModel;
import com.example.dxracer.dxracercrm.Model.OrderModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.QuotationRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.DateUtilsTool;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.AddressFragament;
import com.example.dxracer.dxracercrm.View.ClueInformationFragment;
import com.example.dxracer.dxracercrm.View.CollectionReceiptFragment;
import com.example.dxracer.dxracercrm.View.OrderFragment;
import com.example.dxracer.dxracercrm.View.PrivateCue;
import com.example.dxracer.dxracercrm.View.QuotationRecordFragment;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class CollectionReceiptFragmentAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private CollectionReceiptFragment context;
    private  List<CollectionReceiptFragmentModel.CollectionReceipt> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public CollectionReceiptFragmentAdapter(List<CollectionReceiptFragmentModel.CollectionReceipt> data, CollectionReceiptFragment context){
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.collectionreceiptfragment_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){


            ((lineViewHolder) holder).contractNo.setText(data.get(position).getContractNo());

            ((lineViewHolder) holder).paymentType.setText(data.get(position).getPaymentType());
            if(data.get(position).getPaymentStatus().equals("new")){
                ((lineViewHolder) holder).paymentStatus.setText("已创建");
            }
            if(data.get(position).getPaymentStatus().equals("finished")){
                ((lineViewHolder) holder).paymentStatus.setText("已收款");
            }
            if(data.get(position).getPaymentStatus().equals("created")){
                ((lineViewHolder) holder).paymentStatus.setText("待收款");
            }
            ((lineViewHolder) holder).contractFee.setText("¥"+DateUtilsTool.Format(data.get(position).getContractFee()));
            ((lineViewHolder) holder).paymentCompany.setText(data.get(position).getPaymentCompany());
            ((lineViewHolder) holder).transNo.setText(data.get(position).getTransNo());
            ((lineViewHolder) holder).actFee.setText("¥"+DateUtilsTool.Format(data.get(position).getActFee()));
            ((lineViewHolder) holder).planReceiveDate.setText("最晚收款日期:  "+data.get(position).getPlanReceiveDate());
            ((lineViewHolder) holder).receiveDate.setText("收款校验日期:  "+data.get(position).getReceiveDate());
            ((lineViewHolder) holder).createDate.setText("创建日期:  "+data.get(position).getCreateDate());

            if(data.get(position).getFinalType().equals("after")){
                ((lineViewHolder) holder).finalType.setText("出货后"+data.get(position).getFinalDay()+"天");
            }


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
        private TextView contractNo;
        private TextView paymentType;
        private TextView paymentStatus;
        private TextView contractFee;
        private TextView paymentCompany;
        private TextView transNo;
        private TextView actFee;
        private TextView planReceiveDate;
        private TextView receiveDate;
        private TextView createDate;
        private TextView finalType;



        public lineViewHolder(View itemView) {
            super(itemView);
            contractNo = (TextView) itemView.findViewById(R.id.contractNo);
            paymentType = (TextView) itemView.findViewById(R.id.paymentType);
            paymentStatus = (TextView) itemView.findViewById(R.id.paymentStatus);
            contractFee = (TextView) itemView.findViewById(R.id.contractFee);
            paymentCompany = (TextView) itemView.findViewById(R.id.paymentCompany);
            transNo = (TextView) itemView.findViewById(R.id.transNo);
            actFee = (TextView) itemView.findViewById(R.id.actFee);
            planReceiveDate = (TextView) itemView.findViewById(R.id.planReceiveDate);
            receiveDate = (TextView) itemView.findViewById(R.id.receiveDate);
            createDate = (TextView) itemView.findViewById(R.id.createDate);
            finalType = (TextView) itemView.findViewById(R.id.finalType);


        }
    }
}
