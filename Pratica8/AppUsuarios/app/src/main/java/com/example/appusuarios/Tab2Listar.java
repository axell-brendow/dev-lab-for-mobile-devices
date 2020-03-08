package com.example.appusuarios;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
        usuarios = new ArrayList<>();

        MainActivity.dbReference.child("usuarios").addListenerForSingleValueEvent(
                new ValueEventListener() // Esse evento trará todos os dados do nó usuarios
                {
                    /**
                     * Essa função é chamada com o snapshot atual do firebase e depois
                     * só é chamada quando os dados mudarem.
                     * @param dataSnapshot
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            // Obtém um iterador sobre pares do tipo (idUsuario: usuario)
                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                            for (DataSnapshot child : children) // Percorre cada par
                            {
                                // Obtém o valor correspondente à chave idUsuario.
                                // Esse valor é um objeto JSON com os mesmos atributos
                                // que a classe desta entidade.
                                Usuario entidade = child.getValue(Usuario.class);
                                usuarios.add(entidade);
                            }

                            arrayAdapter = new ArrayAdapter<>(
                                    getContext(), R.layout.simple_list_item_1, usuarios);

                            listaDeUsuarios.setAdapter(arrayAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {  }
                }
        );
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
