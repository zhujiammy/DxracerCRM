package com.example.dxracer.dxracercrm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.R;

import org.w3c.dom.Text;

import java.util.List;

public class PrivateCueAdapter extends RecyclerView.Adapter{

    private Context context;
    private  List<PublicCueMode.Bean> data;

    public PrivateCueAdapter(List<PublicCueMode.Bean> data, Context context){
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.public_clue_data,viewGroup,false);
        return new lineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((lineViewHolder) holder).customerFullName.setText(data.get(position).getCustomerFullName());
        ((lineViewHolder) holder).customerIndustry.setText("行业: "+data.get(position).getCustomerIndustry());
        ((lineViewHolder) holder).customerProvinceName.setText("地址: "+data.get(position).getCustomerProvinceName()+data.get(position).getCustomerCityName()+data.get(position).getCustomerDistrictName());
        ((lineViewHolder) holder).customerScale.setText("行业: "+data.get(position).getCustomerScale());
        ((lineViewHolder) holder).createPersonName.setText("行业: "+data.get(position).getCreatePersonName());
        ((lineViewHolder) holder).contactPersonNum.setText("联系人数:  "+data.get(position).getContactPersonNum());
        ((lineViewHolder) holder).contactsCommunicateNum.setText("沟通次数:  "+data.get(position).getContactsCommunicateNum());
        ((lineViewHolder) holder).leadSource.setText("获得渠道:  "+data.get(position).getLeadSource());
        ((lineViewHolder) holder).customerShortName.setText("客户简称:  "+data.get(position).getCustomerShortName());
        ((lineViewHolder) holder).leadNo.setText("线索编号:  "+data.get(position).getLeadNo());
        ((lineViewHolder) holder).existDays.setText("已创建天数:  "+data.get(position).getExistDays());
        ((lineViewHolder) holder).leadGetDate.setText("获得日期:  "+data.get(position).getLeadGetDate());

    }


    @Override
    public int getItemCount() {
        Log.e("TAG", "111111111: "+data.size());
        return data.size();
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


        }
    }
}
