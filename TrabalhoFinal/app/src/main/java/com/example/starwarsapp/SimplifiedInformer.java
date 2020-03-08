package com.example.starwarsapp;

import com.google.gson.JsonObject;

import java.util.function.Consumer;

/**
 * Interface to get simplified CardView information.
 */
public interface SimplifiedInformer
{
    void getPrimaryInfo(JsonObject jsonObject, final Consumer<String> callback);
    void getInfo1Name(JsonObject jsonObject, final Consumer<String> callback);
    void getInfo1(JsonObject jsonObject, final Consumer<String> callback);
    void getInfo2Name(JsonObject jsonObject, final Consumer<String> callback);
    void getInfo2(JsonObject jsonObject, final Consumer<String> callback);
}
