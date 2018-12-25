package com.example.dxracer.dxracercrm.Presenter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dxracer.dxracercrm.Adapter.ContactsListAdapter;
import com.example.dxracer.dxracercrm.Adapter.PublicCueAdapter;
import com.example.dxracer.dxracercrm.Interface.MaillistInterface;
import com.example.dxracer.dxracercrm.Model.MaillistModel;
import com.example.dxracer.dxracercrm.Model.PublicCueMode;
import com.example.dxracer.dxracercrm.R;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.Constant;
import com.example.dxracer.dxracercrm.Tools.HttpUtils.NetUtils;
import com.example.dxracer.dxracercrm.Tools.NullStringToEmptyAdapterFactory;
import com.example.dxracer.dxracercrm.View.ContactsDetailsActivity;
import com.example.dxracer.dxracercrm.View.ContactsFragment;
import com.example.dxracer.dxracercrm.View.MaillistFragment;
import com.example.dxracer.dxracercrm.common.FloatingBarItemDecoration;
import com.example.dxracer.dxracercrm.common.IndexBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Response;

public class ContactsFragmentPresenter  {

    private ContactsFragment maillistFragment;
    private LinkedHashMap<Integer,String> mHeaderList;
    private MaillistInterface.View view;
    public List<MaillistModel.MaillistBean> mContactList = new ArrayList<>();
    private  MaillistModel maillistModel;
    private int currentPage = 1;
    private int pageSize = 1000;


    public ContactsFragmentPresenter(final ContactsFragment maillistFragment, MaillistInterface.View view){
        this.maillistFragment = maillistFragment;
        this.view = view;


    }


