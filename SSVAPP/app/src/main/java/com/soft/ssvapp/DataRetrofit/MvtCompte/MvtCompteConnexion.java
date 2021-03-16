package com.soft.ssvapp.DataRetrofit.MvtCompte;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MvtCompteConnexion {

    @POST("api/MvtCompte/Create")
    Call<Void> createMvtCompte(@Body MvtCompteResponse mvtCompteResponse);

    @GET("api/MvtCompte/DernierMvtCompte")
    Call<Integer> dernierCompte();
}
