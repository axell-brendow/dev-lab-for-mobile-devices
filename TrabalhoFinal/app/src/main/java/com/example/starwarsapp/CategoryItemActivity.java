package com.example.starwarsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Consumer;

public class CategoryItemActivity extends AppCompatActivity
{
    String toolbarTitle;
    TextView txtCardPrimaryInfo;
    TextView txtCardDescription;
    JsonObject myJson;
    CategoryItemActivity me;

    public static <T> T createInstance(Class<T> tClass)
    {
        T obj = null;

        try
        {
            obj = tClass.getConstructor().newInstance();
        }

        catch (IllegalAccessException | InstantiationException |
            InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        return obj;
    }

    private void startVariables()
    {
        me = this;

        Bundle bundle = getIntent().getExtras();

        String categoryName = bundle.getString("category_name");
        final String firstLetter = "" + categoryName.charAt(0);
        final String remaining = categoryName.substring(1);

        myJson = new Gson().fromJson(bundle.getString("item_json"), JsonObject.class);

        toolbarTitle = firstLetter.toUpperCase() + remaining + " - " +
            ( myJson.has("name") ?
                myJson.get("name").getAsString() : myJson.get("title").getAsString() );

        DetailedInformer detailedInformer = (DetailedInformer)
            APIConsumer.gson.fromJson(myJson, Category.getCategoryItemClass(categoryName));

        detailedInformer.getInfoList(myJson,
            new Consumer<HashMap<String, String>>()
            {
                @Override
                public void accept(HashMap<String, String> stringStringHashMap)
                {
                    RecyclerView recyclerView = findViewById(R.id.card_recycler_view_details);
                    CategoryItemRecyclerViewAdapter myAdapter =
                        new CategoryItemRecyclerViewAdapter(me, stringStringHashMap);

                    recyclerView.setLayoutManager(new GridLayoutManager(me, 1));
                    recyclerView.setAdapter(myAdapter);
                }
            }
        );

        txtCardPrimaryInfo = findViewById(R.id.card_primary_info);

        detailedInformer.getDetailedPrimaryInfo(myJson,
            new Consumer<String>()
            {
                @Override
                public void accept(String s)
                {
                    txtCardPrimaryInfo.setText(s);
                }
            }
        );

        txtCardDescription= findViewById(R.id.card_description);

        detailedInformer.getDescription(myJson,
            new Consumer<String>()
            {
                @Override
                public void accept(String s)
                {
                    txtCardDescription.setText(s);
                }
            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        LayoutInflater inflater = (LayoutInflater) this
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View contentView = inflater.inflate(R.layout.activity_category_item, null, false);
//        MainActivity.drawer.addView(contentView, 0);

        setContentView(R.layout.activity_category_item);

        startVariables();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        setTitle(toolbarTitle);
    }
}
