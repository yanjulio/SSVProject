package com.soft.ssvapp.DataRetrofit.GroupeCompte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GroupeCompteConnexion {

    @GET("api/GroupeCompte/ListDeGroupe")
    Call<List<GroupeCompteResponse>> getGroupeCompte();
}
