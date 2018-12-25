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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dxracer.dxracercrm.Model.AddressFragamentModel;
import com.example.dxracer.dxracercrm.Model.CollectionReceiptFragmentModel;
import com.example.dxracer.dxracercrm.Model.InstallationInformationModel;
import com.example.dxracer.dxracercrm.Model.OrderModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.QuotationRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.DateUtilsTool;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.AddressFragament;
import com.example.dxracer.dxracercrm.View.ClueInformationFragment;
import com.example.dxracer.dxracercrm.View.CollectionReceiptFragment;
import com.example.dxracer.dxracercrm.View.InstallationInformationFragment;
import com.example.dxracer.dxracercrm.View.OrderFragment;
import com.example.dxracer.dxracercrm.View.PrivateCue;
import com.example.dxracer.dxracercrm.View.QuotationRecordFragment;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;
import static com.example.dxracer.dxracercrm.View.PrivateCue.INTENT1;

public class InstallationInformationAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private InstallationInformationFragment context;
    private  List<InstallationInformationModel.installBean> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public InstallationInformationAdapter(List<InstallationInformationModel.installBean> data, InstallationInformationFragment context){
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.installationinformation_data, viewGroup, false);
        lineViewHolder bodyViewHolder = new lineViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  lineViewHolder){


            ((lineViewHolder) holder).installNo.setText(data.get(position).getInstallNo());
            Glide.with(context).load(Constant.imgurl+data.get(position).getSkuMainImg()).into(((lineViewHolder) holder).skuMainImg);

            ((lineViewHolder) holder).fcno.setText(data.get(position).getFcno());

            ((lineViewHolder) holder).quantity.setText(DateUtilsTool.Format(data.get(position).getQuantity()));
            ((lineViewHolder) holder).extendStr1.setText(data.get(position).getExtendStr1());
            ((lineViewHolder) holder).extendStr2.setText(data.get(position).getExtendStr2());
            ((lineViewHolder) holder).supplierPersonName.setText("安装人:  "+data.get(position).getSupplierPersonName());
            ((lineViewHolder) holder).supplierMobile.setText("安装人:  "+data.get(position).getSupplierMobile());
            ((lineViewHolder) holder).planTime.setText("指定安装时间:  "+data.get(position).getPlanTime());
            ((lineViewHolder) holder).installFee.setText("¥"+DateUtilsTool.Format(data.get(position).getInstallFee()));
            ((lineViewHolder) holder).finishedTime.setText(data.get(position).getFinishedTime());

            if(data.get(position).getInstallImg().length()!=0){

                ((lineViewHolder) holder).installImg.setText("点击查看");
            }else {
                ((lineViewHolder) holder).installImg.setText("未上传");
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
        private TextView installNo;
        private ImageView skuMainImg;
        private TextView fcno;
        private TextView quantity;
        private TextView extendStr1;
        private TextView extendStr2;
        private TextView supplierPersonName;
        private TextView supplierMobile;
        private TextView planTime;
        private TextView installFee;
        private TextView finishedTime;
        private TextView installImg;



        public lineViewHolder(View itemView) {
            super(itemView);
            installNo = (TextView) itemView.findViewById(R.id.installNo);
            skuMainImg = (ImageView) itemView.findViewById(R.id.skuMainImg);
            fcno = (TextView) itemView.findViewById(R.id.fcno);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            extendStr1 = (TextView) itemView.findViewById(R.id.extendStr1);
            extendStr2 = (TextView) itemView.findViewById(R.id.extendStr2);
            supplierPersonName = (TextView) itemView.findViewById(R.id.supplierPersonName);
            supplierMobile = (TextView) itemView.findViewById(R.id.supplierMobile);
            planTime = (TextView) itemView.findViewById(R.id.planTime);
            installFee = (TextView) itemView.findViewById(R.id.installFee);
            finishedTime = (TextView) itemView.findViewById(R.id.finishedTime);
            installImg = (TextView) itemView.findViewById(R.id.installImg);


        }
    }
}
