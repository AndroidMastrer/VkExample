package com.example.vkexample.vk;

import android.content.Context;
import android.content.SharedPreferences;

public class VkSession {
    private static final String SHARED_PREFERENCE_BASE_NAME = "vk_shared_preferences";

    private static final String SHARED_PREFERENCE_ACCESS_TOKEN = "vk_shared_access_token";
    private static final String SHARED_PREFERENCE_USER_ID = "vk_shared_user_id";

    private Context mContext;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;

    private String mAccessToken;
    private Long mUserId;

    public VkSession(Context context) {
	mContext = context;
	mShared = context.getSharedPreferences(SHARED_PREFERENCE_BASE_NAME, 0);
	mAccessToken = mShared.getString(SHARED_PREFERENCE_ACCESS_TOKEN, null);
	mUserId = mShared.getLong(SHARED_PREFERENCE_USER_ID, 0);
	mEditor = mShared.edit();
    }

    public String getAccessToken() {
	return mAccessToken;
    }

    public Long getUserId() {
	return mUserId;
    }

    public void logout() {
	initSession(null, null);
    }

    public boolean isActive() {
	return getAccessToken() != null;
    }

    public void initSession(String token, Long userId) {
	mAccessToken = token;
	mEditor.putString(SHARED_PREFERENCE_ACCESS_TOKEN, token);
	mUserId = userId;
	mEditor.putLong(SHARED_PREFERENCE_USER_ID, userId);
	mEditor.commit();
    }

}
