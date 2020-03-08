package com.example.appaula3_telascomnavigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Tela3 extends AppCompatActivity {

    private TextView txtTela3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela3);

        txtTela3 = findViewById(R.id.txtTela3);
        Bundle extra = getIntent().getExtras();
        String texto;

        if (extra != null)
        {
            texto = extra.getString("texto");
            txtTela3.setText("Texto: " + texto);
        }
    }
}
