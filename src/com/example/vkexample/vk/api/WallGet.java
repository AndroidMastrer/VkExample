package com.example.vkexample.vk.api;

import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.vkexample.model.vk.Post;
import com.example.vkexample.vk.api.helper.VKRestApiHelper;

import android.content.Intent;
import android.os.Bundle;

public class WallGet extends VKRestApiHelper {
	public static final String EXTRA_OFFSET = "offset";
	public static final String EXTRA_COUNT = "count";
	public static final String EXTRA_WALL_POSTS = "wall_posts";
	private static final int COUNT = 20;
	private TreeSet<Post> mPosts;

	public WallGet(Intent intent) {
		mPosts = new TreeSet<Post>();
		StringBuilder request = new StringBuilder("wall.get?");
		request.append("offset=").append(intent.getIntExtra(EXTRA_OFFSET, 0))
				.append("&count=")
				.append(intent.getIntExtra(EXTRA_COUNT, COUNT))
				.append("&access_token=")
				.append(intent.getStringExtra(EXTRA_ACCESS_TOKEN));
		sendResult(doGet(request.toString()), intent);
	}

	@Override
    protected void parceResponse(JSONObject response) {
		response = response.optJSONObject("response");	
		JSONArray items = response.optJSONArray("response");	
		for(int i = 0;i<items.length();i++){
			mPosts.add(new Post(items.optJSONObject(i)));
		}
    }

	protected Bundle getBundleParams() {
		Bundle bundle = new Bundle();
		bundle.putParcelableArray(EXTRA_WALL_POSTS, (Post[])mPosts.toArray());
		return null;
	}

}
