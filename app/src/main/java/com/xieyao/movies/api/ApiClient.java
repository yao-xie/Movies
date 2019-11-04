package com.xieyao.movies.api;

import com.xieyao.movies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xieyao on 2019-10-11.
 */
public class ApiClient {

    private static final String API_KEY = "api_key";

    private static volatile ApiClient mInstance;

    private TmdbApi api;


    private ApiClient() throws Exception{
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl httpUrl = original.url()
                                .newBuilder()
                                .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                                .build();

                        original = original.newBuilder()
                                .url(httpUrl)
                                .build();

                        return chain.proceed(original);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(TmdbApi.class);
    }

    public static ApiClient getInstance() throws Exception{
        if (mInstance == null) {
            synchronized (ApiClient.class) {
                if (mInstance == null) {
                    mInstance = new ApiClient();
                }
            }
        }
        return mInstance;
    }

    public TmdbApi getService() {
        return api;
    }
}
