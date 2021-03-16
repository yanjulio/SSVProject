package com.soft.ssvapp.DataRetrofit.Compte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CompteConnexion {

    @GET("api/Compte/ListDeComptes")
    Call<List<CompteReponse>> getCompteOnline();

    @GET("api/Compte/ListDeComptes")
//    Call<List<RapportCompteResponse>> compte_ajout_depense(@Query("groupeCompte") String groupeCompte);
    Call<List<RapportCompteResponse>> compte_ajout_depense();

    @GET("api/Compte/DernierCompte")
    Call<Integer> dernierCompte(@Query("GoupeCompte") String GoupeCompte);

    // pour les comptes
    @POST("api/Compte/Create")
    Call<Integer> addCompte(@Body RapportCompteResponse rapportCompteResponse);

    @POST("api/Compte/Moidifier")
    Call<Integer> modifierCompte(@Body RapportCompteResponse rapportCompteResponse);
}
