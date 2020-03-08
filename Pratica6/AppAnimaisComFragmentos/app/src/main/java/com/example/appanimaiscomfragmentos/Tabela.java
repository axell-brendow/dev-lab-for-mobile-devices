package com.example.appanimaiscomfragmentos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Tabela
{
    ArrayList<String> chavesPrimarias;
    ArrayList<String> autoIncrements;
    String nome;

    /**
     * Arranjo com a definição das colunas.
     * <br>
     * Ex.: { "id INTEGER PRIMARY KEY AUTOINCREMENT", "nome VARCHAR", "email VARCHAR" }
     */
    String[] colunas;

    /**
     * Arranjo com os nomes das colunas.
     * <br>
     * Ex.: { "id", "nome", "email" }
     */
    String[] nomesColunas;

    /**
     * HashMap com os nomes das colunas e os seus tipos.
     * <br>
     * Ex.: { "id": "INT", "nome": "TEXT", "email": "TEXT" }
     */
    HashMap<String, String> colunaParaTipo;
    SQLiteDatabase banco;

    public static final String TIPO_INTEIRO = "INT";
    public static final String TIPO_TEXTO = "TEXT";
    public static final String TIPO_REAL = "REAL";

    // ---------------------- Métodos auxiliares ao construtor

    /**
     * Checa se a string recebida contém alguma das strings do arranjo recebido.
     *
     * @param strs Substrings a serem procuradas na string recebida.
     * @param str String recebida.
     *
     * @return {@code true} se a string recebida conter alguma das substrings
     * recebidas.
     */
    private boolean strContem(String[] strs, String str)
    {
        boolean contem = false;

        for (int i = 0; !contem && i < strs.length; i++)
        {
            contem = str.contains(strs[i]);
        }

        return contem;
    }

    /**
     * Analisa uma query de definição de coluna e extrai o tipo dessa coluna
     * (INT, TEXT, REAL).
     *
     * @param coluna Query de criação de uma coluna. Ex.: "id INTEGER PRIMARY KEY
     *               AUTOINCREMENT".
     *
     * @return Alguma das constantes dessa classe:
     * <br>
     * Tabela.TIPO_INTEIRO ou Tabela.TIPO_TEXTO ou Tabela.TIPO_REAL.
     */
    private String extrairTipo(String coluna)
    {
        String tipoColuna = null;
        String[] tiposDeInteiros = new String[]{ "INT", "INTEGER", "TINYINT", "SMALLINT", "MEDIUMINT", "BIGINT", "UNSIGNED BIGINT", "INT2", "INT8" };
        String[] tiposDeTextos = new String[]{ "CHARACTER", "VARCHAR", "VARYING CHARACTER", "NCHAR", "NATIVE CHARACTER", "NVARCHAR", "TEXT", "CLOB" };
        String[] tiposDeReais = new String[]{ "REAL", "DOUBLE", "DOUBLE PRECISION", "FLOAT" };
        boolean encontrado = false;

        if (strContem(tiposDeInteiros, coluna)) tipoColuna = TIPO_INTEIRO;
        else if (strContem(tiposDeTextos, coluna)) tipoColuna = TIPO_TEXTO;
        else if (strContem(tiposDeReais, coluna)) tipoColuna = TIPO_REAL;

        return tipoColuna;
    }

    // ---------------------- Construtores

    public Tabela(SQLiteDatabase banco, String nome, String... colunas)
    {
        this.chavesPrimarias = new ArrayList<>();
        this.autoIncrements = new ArrayList<>();
        this.colunaParaTipo = new HashMap<>();
        this.nomesColunas = new String[colunas.length];
        this.nome = nome;
        this.colunas = colunas;
        this.banco = banco;

        String coluna;

        for (int i = 0; i < colunas.length; i++)
        {
            coluna = colunas[i].toUpperCase();

            colunaParaTipo.put(coluna, extrairTipo(coluna));

            // A primeira coluna do split [0] é o próprio nome da coluna da tabela.
            nomesColunas[i] = colunas[i].split(" ")[0];

            if (coluna.contains("PRIMARY KEY")) chavesPrimarias.add(nomesColunas[i]);
            if (coluna.contains("AUTOINCREMENT")) autoIncrements.add(nomesColunas[i]);
        }

        create();
    }

    // ---------------------- Getters and setters

    public String[] getNomesColunas()
    {
        return Arrays.copyOf(nomesColunas, nomesColunas.length);
    }

    // ---------------------- Métodos auxiliares

    public String obterNomeDaChavePrimaria()
    {
        return chavesPrimarias.get(0);
    }

    public String obterNomeDoAutoIncrements()
    {
        return autoIncrements.get(0);
    }

    /**
     * Se o tipo da coluna da tabela for string, retorna o valor embrulhado com
     * aspas simples. (ex.: 'valor')
     *
     * @param valor  Valor de um atributo qualquer desta entidade.
     * @param coluna Coluna da tabela correspondente ao atributo.
     *
     * @return se o tipo da coluna da tabela for string, retorna o {@code valor}
     * embrulhado com aspas simples. Caso contrário, retorna o próprio {@code valor}.
     */
    public String processarValor(String valor, String coluna)
    {
        if (colunaParaTipo.get(coluna) == Tabela.TIPO_TEXTO)
        {
            valor = "'" + valor + "'";
        }

        return valor;
    }

    /**
     * Percorre o HashMap que associa colunas da tabela aos valores dos atributos
     * desta entidade e cria uma string para a queries SQL.
     * <br><br>
     * Ex.1: "nome = 'myname', cidade = 'mycity', estado = 'mystate'"
     * <br>
     * Ex.2: "'myname', 'mycity', 'mystate'"
     *
     * @param valores HashMap onde as chaves são as colunas da tabela desta entidade
     *                e os valores são os valores atuais dos atributos correspondentes
     *                desta entidade.
     *
     * @param inserirNomesDasColunas Caso seja {@code true}, o Ex.1 é produzido.
     *                               Caso contrário, o Ex. 2 é produzido.
     *
     * @param colunasIgnoradas       Nomes de colunas que não devem entrar na query.
     *
     * @return uma string para uma query SQL.
     */
    public String obterStringParaQueries(
        HashMap<String, Object> valores, boolean inserirNomesDasColunas,
        String... colunasIgnoradas)
    {
        String str = "";
        Set<String> colunas = valores.keySet();
        Iterator<String> iterator = colunas.iterator();
        List<String> listaDeColunasIgnoradas;
        String coluna;
        int colunasNaQuery = 0;

        if (colunasIgnoradas != null && colunasIgnoradas.length > 0)
            listaDeColunasIgnoradas = Arrays.asList(colunasIgnoradas);

        else listaDeColunasIgnoradas = new ArrayList<>();

        listaDeColunasIgnoradas.addAll(autoIncrements);

        if (iterator.hasNext())
        {
            coluna = iterator.next();

            if (!listaDeColunasIgnoradas.contains(coluna))
            {
                str += ( inserirNomesDasColunas ? coluna + " = " : "" ) +
                    processarValor(valores.get(coluna).toString(), coluna);

                colunasNaQuery++;
            }

            while (iterator.hasNext())
            {
                coluna = iterator.next();

                if (!listaDeColunasIgnoradas.contains(coluna))
                {
                    if (colunasNaQuery > 0) str += ", ";

                    str += ( inserirNomesDasColunas ? coluna + " = " : "" ) +
                        processarValor(valores.get(coluna).toString(), coluna);

                    colunasNaQuery++;
                }
            }
        }

        return str;
    }

    /**
     * Percorre o HashMap que associa colunas da tabela aos valores dos atributos
     * desta entidade e cria uma string para a query SQL UPDATE.
     * <br><br>
     * Ex.: "nome = 'myname', cidade = 'mycity', estado = 'mystate'"
     *
     * @param valores HashMap onde as chaves são as colunas da tabela desta entidade
     *                e os valores são os valores atuais dos atributos correspondentes
     *                desta entidade.
     *
     * @param colunasIgnoradas       Nomes de colunas que não devem entrar na query.
     *
     * @return uma string para a query SQL UPDATE.
     */
    public String obterStringParaUpdate(
        HashMap<String, Object> valores, String... colunasIgnoradas)
    {
        return obterStringParaQueries(valores, true, colunasIgnoradas);
    }

    /**
     * Percorre o HashMap que associa colunas da tabela aos valores dos atributos
     * desta entidade e cria uma string para a query SQL INSERT.
     * <br><br>
     * Ex.: "'myname', 'mycity', 'mystate'"
     *
     * @param valores HashMap onde as chaves são as colunas da tabela desta entidade
     *                e os valores são os valores atuais dos atributos correspondentes
     *                desta entidade.
     *
     * @param colunasIgnoradas       Nomes de colunas que não devem entrar na query.
     *
     * @return uma string para a query SQL INSERT.
     */
    public String obterStringParaInsert(
        HashMap<String, Object> valores, String... colunasIgnoradas)
    {
        return obterStringParaQueries(valores, false, colunasIgnoradas);
    }

    /**
     * Concatena as strings do arranjo separando-as por ", ". No final, embrulha toda
     * a string entre parênteses caso solicitado.
     *
     * @param arranjo           Strings a serem concatenadas.
     * @param colocarParenteses Caso seja {@code true}, embrulha a string com
     *                          parênteses.
     *
     * @return as strings do arranjo contenadas e separandas por ", ".
     */
    public static String gerarStringDoArranjo(String[] arranjo, boolean colocarParenteses)
    {
        String strArranjo = Arrays.toString(arranjo);
        strArranjo = strArranjo.substring(1);
        strArranjo = strArranjo.substring(0, strArranjo.length() - 1);

        if (colocarParenteses) strArranjo = "(" + strArranjo + ")";

        return strArranjo;
    }

    /**
     * Concatena as strings do arranjo separando-as por ", ". No final, embrulha toda
     * a string entre parênteses.
     *
     * @param arranjo           Strings a serem concatenadas.
     *
     * @return entre parênteses, as strings do arranjo contenadas e separandas por ", ".
     */
    public static String gerarStringDoArranjo(String[] arranjo)
    {
        return gerarStringDoArranjo(arranjo, true);
    }

    // ---------------------- Métodos com queries SQL

    private void create()
    {
        String query = "CREATE TABLE IF NOT EXISTS " + nome + gerarStringDoArranjo(colunas);

        banco.execSQL(query);
    }

    /**
     * Exclui esta tabela do banco de dados.
     */
    public void drop()
    {
        String query = "DROP TABLE IF EXISTS " + nome;

        banco.execSQL(query);
    }

    public int[] obterIndicesDasColunas(String[] colunas, Cursor cursor)
    {
        int[] indicesDasColunas = new int[colunas.length];

        for (int i = 0; i < indicesDasColunas.length; i++)
        {
            indicesDasColunas[i] = cursor.getColumnIndex(colunas[i]);
        }

        return indicesDasColunas;
    }

    /**
     * Faz o SELECT no banco de dados com as cláusulas e condições estabelecidas
     * extraindo apenas as colunas informadas.
     * Caso nenhuma coluna for informada, todas as colunas serão recuperadas.
     *
     * @param clausulasECondicoes Cláusulas e condições do SELECT. (ex.: "WHERE id = 7").
     * @param colunas Colunas a serem extraídas.
     *
     * @return Matriz onde as linhas representarão as tuplas das entidades filtradas
     * e as colunas representarão os valores das colunas na tabela.
     */
    public String[][] select(String clausulasECondicoes, String... colunas)
    {
        if (colunas == null || colunas.length == 0) colunas = nomesColunas;

        String query = "SELECT " + gerarStringDoArranjo(colunas, false) +
                " FROM " + nome + " " + clausulasECondicoes;

        Cursor cursor = banco.rawQuery(query, null);
        cursor.moveToFirst();

        int numeroDeRegistros = cursor.getCount();
        int[] indicesDasColunas = obterIndicesDasColunas(colunas, cursor);
        String[][] registros = new String[numeroDeRegistros][colunas.length];

        for (int i = 0; i < numeroDeRegistros; i++)
        {
            for (int j = 0; j < colunas.length; j++)
            {
                registros[i][j] = cursor.getString( indicesDasColunas[j] );
            }

            cursor.moveToNext();
        }

        return registros;
    }

    /**
     * Faz o SELECT no banco de dados com a cláusula WHERE e condições estabelecidas
     * extraindo apenas as colunas informadas.
     * Caso nenhuma coluna for informada, todas as colunas serão recuperadas.
     *
     * @param condicoes Condições da clásula WHERE. (ex.: "id = 7").
     * @param colunas Colunas a serem extraídas.
     *
     * @return Matriz onde as linhas representarão as tuplas das entidades filtradas
     * e as colunas representarão os valores das colunas na tabela.
     */
    public String[][] selectWhere(String condicoes, String... colunas)
    {
        return select("WHERE " + condicoes, colunas);
    }

    /**
     * Seleciona a tupla com a chave primária informada.
     *
     * @param primaryKey Valor da chave primária.
     *
     * @return {@code null} caso não for encontrada uma tupla com a chave primária
     * informada. Caso contrário, o arranjo com os valores das colunas da tabela.
     */
    public String[] selectByPK(Object primaryKey)
    {
        String[] tupla = null;
        String[][] tuplas = selectWhere(
            obterNomeDaChavePrimaria() + " = " + primaryKey);

        if (tuplas != null && tuplas.length > 0) tupla = tuplas[0];

        return tupla;
    }

    /**
     * Exclui entidades no banco filtradas pelas cláusulas e condições estabelecidas.
     *
     * @param clausulasECondicoes Cláusulas e condições da query DELETE.
     *                            (ex.: "WHERE id = 7").
     */
    public void delete(String clausulasECondicoes)
    {
        String query = "DELETE FROM " + nome + " " + clausulasECondicoes;

        banco.execSQL(query);
    }

    /**
     * Exclui entidades no banco filtradas pela cláusula WHERE e as condições
     * estabelecidas.
     *
     * @param condicoes Condições da cláusula WHERE. (ex.: "id = 7").
     */
    public void deleteWhere(String condicoes)
    {
        delete("WHERE " + condicoes);
    }

    /**
     * Exclui a entidade no banco filtrada pela cláusula WHERE e pela chave primária
     * sendo o valor recebido. Só utilize este método se a chave primária for única
     * e não composta.
     *
     * @param primaryKey Valor da chave primária da tupla.
     */
    public void deleteByPK(Object primaryKey)
    {
        deleteWhere(obterNomeDaChavePrimaria() + " = " + primaryKey);
    }

    /**
     * Insere uma tupla na tabela.
     *
     * @param valores Valores da tupla. (ex.: { 'nome', 'cidade', 'estado' }).
     */
    public void insert(String... valores)
    {
        if (valores.length == nomesColunas.length)
        {
            String query = "INSERT INTO " + nome + " " +
                    gerarStringDoArranjo(nomesColunas) + " VALUES " +
                    gerarStringDoArranjo(valores);

            banco.execSQL(query);
        }
    }

    /**
     * Filtra as tuplas de acordo com as cláusulas e condições estabelecidas e então
     * atualiza as colunas recebidas com os valores passados.
     *
     * @param clausulasECondicoes Cláusulas e condições da query UPDATE.
     *                            (ex.: "WHERE id = 7").
     * @param colunasEValores     Colunas e valores a serem atualizados.
     *                            (ex.: "nome = 'myname', cidade = 'mycity'").
     */
    public void update(String clausulasECondicoes, String colunasEValores)
    {
        String query = "UPDATE " + nome + " SET " + colunasEValores +
                " " + clausulasECondicoes;

        banco.execSQL(query);
    }

    /**
     * Filtra as tuplas de acordo com as cláusulas e condições estabelecidas e então
     * atualiza as colunas recebidas com os valores passados.
     *
     * @param condicoes       Condições da cláusula WHERE. (ex.: "id = 7").
     * @param colunasEValores Colunas e valores a serem atualizados.
     *                        (ex.: "nome = 'myname', cidade = 'mycity'").
     */
    public void updateWhere(String condicoes, String colunasEValores)
    {
        update("WHERE " + condicoes, colunasEValores);
    }

    /**
     * Atualiza a entidade no banco filtrada pela cláusula WHERE e pela chave primária
     * sendo o valor recebido. Só utilize este método se a chave primária for única
     * e não composta.
     *
     * @param primaryKey          Valor da chave primária da tupla.
     * @param colunasEValores     Colunas e valores a serem atualizados.
     *                            (ex.: "nome = 'myname', cidade = 'mycity'").
     */
    public void updateByPK(Object primaryKey, String colunasEValores)
    {
        updateWhere(obterNomeDaChavePrimaria() + " = " + primaryKey, colunasEValores);
    }
}
