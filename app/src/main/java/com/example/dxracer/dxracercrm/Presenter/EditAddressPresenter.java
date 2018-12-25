package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.EditAddressInterface;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.CitySelect1Activity;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.EditAddress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hmy.popwindow.PopWindow;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class EditAddressPresenter {

    private EditAddress editAddress;
    private EditAddressInterface.View view;
    private String ProvinceCodestr = "";
    private String CityCodestr = "";
    private String DistrictCodestr = "";

    public EditAddressPresenter(EditAddressInterface.View view, final EditAddress editAddress){
        this.view = view;
        this.editAddress = editAddress;

        ProvinceCodestr = editAddress.intent.getStringExtra("Provice");
        CityCodestr = editAddress.intent.getStringExtra("City");
        DistrictCodestr = editAddress.intent.getStringExtra("Distict");


        editAddress.popWindow = new PopWindow.Builder(editAddress)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setView( editAddress.customView)
                .create();

        editAddress.citySelect1Activity=(CitySelect1Activity) editAddress.customView.findViewById(R.id.apvAddress);
        editAddress.citySelect1Activity.setOnAddressPickerSure(new CitySelect1Activity.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String Province, String City, String District, String ProvinceCode, String CityCode, String DistrictCode) {

                editAddress.selectAddress.setText(Province+" "+City+" "+District);
                ProvinceCodestr = ProvinceCode;
                CityCodestr = CityCode;
                DistrictCodestr = DistrictCode;
                editAddress.popWindow.dismiss();
            }
        });

    }



    //保存数据

    public void SaveData(){
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("id",editAddress.intent.getStringExtra("id"));
        if(TextUtils.isEmpty(editAddress.person.getText().toString())){
            Toast.makeText(editAddress,"联系人不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(editAddress.mobile.getText().toString())) {
            Toast.makeText(editAddress,"联系电话不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(editAddress.addr.getText().toString())) {
            Toast.makeText(editAddress,"详细地址不能为空",Toast.LENGTH_SHORT).show();
        }else {
            reqBody.put("person",editAddress.person.getText().toString());
            reqBody.put("mobile",editAddress.mobile.getText().toString());

            reqBody.put("provice",ProvinceCodestr);
            reqBody.put("city",CityCodestr);
            reqBody.put("distict",DistrictCodestr);

            reqBody.put("addr", editAddress.addr.getText().toString());

            NetUtils netUtils = NetUtils.getInstance();
            netUtils.postDataAsynToNetHeader(Constant.APIURL +"contract/address/update", reqBody, new NetUtils.MyNetCall() {
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
                            Toast.makeText(editAddress,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            Toast.makeText(editAddress,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                            view.succeed();

                        }
                        break;

                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };


    }

