package com.soft.ssvapp.DataRetrofit.RapportParProjet;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RapportParProjetConnexion {

    // MEGER OF THE SOME QUESRIES
    @GET("api/RapportParProjet/SuiviedeprojetPourUnProjet")
    Observable<RapportProjet> rapportProjetResponse(@Query("codeProjet") String codeProjet);

    @GET("api/RapportParProjet/AfficherGrandlivre")
    Observable<RapportGrandLivre> rapportGrandLivreResponse(@Query("Compte") int Compte,
                                                          @Query("date1") String date1,
                                                          @Query("date2") String date2);

    @GET("api/RapportParProjet/AfficherRapportEtabesoinValideetDecaisse")
    Observable<RapportEtatBesoin> rapportEBResponse(@Query("codeProjet") String codeProjet,
                                                          @Query("date1") String date1,
                                                          @Query("date2") String date2);

    @GET("api/RapportParProjet/AfficherRapportEtabesoinNonValide")
    Observable<Double> rapportEBNonValideResponse(@Query("codeProjet") String codeProjet,
                                                     @Query("date1") String date1,
                                                     @Query("date2") String date2);


    // RAPPORT PROJET

    @GET("api/RapportParProjet/SuiviedeprojetPourUnProjet")
    Call<RapportProjet> rapportResponseCallParProjet(@Query("codeProjet") String codeProjet);

    @GET("api/RapportParProjet/AfficherGrandlivre")
    Call<RapportGrandLivre> rapportResponseCallGrandLivre(@Query("Compte") int Compte,
                                                          @Query("date1") String date1,
                                                          @Query("date2") String date2);

    @GET("api/RapportParProjet/AfficherRapportEtabesoinValideetDecaisse")
    Call<RapportEtatBesoin> rapportResponseCallEtatBesoin(@Query("codeProjet") String codeProjet,
                                                          @Query("date1") String date1,
                                                          @Query("date2") String date2);

    @GET("api/RapportParProjet/AfficherRapportEtabesoinNonValide")
    Call<Double> rapportResponseCallSommeEBNonValide(@Query("codeProjet") String codeProjet,
                                                     @Query("date1") String date1,
                                                     @Query("date2") String date2);


    // RAPPORT ARTICLES
    @GET("api/RapportDesArticleEtLigne/ListeDesArticlesConsomesParProjet")
    Call<List<RapportArticleResponse>> rapportResponseCallArticle (@Query("codeProjet") String codeProjet);

    @GET("api/RapportDesArticleEtLigne/ListeDesLigneParRapportAuxArticle")
    Call<List<RapportLigneParArticleResponse>> rapportLigneParArticle(@Query("codeProjet") String codeProjet,
                                                                      @Query("CodeArticle") String CodeArticle);

    @GET("api/RapportDesArticleEtLigne/ListeDesArticleParRapportAuxLIGNE")
    Call<List<RapportArticleParLigneResponse>> rapportArticleParLigne(@Query("codeProjet") String codeProjet,
                                                                      @Query("CodeLigne") String codeLigne);

    @GET("api/RapportDesArticleEtLigne/ListeReferenEtatBesionConsome")
    Call<List<RapportReferenceEtatBesoinConsomme>> rapportReferenceEtatBesoinConsomme(@Query("codeProjet") String codeProjet,
                                                                                      @Query("CodeArticle") String codeArticle,
                                                                                      @Query("CodeLigne") String codeLigne);

    @GET("api/EtatDeBesoin/ListeEtatBesionsSommeValideEtDecaisse")
    Call<List<RapportEBParProjetValideEtDecaisse>> rapportEBParProjetValideEtDecaisse(@Query("codeProjet") String codeProjet,
                                                                                      @Query("Etat") int Etat);
}
