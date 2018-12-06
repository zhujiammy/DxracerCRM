package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.ProductAdapter;
import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Interface.ProductInterface;
import com.example.dxracer.dxracercrm.Model.ProductModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.ProductActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class ProductPresenter {

    private ProductActivity productActivity;
    private ProductInterface.View view;
    private List<ProductModel> productModelList = new ArrayList<>();
    private ProductModel productModel;

    public ProductPresenter(ProductActivity productActivity,ProductInterface.View view){

        this.productActivity = productActivity;
        this.view = view;
        new Thread(){
            @Override
            public void run() {
                loadListData();
            }
        }.start();
    }


    //加载
    public void loadListData(){
        productModelList.clear();
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("searchText","");
        reqBody.put("sortName","ProductSku_id");
        reqBody.put("sortOrder", "desc");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"product/sku/published/list", reqBody, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",result,"header");

                Message msg= Message.obtain(
                        mHandler,0,result
                );
                mHandler.sendMessage(msg);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson  = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
            super.handleMessage(msg);
            try{
                switch (msg.what) {

                    case 0:// 解析返回数据
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                        for (JsonElement user : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            productModel= gson.fromJson(user, ProductModel.class);
                            productModelList.add(productModel);
                        }
                        productActivity.adapter = new ProductAdapter(productModelList,productActivity);
                        productActivity.recyclerView.setAdapter(productActivity.adapter);
                        productActivity.adapter.setOnitemClickListener(new ProductAdapter.OnitemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        });

                        break;


                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };
}
