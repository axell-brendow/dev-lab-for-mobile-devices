package com.example.webservice;

import android.os.AsyncTask;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceEndereco extends AsyncTask<String, Void, String>
{
    String cep;

    // MÉTODO QUE FAZ A REQUISIÇÃO HTTP
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://viacep.com.br/ws/" + strings[0] + "/json/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String linha;
            StringBuffer buffer = new StringBuffer();

            while ((linha = reader.readLine()) != null) {
                buffer.append(linha);
                buffer.append("\n");
            }

            return buffer.toString();
        }

        catch (Exception e) {
            e.printStackTrace();

            if (urlConnection != null) urlConnection.disconnect();

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return null;
    }

    // MÉTODO QUE CONFIGURA A RESPOSTA DO MÉTODO HTTP
    @Override
    protected void onPostExecute(String s) {
        if(s == null)
            Util.popup("Não foi possível recuperar os dados...");

        else {
            try {
                String cep = MainActivity.inputCep.getText().toString();
                final String id = Base64.encodeToString(cep.getBytes(), Base64.DEFAULT)
                    .replaceAll("(\\n|\\r)", "");

                JSONObject json = new JSONObject(s);
                MainActivity.inputLogradouro.setText(json.getString("logradouro"));
                MainActivity.inputComplemento.setText(json.getString("complemento"));
                MainActivity.inputBairro.setText(json.getString("bairro"));
                MainActivity.inputLocalidade.setText(json.getString("localidade"));
                MainActivity.inputUf.setText(json.getString("uf"));
                Util.popup("Endereço recuperado com sucesso!");

                final Endereco endereco = new Endereco(
                    id,
                    cep,
                    json.getString("logradouro"), json.getString("complemento"),
                    json.getString("bairro"), json.getString("localidade"),
                    json.getString("uf")
                );

                MainActivity.enderecosReference.addListenerForSingleValueEvent(
                    new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.hasChild(id))
                            {
                                Util.popup("Este endereço já foi cadastrado!");
                            }

                            else
                            {
                                MainActivity.enderecosReference.child(id).setValue(endereco);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    }
                );
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
