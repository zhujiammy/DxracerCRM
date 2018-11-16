package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.R;

public class ContactsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    //联系人详情

    private Toolbar toolbar;
    public TextView toolbar_title;
    public Intent intent;
    private TextView personName;//姓名
    private TextView position;//职位
    private TextView nickName;//称呼
    private ImageView sex;//性别
    private TextView mobile;//电话号码
    private TextView email;//电子邮箱
    private TextView wechat;//微信
    private TextView birthday;//生日
    private String leadNo;
    private FloatingActionButton Communication_bt;
    private int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactsdetails);
        initUI();
        loadData();
    }

    private void initUI(){
        intent = getIntent();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        personName = (TextView) findViewById(R.id.personName);
        position = (TextView) findViewById(R.id.position);
        nickName = (TextView) findViewById(R.id.nickName);
        mobile = (TextView) findViewById(R.id.mobile);
        email = (TextView) findViewById(R.id.email);
        wechat = (TextView) findViewById(R.id.wechat);
        birthday = (TextView) findViewById(R.id.birthday);
        sex = (ImageView) findViewById(R.id.sex);
        Communication_bt = (FloatingActionButton) findViewById(R.id.Communication_bt);
        Communication_bt.setOnClickListener(this);
    }

    private void loadData(){
        toolbar_title.setText(intent.getStringExtra("personName"));
        personName.setText(intent.getStringExtra("personName"));
        position.setText(intent.getStringExtra("position"));
        nickName.setText(intent.getStringExtra("nickName"));
        mobile.setText(intent.getStringExtra("mobile"));
        email.setText(intent.getStringExtra("email"));
        wechat.setText(intent.getStringExtra("wechat"));
        birthday.setText(intent.getStringExtra("birthday"));
        leadNo = intent.getStringExtra("leadNo");
        id = intent.getIntExtra("id",1);
        if(intent.getStringExtra("sex").equals("M")){
            sex.setImageDrawable(getResources().getDrawable(R.mipmap.m));
        }else {
            sex.setImageDrawable(getResources().getDrawable(R.mipmap.w));
        }

    }

    @Override
    public void onClick(View v) {

        if(v ==  Communication_bt){
            //查看沟通记录
            intent = new Intent(getApplicationContext(),CommunicationRecordActivity.class);
            intent.putExtra("leadNo",leadNo);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }
}
