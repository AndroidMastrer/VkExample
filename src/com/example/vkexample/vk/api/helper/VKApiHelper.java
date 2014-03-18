package com.example.vkexample.vk.api.helper;

import com.example.vkexample.services.ExecutorService;
import com.example.vkexample.vk.api.WallGet;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

public class VKApiHelper {
    private Context mContext;

    public VKApiHelper(Context context) {
	mContext = context;
    }

    public void wallGet(ResultReceiver receiver, int offset, int count, String accessToken, Long userId) {
	Intent intent = getServiceIntent();
	intent.putExtra(VKRestApiHelper.EXTRA_ACCESS_TOKEN, accessToken);
	intent.putExtra(VKRestApiHelper.EXTRA_USER_ID, userId);
	intent.putExtra(VKRestApiHelper.EXTRA_RESULT_RECEIVER, receiver);
	intent.putExtra(WallGet.EXTRA_OFFSET, offset);
	intent.putExtra(WallGet.EXTRA_COUNT, count);
	intent.setAction(WallGet.class.getName());
	mContext.startService(intent);
    }

    private Intent getServiceIntent() {
	return new Intent(mContext, ExecutorService.class);
    }

    public static String unescape(String text) {
	if (text == null)
	    return null;
	return text.replace("&amp;", "&").replace("&quot;", "\"").replace("<br>", "\n").replace("&gt;", ">")
		.replace("&lt;", "<").replace("<br/>", "\n").replace("&ndash;", "-").trim();
    }
}
