package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.AddPrivateCueInterface;
import com.example.dxracer.dxracercrm.Interface.AddPublicCueInterface;
import com.example.dxracer.dxracercrm.Model.AccessChannelsModel;
import com.example.dxracer.dxracercrm.Model.FollowPersonModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.CitySelect1Activity;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.AddPrivateCueActivity;
import com.example.dxracer.dxracercrm.View.AddPublicCueActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hmy.popwindow.PopWindow;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class AddPrivateCuePresenter implements AddPrivateCueInterface.presenter,View.OnClickListener {

    private AddPrivateCueInterface.View view;
    private AddPrivateCueActivity addPrivateCueActivity;
    private List<AccessChannelsModel> accessChannelsModels_lead_source;
    private List<AccessChannelsModel> accessChannelsModels_customer_scale;
    private List<AccessChannelsModel> accessChannelsModels_customer_industry;
    private List<FollowPersonModel> FollowPersonModel;
    public ArrayAdapter<AccessChannelsModel> arrayAdapter_lead_source;
    public ArrayAdapter<AccessChannelsModel> arrayAdapter_customer_scale;
    public ArrayAdapter<AccessChannelsModel> arrayAdapter_customer_industry;
    public ArrayAdapter<FollowPersonModel> arrayAdapter_FollowPersonModel;
    private java.util.Calendar cal;
    private int mYear,mMonth,mDay;
    private TimePickerDialog dialog1;
    private  String date;
    private String ProvinceCodestr = "";
    private String CityCodestr = "";
    private String DistrictCodestr = "";
    private String leadSource;
    private String customerScale;
    private String customerIndustry;
    private String followPersonName;

    public AddPrivateCuePresenter(AddPrivateCueInterface.View view, final AddPrivateCueActivity addPrivateCueActivity){
        this.addPrivateCueActivity = addPrivateCueActivity;
        this.view = view;
        addPrivateCueActivity.leadGetDate.setOnClickListener(this);
        addPrivateCueActivity.selectAddress.setOnClickListener(this);
        //获取当前日期
        getDate();
        addPrivateCueActivity.leadGetDate.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);


        addPrivateCueActivity.popWindow = new PopWindow.Builder(addPrivateCueActivity)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView( addPrivateCueActivity.customView)
                .create();

        addPrivateCueActivity.citySelect1Activity=(CitySelect1Activity) addPrivateCueActivity.customView.findViewById(R.id.apvAddress);
        addPrivateCueActivity.citySelect1Activity.setOnAddressPickerSure(new CitySelect1Activity.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String Province, String City, String District, String ProvinceCode, String CityCode, String DistrictCode) {

                addPrivateCueActivity.selectAddress.setText(Province+" "+City+" "+District);
                ProvinceCodestr = ProvinceCode;
                CityCodestr = CityCode;
                DistrictCodestr = DistrictCode;
                addPrivateCueActivity.popWindow.dismiss();
            }
        });

    }



    private void getDate(){
        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
    }

    //获取获得渠道数据
    @Override
    public void loadgetDataListByType() {

        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","lead_source");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"config/dictionary/data/getDataListByType", reqBody, new NetUtils.MyNetCall() {
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


    //获取客户规模
    @Override
    public void loadgetDataListByCustomerScale() {
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","customer_scale");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"config/dictionary/data/getDataListByType", reqBody, new NetUtils.MyNetCall() {
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


    //获取客户行业
    @Override
    public void loadgetDataListBycustomerindustry() {
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","customer_industry");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"config/dictionary/data/getDataListByType", reqBody, new NetUtils.MyNetCall() {
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


    //获取追踪人

    @Override
    public void loadgetDataListByFollowPerson() {
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("","");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"sys/user/getMineAndChildren", reqBody, new NetUtils.MyNetCall() {
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
                        accessChannelsModels_lead_source = new ArrayList<>();
                        accessChannelsModels_lead_source.clear();
                        accessChannelsModels_lead_source.add(new AccessChannelsModel("0","请选择"));
                        for (JsonElement accesschannels:jsonArray){
                            AccessChannelsModel accessChannelsModel = gson.fromJson(accesschannels,AccessChannelsModel.class);
                            accessChannelsModels_lead_source.add(new AccessChannelsModel(accessChannelsModel.getKey(),accessChannelsModel.getLabel()));
                            arrayAdapter_lead_source = new ArrayAdapter<AccessChannelsModel>(addPrivateCueActivity.getApplication(), R.layout.simple_spinner_item,accessChannelsModels_lead_source);
                            arrayAdapter_lead_source.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addPrivateCueActivity.Accesschannels.setAdapter(arrayAdapter_lead_source);
                            addPrivateCueActivity.Accesschannels.setOnItemSelectedListener(Accesschannelslistener);
                        }

                        if(!addPrivateCueActivity.type.equals("2")){
                            //获取渠道
                            int d=arrayAdapter_lead_source.getCount();
                            for(int j=0;j<d;j++){
                                if(addPrivateCueActivity.intent.getStringExtra("leadSource").equals(arrayAdapter_lead_source.getItem(j).getKey())){
                                    addPrivateCueActivity.Accesschannels.setAdapter(arrayAdapter_lead_source);
                                    addPrivateCueActivity.Accesschannels.setSelection(j,true);
                                }
                            }
                        }

                        break;

                    case 1:// 解析返回数据
                        JsonParser parsercustomer_scale = new JsonParser();
                        JsonArray jsonArray_scale = parsercustomer_scale.parse(msg.obj.toString()).getAsJsonArray();
                        accessChannelsModels_customer_scale = new ArrayList<>();
                        accessChannelsModels_customer_scale.clear();
                        accessChannelsModels_customer_scale.add(new AccessChannelsModel("0","请选择"));

                        for (JsonElement accesschannels:jsonArray_scale){
                            AccessChannelsModel accessChannelsModel = gson.fromJson(accesschannels,AccessChannelsModel.class);
                            accessChannelsModels_customer_scale.add(new AccessChannelsModel(accessChannelsModel.getKey(),accessChannelsModel.getLabel()));
                            arrayAdapter_customer_scale = new ArrayAdapter<AccessChannelsModel>(addPrivateCueActivity.getApplication(), R.layout.simple_spinner_item,accessChannelsModels_customer_scale);
                            arrayAdapter_customer_scale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addPrivateCueActivity.Customersize.setAdapter(arrayAdapter_customer_scale);
                            addPrivateCueActivity.Customersize.setOnItemSelectedListener(Customersizelistener);
                        }

                        if(!addPrivateCueActivity.type.equals("2")){
                            //客户规模
                            int a=arrayAdapter_customer_scale.getCount();
                            for(int j=0;j<a;j++){
                                if(addPrivateCueActivity.intent.getStringExtra("customerScale").equals(arrayAdapter_customer_scale.getItem(j).getKey())){
                                    addPrivateCueActivity.Customersize.setAdapter(arrayAdapter_customer_scale);
                                    addPrivateCueActivity.Customersize.setSelection(j,true);
                                }
                            }
                        }

                        break;
                    case 2:// 解析返回数据
                        JsonParser parsercustomer_industry = new JsonParser();
                        JsonArray jsonArray_industry = parsercustomer_industry.parse(msg.obj.toString()).getAsJsonArray();
                        accessChannelsModels_customer_industry = new ArrayList<>();
                        accessChannelsModels_customer_industry.clear();
                        accessChannelsModels_customer_industry.add(new AccessChannelsModel("0","请选择"));
                        for (JsonElement accesschannels:jsonArray_industry){
                            AccessChannelsModel accessChannelsModel = gson.fromJson(accesschannels,AccessChannelsModel.class);
                            accessChannelsModels_customer_industry.add(new AccessChannelsModel(accessChannelsModel.getKey(),accessChannelsModel.getLabel()));
                            arrayAdapter_customer_industry = new ArrayAdapter<AccessChannelsModel>(addPrivateCueActivity.getApplication(), R.layout.simple_spinner_item,accessChannelsModels_customer_industry);
                            arrayAdapter_customer_industry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addPrivateCueActivity.Customerindustry.setAdapter(arrayAdapter_customer_industry);
                            addPrivateCueActivity.Customerindustry.setOnItemSelectedListener(Customerindustrylistener);
                        }
                        if(!addPrivateCueActivity.type.equals("2")){
                            //客户行业
                            int c=arrayAdapter_customer_industry.getCount();
                            for(int j=0;j<c;j++){
                                if(addPrivateCueActivity.intent.getStringExtra("customerIndustry").equals(arrayAdapter_customer_industry.getItem(j).getKey())){
                                    addPrivateCueActivity.Customerindustry.setAdapter(arrayAdapter_customer_industry);
                                    addPrivateCueActivity.Customerindustry.setSelection(j,true);
                                }
                            }
                        }

                        break;


                    case 4:// 解析返回数据
                        JsonParser MineAndChildren = new JsonParser();
                        JsonArray jsonArray_MineAndChildren = MineAndChildren.parse(msg.obj.toString()).getAsJsonArray();
                        FollowPersonModel = new ArrayList<>();
                        FollowPersonModel.clear();
                        FollowPersonModel.add(new FollowPersonModel(0,"请选择"));
                        for (JsonElement accesschannels:jsonArray_MineAndChildren){
                            FollowPersonModel followPersonModel = gson.fromJson(accesschannels,FollowPersonModel.class);
                            FollowPersonModel.add(new FollowPersonModel(followPersonModel.getId(),followPersonModel.getRealName()));
                            arrayAdapter_FollowPersonModel = new ArrayAdapter<FollowPersonModel>(addPrivateCueActivity.getApplication(), R.layout.simple_spinner_item,FollowPersonModel);
                            arrayAdapter_FollowPersonModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addPrivateCueActivity.followPersonName.setAdapter(arrayAdapter_FollowPersonModel);
                            addPrivateCueActivity.followPersonName.setOnItemSelectedListener(followPersonNamelistener);
                        }
                        if(!addPrivateCueActivity.type.equals("2")){
                            //客户行业
                            int c=arrayAdapter_FollowPersonModel.getCount();
                            for(int j=0;j<c;j++){
                                if(addPrivateCueActivity.intent.getStringExtra("followPersonName").equals(arrayAdapter_FollowPersonModel.getItem(j).getRealName())){
                                    addPrivateCueActivity.followPersonName.setAdapter(arrayAdapter_FollowPersonModel);
                                    addPrivateCueActivity.followPersonName.setSelection(j,true);
                                }
                            }
                        }

                        break;

                    case 3:
                        JsonObject jsonObject = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code = jsonObject.get("code").getAsString();
                        if(code.equals("error")){
                            view.failed();
                            Toast.makeText(addPrivateCueActivity,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
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

    //获取渠道选择
    Spinner.OnItemSelectedListener Accesschannelslistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            leadSource = ((AccessChannelsModel) addPrivateCueActivity.Accesschannels.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //获取客户规模
    Spinner.OnItemSelectedListener Customersizelistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            customerScale = ((AccessChannelsModel) addPrivateCueActivity.Customersize.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //获取客户行业
    Spinner.OnItemSelectedListener Customerindustrylistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            customerIndustry = ((AccessChannelsModel) addPrivateCueActivity.Customerindustry.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    //获取追踪人
    Spinner.OnItemSelectedListener followPersonNamelistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            followPersonName = String.valueOf(((FollowPersonModel) addPrivateCueActivity.followPersonName.getSelectedItem()).getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onClick(View v) {
        if(!addPrivateCueActivity.type.equals("2")){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sf.parse(addPrivateCueActivity.leadGetDate.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                System.out.println();
                System.out.println();
                System.out.println();

                mYear= calendar.get(Calendar.YEAR);
                mMonth = calendar.get(calendar.get(Calendar.MONTH) + 1);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        //获取日期
        if (v == addPrivateCueActivity.leadGetDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(addPrivateCueActivity,
                    R.style.MyDatePickerDialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mYear = year;
                            mMonth = month;
                            mDay = dayOfMonth;
                            String datetime = mYear + "-" + (mMonth + 1) + "-" + mDay;
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                // 注意格式需要与上面一致，不然会出现异常
                                date = sdf.parse(datetime);
                                @SuppressLint("SimpleDateFormat") String sdate=(new SimpleDateFormat("yyyy-MM-dd")).format(date);
                                addPrivateCueActivity.leadGetDate.setText(sdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        //省市区选择
        if(v == addPrivateCueActivity.selectAddress){
            addPrivateCueActivity.popWindow.show();
        }
    }



    //保存数据

    public void SaveData(){
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("leadGetDate", addPrivateCueActivity.leadGetDate.getText().toString());
        if(leadSource.equals("请选择")){
            Toast.makeText(addPrivateCueActivity,"获取渠道不能为空！",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(addPrivateCueActivity.customerShortName.getText().toString())){
            Toast.makeText(addPrivateCueActivity,"客户简称不能为空！",Toast.LENGTH_SHORT).show();
        }else if(followPersonName.equals("0")){
            Toast.makeText(addPrivateCueActivity,"追踪人不能为空！",Toast.LENGTH_SHORT).show();
        }else  {
            reqBody.put("leadSource",leadSource);
            reqBody.put("customerShortName", addPrivateCueActivity.customerShortName.getText().toString());
            reqBody.put("customerFullName", addPrivateCueActivity.customerFullName.getText().toString());
            if(customerScale.equals("请选择")){
                reqBody.put("customerScale","");
            }else {
                reqBody.put("customerScale",customerScale);
            }
            if(customerIndustry.equals("请选择")){
                reqBody.put("customerIndustry","");
            }else {
                reqBody.put("customerIndustry",customerIndustry);
            }

            reqBody.put("followUserId",followPersonName);
            reqBody.put("customerProvinceCode",ProvinceCodestr);
            reqBody.put("customerCityCode",CityCodestr);
            reqBody.put("customerDistrictCode",DistrictCodestr);
            reqBody.put("customerAddress", addPrivateCueActivity.customerAddress.getText().toString());
            reqBody.put("customerIntroduce", addPrivateCueActivity.customerIntroduce.getText().toString());

            NetUtils netUtils = NetUtils.getInstance();
            netUtils.postDataAsynToNetHeader(Constant.APIURL +"lead/private/insert", reqBody, new NetUtils.MyNetCall() {
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
