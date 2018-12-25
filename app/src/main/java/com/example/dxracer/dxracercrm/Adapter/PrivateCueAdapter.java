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
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.PrivateCue;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class PrivateCueAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private PrivateCue context;
    private  List<PublicCueMode.Bean> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public PrivateCueAdapter(List<PublicCueMode.Bean> data, PrivateCue context){
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.private_clue_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){
            ((lineViewHolder) holder).customerFullName.setText(data.get(position).getCustomerFullName());
            ((lineViewHolder) holder).customerIndustry.setText("行业: "+data.get(position).getCustomerIndustry());
            ((lineViewHolder) holder).customerProvinceName.setText("地址: "+data.get(position).getCustomerProvinceName()+data.get(position).getCustomerCityName()+data.get(position).getCustomerDistrictName());
            ((lineViewHolder) holder).customerScale.setText("客户规模: "+data.get(position).getCustomerScale());
            ((lineViewHolder) holder).createPersonName.setText(data.get(position).getCreatePersonName());
            ((lineViewHolder) holder).contactPersonNum.setText("联系人数:  "+data.get(position).getContactPersonNum());
            ((lineViewHolder) holder).contactsCommunicateNum.setText("沟通次数:  "+data.get(position).getContactsCommunicateNum());
            ((lineViewHolder) holder).leadSource.setText("获得渠道:  "+data.get(position).getLeadSource());
            ((lineViewHolder) holder).customerShortName.setText("客户简称:  "+data.get(position).getCustomerShortName());
            ((lineViewHolder) holder).leadNo.setText("线索编号:  "+data.get(position).getLeadNo());
            ((lineViewHolder) holder).existDays.setText("已创建天数:  "+data.get(position).getExistDays());
            ((lineViewHolder) holder).leadGetDate.setText("获得日期:  "+data.get(position).getLeadGetDate());
            ((lineViewHolder) holder).followPersonName.setText("追踪人:  "+data.get(position).getFollowPersonName());

            ((lineViewHolder) holder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context.getApplicationContext(),AddPrivateCueActivity.class);
                    intent.putExtra("type","1");
                    intent.putExtra("leadGetDate",data.get(position).getLeadGetDate());
                    intent.putExtra("leadSource",data.get(position).getLeadSource());
                    intent.putExtra("customerShortName",data.get(position).getCustomerShortName());
                    intent.putExtra("customerFullName",data.get(position).getCustomerFullName());
                    intent.putExtra("customerScale",data.get(position).getCustomerScale());
                    intent.putExtra("customerIndustry",data.get(position).getCustomerIndustry());
                    intent.putExtra("customerAddress",data.get(position).getCustomerAddress());
                    intent.putExtra("customerIntroduce",data.get(position).getCustomerIntroduce());
                    intent.putExtra("customerProvinceCode",data.get(position).getCustomerProvinceCode());
                    intent.putExtra("customerCityCode",data.get(position).getCustomerCityCode());
                    intent.putExtra("customerDistrictCode",data.get(position).getCustomerDistrictCode());
                    intent.putExtra("customerProvinceName",data.get(position).getCustomerProvinceName());
                    intent.putExtra("customerCityName",data.get(position).getCustomerCityName());
                    intent.putExtra("customerDistrictName",data.get(position).getCustomerDistrictName());
                    intent.putExtra("followPersonName",data.get(position).getFollowPersonName());
                    context.startActivityForResult(intent,INTENT1);
                }
            });



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
        private TextView leadNo;
        private TextView existDays;
        private TextView contactPersonNum;
        private TextView contactsCommunicateNum;
        private TextView leadGetDate;
        private TextView leadSource;
        private TextView customerShortName;
        private TextView customerFullName;
        private TextView customerScale;
        private TextView customerIndustry;
        private TextView customerProvinceName;
        private TextView createPersonName;
        private TextView followPersonName;

        private TextView edit;
        private TextView more_btn;

        public lineViewHolder(View itemView) {
            super(itemView);
            leadNo = (TextView) itemView.findViewById(R.id.leadNo);
            existDays = (TextView) itemView.findViewById(R.id.existDays);
            contactPersonNum = (TextView) itemView.findViewById(R.id.contactPersonNum);
            contactsCommunicateNum = (TextView) itemView.findViewById(R.id.contactsCommunicateNum);
            leadGetDate = (TextView) itemView.findViewById(R.id.leadGetDate);
            leadSource = (TextView) itemView.findViewById(R.id.leadSource);
            customerShortName = (TextView) itemView.findViewById(R.id.customerShortName);
            customerFullName = (TextView) itemView.findViewById(R.id.customerFullName);
            customerScale = (TextView) itemView.findViewById(R.id.customerScale);
            customerIndustry = (TextView) itemView.findViewById(R.id.customerIndustry);
            customerProvinceName = (TextView) itemView.findViewById(R.id.customerProvinceName);
            createPersonName = (TextView) itemView.findViewById(R.id.createPersonName);
            followPersonName = (TextView) itemView.findViewById(R.id.followPersonName);
            edit = (TextView) itemView.findViewById(R.id.edit);
            more_btn = (TextView) itemView.findViewById(R.id.more_btn);

        }
    }
}
