package com.example.logindemo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;


import com.example.logindemo.R;
import com.newcom.agent.CollectionAgent;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginin,getout;
	private String username,password;
	private boolean result;
	private Handler handler;
	private Context context;
	private LocationListener locationListener;
	
	private LocationManager locationManager;
	private SharedPreferences sPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		usernameEditText= (EditText) findViewById(R.id.username);
		passwordEditText= (EditText) findViewById(R.id.password);
		loginin=(Button) findViewById(R.id.loginin);
		getout=(Button) findViewById(R.id.getout);
		context=this;
		CollectionAgent.onMain(context);
		CollectionAgent.onCreate(context);
		
			handler=new Handler(){
				public void handleMessage(Message msg){
					super.handleMessage(msg);
					
					if(true){
						
						
						Toast.makeText(getApplicationContext(), "登陆成功", 1000).show();
						Intent intent=new Intent();
						intent.setClass(MainActivity.this,HelloActivity.class);
						startActivity(intent);
					}
					else{
						Toast.makeText(MainActivity.this, "用户名或密码不匹配", 1000).show();
					}
				}
			};
		
		loginin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(){
					@Override 
					public void run(){ 
						username= usernameEditText.getText().toString();
						password=passwordEditText.getText().toString();
					//执行完毕后给handler发送一个空消息 
						//CollectionAgent.collactivityInfo(context);
						CollectionAgent.onEvent(35132, context);
					handler.sendEmptyMessage(0); 
					} 
					}.start();
				

			}
		});
		getout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showExitGameAlert();
			}
		});
		

		
		/*locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);//获取手机位置信息
		List<String> providers=locationManager.getAllProviders();
		for(String provider:providers){
			System.out.println("provider:"+provider);
		}
		Criteria criteria=new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(criteria.POWER_HIGH);
		criteria.setSpeedRequired(true);
		criteria.setAltitudeRequired(true);
		
		//获取最佳provider：手机或者模拟器上均为gps
		String bestProvider=locationManager.getBestProvider(criteria, true);
		System.out.println("最好位置提供者"+bestProvider);
		sPreferences=getSharedPreferences("config", MODE_PRIVATE);
		locationManager.requestLocationUpdates(bestProvider, 6000, 10, new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				location.getAccuracy();
				String latitude=location.getLatitude()+"";
				String longitude=location.getLongitude()+"";
				
				Editor editor=sPreferences.edit();
				editor.putString("lastlocation", latitude+"-"+longitude);
				editor.commit();
				System.out.println("nowlocation:"+latitude+"-"+longitude);
			}
		});*/
		
		
		
		

		
		
		
		
		/*//得到系统服务的LocationManager对象
				LocationManager loctionManager;
				String contextService=Context.LOCATION_SERVICE;
				//通过系统服务，取得LocationManager对象
				loctionManager=(LocationManager) context.getSystemService(contextService);
				//使用标准集合，让系统自动选择可用的最佳位置提供器，提供位置
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);//高精度
				criteria.setAltitudeRequired(false);//不要求海拔
				criteria.setBearingRequired(false);//不要求方位
				criteria.setCostAllowed(true);//允许有花费
				criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗
				//从可用的位置提供器中，匹配以上标准的最佳提供器
				String provider = loctionManager.getBestProvider(criteria, true);
				//获得最后一次变化的位置
				Location location = loctionManager.getLastKnownLocation(provider);
				
		        String longitude=location.getLongitude()+"";
		        String latitude=location.getLatitude()+"";
		        System.out.println("longitude1:"+longitude+"latitude1:"+latitude);
		        location.getLatitude();
		        
		        loctionManager.requestLocationUpdates(provider, 2000, 10, locationListener);
		        locationListener=new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						String longitude=location.getLongitude()+"";
				        String latitude=location.getLatitude()+"";
				        System.out.println("longitude:"+longitude+"latitude:"+latitude);
					}
				};*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CollectionAgent.onPause(context);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("---------ondestory--------");
		//CollectionAgent.onDestroy(context);
	}
/*	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 // 如果是返回键,直接返回到桌面
		 if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
		           showExitGameAlert();
		 }
		 
		 return super.onKeyDown(keyCode, event);
		}*/
	
	
		private void showExitGameAlert() {
		 final AlertDialog dlg = new AlertDialog.Builder(this).create();
		 dlg.show();
		 Window window = dlg.getWindow();
		        // *** 主要就是在这里实现这种效果的.
		        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		 window.setContentView(R.layout.shrew_exit_dialog);
		        // 为确认按钮添加事件,执行退出应用操作
		 Button ok = (Button) window.findViewById(R.id.btn_ok);
		 ok.setOnClickListener(new View.OnClickListener() {
		  public void onClick(View v) {
			  CollectionAgent.onDestroy(context);
		   exitApp(); // 退出应用...
		  }
		 });
		 
		        // 关闭alert对话框架
		        Button cancel = (Button) window.findViewById(R.id.btn_cancel);
		        cancel.setOnClickListener(new View.OnClickListener() {
		   public void onClick(View v) {
		    dlg.cancel();
		  }
		   });
		}
		public void exitApp(){
			android.os.Process.killProcess(android.os.Process.myPid());
		}
}





