package com.pabrou.mppayment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pablo on 13/12/17.
 */

public class PaymentData implements Parcelable {

    private float amount;

    private PaymentMethod paymentMethod;

    private CardIssuer cardIssuer;

    private PayerCost payerCost;

    public PaymentData(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CardIssuer getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(CardIssuer cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public PayerCost getPayerCost() {
        return payerCost;
    }

    public void setPayerCost(PayerCost payerCost) {
        this.payerCost = payerCost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.amount);
        dest.writeParcelable(this.paymentMethod, flags);
        dest.writeParcelable(this.cardIssuer, flags);
        dest.writeParcelable(this.payerCost, flags);
    }

    protected PaymentData(Parcel in) {
        this.amount = in.readFloat();
        this.paymentMethod = in.readParcelable(PaymentMethod.class.getClassLoader());
        this.cardIssuer = in.readParcelable(CardIssuer.class.getClassLoader());
        this.payerCost = in.readParcelable(PayerCost.class.getClassLoader());
    }

    public static final Creator<PaymentData> CREATOR = new Creator<PaymentData>() {
        @Override
        public PaymentData createFromParcel(Parcel source) {
            return new PaymentData(source);
        }

        @Override
        public PaymentData[] newArray(int size) {
            return new PaymentData[size];
        }
    };
}
