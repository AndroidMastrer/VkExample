package com.example.vkexample;

import com.example.vkexample.database.DatabaseHelper;
import com.example.vkexample.model.vk.vkdb.VKDatabaseHelper;
import com.example.vkexample.vk.VkSession;

import android.app.Application;

public class VkExampleApplication extends Application {

    private VkSession mVKSessionInstance;

    public VkSession getVkSession() {
	if (mVKSessionInstance == null)
	    mVKSessionInstance = new VkSession(getApplicationContext());
	return mVKSessionInstance;
    }

    public void onCreate() {
	VKDatabaseHelper.createDBHelper(getApplicationContext());
    };

    @Override
    public void onTerminate() {
	super.onTerminate();
	VKDatabaseHelper.destroyDatabaseHelper();
    }
}
