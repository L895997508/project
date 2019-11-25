package com.hbrb.jiyun.mvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hbrb.jiyun.mvp.adapter.BmjfAdapter;
import com.hbrb.jiyun.mvp.adapter.CityAdapter;
import com.hbrb.jiyun.mvp.bean.City;
import com.hbrb.jiyun.mvp.utils.BaseAppCompatActivity;
import com.hbrb.jiyun.mvp.utils.NoScrollListview;
import com.hbrb.jiyun.mvp.utils.PixAndDpUtil;
import com.neusoft.myapplication.R;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class CityActivity extends BaseAppCompatActivity implements AMapLocationListener {

    /**
     * 声明mlocationClient对象
     */
    private AMapLocationClient mLocationClient;
    /**
     * 声明mLocationOption对象
     */
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = createScrollView();
        LinearLayout linearLayout = createLinearLayout();
        LinearLayout linearLayoutTitle = createLinearLayoutTitle();
        TextView textView = createTextView();
        NoScrollListview  noScrollListview = createListView();
        linearLayout.addView(linearLayoutTitle);
        linearLayout.addView(textView);
        linearLayout.addView(noScrollListview);
        scrollView.addView(linearLayout);
        setContentView(scrollView);
        final ArrayList<City> list = getCityData();
        //添加数据
        CityAdapter adapter = new CityAdapter(CityActivity.this,list);
        noScrollListview.setAdapter(adapter);
        noScrollListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = list.get(position);
                String cityCode = city.getCityCode();
                String cityName = city.getCityName();
                Intent intent = new Intent();
                intent.putExtra("cityCode", cityCode);
                intent.putExtra("cityName", cityName);
                intent.setClass(CityActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //开启定位
        initLocation();
        startMapLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMapLocation();
        //销毁定位客户端。
        mLocationClient.onDestroy();
    }

    /**
     * 初始化定位参数
     */
    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        // 设置定位监听
        mLocationClient.setLocationListener(this);
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    /**
     * 开启定位
     */
    public void startMapLocation() {
        //判断是否开启了，没有开启就开启
        if (!mLocationClient.isStarted()) {
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位服务
     */
    public void stopMapLocation() {
        //判断服务是否开启了，若开启了则停止
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    /**
     * 持续定位
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 可在其中解析amapLocation获取相应内容。
                final String cityCode = aMapLocation.getAdCode();
                final String cityName = aMapLocation.getCity();
                @SuppressLint("ResourceType")
                TextView textView = findViewById(0214);
                textView.setText(cityName);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("cityCode", cityCode);
                        intent.putExtra("cityName", cityName);
                        intent.setClass(CityActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                stopMapLocation();
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                stopMapLocation();
            }
        } else {
            stopMapLocation();
        }
    }

    //创建一个ScrollView
    public ScrollView createScrollView(){
        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParams);
        scrollView.setVerticalScrollBarEnabled(false);
        return scrollView;
    }

    //创建分割线
    public TextView createTextView(){
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(PixAndDpUtil.dp2px(15,this),PixAndDpUtil.dp2px(4,this),0,PixAndDpUtil.dp2px(4,this));
        textView.setBackgroundColor(rgb(240,240,240));
        textView.setText("全部城市");
        textView.setTextSize(12);
        return textView;
    }

    //创建LinearLayout
    @SuppressLint("ResourceType")
    public LinearLayout createLinearLayoutTitle(){
        //创建LinearLayout布局
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,PixAndDpUtil.dp2px(50,this));
        textView.setLayoutParams(params);    //设置布局参数
        textView.setGravity(Gravity.CENTER);
        SpannableString cityString = new SpannableString("选择城市");
        StyleSpan span = new StyleSpan(Typeface.NORMAL);
        cityString.setSpan(span, 0, cityString.length(), Spannable.SPAN_COMPOSING);
        textView.setText(cityString);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(PixAndDpUtil.dp2px(5,this));

        TextView textView2 = new TextView(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
        textView2.setLayoutParams(params2);
        textView2.setBackgroundColor(rgb(200,200,200));


        LinearLayout linearLayout2 = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout2.setLayoutParams(layoutParams3);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        //GPS图标
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(PixAndDpUtil.dp2px(25,this),PixAndDpUtil.dp2px(25,this));
        layoutParams2.setMargins(PixAndDpUtil.dp2px(15,this),PixAndDpUtil.dp2px(15,this),0,0);
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.gps));
        TextView textView3 = new TextView(this);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.setMargins(PixAndDpUtil.dp2px(5,this),PixAndDpUtil.dp2px(15,this),0,PixAndDpUtil.dp2px(15,this));
        textView3.setLayoutParams(params3);    //设置布局参数
        SpannableString cityString2 = new SpannableString("");
        StyleSpan span2 = new StyleSpan(Typeface.BOLD);
        cityString2.setSpan(span2, 0, cityString2.length(), Spannable.SPAN_COMPOSING);
        textView3.setId(0214);
        textView3.setText(cityString2);
        textView3.setTextColor(Color.BLACK);
        textView3.setTextSize(PixAndDpUtil.dp2px(5,this));
        linearLayout2.addView(imageView);
        linearLayout2.addView(textView3);

        linearLayout.addView(textView);
        linearLayout.addView(textView2);
        linearLayout.addView(linearLayout2);
        return linearLayout;
    }

    //创建一个LinearLayout
    public LinearLayout createLinearLayout(){
        //创建LinearLayout布局
        LinearLayout linearLayout = new LinearLayout(this);
        //设置成垂直布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setFocusable(true);
        linearLayout.setFocusableInTouchMode(true);
        return linearLayout;
    }

    //创建一个ListView
    public NoScrollListview createListView(){
        //创建LinearLayout布局
        NoScrollListview listView = new NoScrollListview(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(PixAndDpUtil.dp2px(15,this),0,PixAndDpUtil.dp2px(15,this),0);
        listView.setLayoutParams(layoutParams);
        listView.setDividerHeight(1);
        listView.setVerticalScrollBarEnabled(false);
        return listView;
    }

    public ArrayList<City> getCityData(){
        City city = new City("130000","河北省");
        City city1 = new City("130100","石家庄市");
        City city2 = new City("130200","唐山市");
        City city3 = new City("130300","秦皇岛市");
        City city4 = new City("130400","邯郸市");
        City city5 = new City("130500","邢台市");
        City city6 = new City("130600","保定市");
        City city7 = new City("130700","张家口市");
        City city8 = new City("130800","承德市");
        City city9 = new City("130900","沧州市");
        City city10 = new City("131000","廊坊市");
        City city11 = new City("131100","衡水市");
        ArrayList<City> list = new  ArrayList<City>();
        list.add(city);
        list.add(city1);
        list.add(city2);
        list.add(city3);
        list.add(city4);
        list.add(city5);
        list.add(city6);
        list.add(city7);
        list.add(city8);
        list.add(city9);
        list.add(city10);
        list.add(city11);
        return list;
    }

}
