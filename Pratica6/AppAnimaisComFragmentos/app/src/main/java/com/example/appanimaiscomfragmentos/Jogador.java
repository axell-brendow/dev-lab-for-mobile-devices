package com.example.appanimaiscomfragmentos;

public class Jogador extends Entidade<Jogador>
{
    private int id;
    private String nome;
    private int numeroDeTentativas;

    private Jogador(int id, String nome, int numeroDeTentativas)
    {
        super("jogadores", Jogador.class);

        this.id = id;
        this.nome = nome;
        this.numeroDeTentativas = numeroDeTentativas;
    }

    public Jogador(String nome, int numeroDeTentativas)
    {
        this(-1, nome, numeroDeTentativas);
    }

    public Jogador()
    {
        this(null, -1);
    }

    public int getId() { return id; }
    public int setId (int id) { return this.id = id; }

    public String getNome() { return nome; }
    public String setNome(String nome) { return this.nome = nome; }

    public int getNumeroDeTentativas() { return numeroDeTentativas; }
    public int setNumeroDeTentativas(int numeroDeTentativas)
    {
        return this.numeroDeTentativas = numeroDeTentativas;
    }

    @Override
    public String toString()
    {
        return "{ nome: " + nome + ", numeroDeTentativas: " + numeroDeTentativas + ", id: " + id + " }";
    }
}
