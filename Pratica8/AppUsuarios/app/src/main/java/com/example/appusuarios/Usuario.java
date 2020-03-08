package com.example.appusuarios;

import android.content.Context;

import java.util.HashMap;

public class Usuario extends Entidade<Usuario>
{
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String nascimento;

    private Usuario(int id, String nome, String email, String telefone,
                    String endereco, String nascimento)
    {
        super(MainActivity.tabelaUsuarios, Usuario.class);

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.nascimento = nascimento;
    }

    public Usuario(String nome, String email, String telefone, String endereco, String nascimento)
    {
        this(-1, nome, email, telefone, endereco, nascimento);
    }

    public Usuario()
    {
        this(null, null, null, null, null);
    }

    public static boolean validarDados(String nome, String email, String telefone,
                                       String endereco, String nascimento)
    {
        return nome.length() > 1 &&
                email.length() > 2 && email.contains("@") &&
                telefone.length() >= 8 && telefone.length() <= 15 &&
                endereco.length() >= 5 &&
                nascimento.length() == 10;
    }

    public int getId() { return id; }
    public int setId (int id) { return this.id = id; }

    public String getNome() { return nome; }
    public String setNome(String nome) { return this.nome = nome; }

    public String getEmail() { return email; }
    public String setEmail(String email) { return this.email = email; }

    public String getTelefone() { return telefone; }
    public String setTelefone(String telefone) { return this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public String setEndereco(String endereco) { return this.endereco = endereco; }

    public String getNascimento() { return nascimento; }
    public String setNascimento(String nascimento) { return this.nascimento = nascimento; }

    @Override
    public String toString()
    {
        return "{ id: " + id + ", nome: " + nome +
                ", email: " + email + ", telefone: " + telefone +
                ", endereco: " + endereco + ", nascimento: " + nascimento + " }";
    }
}
