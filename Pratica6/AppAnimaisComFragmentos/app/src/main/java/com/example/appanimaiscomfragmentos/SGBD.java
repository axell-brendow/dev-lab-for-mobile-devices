package com.example.appanimaiscomfragmentos;

import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class SGBD
{
    HashMap<String, Tabela> dictTabelas;

    public SGBD(Tabela... tabelas)
    {
        dictTabelas = new HashMap<>();

        for (Tabela tabela : tabelas)
        {
            adicionarTabela(tabela);
        }
    }

    public void adicionarTabela(Tabela tabela)
    {
        if (tabela != null && tabela.nome != null)
        {
            dictTabelas.put(tabela.nome, tabela);
        }
    }

    public Tabela obterTabela(String nome)
    {
        return dictTabelas.get(nome);
    }
}
