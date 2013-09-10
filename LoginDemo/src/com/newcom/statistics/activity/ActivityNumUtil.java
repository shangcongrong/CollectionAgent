package com.newcom.statistics.activity;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
/**
 * 页面编号工具类
 * @author zhaohonglin
 *
 */
public class ActivityNumUtil {
	protected static final String a = ActivityNumUtil.class.getName();

	/**
	 * 获取应用里所有Activity
	 * @param paramContext
	 * @return
	 */
	public static List<String> getAllActivity(Context paramContext){
		PackageManager localPackageManager = paramContext.getPackageManager();
		List<String> activityList = new ArrayList<String>();	
		try {
			PackageInfo pack=localPackageManager.getPackageInfo(paramContext.getPackageName(), 1);
			ActivityInfo[]  aio=pack.activities;

			for(int i=0;i<aio.length;i++){
				activityList.add(aio[i].name);
				System.out.println("aio[i].name:"+aio[i].name);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			}
		return activityList;
	}	


	/**
	 * 将Activity及对应的编号保存到缓存文件里
	 * @param paramJSON
	 */
	public static void saveActivityNumDataToFile(Context paramContext,String paramJSON) {

		FileOutputStream localFileOutputStream = null;
		try {
			localFileOutputStream = paramContext.openFileOutput("activitynum.txt", 0);
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
		Log.i("CollectionAgent", "saveActivityNumDataToFile success");
	}

	/**
	 * 从缓存文件里获取Activity及对应的编号数据
	 * @param paramContext
	 * @return
	 */
	public static String getActivityNumDataFromFile(Context paramContext){
		String msg="";
		FileInputStream inStream = null;
		ByteArrayOutputStream stream = null;
		try {       
			inStream=paramContext.openFileInput("activitynum.txt");    
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
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}

		}    
		return msg;
	}
	/**
	 * 根据Activity类名获取获取相应的编号
	 * @param paramContext
	 * @param activity
	 * @return
	 */
	public static int getActivityNumByActivity(Context paramContext,String activity){
		int activityNum = 0;
		List<ActivityNumData> lists=parseJson(getActivityNumDataFromFile(paramContext));
		//String activitynameJson=getActivityNumDataFromFile(paramContext);
		
		if(null!=lists&&!lists.isEmpty()){
			for(int i=0;i<lists.size();i++){
				ActivityNumData activityNumData=lists.get(i);
				if(activity.equals(activityNumData.getActivityname())){
					activityNum=activityNumData.getActivitynum();
				}
			}
		}
		return activityNum;	
	}
	/**
	 * 检查Activity个数是否有变化
	 * @param paramContext
	 * @return
	 */
	/*public static boolean checkActivity(Context paramContext){
		boolean flag=false;
		List<ActivityNumData> lists=parseJson( getActivityNumDataFromFile(paramContext));//上次的Activity
		String activitys=getAllActivity(paramContext);
		String[] strs=activitys.split(",");
		if(null!=lists&&!lists.isEmpty()){
			if(lists.size()==strs.length){
				flag=true;
			}
		}
		return flag;
	}*/

	private static List<ActivityNumData> parseJson(String content) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<ActivityNumData>>() {}.getType();
		return gson.fromJson(content, type);
	}
}
