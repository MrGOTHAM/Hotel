package com.acg.hotel;

import android.app.Application;
import android.content.Context;

import com.acg.hotel.bean.MemberBean;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * @Classname MyApplication
 * @Description 继承Application 实现全局Application     （保存登陆后的用户信息）
 * @Version 1.0.0
 * @Date 2022/8/27 19:27
 * @Created by an
 */
public class MyApplication extends Application {

    // 在App启动时执行
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //static 代码段可以防止内存泄露
    // 设置 头部刷新 和 尾部加载 样式
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new DropboxHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
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

    // ===========设置全局的参数

    private MemberBean loginMember;

    public boolean isLogin(){
        return null!= loginMember;
    }

    public MemberBean getLoginMember() {
        return loginMember;
    }

    public void setLoginMember(MemberBean loginMember) {
        this.loginMember = loginMember;
    }
}