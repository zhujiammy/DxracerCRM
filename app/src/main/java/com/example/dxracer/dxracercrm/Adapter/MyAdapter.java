package com.example.dxracer.dxracercrm.Adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Model.HomeIconModel;
import com.example.dxracer.dxracercrm.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    //数据源
    private List<HomeIconModel> dataList;

    //构造函数
    public MyAdapter(List<HomeIconModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
            return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            //其他条目中的逻辑在此
            ((BodyViewHolder) viewHolder).textView.setText(dataList.get(position).getText());
            ((BodyViewHolder) viewHolder).imageView.setImageBitmap(dataList.get(position).getBitmap());

    }

    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size();
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
        return position;
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        public BodyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
            imageView = (ImageView) itemView.findViewById(R.id.icon_img);
        }

    }
}
