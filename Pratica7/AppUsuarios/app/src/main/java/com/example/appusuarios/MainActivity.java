package com.example.appusuarios;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.appusuarios.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive,
     * it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    public static Context applicationContext;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static SGBD sgbd;
    public static SQLiteDatabase banco;

    private void iniciarVariaveis()
    {
        applicationContext = getApplicationContext();
        sharedPreferences = applicationContext.getSharedPreferences(
            "usuarios.txt", MODE_PRIVATE);

        editor = sharedPreferences.edit();

        banco = getApplicationContext().openOrCreateDatabase(
            "DBAppUsuarios", Context.MODE_PRIVATE, null);

        sgbd = new SGBD( // Cria o SGBD e adiciona algumas tabelas a ele
            new Tabela(banco,
                "usuarios",
                    "id INTEGER PRIMARY KEY AUTOINCREMENT",
                    "nome VARCHAR",
                    "email VARCHAR",
                    "telefone VARCHAR",
                    "endereco VARCHAR",
                    "nascimento VARCHAR")
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        iniciarVariaveis();
    }
}