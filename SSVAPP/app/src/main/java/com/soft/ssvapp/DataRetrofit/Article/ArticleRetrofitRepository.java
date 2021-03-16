package com.soft.ssvapp.DataRetrofit.Article;

import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleRetrofitRepository {

    private static ArticleRetrofitRepository instance;

    private ArticleConnexion articleConnexion;

    public static ArticleRetrofitRepository getInstance() {
        if (instance == null) {
            instance = new ArticleRetrofitRepository();
        }
        return instance;
    }

    public ArticleRetrofitRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        articleConnexion = retrofit.create(ArticleConnexion.class);

    }

    public ArticleConnexion articleConnexion()
    {
        return articleConnexion;
    }
}
