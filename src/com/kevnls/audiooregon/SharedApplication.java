package com.kevnls.audiooregon;

import android.app.Application;

public class SharedApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//probably want to do my updates on the local data here
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	} 

}
