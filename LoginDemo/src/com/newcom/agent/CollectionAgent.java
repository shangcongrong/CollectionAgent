package com.newcom.agent;

import java.security.Provider;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.newcom.http.BaseHttp;
import com.newcom.statistics.baseproperty.DeviceUtil;
import com.newcom.statistics.channel.ChannelUtil;
import com.newcom.statistics.common.CommonUtil;
import com.newcom.statistics.common.Constants;
import com.newcom.statistics.activity.ActivityNumUtil;
import com.newcom.statistics.activitypath.ActivityPathUtil;

public class CollectionAgent extends BaseHttp {

	
	public CollectionAgent() {
		super();
	}
	/**
	 * 收集各种基本信息,包括设备基本信息,下载渠道信息,所有activity名(此方法只在主页面添加运行)
	 * @param context
	 */
	public static void onMain(final Context context){
		CommonUtil.testProcess(context);
		new Thread(){
			@Override
			public void run() {
				CollFunction.firstStartDate=CommonUtil.firstStartDate();//收集第一次启动的日期信息
				CollFunction.firstStartTime=CommonUtil.firstStartTime();//收集第一次启动的时间信息
				/*
				CollFunction.activityFirstStartDate=CommonUtil.intoActivityDate();//刚进入某activity页面的时间
				CollFunction.nowActivityName=CollFunction.getRunningActivityName(context);//刚进入的activity页面名
				*/
				System.out.println("---------------"+CommonUtil.checkFirstStart(context));
				if(CommonUtil.checkFirstStart(context)){//判断是不是第一次启动应用
					CommonUtil.savaStartFlag(context);
					CollFunction.collBasePropertyInfo(context);//收集手机基本信息
					CollFunction.collchannelInfo(context);	//收集应用下载渠道信息
					CollFunction.collactivityInfo(context);//收集应用中所有activity页面名 
				}else {
					CollFunction.collactivitypathInfo(context);//若不是第一次启动，怎传输上一次页面跳转的信息
				}
			}
		}.start();
	}
	/**
	 * 收集每个activity页面的信息,在每个activity页面添加
	 * @param context
	 */
	public static void onCreate(final Context context){
		new Thread(){
			@Override
			public void run() {
				CollFunction.activityFirstStartDate=CommonUtil.intoActivityDate();//刚进入某activity页面的时间
				CollFunction.nowActivityName=CollFunction.getRunningActivityName(context);//刚进入的activity页面名
			}
		}.start();
	}
	/**
	 * 统计整个应用运行时间,目前还存在问题,统计不到推退出时间???????
	 * @param context
	 */
	public static void onDestroy(final Context context){
		new Thread(){

			@Override
			public void run() {				
				CollFunction.colldurationInfo(context);//收集整个应用使用时长信息
			}
		}.start();
	}
	/**
	 * 页面跳转的时候统计当前页面的信息,每个页面添加
	 * @param context
	 */
	public static void onPause(final Context context){
		new Thread(){
			@Override
			public void run() {				
				CollFunction.getactivitypathInfo(context);//收集当前activity页面信息
			}
		}.start();
	}
	/**
	 * 收集自定义事件信息,只在事件相应内部添加
	 * @param eventNum 事件编号
	 * @param context
	 */
	public static void onEvent(final int eventNum,final Context context){
		new Thread(){
			@Override
			public void run() {
				CollFunction.colleventInfo(eventNum, context);//收集自定义事件信息
			}
		}.start();
	}
}
