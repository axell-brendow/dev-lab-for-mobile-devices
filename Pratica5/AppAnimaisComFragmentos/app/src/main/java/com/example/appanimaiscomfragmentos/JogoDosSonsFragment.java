package com.example.appanimaiscomfragmentos;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JogoDosSonsFragment extends Fragment {

    private ImageView imgMacaco;
    private ImageView imgGato;
    private ImageView imgVaca;
    private ImageView imgCachorro;
    private ImageView imgPato;
    private ImageView imgElefante;
    private ImageView imgCavalo;
    private ImageView imgLeao;
    private ImageView imgAlce;
    private ImageView imgCoruja;
    private ImageView imgPorco;
    private ImageView imgGalo;
    private ImageView imgOvelha;
    private ImageView imgTigre;
    private ImageView imgPavao;
    private ImageView imgZebra;
    private MediaPlayer mediaPlayer;

    private void setarClickImagemComSom(ImageView imagem, final int idSom)
    {
        imagem.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (mediaPlayer.isPlaying())
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    mediaPlayer = MediaPlayer.create(MainActivity.applicationContext, idSom);
                    mediaPlayer.start();
                }
            }
        );
    }

    public JogoDosSonsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jogo_dos_sons, container, false);

        imgMacaco = view.findViewById(R.id.imgMacaco);
        imgGato = view.findViewById(R.id.imgGato);
        imgVaca = view.findViewById(R.id.imgVaca);
        imgCachorro = view.findViewById(R.id.imgCachorro);
        imgPato = view.findViewById(R.id.imgPato);
        imgElefante = view.findViewById(R.id.imgElefante);
        imgCavalo = view.findViewById(R.id.imgCavalo);
        imgLeao = view.findViewById(R.id.imgLeao);
        imgAlce = view.findViewById(R.id.imgAlce);
        imgCoruja = view.findViewById(R.id.imgCoruja);
        imgPorco = view.findViewById(R.id.imgPorco);
        imgGalo = view.findViewById(R.id.imgGalo);
        imgOvelha = view.findViewById(R.id.imgOvelha);
        imgTigre = view.findViewById(R.id.imgTigre);
        imgPavao = view.findViewById(R.id.imgPavao);
        imgZebra = view.findViewById(R.id.imgZebra);
        mediaPlayer = MediaPlayer.create(MainActivity.applicationContext, R.raw.ape);

        setarClickImagemComSom(imgMacaco, R.raw.ape);
        setarClickImagemComSom(imgGato, R.raw.cat);
        setarClickImagemComSom(imgVaca, R.raw.cow);
        setarClickImagemComSom(imgCachorro, R.raw.dog);
        setarClickImagemComSom(imgPato, R.raw.duck);
        setarClickImagemComSom(imgElefante, R.raw.elephant);
        setarClickImagemComSom(imgCavalo, R.raw.horse);
        setarClickImagemComSom(imgLeao, R.raw.lion);
        setarClickImagemComSom(imgAlce, R.raw.moose);
        setarClickImagemComSom(imgCoruja, R.raw.owl);
        setarClickImagemComSom(imgPorco, R.raw.pig);
        setarClickImagemComSom(imgGalo, R.raw.rooster);
        setarClickImagemComSom(imgOvelha, R.raw.sheep);
        setarClickImagemComSom(imgTigre, R.raw.tiger);
        setarClickImagemComSom(imgPavao, R.raw.turkey);
        setarClickImagemComSom(imgZebra, R.raw.zebra);

//        assert getActivity().getActionBar() != null;
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }

//    @Override
//    public boolean onSupportNavigateUp(){
//        finish();
//        return true;
//    }
}
