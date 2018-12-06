package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dxracer.dxracercrm.Interface.AddQuotationInterface;
import com.example.dxracer.dxracercrm.Model.ProductModel;
import com.example.dxracer.dxracercrm.Presenter.AddQuotationPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

//新增报价单

public class AddQuotationActivity extends AppCompatActivity implements View.OnClickListener,AddQuotationInterface.View {



    public TextView oppoBillNo;
    public Spinner dealerContacts;
    public Spinner auditUserId;
    public Spinner supplierContacts;
    private Toolbar toolbar;
    private TextView product;
    private LinearLayout group;
    public  Intent intent;
    private AddQuotationPresenter presenter;
    public String sku_id;
    public String sku_quantity;
    public static final int  INTENT=1005;
    public List<String>list=new ArrayList<>();
   public final List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
    JsonArray jsonArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addquotation);
        intent = getIntent();
        presenter = new AddQuotationPresenter(this,this);
        initUI();
        presenter.loadgetDataListByTypeperson();
        presenter.loadgetDataListgetMineAndChildren();
        presenter.loadgetDataListgetMineAndParents();

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
        oppoBillNo = (TextView) findViewById(R.id.oppoBillNo);
        oppoBillNo.setText(intent.getStringExtra("oppno"));
        dealerContacts = (Spinner) findViewById(R.id.dealerContacts);
        auditUserId = (Spinner) findViewById(R.id.auditUserId);
        supplierContacts = (Spinner) findViewById(R.id.supplierContacts);
        product = (TextView) findViewById(R.id.product);
        group = (LinearLayout) findViewById(R.id.group);
        product.setOnClickListener(this);
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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case INTENT:
                    if(!data.getStringExtra("json").equals("")){
                        JSONArray jsonArray = new JSONArray(data.getStringExtra("json"));
                        datas.clear();
                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            final Map<String,String> map = new HashMap<String, String>();
                            //使用GSON，直接转成Bean对象

                            final View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.product_lin_data,null);
                            TextView fcno = (TextView) view.findViewById(R.id.fcno);
                            TextView skuName = (TextView) view.findViewById(R.id.skuName);
                            TextView skuSalePrice = (TextView) view.findViewById(R.id.skuSalePrice);
                            ImageView skuMainImg = (ImageView) view.findViewById(R.id.skuMainImg);
                            ImageView del = (ImageView) view.findViewById(R.id.del);
                            LinearLayout lingroup = (LinearLayout)view.findViewById(R.id.lingroup);
                            final EditText number = new EditText(getApplicationContext());
                            number.setBackground(getResources().getDrawable(R.drawable.textview_backgroud));
                            number.setGravity(Gravity.CENTER);
                            number.setTag(i);
                            number.setId(i);
                            number.setInputType(InputType.TYPE_CLASS_NUMBER);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);
                            lp.setMargins(0,20,20,20);
                         //   lp.gravity = Gravity.CENTER|Gravity.RIGHT;
                            lingroup.addView(number,lp);
                            fcno.setText(object.getString("fcno"));
                            skuName.setText(object.getString("skuName"));
                            skuSalePrice.setText("¥ "+object.getString("skuSalePrice"));
                            Glide.with(getApplicationContext()).load(Constant.imgurl+object.getString("skuMainImg")).into(skuMainImg);
                            number.setText("1");
                            sku_id = String.valueOf(object.getInt("id"));
                            sku_quantity = number.getText().toString();
                            map.put("sku_id",sku_id);
                            map.put("sku_quantity",sku_quantity);
                            datas.add(map);
                            del.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    group.removeView(view);
                                }
                            });

                            number.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),""+number.getId(),Toast.LENGTH_SHORT).show();
                                }
                            });
                            number.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    Log.e("TAG", "afterTextChanged: "+view.getId());
                                    for(int i = 0;i<datas.size();i++){
                                        if(number.getId() == i){
                                            map.put("sku_quantity",s.toString());
                                           datas.set(i,map);
                                        }
                                    }
                                }
                            });


                            group.addView(view);
                        }




                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if(v == product){
            //产品
            group.removeAllViews();
            Intent intent = new Intent(getApplicationContext(),ProductActivity.class);
            startActivityForResult(intent,INTENT);
        }
    }

    @Override
    public void succeed() {

    }

    @Override
    public void failed() {

    }


}
