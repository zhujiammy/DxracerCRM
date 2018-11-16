package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.AddCueInterface;
import com.example.dxracer.dxracercrm.Model.AccessChannelsModel;
import com.example.dxracer.dxracercrm.Presenter.AddCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.CitySelect1Activity;
import com.hmy.popwindow.PopWindow;

import org.json.JSONException;

public class AddCueActivity extends AppCompatActivity implements AddCueInterface.View {

    private AddCuePresenter presenter;
    public Spinner Accesschannels;
    public Spinner Customersize;
    public Spinner Customerindustry;
    public TextView leadGetDate;
    public EditText customerShortName;
    public EditText customerFullName;
    public EditText customerAddress;
    public EditText customerIntroduce;
    public TextView selectAddress;
    public PopWindow popWindow;
    public View customView;
    private Toolbar toolbar;
    public TextView toolbar_title;
    public CitySelect1Activity citySelect1Activity;
    public  Intent intent;
    public String type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cue);
        intent = getIntent();
        type = intent.getStringExtra("type");
        initUI();
        presenter = new AddCuePresenter(this,this);
        presenter.loadgetDataListByType();
        presenter.loadgetDataListByCustomerScale();
        presenter.loadgetDataListBycustomerindustry();

        edit();


    }

    private void initUI(){

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
        Accesschannels = (Spinner) findViewById(R.id.Accesschannels);
        Customersize = (Spinner) findViewById(R.id.Customersize);
        Customerindustry = (Spinner) findViewById(R.id.Customerindustry);
        leadGetDate = (TextView) findViewById(R.id.leadGetDate);
        selectAddress = (TextView) findViewById(R.id.selectAddress);
        customerShortName = (EditText) findViewById(R.id.customerShortName);
        customerFullName = (EditText) findViewById(R.id.customerFullName);
        customerAddress = (EditText) findViewById(R.id.customerAddress);
        customerIntroduce = (EditText) findViewById(R.id.customerIntroduce);
        customView = View.inflate(getApplicationContext(), R.layout.address_select, null);
        citySelect1Activity=(CitySelect1Activity) customView.findViewById(R.id.apvAddress);


    }

    private void edit(){
        //编辑时初始数据
        if(type.equals("1")){
            //编辑
            toolbar_title.setText("编辑公有线索");
            //获得日期
            leadGetDate.setText(intent.getStringExtra("leadGetDate"));
            //客户简称
            customerShortName.setText(intent.getStringExtra("customerShortName"));
            //客户全称
            customerFullName.setText(intent.getStringExtra("customerFullName"));
            //省市区
            selectAddress.setText(intent.getStringExtra("customerProvinceName")+" "+intent.getStringExtra("customerCityName")+" "+intent.getStringExtra("customerDistrictName"));
            //详细地址
            customerAddress.setText(intent.getStringExtra("customerAddress"));
            //客户介绍
            customerIntroduce.setText(intent.getStringExtra("customerIntroduce"));
        }
        else{
            //编辑
            toolbar_title.setText("新增公有线索");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simbit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save_btn){
            presenter.SaveData();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void succeed() {
        Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra("statue","1");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void failed() {

    }

}
