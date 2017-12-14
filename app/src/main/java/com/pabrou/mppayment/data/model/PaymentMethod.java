package com.pabrou.mppayment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pablo on 12/12/17.
 */

public class PaymentMethod implements Parcelable {

    public static final String STATUS_ACTIVE = "active";

    public static final String PAYMENT_TYPE_CREDIT_CARD = "credit_card";

    public String id;

    public String name;

    public String thumbnail;

    @SerializedName("payment_type_id")
    public String typeId;

    public String status;

    public boolean isActive() {
        return STATUS_ACTIVE.equals(status);
    }

    public boolean isCreditCard() {
        return PAYMENT_TYPE_CREDIT_CARD.equals(typeId);
    }

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
        dest.writeString(this.typeId);
        dest.writeString(this.status);
    }

    public PaymentMethod() {
    }

    protected PaymentMethod(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.thumbnail = in.readString();
        this.typeId = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<PaymentMethod> CREATOR = new Parcelable.Creator<PaymentMethod>() {
        @Override
        public PaymentMethod createFromParcel(Parcel source) {
            return new PaymentMethod(source);
        }

        @Override
        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };
}
