package com.example.vkexample.model.vk;

import org.json.JSONObject;

import com.example.vkexample.vk.api.helper.VKApiHelper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.os.Parcel;
import android.os.Parcelable;

@DatabaseTable(tableName = "vk_link")
public class Link implements Parcelable {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField
    public String url;

    @DatabaseField
    public String title;

    @DatabaseField
    public String description;

    @DatabaseField
    public String image;

    @Override
    public int describeContents() {
	return 0;
    }

    public Link() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	dest.writeInt(Id);
	dest.writeString(url);
	dest.writeString(title);
	dest.writeString(description);
	dest.writeString(image);
    }

    public static final Parcelable.Creator<Link> CREATOR = new Parcelable.Creator<Link>() {
	public Link createFromParcel(Parcel in) {
	    return new Link(in);
	}

	public Link[] newArray(int size) {
	    return new Link[size];
	}
    };

    private Link(Parcel in) {
	Id = in.readInt();
	url = in.readString();
	title = in.readString();
	description = in.readString();
	image = in.readString();
    }

    public Link(JSONObject source) {
	url = source.optString("url");
	title = VKApiHelper.unescape(source.optString("title"));
	description = VKApiHelper.unescape(source.optString("description"));
	image = source.optString("image_src");
    }
}
