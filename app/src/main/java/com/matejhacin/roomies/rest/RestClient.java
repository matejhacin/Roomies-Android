package com.matejhacin.roomies.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by matejhacin on 22/11/2016.
 */
public class RestClient {

    private static String BASE_URL = "http://lazykiller.herokuapp.com/";
    private static RestClient restClient = new RestClient();

    public RoomiesService roomiesService;

    // MARK: Singleton

    public static RestClient getInstance() {
        return restClient;
    }

    private RestClient() {
        initService();
    }

    // MARK: Private

    private void initService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        roomiesService = retrofit.create(RoomiesService.class);
    }

}
