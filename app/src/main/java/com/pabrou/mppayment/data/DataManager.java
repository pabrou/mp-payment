package com.pabrou.mppayment.data;

import com.pabrou.mppayment.data.model.CardIssuer;
import com.pabrou.mppayment.data.model.PayerCost;
import com.pabrou.mppayment.data.model.PaymentMethod;
import com.pabrou.mppayment.rest.PaymentMethodsService;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by pablo on 12/12/17.
 */

public class DataManager {

    private static final String TAG = DataManager.class.getSimpleName();

    private static DataManager instance;

    private final PaymentMethodsService paymentMethodsService;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager() {
        paymentMethodsService = new PaymentMethodsService();
    }


    public Single<List<PaymentMethod>> getCreditCardPaymentMethods() {

        return paymentMethodsService.getCreditCardPaymentMethods();
    }


    public Single<List<CardIssuer>> getCardIssuers(PaymentMethod paymentMethod) {

        return paymentMethodsService.getCardIssuers(paymentMethod.id);
    }


    public Single<List<PayerCost>> getPayerCosts(float amount,
                                                 PaymentMethod paymentMethod,
                                                 CardIssuer cardIssuer) {

        return paymentMethodsService.getInstallmentOptions(amount, paymentMethod.id, cardIssuer.id)
                .map(installmentOptions -> installmentOptions.get(0))
                .map(installmentOption -> installmentOption.payerCosts);
    }
}
