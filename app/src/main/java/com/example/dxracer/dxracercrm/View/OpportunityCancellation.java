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

import com.example.dxracer.dxracercrm.Interface.OpportunityCancellationInterface;
import com.example.dxracer.dxracercrm.Presenter.OpportunityCancellationPresenter;
import com.example.dxracer.dxracercrm.R;

//作废机会

public class OpportunityCancellation extends AppCompatActivity implements OpportunityCancellationInterface.View {


    public TextView oppoNo;
    public TextView customerShortName;
    public Spinner oppoStopOption;
    public EditText oppoStopNote;
    public Intent intent;
    private Toolbar toolbar;
    public  String id;

    private OpportunityCancellationPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunitycancellation);
        intent = getIntent();
        presenter = new OpportunityCancellationPresenter(this,this);
        initUI();
        presenter.loadgetDataListByType();
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
        oppoNo = (TextView) findViewById(R.id.oppoNo);
        customerShortName = (TextView) findViewById(R.id.customerShortName);
        oppoStopOption = (Spinner) findViewById(R.id.oppoStopOption);
        oppoStopNote = (EditText) findViewById(R.id.oppoStopNote);

        oppoNo.setText(intent.getStringExtra("oppno"));
        customerShortName.setText(intent.getStringExtra("customerShortName"));
        id = intent.getStringExtra("id");
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
        Intent intent=new Intent();
        intent.putExtra("statue","1");
        setResult(RESULT_OK,intent);
        finish();
        Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failed() {

    }
}
