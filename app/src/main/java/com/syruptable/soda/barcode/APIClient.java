package com.syruptable.soda.barcode;

import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private final String URL_DEFAULT = "https://biz.sodawallet.co.kr/";
    private OkHttpClient okHttpClient;
    public APIService APIService;


    public interface LogListener {
        void setLog(String s);
    }

    private LogListener logListener;

    public APIClient(LogListener logListener) {

        this.logListener = logListener;

        if (okHttpClient == null) {
            okHttpClient = setOkHttpClient();
        }

        APIService = new Retrofit.Builder()
                .baseUrl(URL_DEFAULT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(APIService.class);

    }

    private OkHttpClient setOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                Log.d("okhttp", s);
                if (logListener != null) {
                    logListener.setLog(s);
                }
            }
        });

//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
//
//                if(!TextUtils.isEmpty(TokenCode.APPLICATION_KEY)){
//                    Request newRequest = chain.request().newBuilder().addHeader("Application-Key", TokenCode.APPLICATION_KEY).build();
//                    return chain.proceed(newRequest);
//                }
//
//                return  chain.proceed(chain.request().newBuilder().build());
//            }
//        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }
}
