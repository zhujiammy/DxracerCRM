package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.OpportunityCancellationInterface;
import com.example.dxracer.dxracercrm.Model.AddRecordContactModel;
import com.example.dxracer.dxracercrm.Model.AddRecordModel;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.OpportunityCancellation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class OpportunityCancellationPresenter implements OpportunityCancellationInterface.presenter {

    private OpportunityCancellation opportunityCancellation;
    private OpportunityCancellationInterface.View view;
    private List<AddRecordModel> oppoStopOptionmode;
    public ArrayAdapter<AddRecordModel> arrayAdapter_oppoStopOptionmode;
    private String oppoStopOption;


    public OpportunityCancellationPresenter(OpportunityCancellation opportunityCancellation,OpportunityCancellationInterface.View view){
        this.opportunityCancellation = opportunityCancellation;
        this.view = view;


    }

    @Override
    public void loadgetDataListByType() {
        //获取作废原因
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("type","opportunity_close");
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


    public void SaveData(){
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("id",opportunityCancellation.id);

        if(oppoStopOption.equals("请选择")){
            Toast.makeText(opportunityCancellation,"作废原因不能为空",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(opportunityCancellation.oppoStopNote.getText().toString())){
            Toast.makeText(opportunityCancellation,"详细报告不能为空",Toast.LENGTH_SHORT).show();
        }
        else {
            reqBody.put("oppoStopOption",oppoStopOption);
            reqBody.put("oppoStopNote",opportunityCancellation.oppoStopNote.getText().toString());
            NetUtils netUtils = NetUtils.getInstance();

            netUtils.postDataAsynToNetHeader(Constant.APIURL +"oppo/bill/close",reqBody, new NetUtils.MyNetCall() {
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
                            Toast.makeText(opportunityCancellation,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            view.succeed();

                        }
                        break;
                    case 1:// 解析返回数据
                        JsonParser parsercustomer_scale = new JsonParser();
                        JsonArray jsonArray_scale = parsercustomer_scale.parse(msg.obj.toString()).getAsJsonArray();
                        oppoStopOptionmode = new ArrayList<>();
                        oppoStopOptionmode.clear();
                        oppoStopOptionmode.add(new AddRecordModel("0","请选择"));

                        for (JsonElement accesschannels:jsonArray_scale){
                            AddRecordModel addRecordModel = gson.fromJson(accesschannels,AddRecordModel.class);
                            oppoStopOptionmode.add(new AddRecordModel(addRecordModel.getKey(),addRecordModel.getLabel()));
                            arrayAdapter_oppoStopOptionmode = new ArrayAdapter<AddRecordModel>(opportunityCancellation.getApplicationContext(), R.layout.simple_spinner_item,oppoStopOptionmode);
                            arrayAdapter_oppoStopOptionmode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            opportunityCancellation.oppoStopOption.setAdapter(arrayAdapter_oppoStopOptionmode);
                            opportunityCancellation.oppoStopOption.setOnItemSelectedListener(oppoStopOptionmodelistener);
                        }


                        break;


                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };

    Spinner.OnItemSelectedListener oppoStopOptionmodelistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            oppoStopOption = ((AddRecordModel)opportunityCancellation.oppoStopOption.getSelectedItem()).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
