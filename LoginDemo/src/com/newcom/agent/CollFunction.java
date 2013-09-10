package com.newcom.agent;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.newcom.http.BaseHttp;
import com.newcom.statistics.activity.ActivityNumUtil;
import com.newcom.statistics.activitypath.ActivityPathUtil;
import com.newcom.statistics.baseproperty.DeviceUtil;
import com.newcom.statistics.channel.ChannelUtil;
import com.newcom.statistics.common.CommonUtil;
import com.newcom.statistics.common.Constants;

public class CollFunction extends BaseHttp{
	static Date firstStartDate;//获取启动时日期时间
	static String firstStartTime;//应用启动时的时间
	static Date activityFirstStartDate;//某个activity启动时间
	static String nowActivityName;//当前所运行的activity页面名
	public CollFunction() {
		super();
	}
	/**
	 * 收集基本手机信息
	 * @param deviceId 设备编号
	 * @param devModel 设备型号
	 * @param devType 终端类型
	 * @param operator 设备分辨率
	 * @param osVersion 系统版本
	 * @param resolution 网络运营商
	 * @return
	 */
	public static boolean collBasePropertyInfo(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		Build bd = new Build();  
		String devModel = bd.MODEL;
		String deviceId=DeviceUtil.getDeviceNum(context);
		int operator=tm.getNetworkType();
		int devType=0;
		int osVersion=android.os.Build.VERSION_CODES.BASE;
		String resolution=DeviceUtil.getResolution(context);
		try{
			JSONObject jsonObject = new JSONObject();  
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("devModel", devModel);  
            jsonObject.put("devType", devType); 
            jsonObject.put("operator", operator); 
            jsonObject.put("osVersion", osVersion); 
            jsonObject.put("resolution", resolution); 
          
            addNameValuePair("basepropertyinfo_jsonString", jsonObject.toString());
            System.out.println("basepropertyinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.BASEPROPERTYSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 收集网络及位置信息
	 * @param deviceId 设备编号
	 * @param connectMode 联网方式
	 * @param startTime 启动时间
	 * @param longitude 经度
	 * @param latitude 维度
	 * @return
	 */
	public static boolean collInternetInfo(Context context){
		
		String deviceId=DeviceUtil.getDeviceNum(context);
		String startTime =  CommonUtil.getNowTime();
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		String connectMode = info.getTypeName();
		
		
		//得到系统服务的LocationManager对象
		LocationManager locationManager;
		String contextService=Context.LOCATION_SERVICE;
		//通过系统服务，取得LocationManager对象
		locationManager=(LocationManager) context.getSystemService(contextService);
		//使用标准集合，让系统自动选择可用的最佳位置提供器，提供位置
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
		criteria.setAltitudeRequired(false);//不要求海拔
		criteria.setBearingRequired(false);//不要求方位
		criteria.setCostAllowed(true);//允许有花费
		criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗
		//从可用的位置提供器中，匹配以上标准的最佳提供器
		String provider = locationManager.getBestProvider(criteria, true);
		//获得最后一次变化的位置
		Location location = locationManager.getLastKnownLocation(provider);
		
        String longitude=location.getLongitude()+"";
        String latitude=location.getLatitude()+"";
        location.getLatitude();
        
        //loctionManager.requestLocationUpdates(provider, 2000, 10, locationListener);
        
        
        
        
        
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("connectMode", connectMode);  
            jsonObject.put("startTime", startTime);
            jsonObject.put("longitude", longitude); 
            jsonObject.put("latitude", latitude); 
          
            addNameValuePair("internetinfo_jsonString", jsonObject.toString());
            System.out.println("internetinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.INTERNETSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 收集下载渠道信息
	 * @param deviceId 设备编号
	 * @param downloadChannel 下载渠道
	 * @param appVersion 软件版本
	 * @param lastChannel 上次渠道
	 * @param update 更新时间
	 * @return
	 */
	public static boolean collchannelInfo(Context context){
		
		String deviceId=DeviceUtil.getDeviceNum(context);
		String downloadChannel=ChannelUtil.getDownloadChannel(context);
		String appVersion=ChannelUtil.getAppVersion(context); 
		String upTime =   CommonUtil.getNowTime();
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("downloadChannel", downloadChannel);
			jsonObject.put("appVersion", appVersion);  
			jsonObject.put("upTime", upTime);
          
            addNameValuePair("channelinfo_jsonString", jsonObject.toString());
            System.out.println("channelinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.CHANNELSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 收集自定义事件信息
	 * @param deviceId 设备编号
	 * @param eventNum 自定义事件编号
	 * @param eventTime 事件发生时间
	 * @param appVersion 软件版本
	 * @param inTime 入库时间
	 * @return
	 */
	public static boolean colleventInfo(int eventNum,Context context){
		String deviceId=DeviceUtil.getDeviceNum(context);
		String eventTime=  CommonUtil.getNowTime();
		String appVersion=ChannelUtil.getAppVersion(context);
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("eventNum", eventNum);
			jsonObject.put("appVersion", appVersion);  
			jsonObject.put("eventTime", eventTime);
          
            addNameValuePair("eventinfo_jsonString", jsonObject.toString());
            System.out.println("eventinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.EVENTSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 获取页面编号
	 * @param activity activity全类名
	 * @return
	 */
	public static void collactivityInfo(Context context){
		String activityName = null;
		/*InputStream inputStream=context.getClass().getClassLoader().getResourceAsStream("aaa.xml");
		System.out.println("inputstream1:"+inputStream);
		DomgetresponseXml domXml= new DomgetresponseXml();*/
		try {
			//System.out.println("inputstream2:"+inputStream);
			//activityNameList = domXml.getActivitynames(inputStream);
			List<String> activityNameList=ActivityNumUtil.getAllActivity(context);
			JSONArray jsonArray = new JSONArray();
		for(int i = 0;i < activityNameList.size();i++){
			activityName=activityNameList.get(i);
			System.out.println("name:"+activityName);
			JSONObject temp  = new JSONObject();
			temp.put("activityname", activityName);
			jsonArray.put(i,temp);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",jsonArray);
			

            addNameValuePair("activityinfo_jsonString", jsonObject.toString());
            System.out.println("activityinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.GETACTIVITYNUMSERVLET); 
			getresponse();
			JSONObject object =BaseHttp.getJson();
			String activity=object.getString("activity");
			System.out.println("activity:"+activity);
			ActivityNumUtil.saveActivityNumDataToFile(context,activity);//保存activity的json到缓存文件中
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 获取当前运行activity名
	 * @param context
	 * @return
	 */
	public static String getRunningActivityName(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
        RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String runningActivityName = info.topActivity.getClassName();
        System.out.println("runningActivityName:"+runningActivityName);//当前activity名
        return runningActivityName;
	}
	/**
	 * 获取到当前页面的页面信息
	 * @param context
	 */
	public static void getactivitypathInfo(Context context){
		
        String startTime =   CommonUtil.getNowTime();//入库时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date endDate = new Date(System.currentTimeMillis());//获取当前时间   
		long activityDuration=endDate.getTime()-activityFirstStartDate.getTime();//使用时长
		int nowActivityNum=ActivityNumUtil.getActivityNumByActivity(context,nowActivityName);
		String appVersion=ChannelUtil.getAppVersion(context); 
		//System.out.println("nowActivityNum:"+nowActivityNum);
		ActivityPathUtil.saveActivityPathData(context, nowActivityNum, activityDuration,startTime);
		ActivityPathUtil.saveActivityPathDataToJSON(context);
	}
	/**
	 * 发送页面访问路径信息
	 * @param deviceId 设备编号
	 * @param activityDuration 页面访问时长
	 * @param startTime 启动时间
	 * @param lastActivityNum 上一个页面编号
	 * @param nowActivityNum 当前页面编号
	 * @param nextActivityNum 下一个页面编号
	 * @return
	 */
	public static boolean collactivitypathInfo(Context context){
		String activityPathString=ActivityPathUtil.getActivityPathDataFromFile(context);
		System.out.println("activityPathString:"+activityPathString);
		try{
			//net.sf.json.JSONObject activityPathjsonObject =net.sf.json.JSONObject.fromObject(activityPathString);
			//net.sf.json.JSONArray resJsonArray=new net.sf.json.JSONArray();
			//JSONObject jsonObject = new JSONObject();
			/*jsonObject.put("deviceId", deviceId);
			jsonObject.put("duration", activityDuration);
			jsonObject.put("startTime", startTime);
			jsonObject.put("nowActivityNum", nowActivityNum);*/
			//jsonObject.put("nowActivityNum", nowActivityNum); 
			//jsonObject.put("nextActivityNum", nextActivityNum);
            addNameValuePair("activitypathinfo_jsonString", activityPathString);
            System.out.println("activitypathinfo_jsonString:"+activityPathString);
			sendrequest(Constants.ACTIVITYPATHSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 收集应用使用时长信息
	 * @param deviceId 
	 * @param startTime 
	 * @param duration 
	 * @return
	 */
	public static boolean colldurationInfo(Context context){
		String deviceId=DeviceUtil.getDeviceNum(context);	//设备编号
		String startTime =   CommonUtil.getNowTime();	//启动时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date endDate = new Date(System.currentTimeMillis()); 
		long duration=endDate.getTime()-firstStartDate.getTime();	//使用时长
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("startTime", startTime);
			jsonObject.put("duration", duration);

            addNameValuePair("durationinfo_jsonString", jsonObject.toString());
            System.out.println("durationinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.DURATIONSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){//返回错误码
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
