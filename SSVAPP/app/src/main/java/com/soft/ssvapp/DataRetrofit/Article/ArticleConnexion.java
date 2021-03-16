package com.soft.ssvapp.DataRetrofit.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ArticleConnexion {

    @GET("api/Article/ListDesArticles")
    Call<List<ArticleRespone>> getArticle();

    @POST("api/Article/AjouterArticle")
    Call<Void> ajouterArticle(@Body ArticleRespone articleRespone);

    @POST("api/Article/ModifierArticle")
    Call<Boolean> modifierArticle(@Body ArticleRespone articleRespone);

    @POST("api/Article/EffacerArticle")
    Call<Boolean> effacerArticle(@Body ArticleRespone articleRespone);

    @GET("api/Article/DernierArticle")
    Call<Integer> dernierArticle();
}
