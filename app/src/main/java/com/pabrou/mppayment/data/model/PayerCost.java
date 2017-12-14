package com.pabrou.mppayment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pablo on 13/12/17.
 */

public class PayerCost implements Parcelable {

    public int installments;

    @SerializedName("installment_rate")
    public float installmentRate;

    @SerializedName("recommended_message")
    public String recommendedMessage;

    @SerializedName("installment_amount")
    public float installmentAmount;

    @SerializedName("total_amount")
    public float totalAmount;

    public int getInstallments() {
        return installments;
    }

    public String getRecommendedMessage() {
        return recommendedMessage;
    }

    public float getInstallmentAmount() {
        return installmentAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.installments);
        dest.writeFloat(this.installmentRate);
        dest.writeString(this.recommendedMessage);
        dest.writeFloat(this.installmentAmount);
        dest.writeFloat(this.totalAmount);
    }

    public PayerCost() {
    }

    protected PayerCost(Parcel in) {
        this.installments = in.readInt();
        this.installmentRate = in.readFloat();
        this.recommendedMessage = in.readString();
        this.installmentAmount = in.readFloat();
        this.totalAmount = in.readFloat();
    }

    public static final Parcelable.Creator<PayerCost> CREATOR = new Parcelable.Creator<PayerCost>() {
        @Override
        public PayerCost createFromParcel(Parcel source) {
            return new PayerCost(source);
        }

        @Override
        public PayerCost[] newArray(int size) {
            return new PayerCost[size];
        }
    };
}
