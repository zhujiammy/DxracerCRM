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

import com.example.dxracer.dxracercrm.Model.OrderModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.QuotationRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.DateUtilsTool;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.ClueInformationFragment;
import com.example.dxracer.dxracercrm.View.OrderDetails;
import com.example.dxracer.dxracercrm.View.OrderFragment;
import com.example.dxracer.dxracercrm.View.PrivateCue;
import com.example.dxracer.dxracercrm.View.QuotationRecordFragment;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class OrderFragmentAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private OrderFragment context;
    private  List<OrderModel.Beanorder> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public OrderFragmentAdapter(List<OrderModel.Beanorder> data, OrderFragment context){
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderfragment_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){

            ((lineViewHolder) holder).contractNo.setText(data.get(position).getContractNo());
            if(data.get(position).getOrderStatus().equals("created")){
                ((lineViewHolder) holder).orderStatus.setText("已创建");
            }

            ((lineViewHolder) holder).orderCreateDate.setText("创建日期: "+data.get(position).getOrderCreateDate());
            ((lineViewHolder) holder).dmsOrderNo.setText("供应商公司名称:  "+data.get(position).getDmsOrderNo());
            ((lineViewHolder) holder).orderTotalFee.setText("¥"+DateUtilsTool.Format(data.get(position).getOrderTotalFee()));
            ((lineViewHolder) holder).logistic.setText("物流公司:  "+data.get(position).getLogistic());
            ((lineViewHolder) holder).expressNo.setText("快递单号:  "+data.get(position).getExpressNo());
            ((lineViewHolder) holder).deliveryTime.setText("发货时间:  "+data.get(position).getDeliveryTime());
            //订单明细
            ((lineViewHolder)holder).orderdetails_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context.getActivity(),OrderDetails.class);
                    String orderId = String.valueOf(data.get(position).getId());
                    intent.putExtra("orderId",orderId);
                    context.startActivity(intent);
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
        private TextView contractNo;
        private TextView orderStatus;
        private TextView orderCreateDate;
        private TextView dmsOrderNo;
        private TextView orderTotalFee;
        private TextView logistic;
        private TextView expressNo;
        private TextView deliveryTime;

        private TextView orderdetails_btn;

        public lineViewHolder(View itemView) {
            super(itemView);
            contractNo = (TextView) itemView.findViewById(R.id.contractNo);
            orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
            orderCreateDate = (TextView) itemView.findViewById(R.id.orderCreateDate);
            dmsOrderNo = (TextView) itemView.findViewById(R.id.dmsOrderNo);
            orderTotalFee = (TextView) itemView.findViewById(R.id.orderTotalFee);
            logistic = (TextView) itemView.findViewById(R.id.logistic);
            expressNo = (TextView) itemView.findViewById(R.id.expressNo);
            deliveryTime = (TextView) itemView.findViewById(R.id.deliveryTime);

            orderdetails_btn = (TextView) itemView.findViewById(R.id.orderdetails_btn);

        }
    }
}
