package com.newcom.statistics.common;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import com.newcom.agent.CollFunction;
import com.newcom.statistics.activitypath.ActivityPathUtil;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 公共工具类
 * @author zhaohonglin
 *
 */
public class CommonUtil {
	static boolean flag=true;
	/**
	 * 判断应用程序什么时候退出
	 * @param paramContext
	 * @return 返回true则程序正在运行,返回false则当前应用停止运行
	 */

	public  static boolean judgeApp(Context paramContext)
	{
		ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");

		List<?> localList = localActivityManager.getRunningAppProcesses();
		if (localList == null)
			return false;

		String str = paramContext.getPackageName();
		for (Iterator<?> localIterator = localList.iterator(); localIterator.hasNext(); ) {
			ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
			if ((localRunningAppProcessInfo.importance == 100) && (localRunningAppProcessInfo.processName.equals(str)))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * 不断检测应用程序什么时候结束,一旦应用程序包结束则保存,自定义事件,页面访问路径,访问时长等信息
	 * @param paramContext
	 */
	public static void testProcess(final Context paramContext){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(flag){
					//System.out.println("-----testProcess-----");
					if(!CommonUtil.judgeApp(paramContext)){

						//ActivityPathUtil.saveActivityPathDataToJSON(paramContext);
						//EventUtil.saveEventDataToJSON(paramContext);
						//AppDurationUtil.saveAppDurationData(paramContext);
						//System.out.println("endProcess");
						CollFunction.colldurationInfo(paramContext);
						/**
						 * 测试用例
						 */
						/*ChannelUtil.getChannelData(paramContext);
						DeviceUtil.getDeviceData(paramContext);
						InternetUtil.getInternetData(paramContext);*/

						flag=false;
					}
				}
			}
		}).start();
	}
//	/**
//	 * 不断检测网络是否中断,一旦网络中断则保存自定义事件,页面访问路径,访问时长等信息
//	 * @param paramContext
//	 */
//	public static void testNet(final Context paramContext){
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(flag){
//					if(!CommonUtil.checkNet(paramContext)){
//
//						ActivityPathUtil.saveActivityPathDataToJSON(paramContext);
//						EventUtil.saveEventDataToJSON(paramContext);
//						AppDurationUtil.saveAppDurationData(paramContext);
//
//
//						/**
//						 * 测试用例
//						 */
//						ChannelUtil.getChannelData(paramContext);
//						DeviceUtil.getDeviceData(paramContext);
//						InternetUtil.getInternetData(paramContext);
//
//						flag=false;
//					}
//				}
//			}
//		}).start();
//	}
	/**
	 * 获取应用的主Activity
	 * @param paramContext
	 * @return
	 */
	public static String getMainActivity(Context paramContext){
		String classname = null;
		Intent intent = new Intent(Intent.ACTION_MAIN, null); 
		intent.addCategory(Intent.CATEGORY_LAUNCHER);  
		List<ResolveInfo> list =paramContext.getPackageManager().
				queryIntentActivities(intent,   PackageManager.GET_ACTIVITIES);
		for (int i = 0; i < list.size(); i++){
			String  packageName=list.get(i).activityInfo.packageName;
			if(packageName.equals(paramContext.getPackageName())){
				classname=list.get(i).activityInfo.name;
			}
		}
		return classname;
	}
	/**
	 * 获取刚进入应用时的时间
	 * @return 返回当前的时间
	 */
	public static Date firstStartDate(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date firststartDate = new Date(System.currentTimeMillis());//获取当前时间     
		return firststartDate;
	}
	/**
	 * 获取刚进入应用时的启动时间
	 * @return 返回当前的时间
	 */
	public static String firstStartTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date firststartDate = new Date(System.currentTimeMillis());//获取当前时间    
		String firststartTime = formatter.format(firststartDate);
		return firststartTime;
	}
	/**
	 * 获取页面启动时间，事件发生时间等当前时间
	 * @return 返回当前的时间
	 */
	public static String getNowTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date nowDate = new Date(System.currentTimeMillis());//获取当前时间    
		String nowTime = formatter.format(nowDate);
		return nowTime;
	}
	/**
	 * 获取刚进入某activity时的时间，为了统计页面访问时间
	 * @return 返回当前的时间
	 */
	public static Date intoActivityDate(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDate = new Date(System.currentTimeMillis());//获取当前时间     
		return startDate;
	}

	/**
	 * 获取格式化后的时间
	 * @param timeMillis
	 * @return
	 */
	public static String getFormatTime(long timeMillis)
	{
		Date localDate = new Date(timeMillis);
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime=localSimpleDateFormat.format(localDate);
		return currentTime;
	}
	/**
	 * 获取格式化后的时间
	 * @param timeMillis
	 * @return
	 */
/*	public static String getFormatTime()
	{
		Date localDate = new Date();
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime=localSimpleDateFormat.format(localDate);
		return currentTime;
	}*/
	/**
	 * 保存应用启动的时间
	 * @param paramContext
	 */
	public static void saveStartTime(Context paramContext){
		SharedPreferences time=paramContext.getSharedPreferences("mobclick_agent_" + paramContext.getPackageName(), 0);
		Editor editor=time.edit().putLong("start_millis",System.currentTimeMillis());
		editor.commit();
	}
	/**
	 * 判断是不是第一次启动
	 * @param paramContext
	 */
	public static boolean checkFirstStart(Context paramContext){
		SharedPreferences time=paramContext.getSharedPreferences("collection_agent_" + paramContext.getPackageName(), 0);
		int flag=time.getInt("flag",0);
		if(flag==0){
			return true;
		}
		return false;
	}	
	/**
	 * 每次启动调用一次
	 * @param paramContext
	 */
	public static void savaStartFlag(Context paramContext){
		SharedPreferences time=paramContext.getSharedPreferences("collection_agent_" + paramContext.getPackageName(), 0);
		int flag=time.getInt("flag",0);
		Editor editor=time.edit().putInt("flag",(flag+1));
		editor.commit();
	}	
	/**
	 * 检查网络状况
	 * @param paramContext
	 * @return 
	 */
	public static boolean checkNet(Context paramContext)
	{  
		ConnectivityManager manager = (ConnectivityManager) paramContext.getSystemService("connectivity");    
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();    
		if (networkinfo == null || !networkinfo.isAvailable()) {    
			return false;  
		}  
		return true;  
	}
}
