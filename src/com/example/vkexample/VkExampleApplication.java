package com.example.vkexample;

import com.example.vkexample.vk.VkSession;

import android.app.Application;

public class VkExampleApplication extends Application {
	
	private VkSession mVKSessionInstance;
	
	public VkSession getVkSession(){
		if(mVKSessionInstance==null)
			mVKSessionInstance = new VkSession(getApplicationContext());
		return mVKSessionInstance;
	}
}
