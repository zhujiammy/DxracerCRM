package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.AddRecordInterface;
import com.example.dxracer.dxracercrm.Model.AccessChannelsModel;
import com.example.dxracer.dxracercrm.Model.AddRecordContactModel;
import com.example.dxracer.dxracercrm.Model.AddRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.AddRecordActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hmy.popwindow.PopItemAction;

import java.io.File;
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

public class AddRecordPresenter implements AddRecordInterface.presenter,View.OnClickListener {

    private AddRecordActivity addRecordActivity;
    private AddRecordInterface.View view;
    private List<AddRecordContactModel> AddRecordModel_contactsPerson;
    private List<AddRecordModel> AddRecordModel_communicateType;
    private List<AddRecordModel> AddRecordModel_communicateStage;

    public ArrayAdapter<AddRecordContactModel> arrayAdapter_contactsPerson;
    public ArrayAdapter<AddRecordModel> arrayAdapter_communicateType;
    public ArrayAdapter<AddRecordModel> arrayAdapter_communicateStage;

    private java.util.Calendar cal;
    private int mYear,mMonth,mDay,mhour,mMinute,mSecond;
    private TimePickerDialog dialog1;
    private  String date;
    private String contactsPerson;
    private String communicateType;
    private String communicateStage;
    private String file;

    public AddRecordPresenter (AddRecordInterface.View view,AddRecordActivity addRecordActivity){
        this.addRecordActivity = addRecordActivity;
        this.view = view;
        addRecordActivity.communicateTime.setOnClickListener(this);
        //获取当前日期
        getDate();
        file = String.valueOf(mYear +(mMonth + 1) + mDay +mhour+mMinute+mSecond);
        if(addRecordActivity.intent.getStringExtra("type").equals("0")){
            addRecordActivity.communicateTime.setText(mYear + "-" + (mMonth + 1) + "-" + mDay +" "+mhour+":"+mMinute+":"+mSecond);
        }else {
            addRecordActivity.communicateTime.setText(addRecordActivity.intent.getStringExtra("communicateTime"));
        }

    }

    private void getDate(){
        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mhour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        mSecond = cal.get(Calendar.SECOND);
    }

