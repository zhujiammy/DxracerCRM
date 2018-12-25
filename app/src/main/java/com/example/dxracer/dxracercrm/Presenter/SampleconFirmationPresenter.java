package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Adapter.SampleconFirmationAdapter;
import com.example.dxracer.dxracercrm.Interface.SampleConfirmationInterface;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Model.SampleconFirmationMode;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.HttpUtils;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.OpportunityDetailsActivity;
import com.example.dxracer.dxracercrm.View.QuotationDetailsActivity;
import com.example.dxracer.dxracercrm.View.SampleconFirmationActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class SampleconFirmationPresenter {

    private SampleconFirmationActivity sampleconFirmationActivity;
    private SampleConfirmationInterface.View view;
    private List<SampleconFirmationMode.SampleMode> sampleModeList = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;
    private int pages;
    private SampleconFirmationMode sampleconFirmationMode;

    public SampleconFirmationPresenter(SampleconFirmationActivity sampleconFirmationActivity, SampleConfirmationInterface.View view) {

        this.sampleconFirmationActivity = sampleconFirmationActivity;
        this.view = view;
        new Thread() {
            @Override
            public void run() {
                loadListData();
            }
        }.start();
    }


    //加载
    public void loadListData() {
        sampleModeList.clear();
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();

        currentPage = 1;
        reqBody.put("sort", "OppoBill_id desc");
        reqBody.put("currentPage", String.valueOf(currentPage));
        reqBody.put("pageSize", String.valueOf(pageSize));
        reqBody.put("oppoNo", "");
        reqBody.put("leadNo", "");
        reqBody.put("oppoCreateDateStart", "");
        reqBody.put("oppoCreateDateEnd", "");
        reqBody.put("oppoStatus", sampleconFirmationActivity.intent.getStringExtra("oppoStatus"));

        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL + "oppo/bill/list", reqBody, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag", result, "header");

                Message msg = Message.obtain(
                        mHandler, 0, result
                );
                mHandler.sendMessage(msg);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    //加载更多
    public void LoadMoreData(){
        HashMap<String, String> params = new HashMap<>();

        params.put("currentPage", String.valueOf(++currentPage));
        params.put("sort", "OppoBill_id desc");
        params.put("pageSize", String.valueOf(pageSize));
        params.put("oppoNo", "");
        params.put("leadNo", "");
        params.put("oppoCreateDateStart", "");
        params.put("oppoCreateDateEnd", "");
        params.put("oppoStatus", sampleconFirmationActivity.intent.getStringExtra("oppoStatus"));
        new HttpUtils().PostAPI(Constant.APIURL+"oppo/bill/list",params,new HttpUtils.HttpCallback() {

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
                        sampleconFirmationMode = gson.fromJson(msg.obj.toString(),SampleconFirmationMode.class);
                        int total = sampleconFirmationMode.getTotal();
                        sampleModeList = sampleconFirmationMode.getList();
                        sampleconFirmationActivity.adapter = new SampleconFirmationAdapter(sampleModeList,sampleconFirmationActivity);
                        sampleconFirmationActivity.recyclerView.setAdapter(sampleconFirmationActivity.adapter);
                        sampleconFirmationActivity.adapter.setOnitemClickListener(new SampleconFirmationAdapter.OnitemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if(sampleconFirmationActivity.intent.getStringExtra("oppoStatus").equals("60")){
                                    Intent intent = new Intent(sampleconFirmationActivity,QuotationDetailsActivity.class);
                                    intent.putExtra("leadNo",sampleModeList.get(position).getLeadNo());
                                    intent.putExtra("oppoStatus","60");
                                    intent.putExtra("oppoBillNo",sampleModeList.get(position).getOppoNo());
                                    sampleconFirmationActivity.startActivity(intent);
                                }

                                if(sampleconFirmationActivity.intent.getStringExtra("oppoStatus").equals("40")){
                                    Intent intent = new Intent(sampleconFirmationActivity,QuotationDetailsActivity.class);
                                    intent.putExtra("leadNo",sampleModeList.get(position).getLeadNo());
                                    intent.putExtra("oppoStatus","40");
                                    sampleconFirmationActivity.startActivity(intent);
                                }

                                if(sampleconFirmationActivity.intent.getStringExtra("oppoStatus").equals("50")){
                                    Intent intent = new Intent(sampleconFirmationActivity,QuotationDetailsActivity.class);
                                    intent.putExtra("leadNo",sampleModeList.get(position).getLeadNo());
                                    intent.putExtra("oppoStatus","50");
                                    sampleconFirmationActivity.startActivity(intent);
                                }

                                if(sampleconFirmationActivity.intent.getStringExtra("oppoStatus").equals("00")){
                                    Intent intent = new Intent(sampleconFirmationActivity,QuotationDetailsActivity.class);
                                    intent.putExtra("leadNo",sampleModeList.get(position).getLeadNo());
                                    intent.putExtra("oppoStatus","00");
                                    intent.putExtra("oppoBillNo",sampleModeList.get(position).getOppoNo());
                                    sampleconFirmationActivity.startActivity(intent);
                                }

                                if(sampleconFirmationActivity.intent.getStringExtra("oppoStatus").equals("")){
                                    Intent intent = new Intent(sampleconFirmationActivity,QuotationDetailsActivity.class);
                                    intent.putExtra("leadNo",sampleModeList.get(position).getLeadNo());
                                    intent.putExtra("oppoStatus","00");
                                    intent.putExtra("oppoBillNo",sampleModeList.get(position).getOppoNo());
                                    sampleconFirmationActivity.startActivity(intent);
                                }
                            }
                        });
                        pages = sampleconFirmationMode.getPages();
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
                        if(currentPage<=pages){
                            Log.e("TAG", "handleMessage: "+currentPage);
                            sampleconFirmationMode  =gson.fromJson(msg.obj.toString(),SampleconFirmationMode.class);
                            // hasNext = publicCueMode.isHasNextPage();
                            sampleModeList.addAll(sampleconFirmationMode.getList());
                            view.onLoadMore();
                        }else {
                            view.onNothingData();
                        }

                        break;

                    case 2:
                        JsonObject jsonObject = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code = jsonObject.get("code").getAsString();
                        if(code.equals("error")){
                            view.failed();
                            Toast.makeText(sampleconFirmationActivity.getApplicationContext(),jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            Toast.makeText(sampleconFirmationActivity.getApplicationContext(),jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                            sampleconFirmationActivity.refreshLayout.autoRefresh();
                            App app = (App)sampleconFirmationActivity.getApplication();
                            app.setRefresh(true);

                        }
                        break;


                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };


    public void showNormalDialog(final String id){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(sampleconFirmationActivity);
        normalDialog.setTitle("提示!");
        normalDialog.setMessage("确认当前阶段已完成吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        HashMap<String, String> params = new HashMap<>();
                        new HttpUtils().PostAPI(Constant.APIURL+"oppo/bill/confirm/"+id,params,new HttpUtils.HttpCallback() {

                            @Override
                            public void onSuccess(String data) {
                                Log.e("TAG", "onSuccess: "+data );
                                // TODO Auto-generated method stub
                                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",data,"header");
                                Message msg= Message.obtain(
                                        mHandler,2,data
                                );
                                mHandler.sendMessage(msg);
                            }

                        });
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}

