package com.example.appanimaiscomfragmentos;

import android.widget.Toast;
import android.content.ContextWrapper;

import androidx.fragment.app.Fragment;

public class Util
{
    public static void startFragment(Class<? extends Fragment> fragmentClass)
    {
        Fragment fragment;

        try
        {
            fragment = fragmentClass.newInstance();

            MainActivity.fragmentManager.beginTransaction()
                    .replace(R.id.frame_content, fragment).commit();
        }

        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
    }

    public static void popup(String text)
    {
        Toast.makeText(
                MainActivity.applicationContext,
                text, Toast.LENGTH_LONG).show();
    }
}
