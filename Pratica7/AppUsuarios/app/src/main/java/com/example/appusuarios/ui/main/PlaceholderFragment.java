package com.example.appusuarios.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appusuarios.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle); // Coloca o bundle como argumento do fragmento

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtém o provedor de modelos de views deste fragmento
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;

        if (getArguments() != null) {
            // Dos parâmetros do fragmento, obtém o índice da aba dele
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        int tabIndex = getArguments().getInt(ARG_SECTION_NUMBER);
        int tabId = R.layout.fragment_tab1_cadastrar;

        switch (tabIndex)
        {
            case 1: tabId = R.layout.fragment_tab1_cadastrar;    break;
            case 2: tabId = R.layout.fragment_tab2_listar;       break;
            case 3: tabId = R.layout.fragment_tab3_atualizar;    break;
        }

        View root = inflater.inflate(tabId, container, false);

        return root;
    }
}