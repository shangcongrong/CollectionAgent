package com.newcom.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


public class BaseHttp {

	private static String jsonstring =null;

	private static HttpClient httpClient;
	private static HttpPost httpRequest;
	private static HttpResponse httpResponse;

	private static List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();

	public BaseHttp() {
		httpClient = new DefaultHttpClient();
	}

	// 向服务器发送请求
	protected static void sendrequest(String url) throws Exception {
		httpClient = new DefaultHttpClient();
		httpRequest = new HttpPost(url);
		httpRequest.setEntity(new UrlEncodedFormEntity(nameValueList));
		httpResponse = httpClient.execute(httpRequest);
	}

	// 得到返回数据
	protected static void getresponse() throws Exception {
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent()));
			jsonstring = bufferedReader.readLine();
			/*for (String sstring = bufferedReader.readLine(); sstring != null; sstring = bufferedReader
					.readLine()) {
				
				Log.i("sstring", sstring);
			}*/
		}
	}

	/**
	 * 向服务器发送信息
	 * 
	 * @param key
	 * @param value
	 */
	public static void addNameValuePair(String key, String value) {
		nameValueList.add(new BasicNameValuePair(key, value));
	}

	// 返回jsonObject对象数据模型
	public static JSONObject getJson() throws JSONException {
		return new JSONObject(jsonstring);
	}
}
