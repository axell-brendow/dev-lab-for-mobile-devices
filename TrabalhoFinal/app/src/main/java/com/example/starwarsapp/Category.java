package com.example.starwarsapp;

import com.google.gson.JsonArray;

import java.util.List;

public class Category
{
    private int count;
    private String next;
    private String previous;
    private JsonArray results;
    private String name;
    private int thumbnail;
    private int page;
    private String url;
    private Class<? extends SimplifiedInformer> itemClass;

    public Category()
    {
    }

    public Category(
        int count, String next, String previous, JsonArray results, String name,
        int thumbnail, int page, String url, Class<? extends SimplifiedInformer> itemClass)
    {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
        this.name = name;
        this.thumbnail = thumbnail;
        this.page = page;
        this.url = url;
        this.itemClass = itemClass;
    }

    public static int getCategoryThumbnail(String categoryName)
    {
        int thumbnail = -1;

        switch (categoryName)
        {
            case "films":
                thumbnail = R.drawable.starwars_films_theforceawakens;
                break;

            case "people":
                thumbnail = R.drawable.starwars_people_lukeskywalker;
                break;

            case "planets":
                thumbnail = R.drawable.starwars_planets_tethtve;
                break;

            case "species":
                thumbnail = R.drawable.starwars_species_wookie;
                break;

            case "starships":
                thumbnail = R.drawable.starwars_spaceship_millenniumfalcon;
                break;

            case "vehicles":
                thumbnail = R.drawable.starwars_vehicles_c1426_x_34_landspeeder;
                break;
        }

        return thumbnail;
    }

    public static Class<? extends SimplifiedInformer> getCategoryItemClass(String categoryName)
    {
        Class<? extends SimplifiedInformer> itemClass = null;

        switch (categoryName)
        {
            case "films":
                itemClass = Film.class;
                break;

            case "people":
                itemClass = People.class;
                break;

            case "planets":
                itemClass = Planet.class;
                break;

            case "species":
                itemClass = Species.class;
                break;

            case "starships":
                itemClass = Starship.class;
                break;

            case "vehicles":
                itemClass = Vehicle.class;
                break;
        }

        return itemClass;
    }

    // --------------- Getters and Setters

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getNext()
    {
        return next;
    }

    public void setNext(String next)
    {
        this.next = next;
    }

    public String getPrevious()
    {
        return previous;
    }

    public void setPrevious(String previous)
    {
        this.previous = previous;
    }

    public JsonArray getResults()
    {
        return results;
    }

    public void setResults(JsonArray results)
    {
        this.results = results;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    // --------------- Methods

    public <T> List<T> getResult(Class< List<T> > listClass)
    {
        return APIConsumer.gson.fromJson(results, listClass);
    }

    public Class<? extends SimplifiedInformer> getItemClass()
    {
        return itemClass;
    }

    public void setItemClass(Class<? extends SimplifiedInformer> itemClass)
    {
        this.itemClass = itemClass;
    }
}
