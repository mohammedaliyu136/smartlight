package com.mohammedaliyu.smartswitchlite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class LightRepository {

    private LightDao lightDao;
    private LiveData<List<Light>> allLights;

    public LightRepository(Application application){
        LightDatabase database = LightDatabase.getInstance(application);
        lightDao = database.lightDao();
        allLights = lightDao.getAll_Light();
    }

    public void insert(Light light){
        new InsertLightAsyncTask(lightDao).execute(light);
    }

    public void update(Light light){
        new UpdateLightAsyncTask(lightDao).execute(light);
    }

    public void delete(Light light){
        new DeleteLightAsyncTask(lightDao).execute(light);
    }


    public LiveData<List<Light>> getAllLights(){
        return allLights;
    }

    private static class UpdateLightAsyncTask extends AsyncTask<Light, Void, Void> {
        private LightDao lightDao;
        private UpdateLightAsyncTask(LightDao lightDao){
            this.lightDao = lightDao;
        }
        @Override
        protected Void doInBackground(Light... lights) {
            lightDao.update(lights[0]);
            return null;
        }
    }
    private static class InsertLightAsyncTask extends AsyncTask<Light, Void, Void> {
        private LightDao lightDao;
        private InsertLightAsyncTask(LightDao lightDao){
            this.lightDao = lightDao;
        }
        @Override
        protected Void doInBackground(Light... lights) {
            lightDao.insert(lights[0]);
            return null;
        }
    }
    private static class DeleteLightAsyncTask extends AsyncTask<Light, Void, Void> {
        private LightDao lightDao;
        private DeleteLightAsyncTask(LightDao lightDao){
            this.lightDao = lightDao;
        }
        @Override
        protected Void doInBackground(Light... lights) {
            lightDao.delete(lights[0]);
            return null;
        }
    }


}
