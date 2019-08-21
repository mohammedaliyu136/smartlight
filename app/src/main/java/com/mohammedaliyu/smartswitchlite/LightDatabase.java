package com.mohammedaliyu.smartswitchlite;

import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Light.class, version = 1)
public abstract class LightDatabase extends RoomDatabase {

    private static LightDatabase instance;

    public  abstract LightDao lightDao();

    public static synchronized LightDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LightDatabase.class, "light_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private LightDao lightDao;
        private PopulateDbAsyncTask(LightDatabase db){
            lightDao = db.lightDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            /*
            lightDao.insert(new Light("Living room Light", "1", true, "12345678"));
            lightDao.insert(new Light("Living room Light", "1", true, "12345678"));
            lightDao.insert(new Light("Living room Light", "1", true, "12345678"));
            lightDao.insert(new Light("Living room Light", "1", true, "12345678"));
            lightDao.insert(new Light("Living room Light", "1", true, "12345678"));
            */
            return null;
        }
    }

}
