package com.example.starwarsapp;

/*
Links:
https://www.dimensions.guide/element/star-wars-c-3po
https://arte.folha.uol.com.br/ilustrada/2015/12/06/starwars/
https://thenounproject.com/term/death-star/1838/
https://www.dafont.com/pt/star-jedi.font
https://www.flaticon.com/free-icons/star-wars?word=star%20wars&license=selection&order_by=1

https://www.youtube.com/watch?v=SD2t75T5RdY
*/

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    public static MainActivity me;
    public static List<Category> categories;
    public static DrawerLayout drawer;

    private void startVariables()
    {
        me = this;

        APIConsumer.getCategories(
                new Consumer<List<Category>>()
                {
                    @Override
                    public void accept(List<Category> categories)
                    {
                        me.categories = categories;
                        toolbar = findViewById(R.id.toolbar);

                        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(me, categories);
                        recyclerView.setLayoutManager(new GridLayoutManager(me, 2));
                        recyclerView.setAdapter(myAdapter);
                    }
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        startVariables();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (toolbar != null) toolbar.setTitle("STAR WARS API");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent(this, CategoryActivity.class);
        String categoryName = "";

        if (id == R.id.nav_films)
        {
            categoryName = "films";
        }

        else if (id == R.id.nav_people)
        {
            categoryName = "people";
        }

        else if (id == R.id.nav_planets)
        {
            categoryName = "planets";
        }

        else if (id == R.id.nav_species)
        {
            categoryName = "species";
        }

        else if (id == R.id.nav_starships)
        {
            categoryName = "starships";
        }

        else if (id == R.id.nav_vehicles)
        {
            categoryName = "vehicles";
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        intent.putExtra("category_name", categoryName);
        startActivity(intent);

        return true;
    }
}
