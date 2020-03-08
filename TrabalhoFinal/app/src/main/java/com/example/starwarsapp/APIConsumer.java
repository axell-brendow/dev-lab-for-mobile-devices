package com.example.starwarsapp;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Class to consume APIs and store/cache their data in the phone.
 */
public class APIConsumer
{
    // ----------------- ATTRIBUTES

    public static final Map<String, JsonObject> apiData = new HashMap<>();
    public static final Gson gson = new Gson();

    public static final String API_BASE = "https://swapi.co/api/";

    // ----------------- METHODS

    public static String createUrl(String category, String query)
    {
        String url = API_BASE + category + "/";

        if (query != null) url += query + "/";

        return url;
    }

    public static String createUrl(String category)
    {
        return createUrl(category, null);
    }

    private static boolean hasQuery(String url)
    {
        String[] split = url.split("/");
        boolean hasQuery = false;

        try
        {
            // Checks if the last field of the url is an integer
            Integer.parseInt(split[split.length - 1]);
            hasQuery = true;
        }

        catch (NumberFormatException e) { }

        return hasQuery;
    }

    public static AsyncTask<String, Integer, JsonObject> get(String url, final Consumer<JsonObject> callback)
    {
        return new AsyncTask<String, Integer, JsonObject>()
        {
            @Override
            protected JsonObject doInBackground(String... urls)
            {
                String url = urls[0].toLowerCase();

                JsonObject jsonObject = APIConsumer.apiData.get(url);

                if (jsonObject == null)
                {
                    try
                    {
                        URL apiUrl = new URL(url);
                        InputStreamReader inputStreamReader = new InputStreamReader(apiUrl.openStream());
                        jsonObject = APIConsumer.gson.fromJson(inputStreamReader, JsonObject.class);

                        APIConsumer.apiData.put(url, jsonObject);
                    }

                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                return jsonObject;
            }

            @Override
            protected void onPostExecute(JsonObject jsonObject)
            {
                super.onPostExecute(jsonObject);

                callback.accept(jsonObject);
            }
        }
        .execute(url);
    }

    public static <T> void get(String url, final Class<T> myClass, final Consumer<T> callback)
    {
        get(url,
                new Consumer<JsonObject>()
                {
                    @Override
                    public void accept(JsonObject jsonObject)
                    {
                        T obj = gson.fromJson(jsonObject, myClass);
                        callback.accept(obj);
                    }
                }
        );
    }

    public static void getCategory(final String categoryName, final Consumer<Category> callback)
    {
        final String categoryUrl = createUrl(categoryName);

        get(categoryUrl, Category.class,
                new Consumer<Category>()
                {
                    @Override
                    public void accept(Category category)
                    {
                        category.setName(categoryName);
                        category.setUrl(categoryUrl);
                        category.setThumbnail(Category.getCategoryThumbnail(categoryName));
                        category.setPage(1);
                        category.setItemClass(Category.getCategoryItemClass(categoryName));

                        callback.accept(category);
                    }
                }
        );
    }

    public static void getCategories(final Consumer< List<Category> > callback)
    {
        get(API_BASE,
                new Consumer<JsonObject>()
                {
                    @Override
                    public void accept(JsonObject jsonObject)
                    {
                        final List<Category> list = new ArrayList<>();
                        final int size = jsonObject.entrySet().size();
                        final int[] i = new int[] { 0 };

                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
                        {
                            String categoryName = entry.getKey();

                            getCategory(categoryName,
                                    new Consumer<Category>()
                                    {
                                        @Override
                                        public void accept(Category category)
                                        {
                                            list.add(category);
                                            i[0]++;

                                            if (i[0] >= size - 1) callback.accept(list);
                                        }
                                    }
                            );
                        }
                    }
                }
        );
    }

    public static void getHomeWorld(String homeWorldUrl, final Consumer<Planet> callback)
    {
        get(homeWorldUrl, Planet.class, callback);
    }

    public static void getHomeWorld(JsonObject jsonObject, final Consumer<Planet> callback)
    {
        getHomeWorld(jsonObject.get("homeworld").getAsString(), callback);
    }
}
