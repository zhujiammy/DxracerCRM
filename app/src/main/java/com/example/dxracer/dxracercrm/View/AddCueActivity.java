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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.Interface.AddCueInterface;
import com.example.dxracer.dxracercrm.Presenter.AddCuePresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.CitySelect1Activity;
import com.hmy.popwindow.PopWindow;

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
    public CitySelect1Activity citySelect1Activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cue);
        initUI();
        presenter = new AddCuePresenter(this,this);
        presenter.loadgetDataListByType();
        presenter.loadgetDataListByCustomerScale();
        presenter.loadgetDataListBycustomerindustry();


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
