package com.soft.ssvapp.DataRetrofit.Ligne;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LigneConnexion {

    @POST("api/Ligne/Create")
    Call<Void> createLigne(@Body LigneRetrofit ligneRetrofit);

    @POST("api/Ligne/Update")
    Call<Boolean> updateLigne(@Body LigneRetrofit ligneRetrofit);

    @POST("api/Ligne/Delete")
    Call<Boolean> deleteLigne(@Body LigneRetrofit ligneRetrofit);

    @GET("api/Ligne/ToutesLesLignes")
    Call<List<LigneRetrofit>> getToutesLesLignes();

    @GET("api/Ligne/LignesParCodeProjet")
    Call<List<LigneRetrofit>> getLignesParProjet(@Query("codeProjet") String codeProjet);
}
