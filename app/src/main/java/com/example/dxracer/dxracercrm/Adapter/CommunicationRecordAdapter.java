package com.example.dxracer.dxracercrm.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Model.CommunicationRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.View.CommunicationRecordActivity;

import java.util.List;

public class CommunicationRecordAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //数据源
    private List<CommunicationRecordModel.Recor> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public CommunicationRecordActivity communicationRecordActivity;
    //构造函数
    public CommunicationRecordAdapter(List<CommunicationRecordModel.Recor> dataList,CommunicationRecordActivity communicationRecordActivity) {
        this.data = dataList;
        this.communicationRecordActivity = communicationRecordActivity;
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.communicationrecord_data, viewGroup, false);
        BodyViewHolder bodyViewHolder = new BodyViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        //其他条目中的逻辑在此
        if (viewHolder instanceof BodyViewHolder) {

            ((BodyViewHolder) viewHolder).contactsPersonName.setText(data.get(position).getContactsPersonName());
            ((BodyViewHolder) viewHolder).communicateType.setText(data.get(position).getCommunicateType());
            ((BodyViewHolder) viewHolder).createPersonName.setText(data.get(position).getCreatePersonName());
            ((BodyViewHolder) viewHolder).communicateTime.setText("跟进时间:  "+data.get(position).getCommunicateTime());
            ((BodyViewHolder) viewHolder).communicateStage.setText("跟进阶段:  "+data.get(position).getCommunicateStage());
            ((BodyViewHolder) viewHolder).communicateResult.setText("跟进结果:  "+data.get(position).getCommunicateResult());
            ((BodyViewHolder) viewHolder).createTime.setText("录入时间:  "+data.get(position).getCreateTime());


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
        private TextView contactsPersonName;
        private TextView createPersonName;
        private TextView communicateType;
        private TextView communicateTime;
        private TextView communicateStage;
        private TextView communicateResult;
        private TextView createTime;
        private TextView communicateFile;
        private TextView edit;


        public BodyViewHolder(View itemView) {
            super(itemView);
            contactsPersonName = (TextView) itemView.findViewById(R.id.contactsPersonName);
            createPersonName = (TextView) itemView.findViewById(R.id.createPersonName);
            communicateType = (TextView) itemView.findViewById(R.id.communicateType);
            communicateTime = (TextView) itemView.findViewById(R.id.communicateTime);
            communicateStage = (TextView) itemView.findViewById(R.id.communicateStage);
            communicateResult = (TextView) itemView.findViewById(R.id.communicateResult);
            createTime = (TextView) itemView.findViewById(R.id.createTime);
            communicateFile = (TextView) itemView.findViewById(R.id.communicateFile);

            createPersonName = (TextView) itemView.findViewById(R.id.createPersonName);
            edit = (TextView) itemView.findViewById(R.id.edit);

        }

    }
}
