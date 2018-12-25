/**
 * Copyright 2017 ChenHao Dendi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dxracer.dxracercrm.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Model.MaillistModel;
import com.example.dxracer.dxracercrm.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<MaillistModel.MaillistBean> mList;
    private LayoutInflater mLayoutInflater;
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;


    public ContactsListAdapter(LayoutInflater layoutInflater, List<MaillistModel.MaillistBean> list) {
        this.mList = list;
        mLayoutInflater = layoutInflater;
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
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_share_contact_content, viewGroup, false);
        ContactsViewHolder bodyViewHolder = new ContactsViewHolder(baseView);
        baseView.setOnClickListener(this);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContactsViewHolder) {
            ((ContactsViewHolder) holder).nameTx.setText(mList.get(position).getPersonName());
            ((ContactsViewHolder) holder).phoneTv.setText(mList.get(position).getPosition());

        }
        holder.itemView.setTag(position);
    }

    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     * @return
     */
    @Override
    public int getItemCount() {
        if (mList.size() == 0) {
            return 1;
        }
        return mList.size();
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

        if (mList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        //如果有数据，则使用ITEM的布局
        return VIEW_TYPE_ITEM;
    }

    private class ContactsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTx;
        private TextView phoneTv;

        ContactsViewHolder(View itemView) {
            super(itemView);
            nameTx = (TextView) itemView.findViewById(R.id.list_item_contact_name);
            phoneTv = (TextView) itemView.findViewById(R.id.list_item_contact_number);
        }

    }


}