    @Override
    public void loadgetDataListByType() {
        //获取联系方式

        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","communicate_type");
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

    @Override
    public void loadgetDataListByPerson() {
        //获取联系人
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("leadNo",addRecordActivity.leadNo.getText().toString());
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"contacts/communicate/leadno/person", reqBody, new NetUtils.MyNetCall() {
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

    @Override
    public void loadgetDatalistBycommunicateStage() {
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","communicate_stage");
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


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson  = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
            super.handleMessage(msg);
            try{
                switch (msg.what) {
                    case 0:
                        JsonObject jsonObject = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code = jsonObject.get("code").getAsString();
                        if(code.equals("error")){
                            view.failed();
                            Toast.makeText(addRecordActivity,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            view.succeed();

                        }
                        break;
                    case 1:// 解析返回数据
                        JsonParser parsercustomer_scale = new JsonParser();
                        JsonArray jsonArray_scale = parsercustomer_scale.parse(msg.obj.toString()).getAsJsonArray();
                        AddRecordModel_communicateType = new ArrayList<>();
                        AddRecordModel_communicateType.clear();
                        AddRecordModel_communicateType.add(new AddRecordModel("0","请选择"));

                        for (JsonElement accesschannels:jsonArray_scale){
                            AddRecordModel addRecordModel = gson.fromJson(accesschannels,AddRecordModel.class);
                            AddRecordModel_communicateType.add(new AddRecordModel(addRecordModel.getKey(),addRecordModel.getLabel()));
                            arrayAdapter_communicateType = new ArrayAdapter<AddRecordModel>(addRecordActivity.getApplicationContext(), R.layout.simple_spinner_item,AddRecordModel_communicateType);
                            arrayAdapter_communicateType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addRecordActivity.communicateType.setAdapter(arrayAdapter_communicateType);
                            addRecordActivity.communicateType.setOnItemSelectedListener(communicateTypelistener);
                        }

                        if(addRecordActivity.intent.getStringExtra("type").equals("1")){
                            int a=arrayAdapter_communicateType.getCount();
                            for(int j=0;j<a;j++){
                                if(addRecordActivity.intent.getStringExtra("communicateType").equals(arrayAdapter_communicateType.getItem(j).getKey())){
                                    addRecordActivity.communicateType.setAdapter(arrayAdapter_communicateType);
                                    addRecordActivity.communicateType.setSelection(j,true);
                                }
                            }
                        }


                        break;
                    case 2:// 解析返回数据
                        JsonParser parsercustomer_industry = new JsonParser();
                        JsonArray jsonArray_industry = parsercustomer_industry.parse(msg.obj.toString()).getAsJsonArray();
                        AddRecordModel_contactsPerson = new ArrayList<>();
                        AddRecordModel_contactsPerson.clear();
                        AddRecordModel_contactsPerson.add(new AddRecordContactModel("0","请选择"));
                        for (JsonElement accesschannels:jsonArray_industry){
                            AddRecordContactModel addRecordContactModel = gson.fromJson(accesschannels,AddRecordContactModel.class);
                            AddRecordModel_contactsPerson.add(new AddRecordContactModel(addRecordContactModel.getId(),addRecordContactModel.getPersonName()));
                            arrayAdapter_contactsPerson = new ArrayAdapter<AddRecordContactModel>(addRecordActivity.getApplication(), R.layout.simple_spinner_item,AddRecordModel_contactsPerson);
                            arrayAdapter_contactsPerson.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addRecordActivity.contactsPersonId.setAdapter(arrayAdapter_contactsPerson);
                            addRecordActivity.contactsPersonId.setOnItemSelectedListener(contactsPersonlistener);
                        }

                        if(addRecordActivity.intent.getStringExtra("type").equals("1")){
                            int a=arrayAdapter_contactsPerson.getCount();
                            for(int j=0;j<a;j++){
                                if(addRecordActivity.intent.getStringExtra("contactsPersonName").equals(arrayAdapter_contactsPerson.getItem(j).getPersonName())){
                                    addRecordActivity.contactsPersonId.setAdapter(arrayAdapter_contactsPerson);
                                    addRecordActivity.contactsPersonId.setSelection(j,true);
                                }
                            }
                        }

                        break;

                    case 4:// 解析返回数据
                        JsonParser parsercustomer_communicate_stage = new JsonParser();
                        JsonArray jsonArray_communicate_stage = parsercustomer_communicate_stage.parse(msg.obj.toString()).getAsJsonArray();
                        AddRecordModel_communicateStage = new ArrayList<>();
                        AddRecordModel_communicateStage.clear();
                        AddRecordModel_communicateStage.add(new AddRecordModel("0","请选择"));

                        for (JsonElement accesschannels:jsonArray_communicate_stage){
                            AddRecordModel addRecordModel = gson.fromJson(accesschannels,AddRecordModel.class);
                            AddRecordModel_communicateStage.add(new AddRecordModel(addRecordModel.getKey(),addRecordModel.getLabel()));
                            arrayAdapter_communicateStage = new ArrayAdapter<AddRecordModel>(addRecordActivity.getApplicationContext(), R.layout.simple_spinner_item,AddRecordModel_communicateStage);
                            arrayAdapter_communicateStage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            addRecordActivity.communicateStage.setAdapter(arrayAdapter_communicateStage);
                            addRecordActivity.communicateStage.setOnItemSelectedListener(communicate_stagelistener);
                        }

                 /*       if(!addRecordActivity.type.equals("2")){
                            //客户规模
                            int a=arrayAdapter_customer_scale.getCount();
                            for(int j=0;j<a;j++){
                                if(addCueActivity.intent.getStringExtra("customerScale").equals(arrayAdapter_customer_scale.getItem(j).getKey())){
                                    addCueActivity.Customersize.setAdapter(arrayAdapter_customer_scale);
                                    addCueActivity.Customersize.setSelection(j,true);
                                }
                            }
                        }*/

                        break;

                    case 3:
                        JsonObject jsonObject1 = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code1 = jsonObject1.get("code").getAsString();
                        if(code1.equals("error")){
                            view.failed();
                           // Toast.makeText(addCueActivity,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code1.equals("success")){
                            view.succeed();

                        }
                        break;

                    case 5:
                        JsonObject jsonObject5 = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code5 = jsonObject5.get("code").getAsString();
                        if(code5.equals("error")){
                            view.failed();
                            Toast.makeText(addRecordActivity,jsonObject5.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code5.equals("success")){
                            App app = (App)addRecordActivity.getApplication();
                            app.setIsrecordRefresh(true);
                            view.succeed();

                        }
                        break;

                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };


    //联系人下拉框值
    Spinner.OnItemSelectedListener contactsPersonlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            contactsPerson = ((AddRecordContactModel)addRecordActivity.contactsPersonId.getSelectedItem()).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //联系方式下拉框值
    Spinner.OnItemSelectedListener communicateTypelistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            communicateType = ((AddRecordModel)addRecordActivity.communicateType.getSelectedItem()).getKey();
            Log.e("TAG", "onItemSelected: "+communicateType );

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    //跟进阶段下拉框值
    Spinner.OnItemSelectedListener communicate_stagelistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/*            communicateType = ((AddRecordModel)addRecordActivity.communicateType.getSelectedItem()).getKey();
            Log.e("TAG", "onItemSelected: "+communicateType );*/
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void SaveData(){
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("leadNo",addRecordActivity.leadNo.getText().toString());
        if(addRecordActivity.intent.getStringExtra("type").equals("1")){
            reqBody.put("id",addRecordActivity.intent.getStringExtra("id"));
        }
        if(contactsPerson.equals("0")){
            Toast.makeText(addRecordActivity,"联系人不能为空",Toast.LENGTH_SHORT).show();
        }else if(communicateType.equals("请选择")){
            Toast.makeText(addRecordActivity,"联系方式不能为空",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(addRecordActivity.communicateResult.getText().toString())){
            Toast.makeText(addRecordActivity,"跟进结果不能为空",Toast.LENGTH_SHORT).show();
        }/*else if(communicateStage.equals("请选择")){
            Toast.makeText(addRecordActivity,"跟进阶段不能为空",Toast.LENGTH_SHORT).show();
        }*/
        else {
            reqBody.put("contactsPersonId",contactsPerson);
            reqBody.put("communicateType",communicateType);
            reqBody.put("communicateTime",addRecordActivity.communicateTime.getText().toString());
            reqBody.put("communicateResult",addRecordActivity.communicateResult.getText().toString());
           // reqBody.put("communicateStage",communicateStage);
        }



        if(addRecordActivity.intent.getStringExtra("type").equals("0")){
            NetUtils netUtils = NetUtils.getInstance();

            netUtils.uploadFile(Constant.APIURL +"contacts/communicate/insert",addRecordActivity.file,"communicateFileImg",file+".png", reqBody, new NetUtils.MyNetCall() {
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
        }else {
            NetUtils netUtils = NetUtils.getInstance();

            netUtils.uploadFile(Constant.APIURL +"contacts/communicate/update",addRecordActivity.file,"communicateFileImg",file+".png", reqBody, new NetUtils.MyNetCall() {
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






    }


    @Override
    public void onClick(View v) {

        if(v == addRecordActivity.communicateTime){
            DatePickerDialog datePickerDialog = new DatePickerDialog(addRecordActivity,
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
                                @SuppressLint("SimpleDateFormat") final String sdate=(new SimpleDateFormat("yyyy-MM-dd")).format(date);
                                new TimePickerDialog(addRecordActivity,new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        Date date1 = null;
                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                                        String time = hourOfDay+":"+minute+":"+"00";
                                        try {

                                            date1 = sdf1.parse(time);
                                            @SuppressLint("SimpleDateFormat") final String sdate1=(new SimpleDateFormat("HH:mm:ss")).format(date1);
                                            addRecordActivity.communicateTime.setText(sdate+" "+sdate1);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                },mhour, mMinute, true).show();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    mYear, mMonth, mDay);
            datePickerDialog.show();

        }
    }
}
