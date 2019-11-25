package com.hbrb.jiyun.mvp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hbrb.jiyun.mvp.bean.City;
import com.hbrb.jiyun.mvp.utils.PixAndDpUtil;
import com.neusoft.myapplication.R;

import java.util.List;

public class CityAdapter extends BaseAdapter {

    private Context mContext;
    private List<City> listdatas;

    public CityAdapter(Context mContext, List<City> listdatas) {
        super();
        this.mContext = mContext;
        this.listdatas = listdatas;
    }

    @Override
    public int getCount() {
        return listdatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = (City)getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(mContext).inflate(R.layout.city, parent, false);
        TextView textView = view.findViewById(R.id.content_tv);
        textView.setPadding(0,PixAndDpUtil.dp2px(20,mContext),0,PixAndDpUtil.dp2px(20,mContext));
        SpannableString cityString = new SpannableString(city.getCityName());
        StyleSpan span = new StyleSpan(Typeface.NORMAL);
        cityString.setSpan(span, 0, cityString.length(), Spannable.SPAN_COMPOSING);
        textView.setText(cityString);
        textView.setTextSize(PixAndDpUtil.dp2px(4,mContext));
        return view;
    }

}
