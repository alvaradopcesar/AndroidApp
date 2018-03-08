package iosco.app.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.Map;

import iosco.app.model.entity.File;
import iosco.app.utils.CustomImageDownaloder;
import iosco.app.utils.Global;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by usuario on 16/02/2016.
 */
public class ApiImplementation {
//    private static String baseUrl = "http://www.iosco2016lima.pe:96/";
    private static String baseUrl = "http://181.65.157.206:96/";


    public static String getBaseUrl(){
        return baseUrl;
    }
    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

    private static OkHttpClient picassoClient = new OkHttpClient();

    public static ApiService getService(){
        return retrofit.create(ApiService.class);
    }


    public static void configImageLoader(Context context){
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", Global.getUserToken(context));

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
        .extraForDownloader(headers)
        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .imageDownloader(new CustomImageDownaloder(context))
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .build();


        ImageLoader.getInstance().init(config);

    }


    /*public static Picasso getServiceImage(final Context context){
        picassoClient.networkInterceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization",Global.getUserToken(context))
                        .build();
                Log.i("respuesta6","networkInterceptors()");
                return chain.proceed(newRequest);
            }
        });
        picassoClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization",Global.getUserToken(context))
                        .build();
                Log.i("respuesta6","interceptors()");
                return chain.proceed(newRequest);
            }
        });

        //Picasso p = new Picasso.Builder(context).downloader(new OkHttpDownloader(picassoClient)).build();
        //p.setIndicatorsEnabled(true);
        //p.setDebugging(true);

        return new Picasso.Builder(context).downloader(new OkHttpDownloader(picassoClient)).build();
    }*/



}
