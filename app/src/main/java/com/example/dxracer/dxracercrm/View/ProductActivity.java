package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Adapter.ProductAdapter;
import com.example.dxracer.dxracercrm.Interface.ProductInterface;
import com.example.dxracer.dxracercrm.Model.ProductModel;
import com.example.dxracer.dxracercrm.Presenter.ProductPresenter;
import com.example.dxracer.dxracercrm.Presenter.PublicCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.RecyclerViewEmptySupport;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


//产品列表

public class ProductActivity extends AppCompatActivity implements ProductInterface.View {

    public RecyclerViewEmptySupport recyclerView;
    private ProductPresenter presenter;
    public RefreshLayout refreshLayout;
    private Toolbar toolbar;
    public TextView toolbar_title;
    public ProductAdapter adapter;
    private List<ProductModel> datamode = new ArrayList<>();
    private String json="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        presenter = new ProductPresenter(this,this);
        initUI();

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("json",json);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void initUI(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.putExtra("json",json);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        recyclerView = (RecyclerViewEmptySupport)findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.loadListData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

            }
        });
    }

    public void addCart(List<ProductModel> data,int position){

        ProductModel productModel = new ProductModel();
        productModel.setId(data.get(position).getId());
        productModel.setSkuMainImg(data.get(position).getSkuMainImg());
        productModel.setFcno(data.get(position).getFcno());
        productModel.setSkuName(data.get(position).getSkuName());
        productModel.setSkuSalePrice(data.get(position).getSkuSalePrice());
        datamode.add(productModel);
        Gson gson = new Gson();
         json = gson.toJson(datamode);
        Log.e("TAG", "onBindViewHolder: "+json );
    }

    @Override
    public void succeed() {

    }

    @Override
    public void failed() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onNothingData() {

    }
}
