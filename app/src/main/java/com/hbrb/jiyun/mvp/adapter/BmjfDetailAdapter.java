package com.hbrb.jiyun.mvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbrb.jiyun.mvp.bean.BmjfDetail;
import com.hbrb.jiyun.mvp.utils.IpAddressUtils;
import com.hbrb.jiyun.mvp.utils.MyImageView;
import com.hbrb.jiyun.mvp.utils.PixAndDpUtil;
import com.neusoft.myapplication.R;

import java.util.ArrayList;

public class BmjfDetailAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;
    private ArrayList<BmjfDetail> mList;

    public BmjfDetailAdapter(Context mContext, ArrayList<BmjfDetail> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mList == null)
            return null;
        return this.mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        final BmjfDetail bmjfDetail = (BmjfDetail)getItem(position);//获取当前项的实例
        if(convertView==null){
            convertView=inflater.inflate(R.layout.bmjf_grid,null);
            convertView.setPadding(0,0,0, PixAndDpUtil.dp2px(15,mContext));
            holder=new Holder();
            holder.item_img=convertView.findViewById(R.id.item_img);
            holder.item_tex=convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }
        SpannableString spanString = new SpannableString(bmjfDetail.getSecServiceName());
        StyleSpan span = new StyleSpan(Typeface.NORMAL);
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_COMPOSING);
        holder.item_tex.setText(spanString);
        holder.item_tex.setTextSize(PixAndDpUtil.dp2px(4,mContext));
        holder.item_tex.setSingleLine();
        holder.item_img.setImageURL(bmjfDetail.getIconUrl());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PixAndDpUtil.dp2px(25,mContext),
                PixAndDpUtil.dp2px(25,mContext));
        params.setMargins(0,0,0,30);
        holder.item_img.setLayoutParams(params);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent();
                in.setClassName(mContext,"com.neusoft.gov.NeuMainActivity");
                String userId = "0002";
                String userName = "孙晓东";
//                String idCard = "210103198911135433";
                String idCard = null;
                String sex = "男";
                String phoneNumber = "15804017291";
                String cityCode = "130100";
                String secServiceName = bmjfDetail.getSecServiceName();
                String secServiceCode = bmjfDetail.getSecServiceCode();
                String serviceType = bmjfDetail.getServiceType();
                String serviceUrl = bmjfDetail.getServiceUrl();
                String userType = "1";
                String wechatNumber = "546451253";
                String mail = "546451253@qq.com";
                String deviceId = "19283891918283818192838289121212121212";
//                String ipAddress = "fe80::1078:15e6:2a1:ab45%9";
//                String ipAddress = IpAddressUtils.getPsdnIp4();
                String ipAddress = IpAddressUtils.getPsdnIp6();

                //直接以键值对的方式把数据存入Intent
                in.putExtra("userId", userId);
                in.putExtra("userName", userName);
                in.putExtra("idCard", idCard);
                in.putExtra("sex", sex);
                in.putExtra("phoneNumber", phoneNumber);
                in.putExtra("cityCode", cityCode);
                in.putExtra("secServiceCode", secServiceCode);
                in.putExtra("userType", userType);
                in.putExtra("wechatNumber", wechatNumber);
                in.putExtra("mail", mail);
                in.putExtra("secServiceName", secServiceName);
                in.putExtra("serviceType", serviceType);
                in.putExtra("serviceUrl", serviceUrl);
                in.putExtra("deviceId", deviceId);
                in.putExtra("ipAddress", ipAddress);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(in);
            }
        });
        return convertView;
    }

    private class Holder{
        MyImageView item_img;
        TextView item_tex;

        public MyImageView getItem_img() {
            return item_img;
        }
        public void setItem_img(MyImageView item_img) {
            this.item_img = item_img;
        }
        public TextView getItem_tex() {
            return item_tex;
        }
        public void setItem_tex(TextView item_tex) {
            this.item_tex = item_tex;
        }
    }

}
