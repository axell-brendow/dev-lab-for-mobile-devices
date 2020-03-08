package com.example.appanimais;

import android.widget.Toast;
import android.content.ContextWrapper;

public class Util
{
    public static void popup(String text)
    {
        Toast.makeText(
                MainActivity.applicationContext,
                text, Toast.LENGTH_LONG).show();
    }
}
