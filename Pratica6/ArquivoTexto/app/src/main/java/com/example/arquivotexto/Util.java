package com.example.arquivotexto;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Util
{
    public static void popup(String text)
    {
        Toast.makeText(
                MainActivity.applicationContext, text, Toast.LENGTH_LONG).show();
    }
}
