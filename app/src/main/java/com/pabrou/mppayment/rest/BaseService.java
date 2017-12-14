package com.pabrou.mppayment.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pabrou.mppayment.BuildConfig;
import com.pabrou.mppayment.data.Settings;

import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pablo on 12/12/17.
 */

public class BaseService {

    private final static String TAG = BaseService.class.getSimpleName();

    private static final String PUBLIC_KEY_PARAMETER = "public_key";

    private final static String ENV_DEV = "DEV";
    private final static String ENV_PROD = "PROD";

    public static HashMap<String, Environment> environments = new HashMap<>();
    static {
        environments.put(ENV_DEV, new Environment("Development", "https://api.mercadopago.com/v1/"));
        environments.put(ENV_PROD, new Environment("Production", "https://api.mercadopago.com/v1/"));
    }

    private final Gson gson;
    private Environment currentEnvironment;

    public BaseService() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        currentEnvironment = getCurrentEnvironment();
    }

    public <T> T buildApi(Class<T> apiClass) {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(tokenInterceptor);
        httpClientBuilder.addInterceptor(logger);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(currentEnvironment.baseUrl)
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        return retrofitBuilder.build().create(apiClass);
    }

    private static HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
    static {
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC :  HttpLoggingInterceptor.Level.NONE);
    }

    private static Interceptor tokenInterceptor = chain -> {

        Request request = chain.request();

        // Let's add the public key parameter to all request
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter(PUBLIC_KEY_PARAMETER,BuildConfig.MERCADO_PAGO_PUBLIC_KEY)
                .build();

        request = request.newBuilder().url(url).build();

        return chain.proceed(request);
    };

    public static Environment getCurrentEnvironment() {
        String currentEnv = Settings.getCurrentEnvironment();

        // Setting Default Environment
        if (currentEnv == null) {
            currentEnv = BuildConfig.DEBUG ? ENV_DEV : ENV_PROD;
            Settings.setCurrentEnvironment(currentEnv);
        }
        return environments.get(currentEnv);
    }

    public static class Environment {
        public String name;
        public String baseUrl;

        public Environment(String name, String baseUrl) {
            this.name = name;
            this.baseUrl = baseUrl;
        }
    }

}
