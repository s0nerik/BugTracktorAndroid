package com.github.sonerik.bugtracktor.di.modules;

import android.app.Application;

import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.api.BugTracktorWebService;
import com.github.sonerik.bugtracktor.prefs.MainPrefs;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by sonerik on 5/28/16.
 */
@Module
public class WebServiceModule {
    private static final String BASE_URL = "https://bug-tracktor.herokuapp.com/v1/";

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, MainPrefs prefs) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    String token = prefs.getToken();
                    if (!token.isEmpty()) {
                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                                                 .header("api_key", token)
                                                                 .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    } else {
                        return chain.proceed(original);
                    }
                })
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    BugTracktorWebService provideBugTracktorService(Retrofit retrofit) {
        return retrofit.create(BugTracktorWebService.class);
    }

    @Provides
    BugTracktorApi provideBugTracktorApi(BugTracktorWebService service,
                                         Application context,
                                         MainPrefs prefs) {
        return new BugTracktorApi(service, context.getCacheDir(), prefs);
    }
}
