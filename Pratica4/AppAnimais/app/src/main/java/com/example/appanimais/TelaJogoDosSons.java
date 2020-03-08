package com.example.appanimais;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TelaJogoDosSons extends AppCompatActivity {

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

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), idSom);
                    mediaPlayer.start();
                }
            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo_dos_sons);

        imgMacaco = findViewById(R.id.imgMacaco);
        imgGato = findViewById(R.id.imgGato);
        imgVaca = findViewById(R.id.imgVaca);
        imgCachorro = findViewById(R.id.imgCachorro);
        imgPato = findViewById(R.id.imgPato);
        imgElefante = findViewById(R.id.imgElefante);
        imgCavalo = findViewById(R.id.imgCavalo);
        imgLeao = findViewById(R.id.imgLeao);
        imgAlce = findViewById(R.id.imgAlce);
        imgCoruja = findViewById(R.id.imgCoruja);
        imgPorco = findViewById(R.id.imgPorco);
        imgGalo = findViewById(R.id.imgGalo);
        imgOvelha = findViewById(R.id.imgOvelha);
        imgTigre = findViewById(R.id.imgTigre);
        imgPavao = findViewById(R.id.imgPavao);
        imgZebra = findViewById(R.id.imgZebra);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ape);

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

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
