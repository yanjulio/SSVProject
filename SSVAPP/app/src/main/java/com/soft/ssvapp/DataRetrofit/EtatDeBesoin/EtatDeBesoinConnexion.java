package com.soft.ssvapp.DataRetrofit.EtatDeBesoin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EtatDeBesoinConnexion {

    @POST("api/EtatDeBesoin/Create")
    Call<EtatDeBesoinRetrofit> createEtatDeBesoin(@Body EtatDeBesoinRetrofit etatDeBesoinRetrofit,
                                                  @Query("InitialUtilisateur") String initial_nom);

    @GET("api/EtatDeBesoin/ListEtatDeBesoins")
    Call<List<EtatDeBesoinRetrofit>> getEtatDeBesoin();

    @POST("api/EtatDeBesoin/Modifier")
    Call<Void> modifierEtatDeBesoin(@Body EtatDeBesoinRetrofit etatDeBesoinRetrofit,
                                    @Query("codeEtatdeBesoin") String codeEtatdeBesoin);

    @GET("api/EtatDeBesoin/DernierEtatBesoin")
    Call<Integer> getDernierEtatDeBesoin();

    @GET("api/EtatDeBesoin/EtatDeBesoinsValider")
    Call<List<EtatDeBesoinRetrofit>> getEtatDeBesoinValider();

    @GET("api/EtatDeBesoin/EtatDeBesoinsValiderParProjet")
    Call<List<EtatDeBesoinRetrofit>> getEtatDeBesoinValiderParProjet(@Query("codeProjet") String codeProjet);

    @GET("api/EtatDeBesoin/SupprimerEtatBesion")
    Call<Boolean> supprimerEtatBesoin(@Query("codeEtaBesion") String CodeEtaBesion);

}
