package com.example.appanimaiscomfragmentos;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class JogoDoAdivinhaFragment extends Fragment {

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

    public JogoDoAdivinhaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jogo_do_adivinha, container, false);

//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnGerarAleatorio = view.findViewById(R.id.btnGerarAleatorio);
        inputValor = view.findViewById(R.id.inputValor);
        btnConfirmar = view.findViewById(R.id.btnConfirmar);

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

        return view;
    }

//    @Override
//    public boolean onSupportNavigateUp(){
//        finish();
//        return true;
//    }

}
