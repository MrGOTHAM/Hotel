package com.acg.hotel.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acg.hotel.MyApplication;
import com.acg.hotel.R;
import com.acg.hotel.bean.RoomTypeBean;
import com.acg.hotel.util.ServiceUrls;
import com.acg.hotel.util.Tools;
import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

/**
 * @Classname RvRoomTypeAdapter
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/4 15:39
 * @Created by an
 */
public class RvRoomTypeAdapter extends RecyclerView.Adapter<RvRoomTypeAdapter.ViewHolder> {

    private List<RoomTypeBean> mListDatas; // recyclerView数据

    private Activity mActivityContext;  // Activity上下文

    private MyApplication mMyApplication; // 全局Application对象

    private OnItemClickListener mOnItemClickListener;   // 存放点击事件接口实现类

    public interface OnItemClickListener {
        // 整条数据的点击事件
        void onItemClick(View view, RoomTypeBean data, int position);

        // 客房类型图片点击事件
        void onImageClick(View view, RoomTypeBean data, int position);
    }

    // 设置点击事件方法
    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public RvRoomTypeAdapter(List<RoomTypeBean> listDatas, Activity activityContext, MyApplication myApplication) {
        this.mListDatas = listDatas;
        this.mActivityContext = activityContext;
        this.mMyApplication = myApplication;
    }

    // 用于创建ViewHolder 设置RecyclerView Item的布局
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_search_result, parent, false);
        return new ViewHolder(view);
    }

    // 绑定数据
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RoomTypeBean roomTypeBean = mListDatas.get(position);
        if (roomTypeBean != null) {

            // == 判断是否还有剩余客房数
            if (roomTypeBean.getMimiRemainingNum() < 1) {
                // 没有客房数，设置为灰色
                holder.mRoomNameTv.setTextColor(mActivityContext.getResources().getColor(R.color.colorGray));
            }

            // == 设置图片
            String picture = roomTypeBean.getPicture();
            if (Tools.isNotNull(picture)) {
                String[] pictures = picture.split(";");
                if (pictures.length > 0) {
                    // 使用Glide加载图片
                    String imageUrl = ServiceUrls.getReserveMethodUrl("getRoomTypePicture") + "?pictureName=" + pictures[0];
                    Glide.with(mActivityContext)
                            .load(imageUrl)
                            .into(holder.mPictureIv);
                }
            }

            // 设置roomTypeName
            holder.mRoomNameTv.setText(roomTypeBean.getRoomTypeName());

            // 设置客房信息
            StringBuilder roomInfo = new StringBuilder();
            // 房间大小
            String area = String.format(Locale.CHINESE, "%1$sm²", roomTypeBean.getAverageArea());
            roomInfo.append(area);

            // 是否有窗户
            boolean hasWindow = roomTypeBean.getWindow();
            if (hasWindow) {
                roomInfo.append(" ");
                roomInfo.append("有窗");
            }
            // 客房床信息
            roomInfo.append(" ");
            roomInfo.append(roomTypeBean.getBedType());
            // 显示文本值
            holder.mTypeInfoTv.setText(roomInfo.toString());

            // 设置标准价格 (门市价格)
            holder.mTraditionalPriceTv.setText(String.format(Locale.CHINESE, "￥%d", roomTypeBean.getRoomStandardPrice().intValue()));
            holder.mPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 设置删除线

            // 设置最小房间数
            holder.mRoomMinimumTv.setText(String.format("%s间", roomTypeBean.getMimiRemainingNum()));

            // 设置价格
            BigDecimal discount = BigDecimal.valueOf(0.98); // 默认折扣  青铜会员 0.98
            if (mMyApplication.isLogin()) {
                discount = mMyApplication.getLoginMember().getDiscount();
            }
            BigDecimal price = roomTypeBean.getRoomStandardPrice();
            price = price.multiply(discount);
            holder.mPriceTv.setText(String.valueOf(price.intValue()));

            // 设置事件
            if (mOnItemClickListener != null) {
                // 设置整条数据的点击事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view, roomTypeBean, position);
                    }
                });

                holder.mPictureIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onImageClick(view, roomTypeBean, position);
                    }
                });
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(List<RoomTypeBean> listAdd, int loadPage){
        if (loadPage == 1){
            // 如果是加载第一页， 需要先清空数据
            mListDatas.clear();
            if (listAdd != null){
                mListDatas.addAll(listAdd);
            }
            // 通知RecyclerView进行改变--整体
            notifyDataSetChanged();
        }else {
            // 不是第一页
            // 添加数据
            int nowCount = mListDatas.size();
            if (listAdd !=null){
                mListDatas.addAll(listAdd);
            }
            // 通知RecyclerView进行改变 --- 局部刷新
            notifyItemRangeChanged(nowCount, mListDatas.size());
        }
    }

    // 告诉RecyclerView有多少条数据
    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    // 获取列表项的控件
    static class ViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout mPictureFl;
        private TextView mPriceTv;
        private TextView mTypeInfoTv;
        private TextView mRoomNameTv;
        private TextView mTraditionalPriceTv;
        private TextView mRoomMinimumTv;
        private ImageView mPictureIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPictureIv = itemView.findViewById(R.id.iv_item_result_room_picture);
            mRoomMinimumTv = itemView.findViewById(R.id.tv_item_result_room_minimum);
            mPriceTv = itemView.findViewById(R.id.tv_item_result_room_price);
            mTypeInfoTv = itemView.findViewById(R.id.tv_item_result_room_type_info);
            mRoomNameTv = itemView.findViewById(R.id.tv_item_result_room_type_name);
            mTraditionalPriceTv = itemView.findViewById(R.id.tv_item_result_room_traditional_price);
            mPictureFl = itemView.findViewById(R.id.fl_item_result_picture);
        }
    }
}
