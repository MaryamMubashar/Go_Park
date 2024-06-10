package com.example.gopark.apis;
import com.example.gopark.MainActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String BASE_URL = "http://192.168.137.1/MyParkingApp/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor authIntercepter= chain -> {
            return chain.proceed(chain.request().newBuilder().addHeader("session", MainActivity.session).build());
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(authIntercepter)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}

