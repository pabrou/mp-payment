package com.pabrou.mppayment.rest;

import com.pabrou.mppayment.data.model.CardIssuer;
import com.pabrou.mppayment.data.model.InstallmentOption;
import com.pabrou.mppayment.data.model.PaymentMethod;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pablo on 12/12/17.
 */

public class PaymentMethodsService extends BaseService {

    private static final String TAG = PaymentMethodsService.class.getSimpleName();

    private final PaymentMethodsApi paymentMethodsApi;

    public PaymentMethodsService() {
        paymentMethodsApi = buildApi(PaymentMethodsApi.class);
    }

    public Single<List<PaymentMethod>> getCreditCardPaymentMethods() {
        return paymentMethodsApi.getPaymentMethods()
                .retry(1)
                .flattenAsObservable(paymentMethods -> paymentMethods)
                .filter(PaymentMethod::isActive)
                .filter(PaymentMethod::isCreditCard)
                .toList();
    }

    public Single<List<CardIssuer>> getCardIssuers(String paymentMethodId) {
        return paymentMethodsApi.getCardIssuers(paymentMethodId)
                .retry(1);
    }

    public Single<List<InstallmentOption>> getInstallmentOptions(float amount,
                                                                 String paymentMethodId,
                                                                 String issuerId) {
        return paymentMethodsApi.getInstallmentOptions(amount, paymentMethodId, issuerId)
                .retry(1);
    }
}
