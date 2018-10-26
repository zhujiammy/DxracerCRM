package com.example.dxracer.dxracercrm.Presenter;

import android.os.Handler;

import com.example.dxracer.dxracercrm.Interface.LoginInterface;
import com.example.dxracer.dxracercrm.Model.LoginModel;

public class LoginPresenter implements LoginInterface.Presenter {
    private LoginInterface.View view;
    private LoginModel model;

    public LoginPresenter(LoginInterface.View view){
        this.view = view;
        this.model = new LoginModel();
    }
    @Override
    public void login(final String username, final String password) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(username.equals("zhujia")&&password.equals("1234")){
                    view.succeed();
                }else {
                    view.failed();
                }
            }
        },3000);

    }
}
