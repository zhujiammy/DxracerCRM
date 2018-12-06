package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.AddQuotationInterface;
import com.example.dxracer.dxracercrm.Model.AccessChannelsModel;
import com.example.dxracer.dxracercrm.Model.ChildrenModel;
import com.example.dxracer.dxracercrm.Model.FollowPersonModel;
import com.example.dxracer.dxracercrm.Model.MineAndParentsModel;
import com.example.dxracer.dxracercrm.Model.PersonModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.AddQuotationActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class AddQuotationPresenter implements AddQuotationInterface.presenter {

    private AddQuotationActivity addQuotationActivity;
    private AddQuotationInterface.View view;
    private List<PersonModel> PersonModel;
    public ArrayAdapter<PersonModel> arrayAdapter_PersonModel;

    private List<ChildrenModel> childrenModels;
    public ArrayAdapter<ChildrenModel> arrayAdapter_ChildrenModel;

    private List<MineAndParentsModel> mineAndParentsModels;
    private ArrayAdapter<MineAndParentsModel>andParentsModelArrayAdapter;

    private String dealerContacts;
    private String auditUserId;
    private String supplierContacts;
    private List<String> sku_id=new ArrayList<>();
    private List<String> sku_quantity=new ArrayList<>();


    public AddQuotationPresenter(AddQuotationActivity addQuotationActivity,AddQuotationInterface.View view){

        this.addQuotationActivity = addQuotationActivity;
        this.view = view;

    }

    @Override
    public void loadgetDataListByTypeperson() {

        //采购方联系人
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("leadNo",addQuotationActivity.intent.getStringExtra("leadNo"));
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"contacts/communicate/leadno/person", reqBody, new NetUtils.MyNetCall() {
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

    @Override
    public void loadgetDataListgetMineAndChildren() {
        //供应商联系人

        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"sys/user/getMineAndChildren", reqBody, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",result,"header");

                Message msg= Message.obtain(
                        mHandler,1,result
                );
                mHandler.sendMessage(msg);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    @Override
    public void loadgetDataListgetMineAndParents() {
        //折扣率
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"sys/user/getMineAndParents", reqBody, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",result,"header");

                Message msg= Message.obtain(
                        mHandler,2,result
                );
                mHandler.sendMessage(msg);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }


    Spinner.OnItemSelectedListener dealerContactslistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            dealerContacts = String.valueOf(((PersonModel)addQuotationActivity.dealerContacts.getSelectedItem()).getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    Spinner.OnItemSelectedListener auditUserIdlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            auditUserId = String.valueOf(((MineAndParentsModel)addQuotationActivity.auditUserId.getSelectedItem()).getUser_id());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    Spinner.OnItemSelectedListener supplierContactslistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            supplierContacts = String.valueOf(((ChildrenModel)addQuotationActivity.supplierContacts.getSelectedItem()).getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
                        PersonModel = new ArrayList<>();
                        PersonModel.clear();
                        PersonModel.add(new PersonModel(0,"请选择"));
                        for (JsonElement accesschannels:jsonArray){
                            PersonModel personModel = gson.fromJson(accesschannels,PersonModel.class);
                            PersonModel.add(new PersonModel(personModel.getId(),personModel.getPersonName()));
                            arrayAdapter_PersonModel = new ArrayAdapter<PersonModel>(addQuotationActivity.getApplication(), R.layout.simple_spinner_item,PersonModel);
                            arrayAdapter_PersonModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addQuotationActivity.dealerContacts.setAdapter(arrayAdapter_PersonModel);
                            addQuotationActivity.dealerContacts.setOnItemSelectedListener(dealerContactslistener);
                        }


                        break;
                    case 1:// 解析返回数据
                        JsonParser parser1 = new JsonParser();
                        JsonArray jsonArray1 = parser1.parse(msg.obj.toString()).getAsJsonArray();
                        childrenModels = new ArrayList<>();
                        childrenModels.clear();
                        childrenModels.add(new ChildrenModel(0,"请选择"));
                        for (JsonElement accesschannels:jsonArray1){
                            ChildrenModel childrenModel = gson.fromJson(accesschannels,ChildrenModel.class);
                            childrenModels.add(new ChildrenModel(childrenModel.getId(),childrenModel.getRealName()));
                            arrayAdapter_ChildrenModel = new ArrayAdapter<ChildrenModel>(addQuotationActivity.getApplication(), R.layout.simple_spinner_item,childrenModels);
                            arrayAdapter_ChildrenModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addQuotationActivity.supplierContacts.setAdapter(arrayAdapter_ChildrenModel);
                             addQuotationActivity.supplierContacts.setOnItemSelectedListener(supplierContactslistener);
                        }


                        break;


                    case 2:// 解析返回数据
                        try {
                            JSONArray jsonArray3 = new JSONArray(msg.obj.toString());
                            mineAndParentsModels = new ArrayList<>();
                            mineAndParentsModels.clear();
                            mineAndParentsModels.add(new MineAndParentsModel("无需折扣","0"));
                            for(int i= 0;i<jsonArray3.length();i++){
                                JSONObject object = jsonArray3.getJSONObject(i);
                                JSONObject jsonObject = object.getJSONObject("sysUserConfig");
                                String user_id = jsonObject.getString("user_id");
                                int setOppo_price_discount = jsonObject.getInt("oppo_price_discount");
                                if(setOppo_price_discount != 0){
                                    mineAndParentsModels.add(new MineAndParentsModel(String.valueOf(setOppo_price_discount)+"%"+"["+object.getString("realName")+"]",user_id));
                                    andParentsModelArrayAdapter = new ArrayAdapter<MineAndParentsModel>(addQuotationActivity.getApplication(), R.layout.simple_spinner_item,mineAndParentsModels);
                                    andParentsModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    addQuotationActivity.auditUserId.setAdapter(andParentsModelArrayAdapter);
                                    addQuotationActivity.auditUserId.setOnItemSelectedListener(auditUserIdlistener);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        break;

                    case 3:
                        JsonObject jsonObject = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code = jsonObject.get("code").getAsString();
                        if(code.equals("error")){
                            view.failed();
                            Toast.makeText(addQuotationActivity,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            view.succeed();

                        }
                        break;


                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };





    public void SaveData(){
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("oppoBillNo",addQuotationActivity.oppoBillNo.getText().toString());
        Log.e("TAG", "SaveData: "+addQuotationActivity.datas.toString());
        if(dealerContacts.equals("0")){
            Toast.makeText(addQuotationActivity,"采购方联系人不能为空！",Toast.LENGTH_SHORT).show();
        }else if(supplierContacts.equals("0")){
            Toast.makeText(addQuotationActivity,"供应商联系人不能为空！",Toast.LENGTH_SHORT).show();
        } else  {
            reqBody.put("dealerContacts",dealerContacts);
            reqBody.put("auditUserId",auditUserId);
            reqBody.put("supplierContacts", supplierContacts);
            for(int i = 0;i<addQuotationActivity.datas.size();i++){
                sku_id.add(addQuotationActivity.datas.get(i).get("sku_id"));
                sku_quantity.add(addQuotationActivity.datas.get(i).get("sku_quantity"));
            }
            reqBody.put("sku_id", String.valueOf(sku_id).replace(" ", ""));
            reqBody.put("sku_quantity", String.valueOf(sku_quantity).replace(" ", ""));

            NetUtils netUtils = NetUtils.getInstance();
            netUtils.postDataAsynToNetHeader(Constant.APIURL +"oppo/price/apply/insert", reqBody, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    // TODO Auto-generated method stub
                    com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",result,"header");

                    Message msg= Message.obtain(
                            mHandler,3,result
                    );
                    mHandler.sendMessage(msg);
                }

                @Override
                public void failed(Call call, IOException e) {

                }
            });
        }


    }
}
