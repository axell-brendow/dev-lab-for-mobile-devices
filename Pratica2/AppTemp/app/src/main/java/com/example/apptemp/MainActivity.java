package com.example.apptemp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputTemperatura;
    private CheckBox checkboxKelvin;
    private CheckBox checkboxFahrenheit;
    private CheckBox checkboxReaumur;
    private CheckBox checkboxRankine;
//    private Button btnCalcular;
    private TextView txtKelvin;
    private TextView txtFahrenheit;
    private TextView txtReaumur;
    private TextView txtRankine;

    private Double fahrenheit(Double celsius) { return 32 + 9 * celsius / 5; }
    private Double kelvin(Double celsius) { return celsius + 273; }
    private Double reaumur(Double celsius) { return celsius / 5 * 4; }
    private Double rankine(Double celsius) { return (celsius + 273.15) / 5 * 9; }

    private void setarTemperatura(
            Double valorTemperatura, TextView txtResultado, CheckBox checkBoxDeAtivacao)
    {
        if (checkBoxDeAtivacao.isEnabled())
        {
            txtResultado.setText( String.format("%.2f", valorTemperatura.doubleValue()) );
        }
    }

    private void setarOnChecked(CheckBox checkbox, final TextView txtEquivalente)
    {
        checkbox.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    txtEquivalente.setVisibility( isChecked ? View.VISIBLE : View.INVISIBLE );
                }
            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTemperatura = findViewById(R.id.inputTemperatura);
        checkboxKelvin = findViewById(R.id.checkboxKelvin);
        checkboxFahrenheit = findViewById(R.id.checkboxFahrenheit);
        checkboxReaumur = findViewById(R.id.checkboxReaumur);
        checkboxRankine = findViewById(R.id.checkboxRankine);
//        btnCalcular = findViewById(R.id.btnCalcular);
        txtKelvin = findViewById(R.id.txtKelvin);
        txtFahrenheit = findViewById(R.id.txtFahrenheit);
        txtReaumur = findViewById(R.id.txtReaumur);
        txtRankine = findViewById(R.id.txtRankine);

        setarOnChecked(checkboxKelvin, txtKelvin);
        setarOnChecked(checkboxFahrenheit, txtFahrenheit);
        setarOnChecked(checkboxReaumur, txtReaumur);
        setarOnChecked(checkboxRankine, txtRankine);

        inputTemperatura.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    String strCelsius = editable.toString();

                    if (!strCelsius.isEmpty())
                    {
                        try
                        {
                            Double celsius = Double.valueOf(strCelsius);

                            setarTemperatura(kelvin(celsius), txtKelvin, checkboxKelvin);
                            setarTemperatura(fahrenheit(celsius), txtFahrenheit, checkboxFahrenheit);
                            setarTemperatura(reaumur(celsius), txtReaumur, checkboxReaumur);
                            setarTemperatura(rankine(celsius), txtRankine, checkboxRankine);
                        }

                        catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        );
    }

    /*public void onClickBtnCalcular(View view)
    {
        //
    }*/
}
