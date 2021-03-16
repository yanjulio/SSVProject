package com.soft.ssvapp.DataRetrofit.Operation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OperationConnexion {

    @POST("api/Operation/Create")
    Call<Void> createOperation(@Body OperationRetrofit operationRetrofit);

    @GET("api/Operation/DernierOperation")
    Call<Integer> dernierOperation();
}
