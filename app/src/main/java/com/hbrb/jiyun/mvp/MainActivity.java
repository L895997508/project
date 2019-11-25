package com.hbrb.jiyun.mvp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hbrb.jiyun.mvp.bean.Bmjf;
import com.hbrb.jiyun.mvp.bean.BmjfDetail;
import com.hbrb.jiyun.mvp.adapter.BmjfAdapter;
import com.hbrb.jiyun.mvp.utils.BaseAppCompatActivity;
import com.hbrb.jiyun.mvp.utils.NoScrollListview;
import com.hbrb.jiyun.mvp.utils.PixAndDpUtil;
import com.neusoft.gov.service.ServiceUtils;
import com.neusoft.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity implements AMapLocationListener {

    /**
     * 声明mlocationClient对象
     */
    private AMapLocationClient mLocationClient;
    /**
     * 声明mLocationOption对象
     */
    public AMapLocationClientOption mLocationOption = null;
    //创建列表list
    private List<Bmjf> bmjf = new ArrayList<>();
    //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();

    //权限请求码
    private final int mRequestCode = 100;
    private static String[] permissions_storage = {
            Manifest.permission.INTERNET,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
    };

    //默认开启权限
    public void verifyStoragePermissions(Activity activity) {
        mPermissionList.clear();//清空没有通过的权限
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions_storage.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions_storage[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions_storage[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions_storage, mRequestCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启权限
        verifyStoragePermissions(this);
        //创建父ScrollView
        ScrollView scrollView =  createScrollView();
        //创建头文件
        LinearLayout linearLayoutTitle = createLinearLayoutTitle();
        //创建图片
        ImageView imageView =  createImageView();
        //创建父LinearLayout
        LinearLayout linearLayout = createLinearLayout();
        //创建子ListView
        final NoScrollListview listView = createListView();
        linearLayout.addView(linearLayoutTitle);
        linearLayout.addView(imageView);
        linearLayout.addView(listView);
        scrollView.addView(linearLayout);
        setContentView(scrollView);
        //判断是否传值
        Intent intent = getIntent();
        final String cityCode = intent.getStringExtra("cityCode");
        final String cityName = intent.getStringExtra("cityName");
        if(cityCode == null || "".equals(cityCode)){
            //开启定位
            initLocation();
            startMapLocation();
        }else{
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    //赋值
                    @SuppressLint("ResourceType")
                    TextView textView = findViewById(1113);
                    textView.setText(cityName);
                    try {
                        initData(cityCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //添加数据
                    BmjfAdapter adapter = new BmjfAdapter(MainActivity.this,bmjf);
                    listView.setAdapter(adapter);
                }
            });
        }
        //屏幕保持高亮
//        setScreenBrightness(MainActivity.this,255);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = getIntent();
        String cityCode = intent.getStringExtra("cityCode");
        if(cityCode == null || "".equals(cityCode)){
            stopMapLocation();
            //销毁定位客户端。
            mLocationClient.onDestroy();
        }
    }

    //创建LinearLayout
    @SuppressLint("ResourceType")
    public LinearLayout createLinearLayoutTitle(){
        //创建LinearLayout布局
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(Color.rgb(29, 80, 163));
        //地理坐标
        TextView textView = new TextView(this);
        textView.setId(1113);
        textView.setPadding(PixAndDpUtil.dp2px(15,this),PixAndDpUtil.dp2px(10,this),0,0);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(PixAndDpUtil.dp2px(5,this));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent();
                in.setClassName(getApplication(),"com.hbrb.jiyun.mvp.CityActivity");
                getApplication().startActivity(in);
            }
        });
        //向下箭头
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(PixAndDpUtil.dp2px(20,this),PixAndDpUtil.dp2px(20,this));
        layoutParams2.setMargins(0,PixAndDpUtil.dp2px(12,this),0,0);
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.down));
        linearLayout.addView(textView);
        linearLayout.addView(imageView);
        return linearLayout;
    }

    //创建图片
    public ImageView createImageView(){
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,PixAndDpUtil.dp2px(250,this));
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.neusoft));
        return imageView;
    }

    //创建一个ScrollView
    public ScrollView createScrollView(){
        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParams);
        scrollView.setVerticalScrollBarEnabled(false);
        return scrollView;
    }

    //创建一个LinearLayout
    public LinearLayout createLinearLayout(){
        //创建LinearLayout布局
        LinearLayout linearLayout = new LinearLayout(this);
        //设置宽高
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //设置边距
        layoutParams.setMargins(0,0,0,0);
        //将以上属性赋值给LinearLayout
        linearLayout.setLayoutParams(layoutParams);
        //设置成垂直布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //设置背景颜色
        linearLayout.setBackgroundColor(Color.WHITE);
        return linearLayout;
    }

    //创建一个ListView
    @SuppressLint("ResourceType")
    public NoScrollListview createListView(){
        //创建LinearLayout布局
        NoScrollListview listView = new NoScrollListview(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //设置边距
        layoutParams.setMargins(0,0,0, PixAndDpUtil.dp2px(30,this));
        listView.setId(1016);
        listView.setLayoutParams(layoutParams);
        listView.setDividerHeight(0);
        listView.setDivider(Drawable.createFromPath("#ffffff"));
        listView.setEnabled(false);
        listView.setVerticalScrollBarEnabled(false);
        return listView;
    }

    /**
     * 初始化数据
     */
    private void initData(String cityCode) throws JSONException {
        String result = ServiceUtils.queryServices(cityCode, MainActivity.this);
        Log.i("=======",result);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = (JSONArray)jsonObject.get("services");
        for(int i = 0;i < jsonArray.length();i++){
            JSONObject jsonObject2 = (JSONObject)jsonArray.get(i);
            String priServiceName = (String)jsonObject2.get("priServiceName");
            //服务列表集合
            JSONArray jsonArray2 = (JSONArray)jsonObject2.get("secServices");
            ArrayList<BmjfDetail> bmjfDetailList = new ArrayList<BmjfDetail>();
            for(int j = 0;j < jsonArray2.length();j++){
                JSONObject jsonObject3 = (JSONObject)jsonArray2.get(j);
                String serviceType = (String)jsonObject3.get("serviceType");
                String secServiceName = (String)jsonObject3.get("secServiceName");
                String secServiceCode = (String)jsonObject3.get("secServiceCode");
                String serviceUrl = (String)jsonObject3.get("serviceUrl");
                String iconUrl = (String)jsonObject3.get("iconUrl");
                BmjfDetail bmjfDetail = new BmjfDetail(serviceType,secServiceName,secServiceCode,serviceUrl,iconUrl);
                bmjfDetailList.add(bmjfDetail);
            }
            bmjf.add(new Bmjf(priServiceName,bmjfDetailList));
        }
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
                @SuppressLint("ResourceType")
                TextView textView = findViewById(1113);
//                SpannableString cityString = new SpannableString(aMapLocation.getCity());
                SpannableString cityString = new SpannableString("石家庄市");
                StyleSpan span = new StyleSpan(Typeface.NORMAL);
                cityString.setSpan(span, 0, cityString.length(), Spannable.SPAN_COMPOSING);
                textView.setText(cityString);
                //获取cityCode
//                String cityCode = aMapLocation.getAdCode();
                String cityCode = "130100";
                try {
                    initData(cityCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取当前View
                @SuppressLint("ResourceType")
                NoScrollListview listView = findViewById(1016);
                //添加数据
                BmjfAdapter adapter = new BmjfAdapter(MainActivity.this,bmjf);
                listView.setAdapter(adapter);
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

    public void setScreenBrightness(Activity activity, int value) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.screenBrightness = value / 255f;
        activity.getWindow().setAttributes(params);
    }
}
