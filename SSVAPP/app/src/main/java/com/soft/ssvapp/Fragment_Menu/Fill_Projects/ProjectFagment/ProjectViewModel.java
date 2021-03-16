package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.Application;
import android.net.IpSecManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.soft.ssvapp.Data.Entity_Project;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository repository;
    private LiveData<List<Entity_Project>> onGoingProjects;
    private LiveData<List<Entity_Project>> endProjects;


    public ProjectViewModel(Application application)
    {
        super(application);
        repository = new ProjectRepository(application);
        repository.UpdateListProjet();
        onGoingProjects = repository.getOnGoingProjects();
        endProjects = repository.getEnProjects();
    }

    public void init()
    {
        repository.UpdateListProjet();
    }

    public void insert(Entity_Project entity_project)
    {
        repository.insert(entity_project);
    }

    public void update(Entity_Project entity_project)
    {
        repository.update(entity_project);
    }

    public void update_custom(Entity_Project entity_project)
    {
        repository.update_custom(entity_project);
    }

    public void delete(Entity_Project entity_project)
    {
        repository.delete(entity_project);
    }

    public void deleteAllProjects()
    {
        repository.deleteAllProjects();
    }

    public LiveData<List<Entity_Project>> getOngoigProjects()
    {
        return onGoingProjects;
    }

    public LiveData<List<Entity_Project>> getEndProjects()
    {
        return endProjects;
    }

    public MutableLiveData<Boolean> getIsLoding()
    {
        return repository.getIsdownloaing();
    }
}
