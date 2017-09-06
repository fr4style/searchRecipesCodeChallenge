package com.fflorio.recipesdemo.network;

import com.fflorio.recipesdemo.BuildConfig;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class WS {

    protected static Retrofit createRetrofit(final OkHttpClient okHttpClient,
                                             final Executor executor, final String baseUrl){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .callbackExecutor(executor)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createGsonConverterFactory())
                .build();
        return retrofit;
    }

    protected static Executor createExecutor(){
        return Executors.newCachedThreadPool();
    }

    protected static Interceptor createInterceptor(){
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    protected static GsonBuilder getGsonBuilder(){
        final GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder;
    }

    protected static GsonConverterFactory createGsonConverterFactory(){
        return GsonConverterFactory.create(getGsonBuilder().create());
    }

    protected static OkHttpClient createOkHttpClient(final Interceptor interceptor,
            final int connectionTimeoutInSeconds, final int readTimeoutInSeconds){
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(readTimeoutInSeconds, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        if(BuildConfig.DEBUG){ builder.addInterceptor(interceptor); }

        return builder.build();
    }

    protected static Retrofit createDefaultRetrofitConfiguration(){
        final WSConfig wsConfig = new WSConfig.Builder().build();
        final Interceptor interceptor = createInterceptor();
        final OkHttpClient okHttpClient = createOkHttpClient(interceptor,
                wsConfig.connectionTimeoutInSeconds, wsConfig.readTimeoutInSeconds);
        final Executor executor = createExecutor();
        return createRetrofit(okHttpClient, executor, wsConfig.baseUrl);
    }
}
