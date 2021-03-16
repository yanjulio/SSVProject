package com.soft.ssvapp.DataRetrofit.ProjetRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProjetConnexion {

    @POST("api/Projet/Create")
    Call<Void> createProjet(@Body ProjetRetrofit projetRetrofit);

    @POST("api/Projet/ModifierProjet")
    Call<Boolean> modifierProjet(@Body ProjetRetrofit projetRetrofit);

    @GET("api/Projet/NombreDeProjet")
    Call<Integer> getNombreProjet();

    @GET("api/Projet/ToutLeProjetsEncours") //api/Projet/ToutLeProjetsEncours
    Call<List<ProjetRetrofit>> getToutLeProjetsEncours();


}