    private void loadIndexBar(){
        maillistFragment.mRecyclerView.addItemDecoration(
                new FloatingBarItemDecoration(maillistFragment.getActivity(),  mHeaderList));
        maillistFragment.indexBar.setNavigators(new ArrayList<>( mHeaderList.values()));
        maillistFragment.indexBar.setOnTouchingLetterChangedListener(new IndexBar.OnTouchingLetterChangeListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                showLetterHintDialog(s);
                for (Integer position : mHeaderList.keySet()) {
                    if ( mHeaderList.get(position).equals(s)) {
                        maillistFragment. mLayoutManager.scrollToPositionWithOffset(position, 0);
                        return;
                    }
                }
            }

            @Override
            public void onTouchingStart(String s) {
                showLetterHintDialog(s);
            }

            @Override
            public void onTouchingEnd(String s) {
                hideLetterHintDialog();
            }
        });
    }

    private void hideLetterHintDialog() {
        maillistFragment.mOperationInfoDialog.dismiss();
    }
    /**
     * related to {@link IndexBar#mOnTouchingLetterChangeListener}
     * show dialog in the center of this window
     * @param s
     */
    private void showLetterHintDialog(String s) {
        if (maillistFragment.mOperationInfoDialog == null) {
            maillistFragment.mLetterHintView = maillistFragment.getLayoutInflater().inflate(R.layout.dialog_letter_hint, null);
            maillistFragment.mOperationInfoDialog = new PopupWindow(maillistFragment.mLetterHintView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
            maillistFragment.mOperationInfoDialog.setOutsideTouchable(true);
        }
        ((TextView) maillistFragment.mLetterHintView.findViewById(R.id.dialog_letter_hint_textview)).setText(s);
        maillistFragment.getActivity().getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                maillistFragment.mOperationInfoDialog.showAtLocation(maillistFragment.getActivity().getWindow().getDecorView().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
            }
        });
    }

    private void fetchContactList(){

        //构造请求参数
        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        reqBody.put("sort","ContactsPerson_id desc");
        currentPage = 1;
        reqBody.put("currentPage", String.valueOf(currentPage));
        reqBody.put("pageSize", String.valueOf(pageSize));
        reqBody.put("leadNo",maillistFragment.leadNo);
        reqBody.put("mainPerson", "");
        reqBody.put("personName", "");
        reqBody.put("mobile", "");
        reqBody.put("wechat", "");
        reqBody.put("sex", "");
        reqBody.put("birthday", "");
        reqBody.put("nickName", "");
        reqBody.put("position", "");
        reqBody.put("email", "");
        NetUtils netUtils = NetUtils.getInstance();
        netUtils.postDataAsynToNetHeader(Constant.APIURL +"contacts/person/list", reqBody, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String result = response.body().string();
                // TODO Auto-generated method stub
                com.example.dxracer.dxracercrm.Tools.Log.printJson("tag",result,"header");

                Message msg= Message.obtain(
                        mHandler,0,result
                );
                mHandler.sendMessage(msg);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });


    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson  = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
            super.handleMessage(msg);
            try{
                switch (msg.what) {

                    case 0:// 解析返回数据
                        //GSON直接解析成对象
                        //对象中拿到集合
                        //然后用上面一行写的gson来序列化和反序列化实体类type
                        maillistModel = gson.fromJson(msg.obj.toString(),MaillistModel.class);
                        //mContactList = maillistModel.getList();
                        for(int i = 0;i<maillistModel.getList().size();i++){
                            mContactList.add(new MaillistModel.MaillistBean(maillistModel.getList().get(i).getPersonName(),maillistModel.getList().get(i).getPosition(),maillistModel.getList().get(i).getMobile(),maillistModel.getList().get(i).getNickName(),maillistModel.getList().get(i).getEmail(),maillistModel.getList().get(i).getWechat(),maillistModel.getList().get(i).getBirthday(),
                                    maillistModel.getList().get(i).getLeadNo(),maillistModel.getList().get(i).getSex(),maillistModel.getList().get(i).getId(),maillistModel.getList().get(i).getMainPerson()));

                        }
                        maillistFragment.mAdapter.setOnitemClickListener(new ContactsListAdapter.OnitemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                              Intent  intent = new Intent(maillistFragment.getActivity(),ContactsDetailsActivity.class);
                                intent.putExtra("id",mContactList.get(position).getId());
                                intent.putExtra("personName",mContactList.get(position).getPersonName());
                                intent.putExtra("nickName",mContactList.get(position).getNickName());
                                intent.putExtra("position",mContactList.get(position).getPosition());
                                intent.putExtra("sex",mContactList.get(position).getSex());
                                intent.putExtra("mobile",mContactList.get(position).getMobile());
                                intent.putExtra("email",mContactList.get(position).getEmail());
                                intent.putExtra("wechat",mContactList.get(position).getWechat());
                                intent.putExtra("birthday",mContactList.get(position).getBirthday());
                                intent.putExtra("BusinessCar",mContactList.get(position).getBusinessCard());
                                intent.putExtra("leadNo",mContactList.get(position).getLeadNo());
                                intent.putExtra("mainPerson",mContactList.get(position).getMainPerson());
                                maillistFragment.startActivity(intent);
                            }
                        });
                        Log.e("TAG", "handleMessage: "+maillistModel.getList().size());
                        Collections.sort(mContactList, new Comparator<MaillistModel.MaillistBean>() {
                            @Override
                            public int compare(MaillistModel.MaillistBean l, MaillistModel.MaillistBean r) {
                                return l.compareTo(r);
                            }
                        });


                        preOperation();
                        loadIndexBar();

                }
            }catch (JsonIOException e){
                e.printStackTrace();
            }

        }
    };



    /**
     * fetch the data to display
     * need permission of Manifest.permission.READ_CONTACTS
     */
    public void fetchData() {
        if (mHeaderList == null) {
            mHeaderList = new LinkedHashMap<>();
        }
        fetchContactList();
    }

    /**
     * calculate the TAG and add to {@link #mHeaderList}
     */
    private void preOperation() {
        mHeaderList.clear();
        if (mContactList.size() == 0) {
            return;
        }
        addHeaderToList(0, mContactList.get(0).getInitial());
        for (int i = 1; i < mContactList.size(); i++) {
            if (!mContactList.get(i - 1).getInitial().equalsIgnoreCase(mContactList.get(i).getInitial())) {
                addHeaderToList(i, mContactList.get(i).getInitial());
            }
        }
    }




    private void addHeaderToList(int index, String header) {
        mHeaderList.put(index, header);
    }
}
