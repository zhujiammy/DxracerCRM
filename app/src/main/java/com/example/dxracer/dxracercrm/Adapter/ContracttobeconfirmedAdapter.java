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

import com.example.dxracer.dxracercrm.Model.ContracttobeconfirmedModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.ContracttobeconfirmedActivity;
import com.example.dxracer.dxracercrm.View.PrivateCue;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class ContracttobeconfirmedAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private ContracttobeconfirmedActivity context;
    private  List<ContracttobeconfirmedModel.ContractBean> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public ContracttobeconfirmedAdapter(List<ContracttobeconfirmedModel.ContractBean> data, ContracttobeconfirmedActivity context){
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contracttobeconfirmed_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){
            ((lineViewHolder) holder).supplierName.setText(data.get(position).getSupplierName());
            ((lineViewHolder) holder).contractNo.setText(data.get(position).getContractNo());
            ((lineViewHolder) holder).contractFee.setText("¥"+data.get(position).getContractFee());
            ((lineViewHolder) holder).createPerson.setText(data.get(position).getCreatePerson());
            ((lineViewHolder) holder).leadNo.setText("线索编号:  "+data.get(position).getLeadNo());
            ((lineViewHolder) holder).oppoBillNo.setText("机会编号:  "+data.get(position).getOppoBillNo());
            ((lineViewHolder) holder).opppPriceNo.setText("报价单号:  "+data.get(position).getOpppPriceNo());
            ((lineViewHolder) holder).createDate.setText("合同创建日期:  "+data.get(position).getCreateDate());


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
        private TextView contractNo;
        private TextView supplierName;
        private TextView leadNo;
        private TextView oppoBillNo;
        private TextView opppPriceNo;
        private TextView createPerson;
        private TextView createDate;
        private TextView contractFee;


        private TextView edit;
        private TextView more_btn;

        public lineViewHolder(View itemView) {
            super(itemView);
            leadNo = (TextView) itemView.findViewById(R.id.leadNo);
            supplierName = (TextView) itemView.findViewById(R.id.supplierName);
            contractNo = (TextView) itemView.findViewById(R.id.contractNo);
            oppoBillNo = (TextView) itemView.findViewById(R.id.oppoBillNo);
            opppPriceNo = (TextView) itemView.findViewById(R.id.opppPriceNo);
            createPerson = (TextView) itemView.findViewById(R.id.createPerson);
            createDate = (TextView) itemView.findViewById(R.id.createDate);
            contractFee = (TextView) itemView.findViewById(R.id.contractFee);
            edit = (TextView) itemView.findViewById(R.id.edit);
            more_btn = (TextView) itemView.findViewById(R.id.more_btn);

        }
    }
}
