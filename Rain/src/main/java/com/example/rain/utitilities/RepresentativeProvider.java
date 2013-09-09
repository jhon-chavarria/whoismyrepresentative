package com.example.rain.utitilities;


import com.example.rain.Aplication;
import com.example.rain.entities.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepresentativeProvider {

    private WebService mainfeed;


    public List<Person> getData(String url, Aplication dtoFactory,String type) throws SQLException {

        Dao<Person, Integer> person = dtoFactory.getContatoDao();

        ArrayList<Person> representative = new ArrayList<Person>();

        mainfeed = new WebService(url);

        Map<String, String> params = new HashMap<String, String>();

        String response = mainfeed.webGet("", params);

        JSONObject json;

        try {
            json = new JSONObject(response.toString());
            JSONArray venues = json.getJSONArray("results");
            JSONObject JSONObj;
            Person r;
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();

            for (int i = 0; i < venues.length(); i++) {
                JSONObj = (JSONObject) venues.get(i);
                r = gson.fromJson(JSONObj.toString(), Person.class);
                r.setType(type);
                representative.add(r);
                person.create(r);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return representative;
    }

}
