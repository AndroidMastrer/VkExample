package com.example.vkexample.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.os.Parcel;
import android.os.Parcelable;

@DatabaseTable(tableName = "posts")
public class Post implements Parcelable {

	@DatabaseField(generatedId = true)
	private int Id;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(val);
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
		mData = in.readInt();
	}

}
