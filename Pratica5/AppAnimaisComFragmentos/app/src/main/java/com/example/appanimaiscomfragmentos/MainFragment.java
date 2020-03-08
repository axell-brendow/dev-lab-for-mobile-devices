package com.example.appanimaiscomfragmentos;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ImageView imgSom;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        imgSom = view.findViewById(R.id.imgSom);

        imgSom.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Util.startFragment(JogoDosSonsFragment.class);
//                    startActivity(new Intent(MainActivity.this, JogoDosSonsFragment.class));
                }
            }
        );

        return view;
    }

}
