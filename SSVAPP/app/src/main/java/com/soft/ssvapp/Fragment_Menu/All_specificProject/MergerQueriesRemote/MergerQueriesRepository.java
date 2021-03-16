package com.soft.ssvapp.Fragment_Menu.All_specificProject.MergerQueriesRemote;

import android.app.Application;
import android.text.style.IconMarginSpan;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Observer;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEtatBesoin;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportGrandLivre;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportParprojetRetrofitRepository;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportProjet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MergerQueriesRepository {

    RapportParprojetRetrofitRepository rapportParprojetRetrofitRepository;
    private Application application;
    private MutableLiveData<RapportProjet> rapportProjet;
    private MutableLiveData<RapportEtatBesoin> rapportEb;
    private MutableLiveData<Double> rapportEbNonValide;
    private MutableLiveData<RapportGrandLivre> rapportGrandLivreEtClient;
    private MutableLiveData<Boolean> isLoading;

    public MergerQueriesRepository(Application application)
    {
        this.application = application;
        rapportParprojetRetrofitRepository = RapportParprojetRetrofitRepository.getInstance();
        this.rapportProjet = new MutableLiveData<>();
        this.rapportEb = new MutableLiveData<>();
        this.rapportEbNonValide = new MutableLiveData<>();
        this.rapportGrandLivreEtClient = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
    }

    public LiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public LiveData<RapportProjet> getRapportParProjet()
    {
        return rapportProjet;
    }

    public LiveData<RapportEtatBesoin> getRapporteEB()
    {
        return rapportEb;
    }

    public LiveData<RapportGrandLivre> getRapportGrandLivre()
    {
        return rapportGrandLivreEtClient;
    }

    public LiveData<Double> getRapportEBNonValide()
    {
        return rapportEbNonValide;
    }

    public void setMergerQueries(String codeProjet, int compte_client, int compte_projet, String date1, String date2)
    {
        List<Observable<?>> requests = new ArrayList<>();
        requests.add(rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportProjetResponse(codeProjet));
        requests.add(rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportEBResponse(codeProjet, date1, date2));
        requests.add(rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportEBNonValideResponse(codeProjet, date1, date2));
        requests.add(rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportGrandLivreResponse(compte_projet, date1, date2));

        Observable.zip(requests, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) throws Exception {
//                isLoading.postValue(true);
                return new Object();
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
//                isLoading.postValue(false);
                rapportProjet.postValue((RapportProjet)o);
                rapportEb.postValue((RapportEtatBesoin)o);
                rapportEbNonValide.postValue((double)o);
                rapportGrandLivreEtClient.postValue((RapportGrandLivre)o);
            }
        },
        new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Exception {
//                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion groupe queries", Toast.LENGTH_LONG).show();
            }
        });
    }
}
