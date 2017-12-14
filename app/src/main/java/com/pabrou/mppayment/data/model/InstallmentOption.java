package com.pabrou.mppayment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pablo on 12/12/17.
 */

public class InstallmentOption implements Parcelable {

    @SerializedName("payment_method_id")
    public String paymentMethodId;

    @SerializedName("payment_type_id")
    public String paymentTypeId;

    public CardIssuer issuer;

    @SerializedName("payer_costs")
    public List<PayerCost> payerCosts;

    public InstallmentOption() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentMethodId);
        dest.writeString(this.paymentTypeId);
        dest.writeParcelable(this.issuer, flags);
        dest.writeTypedList(this.payerCosts);
    }

    protected InstallmentOption(Parcel in) {
        this.paymentMethodId = in.readString();
        this.paymentTypeId = in.readString();
        this.issuer = in.readParcelable(CardIssuer.class.getClassLoader());
        this.payerCosts = in.createTypedArrayList(PayerCost.CREATOR);
    }

    public static final Creator<InstallmentOption> CREATOR = new Creator<InstallmentOption>() {
        @Override
        public InstallmentOption createFromParcel(Parcel source) {
            return new InstallmentOption(source);
        }

        @Override
        public InstallmentOption[] newArray(int size) {
            return new InstallmentOption[size];
        }
    };
}
