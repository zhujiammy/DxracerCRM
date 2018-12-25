package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Interface.EditAddressInterface;
import com.example.dxracer.dxracercrm.Presenter.EditAddressPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.CitySelect1Activity;
import com.hmy.popwindow.PopWindow;

import org.w3c.dom.Text;


//编辑地址
public class EditAddress extends AppCompatActivity implements EditAddressInterface.View,View.OnClickListener {


    private EditAddressPresenter presenter;
    private TextView contractNo;
    private TextView addtype;
    public EditText person;
    public EditText mobile;
    public TextView selectAddress;
    public EditText addr;
    private Toolbar toolbar;
    public TextView toolbar_title;
    public Intent intent;
    public CitySelect1Activity citySelect1Activity;
    public PopWindow popWindow;
    public View customView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dditaddress);
        intent = getIntent();
        initUI();
        presenter = new EditAddressPresenter(this,this);
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

        contractNo = (TextView) findViewById(R.id.contractNo);
        addtype = (TextView) findViewById(R.id.type);
        person = (EditText) findViewById(R.id.person);
        mobile = (EditText) findViewById(R.id.mobile);
        selectAddress = (TextView) findViewById(R.id.selectAddress);
        selectAddress.setOnClickListener(this);
        addr = (EditText) findViewById(R.id.addr);
        contractNo = (TextView) findViewById(R.id.contractNo);
        customView = View.inflate(getApplicationContext(), R.layout.address_select, null);
        citySelect1Activity=(CitySelect1Activity) customView.findViewById(R.id.apvAddress);

        contractNo.setText(intent.getStringExtra("contractNo"));
        addtype.setText(intent.getStringExtra("type"));
        mobile.setText(intent.getStringExtra("mobile"));
        person.setText(intent.getStringExtra("person"));
        selectAddress.setText(intent.getStringExtra("address"));
        addr.setText(intent.getStringExtra("addr"));
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
        App app = (App)getApplication();
        app.setIseditaddressRefresh(true);
        finish();
    }

    @Override
    public void failed() {

    }

    @Override
    public void onClick(View v) {
        if(v == selectAddress){
          popWindow.show();
        }
    }
}
