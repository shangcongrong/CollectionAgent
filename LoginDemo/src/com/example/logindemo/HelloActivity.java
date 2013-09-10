package com.example.logindemo;

import com.example.logindemo.R;
import com.newcom.agent.CollectionAgent;

import android.app.Activity;
import android.os.Bundle;

public class HelloActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_activity);
		CollectionAgent.onCreate(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CollectionAgent.onPause(this);
	}
	

}
