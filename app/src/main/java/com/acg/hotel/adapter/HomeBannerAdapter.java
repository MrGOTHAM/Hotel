package com.acg.hotel.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acg.hotel.bean.BannerBean;
import com.acg.hotel.util.ServiceUrls;
import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * @Classname HomeBannerAdapter
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/29 19:07
 * @Created by an
 */
public class HomeBannerAdapter extends BannerAdapter<BannerBean, HomeBannerAdapter.BannerViewHolder> {

    private Context mContext;

    public HomeBannerAdapter(List<BannerBean> mDatas, Context context) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.mContext = context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    // 绑定控件的数据
    @Override
    public void onBindView(BannerViewHolder holder, BannerBean data, int position, int size) {
        String strImagePath = ServiceUrls.getMainPageMethodUrl("getBannerPicture")+"?pictureName="+data.getBannerPicture();

        // Glide 加载图片  - 简单用法
        Glide.with(mContext)
                .load(strImagePath)
                .into(holder.imageView);

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}