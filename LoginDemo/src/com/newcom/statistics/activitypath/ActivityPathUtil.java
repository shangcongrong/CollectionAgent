package com.newcom.statistics.activitypath;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.newcom.statistics.channel.ChannelUtil;
import com.newcom.statistics.common.CommonUtil;
/**
 * 页面访问路径工具类
 * @author zhaohonglin
 *
 */
public class ActivityPathUtil {
	protected static final String a = ActivityPathUtil.class.getName();
	public static List<ActivityPathData> activityPathDatas=new ArrayList<ActivityPathData>();

	/**
	 * 将自定义事件数据保存到集合
	 * @param paramContext
	 * @return
	 */
	public static void saveActivityPathData(Context paramContext,int activityNum,long activityDuration,String startTime) {
		ActivityPathData activityPathData=new ActivityPathData();
		activityPathData.setAppVersion(ChannelUtil.getAppVersion(paramContext));
		activityPathData.setActivityNum(activityNum);
		SharedPreferences time=paramContext.getSharedPreferences("mobclick_agent_" + paramContext.getPackageName(), 0);
		activityPathData.setStartTime(startTime);
		activityPathData.setActivityDuration(activityDuration);
		activityPathDatas.add(activityPathData);
		Log.i("CollectionAgent", "saveActivityPathData succeed");
	}

	public static void saveActivityPathDataToJSON(Context paramContext ) {
		Gson gson = new Gson();
		String json=gson.toJson(activityPathDatas);
		saveActivityPathDataToFile(paramContext,json);
		Log.i("CollectionAgent", json);
	}


	/**
	 * 将页面访问路径数据保存到缓存文件里
	 * @param paramJSONObject
	 */
	public static void saveActivityPathDataToFile(Context paramContext,String paramJSON) {

		FileOutputStream localFileOutputStream = null;
		//	    paramJSONObject.put("cache_version", paramString);
		try {
			localFileOutputStream = paramContext.openFileOutput("activitypath.txt",0);//32768
			localFileOutputStream.write(paramJSON.getBytes());
			localFileOutputStream.flush();
		} catch (Exception e) {
			Log.i(a, e.toString());
		}finally{
			if(!(null==localFileOutputStream)){
				try {
					localFileOutputStream.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		} 
		Log.i("CollectionAgent", "saveActivityPathDataToFile succeed");
	}
	/**
	 * 从缓存文件里获取自定义事件数据
	 * @param paramContext
	 * @return
	 */
	public static String getActivityPathDataFromFile(Context paramContext){
		String msg="";
		FileInputStream inStream = null;
		ByteArrayOutputStream stream = null;
		try {       
			inStream=paramContext.openFileInput("activitypath.txt");    
			stream=new ByteArrayOutputStream();      
			byte[] buffer=new byte[1024];       
			int length=-1;      
			while((length=inStream.read(buffer))!=-1)   {     
				stream.write(buffer,0,length); 
			}    
			if(!"".equals(stream)){
				msg=stream.toString();
			}	

		} catch (Exception e) {      
			e.printStackTrace();    
		}finally{
			if(stream!=null){
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
			if(inStream!=null){
				try {
					inStream.close();
					deleteFile(paramContext);
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}

		}    
		return msg;
	}
	/**
	 * 删除缓存文件
	 * @param paramContext
	 */
	public static void deleteFile(Context paramContext){
		paramContext.deleteFile("activitypath.txt");
	}

}
