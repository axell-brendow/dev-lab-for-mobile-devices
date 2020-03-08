package com.example.starwarsapp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Species implements SimplifiedInformer, DetailedInformer
{
    String name;
    String classification;
    String designation;
    String average_height;
    String skin_colors;
    String hair_colors;
    String eye_colors;
    String average_lifespan;
    String homeworld;
    String language;
    List<String> people;
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
        callback.accept(classification);
    }

    @Override
    public void getInfoList(final JsonObject jsonObject, final Consumer<HashMap<String, String>> callback)
    {
        final HashMap<String, String> infos = new HashMap<>();

        infos.put("Designation", jsonObject.get("designation").getAsString());
        infos.put("Average height", jsonObject.get("average_height").getAsString());
        infos.put("Skin colors", jsonObject.get("skin_colors").getAsString());
        infos.put("Hair colors", jsonObject.get("hair_colors").getAsString());
        infos.put("Eye colors", jsonObject.get("eye_colors").getAsString());
        infos.put("Average lifespan", jsonObject.get("average_lifespan").getAsString());
        infos.put("Language", jsonObject.get("language").getAsString());

        APIConsumer.getHomeWorld(jsonObject,
            new Consumer<Planet>()
            {
                @Override
                public void accept(Planet planet)
                {
                    infos.put("Homeworld", planet.name);
                    callback.accept(infos);
                }
            }
        );
    }

    @Override
    public void getPrimaryInfo(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(name);
    }

    @Override
    public void getInfo1Name(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept("Classification");
    }

    @Override
    public void getInfo1(JsonObject jsonObject, final Consumer<String> callback)
    {
        callback.accept(classification);
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
