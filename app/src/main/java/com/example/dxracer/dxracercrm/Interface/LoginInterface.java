package com.example.dxracer.dxracercrm.Interface;

import android.content.Context;

public interface LoginInterface {

    //Model
    interface Model{
        String getUsername();
        String getPassword();
    }

    //view
    interface View{
        void succeed();
        void failed();
    }
    //presenter
    interface Presenter{
        void login(String url, String username, String password);
    }
}
