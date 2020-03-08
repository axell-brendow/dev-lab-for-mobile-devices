package com.example.appanimais;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaAdivinha extends AppCompatActivity {

    private Button btnGerarAleatorio;
    private EditText inputValor;
    private Button btnConfirmar;
    private int numeroAleatorio = -1;
    private int numeroDeTentativas = 0;

    public int gerarAleatorioEntre(int limiteInferior, int limiteSuperior)
    {
        return (int) Math.round(
                limiteInferior + Math.random() * (limiteSuperior - limiteInferior) );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adivinha);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnGerarAleatorio = findViewById(R.id.btnGerarAleatorio);
        inputValor = findViewById(R.id.inputValor);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        btnGerarAleatorio.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numeroDeTentativas = 0;
                    numeroAleatorio = gerarAleatorioEntre(0, 100);
                    Util.popup("Novo número gerado !");
                }
            }
        );

        btnConfirmar.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (numeroAleatorio == -1)
                    {
                        Util.popup("Primeiro gere um número.");
                    }

                    else
                    {
                        String strNumero = inputValor.getText().toString();

                        if (!strNumero.isEmpty())
                        {
                            try
                            {
                                Integer numero = Integer.valueOf(strNumero);
                                Integer diferenca = Math.abs(numeroAleatorio - numero);
                                String mensagem;

                                if (diferenca == 0)
                                {
                                    mensagem = "Parabéns, você acertou em " +
                                            numeroDeTentativas + " tentativas.";

                                    btnGerarAleatorio.callOnClick();
                                }

                                else
                                {
                                    numeroDeTentativas++;

                                    if (diferenca > 75) mensagem = "Muito frio";
                                    else if (diferenca > 50) mensagem = "Frio";
                                    else if (diferenca > 25) mensagem = "Quente";
                                    else mensagem = "Muito quente";
                                }

                                Util.popup(mensagem);
                            }

                            catch (NumberFormatException e)
                            {
                                e.printStackTrace();
                                Util.popup("Número inválido !");
                            }
                        }
                    }
                }
            }
        );
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
