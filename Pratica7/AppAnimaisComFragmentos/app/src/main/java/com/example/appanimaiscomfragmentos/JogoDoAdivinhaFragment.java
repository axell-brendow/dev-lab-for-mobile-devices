package com.example.appanimaiscomfragmentos;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JogoDoAdivinhaFragment extends Fragment {

    private Button btnGerarAleatorio;
    private EditText inputValor;
    private Button btnConfirmar;
    private TextView txtAviso;
    private int numeroAleatorio = -1;
    private int numeroDeTentativas = 0;

    public int gerarAleatorioEntre(int limiteInferior, int limiteSuperior)
    {
        return (int) Math.round(
                limiteInferior + Math.random() * (limiteSuperior - limiteInferior) );
    }

    public void salvarJogada(int jogador, int numeroDeTentativas)
    {
        // Salva o id do jogador e a sua pontuação
        Persistence.save("" + jogador, numeroDeTentativas);
        Persistence.save("last_player", jogador);
        MainActivity.ultimoJogador = jogador;
        atualizarRecorde(numeroDeTentativas);

        if (numeroDeTentativas < MainActivity.recorde)
        {
            Persistence.save("record", numeroDeTentativas);
            Util.popup("Parabéns, você quebrou o recorde atual e está liderando.");
            MainActivity.recorde = numeroDeTentativas;
        }
    }

    public void atualizarRecorde(int novoValor)
    {
        if (novoValor <= MainActivity.recorde)
        {
            MainActivity.recorde = novoValor;
            txtAviso.setText("Recorde: " + ( (novoValor != Integer.MAX_VALUE) ? novoValor : "vazio" ));
        }
    }

    public JogoDoAdivinhaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jogo_do_adivinha, container, false);

        String recorde = (MainActivity.recorde != Integer.MAX_VALUE) ? "" + MainActivity.recorde : "vazio";

        Util.popup("Você é o(a) jogador(a) número " + MainActivity.ultimoJogador +
                " e o recorde até agora é " + recorde + ".");

//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnGerarAleatorio = view.findViewById(R.id.btnGerarAleatorio);
        inputValor = view.findViewById(R.id.inputValor);
        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        txtAviso = view.findViewById(R.id.txtAviso);

        atualizarRecorde(MainActivity.recorde);

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

                                    salvarJogada(MainActivity.ultimoJogador, numeroDeTentativas);

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
