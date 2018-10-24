package com.example.dxracer.dxracercrm.Model;

import com.example.dxracer.dxracercrm.Interface.LoginInterface;

public class LoginModel implements LoginInterface.Model {

    private String username;
    private String password;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
