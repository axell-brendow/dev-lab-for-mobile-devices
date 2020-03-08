package com.example.appanimaiscomfragmentos;

import android.content.SharedPreferences;
import android.widget.Toast;
import android.content.ContextWrapper;

import androidx.fragment.app.Fragment;

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

    public static int recoverInt(String key)
    {
        return MainActivity.sharedPreferences.getInt(key, -1);
    }
}
