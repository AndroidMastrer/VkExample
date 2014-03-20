package com.example.vkexample.vk.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.json.JSONArray;
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

public class WallGet extends VKRestApiHelper {
    public static final String EXTRA_OFFSET = "offset";
    public static final String EXTRA_COUNT = "count";
    public static final String EXTRA_WALL_POSTS = "wall_posts";
    private static final int COUNT = 20;
    private List<Post> mPosts;
    private Long mUserId;
    private long mOffset = 0;
    private long mLimit = COUNT;

    public WallGet(Intent intent) {
	mPosts = new ArrayList<Post>();
	mOffset = intent.getIntExtra(EXTRA_OFFSET, 0);
	mLimit = intent.getIntExtra(EXTRA_COUNT, COUNT);
	mUserId = intent.getLongExtra(EXTRA_USER_ID, 0);
	mPosts.addAll(VKPostsDBHelper.getPostList(mUserId, mOffset, mLimit));
	if (mOffset > 0 && mPosts.size() > 0) {
	    ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
	    receiver.send(VKHTTPConstants.HTTP_OK, getBundleParams());
	}
	StringBuilder request = new StringBuilder("wall.get?");
	request.append("offset=").append(mOffset).append("&count=").append(mLimit).append("&access_token=")
		.append(intent.getStringExtra(EXTRA_ACCESS_TOKEN));
	sendResult(doGet(request.toString()), intent);
    }

    @Override
    protected void parceResponse(JSONObject response) {
	response = response.optJSONObject("response");
	JSONArray items = response.optJSONArray("response");
	List<Post> postsToDB = new ArrayList<Post>();
	List<Post> postsFromDB = new ArrayList<Post>();
	List<Attachment> attachsToDB = new ArrayList<Attachment>();
	List<Attachment> attachsFromDB = new ArrayList<Attachment>();
	Post temp;

	TreeSet<Post> sortedPosts = new TreeSet<Post>(mPosts);
	Post sortedTemp;
	int tempInd;
	for (int i = 0; i < items.length(); i++) {
	    temp = new Post(items.optJSONObject(i));
	    postsToDB.addAll(Arrays.asList(temp.copyHistoreArray));
	    attachsToDB.addAll(Arrays.asList(temp.attachmentsArray));
	    tempInd = mPosts.indexOf(temp);
	    if (tempInd >= 0) {
		temp.Id = mPosts.get(tempInd).Id;
		mPosts.set(tempInd, temp);
	    } else {
		sortedPosts.add(temp);
		while (!sortedPosts.first().equals(temp)) {
		    sortedTemp = sortedPosts.pollFirst();
		    mPosts.remove(sortedTemp);
		    postsFromDB.addAll(sortedTemp.copyHistory);
		    attachsFromDB.addAll(sortedTemp.attachments);
		    postsFromDB.add(sortedTemp);
		}
		mPosts.add(temp);
	    }
	}
	VKPostsDBHelper.removePosts(postsFromDB);
	VKAttachmentsDBHelper.removeAttachments(attachsFromDB);
	VKPostsDBHelper.addPosts(mPosts);
	VKPostsDBHelper.addPosts(postsToDB);
	VKAttachmentsDBHelper.addAttachments(attachsToDB);
	mPosts.clear();
	mPosts.addAll(VKPostsDBHelper.getPostList(mUserId, mOffset, mLimit));
    }

    protected Bundle getBundleParams() {
	Bundle bundle = new Bundle();
	bundle.putString(EXTRA_RESULT_ACTION, WallGet.class.getSimpleName());
	bundle.putParcelableArrayList(EXTRA_WALL_POSTS, new ArrayList<Post>(mPosts));
	return null;
    }

}
