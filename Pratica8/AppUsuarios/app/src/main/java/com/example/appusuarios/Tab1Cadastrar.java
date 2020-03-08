package com.example.appusuarios;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Cadastrar extends Fragment {

    private View view;
    private EditText inputNome;
    private EditText inputEmail;
    private EditText inputTelefone;
    private EditText inputEndereco;
    private EditText inputNascimento;
    private Button btnSalvar;

    private TextWatcher gerarObservadorDeDatas(final EditText input)
    {
        return new TextWatcher()
        {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!s.toString().equals(current))
                {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;

                    for (int i = 2; i <= cl && i < 6; i += 2) sel++;

                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) clean = clean + ddmmyyyy.substring(clean.length());

                    else
                    {
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    input.setText(current);
                    input.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private void iniciarVariaveis()
    {
        inputNome = view.findViewById(R.id.inputNome);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputTelefone = view.findViewById(R.id.inputTelefone);
        inputEndereco = view.findViewById(R.id.inputEndereco);
        inputNascimento = view.findViewById(R.id.inputNascimento);
        btnSalvar = view.findViewById(R.id.btnSalvar);

        inputTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        inputNascimento.addTextChangedListener( gerarObservadorDeDatas(inputNascimento) );

        btnSalvar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nome = inputNome.getText().toString();
                        String email = inputEmail.getText().toString();
                        String telefone = inputTelefone.getText().toString();
                        String endereco = inputEndereco.getText().toString();
                        String nascimento = inputNascimento.getText().toString();

                        if (Usuario.validarDados(nome, email, telefone, endereco, nascimento))
                        {
                            Usuario user = new Usuario(nome, email, telefone, endereco, nascimento);
                            user.insert();

                            Util.popup("Usuário cadastrado com sucesso!");
                            inputNome.setText("");
                            inputEmail.setText("");
                            inputTelefone.setText("");
                            inputEndereco.setText("");
                            inputNascimento.setText("");
                        }
                    }
                }
        );
    }

    public Tab1Cadastrar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab1_cadastrar, container, false);

        iniciarVariaveis();

        return view;
    }

}
