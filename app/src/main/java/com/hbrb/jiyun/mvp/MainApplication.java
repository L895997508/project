package com.hbrb.jiyun.mvp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ccb.ccbnetpay.platform.CCBWXPayAPI;
import com.facebook.soloader.SoLoader;
import com.neusoft.gov.utils.BusinessConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

public class MainApplication extends Application{

  public static SharedPreferences sharedPreferences = null;

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native 111exopackage22222 */ false);

    /**
     * 注册APPID
     * context 应用上下文
     * appId 微信平台审核通过的appId
     */
//    CCBWXPayAPI.getInstance().init(this, BusinessConfig.APP_ID);
//    sharedPreferences = this.getSharedPreferences(BusinessConfig.SHARED_NAME,Context.MODE_PRIVATE);

    // 初始化Umeng分享
    UMConfigure.init(this,"5d8add49570df301b000093c","umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
  }

  // 配置平台key、secret信息
  {
    PlatformConfig.setWeixin("wx5df9bde0e04ec4ed", "e88d487eb2c1f9d13203cee782481e16");
    PlatformConfig.setQQZone("1109939412", "lg8Dv5z1fmfjNY9O");
    PlatformConfig.setSinaWeibo("543472512", "b1f0ef887a5774f3991cf0080dfe83d2", "https://api.weibo.com/oauth2/default.html");
  }
}
