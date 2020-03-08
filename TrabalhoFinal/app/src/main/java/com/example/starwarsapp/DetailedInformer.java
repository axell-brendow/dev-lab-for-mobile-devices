package com.example.starwarsapp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Interface to get simplified CardView information.
 */
public interface DetailedInformer
{
    void getDetailedPrimaryInfo(JsonObject jsonObject, final Consumer<String> callback);
    void getDescription(JsonObject jsonObject, final Consumer<String> callback);
    void getInfoList(JsonObject jsonObject, final Consumer< HashMap<String, String> > callback);
}
