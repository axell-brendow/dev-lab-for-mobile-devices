package com.example.appaula3_telascomnavigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btnNavegarParaAnimais;
    private Button btnParaTela3;
    private EditText inputMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNavegarParaAnimais = findViewById(R.id.btnNavegarParaAnimais);
        btnParaTela3 = findViewById(R.id.btnParaTela3);
        inputMain = findViewById(R.id.inputMain);

        btnNavegarParaAnimais.setOnClickListener( // botão de navegação
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity( new Intent(MainActivity.this, TelaAnimais.class) );
                }
            }
        );

        btnParaTela3.setOnClickListener( // botão de navegação para a tela 3
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent transicao = new Intent(MainActivity.this, Tela3.class);

                    // Põe um dado na transição de telas
                    transicao.putExtra("texto", inputMain.getText().toString());

                    startActivity(transicao);
                }
            }
        );
    }
}
