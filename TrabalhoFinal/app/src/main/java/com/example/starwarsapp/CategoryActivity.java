package com.example.starwarsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.function.Consumer;

public class CategoryActivity extends AppCompatActivity
{
    Intent intent;
    Bundle bundle;
    String toolbarTitle;
    Category category;
    CategoryActivity me;

    private void startVariables()
    {
        me = this;
        intent = getIntent();
        bundle = intent.getExtras();

        String categoryName = bundle.getString("category_name");
        final String firstLetter = "" + categoryName.charAt(0);
        final String remaining = categoryName.substring(1);

        APIConsumer.getCategory(categoryName,
                new Consumer<Category>()
                {
                    @Override
                    public void accept(Category category)
                    {
                        me.category = category;
                        toolbarTitle = firstLetter.toUpperCase() + remaining;
                        setTitle(toolbarTitle);

                        RecyclerView recyclerView = findViewById(R.id.category_recyclerview);
                        CategoryRecyclerViewAdapter myAdapter =
                                new CategoryRecyclerViewAdapter(me, category);
                        recyclerView.setLayoutManager(new GridLayoutManager(me, 1));
                        recyclerView.setAdapter(myAdapter);
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
//        View contentView = inflater.inflate(R.layout.activity_category, null, false);
//        MainActivity.drawer.addView(contentView, 0);

        setContentView(R.layout.activity_category);

        startVariables();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        setTitle(toolbarTitle);
    }
}
