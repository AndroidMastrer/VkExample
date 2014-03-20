package com.example.vkexample.model.vk.vkdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.util.Log;

import com.example.vkexample.model.vk.Attachment;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class VKAttachmentsDBHelper {
    private static final String TAG = VKAttachmentsDBHelper.class.getSimpleName();

    public static void addAttachments(Collection<Attachment> Attachments) {
	try {
	    final Dao<Attachment, Integer> AttachmentDao = VKDatabaseHelper.getDatabaseHelper().getAttachmentDao();
	    for (Attachment Attachment : Attachments)
		AttachmentDao.createOrUpdate(Attachment);
	} catch (Exception e) {
	    Log.d(TAG, "Can't add VK Attachment to database");
	}
    }

    public static void removeAttachments(Collection<Attachment> Attachments) {
	try {
	    final Dao<Attachment, Integer> AttachmentDao = VKDatabaseHelper.getDatabaseHelper().getAttachmentDao();
	    AttachmentDao.delete(Attachments);
	} catch (Exception e) {
	    Log.d(TAG, "Can't add VK Attachment to database");
	}
    }

    public static List<Attachment> getAttachmentList() {
	List<Attachment> Attachments = new ArrayList<Attachment>();
	try {
	    final Dao<Attachment, Integer> AttachmentsDao = VKDatabaseHelper.getDatabaseHelper().getAttachmentDao();
	    QueryBuilder<Attachment, Integer> queryBuilder = AttachmentsDao.queryBuilder();

	    PreparedQuery<Attachment> preparedQuery = queryBuilder.prepare();
	    Attachments = AttachmentsDao.query(preparedQuery);
	} catch (Exception e) {
	    Log.d(TAG, "Can't get VK Attachments from database");
	}
	return Attachments;
    }
}
