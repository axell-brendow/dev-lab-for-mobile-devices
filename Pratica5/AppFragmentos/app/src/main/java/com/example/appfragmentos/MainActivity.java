package com.example.appfragmentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {

    private Button btnMainFragment;
    private Button btnSecondFragment;

    private void changeFragment(int fragmentId, Class<? extends Fragment> fragmentClass)
    {
        Constructor<? extends Fragment> constructor = null;

        try {
            constructor = fragmentClass.getConstructor();
        }

        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (constructor != null)
        {
            try {
                getSupportFragmentManager().beginTransaction().add(
                        fragmentId, constructor.newInstance()
                ).commit();
            }

            catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            catch (InstantiationException e) {
                e.printStackTrace();
            }

            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void setFragmentOnClick(Button button, final int fragmentId, final Class<? extends Fragment> fragmentClass)
    {
        button.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeFragment(fragmentId, fragmentClass);
                }
            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            changeFragment(R.id.activityMainFragment, MainFragment.class);
        }

        setFragmentOnClick(btnMainFragment, R.id.activityMainFragment, MainFragment.class);
        setFragmentOnClick(btnSecondFragment, R.id.btnSecondFragment, SecondFragment.class);
    }
}
