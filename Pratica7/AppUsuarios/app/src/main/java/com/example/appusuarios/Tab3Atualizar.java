package com.example.appusuarios;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3Atualizar extends Fragment {

    private View view;
    private EditText inputIdentificador;
    private EditText inputNome;
    private EditText inputEmail;
    private EditText inputTelefone;
    private EditText inputEndereco;
    private EditText inputNascimento;

    private Button btnAtualizar;
    private Button btnDeletar;

    private String obterTexto(EditText input) { return input.getText().toString(); }

    private String obterNome() { return obterTexto(inputNome); }
    private String obterEmail() { return obterTexto(inputEmail); }
    private String obterTelefone() { return obterTexto(inputTelefone); }
    private String obterEndereco() { return obterTexto(inputEndereco); }
    private String obterNascimento() { return obterTexto(inputNascimento); }

    private int obterId()
    {
        int id = -1;

        try
        {
            id = Integer.parseInt( obterTexto(inputIdentificador) );
        }

        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        return id;
    }

    private void buscarUsuario()
    {
        int id = obterId();
        Usuario usuario = new Usuario().selectByPK(id);

        if (usuario == null || id == -1)
        {
            Util.popup("Usuário não encontrado!");
        }

        else
        {
            inputNome.setText(usuario.getNome());
            inputEmail.setText(usuario.getEmail());
            inputTelefone.setText(usuario.getTelefone());
            inputEndereco.setText(usuario.getEndereco());
            inputNascimento.setText(usuario.getNascimento());
        }
    }

    private void iniciarVariaveis()
    {
        inputIdentificador = view.findViewById(R.id.inputIdentificador);
        inputNome = view.findViewById(R.id.inputNome);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputTelefone = view.findViewById(R.id.inputTelefone);
        inputEndereco = view.findViewById(R.id.inputEndereco);
        inputNascimento = view.findViewById(R.id.inputNascimento);

        btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnDeletar = view.findViewById(R.id.btnDeletar);

        inputIdentificador.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        if (!s.toString().isEmpty()) buscarUsuario();
                    }

                    @Override
                    public void afterTextChanged(Editable s) { }
                }
        );

        btnAtualizar.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String nome = obterNome();
                        String email = obterEmail();
                        String telefone = obterTelefone();
                        String endereco = obterEndereco();
                        String nascimento = obterNascimento();
                        int id = obterId();

                        if (id != -1 && Usuario.validarDados(nome, email, telefone, endereco, nascimento))
                        {
                            Usuario user = new Usuario().selectByPK(id);

                            if (user == null)
                            {
                                Util.popup("Usuário não encontrado!");
                            }

                            else
                            {
                                user.setNome(nome);
                                user.setEmail(email);
                                user.setTelefone(telefone);
                                user.setEndereco(endereco);
                                user.setNascimento(nascimento);
                                user.update();

                                Util.popup("Usuário alterado com sucesso!");
                                inputNome.setText("");
                                inputEmail.setText("");
                                inputTelefone.setText("");
                                inputEndereco.setText("");
                                inputNascimento.setText("");
                            }
                        }

                        else Util.popup("Usuário não encontrado!");
                    }
                }
        );

        btnDeletar.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        int id = obterId();

                        if (id == -1) Util.popup("Usuário não encontrado!");

                        else
                        {
                            Usuario user = new Usuario().selectByPK(id);

                            if (user == null) Util.popup("Usuário não encontrado!");

                            else
                            {
                                user.delete();

                                Util.popup("Usuário excluído com sucesso!");
                                inputNome.setText("");
                                inputEmail.setText("");
                            }
                        }
                    }
                }
        );
    }

    public Tab3Atualizar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab3_atualizar, container, false);

        iniciarVariaveis();

        return view;
    }

}
