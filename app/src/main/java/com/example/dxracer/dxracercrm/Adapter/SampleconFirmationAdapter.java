package com.example.dxracer.dxracercrm.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.SampleconFirmationMode;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.example.dxracer.dxracercrm.View.PublicCue;
import com.example.dxracer.dxracercrm.View.SampleconFirmationActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import static com.example.dxracer.dxracercrm.View.CueManagementActivity.INTENT;

public class SampleconFirmationAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //数据源
    private List<SampleconFirmationMode.SampleMode> data;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public SampleconFirmationActivity sampleconFirmationActivity;
    //构造函数
    public SampleconFirmationAdapter(List<SampleconFirmationMode.SampleMode> dataList,SampleconFirmationActivity sampleconFirmationActivity) {
        this.data = dataList;
        this.sampleconFirmationActivity = sampleconFirmationActivity;
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sampleconfirmation_data, viewGroup, false);
        BodyViewHolder bodyViewHolder = new BodyViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        //其他条目中的逻辑在此
        if (viewHolder instanceof BodyViewHolder) {
            Gson gson = new Gson();

            JsonObject jsonObject = (JsonObject)new JsonParser().parse(gson.toJson(data.get(position).getLead()));
            ((BodyViewHolder) viewHolder).oppoCreatePersonName.setText(data.get(position).getOppoCreatePersonName());
            ((BodyViewHolder) viewHolder).oppoNo.setText("机会编号: "+data.get(position).getOppoNo());
            ((BodyViewHolder) viewHolder).contactPersonNum.setText("联系人数:  "+data.get(position).getContactPersonNum());
            ((BodyViewHolder) viewHolder).contactsCommunicateNum.setText("沟通次数:  "+data.get(position).getContactsCommunicateNum());
            ((BodyViewHolder) viewHolder).customerShortName.setText(jsonObject.get("customerShortName").getAsString());
            ((BodyViewHolder) viewHolder).leadNo.setText("线索编号:  "+data.get(position).getLeadNo());
            ((BodyViewHolder) viewHolder).oppoExistDays.setText("机会创建天数:  "+data.get(position).getOppoExistDays());
            ((BodyViewHolder) viewHolder).oppoStopOption.setText("关闭原因:  "+data.get(position).getOppoStopOption());
            ((BodyViewHolder) viewHolder).oppoStopNote.setText("机会说明:  "+data.get(position).getOppoStopNote());
            ((BodyViewHolder) viewHolder).oppoStopTime.setText("机会关闭时间:  "+data.get(position).getOppoStopTime());
            if (sampleconFirmationActivity.intent.getStringExtra("oppoStatus").equals("00")){
                ((BodyViewHolder) viewHolder).oppoStopOption.setVisibility(View.VISIBLE);
                ((BodyViewHolder) viewHolder).oppoStopNote.setVisibility(View.VISIBLE);
                ((BodyViewHolder) viewHolder).oppoStopTime.setVisibility(View.GONE);
                ((BodyViewHolder)viewHolder).more_btn.setVisibility(View.GONE);
            }else {
                ((BodyViewHolder) viewHolder).oppoStopOption.setVisibility(View.GONE);
                ((BodyViewHolder) viewHolder).oppoStopNote.setVisibility(View.GONE);

            }

            if (sampleconFirmationActivity.intent.getStringExtra("oppoStatus").equals("")){
                ((BodyViewHolder) viewHolder).oppoStopOption.setVisibility(View.VISIBLE);
                ((BodyViewHolder) viewHolder).oppoStopNote.setVisibility(View.VISIBLE);
                ((BodyViewHolder) viewHolder).oppoStopTime.setVisibility(View.VISIBLE);
                ((BodyViewHolder)viewHolder).more_btn.setVisibility(View.GONE);
            }else {
                ((BodyViewHolder) viewHolder).oppoStopOption.setVisibility(View.GONE);
                ((BodyViewHolder) viewHolder).oppoStopNote.setVisibility(View.GONE);
                ((BodyViewHolder) viewHolder).oppoStopTime.setVisibility(View.GONE);
            }

            final String customerShortName = jsonObject.get("customerShortName").getAsString();
            ((BodyViewHolder) viewHolder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ((BodyViewHolder)viewHolder).more_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //更多
                    sampleconFirmationActivity.showPopwindows(data,position,customerShortName);

                }
            });
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
        private TextView leadNo;
        private TextView oppoNo;
        private TextView customerShortName;
        private TextView oppoCreatePersonName;
        private TextView contactPersonNum;
        private TextView contactsCommunicateNum;
        private TextView oppoExistDays;
        private TextView oppoStopNote;
        private TextView oppoStopTime;
        private TextView oppoStopOption;
        private TextView edit;
        private TextView more_btn;

        public BodyViewHolder(View itemView) {
            super(itemView);
            leadNo = (TextView) itemView.findViewById(R.id.leadNo);
            oppoNo = (TextView) itemView.findViewById(R.id.oppoNo);
            customerShortName = (TextView) itemView.findViewById(R.id.customerShortName);
            oppoCreatePersonName = (TextView) itemView.findViewById(R.id.oppoCreatePersonName);
            contactPersonNum = (TextView) itemView.findViewById(R.id.contactPersonNum);
            contactsCommunicateNum = (TextView) itemView.findViewById(R.id.contactsCommunicateNum);
            oppoExistDays = (TextView) itemView.findViewById(R.id.oppoExistDays);
            oppoStopOption = (TextView) itemView.findViewById(R.id.oppoStopOption);
            oppoStopTime = (TextView) itemView.findViewById(R.id.oppoStopTime);
            oppoStopNote = (TextView) itemView.findViewById(R.id.oppoStopNote);
            edit = (TextView) itemView.findViewById(R.id.edit);
            more_btn = (TextView) itemView.findViewById(R.id.more_btn);
        }

    }
}
