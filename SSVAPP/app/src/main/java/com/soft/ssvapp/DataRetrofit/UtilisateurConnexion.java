package com.soft.ssvapp.DataRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UtilisateurConnexion {

    @GET("api/Utilisateur/Login")
    Call<UtilisateurResponse> Login(
            @Query("nomUtilisateur") String NomUtilisateur,
            @Query("motDePasse") String MotDePassUtilisateur
    );

    @POST("/api/Utilisateur/Create")
    Call<Integer> creerUtilisateur(@Body UtilisateurResponse utilisateurResponse);

    @POST("/api/Utilisateur/Moidifier")
    Call<Integer> modifierUtilisateur(@Body UtilisateurResponse utilisateurResponse);

    @GET("api/Utilisateur/listedeCompteDetousLesUtilisateur")
    Call<List<UtilisateurResponse>> listeUtilisateur();
}
