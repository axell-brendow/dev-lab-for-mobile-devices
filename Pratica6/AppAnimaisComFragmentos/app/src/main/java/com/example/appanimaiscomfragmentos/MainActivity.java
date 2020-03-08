package com.example.appanimaiscomfragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context applicationContext;
    public static FragmentManager fragmentManager;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static int ultimoJogador;
    public static int recorde;

    public static SQLiteDatabase banco;
    public static SGBD sgbd;

    private void iniciarVariaveis()
    {
        applicationContext = getApplicationContext();
        fragmentManager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences("ranking.txt", 0);
        editor = sharedPreferences.edit();

        banco = applicationContext.openOrCreateDatabase(
            "DBAppJogos", MODE_PRIVATE, null);

        sgbd = new SGBD(
            new Tabela(
                banco,
                "jogadores",
                "id INTEGER PRIMARY KEY AUTOINCREMENT",
                "nome VARCHAR",
                "numeroDeTentativas INTEGER"
            )
        );

//        editor.clear().commit();  // Limpa todos os dados salvos
        ultimoJogador = Persistence.recoverInt("last_player") + 1;
        recorde = Persistence.recoverInt("record");

        if (ultimoJogador == 0) ultimoJogador = 1;
        if (recorde == -1) recorde = Integer.MAX_VALUE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        iniciarVariaveis();

        if (savedInstanceState == null) Util.startFragment(MainFragment.class);
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
        Class<? extends Fragment> fragmentClass = null;

        if (id == R.id.nav_som)
        {
            fragmentClass = JogoDosSonsFragment.class;
//            startActivity(new Intent(MainActivity.this, JogoDosSonsFragment.class));
        }

        else if (id == R.id.nav_adivinha)
        {
            fragmentClass = JogoDoAdivinhaFragment.class;
//            startActivity(new Intent(MainActivity.this, JogoDoAdivinhaFragment.class));
        }

        if (fragmentClass != null) Util.startFragment(fragmentClass);
        else Util.popup("Nenhum fragmento foi iniciado.");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
