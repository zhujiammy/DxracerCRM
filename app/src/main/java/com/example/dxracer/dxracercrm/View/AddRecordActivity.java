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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dxracer.dxracercrm.Interface.AddContactsInterface;
import com.example.dxracer.dxracercrm.Interface.AddRecordInterface;
import com.example.dxracer.dxracercrm.Presenter.AddRecordPresenter;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.MyBottomSheetDialog;

import org.devio.takephoto.app.TakePhoto;
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

public class AddRecordActivity extends AppCompatActivity implements AddRecordInterface.View,AddContactsInterface.View,TakePhoto.TakeResultListener,InvokeListener,View.OnClickListener {

    private AddRecordPresenter presenter;
    public TextView leadNo;
    public Spinner contactsPersonId;
    public Spinner communicateType;
    public Spinner communicateStage;
    public TextView communicateTime;
    public EditText communicateResult;

    private LinearLayout communicateStage_lin;

    public TakePhoto takePhoto;
    public File file;
    public ImageView communicateFileImg;

    private Toolbar toolbar;
    public TextView toolbar_title;
    private Intent intent;
    public InvokeParam invokeParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrecord);
        intent = getIntent();
        initUI();
        presenter = new AddRecordPresenter(this,this);
        presenter.loadgetDataListByPerson();
        presenter.loadgetDataListByType();
        presenter.loadgetDatalistBycommunicateStage();
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
        leadNo = (TextView) findViewById(R.id.leadNo);
        contactsPersonId = (Spinner) findViewById(R.id.contactsPersonId);
        communicateType = (Spinner) findViewById(R.id.communicateType);
        communicateStage = (Spinner) findViewById(R.id.communicateStage);
        communicateTime = (TextView) findViewById(R.id.communicateTime);
        communicateResult = (EditText) findViewById(R.id.communicateResult);
        communicateFileImg = (ImageView) findViewById(R.id.communicateFileImg);
        communicateFileImg.setOnClickListener(this);
        communicateStage_lin = (LinearLayout) findViewById(R.id.communicateStage_lin);
        communicateStage_lin.setVisibility(View.GONE);

        leadNo.setText(intent.getStringExtra("leadNo"));


    }
    @Override
    public void succeed() {
        Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failed() {

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
    public void takeSuccess(TResult result) {
        String iconPath = result.getImage().getOriginalPath();
        file = new File(iconPath);
        Glide.with(this).load(iconPath).into(communicateFileImg);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
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

    @Override
    public void onClick(View v) {
        if(v == communicateFileImg){
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
}
