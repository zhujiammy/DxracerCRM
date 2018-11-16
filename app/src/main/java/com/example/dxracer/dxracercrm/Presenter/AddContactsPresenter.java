package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.AddContactsInterface;
import com.example.dxracer.dxracercrm.Model.AccessChannelsModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.AddContactsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class AddContactsPresenter implements View.OnClickListener {
    private AddContactsActivity addContactsActivity;

    private AddContactsInterface.View view;
    private java.util.Calendar cal;
    private int mYear,mMonth,mDay;
    public String mainPerson;
    public String sex;

    public AddContactsPresenter(AddContactsInterface.View view,AddContactsActivity addContactsActivity){

        this.addContactsActivity = addContactsActivity;
        this.view = view;
        addContactsActivity.birthday.setOnClickListener(this);
        //获取当前日期
        getDate();
        addContactsActivity.birthday.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        addContactsActivity.sex_group.setOnCheckedChangeListener(listener1);
        addContactsActivity.contact_group.setOnCheckedChangeListener(listener);

    }


    private void getDate(){
        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
    }

    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int radioButtonId = group.getCheckedRadioButtonId();
            //根据ID获取RadioButton的实例
            RadioButton rb = (RadioButton)addContactsActivity.findViewById(radioButtonId);
            //更新文本内容，以符合选中项
            if(rb.getText().equals("是")){
                mainPerson ="Y";
            }else {
                mainPerson ="N";
            }


        }
    };


    RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int radioButtonId = group.getCheckedRadioButtonId();
            //根据ID获取RadioButton的实例
            RadioButton rb = (RadioButton)addContactsActivity.findViewById(radioButtonId);
            //更新文本内容，以符合选中项
            if(rb.getText().equals("男")){
                sex ="M";
            }else {
                sex ="W";
            }


        }
    };

    public void SaveData(){
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("leadNo",addContactsActivity.leadNo.getText().toString());
        reqBody.put("mainPerson",mainPerson);
        if(TextUtils.isEmpty(addContactsActivity.personName.getText().toString())){
            Toast.makeText(addContactsActivity,"姓名不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(addContactsActivity.personName.getText().toString())){
            Toast.makeText(addContactsActivity,"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(addContactsActivity.email.getText().toString())){
            Toast.makeText(addContactsActivity,"电子邮箱不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(addContactsActivity.wechat.getText().toString())){
            Toast.makeText(addContactsActivity,"微信号不能为空",Toast.LENGTH_SHORT).show();
        }else {

            reqBody.put("personName",addContactsActivity.personName.getText().toString());
            reqBody.put("mobile",addContactsActivity.mobile.getText().toString());
            reqBody.put("email",addContactsActivity.email.getText().toString());
            reqBody.put("wechat",addContactsActivity.wechat.getText().toString());
            reqBody.put("sex",sex);
            reqBody.put("birthday",addContactsActivity.birthday.getText().toString());
            reqBody.put("nickName",addContactsActivity.nickName.getText().toString());
            reqBody.put("position",addContactsActivity.position.getText().toString());
        }



            NetUtils netUtils = NetUtils.getInstance();
            netUtils.uploadFile(Constant.APIURL +"contacts/person/insert",addContactsActivity.file,"businessCardFile",addContactsActivity.file.getAbsolutePath()+".png", reqBody, new NetUtils.MyNetCall() {
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
            super.handleMessage(msg);
            try{
                switch (msg.what) {
                    case 0:
                        JsonObject jsonObject = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code = jsonObject.get("code").getAsString();
                        if(code.equals("error")){
                            view.failed();
                            Toast.makeText(addContactsActivity,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {


        //生日日期
        if(v == addContactsActivity.birthday){
            DatePickerDialog datePickerDialog = new DatePickerDialog(addContactsActivity,
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
                                addContactsActivity.birthday.setText(sdate);
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
