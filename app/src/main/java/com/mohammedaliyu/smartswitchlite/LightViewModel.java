package com.mohammedaliyu.smartswitchlite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LightViewModel extends AndroidViewModel {

    private LightRepository repository;
    private LiveData<List<Light>> allLights;

    public LightViewModel(@NonNull Application application) {
        super(application);
        repository = new LightRepository(application);
        allLights = repository.getAllLights();
    }
    public void insert(Light light){
        repository.insert(light);
    }
    public void update(Light light){
        repository.update(light);
    }
    public void delete(Light light){
        repository.delete(light);
    }
    public LiveData<List<Light>> getAllLights(){
        return allLights;
    }
}
