package com.example.starwarsapp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Planet implements SimplifiedInformer, DetailedInformer
{
    String name;
    String rotation_period;
    String orbital_period;
    String diameter;
    String climate;
    String gravity;
    String terrain;
    String surface_water;
    String population;
    List<String> residents;
    List<String> films;
    String created;
    String edited;
    String url;

    @Override
    public void getDetailedPrimaryInfo(JsonObject jsonObject, Consumer<String> callback)
    {
        callback.accept(name);
    }

    @Override
    public void getDescription(JsonObject jsonObject, Consumer<String> callback)
    {
        callback.accept(terrain);
    }

    @Override
    public void getInfoList(JsonObject jsonObject, Consumer<HashMap<String, String>> callback)
    {
        HashMap<String, String> infos = new HashMap<>();

        infos.put("Rotation period", jsonObject.get("rotation_period").getAsString());
        infos.put("Orbital period", jsonObject.get("orbital_period").getAsString());
        infos.put("Diameter", jsonObject.get("diameter").getAsString());
        infos.put("Climate", jsonObject.get("climate").getAsString());
        infos.put("Gravity", jsonObject.get("gravity").getAsString());
        infos.put("Surface water", jsonObject.get("surface_water").getAsString());
        infos.put("Population", jsonObject.get("population").getAsString());

        callback.accept(infos);
    }

    @Override
    public void getPrimaryInfo(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(name);
    }

    @Override
    public void getInfo1Name(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept("Terrain");
    }

    @Override
    public void getInfo1(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(terrain);
    }

    @Override
    public void getInfo2Name(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept("Population");
    }

    @Override
    public void getInfo2(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(population);
    }
}
