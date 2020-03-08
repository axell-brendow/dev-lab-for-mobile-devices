package com.example.starwarsapp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class People implements SimplifiedInformer, DetailedInformer
{
    String name;
    String height;
    String mass;
    String hair_color;
    String skin_color;
    String eye_color;
    String birth_year;
    String gender;
    String homeworld;
    List<String> films;
    List<String> species;
    List<String> vehicles;
    List<String> starships;
    String created;
    String edited;
    String url;

    @Override
    public void getDetailedPrimaryInfo(JsonObject jsonObject, Consumer<String> callback)
    {
        callback.accept(name);
    }

    @Override
    public void getDescription(JsonObject jsonObject, final Consumer<String> callback)
    {
        APIConsumer.getHomeWorld(homeworld,
            new Consumer<Planet>()
            {
                @Override
                public void accept(Planet planet)
                {
                    callback.accept(planet.name);
                }
            }
        );
    }

    @Override
    public void getInfoList(JsonObject jsonObject, Consumer<HashMap<String, String>> callback)
    {
        HashMap<String, String> infos = new HashMap<>();

        infos.put("Height", jsonObject.get("height").getAsString());
        infos.put("Mass", jsonObject.get("mass").getAsString());
        infos.put("Hair color", jsonObject.get("hair_color").getAsString());
        infos.put("Skin color", jsonObject.get("skin_color").getAsString());
        infos.put("Eye color", jsonObject.get("eye_color").getAsString());
        infos.put("Birth year", jsonObject.get("birth_year").getAsString());
        infos.put("Gender", jsonObject.get("gender").getAsString());

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
        callback.accept("Birth Year");
    }

    @Override
    public void getInfo1(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(birth_year);
    }

    @Override
    public void getInfo2Name(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept("Home World");
    }

    @Override
    public void getInfo2(JsonObject jsonObject, final Consumer<String> callback)
    {
        APIConsumer.getHomeWorld(jsonObject,
                new Consumer<Planet>()
                {
                    @Override
                    public void accept(Planet planet)
                    {
                        callback.accept(planet.name);
                    }
                }
        );
    }
}
