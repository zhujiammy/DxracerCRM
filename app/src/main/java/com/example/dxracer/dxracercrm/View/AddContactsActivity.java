package com.example.dxracer.dxracercrm.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dxracer.dxracercrm.Interface.AddContactsInterface;
import com.example.dxracer.dxracercrm.Presenter.AddContactsPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.App;
import com.example.dxracer.dxracercrm.Tools.MyBottomSheetDialog;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;


//新增联系人
public class AddContactsActivity extends AppCompatActivity implements AddContactsInterface.View,TakePhoto.TakeResultListener,InvokeListener ,View.OnClickListener {


    private AddContactsPresenter presenter;
    public InvokeParam invokeParam;
    private Toolbar toolbar;
    public TextView toolbar_title;
    //必要信息
    public int id;//id
    public TextView leadNo;//线索编号
    public RadioButton Rb_Yes,Rb_No;//是否默认联系人
    public EditText personName;//姓名
    public EditText mobile;//手机
    public EditText email;//电子邮箱
    public EditText wechat;//微信账号

    public RadioGroup contact_group;
    public RadioGroup sex_group;

    //补充信息
    public RadioButton Rb_m,Rb_w;//性别
    public TextView birthday;//生日
    public EditText nickName;//称呼
    public EditText position;//职位
    public ImageView businessCardFile;//名片
    private String sex,mainPerson;

    public TakePhoto takePhoto;
    public Intent intent;
    public File file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontacts);
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
        initUI();

        presenter = new AddContactsPresenter(this,AddContactsActivity.this);

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private  void  initUI(){

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        sex_group = (RadioGroup) findViewById(R.id.sex_group);
        contact_group = (RadioGroup)findViewById(R.id.contact_group);
        leadNo = (TextView) findViewById(R.id.leadNo);
        Rb_Yes = (RadioButton) findViewById(R.id.Rb_Yes);
        Rb_No = (RadioButton) findViewById(R.id.Rb_No);
        Rb_m = (RadioButton) findViewById(R.id.Rb_m);
        Rb_w = (RadioButton) findViewById(R.id.Rb_w);
        personName = (EditText) findViewById(R.id.personName);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        wechat = (EditText) findViewById(R.id.wechat);
        birthday = (TextView) findViewById(R.id.birthday);
        nickName = (EditText) findViewById(R.id.nickName);
        position = (EditText) findViewById(R.id.position);
        businessCardFile = (ImageView) findViewById(R.id.businessCardFile);
        businessCardFile.setOnClickListener(this);

        leadNo.setText(intent.getStringExtra("leadNo"));

        if(intent.getStringExtra("type").equals("0")){
            //新增联系人
            toolbar_title.setText("新增联系人");
        }

        if(intent.getStringExtra("type").equals("1")){
            //编辑联系人
            toolbar_title.setText("编辑联系人");
            id = intent.getIntExtra("id",0);
            personName.setText(intent.getStringExtra("personName"));
            position.setText(intent.getStringExtra("position"));
            nickName.setText(intent.getStringExtra("nickName"));
            mobile.setText(intent.getStringExtra("mobile"));
            email.setText(intent.getStringExtra("email"));
            wechat.setText(intent.getStringExtra("wechat"));
            birthday.setText(intent.getStringExtra("birthday"));
            sex = intent.getStringExtra("sex");
            mainPerson = intent.getStringExtra("mainPerson");
            if(intent.getStringExtra("sex").equals("M")){
                Rb_m.setChecked(true);
            }else {
                Rb_w.setChecked(true);
            }

            if(intent.getStringExtra("mainPerson").equals("Y")){
                Rb_Yes.setChecked(true);
            }else {
                Rb_No.setChecked(true);
            }


        }
    }



    @Override
    public void succeed() {
        Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_SHORT).show();
        App app = (App)getApplication();
        app.setMaillisRefresh(true);
        app.setContactsRefresh(true);

        Bundle bundle = new Bundle();
        bundle.putString("personName", personName.getText().toString());
        bundle.putString("position", position.getText().toString());
        bundle.putString("nickName", nickName.getText().toString());
        bundle.putString("mobile", mobile.getText().toString());
        bundle.putString("email", email.getText().toString());
        bundle.putString("wechat", wechat.getText().toString());
        bundle.putString("birthday", birthday.getText().toString());
        bundle.putString("sex",presenter.sex);
        bundle.putString("mainPerson", mainPerson);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        // 返回intent
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void failed() {

    }

    @Override
    public void takeSuccess(TResult result) {
        String iconPath = result.getImage().getOriginalPath();
        file = new File(iconPath);
        Glide.with(this).load(iconPath).into(businessCardFile);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    @Override
    public void onClick(View v) {
        if (v == businessCardFile){
            final MyBottomSheetDialog dialog = new MyBottomSheetDialog(this);
            View box_view = LayoutInflater.from(this).inflate(R.layout.takephoto,null);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
            dialog.setContentView(box_view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            TextView view_big_p = (TextView) box_view.findViewById(R.id.view_big_p);
            TextView camera = (TextView) box_view.findViewById(R.id.camera);
            TextView Album = (TextView) box_view.findViewById(R.id.Album);
            TextView cancel_btn = (TextView) box_view.findViewById(R.id.cancel_btn);
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent ;
                    File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
                    Uri uri = Uri.fromFile(file);
                    int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
                    CropOptions cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
                    switch (v.getId()) {

                        //查看大图
                        case R.id.view_big_p:

                            break;
                        //相机
                        case R.id.camera:
                            takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
                            break;
                        //相册
                        case R.id.Album:
                            //相机获取照片并剪裁
                            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
                            break;
                            //取消
                        case R.id.cancel_btn:
                            dialog.dismiss();
                            break;

                    }
                    dialog.dismiss();
                }
            };

            view_big_p.setOnClickListener(listener);
            cancel_btn.setOnClickListener(listener);
            camera.setOnClickListener(listener);
            Album.setOnClickListener(listener);



        }
    }

    /** * 获取TakePhoto实例 * @return */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        //设置压缩规则，最大500kb
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true);
        return takePhoto;
    }

}
