package com.example.vkexample.model.vk;

import org.json.JSONObject;

import com.example.vkexample.vk.api.helper.VKApiHelper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.os.Parcel;
import android.os.Parcelable;

@DatabaseTable(tableName = "vk_photo")
public class Photo implements Parcelable {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField
    public long photoId;

    @DatabaseField
    public long albumId;

    @DatabaseField
    public long ownerId;

    @DatabaseField
    public long userId;

    @DatabaseField
    public String photo_75;

    @DatabaseField
    public String photo_130;

    @DatabaseField
    public String photo_604;

    @DatabaseField
    public String photo_807;

    @DatabaseField
    public int width;

    @DatabaseField
    public int height;

    @DatabaseField
    public String text;

    @DatabaseField
    public long date;

    @DatabaseField
    public String accessKey;

    @Override
    public int describeContents() {
	return 0;
    }

    public Photo() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	dest.writeInt(Id);
	dest.writeLong(photoId);
	dest.writeLong(albumId);
	dest.writeLong(ownerId);
	dest.writeLong(userId);
	dest.writeString(photo_75);
	dest.writeString(photo_130);
	dest.writeString(photo_604);
	dest.writeString(photo_807);
	dest.writeInt(width);
	dest.writeInt(height);
	dest.writeString(text);
	dest.writeLong(date);
	dest.writeString(accessKey);
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
	public Photo createFromParcel(Parcel in) {
	    return new Photo(in);
	}

	public Photo[] newArray(int size) {
	    return new Photo[size];
	}
    };

    private Photo(Parcel in) {
	Id = in.readInt();
	photoId = in.readLong();
	albumId = in.readLong();
	ownerId = in.readLong();
	userId = in.readLong();
	photo_75 = in.readString();
	photo_130 = in.readString();
	photo_604 = in.readString();
	photo_807 = in.readString();
	width = in.readInt();
	height = in.readInt();
	text = in.readString();
	date = in.readLong();
	accessKey = in.readString();
    }

    public Photo(JSONObject source) {
	photoId = source.optLong("id");
	albumId = source.optLong("album_id");
	ownerId = source.optLong("owner_id");
	userId = source.optLong("user_id");
	photo_75 = source.optString("photo_75");
	photo_130 = source.optString("photo_130");
	photo_604 = source.optString("photo_604");
	photo_807 = source.optString("photo_807");
	width = source.optInt("width");
	height = source.optInt("height");
	text = VKApiHelper.unescape(source.optString("text"));
	date = source.optLong("date");
	accessKey = source.optString("access_key");
    }

}
