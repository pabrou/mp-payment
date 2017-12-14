package com.pabrou.mppayment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pablo on 12/12/17.
 */

public class CardIssuer implements Parcelable {

    public String id;

    public String name;

    public String thumbnail;

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.thumbnail);
    }

    public CardIssuer() {
    }

    protected CardIssuer(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.thumbnail = in.readString();
    }

    public static final Parcelable.Creator<CardIssuer> CREATOR = new Parcelable.Creator<CardIssuer>() {
        @Override
        public CardIssuer createFromParcel(Parcel source) {
            return new CardIssuer(source);
        }

        @Override
        public CardIssuer[] newArray(int size) {
            return new CardIssuer[size];
        }
    };
}
