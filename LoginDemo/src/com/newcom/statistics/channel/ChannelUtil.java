package com.newcom.statistics.channel;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
/**
 * 渠道信息工具类
 * @author zhaohonglin
 *
 */
public class ChannelUtil {
	protected static final String a = ChannelUtil.class.getName();

	/**
	 * 获取下载渠道
	 * @param paramContext
	 * @return
	 */
	public static String getDownloadChannel(Context paramContext)
	{
		Object localObject1 = "Unknown";
		try {
			PackageManager localPackageManager = paramContext.getPackageManager();
			ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 128);
			//			localPackageManager.get
			if ((localApplicationInfo != null) && (localApplicationInfo.metaData != null)) {
				Object localObject2 = localApplicationInfo.metaData.get("XXT_CHANNEL");//TODO 下载渠道  待定
				if (localObject2 != null) {
					String str = localObject2.toString();
					if (str != null)
						localObject1 = str;
					else
						Log.i(a, "Could not read XXT_CHANNEL meta-data from AndroidManifest.xml.");
				}
			}
		}
		catch (Exception localException)
		{
			Log.i(a, "Could not read XXT_CHANNEL meta-data from AndroidManifest.xml.");

			localException.printStackTrace();
		}
		return ((String)localObject1);
	}
	/**
	 * 获取应用版本号
	 * @param paramContext
	 * @return
	 */
	public static String getAppVersion(Context paramContext)
	{
		PackageInfo localPackageInfo;
		try
		{
			localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);

			return localPackageInfo.versionName;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			return "Unknown";
		}
	}

	/**
	 * 将渠道信息封装成json
	 * @param paramContext
	 * @return
	 */
	/*public static String  getChannelData(Context paramContext) {
		ChannelData channelData=new ChannelData();
		channelData.setAppVersion(getAppVersion(paramContext));
		channelData.setDownloadChannel(getDownloadChannel(paramContext));
		Gson gson = new Gson();
		String json=gson.toJson(channelData);
		Log.i("MobclickAgent", json);
		return json;
	}*/
}
