package com.pabrou.mppayment;

import android.app.Application;

import com.pabrou.mppayment.data.Settings;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by pablo on 12/12/17.
 */

public class MPPaymentApplication extends Application {

    private static final String TAG = MPPaymentApplication.class.getSimpleName();

    private static MPPaymentApplication mInstance;

    public static MPPaymentApplication getInstance() {
        return mInstance;
    }

    public MPPaymentApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Settings.initialize(this);

        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
    }
}
