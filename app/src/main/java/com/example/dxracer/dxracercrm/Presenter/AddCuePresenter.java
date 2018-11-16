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

import com.example.dxracer.dxracercrm.Interface.AddCueInterface;
import com.example.dxracer.dxracercrm.Model.AccessChannelsModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.CitySelect1Activity;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.AddCueActivity;
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

public class AddCuePresenter implements AddCueInterface.presenter,View.OnClickListener {

    private AddCueInterface.View view;
    private AddCueActivity addCueActivity;
    private List<AccessChannelsModel> accessChannelsModels_lead_source;
    private List<AccessChannelsModel> accessChannelsModels_customer_scale;
    private List<AccessChannelsModel> accessChannelsModels_customer_industry;
    public ArrayAdapter<AccessChannelsModel> arrayAdapter_lead_source;
    public ArrayAdapter<AccessChannelsModel> arrayAdapter_customer_scale;
    public ArrayAdapter<AccessChannelsModel> arrayAdapter_customer_industry;
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

    public AddCuePresenter(AddCueInterface.View view,final AddCueActivity addCueActivity){
        this.addCueActivity = addCueActivity;
        this.view = view;
        addCueActivity.leadGetDate.setOnClickListener(this);
        addCueActivity.selectAddress.setOnClickListener(this);
        //获取当前日期
        getDate();
        addCueActivity.leadGetDate.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);


        addCueActivity.popWindow = new PopWindow.Builder( addCueActivity)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView( addCueActivity.customView)
                .create();

        addCueActivity.citySelect1Activity=(CitySelect1Activity) addCueActivity.customView.findViewById(R.id.apvAddress);
        addCueActivity.citySelect1Activity.setOnAddressPickerSure(new CitySelect1Activity.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String Province, String City, String District, String ProvinceCode, String CityCode, String DistrictCode) {

                addCueActivity.selectAddress.setText(Province+" "+City+" "+District);
                ProvinceCodestr = ProvinceCode;
                CityCodestr = CityCode;
                DistrictCodestr = DistrictCode;
                addCueActivity.popWindow.dismiss();
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
                            arrayAdapter_lead_source = new ArrayAdapter<AccessChannelsModel>(addCueActivity.getApplication(), R.layout.simple_spinner_item,accessChannelsModels_lead_source);
                            arrayAdapter_lead_source.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addCueActivity.Accesschannels.setAdapter(arrayAdapter_lead_source);
                            addCueActivity.Accesschannels.setOnItemSelectedListener(Accesschannelslistener);
                        }

                        if(!addCueActivity.type.equals("2")){
                            //获取渠道
                            int d=arrayAdapter_lead_source.getCount();
                            for(int j=0;j<d;j++){
                                if(addCueActivity.intent.getStringExtra("leadSource").equals(arrayAdapter_lead_source.getItem(j).getKey())){
                                    addCueActivity.Accesschannels.setAdapter(arrayAdapter_lead_source);
                                    addCueActivity.Accesschannels.setSelection(j,true);
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
                            arrayAdapter_customer_scale = new ArrayAdapter<AccessChannelsModel>(addCueActivity.getApplication(), R.layout.simple_spinner_item,accessChannelsModels_customer_scale);
                            arrayAdapter_customer_scale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addCueActivity.Customersize.setAdapter(arrayAdapter_customer_scale);
                            addCueActivity.Customersize.setOnItemSelectedListener(Customersizelistener);
                        }

                        if(!addCueActivity.type.equals("2")){
                            //客户规模
                            int a=arrayAdapter_customer_scale.getCount();
                            for(int j=0;j<a;j++){
                                if(addCueActivity.intent.getStringExtra("customerScale").equals(arrayAdapter_customer_scale.getItem(j).getKey())){
                                    addCueActivity.Customersize.setAdapter(arrayAdapter_customer_scale);
                                    addCueActivity.Customersize.setSelection(j,true);
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
                            arrayAdapter_customer_industry = new ArrayAdapter<AccessChannelsModel>(addCueActivity.getApplication(), R.layout.simple_spinner_item,accessChannelsModels_customer_industry);
                            arrayAdapter_customer_industry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addCueActivity.Customerindustry.setAdapter(arrayAdapter_customer_industry);
                            addCueActivity.Customerindustry.setOnItemSelectedListener(Customerindustrylistener);
                        }
                        if(!addCueActivity.type.equals("2")){
                            //客户行业
                            int c=arrayAdapter_customer_industry.getCount();
                            for(int j=0;j<c;j++){
                                if(addCueActivity.intent.getStringExtra("customerIndustry").equals(arrayAdapter_customer_industry.getItem(j).getKey())){
                                    addCueActivity.Customerindustry.setAdapter(arrayAdapter_customer_industry);
                                    addCueActivity.Customerindustry.setSelection(j,true);
                                }
                            }
                        }

                        break;

                    case 3:
                        JsonObject jsonObject = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code = jsonObject.get("code").getAsString();
                        if(code.equals("error")){
                            view.failed();
                            Toast.makeText(addCueActivity,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
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
            leadSource = ((AccessChannelsModel)addCueActivity.Accesschannels.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //获取客户规模
    Spinner.OnItemSelectedListener Customersizelistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            customerScale = ((AccessChannelsModel)addCueActivity.Customersize.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //获取客户行业
    Spinner.OnItemSelectedListener Customerindustrylistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            customerIndustry = ((AccessChannelsModel)addCueActivity.Customerindustry.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onClick(View v) {
        if(!addCueActivity.type.equals("2")){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sf.parse(addCueActivity.leadGetDate.getText().toString());
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
        if (v == addCueActivity.leadGetDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(addCueActivity,
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
                                addCueActivity.leadGetDate.setText(sdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        //省市区选择
        if(v == addCueActivity.selectAddress){
            addCueActivity.popWindow.show();
        }
    }



    //保存数据

    public void SaveData(){
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("leadGetDate",addCueActivity.leadGetDate.getText().toString());
        if(leadSource.equals("请选择")){
            Toast.makeText(addCueActivity,"获取渠道不能为空！",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(addCueActivity.customerShortName.getText().toString())){
            Toast.makeText(addCueActivity,"客户简称不能为空！",Toast.LENGTH_SHORT).show();
        }else  {
            reqBody.put("leadSource",leadSource);
            reqBody.put("customerShortName",addCueActivity.customerShortName.getText().toString());
            reqBody.put("customerFullName",addCueActivity.customerFullName.getText().toString());
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


            reqBody.put("customerProvinceCode",ProvinceCodestr);
            reqBody.put("customerCityCode",CityCodestr);
            reqBody.put("customerDistrictCode",DistrictCodestr);
            reqBody.put("customerAddress",addCueActivity.customerAddress.getText().toString());
            reqBody.put("customerIntroduce",addCueActivity.customerIntroduce.getText().toString());

            NetUtils netUtils = NetUtils.getInstance();
            netUtils.postDataAsynToNetHeader(Constant.APIURL +"lead/public/insert", reqBody, new NetUtils.MyNetCall() {
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
