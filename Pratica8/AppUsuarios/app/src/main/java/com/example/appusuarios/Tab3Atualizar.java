package com.example.appusuarios;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


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
        final int id = obterId();
//        Usuario usuario = new Usuario().selectByPK(id);
        final ArrayList<Usuario> usuarios = new ArrayList<>();

        MainActivity.dbReference.child("usuarios").addListenerForSingleValueEvent(
            new ValueEventListener() // Esse evento trará todos os dados do nó usuarios
            {
                /**
                 * Essa função é chamada com o snapshot atual do firebase e depois
                 * só é chamada quando os dados mudarem.
                 * @param dataSnapshot
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        // Obtém um iterador sobre pares do tipo (idUsuario: usuario)
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child : children) // Percorre cada par
                        {
                            // Obtém o valor correspondente à chave idUsuario.
                            // Esse valor é um objeto JSON com os mesmos atributos
                            // que a classe desta entidade.
                            Usuario entidade = child.getValue(Usuario.class);
                            usuarios.add(entidade);
                        }

                        int indice = Util.first(
                            new Util.MyFunction<Usuario, Boolean>() {
                                @Override
                                public Boolean apply(Usuario usuario) {
                                    return usuario.getId() == id;
                                }
                            },
                            usuarios
                        );

                        if (indice == -1)
                        {
                            Util.popup("Usuário não encontrado!");
                        }

                        else
                        {
                            Usuario usuario = usuarios.get(indice);
                            inputNome.setText(usuario.getNome());
                            inputEmail.setText(usuario.getEmail());
                            inputTelefone.setText(usuario.getTelefone());
                            inputEndereco.setText(usuario.getEndereco());
                            inputNascimento.setText(usuario.getNascimento());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {  }
            }
        );
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
                    final String nome = obterNome();
                    final String email = obterEmail();
                    final String telefone = obterTelefone();
                    final String endereco = obterEndereco();
                    final String nascimento = obterNascimento();
                    final int id = obterId();

                    if (id != -1 && Usuario.validarDados(nome, email, telefone, endereco, nascimento))
                    {
                        final ArrayList<Usuario> usuarios = new ArrayList<>();

                        MainActivity.dbReference.child("usuarios").addListenerForSingleValueEvent(
                            new ValueEventListener() // Esse evento trará todos os dados do nó usuarios
                            {
                                /**
                                 * Essa função é chamada com o snapshot atual do firebase e depois
                                 * só é chamada quando os dados mudarem.
                                 * @param dataSnapshot
                                 */
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if (dataSnapshot.exists())
                                    {
                                        // Obtém um iterador sobre pares do tipo (idUsuario: usuario)
                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                        for (DataSnapshot child : children) // Percorre cada par
                                        {
                                            // Obtém o valor correspondente à chave idUsuario.
                                            // Esse valor é um objeto JSON com os mesmos atributos
                                            // que a classe desta entidade.
                                            Usuario entidade = child.getValue(Usuario.class);
                                            usuarios.add(entidade);
                                        }

                                        int indice = Util.first(
                                                new Util.MyFunction<Usuario, Boolean>() {
                                                    @Override
                                                    public Boolean apply(Usuario usuario) {
                                                        return usuario.getId() == id;
                                                    }
                                                },
                                                usuarios
                                        );

                                        if (indice == -1)
                                        {
                                            Util.popup("Usuário não encontrado!");
                                        }

                                        else
                                        {
                                            Usuario usuario = usuarios.get(indice);
                                            usuario.setNome(nome);
                                            usuario.setEmail(email);
                                            usuario.setTelefone(telefone);
                                            usuario.setEndereco(endereco);
                                            usuario.setNascimento(nascimento);
                                            usuario.update();

                                            Util.popup("Usuário alterado com sucesso!");
                                            inputNome.setText("");
                                            inputEmail.setText("");
                                            inputTelefone.setText("");
                                            inputEndereco.setText("");
                                            inputNascimento.setText("");
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {  }
                            }
                        );
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
                    final int id = obterId();
                    final ArrayList<Usuario> usuarios = new ArrayList<>();

                    MainActivity.dbReference.child("usuarios").addListenerForSingleValueEvent(
                        new ValueEventListener() // Esse evento trará todos os dados do nó usuarios
                        {
                            /**
                             * Essa função é chamada com o snapshot atual do firebase e depois
                             * só é chamada quando os dados mudarem.
                             * @param dataSnapshot
                             */
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    // Obtém um iterador sobre pares do tipo (idUsuario: usuario)
                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                    for (DataSnapshot child : children) // Percorre cada par
                                    {
                                        // Obtém o valor correspondente à chave idUsuario.
                                        // Esse valor é um objeto JSON com os mesmos atributos
                                        // que a classe desta entidade.
                                        Usuario entidade = child.getValue(Usuario.class);
                                        usuarios.add(entidade);
                                    }

                                    int indice = Util.first(
                                            new Util.MyFunction<Usuario, Boolean>() {
                                                @Override
                                                public Boolean apply(Usuario usuario) {
                                                    return usuario.getId() == id;
                                                }
                                            },
                                            usuarios
                                    );

                                    if (indice == -1)
                                    {
                                        Util.popup("Usuário não encontrado!");
                                    }

                                    else
                                    {
                                        Usuario usuario = usuarios.get(indice);

                                        if (usuario == null) Util.popup("Usuário não encontrado!");

                                        else
                                        {
                                            usuario.delete();

                                            Util.popup("Usuário excluído com sucesso!");
                                            inputNome.setText("");
                                            inputEmail.setText("");
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {  }
                        }
                    );
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
