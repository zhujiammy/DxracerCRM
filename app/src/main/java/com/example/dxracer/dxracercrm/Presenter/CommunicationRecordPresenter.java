package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.dxracer.dxracercrm.Adapter.CommunicationRecordAdapter;
import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Interface.CommunicationRecordInterface;
import com.example.dxracer.dxracercrm.Model.CommunicationRecordModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.HttpUtils;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.CommunicationRecordActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class CommunicationRecordPresenter {

    private CommunicationRecordInterface.View view;
    private CommunicationRecordActivity communicationRecordActivity;
    private CommunicationRecordModel communicationRecordModel;
    private List<CommunicationRecordModel.Recor> beanList = new ArrayList<>();
    private  List<CommunicationRecordModel.Recor> beanList1 = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;
    private boolean hasNext;

    public CommunicationRecordPresenter(CommunicationRecordInterface.View view,CommunicationRecordActivity communicationRecordActivity){

        this.view = view;
        this.communicationRecordActivity = communicationRecordActivity;
        new Thread(){
            @Override
            public void run() {
                LoadListData();
            }
        }.start();
    }


    public void LoadListData(){
        beanList.clear();
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("sort","ContactsCommunicate_communicateTime desc");
        currentPage = 1;
        reqBody.put("currentPage", String.valueOf(currentPage));
        reqBody.put("pageSize", String.valueOf(pageSize));
        reqBody.put("contactsPersonId", String.valueOf(communicationRecordActivity.id));
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"contacts/communicate/list", reqBody, new NetUtils.MyNetCall() {
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

    public void LoadMoreData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("sort","ContactsCommunicate_communicateTime desc");
        params.put("currentPage", String.valueOf(currentPage + 1));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("contactsPersonId", String.valueOf(communicationRecordActivity.id));
        new HttpUtils().PostAPI(Constant.APIURL+"contacts/communicate/list",params,new HttpUtils.HttpCallback() {

            @Override
            public void onSuccess(String data) {
                Log.e("TAG", "onSuccess: "+data );
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",data,"header");
                Message msg= Message.obtain(
                        mHandler,1,data
                );
                mHandler.sendMessage(msg);
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
                        //GSON直接解析成对象
                        //对象中拿到集合

                        //然后用上面一行写的gson来序列化和反序列化实体类type
                        communicationRecordModel = gson.fromJson(msg.obj.toString(),CommunicationRecordModel.class);
                        int total = communicationRecordModel.getTotal();
                        beanList = communicationRecordModel.getList();
                        communicationRecordActivity.adapter = new CommunicationRecordAdapter(beanList,communicationRecordActivity);
                        communicationRecordActivity.recyclerView.setAdapter(communicationRecordActivity.adapter);
                        communicationRecordActivity.adapter.setOnitemClickListener(new CommunicationRecordAdapter.OnitemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        });
                        hasNext = communicationRecordModel.isHasNextPage();
                        if(total != 0){
                            view.onRefresh();
                        }else {
                            // publicCue.loadAdapter(beanList);
                            view.failed();
                        }
                        break;

                    case 1:
                        //GSON直接解析成对象
                        //对象中拿到集合
                        if(hasNext){
                            communicationRecordModel  =gson.fromJson(msg.obj.toString(),CommunicationRecordModel.class);
                            hasNext = communicationRecordModel.isHasNextPage();
                            beanList1 = communicationRecordModel.getList();
                            beanList.addAll(beanList1);
                            view.onLoadMore();
                        }else {
                            view.onNothingData();
                        }

                        break;

                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };
}
