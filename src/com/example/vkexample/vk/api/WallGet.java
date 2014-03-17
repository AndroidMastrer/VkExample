package com.example.vkexample.vk.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.vkexample.model.vk.Attachment;
import com.example.vkexample.model.vk.Post;
import com.example.vkexample.model.vk.vkdb.VKAttachmentsDBHelper;
import com.example.vkexample.model.vk.vkdb.VKPostsDBHelper;
import com.example.vkexample.vk.api.helper.VKHTTPConstants;
import com.example.vkexample.vk.api.helper.VKRestApiHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class WallGet extends VKRestApiHelper {
	public static final String EXTRA_OFFSET = "offset";
	public static final String EXTRA_COUNT = "count";
	public static final String EXTRA_WALL_POSTS = "wall_posts";
	private static final int COUNT = 20;
	private TreeSet<Post> mPosts;

	public WallGet(Intent intent) {
		mPosts = new TreeSet<Post>();
		long offset = intent.getIntExtra(EXTRA_OFFSET, 0);
		long limit = intent.getIntExtra(EXTRA_COUNT, COUNT);
		mPosts.addAll(VKPostsDBHelper.getPostList());
		if (mPosts.size() > 0) {
			ResultReceiver receiver = intent
					.getParcelableExtra(EXTRA_RESULT_RECEIVER);
			receiver.send(VKHTTPConstants.HTTP_OK, getBundleParams());
		}
		StringBuilder request = new StringBuilder("wall.get?");
		request.append("offset=").append(offset).append("&count=")
				.append(limit).append("&access_token=")
				.append(intent.getStringExtra(EXTRA_ACCESS_TOKEN));
		sendResult(doGet(request.toString()), intent);
	}

	@Override
	protected void parceResponse(JSONObject response) {
		response = response.optJSONObject("response");
		JSONArray items = response.optJSONArray("response");
		List<Post> postsToDB = new ArrayList<Post>();
		List<Attachment> attachsToDB = new ArrayList<Attachment>();
		Post temp;
		for (int i = 0; i < items.length(); i++) {
			temp = new Post(items.optJSONObject(i));
			postsToDB.addAll(Arrays.asList(temp.copyHistoreArray));
			attachsToDB.addAll(Arrays.asList(temp.attachmentsArray));
			mPosts.add(temp);
		}
		VKPostsDBHelper.addPosts(postsToDB);
		VKAttachmentsDBHelper.addAttachments(attachsToDB);
	}

	protected Bundle getBundleParams() {
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_RESULT_ACTION, WallGet.class.getSimpleName());
		bundle.putParcelableArrayList(EXTRA_WALL_POSTS, new ArrayList<Post>(mPosts));
		return null;
	}

}
