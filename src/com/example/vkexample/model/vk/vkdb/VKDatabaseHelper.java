package com.example.vkexample.model.vk.vkdb;

import java.sql.SQLException;

import android.content.Context;

import com.example.vkexample.database.DatabaseHelper;
import com.example.vkexample.model.vk.Attachment;
import com.example.vkexample.model.vk.Link;
import com.example.vkexample.model.vk.Photo;
import com.example.vkexample.model.vk.Post;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class VKDatabaseHelper extends DatabaseHelper {
    public VKDatabaseHelper(Context context) {
	super(context);
    }

    protected final String TAG = VKDatabaseHelper.class.getSimpleName();
    protected static VKDatabaseHelper databaseHelper;

    private Dao<Post, Integer> mPostDao;
    private Dao<Attachment, Integer> mAttachmentDao;
    private Dao<Link, Integer> mLinkDao;
    private Dao<Photo, Integer> mPhotoDao;

    @Override
    protected void createTables(ConnectionSource connectionSource) throws SQLException {
	TableUtils.createTable(connectionSource, Post.class);
	TableUtils.createTable(connectionSource, Attachment.class);
	TableUtils.createTable(connectionSource, Link.class);
	TableUtils.createTable(connectionSource, Photo.class);
    }

    @Override
    protected void dropTables(ConnectionSource connectionSource) throws SQLException {
	TableUtils.dropTable(connectionSource, Post.class, true);
	TableUtils.dropTable(connectionSource, Attachment.class, true);
	TableUtils.dropTable(connectionSource, Link.class, true);
	TableUtils.dropTable(connectionSource, Photo.class, true);
    }

    @Override
    protected void clearTables(AndroidConnectionSource connectionSource) throws SQLException {
	TableUtils.clearTable(connectionSource, Post.class);
	TableUtils.clearTable(connectionSource, Attachment.class);
	TableUtils.clearTable(connectionSource, Link.class);
	TableUtils.clearTable(connectionSource, Photo.class);
    }

    public Dao<Post, Integer> getPostDao() throws SQLException {
	if (mPostDao == null) {
	    mPostDao = getDao(Post.class);
	}
	return mPostDao;
    }

    public Dao<Attachment, Integer> getAttachmentDao() throws SQLException {
	if (mAttachmentDao == null) {
	    mAttachmentDao = getDao(Attachment.class);
	}
	return mAttachmentDao;
    }

    public Dao<Link, Integer> getLinkDao() throws SQLException {
	if (mLinkDao == null) {
	    mLinkDao = getDao(Link.class);
	}
	return mLinkDao;
    }

    public Dao<Photo, Integer> getPhotoDao() throws SQLException {
	if (mPhotoDao == null) {
	    mPhotoDao = getDao(Photo.class);
	}
	return mPhotoDao;
    }

    public static VKDatabaseHelper getDatabaseHelper() {
	return databaseHelper;
    }

    public static void createDBHelper(Context applicationContext) {
	if (databaseHelper == null)
	    databaseHelper = OpenHelperManager.getHelper(applicationContext, VKDatabaseHelper.class);
    }

    public static void destroyDatabaseHelper() {
	if (databaseHelper != null) {
	    OpenHelperManager.releaseHelper();
	    databaseHelper = null;
	}
    }
}
