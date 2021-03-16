package com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin;

import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitDetailEB;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DetailsEtatDeBesoinConnexion {

    @POST("api/DetailEtatDeBesoin/Create")
    Call<Void> createDetailsEtatDeBesoin(@Body DetailsEtatDeBesoinRetrofit detailsEtatDeBesoinRetrofit);

    @POST("api/DetailEtatDeBesoin/ModifierDetailEtatDeBesoin")
    Call<Boolean> modifierDetailsEtatBesoin(@Body DetailsEtatDeBesoinRetrofit detailsEtatDeBesoinRetrofit);

    @POST("api/DetailEtatDeBesoin/EffacerDetailEtatDeBesoin")
    Call<Boolean> effaceDetailEtatBesoin(@Body DetailsEtatDeBesoinRetrofit detailsEtatDeBesoinRetrofit);

    @GET("api/DetailEtatDeBesoin/DernierDetailEtatDeBesoin")
    Call<Integer> dernierDetailEtatDeBesoin();

    @GET("api/DetailEtatDeBesoin/ListDetailEtatDeBesoins")
    Call<List<DetailsEtatDeBesoinRetrofit>> getListDetailsBesoin(
            @Query("codeEtatBesoin") String codeEtatBesoin);

    @GET("api/DetailEtatDeBesoin/ListDetailDecaissementEtatDeBesion")
    Call<List<EtatDeBesoinRetrofitDetailEB>> detailMvtEb(@Query("codeEtatBesoin") String codeEtatBesoin);
}
