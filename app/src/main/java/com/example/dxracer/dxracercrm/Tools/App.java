package com.example.dxracer.dxracercrm.Tools;
/**
 * 用于存放倒计时时间
 * @author bnuzlbs-xuboyu 2017/4/5.
 */

import android.app.Application;
import android.content.Context;

//import com.facebook.drawee.backends.pipeline.Fresco;

import com.example.dxracer.dxracercrm.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.Map;

public class App extends Application {
    // 用于存放倒计时时间

    private static Context context;

    private boolean Refresh;//属否刷新
    private boolean maillisRefresh;//联系人列表刷新

    public static void setContext(Context context) {
        App.context = context;
    }

    public boolean isRefresh() {
        return Refresh;
    }

    public void setRefresh(boolean refresh) {
        Refresh = refresh;
    }


    public boolean isMaillisRefresh() {
        return maillisRefresh;
    }

    public void setMaillisRefresh(boolean maillisRefresh) {
        this.maillisRefresh = maillisRefresh;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

        //Fresco.initialize(this);
        //YoukuPlayerConfig.setClientIdAndSecret(*//*请修改成你自己的clientId和clientSecret*//*"792b1d08a5348d0d","9a98ce3841ae9f686fbea940a93b8167");

    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.textcolor_t, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
