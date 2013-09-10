package com.newcom.statistics.baseproperty;

import java.util.UUID;

import com.google.gson.Gson;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
/**
 * 获取终端属性工具类
 * @author zhaohonglin
 */
public class DeviceUtil {
	protected static final String a = DeviceUtil.class.getName();
	/**
	 * 获取设备型号 
	 * @return
	 */
	public static String getDeviceModel(){
		return Build.MODEL; 
	}
	/**
	 * 获取终端类型
	 * @return
	 */
	public static String getDeviceType(){
		return "0"; // android=0，ios=1
	}
	/**
	 * 获取操作系统版本
	 * @return 
	 */
	public static String getOSVersion(){

		return "android"+Build.VERSION.RELEASE; 
	}

	/**
	 * 获取设备分辨率 
	 * @param paramContext
	 * @return
	 */
	public static String getResolution(Context paramContext)
	{
		DisplayMetrics localDisplayMetrics;
		try
		{
			localDisplayMetrics = new DisplayMetrics();
			WindowManager localWindowManager = (WindowManager)(WindowManager)paramContext.getSystemService("window");

			localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);

			int i = localDisplayMetrics.widthPixels;
			int j = localDisplayMetrics.heightPixels;

			String str = String.valueOf(j) + "*" + String.valueOf(i);

			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
			return "Unknown";
		}
	}
	/**
	 * 获取网络运营商
	 * @param paramContext
	 * @return
	 */
	public static String getOperator(Context paramContext){
		String msg="Unknown";
		TelephonyManager telManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE); 
		String operator = telManager.getSimOperator();
		if(operator!=null){ 
			if(operator.equals("46000") || operator.equals("46002")|| operator.equals("46007")){
				msg="中国移动";//中国移动
			}else if(operator.equals("46001")){
				msg="中国联通";//中国联通
			}else if(operator.equals("46003")){
				msg="中国电信";//中国电信
			} }
		return msg; 
	}
	/**
	 * 获取设备编号 
	 * 用UUID生成设备的唯一编号
	 * @param paramContext
	 * @return
	 */
	@SuppressWarnings("unused")
	public  static String getDeviceNum(Context paramContext){
		TelephonyManager tm = (TelephonyManager) ((ContextWrapper) paramContext).getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		String tmDevice = "" + tm.getDeviceId();    
		String tmSerial = "" + tm.getSimSerialNumber();   //TODO 不考虑换手机卡的情况,若换手机卡则认为是新增用户??
		String androidId = "" + android.provider.Settings.Secure.getString(paramContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID); 
		if (null == tmDevice){
			Log.i(a,"[tmDevice]"+tmDevice);
			tmDevice = new String();
		}
		if (null == androidId){
			Log.i(a,"[androidId]"+androidId);
			androidId = new String();
		}		
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());   
		return deviceUuid.toString();}

	/**
	 * 将设备信息封装成json
	 * @param paramContext
	 * @return
	 */
	/*public static String  getDeviceData(Context paramContext) {
		DeviceData deviceData=new DeviceData();
		deviceData.setDevModel(getDeviceModel());
		deviceData.setDevType(getDeviceType());
		deviceData.setResolution(getResolution(paramContext));
		deviceData.setOsVersion(getOSVersion());
		deviceData.setOperator(getOperator(paramContext));
		Gson gson = new Gson();
		String json=gson.toJson(deviceData);
		Log.i("MobclickAgent", json);
		return json;
	}	*/
}
