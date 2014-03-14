package com.example.vkexample.model.vk;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.vkexample.model.vk.vkdb.VKDatabaseHelper;
import com.example.vkexample.vk.api.helper.VKApiHelper;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import android.os.Parcel;
import android.os.Parcelable;

@DatabaseTable(tableName = "vk_posts")
public class Post implements Parcelable {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField
    public long postId;

    @DatabaseField
    public long fromId;

    @DatabaseField
    public long ownerId;

    @DatabaseField
    public long date;

    @DatabaseField
    public String text;

    @DatabaseField
    public String postType;

    @DatabaseField(foreign = true, canBeNull = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Post post;

    @ForeignCollectionField
    public ForeignCollection<Post> copyHistory;

    @ForeignCollectionField
    public ForeignCollection<Attachment> attachments;

    @Override
    public int describeContents() {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	dest.writeInt(Id);
	dest.writeLong(postId);
	dest.writeLong(ownerId);
	dest.writeLong(date);
	dest.writeString(text);
	dest.writeString(postType);
	dest.writeParcelable(post, 0);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
	public Post createFromParcel(Parcel in) {
	    return new Post(in);
	}

	public Post[] newArray(int size) {
	    return new Post[size];
	}
    };

    private Post(Parcel in) {
	Id = in.readInt();
	postId = in.readLong();
	ownerId = in.readLong();
	date = in.readLong();
	text = in.readString();
	postType = in.readString();
	post = in.readParcelable(Post.class.getClassLoader());
    }

    public Post(JSONObject source) {
	createPost(source);
    }

    public Post(Post post, JSONObject source) {
	this.post = post;
	createPost(source);
    }
    
    private void createPost(JSONObject source){
	postId = source.optLong("id");
	fromId = source.optLong("from_id");
	if (source.has("to_id"))
	    ownerId = source.optLong("to_id");
	else
	    ownerId = source.optLong("owner_id");
	date = source.optLong("date");
	postType = source.optString("post_type");
	text = VKApiHelper.unescape(source.optString("text"));
	JSONArray copyHistoryJson = source.optJSONArray("copy_history");
	if (copyHistoryJson != null) {
	    for (int i = 0; i < copyHistoryJson.length(); ++i) {
		JSONObject history_item = copyHistoryJson.optJSONObject(i);

		if (history_item == null || history_item.isNull("id"))
		    continue;
		
		new Post(this, history_item);
	    }
	}
	JSONArray attachments = source.optJSONArray("attachments");
	if (attachments != null) {
	    for (int i = 0; i < attachments.length(); ++i) {
		JSONObject attachment = attachments.optJSONObject(i);

		if (attachment == null || attachment.isNull("id"))
		    continue;

		new Attachment(this, attachment);
	    }
	}
    }

}
