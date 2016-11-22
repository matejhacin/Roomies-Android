package com.matejhacin.roomies.rest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matejhacin on 22/11/2016.
 */

public abstract class RCallback<T> implements Callback<T> {

    public RCallback() {}

    public abstract void onSuccess(T responseObject);
    public abstract void onFailure(Throwable t);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onFailure(null);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(t);
    }

}
