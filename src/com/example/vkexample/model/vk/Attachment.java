package com.example.vkexample.model.vk;

import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.os.Parcel;
import android.os.Parcelable;

@DatabaseTable(tableName = "vk_attachment")
public class Attachment implements Parcelable {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField
    public String type;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    public Link link;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    public Photo photo;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Post post;

    @Override
    public int describeContents() {
	return 0;
    }

    public Attachment() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	dest.writeInt(Id);
	dest.writeParcelable(link, flags);
	dest.writeParcelable(photo, flags);
	dest.writeParcelable(post, flags);
    }

    public static final Parcelable.Creator<Attachment> CREATOR = new Parcelable.Creator<Attachment>() {
	public Attachment createFromParcel(Parcel in) {
	    return new Attachment(in);
	}

	public Attachment[] newArray(int size) {
	    return new Attachment[size];
	}
    };

    private Attachment(Parcel in) {
	Id = in.readInt();
	link = in.readParcelable(Link.class.getClassLoader());
	photo = in.readParcelable(Photo.class.getClassLoader());
	post = in.readParcelable(Post.class.getClassLoader());
    }

    public Attachment(Post post, JSONObject source) {
	type = source.optString("type");
	if (type.equals("photo") || type.equals("posted_photo")) {
	    JSONObject x = source.optJSONObject("photo");
	    if (x != null)
		photo = new Photo(x);
	} else if (type.equals("link"))
	    link = new Link(source.optJSONObject("link"));
	this.post = post;
    }
}
