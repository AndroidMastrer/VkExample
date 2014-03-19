package com.example.vkexample.model.vk.vkdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.util.Log;

import com.example.vkexample.model.vk.Post;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class VKPostsDBHelper {
    private static final String TAG = VKPostsDBHelper.class.getSimpleName();

    public static void addPosts(Collection<Post> posts) {
	try {
	    final Dao<Post, Integer> postDao = VKDatabaseHelper.getDatabaseHelper().getPostDao();
	    for (Post post : posts)
		postDao.createOrUpdate(post);
	} catch (Exception e) {
	    Log.d(TAG, "Can't add VK post to database");
	}
    }

    public static List<Post> getPostList(Long ownerId, long offset, long limit) {
	List<Post> posts = new ArrayList<Post>();
	try {
	    final Dao<Post, Integer> postsDao = VKDatabaseHelper.getDatabaseHelper().getPostDao();
	    QueryBuilder<Post, Integer> queryBuilder = postsDao.queryBuilder();
	    queryBuilder.orderBy("date", false).limit(limit).offset(offset).where().eq("ownerId", ownerId);

	    PreparedQuery<Post> preparedQuery = queryBuilder.prepare();
	    posts = postsDao.query(preparedQuery);
	} catch (Exception e) {
	    Log.d(TAG, "Can't get VK posts from database");
	}
	return posts;
    }
}
