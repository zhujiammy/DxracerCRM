package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.PrivateCueAdapter;
import com.example.dxracer.dxracercrm.Interface.PrivateCueInterface;
import com.example.dxracer.dxracercrm.Interface.PublicCueInterface;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.HttpUtils;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.Tools.SharedPreferencesUtils;
import com.example.dxracer.dxracercrm.Tools.StringConverter;
import com.example.dxracer.dxracercrm.View.ClueDetailsActivity;
import com.example.dxracer.dxracercrm.View.PrivateCue;
import com.example.dxracer.dxracercrm.View.PublicCue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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

public class PrivateCuePresenter {

    private Context context;
    private PrivateCue privateCue;
    private PrivateCueInterface.View view;
    private  PublicCueMode publicCueMode;
    private  List<PublicCueMode.Bean> beanList = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;
    private int pages;
    private boolean hasNext;
    public PrivateCuePresenter (Context context,PrivateCue privateCue,PrivateCueInterface.View view){
        this.context = context;
        this.privateCue = privateCue;
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
        beanList.clear();
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("sort","LeadPrivate_id desc");
        currentPage = 1;
        reqBody.put("currentPage", String.valueOf(currentPage));
        reqBody.put("pageSize", String.valueOf(pageSize));
        reqBody.put("lead","1");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"lead/private/list", reqBody, new NetUtils.MyNetCall() {
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


    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }



    //加载更多
    public void LoadMoreData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("sort","LeadPrivate_id desc");
        params.put("currentPage", String.valueOf(++currentPage));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("lead","1");
        new HttpUtils().PostAPI(Constant.APIURL+"lead/private/list",params,new HttpUtils.HttpCallback() {

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
                        publicCueMode = gson.fromJson(msg.obj.toString(),PublicCueMode.class);
                        int total = publicCueMode.getTotal();
                        beanList = publicCueMode.getList();
                        privateCue.adapter = new PrivateCueAdapter(beanList,privateCue);
                        privateCue.recyclerView.setAdapter(privateCue.adapter);
                        privateCue.adapter.setOnitemClickListener(new PrivateCueAdapter.OnitemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(privateCue,ClueDetailsActivity.class);
                                intent.putExtra("leadNo",beanList.get(position).getLeadNo());
                                privateCue.startActivity(intent);
                            }
                        });
                        hasNext = publicCueMode.isHasNextPage();
                        pages = publicCueMode.getPages();
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
                            publicCueMode  =gson.fromJson(msg.obj.toString(),PublicCueMode.class);
                            // hasNext = publicCueMode.isHasNextPage();
                            beanList.addAll(publicCueMode.getList());
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
                            Toast.makeText(privateCue.getApplicationContext(),jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            privateCue.refreshLayout.autoRefresh();
                            App app = (App)privateCue.getApplication();
                            app.setRefresh(true);

                        }
                        break;

                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };

    //转为私有线索
    public void showNormalDialog(final int i, final String str, final String url){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(privateCue);
        normalDialog.setTitle("提示!");
        normalDialog.setMessage(str);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        HashMap<String, String> params = new HashMap<>();
                        if(str.equals("确认转到公有线索吗？")){
                            params.put("id", String.valueOf(beanList.get(i).getId()));
                        }else {
                            params.put("leadNo", String.valueOf(beanList.get(i).getLeadNo()));
                        }

                        new HttpUtils().PostAPI(Constant.APIURL+url,params,new HttpUtils.HttpCallback() {

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
