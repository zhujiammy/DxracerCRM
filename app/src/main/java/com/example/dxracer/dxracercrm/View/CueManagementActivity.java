package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dxracer.dxracercrm.R;

//线索管理

public class CueManagementActivity extends AppCompatActivity  implements View.OnClickListener {

    private Toolbar toolbar;
    private FragmentManager manager;
    private PublicCue publicCue;
    private PrivateCue privateCue;
    private RadioButton publiccue_rb;
    private RadioButton privatecue_rb;
    public static final int  INTENT=1004;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuemanagement);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        publiccue_rb = (RadioButton) findViewById(R.id.publiccue);
        privatecue_rb = (RadioButton) findViewById(R.id.privatecue);

        publiccue_rb.setOnClickListener(this);
        privatecue_rb.setOnClickListener(this);

        publicCue = new PublicCue();
        privateCue = new PrivateCue();


        manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.main_content,publicCue);
        transaction.add(R.id.main_content,privateCue);
        transaction.show(publicCue);
        transaction.hide(privateCue);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent ;
        int id = item.getItemId();
        if(id == R.id.save_btn){
            if(publicCue.isHidden()){
                Toast.makeText(getApplicationContext(),"私有",Toast.LENGTH_SHORT).show();
            }else {
                intent = new Intent(getApplicationContext(),AddCueActivity.class);
                startActivityForResult(intent,INTENT);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case INTENT:
                    if(data.getStringExtra("statue").equals("1")){
                        publicCue.onRefresh();
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        FragmentTransaction transaction=manager.beginTransaction();

        switch (v.getId()){

            //公有线索
            case R.id.publiccue:
                transaction.show(publicCue);
                transaction.hide(privateCue);
                transaction.commit();
                break;
             //私有线索
            case R.id.privatecue:
                transaction.show(privateCue);
                transaction.hide(publicCue);
                transaction.commit();
                break;
        }
    }
}
