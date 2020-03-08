package com.example.webservice;

public class Endereco
{
    public String id;
    public String cep;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String localidade;
    public String uf;

    public Endereco(
        String id,
        String cep, String logradouro, String complemento,
        String bairro, String localidade, String uf)
    {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }
}
