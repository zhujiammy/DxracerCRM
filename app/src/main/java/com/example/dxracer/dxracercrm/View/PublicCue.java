package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Interface.AddCueInterface;
import com.example.dxracer.dxracercrm.Interface.PublicCueInterface;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Presenter.AddCuePresenter;
import com.example.dxracer.dxracercrm.Presenter.PublicCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.MyBottomSheetDialog;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Date;
import java.util.List;

public class PublicCue extends Fragment implements PublicCueInterface.View{

    private View view;
    public RecyclerViewEmptySupport recyclerView;
    public PublicCuePresenter presenter;
    public RefreshLayout refreshLayout;
    public PublicCueAdapter adapter;
    public static final int  INTENT=1004;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.publiccue,container,false);
        presenter = new PublicCuePresenter(getActivity(),PublicCue.this,this);
        initUI();
/*        Log.e("TAG", "onCreateView: "+presenter.isMainThread() );
        adapter = new PublicCueAdapters(presenter.getData());
        recyclerView.setAdapter(adapter);*/
        return view;
    }

    private void initUI(){
        recyclerView = (RecyclerViewEmptySupport) view.findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.loadListData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                presenter.LoadMoreData();
            }
        });

    }




    //更多
    public void showPopwindows(final List<PublicCueMode.Bean> modes, final int i){

        final MyBottomSheetDialog dialog = new MyBottomSheetDialog(getActivity());
        View box_view = LayoutInflater.from(getActivity()).inflate(R.layout.popdialog,null);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
        dialog.setContentView(box_view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        TextView add_contacts = (TextView) box_view.findViewById(R.id.add_contacts);
        TextView add_record = (TextView) box_view.findViewById(R.id.add_record);
        TextView Tochangeinto = (TextView) box_view.findViewById(R.id.Tochangeinto);
        TextView cancel_btn = (TextView) box_view.findViewById(R.id.cancel_btn);
        TextView  name = (TextView) box_view.findViewById(R.id.name);
        name.setText(modes.get(i).getCustomerFullName());
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent ;

                switch (v.getId()) {

                    //新增联系人
                    case R.id.add_contacts:
                        intent = new Intent(getActivity(),AddContactsActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        startActivity(intent);
                        break;
                    //新增沟通记录
                    case R.id.add_record:
                        intent = new Intent(getActivity(),AddRecordActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        startActivity(intent);
                        break;
                    //转入私有线索
                    case R.id.Tochangeinto:
                        break;
                    case R.id.cancel_btn:
                       dialog.dismiss();
                        break;

                }
                dialog.dismiss();
            }
        };

        add_contacts.setOnClickListener(listener);
        cancel_btn.setOnClickListener(listener);
        add_record.setOnClickListener(listener);
        Tochangeinto.setOnClickListener(listener);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case INTENT:
                    if(data.getStringExtra("statue").equals("1")){
                      refreshLayout.autoRefresh();
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }








    @Override
    public void succeed() {

    }

    @Override
    public void failed() {
        refreshLayout.finishRefresh(true);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {

        refreshLayout.finishRefresh();//结束刷新


    }

    @Override
    public void onLoadMore() {
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(true);


    }

    @Override
    public void onNothingData() {
        //没有更多数据了
        refreshLayout.finishLoadMoreWithNoMoreData();
    }


}
