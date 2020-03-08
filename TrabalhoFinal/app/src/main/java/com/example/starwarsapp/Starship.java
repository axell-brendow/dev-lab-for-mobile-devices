package com.example.starwarsapp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Starship implements SimplifiedInformer, DetailedInformer
{
    String name;
    String model;
    String manufacturer;
    String cost_in_credits;
    String length;
    String max_atmosphering_speed;
    String crew;
    String passengers;
    String cargo_capacity;
    String consumables;
    String hyperdrive_rating;
    String MGLT;
    String starship_class;
    List<String> pilots;
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
        callback.accept(model);
    }

    @Override
    public void getInfoList(JsonObject jsonObject, Consumer<HashMap<String, String>> callback)
    {
        HashMap<String, String> infos = new HashMap<>();

        infos.put("Manufacturer", jsonObject.get("manufacturer").getAsString());
        infos.put("Cost in credits", jsonObject.get("cost_in_credits").getAsString());
        infos.put("Length", jsonObject.get("length").getAsString());
        infos.put("Max atmosphering speed", jsonObject.get("max_atmosphering_speed").getAsString());
        infos.put("Crew", jsonObject.get("crew").getAsString());
        infos.put("Passengers", jsonObject.get("passengers").getAsString());
        infos.put("Cargo capacity", jsonObject.get("cargo_capacity").getAsString());
        infos.put("Consumables", jsonObject.get("consumables").getAsString());
        infos.put("Hyperdrive rating", jsonObject.get("hyperdrive_rating").getAsString());
        infos.put("MGLT", jsonObject.get("MGLT").getAsString());
        infos.put("Class", jsonObject.get("starship_class").getAsString());

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
        callback.accept("Cost in Credits");
    }

    @Override
    public void getInfo1(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(cost_in_credits);
    }

    @Override
    public void getInfo2Name(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept("Class");
    }

    @Override
    public void getInfo2(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(starship_class);
    }
}
