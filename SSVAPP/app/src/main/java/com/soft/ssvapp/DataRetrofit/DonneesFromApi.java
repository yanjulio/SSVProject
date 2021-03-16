package com.soft.ssvapp.DataRetrofit;

import android.content.Context;
import android.util.Log;

public class DonneesFromApi extends ConnexionApi {

    String url = "";
    String response = "";
    Context context;
    public static String serveur = "kapaapi.afri-soft.com";
//    public static String serveur = "192.168.0.107/apiVH";

    public DonneesFromApi(Context context){
        this.context = context;
    }



    public String tUtilisateur(String nomUt, String motDePasse)
    {
        try{
            url = "http://192.168.0.108/ApisKpBatiment2/api/Utilisateur/Login?nomUtilisateur=STEVE&motDePasse=123";
//            url = "http://192.168.0.100/Musosa/api/tUtilisateur/login?nomUtilisateur=STEVE&motDePasse=5678";
            response = call(url);
        }catch (Exception e)
        {
            Log.e("Error connecting", e.toString());
        }
        return response;
    }
}