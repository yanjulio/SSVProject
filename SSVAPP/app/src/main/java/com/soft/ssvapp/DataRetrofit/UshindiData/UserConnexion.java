package com.soft.ssvapp.DataRetrofit.UshindiData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserConnexion {

    @POST("api/Test/Create")
    Call<Void> insertTest(@Body UserResponse userResponse);
}
