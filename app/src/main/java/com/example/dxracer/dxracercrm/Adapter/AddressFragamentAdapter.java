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
import com.example.dxracer.dxracercrm.Model.OrderModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.QuotationRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.DateUtilsTool;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.AddressFragament;
import com.example.dxracer.dxracercrm.View.ClueInformationFragment;
import com.example.dxracer.dxracercrm.View.EditAddress;
import com.example.dxracer.dxracercrm.View.OrderFragment;
import com.example.dxracer.dxracercrm.View.PrivateCue;
import com.example.dxracer.dxracercrm.View.QuotationRecordFragment;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class AddressFragamentAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private AddressFragament context;
    private  List<AddressFragamentModel.AddressBean> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public AddressFragamentAdapter(List<AddressFragamentModel.AddressBean> data, AddressFragament context){
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addressfragament_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){

            ((lineViewHolder) holder).contractNo.setText(data.get(position).getContractNo());

            ((lineViewHolder) holder).type.setText(data.get(position).getType());
            ((lineViewHolder) holder).person.setText(data.get(position).getPerson());
            ((lineViewHolder) holder).mobile.setText(data.get(position).getMobile());
            ((lineViewHolder) holder).Province_city.setText(data.get(position).getProviceName()+" "+data.get(position).getCityName()+" "+data.get(position).getDistictName());
            ((lineViewHolder) holder).addr.setText("详细地址:  "+data.get(position).getAddr());
            //订单明细
            ((lineViewHolder)holder).edit_addr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 Intent intent = new Intent(context.getActivity(),EditAddress.class);
                 intent.putExtra("contractNo",data.get(position).getContractNo());
                 intent.putExtra("type",data.get(position).getType());
                 intent.putExtra("person",data.get(position).getPerson());
                 intent.putExtra("mobile",data.get(position).getMobile());
                 intent.putExtra("address",data.get(position).getProviceName()+" "+data.get(position).getCityName()+" "+data.get(position).getDistictName());
                 intent.putExtra("addr",data.get(position).getAddr());
                 intent.putExtra("Provice",data.get(position).getProvice());
                 intent.putExtra("City",data.get(position).getCity());
                 intent.putExtra("Distict",data.get(position).getDistict());
                 String id = String.valueOf(data.get(position).getId());
                 intent.putExtra("id",id);
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
        private TextView type;
        private TextView person;
        private TextView mobile;
        private TextView Province_city;
        private TextView addr;

        private TextView edit_addr;

        public lineViewHolder(View itemView) {
            super(itemView);
            contractNo = (TextView) itemView.findViewById(R.id.contractNo);
            type = (TextView) itemView.findViewById(R.id.type);
            person = (TextView) itemView.findViewById(R.id.person);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            Province_city = (TextView) itemView.findViewById(R.id.Province_city);
            addr = (TextView) itemView.findViewById(R.id.addr);

            edit_addr = (TextView) itemView.findViewById(R.id.edit_addr);

        }
    }
}
