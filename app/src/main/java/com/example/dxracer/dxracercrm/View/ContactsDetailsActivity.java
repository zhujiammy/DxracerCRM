package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;

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
    private String sexstr,mainPerson;
    private String leadNo;
    int REQUEST_CODE = 0;
    private FloatingActionButton Communication_bt;
    private int idc;


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
        idc = intent.getIntExtra("id",1);
        sexstr = intent.getStringExtra("sex");
        mainPerson = intent.getStringExtra("mainPerson");
        if(intent.getStringExtra("sex").equals("M")){
            sex.setImageDrawable(getResources().getDrawable(R.mipmap.m));
        }else {
            sex.setImageDrawable(getResources().getDrawable(R.mipmap.w));
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.edit_btn){
            //编辑
            intent = new Intent(getApplicationContext(),AddContactsActivity.class);
            intent.putExtra("type","1");
            intent.putExtra("personName",personName.getText().toString());
            intent.putExtra("position",position.getText().toString());
            intent.putExtra("nickName",nickName.getText().toString());
            intent.putExtra("mobile",mobile.getText().toString());
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("wechat",wechat.getText().toString());
            intent.putExtra("birthday",birthday.getText().toString());
            intent.putExtra("leadNo",leadNo);
            intent.putExtra("id",idc);
            intent.putExtra("sex",sexstr);
            intent.putExtra("mainPerson",mainPerson);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    // 当结果返回后判断并执行操作
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();

                if (extras != null) {
                    Log.e("TAG", "onActivityResult: "+extras.getString("personName") );
                    toolbar_title.setText(extras.getString("personName"));
                    personName.setText(extras.getString("personName"));
                    position.setText(extras.getString("position"));
                    nickName.setText(extras.getString("nickName"));
                    mobile.setText(extras.getString("mobile"));
                    email.setText(extras.getString("email"));
                    wechat.setText(extras.getString("wechat"));
                    birthday.setText(extras.getString("birthday"));
                    if(extras.getString("sex").equals("M")){
                        sex.setImageDrawable(getResources().getDrawable(R.mipmap.m));
                    }else {
                        sex.setImageDrawable(getResources().getDrawable(R.mipmap.w));
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v ==  Communication_bt){
            //查看沟通记录
            intent = new Intent(getApplicationContext(),CommunicationRecordActivity.class);
            intent.putExtra("leadNo",leadNo);
            intent.putExtra("id",idc);
            startActivity(intent);
        }
    }
}
