package com.github.sonerik.bugtracktor.di.modules;

import android.app.Application;

import com.cloudinary.Cloudinary;
import com.github.sonerik.bugtracktor.api.BugTracktorApi;
import com.github.sonerik.bugtracktor.api.BugTracktorWebService;
import com.github.sonerik.bugtracktor.models.Token;
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
import okhttp3.Response;
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
//    private static final String BASE_URL = "https://bug-tracktor.herokuapp.com/v1/";
    private static final String BASE_URL = "http://192.168.1.106:10010/v1/";

    private static final String CLOUDINARY_URL = "cloudinary://838498517918848:dh_xITvo3G_4y39whY7wGjz7YHs@s0nerik";

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
                .retryOnConnectionFailure(true)
                .readTimeout(3, TimeUnit.MINUTES)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    String token = prefs.getToken();
                    Response response;
                    if (!token.isEmpty()) {
                        // Request customization: add request headers
                        Request request = original.newBuilder()
                                                  .header("api_key", token)
                                                  .method(original.method(), original.body())
                                                  .build();
                        response = chain.proceed(request);

                        // Handle invalid token case (tokens are valid only for 6 hours)
                        if(response.code() == 403 && !prefs.getEmail().isEmpty() && !prefs.getPassword().isEmpty()) {
                            Request tokenRequest = new Request.Builder()
                                    .get()
                                    .url(BASE_URL + "get_token")
                                    .addHeader("email", prefs.getEmail())
                                    .addHeader("password", prefs.getPassword())
                                    .build();
                            Response tokenResponse = chain.proceed(tokenRequest);
                            if (tokenResponse.isSuccessful()) {
                                Token newToken = new Gson().fromJson(tokenResponse.body().string(), Token.class);
                                prefs.setToken(newToken.getToken());

                                // Proceed with original request
                                request = original.newBuilder()
                                                  .header("api_key", newToken.getToken())
                                                  .method(original.method(), original.body())
                                                  .build();
                                response = chain.proceed(request);
                            }
                        }
                    } else {
                        response = chain.proceed(original);
                    }

                    return response;
                })
// TODO: enable cache after finishing testing
//                .cache(cache)
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
    BugTracktorApi provideBugTracktorApi(
            BugTracktorWebService service,
            Application context,
            MainPrefs prefs,
            Cloudinary cloudinary
    ) {
        return new BugTracktorApi(service, context.getCacheDir(), prefs, cloudinary);
    }

    @Provides
    @Singleton
    Cloudinary provideCloudinary() {
        return new Cloudinary(CLOUDINARY_URL);
    }
}
