package com.example.arquivotexto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    public static AppCompatActivity me;
    public static Context applicationContext;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    private EditText editTextMultilinha;
    private Button btnSalvar;
    private RadioGroup radioGroup;
    private RadioButton radioBtnCorTexto;

    private void iniciarAtributos()
    {
        me = this;
        editTextMultilinha = findViewById(R.id.editTextMultilinha);
        btnSalvar = findViewById(R.id.btnSalvar);
        radioGroup = findViewById(R.id.radioGroup);

        applicationContext = getApplicationContext();
        sharedPreferences = getSharedPreferences("configTexto.txt", 0);
        editor = sharedPreferences.edit();
    }

    private int obterCorDoTexto()
    {
        int cor = editTextMultilinha.getCurrentTextColor();
        int idRadioButton = radioGroup.getCheckedRadioButtonId();

        if (idRadioButton > 0)
        {
            radioBtnCorTexto = findViewById(idRadioButton);
            String textoRadioButton = radioBtnCorTexto.getText().toString();
            cor = Color.parseColor(
                    textoRadioButton.equals("Azul") ? "#ff33b5e5" : "#ffff4444");
        }

        return cor;
    }

    private void atualizarCorDoTexto()
    {
        editTextMultilinha.setTextColor(obterCorDoTexto());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarAtributos();

        btnSalvar.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    atualizarCorDoTexto();

                    String texto = editTextMultilinha.getText().toString();

                    Persistence.salvarArquivo(
                                "blocoDeNotas.txt", Context.MODE_PRIVATE, texto, me);

                    Util.popup("Salvo com sucesso");
                }
            }
        );

        radioGroup.setOnCheckedChangeListener(
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    Persistence.save("textColor", obterCorDoTexto());
                }
            }
        );

        String texto = Persistence.abrirArquivo("blocoDeNotas.txt", this);
        editTextMultilinha.setText(texto);
        editTextMultilinha.setTextColor(
                Persistence.recoverInt("textColor", Color.parseColor("#000000")));
    }
}
