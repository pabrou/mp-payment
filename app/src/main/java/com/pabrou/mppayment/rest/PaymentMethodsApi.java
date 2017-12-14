package com.pabrou.mppayment.rest;

import com.pabrou.mppayment.data.model.CardIssuer;
import com.pabrou.mppayment.data.model.InstallmentOption;
import com.pabrou.mppayment.data.model.PaymentMethod;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pablo on 12/12/17.
 */

public interface PaymentMethodsApi {

    @GET("payment_methods")
    Single<List<PaymentMethod>> getPaymentMethods();

    @GET("payment_methods/card_issuers")
    Single<List<CardIssuer>> getCardIssuers(@Query("payment_method_id") String paymentMethodId);

    @GET("payment_methods/installments")
    Single<List<InstallmentOption>> getInstallmentOptions(@Query("amount") float amount,
                                                          @Query("payment_method_id") String paymentMethodId,
                                                          @Query("issuer.id") String issuerId);

}
