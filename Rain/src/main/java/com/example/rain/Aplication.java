package com.example.rain;

/**
 * Created by jonathanc on 9/9/13.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.rain.entities.Person;
import com.example.rain.utitilities.Database;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;

public class Aplication extends Application
{
    private SharedPreferences preferences;
    private Database databaseHelper = null;
    private Dao<Person, Integer> person = null;


    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseHelper = new Database(this);
    }

   // public File getVideosDir() {return videos_dir;}
    //public SharedPreferences getPreferences() {return preferences;}


    public Dao<Person, Integer> getContatoDao() throws SQLException {
        if (person == null) {
            person = databaseHelper.getDao(Person.class);
        }
        return person;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public  void ClearPerson() throws SQLException {
        databaseHelper.CleanPerson();
    }

}