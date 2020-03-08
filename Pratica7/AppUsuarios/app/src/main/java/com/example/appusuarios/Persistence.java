package com.example.appusuarios;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Persistence
{
    public static void save(String... keyValuePairs)
    {
        if (keyValuePairs.length % 2 == 0)
        {
            for (int i = 0; i < keyValuePairs.length; i += 2)
            {
                MainActivity.editor.putString(keyValuePairs[i], keyValuePairs[i + 1]);
            }

            MainActivity.editor.commit();
        }
    }

    public static void save(String[] keys, int[] values)
    {
        if (keys.length == values.length)
        {
            for (int i = 0; i < keys.length; i++)
            {
                MainActivity.editor.putInt(keys[i], values[i]);
            }

            MainActivity.editor.commit();
        }
    }

    public static void save(String key, int value)
    {
        save(new String[]{ key }, new int[] { value });
    }

    public static String recoverStr(String key)
    {
        return MainActivity.sharedPreferences.getString(key, null);
    }

    public static int recoverInt(String key, int defaultValue)
    {
        return MainActivity.sharedPreferences.getInt(key, defaultValue);
    }

    public static void salvarArquivo(
            String name, int mode, String texto, Activity activity)
    {
        try
        {
            FileOutputStream outputStream = activity.openFileOutput(name, mode);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(texto);
            outputStreamWriter.close();
        }

        catch (IOException e)
        {
            Log.v(activity.getClass().getName(), e.toString());
        }
    }

    public static String abrirArquivo(String name, Activity activity)
    {
        String textoFinal = "";

        try
        {
            InputStream arquivo = activity.openFileInput(name);

            if (arquivo != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                {
                    textoFinal += line + "\n";
                }

                arquivo.close();
            }
        }

        catch (IOException e)
        {
            Log.v(activity.getClass().getName(), e.toString());
        }

        return textoFinal;
    }
}
