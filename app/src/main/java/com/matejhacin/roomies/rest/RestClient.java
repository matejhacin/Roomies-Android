package com.matejhacin.roomies.rest;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        roomiesService = retrofit.create(RoomiesService.class);
    }

}
