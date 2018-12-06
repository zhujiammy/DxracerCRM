package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Adapter.ContracttobeconfirmedAdapter;
import com.example.dxracer.dxracercrm.Adapter.PrivateCueAdapter;
import com.example.dxracer.dxracercrm.Interface.ContracttobeconfirmedInterface;
import com.example.dxracer.dxracercrm.Model.AccessChannelsModel;
import com.example.dxracer.dxracercrm.Model.ContracttobeconfirmedModel;
import com.example.dxracer.dxracercrm.Model.DaysofpaymentModel;
import com.example.dxracer.dxracercrm.Model.DepositratioModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.HttpUtils;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.ContracttobeconfirmedActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class ContracttobeconfirmedPresenter implements ContracttobeconfirmedInterface.presenter {

    private ContracttobeconfirmedActivity activity;
    private ContracttobeconfirmedInterface.View view;
    private ContracttobeconfirmedModel model;
    private List<ContracttobeconfirmedModel.ContractBean> beanList = new ArrayList<>();

    private List<DaysofpaymentModel> paymentPoint_model;
    private List<DepositratioModel> paymentFinalDay_model;
    public ArrayAdapter<DaysofpaymentModel> arrayAdapter_paymentPoint;
    public ArrayAdapter<DepositratioModel> arrayAdapter_paymentFinalDay;
    public String paymentPoint;
    public String paymentFinalDay;

    private int currentPage = 1;
    private int pageSize = 10;
    private int pages;
    public ContracttobeconfirmedPresenter(ContracttobeconfirmedActivity activity,ContracttobeconfirmedInterface.View view){

        this.activity = activity;
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
        reqBody.put("sort","ContractBill_id desc");
        currentPage = 1;
        reqBody.put("currentPage", String.valueOf(currentPage));
        reqBody.put("pageSize", String.valueOf(pageSize));
        reqBody.put("leadNo","");
        reqBody.put("oppoBillNo","");
        reqBody.put("opppPriceNo","");
        reqBody.put("contractNo","");
        reqBody.put("contractStatus","70");
        reqBody.put("createDateStart","");
        reqBody.put("createDateEnd","");
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
        params.put("contractStatus","70");
        params.put("createDateStart","");
        params.put("createDateEnd","");
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
                        model = gson.fromJson(msg.obj.toString(),ContracttobeconfirmedModel.class);
                        int total = model.getTotal();
                        beanList = model.getList();
                        activity.adapter = new ContracttobeconfirmedAdapter(beanList,activity);
                        activity.recyclerView.setAdapter(activity.adapter);
                        pages = model.getPages();
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
                            model  =gson.fromJson(msg.obj.toString(),ContracttobeconfirmedModel.class);
                            // hasNext = publicCueMode.isHasNextPage();
                            beanList.addAll(model.getList());
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
                            activity.refreshLayout.autoRefresh();
                            Toast.makeText(activity.getApplicationContext(),jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
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

                    case 4:// 解析返回数据
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(msg.obj.toString()).getAsJsonArray();
                        paymentPoint_model = new ArrayList<>();
                        paymentPoint_model.clear();
                        paymentPoint_model.add(new DaysofpaymentModel("0","请选择"));
                        for (JsonElement accesschannels:jsonArray){
                            DaysofpaymentModel daysofpaymentModel = gson.fromJson(accesschannels,DaysofpaymentModel.class);
                            paymentPoint_model.add(new DaysofpaymentModel(daysofpaymentModel.getKey(),daysofpaymentModel.getLabel()));
                            arrayAdapter_paymentPoint = new ArrayAdapter<DaysofpaymentModel>(activity, R.layout.simple_spinner_item,paymentPoint_model);
                            arrayAdapter_paymentPoint.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            activity.paymentPoint.setAdapter(arrayAdapter_paymentPoint);
                           activity.paymentPoint.setOnItemSelectedListener(paymentPointlistener);
                        }

                        break;

                    case 5:// 解析返回数据
                        JsonParser parser1 = new JsonParser();
                        JsonArray jsonArray1 = parser1.parse(msg.obj.toString()).getAsJsonArray();
                        paymentFinalDay_model = new ArrayList<>();
                        paymentFinalDay_model.clear();
                        paymentFinalDay_model.add(new DepositratioModel("0","请选择"));
                        for (JsonElement accesschannels:jsonArray1){
                            DepositratioModel depositratioModel = gson.fromJson(accesschannels,DepositratioModel.class);
                            paymentFinalDay_model.add(new DepositratioModel(depositratioModel.getKey(),depositratioModel.getLabel()));
                            arrayAdapter_paymentFinalDay = new ArrayAdapter<DepositratioModel>(activity.getApplication(), R.layout.simple_spinner_item,paymentFinalDay_model);
                            arrayAdapter_paymentFinalDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            activity.paymentFinalDay.setAdapter(arrayAdapter_paymentFinalDay);
                            activity.paymentFinalDay.setOnItemSelectedListener(paymentFinalDaylistener);
                        }

                        break;

                    case 6:
                        JsonObject jsonObjects = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String codes = jsonObjects.get("code").getAsString();
                        if(codes.equals("error")){
                            view.failed();
                            Toast.makeText(activity,jsonObjects.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(codes.equals("success")){
                            activity.refreshLayout.autoRefresh();
                            Toast.makeText(activity,jsonObjects.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                            App app = (App)activity.getApplication();
                            app.setRefresh(true);

                        }
                        break;

                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };

    @Override
    public void getDataListDepositratio() {
        //定金比例
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","payment_type");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"config/dictionary/data/getDataListByType", reqBody, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",result,"header");

                Message msg= Message.obtain(
                        mHandler,4,result
                );
                mHandler.sendMessage(msg);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    @Override
    public void getDataLisDaysofpayment() {
        //尾款天数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","final_monery_day");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"config/dictionary/data/getDataListByType", reqBody, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",result,"header");

                Message msg= Message.obtain(
                        mHandler,5,result
                );
                mHandler.sendMessage(msg);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    //保存付款方式
    public void savePayment(String id){
        HashMap<String, String> params = new HashMap<>();
        if(paymentPoint.equals("请选择")){
            Toast.makeText(activity,"定金比例不能为空",Toast.LENGTH_SHORT).show();
        }else if(paymentFinalDay.equals("请选择")){
            Toast.makeText(activity,"尾款天数不能为空",Toast.LENGTH_SHORT).show();
        }else {
            params.put("id",id);
            params.put("paymentPoint",paymentPoint);
            params.put("paymentFinalType","after");
            params.put("paymentFinalDay",paymentFinalDay);
            new HttpUtils().PostAPI(Constant.APIURL+"contract/bill/payment",params,new HttpUtils.HttpCallback() {

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

    }

    //生成合同
    public void showNormalDialog(final String id, String message, final String url){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(activity);
        normalDialog.setTitle("提示!");
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id",id);
                        new HttpUtils().PostAPI(Constant.APIURL+url,params,new HttpUtils.HttpCallback() {

                            @Override
                            public void onSuccess(String data) {
                                Log.e("TAG", "onSuccess: "+data );
                                // TODO Auto-generated method stub
                                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",data,"header");
                                Message msg= Message.obtain(
                                        mHandler,6,data
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

    Spinner.OnItemSelectedListener paymentPointlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            paymentPoint = ((DaysofpaymentModel) activity.paymentPoint.getSelectedItem()).getKey();
            if(paymentPoint.equals("请选择")){
                activity.Earnestmoney.setText("");
                activity.Tailmoney.setText("");
            }else {
                int x = Integer.parseInt(((DaysofpaymentModel) activity.paymentPoint.getSelectedItem()).getLabel());
                double total = Double.parseDouble(activity.contractFeestr);
                activity.Earnestmoney.setText(getPercent(x,total));
                double Earnestmoneystr = Double.parseDouble(getPercent(x,total));
                DecimalFormat df = new DecimalFormat("#.00");
                double Tailmoneystr = total - Earnestmoneystr;
                activity.Tailmoney.setText(df.format(Tailmoneystr));
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public String getPercent(int x,double total){

        float num= (float)x/100;
        double tempresult=total*num;
        DecimalFormat df = new DecimalFormat("#.00");
         String str = df.format(tempresult);
        return str;
    }

    Spinner.OnItemSelectedListener paymentFinalDaylistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            paymentFinalDay = ((DepositratioModel) activity.paymentFinalDay.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}
