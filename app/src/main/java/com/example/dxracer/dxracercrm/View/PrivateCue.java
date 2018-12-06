package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Adapter.PrivateCueAdapter;
import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Interface.PrivateCueInterface;
import com.example.dxracer.dxracercrm.Interface.PublicCueInterface;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Presenter.PrivateCuePresenter;
import com.example.dxracer.dxracercrm.Presenter.PublicCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.MyBottomSheetDialog;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Date;
import java.util.List;

public class PrivateCue extends AppCompatActivity implements PrivateCueInterface.View {

    public RecyclerViewEmptySupport recyclerView;
    public PrivateCuePresenter presenter;
    public RefreshLayout refreshLayout;
    public PrivateCueAdapter adapter;
    public static final int  INTENT1=1005;
    private Toolbar toolbar;
    public TextView toolbar_title;


    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privatecue);
        presenter = new PrivateCuePresenter(getApplicationContext(),PrivateCue.this,this);

        initUI();

    }

    private void initUI(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        recyclerView = (RecyclerViewEmptySupport) findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case INTENT1:
                    if(data.getStringExtra("statue").equals("1")){
                        refreshLayout.autoRefresh();
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //更多
    public void showPopwindows(final List<PublicCueMode.Bean> modes, final int i){

        final MyBottomSheetDialog dialog = new MyBottomSheetDialog(PrivateCue.this);
        View box_view = LayoutInflater.from(PrivateCue.this).inflate(R.layout.popdialog,null);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
        dialog.setContentView(box_view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        TextView add_contacts = (TextView) box_view.findViewById(R.id.add_contacts);
        TextView add_record = (TextView) box_view.findViewById(R.id.add_record);
        TextView TochangeintoOpportunity =(TextView) box_view.findViewById(R.id.TochangeintoOpportunity);
        TochangeintoOpportunity.setVisibility(View.VISIBLE);
        TextView Tochangeinto = (TextView) box_view.findViewById(R.id.Tochangeinto);
        Tochangeinto.setText("转到公有线索");
        TextView cancel_btn = (TextView) box_view.findViewById(R.id.cancel_btn);
        TextView  name = (TextView) box_view.findViewById(R.id.name);
        name.setText(modes.get(i).getCustomerFullName());
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent ;

                switch (v.getId()) {

                    //新增联系人
                    case R.id.add_contacts:
                        intent = new Intent(PrivateCue.this,AddContactsActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        startActivity(intent);
                        break;
                    //新增沟通记录
                    case R.id.add_record:
                        intent = new Intent(PrivateCue.this,AddRecordActivity.class);
                        intent.putExtra("leadNo",modes.get(i).getLeadNo());
                        startActivity(intent);
                        break;
                    //转入公有线索
                    case R.id.Tochangeinto:
                        presenter.showNormalDialog(i,"确认转到公有线索吗？","lead/private/toPublic");
                        break;
                        //转为机会
                    case R.id.TochangeintoOpportunity:
                        presenter.showNormalDialog(i,"确认转到机会吗？","oppo/bill/create");
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
        TochangeintoOpportunity.setOnClickListener(listener);

    }


/*    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            App app = (App)getActivity().getApplication();
            if(app.isRefresh()){
                refreshLayout.autoRefresh();
                app.setRefresh(false);
            }
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        App app = (App)getApplication();
        if(app.isRefresh()){
            refreshLayout.autoRefresh();
            app.setRefresh(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent ;
        int id = item.getItemId();
        if(id == R.id.save_btn){
            intent = new Intent(getApplicationContext(),AddPublicCueActivity.class);
            intent.putExtra("type","2");
            startActivityForResult(intent,INTENT1);

        }
        return super.onOptionsItemSelected(item);
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
