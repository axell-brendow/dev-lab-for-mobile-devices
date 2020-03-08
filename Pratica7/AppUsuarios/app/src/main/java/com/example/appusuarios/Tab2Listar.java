package com.example.appusuarios;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Listar extends Fragment {

    private View view;

    private ListView listaDeUsuarios;

    private ArrayAdapter<Usuario> arrayAdapter;
    private ArrayList<Usuario> usuarios;

    private void iniciarVariaveis()
    {
        listaDeUsuarios = view.findViewById(R.id.listaDeUsuarios);
    }

    private void atualizarUsuarios()
    {
        usuarios = new Usuario().select();
        arrayAdapter = new ArrayAdapter<>(
                getContext(), R.layout.simple_list_item_1, usuarios);

        listaDeUsuarios.setAdapter(arrayAdapter);
    }

    public Tab2Listar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab2_listar, container, false);

        iniciarVariaveis();
        atualizarUsuarios();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) atualizarUsuarios();
    }
}
