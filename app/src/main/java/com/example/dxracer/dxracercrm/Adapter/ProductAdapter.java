package com.example.dxracer.dxracercrm.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dxracer.dxracercrm.Model.CommunicationRecordModel;
import com.example.dxracer.dxracercrm.Model.ProductModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.View.CommunicationRecordActivity;
import com.example.dxracer.dxracercrm.View.ProductActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //数据源
    private List<ProductModel> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public int count;
    private int layoutPosition; //记录当前点击位置
    public ProductActivity productActivity;
    //构造函数
    public ProductAdapter(List<ProductModel> dataList,ProductActivity productActivity) {
        this.data = dataList;
        this.productActivity = productActivity;
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_data, viewGroup, false);
        BodyViewHolder bodyViewHolder = new BodyViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        //其他条目中的逻辑在此
        if (viewHolder instanceof BodyViewHolder) {

            ((BodyViewHolder) viewHolder).fcno.setText(data.get(position).getFcno());
            ((BodyViewHolder) viewHolder).skuName.setText(data.get(position).getSkuName());
            ((BodyViewHolder) viewHolder).skuSalePrice.setText("¥ "+data.get(position).getSkuSalePrice());

            Glide.with(productActivity).load(Constant.imgurl+data.get(position).getSkuMainImg()).into(  ((BodyViewHolder) viewHolder).skuMainImg);
            ((BodyViewHolder) viewHolder).addcar.setTag(position);

            ((BodyViewHolder) viewHolder).addcar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productActivity.addCart(data,position);
                    data.get(position).setIscheck(true);
                    notifyDataSetChanged();

                }
            });

            if(data.get(position).isIscheck()){
                ((BodyViewHolder) viewHolder).addcar.setVisibility(View.INVISIBLE);
            }else {
                ((BodyViewHolder) viewHolder).addcar.setVisibility(View.VISIBLE);
            }

         /*   if(count == position){

            }else {
                ((BodyViewHolder) viewHolder).addcar.setVisibility(View.VISIBLE);
            }*/







        }

        viewHolder.itemView.setTag(position);


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

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private ImageView skuMainImg;
        private TextView fcno;
        private TextView skuName;
        private TextView skuSalePrice;
        private ImageView addcar;



        public BodyViewHolder(View itemView) {
            super(itemView);
            skuMainImg = (ImageView) itemView.findViewById(R.id.skuMainImg);
            fcno = (TextView) itemView.findViewById(R.id.fcno);
            skuName = (TextView) itemView.findViewById(R.id.skuName);
            skuSalePrice = (TextView) itemView.findViewById(R.id.skuSalePrice);
            addcar = (ImageView) itemView.findViewById(R.id.addcar);


        }

    }
}
