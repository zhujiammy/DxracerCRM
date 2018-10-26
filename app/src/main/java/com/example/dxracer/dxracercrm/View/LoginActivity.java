package com.example.dxracer.dxracercrm.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.example.dxracer.dxracercrm.Interface.LoginInterface;
import com.example.dxracer.dxracercrm.Presenter.LoginPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.AndroidBug5497Workaround;
import com.example.dxracer.dxracercrm.Tools.ClearEditText;
import com.example.dxracer.dxracercrm.Tools.IEditTextChangeListener;
import com.example.dxracer.dxracercrm.Tools.WorksSizeCheckUtil;

public class LoginActivity extends AppCompatActivity  implements LoginInterface.View ,View.OnClickListener {

    private LoginPresenter presenter;
    private ClearEditText username_et;
    private ClearEditText password_et;
    private Button login_bt;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // fullScreen(this);
        setContentView(R.layout.login);
        //AndroidBug5497Workaround.assistActivity(this);

        presenter = new LoginPresenter(this);
        initView();
        initEvent();
    }

    private void initView(){
        login_bt = (Button)findViewById(R.id.login_btn);
        login_bt.setEnabled(false);
        username_et = (ClearEditText)findViewById(R.id.username_et);
        password_et = (ClearEditText)findViewById(R.id.password_et);
        WorksSizeCheckUtil.textChangeListener textChangeListener = new WorksSizeCheckUtil.textChangeListener(login_bt);
        textChangeListener.addAllEditText(username_et,password_et);
        WorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void textChange(boolean isHasContent){
                if(!isHasContent){
                    login_bt.setBackground(getResources().getDrawable(R.drawable.loginbtn_drawable));
                    login_bt.setEnabled(false);
                }else {
                    login_bt.setBackground(getResources().getDrawable(R.drawable.loginbtn_drawable_enabled));
                    login_bt.setEnabled(true);
                }
            }
        });

    }


    @SuppressLint("ObsoleteSdkInt")
    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }




    private void initEvent(){
        login_bt.setOnClickListener(this);
    }

    @Override
    public void succeed() {
        progressDialog.dismiss();
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void failed() {
        progressDialog.dismiss();
        Toast.makeText(this,"用户名或密码错误!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == login_bt){
            progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getResources().getString(R.string.progressDialog_login));
            progressDialog.show();
            presenter.login(username_et.getText().toString(),password_et.getText().toString());
        }
    }
}
