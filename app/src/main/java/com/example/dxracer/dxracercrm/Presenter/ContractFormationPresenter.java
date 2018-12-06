package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.ContractFormationAdapter;
import com.example.dxracer.dxracercrm.Adapter.PrivateCueAdapter;
import com.example.dxracer.dxracercrm.Interface.ContractFormationInterface;
import com.example.dxracer.dxracercrm.Model.ContractFormationModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.HttpUtils;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.ContractFormationActivity;
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

public class ContractFormationPresenter {

    private ContractFormationActivity activity;
    private ContractFormationInterface.View view;
    private ContractFormationModel contractFormationModel;
    private List<ContractFormationModel.FormationBean> beanList = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;
    private int pages;
    public ContractFormationPresenter(ContractFormationActivity activity,ContractFormationInterface.View view){
        this.view = view;
        this.activity = activity;

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
        reqBody.put("sort","ContractBill_id desc");
        currentPage = 1;
        reqBody.put("currentPage", String.valueOf(currentPage));
        reqBody.put("pageSize", String.valueOf(pageSize));
        reqBody.put("leadNo","");
        reqBody.put("oppoBillNo","");
        reqBody.put("opppPriceNo","");
        reqBody.put("contractNo","");
        reqBody.put("contractStatus",activity.intent.getStringExtra("contractStatus"));
        reqBody.put("createDateStart","");
        reqBody.put("createDateEnd","");
        reqBody.put("genDateStart","");
        reqBody.put("genDateEnd","");

        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"contract/bill/list", reqBody, new NetUtils.MyNetCall() {
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


    //加载更多
    public void LoadMoreData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("sort","ContractBill_id desc");
        params.put("currentPage", String.valueOf(++currentPage));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("leadNo","");
        params.put("oppoBillNo","");
        params.put("opppPriceNo","");
        params.put("contractNo","");
        params.put("contractStatus",activity.intent.getStringExtra("contractStatus"));
        params.put("createDateStart","");
        params.put("createDateEnd","");
        params.put("genDateStart","");
        params.put("genDateEnd","");
        new HttpUtils().PostAPI(Constant.APIURL+"contract/bill/list",params,new HttpUtils.HttpCallback() {

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

    public void cancel(String id,String closeNote){
        //作废原因
        HashMap<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("closeNote",closeNote);
        new HttpUtils().PostAPI(Constant.APIURL+"contract/bill/cancel",params,new HttpUtils.HttpCallback() {

            @Override
            public void onSuccess(String data) {
                Log.e("TAG", "onSuccess: "+data );
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",data,"header");
                Message msg= Message.obtain(
                        mHandler,3,data
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
                        contractFormationModel = gson.fromJson(msg.obj.toString(),ContractFormationModel.class);
                        int total = contractFormationModel.getTotal();
                        beanList = contractFormationModel.getList();
                        activity.adapter = new ContractFormationAdapter(beanList,activity);
                        activity.recyclerView.setAdapter(activity.adapter);
                        pages = contractFormationModel.getPages();
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
                            contractFormationModel  =gson.fromJson(msg.obj.toString(),ContractFormationModel.class);
                            // hasNext = publicCueMode.isHasNextPage();
                            beanList.addAll(contractFormationModel.getList());
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
                            Toast.makeText(activity.getApplicationContext(),jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            Toast.makeText(activity.getApplicationContext(),jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                            activity.refreshLayout.autoRefresh();
                            App app = (App)activity.getApplication();
                            app.setRefresh(true);

                        }
                        break;

                    case 3:
                        JsonObject jsonObject1 = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code1 = jsonObject1.get("code").getAsString();
                        if(code1.equals("error")){
                            Toast.makeText(activity.getApplicationContext(),jsonObject1.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code1.equals("success")){
                            activity.dialog1.dismiss();
                            Toast.makeText(activity.getApplicationContext(),jsonObject1.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                            activity.refreshLayout.autoRefresh();

                        }
                        break;

                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };

}
