package com.example.starwarsapp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Film implements SimplifiedInformer, DetailedInformer
{
    String title;
    int episode_id;
    String opening_crawl;
    String director;
    String producer;
    String release_date;
    List<String> characters;
    List<String> planets;
    List<String> starships;
    List<String> vehicles;
    List<String> species;
    String created;
    String edited;
    String url;

    @Override
    public void getDetailedPrimaryInfo(JsonObject jsonObject, Consumer<String> callback)
    {
        callback.accept(title);
    }

    @Override
    public void getDescription(JsonObject jsonObject, Consumer<String> callback)
    {
        callback.accept(opening_crawl);
    }

    @Override
    public void getInfoList(JsonObject jsonObject, Consumer< HashMap<String, String> > callback)
    {
        HashMap<String, String> infos = new HashMap<>();

        infos.put("Director", jsonObject.get("director").getAsString());
        infos.put("Producer", jsonObject.get("producer").getAsString());
        infos.put("Release Date", jsonObject.get("release_date").getAsString());

        callback.accept(infos);
    }

    @Override
    public void getPrimaryInfo(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(title);
    }

    @Override
    public void getInfo1Name(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept("Director");
    }

    @Override
    public void getInfo1(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(director);
    }

    @Override
    public void getInfo2Name(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept("Release Date");
    }

    @Override
    public void getInfo2(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(release_date);
    }
}
