package com.hbrb.jiyun.mvp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbrb.jiyun.mvp.bean.Bmjf;
import com.hbrb.jiyun.mvp.utils.NoScrollGridView;
import com.hbrb.jiyun.mvp.utils.PixAndDpUtil;
import com.neusoft.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BmjfAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bmjf> listdatas;

    public BmjfAdapter(Context mContext,List<Bmjf> listdatas) {
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
        Bmjf bmjf = (Bmjf)getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(mContext).inflate(R.layout.bmfw_info, parent, false);
        TextView textView = view.findViewById(R.id.name);
        textView.setText("  ");
        textView.setIncludeFontPadding(false);
        textView.setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(PixAndDpUtil.dp2px(12,mContext),0,0,PixAndDpUtil.dp2px(20,mContext));
        textView.setLayoutParams(params);

        TextView textView2 = view.findViewById(R.id.name2);
        SpannableString spanString = new SpannableString(bmjf.getPriServiceName());
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("black"));
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_COMPOSING);
        spanString.setSpan(foregroundColorSpan, 0, spanString.length(), Spannable.SPAN_COMPOSING);
        textView2.setText(spanString);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(PixAndDpUtil.dp2px(5,mContext),0,0,PixAndDpUtil.dp2px(10,mContext));
        textView2.setLayoutParams(params2);


        NoScrollGridView gridView = view.findViewById(R.id.gridView);
        ArrayList bmjfDetail = bmjf.getBmjfDetailList();
        BmjfDetailAdapter adapter = new BmjfDetailAdapter(mContext,bmjfDetail);
        gridView.setAdapter(adapter);
        return view;
    }
}
