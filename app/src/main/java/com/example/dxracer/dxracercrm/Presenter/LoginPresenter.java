package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.LoginInterface;
import com.example.dxracer.dxracercrm.Model.LoginModel;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.HttpUtils;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class LoginPresenter implements LoginInterface.Presenter {
    private LoginInterface.View view;
    private LoginModel model;
    private Context context;

    public LoginPresenter(LoginInterface.View view,Context context){
        this.view = view;
        this.model = new LoginModel();
        this.context = context;
    }

    @Override
    public void login(String url, String username, String password) {
        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put( "userName", username );
        reqBody.put( "password", password );
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNet(Constant.APIURL + url, reqBody, new NetUtils.MyNetCall() {
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


    /**
     * 消息处理Handler
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                switch (msg.what) {
                    case 0:// 解析返回数据
                        JsonObject jsonObject = (JsonObject)new JsonParser().parse(msg.obj.toString());
                        String code = jsonObject.get("code").getAsString();
                        if(code.equals("error")){
                            view.failed();
                            Toast.makeText(context,jsonObject.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                        }
                        if(code.equals("success")){
                            view.succeed();
                            SharedPreferencesUtils.getInstance().putString(SharedPreferencesUtils.Authorization,jsonObject.get("msg").getAsString());
                        }

                        break;



                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }
        }

    };
}
