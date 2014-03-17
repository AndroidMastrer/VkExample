package com.example.vkexample.services;

import java.lang.reflect.Constructor;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ExecutorService extends IntentService {
	protected static String mServiceName = "RequestExecutorService";

	public ExecutorService() {
		super(mServiceName);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getAction();
		try {
			Class<?> clazz = Class.forName(action);
			Constructor<?> ctor = clazz.getConstructor(Intent.class);
			ctor.newInstance(intent);
		} catch (Exception ex) {
			Log.e(ExecutorService.class.getSimpleName(), "Can't execute "
					+ action);
		}
	}
}
