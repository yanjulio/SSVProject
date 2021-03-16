package com.soft.ssvapp.DataRetrofit.Rapport;

import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RapportConnexion {

    @GET("api/Balance/GetlaBalance")
    Call<List<RapportResponse>> balance();

    @GET("api/Balance/GetlaBalancedeGroupe")
    Call<List<RapportResponse>> balanceDeGroupe();

    @GET("api/Balance/GetlaBalanceParGoupe")
    Call<List<RapportResponse>> balanceParGroupe(@Query("groupeCompte") String groupeCompte);

    @GET("api/Balance/GetDetail")
    Call<List<DetailRapportResponse>> balanceDetail(@Query("numCompte") int numCompte,
                                              @Query("date1") String date1,
                                              @Query("date2") String date2);

    @GET("api/Projet/Suiviedeprojet")
    Call<List<RapportProjetResponse>> plan_suivi_projet();

    @GET("api/Ligne/LignesConsommatioParProjet")
    Call<List<RapportDetailProjetResponse>> rapportDetailsuiviProjet(@Query("codeProjet") String codeProjet);

    @GET("api/EtatDeBesoin/ListeEtatBesionParPeriode")
    Call<List<RapportEtatBesoinResponse>> listEtatBesoinParPeriode(@Query("date1") String date1,
                                                                   @Query("date2") String date2);

    @GET("api/Operation/GetDetail")
    Call<List<RapportOperationResponse>> rapportDetailOperation(@Query("NumeroOperation") String NumeroOperation);

    @GET("api/Operation/supprimerOperation")
    Call<Integer> supprimerOperation(@Query("NumeOperation") String NumeOperation);

    //Modifier compte mais je met ca ici
    @POST("api/Compte/Moidifier")
    Call<Integer> modifierCompte(@Body RapportCompteResponse rapportCompteResponse);
}
