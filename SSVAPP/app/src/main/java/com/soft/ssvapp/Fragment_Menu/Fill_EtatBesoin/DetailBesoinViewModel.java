package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_DetailBesoinWithEntity_Article;

import java.util.List;

public class DetailBesoinViewModel extends AndroidViewModel {

    private DetailBesoinRepository detailBesoinRepository;
//    private LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> detailBesoinLiveData;
    private LiveData<List<Entity_DetailBesoinWithEntity_Article>> detailBesoinArticleLiveData;
    private List<Entity_DetailBesoinWithEntity_Article> list_toputOnline;
    public DetailBesoinViewModel(@NonNull Application application) {
        super(application);
        detailBesoinRepository = new DetailBesoinRepository(application);
    }

    public void init(String codeEtatDeBesoin)
    {
        list_toputOnline = detailBesoinRepository.getDetailsBesoinList(codeEtatDeBesoin);
//        detailBesoinLiveData = detailBesoinRepository.getDetailsBesoin(codeEtatDeBesoin);
        detailBesoinArticleLiveData = detailBesoinRepository.getDetailBesoinArticle(codeEtatDeBesoin);
    }

    public void insertDetails(Entity_DetailBesoin entity_detailBesoin)
    {
        detailBesoinRepository.insert(entity_detailBesoin);
    }

    public int updateDetail(Entity_DetailBesoin entity_detailBesoin)
    {
        detailBesoinRepository.update(entity_detailBesoin);
        return 1;
    }

    public void updateDetailOnline(Entity_DetailBesoin entity_detailBesoin)
    {
        detailBesoinRepository.updateDetailOnline(entity_detailBesoin);
    }

    public void deleteOnline(Entity_DetailBesoin entity_detailBesoin)
    {
        detailBesoinRepository.deleteDetailOnline(entity_detailBesoin);
    }

    public int deleteDetails(Entity_DetailBesoin entity_detailBesoin)
    {
        detailBesoinRepository.deleteDetail(entity_detailBesoin);
        return 1;
    }

    public void deleteAllDetails()
    {
        detailBesoinRepository.deleteAll();
    }

//    public LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> getDetailBesoinLiveData()
//    {
//        return detailBesoinLiveData;
//    }

    public LiveData<List<Entity_DetailBesoinWithEntity_Article>> getDetailBesoinArticleLiveData()
    {
//        return detailBesoinRepository.getDetailBesoinArticle();
        return detailBesoinArticleLiveData;
    }

    public MutableLiveData<Boolean> isLoaging()
    {
        return detailBesoinRepository.isLoading();
    }

    public List<Entity_DetailBesoinWithEntity_Article> getList_toputOnline()
    {
        return list_toputOnline;
    }
}
