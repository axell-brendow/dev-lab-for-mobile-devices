package com.example.webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static Context applicationContext;

    public static EditText inputCep;
    public static EditText inputLogradouro;
    public static EditText inputComplemento;
    public static EditText inputBairro;
    public static EditText inputLocalidade;
    public static EditText inputUf;

    public static DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference enderecosReference = dbReference.child("Enderecos");

    private void inicializarVariaveis()
    {
        applicationContext = getApplicationContext();

        inputCep = findViewById(R.id.inputCep);
        inputLogradouro = findViewById(R.id.inputLogradouro);
        inputComplemento = findViewById(R.id.inputComplemento);
        inputBairro = findViewById(R.id.inputBairro);
        inputLocalidade = findViewById(R.id.inputLocalidade);
        inputUf = findViewById(R.id.inputUf);

        inputCep.addTextChangedListener(
            new TextWatcher()
            {
                @Override
                public void beforeTextChanged(
                    CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                    if (charSequence.length() >= 8)
                    {
                        new WebServiceEndereco().execute(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) { }
            }
        );
    }

    public void print(Object msg)
    {
        Toast.makeText(this, msg.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVariaveis();
    }
}
