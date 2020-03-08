package com.example.webservice;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Iterator;
import java.util.function.Function;

public class Util
{
    public static void popup(String text)
    {
        Toast.makeText(
                MainActivity.applicationContext, text, Toast.LENGTH_LONG).show();
    }

    interface MyFunction<TIPO_ARG, TIPO_RET>
    {
        TIPO_RET apply(TIPO_ARG arg);
    }

    public static <T, U extends Iterable<T>> int first(MyFunction<T, Boolean> chooser, U iterable)
    {
        int index = -1, i = 0;
        T value = null;
        Iterator<T> iterator = iterable.iterator();

        while (index == -1 && iterator.hasNext())
        {
            value = iterator.next();

            if (chooser.apply(value)) index = i;
            else i++;
        }

        return index;
    }
}
