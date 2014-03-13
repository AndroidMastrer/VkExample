package com.example.vkexample.vk.api;

import com.example.vkexample.restapi.ServerResponse;
import com.example.vkexample.vk.api.helper.VKRestApiHelper;

import android.content.Intent;
import android.os.Bundle;

public class WallGet extends VKRestApiHelper {
    public static final String EXTRA_OFFSET = "offset";
    public static final String EXTRA_COUNT = "count";
    public static final String EXTRA_WALL_POSTS = "awll_posts";
    private static final int COUNT = 30;

    public WallGet(Intent intent) {
	StringBuilder request = new StringBuilder("wall.get?");
	request.append("offset=").append(intent.getIntExtra(EXTRA_OFFSET, 0)).append("&count=")
		.append(intent.getIntExtra(EXTRA_COUNT, COUNT)).append("&access_token=")
		.append(intent.getStringExtra(EXTRA_ACCESS_TOKEN));	
	sendResult(doGet(request.toString()), intent);
    }
    
    @Override
    protected void parceResponse(ServerResponse response) {
	if(response)
    }

    protected Bundle getBundleParams() {
	Bundle bundle = new Bundle();
	bundle.putParcelableArray(EXTRA_WALL_POSTS, value);
	return null;
    }

}
