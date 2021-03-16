package com.soft.ssvapp.Fragment_Menu.Fill_Operation.True_Operations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.soft.ssvapp.Data.Entity_Operation;

public class OperationViewModel extends AndroidViewModel {

    private OperationRepository operationRepository;
    public OperationViewModel(@NonNull Application application) {
        super(application);
        operationRepository = new OperationRepository(application);
    }

    public void insert(Entity_Operation entity_operation)
    {
        operationRepository.insert(entity_operation);
    }
}
